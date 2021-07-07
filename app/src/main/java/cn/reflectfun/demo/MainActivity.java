package cn.reflectfun.demo;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import me.hhhaiai.refcore.Rvoke;
import me.hhhaiai.refcore.fkhide.NativeHidden;
import me.hhhaiai.refcore.utils.MContext;

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
        TextView tv = (TextView) findViewById(R.id.textView);
        tv.setText(NativeHidden.test());

    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            int x = NativeHidden.unseal(MContext.getContext().getApplicationInfo().targetSdkVersion);
            Log.i("sanbo", "r:" + x);
        } catch (Throwable e) {
            Log.e("sanbo", Log.getStackTraceString(e));
        }
    }

    public void onClick(View view) {
        CaseCtl.gotoCase(MainActivity.this.getApplicationContext());
    }
}