package com.cs.bulletinboard;

public class WEBMAIL {
    private String data;
    public String getData()
    {
        return data;
    }
    public void setData(String data)
    {
        this.data = data;
    }
    private static WEBMAIL instance = null;
    public static synchronized WEBMAIL getInstance(){
        if(null == instance){
            instance = new WEBMAIL();
        }
        return instance;
    }
}