package cn.reflectfun.demo.cases.systemapi;

import android.os.Build;
import android.os.UserManager;
import android.telephony.TelephonyManager;

import cn.reflectfun.demo.cases.print.PrintField;

import me.hhhaiai.testcaselib.defcase.ETestCase;

public class PublicClassSystemField extends ETestCase {

    static String mName = "公开类系统变量";

    public PublicClassSystemField() {
        super(mName);
    }

    @Override
    public void prepare() {}

    @Override
    public boolean predicate() {

        if (Build.VERSION.SDK_INT > 16) {
            PrintField.hoo(UserManager.class, "USER_TYPE_FULL_SYSTEM");
            PrintField.hoo(UserManager.class, "USER_TYPE_SYSTEM_HEADLESS");
        }

        PrintField.hoo(TelephonyManager.class, "KEY_TYPE_EPDG");
        PrintField.hoo(TelephonyManager.class, "KEY_TYPE_WLAN");
        return true;
    }
}
