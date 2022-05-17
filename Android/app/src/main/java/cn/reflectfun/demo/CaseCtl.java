package cn.reflectfun.demo;

import android.content.Context;

import me.hhhaiai.testcaselib.CaseHelper;
import me.hhhaiai.testcaselib.utils.L;

public class CaseCtl {

    public static void gotoCase(Context context) {
        try {
            CaseHelper.addSuite(context, "[@hide]测试", "cn.reflectfun.demo.cases.hide");
            CaseHelper.addSuite(
                    context,
                    "[@UnsupportedAppUsage]测试",
                    "cn.reflectfun.demo.cases.unsupportedappusage");
            CaseHelper.addSuite(context, "[@SystemApi]测试", "cn.reflectfun.demo.cases.systemapi");
            CaseHelper.addSuite(context, "[@testAPI]测试", "cn.reflectfun.demo.cases.testapi");
            CaseHelper.openCasePage(context);
        } catch (Throwable e) {
            L.e(e);
        }
    }
}
