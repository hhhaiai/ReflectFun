package cn.reflectfun.demo;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import me.hhhaiai.refcore.Rvoke;

;

public class MainActivity extends Activity {


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);

        // for implementation 'org.lsposed.hiddenapibypass:hiddenapibypass:2.0'
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            Rvoke.invokeStaticMethod("org.lsposed.hiddenapibypass.HiddenApiBypass"
                    , "addHiddenApiExemptions"
                    , new Class[]{String.class},
                    new Object[]{"L"}
            )
            ;
        }

        // for implementation 'me.weishu:free_reflection:3.0.1'
        Rvoke.invokeStaticMethod("me.weishu.reflection.Reflection"
                , "unseal"
                , new Class[]{Context.class},
                new Object[]{newBase}
        );

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void onClick(View view) {
        CaseCtl.gotoCase(MainActivity.this.getApplicationContext());
    }
}