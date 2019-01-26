package com.cs.bulletinboard;

public class Nickdata {
    private String a;
    private String b;

    public Nickdata() { }

    public Nickdata(String aa, String bb) {
        this.a = aa;
        this.b = bb;
    }

    public String getUserName() {
        return a;
    }

    public void setUserName(String aaa) {
        this.a = aaa;
    }

    public String getMessage() {
        return b;
    }

    public void setMessage(String bbb) {
        this.b = bbb;
    }
}
