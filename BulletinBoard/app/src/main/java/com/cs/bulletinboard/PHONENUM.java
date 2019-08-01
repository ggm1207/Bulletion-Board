package com.cs.bulletinboard;

public class PHONENUM {
    private String  data;
    public String getData()
    {
        return data;
    }
    public void setData(String data)
    {
        this.data = data;
    }
    private static PHONENUM instance = null;
    public static synchronized PHONENUM getInstance(){
        if(null == instance){
            instance = new PHONENUM();
        }
        return instance;
    }
}