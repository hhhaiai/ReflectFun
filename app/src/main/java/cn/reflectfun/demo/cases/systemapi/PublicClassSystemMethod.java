package cn.reflectfun.demo.cases.systemapi;

import android.os.Build;
import android.os.UserHandle;
import android.os.UserManager;
import android.telephony.TelephonyManager;

import cn.reflectfun.demo.cases.print.PrintMethod;
import me.hhhaiai.testcaselib.defcase.ETestCase;

public class PublicClassSystemMethod extends ETestCase {

    static String mName = "公开类系统API方法";


    public PublicClassSystemMethod() {
        super(mName);
    }

    @Override
    public void prepare() {
    }

    @Override
    public boolean predicate() {



        if (Build.VERSION.SDK_INT > 16) {
            //    @SystemApi
            //    @RequiresPermission(android.Manifest.permission.MANAGE_USERS)
            //    public boolean isManagedProfile() {
            PrintMethod.hoo(UserManager.class, "isManagedProfile");
            //        @SystemApi
//        @RequiresPermission(android.Manifest.permission.MANAGE_USERS)
//        public String getSeedAccountName() {
            PrintMethod.hoo(UserManager.class, "getSeedAccountName");
        }

        PrintMethod.hoo(TelephonyManager.class, "getCurrentPhoneType");
        PrintMethod.hoo(TelephonyManager.class, "getVisualVoicemailSettings");
        return true;
    }


}


