package cn.reflectfun.demo.cases.hide;

import cn.reflectfun.demo.cases.print.PrintClass;
import me.hhhaiai.refcore.utils.RLog;
import me.hhhaiai.testcaselib.defcase.ETestCase;

public class HasNoClass extends ETestCase {

    static String mName = "不存在类";


    public HasNoClass() {
        super(mName);
    }

    @Override
    public void prepare() {
    }


    @Override
    public boolean predicate() {

        RLog.i("===================不存在类===================");
        PrintClass.hoo("android.app.GG");

        return true;
    }


}


