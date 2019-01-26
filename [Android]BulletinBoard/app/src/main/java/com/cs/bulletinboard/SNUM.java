package com.cs.bulletinboard;

public class SNUM {
    private String data;
    public String getData()
    {
        return data;
    }
    public void setData(String data)
    {
        this.data = data;
    }
    private static SNUM instance = null;
    public static synchronized SNUM getInstance(){
        if(null == instance){
            instance = new SNUM();
        }
        return instance;
    }
}