package cn.reflectfun.demo.cases.testapi;

import android.content.pm.PackageManager;

import cn.reflectfun.demo.cases.print.PrintMethod;
import me.hhhaiai.testcaselib.defcase.ETestCase;

public class PublicClassTestSystemMethod extends ETestCase {

    static String mName = "公开类测试+系统方法";


    public PublicClassTestSystemMethod() {
        super(mName);
    }

    @Override
    public void prepare() {
    }

    @Override
    public boolean predicate() {
        PrintMethod.hoo(PackageManager.class, "getDefaultBrowserPackageNameAsUser",int.class);

        return true;
    }


}


