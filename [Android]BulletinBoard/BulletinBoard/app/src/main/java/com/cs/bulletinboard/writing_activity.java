package com.cs.bulletinboard;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;


public class writing_activity extends Fragment {
    long mNow;
    Date mDate;
    String btitles;
    String webmail;
    String btexts;
    String bdate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    MainActivity activity;
    public static writing_activity newInstance(){
        return new writing_activity();
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    private String getTime(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return mFormat.format(mDate);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        View view = (View)inflater.inflate(R.layout.writing,null);

        final EditText Ebtitle = (EditText)view.findViewById(R.id.btitle);
        final EditText Ebtext = (EditText)view.findViewById(R.id.btext);
        Button button1=(Button)view.findViewById(R.id.btn);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btitles = Ebtitle.getText().toString();
                btexts = Ebtext.getText().toString();
                webmail = WEBMAIL.getInstance().getData();
                bdate = getTime();
                try{
                    String result = "실패";
                    JSONObject boarddata = new JSONObject();
                    boarddata.accumulate("bdate", bdate);
                    boarddata.accumulate("webmail",webmail);
                    boarddata.accumulate("btitle", btitles);
                    boarddata.accumulate("btext",btexts);
                    result = new JSONTASK().execute("putboard",boarddata.toString()).get();
                } catch (Exception e){
                    e.printStackTrace();
                }
                MainActivity activity = (MainActivity)getActivity();
                activity.replaceFragment(new List_activity());
            }
        });
        return view;
    }

}
