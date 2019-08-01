package com.cs.bulletinboard;

public class BUTTON {
    private int data;
    public int getData()
    {
        return data;
    }
    public void setData(int data)
    {
        this.data = data;
    }
    private static BUTTON instance = null;
    public static synchronized BUTTON getInstance(){
        if(null == instance){
            instance = new BUTTON();
        }
        return instance;
    }
}