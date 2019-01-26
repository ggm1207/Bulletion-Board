package com.cs.bulletinboard;

public class PROFESSOR {
    private String data;
    public String getData()
    {
        return data;
    }
    public void setData(String data)
    {
        this.data = data;
    }
    private static PROFESSOR instance = null;
    public static synchronized PROFESSOR getInstance(){
        if(null == instance){
            instance = new PROFESSOR();
        }
        return instance;
    }
}