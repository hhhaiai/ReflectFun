package me.hhhaiai.refcore;

import org.junit.Test;

import me.hhhaiai.refcore.utils.RLog;

public class RLogTest {
    @Test
    public void v() {
        RLog.v("xxx from v");
    }

    @Test
    public void d() {
        RLog.d("xxx from d");
    }

    @Test
    public void i() {
        RLog.i("xxx from i");
    }

    @Test
    public void e() {
        RLog.e("xxx from e");
    }

    @Test
    public void w() {
        RLog.w("xxx from w");
    }

    @Test
    public void wtf() {
        RLog.wtf("xxx from wtf");
    }
}
