package me.demo.tesss;

import me.hhhaiai.refcore.JRvoke;

public class TT {

    public static void main(String[] args) {
//        Method getName = JRvoke.getMethod("me.demo.tess.Pria", "getName");
//        Method getB =JRvoke. getMethod("me.demo.tess.PublicB", "getB");
//        System.out.println("getName:"+getName);
//        System.out.println("getB:"+getB);

//        PublicB o = new PublicB();
//        System.out.println(o.getB());
        System.out.println(JRvoke.invokeMethod("me.demo.tess.PublicB", "getA"));
        System.out.println(JRvoke.getMethod("me.demo.tess.PublicB", "getB"));
        //  not instance, cann't find public/private not static args
        System.out.println(JRvoke.invokeMethod("me.demo.tess.PublicB", "getB"));
        System.out.println(JRvoke.invokeMethod("me.demo.tess.PublicB", "getSss"));
        // instance
        System.out.println(JRvoke.invokeMethod(JRvoke.getIntance("me.demo.tess.PublicB"), "getB"));
        System.out.println(JRvoke.invokeMethod(JRvoke.getIntance("me.demo.tess.PublicB"), "getSss"));
    }

}
