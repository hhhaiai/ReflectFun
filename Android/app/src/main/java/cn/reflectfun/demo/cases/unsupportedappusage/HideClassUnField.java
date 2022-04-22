package cn.reflectfun.demo.cases.unsupportedappusage;

import android.content.pm.PackageManager;

import cn.reflectfun.demo.cases.print.PrintField;
import cn.reflectfun.demo.cases.print.PrintMethod;
import me.hhhaiai.testcaselib.defcase.ETestCase;

public class HideClassUnField extends ETestCase {

    static String mName = "隐藏类不支持变量";


    public HideClassUnField() {
        super(mName);
    }

    @Override
    public void prepare() {
    }

    @Override
    public boolean predicate() {

        PrintField.hoo("com.android.internal.telephony.Connection", "mCnapName");
        PrintField.hoo("com.android.internal.telephony.Connection", "mDialString");

        return true;
    }


}


