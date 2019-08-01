package com.cs.bulletinboard;

public class LOCATE {
    private String data;
    public String getData()
    {
        return data;
    }
    public void setData(String data)
    {
        this.data = data;
    }
    private static LOCATE instance = null;
    public static synchronized LOCATE getInstance(){
        if(null == instance){
            instance = new LOCATE();
        }
        return instance;
    }
}