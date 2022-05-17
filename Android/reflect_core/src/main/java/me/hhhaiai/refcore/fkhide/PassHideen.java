/*
 * Copyright (C) 2021 LSPosed
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.hhhaiai.refcore.fkhide;

import android.annotation.TargetApi;

import me.hhhaiai.refcore.utils.RLog;
import me.hhhaiai.refcore.utils.Unsafe;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandleInfo;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Executable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

// https://lovesykun.cn/archives/android-hidden-api-bypass.html
// https://github.com/LSPosed/AndroidHiddenApiBypass
@TargetApi(26)
public final class PassHideen {
    private static final String TAG = "sanbo.HiddenApiBypass";

    private static final long artOffset;
    private static final long infoOffset;
    private static final long methodsOffset;
    private static final long memberOffset;
    private static final long size;
    private static final long bias;
    private static final Set<String> signaturePrefixes = new HashSet<>();

    static {
        try {
            artOffset =
                    Unsafe.objectFieldOffset(
                            PassHelper.MethodHandle.class.getDeclaredField("artFieldOrMethod"));
            infoOffset =
                    Unsafe.objectFieldOffset(
                            PassHelper.MethodHandleImpl.class.getDeclaredField("info"));
            methodsOffset =
                    Unsafe.objectFieldOffset(PassHelper.Class.class.getDeclaredField("methods"));
            memberOffset =
                    Unsafe.objectFieldOffset(
                            PassHelper.HandleInfo.class.getDeclaredField("member"));
            MethodHandle mhA =
                    MethodHandles.lookup()
                            .unreflect(PassHelper.NeverCall.class.getDeclaredMethod("a"));
            MethodHandle mhB =
                    MethodHandles.lookup()
                            .unreflect(PassHelper.NeverCall.class.getDeclaredMethod("b"));
            long aAddr = Unsafe.getLong(mhA, artOffset);
            long bAddr = Unsafe.getLong(mhB, artOffset);
            long aMethods = Unsafe.getLong(PassHelper.NeverCall.class, methodsOffset);

            size = bAddr - aAddr;
            RLog.v(
                    TAG,
                    size
                            + " "
                            + Long.toString(aAddr, 16)
                            + ", "
                            + Long.toString(bAddr, 16)
                            + ", "
                            + Long.toString(aMethods, 16));
            bias = aAddr - aMethods - size;
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * get declared methods of given class without hidden api restriction
     *
     * @param clazz the class to fetch declared methods
     * @return list of declared methods of {@code clazz}
     */
    public static List<Executable> getDeclaredMethods(Class<?> clazz) {
        ArrayList<Executable> list = new ArrayList<>();
        if (clazz.isPrimitive() || clazz.isArray()) return list;
        MethodHandle mh;
        try {
            mh =
                    MethodHandles.lookup()
                            .unreflect(PassHelper.NeverCall.class.getDeclaredMethod("a"));
        } catch (NoSuchMethodException | IllegalAccessException e) {
            return list;
        }
        long methods = Unsafe.getLong(clazz, methodsOffset);
        int numMethods = Unsafe.getInt(methods);
        RLog.d(TAG, clazz + " has " + numMethods + " methods");
        for (int i = 0; i < numMethods; i++) {
            long method = methods + i * size + bias;
            Unsafe.putLong(mh, artOffset, method);
            Unsafe.putObject(mh, infoOffset, null);
            try {
                MethodHandles.lookup().revealDirect(mh);
            } catch (Throwable ignored) {
            }
            MethodHandleInfo info = (MethodHandleInfo) Unsafe.getObject(mh, infoOffset);
            Executable member = (Executable) Unsafe.getObject(info, memberOffset);
            //            RLog.v(TAG, "got " + clazz.getTypeName() + "." + member +
            //                    "(" +
            // Arrays.stream(member.getTypeParameters()).map(Type::getTypeName).collect(Collectors.joining()) + ")");
            list.add(member);
        }
        return list;
    }

    /**
     * Sets the list of exemptions from hidden API access enforcement.
     *
     * @param signaturePrefixes A list of class signature prefixes. Each item in the list is a prefix match on the type
     *                          signature of a blacklisted API. All matching APIs are treated as if they were on
     *                          the whitelist: access permitted, and no logging..
     * @return whether the operation is successful
     */
    public static boolean setHiddenApiExemptions(String... signaturePrefixes)
            throws ClassNotFoundException {
        List<Executable> methods = getDeclaredMethods(Class.forName("dalvik.system.VMRuntime"));
        Optional<Executable> getRuntime =
                methods.stream().filter(it -> it.getName().equals("getRuntime")).findFirst();
        Optional<Executable> setHiddenApiExemptions =
                methods.stream()
                        .filter(it -> it.getName().equals("setHiddenApiExemptions"))
                        .findFirst();
        if (getRuntime.isPresent() && setHiddenApiExemptions.isPresent()) {
            getRuntime.get().setAccessible(true);
            try {
                Object runtime = ((Method) getRuntime.get()).invoke(null);
                setHiddenApiExemptions.get().setAccessible(true);
                ((Method) setHiddenApiExemptions.get()).invoke(runtime, (Object) signaturePrefixes);
                return true;
            } catch (IllegalAccessException | InvocationTargetException ignored) {
            }
        }
        return false;
    }

    /**
     * Adds the list of exemptions from hidden API access enforcement.
     *
     * @param signaturePrefixes A list of class signature prefixes. Each item in the list is a prefix match on the type
     *                          signature of a blacklisted API. All matching APIs are treated as if they were on
     *                          the whitelist: access permitted, and no logging..
     * @return whether the operation is successful
     */
    public static boolean addHiddenApiExemptions(String... signaturePrefixes)
            throws ClassNotFoundException {
        PassHideen.signaturePrefixes.addAll(Arrays.asList(signaturePrefixes));
        String[] strings = new String[PassHideen.signaturePrefixes.size()];
        PassHideen.signaturePrefixes.toArray(strings);
        return setHiddenApiExemptions(strings);
    }

    /**
     * Clear the list of exemptions from hidden API access enforcement.
     *
     * @return whether the operation is successful
     */
    public static boolean clearHiddenApiExemptions() throws ClassNotFoundException {
        PassHideen.signaturePrefixes.clear();
        return setHiddenApiExemptions("");
    }
}
