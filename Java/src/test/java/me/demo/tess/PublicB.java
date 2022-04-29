package me.demo.tess;

public class PublicB {
    String name = "q";
    static String stati_Name = "Twodog";

    public PublicB() {
        name += "q";
    }

    public String getB() {
        return name;
    }

    public String getSss() {
        return "22222";
    }

    public static String getSome() {
        return "static args:" + stati_Name + "\r\n" + "not static cann't read！" ;
    }

    public static String getA() {
        return "QQ";
    }
}
