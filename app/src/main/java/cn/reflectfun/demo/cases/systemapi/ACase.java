package cn.reflectfun.demo.cases.systemapi;

import me.hhhaiai.testcaselib.defcase.ETestCase;

public class ACase extends ETestCase {

    static String mName = "A";


    public ACase() {
        super(mName);
    }

    @Override
    public void prepare() {
    }

    @Override
    public boolean predicate() {
        return true;
    }


}


