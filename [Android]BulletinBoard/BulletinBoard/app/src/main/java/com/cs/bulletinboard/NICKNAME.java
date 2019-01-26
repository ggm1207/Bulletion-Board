package com.cs.bulletinboard;

public class NICKNAME {
    private String data;
    public String getData()
    {
        return data;
    }
    public void setData(String data)
    {
        this.data = data;
    }
    private static NICKNAME instance = null;
    public static synchronized NICKNAME getInstance(){
        if(null == instance){
            instance = new NICKNAME();
        }
        return instance;
    }
}