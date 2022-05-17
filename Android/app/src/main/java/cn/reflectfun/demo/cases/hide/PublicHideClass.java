package cn.reflectfun.demo.cases.hide;

import cn.reflectfun.demo.cases.print.PrintClass;

import me.hhhaiai.refcore.utils.RLog;
import me.hhhaiai.testcaselib.defcase.ETestCase;

public class PublicHideClass extends ETestCase {

    static String mName = "隐藏类";

    public PublicHideClass() {
        super(mName);
    }

    @Override
    public void prepare() {}

    @Override
    public boolean predicate() {

        RLog.i("===================隐藏类===================");

        PrintClass.hoo("android.app.ActivityManagerNative");
        PrintClass.hoo("android.app.JobSchedulerImpl");
        PrintClass.hoo("android.app.OnActivityPausedListener");
        PrintClass.hoo("android.app.PackageDeleteObserver");
        PrintClass.hoo("android.app.PackageInstallObserver");

        return true;
    }
}
