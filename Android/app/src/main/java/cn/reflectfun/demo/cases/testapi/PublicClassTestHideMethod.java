package cn.reflectfun.demo.cases.testapi;

import android.content.Context;
import android.content.pm.LauncherApps;
import android.content.pm.PackageManager;
import android.os.Build;

import cn.reflectfun.demo.cases.print.PrintMethod;
import me.hhhaiai.testcaselib.defcase.ETestCase;

public class PublicClassTestHideMethod extends ETestCase {

    static String mName = "公开类测试+系统方法";


    public PublicClassTestHideMethod() {
        super(mName);
    }

    @Override
    public void prepare() {
    }

    @Override
    public boolean predicate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            PrintMethod.hoo(LauncherApps.class, "LauncherApps", Context.class);
        }

        return true;
    }


}


