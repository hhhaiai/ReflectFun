package cn.reflectfun.demo.cases.hide;

import cn.reflectfun.demo.cases.print.PrintMethod;

import me.hhhaiai.refcore.utils.RLog;
import me.hhhaiai.testcaselib.defcase.ETestCase;

public class HideClassPublicMethod extends ETestCase {

    static String mName = "隐藏类公开方法";

    public HideClassPublicMethod() {
        super(mName);
    }

    @Override
    public void prepare() {}

    @Override
    public boolean predicate() {
        RLog.i("===================隐藏类公开方法===================");

        PrintMethod.hoo("android.app.ResourcesManager", "getConfiguration");
        PrintMethod.hoo("android.app.ResourcesManager", "getInstance");

        return true;
    }
}
