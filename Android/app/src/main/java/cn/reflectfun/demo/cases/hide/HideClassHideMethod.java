package cn.reflectfun.demo.cases.hide;

import android.bluetooth.BluetoothDevice;

import cn.reflectfun.demo.cases.print.PrintMethod;

import me.hhhaiai.refcore.utils.RLog;
import me.hhhaiai.testcaselib.defcase.ETestCase;

import java.io.PrintWriter;

public class HideClassHideMethod extends ETestCase {

    static String mName = "隐藏类隐藏方法";

    public HideClassHideMethod() {
        super(mName);
    }

    @Override
    public void prepare() {}

    @Override
    public boolean predicate() {
        RLog.i("===================隐藏类隐藏方法===================");

        PrintMethod.hoo("android.app.ResourcesManager", "dump", String.class, PrintWriter.class);
        PrintMethod.hoo("android.bluetooth.BluetoothHidHost", "connect", BluetoothDevice.class);
        PrintMethod.hoo("android.bluetooth.BluetoothHidHost", "getPriority", BluetoothDevice.class);
        PrintMethod.hoo(
                "android.bluetooth.BluetoothHidHost",
                "setPriority",
                BluetoothDevice.class,
                int.class);

        return true;
    }
}
