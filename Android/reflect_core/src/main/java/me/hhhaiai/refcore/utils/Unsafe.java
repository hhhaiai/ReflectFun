package me.hhhaiai.refcore.utils;

import java.lang.reflect.Field;

public final class Unsafe {
    private static final String TAG = "Unsafe";

    private static Object unsafe;
    private static Class unsafeClass;

    static {
        try {
            unsafeClass = Class.forName("sun.misc.Unsafe");
            Field theUnsafe = unsafeClass.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            unsafe = theUnsafe.get(null);
        } catch (Exception e) {
            try {
                final Field theUnsafe = unsafeClass.getDeclaredField("THE_ONE");
                theUnsafe.setAccessible(true);
                unsafe = theUnsafe.get(null);
            } catch (Exception e2) {
            }
        }
    }

    private Unsafe() {}

    @SuppressWarnings("unchecked")
    public static int arrayBaseOffset(Class cls) {
        try {
            return (int)
                    unsafeClass
                            .getDeclaredMethod("arrayBaseOffset", Class.class)
                            .invoke(unsafe, cls);
        } catch (Exception e) {
            return 0;
        }
    }

    @SuppressWarnings("unchecked")
    public static int arrayIndexScale(Class cls) {
        try {
            return (int)
                    unsafeClass
                            .getDeclaredMethod("arrayIndexScale", Class.class)
                            .invoke(unsafe, cls);
        } catch (Exception e) {
            return 0;
        }
    }

    @SuppressWarnings("unchecked")
    public static long objectFieldOffset(Field field) {
        try {
            return (long)
                    unsafeClass
                            .getDeclaredMethod("objectFieldOffset", Field.class)
                            .invoke(unsafe, field);
        } catch (Exception e) {
            return 0;
        }
    }

    @SuppressWarnings("unchecked")
    public static int getInt(Object array, long offset) {
        try {
            return (int)
                    unsafeClass
                            .getDeclaredMethod("getInt", Object.class, long.class)
                            .invoke(unsafe, array, offset);
        } catch (Exception e) {
            try {
                return (int)
                        unsafeClass
                                .getDeclaredMethod("getIntVolatile", Object.class, long.class)
                                .invoke(unsafe, array, offset);
            } catch (Exception e1) {
            }
        }
        return 0;
    }

    public static int getInt(long address) {
        try {
            return (int)
                    unsafeClass.getDeclaredMethod("getInt", long.class).invoke(unsafe, address);
        } catch (Exception e) {
        }
        return 0;
    }

    @SuppressWarnings("unchecked")
    public static long getLong(Object array, long offset) {
        try {
            return (long)
                    unsafeClass
                            .getDeclaredMethod("getLong", Object.class, long.class)
                            .invoke(unsafe, array, offset);
        } catch (Exception e) {
            try {
                return (long)
                        unsafeClass
                                .getDeclaredMethod("getLongVolatile", Object.class, long.class)
                                .invoke(unsafe, array, offset);
            } catch (Exception e1) {
            }
        }
        return 0;
    }

    @SuppressWarnings("unchecked")
    public static void putLong(Object array, long offset, long value) {
        try {
            unsafeClass
                    .getDeclaredMethod("putLongVolatile", Object.class, long.class, long.class)
                    .invoke(unsafe, array, offset, value);
        } catch (Exception e) {
            try {
                unsafeClass
                        .getDeclaredMethod("putLong", Object.class, long.class, long.class)
                        .invoke(unsafe, array, offset, value);
            } catch (Exception e1) {
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static void putInt(Object array, long offset, int value) {
        try {
            unsafeClass
                    .getDeclaredMethod("putIntVolatile", Object.class, long.class, int.class)
                    .invoke(unsafe, array, offset, value);
        } catch (Exception e) {
            try {
                unsafeClass
                        .getDeclaredMethod("putInt", Object.class, long.class, int.class)
                        .invoke(unsafe, array, offset, value);
            } catch (Exception e1) {
            }
        }
    }

    public static long getObjectAddress(Object obj) {
        try {
            Object[] array = new Object[] {obj};
            if (arrayIndexScale(Object[].class) == 8) {
                return getLong(array, arrayBaseOffset(Object[].class));
            } else {
                return 0xffffffffL & getInt(array, arrayBaseOffset(Object[].class));
            }
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * get Object from address, refer: http://mishadoff.com/blog/java-magic-part-4-sun-dot-misc-dot-unsafe/
     *
     * @param address the address of a object.
     * @return
     */
    public static Object getObject(long address) {
        Object[] array = new Object[] {null};
        long baseOffset = arrayBaseOffset(Object[].class);
        if (Runtime.is64Bit()) {
            putLong(array, baseOffset, address);
        } else {
            putInt(array, baseOffset, (int) address);
        }
        return array[0];
    }

    /**
     * Stores an <code>Object</code> field into the given object.
     *
     * @param obj      non-null; object containing the field
     * @param offset   offset to the field within <code>obj</code>
     * @param newValue the value to store
     */
    public static void putObject(Object obj, long offset, Object newValue) {
        try {
            unsafeClass
                    .getDeclaredMethod("putObjectVolatile", Object.class, long.class, Object.class)
                    .invoke(unsafe, obj, offset, newValue);
        } catch (Exception e) {
            try {
                unsafeClass
                        .getDeclaredMethod("putObject", Object.class, long.class, Object.class)
                        .invoke(unsafe, obj, offset, newValue);
            } catch (Exception e1) {
            }
        }
    }

    /**
     * Gets an <code>Object</code> field from the given object.
     *
     * @param obj    non-null; object containing the field
     * @param offset offset to the field within <code>obj</code>
     * @return the retrieved value
     */
    public static Object getObject(Object obj, long offset) {
        try {
            return unsafeClass
                    .getDeclaredMethod("getObjectVolatile", Object.class, long.class)
                    .invoke(unsafe, obj, offset);
        } catch (Exception e) {
            try {
                return unsafeClass
                        .getDeclaredMethod("getObject", Object.class, long.class)
                        .invoke(unsafe, obj, offset);
            } catch (Exception e1) {
            }
        }
        return null;
    }
}
