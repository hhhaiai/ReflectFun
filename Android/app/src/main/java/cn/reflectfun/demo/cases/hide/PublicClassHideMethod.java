package cn.reflectfun.demo.cases.hide;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.os.UserManager;

import cn.reflectfun.demo.cases.print.PrintMethod;

import me.hhhaiai.refcore.utils.RLog;
import me.hhhaiai.testcaselib.defcase.ETestCase;

public class PublicClassHideMethod extends ETestCase {

    static String mName = "公开类隐藏方法";

    public PublicClassHideMethod() {
        super(mName);
    }

    @Override
    public void prepare() {}

    @Override
    public boolean predicate() {
        RLog.i("===================公开类隐藏方法===================");

        // failed
        PrintMethod.hoo(ActivityManager.class, "getStatusBarColor");
        PrintMethod.hoo(ApplicationInfo.class, "getHiddenApiEnforcementPolicy");
        PrintMethod.hoo(Activity.class, "canStartActivityForResult");
        PrintMethod.hoo(Activity.class, "autofillClientGetComponentName");
        if (Build.VERSION.SDK_INT > 16) {
            // /** {@hide} */
            //    public boolean isUserRunning(@UserIdInt int userId) {
            PrintMethod.hoo(UserManager.class, "isUserRunning", int.class);
        }
        return true;
    }
}
