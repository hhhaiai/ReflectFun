package cn.reflectfun.demo.cases.testapi;

import android.content.pm.PackageManager;

import cn.reflectfun.demo.cases.print.PrintMethod;
import me.hhhaiai.testcaselib.defcase.ETestCase;

public class PublicClassTestapiMethod extends ETestCase {

    static String mName = "公开类测试方法";


    public PublicClassTestapiMethod() {
        super(mName);
    }

    @Override
    public void prepare() {
    }

    @Override
    public boolean predicate() {
        PrintMethod.hoo(PackageManager.class, "isPermissionReviewModeEnabled");

        return true;
    }


}


