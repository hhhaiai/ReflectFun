package cn.reflectfun.demo.cases.unsupportedappusage;

import cn.reflectfun.demo.cases.print.PrintMethod;

import me.hhhaiai.testcaselib.defcase.ETestCase;

public class HideClassUnMethod extends ETestCase {

    static String mName = "隐藏类不支持方法";

    public HideClassUnMethod() {
        super(mName);
    }

    @Override
    public void prepare() {}

    @Override
    public boolean predicate() {
        PrintMethod.hoo("com.android.internal.telephony.Connection", "getCall");
        PrintMethod.hoo("com.android.internal.telephony.Connection", "getDurationMillis");
        PrintMethod.hoo("com.android.internal.telephony.Connection", "getDisconnectCause");

        return true;
    }
}
