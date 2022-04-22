package cn.reflectfun.demo.cases.testapi;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.UserHandle;

import cn.reflectfun.demo.cases.print.PrintMethod;
import me.hhhaiai.testcaselib.defcase.ETestCase;

public class PublicClassTestapiAbstractMethod extends ETestCase {

    static String mName = "公开类测试抽象方法";


    public PublicClassTestapiAbstractMethod() {
        super(mName);
    }

    @Override
    public void prepare() {
    }

    @Override
    public boolean predicate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            PrintMethod.hoo(PackageManager.class, "getInstallReason", String.class, UserHandle.class);
        }

        return true;
    }


}


