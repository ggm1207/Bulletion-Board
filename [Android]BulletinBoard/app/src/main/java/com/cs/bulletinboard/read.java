package com.cs.bulletinboard;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class read extends Fragment {

    Button writecomment;
    ListView listview;
    TextView Btext, Bdate, Btitle;

    String bnum;
    String result1, result2, result3;
    String btitle;
    String bdate;
    String nc;

    public static read newInstance(String a1){
        read f1 = new read();
        Bundle args = new Bundle();
        args.putString("bnum",a1);
        f1.setArguments(args);
        return f1;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);}

    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.read, container,false);
        bnum = getArguments().getString("bnum");

        /*Intent intent = getIntent();
        bnum = intent.getStringExtra("bnum");
        btitle = intent.getStringExtra("btitle");
        bdate = intent.getStringExtra("bdate");
        */

        final List<String> list = new ArrayList<>();

        Btext = (TextView)view.findViewById(R.id.btext);
        Bdate = (TextView)view.findViewById(R.id.bdate);
        Btitle = (TextView)view.findViewById(R.id.btitle);
        writecomment = (Button)view.findViewById(R.id.writecomment);
        listview = (ListView)view.findViewById(R.id.ctext);

        final EditText newcomment = (EditText)view.findViewById(R.id.comment);

        // 게시글 내용 채우기
        try {
            JSONObject getboard = new JSONObject();
            getboard.accumulate("bnum",bnum);
            result1 = new JSONTASK().execute("getboard_for_read", getboard.toString()).get(); // 정보를 받아오기만 함!
            if(result1.equals("0")){
            } else{
                String[] date;
                String fine;
                JSONArray obj = new JSONArray(result1);
                JSONObject board = obj.getJSONObject(0);
                Btext.setText(board.getString("btext"));
                date = board.getString("bdate").split("T");
                fine = date[0] + " " + date[1].split(".0")[0];
                Bdate.setText(fine);
                Btitle.setText(board.getString("btitle"));
            }

            JSONObject getcomment = new JSONObject();
            getcomment.accumulate("bnum",bnum);
            result3 = new JSONTASK().execute("getcomment_for_read",getcomment.toString()).get();
            if(result3.equals("0")) {
            }else{
                System.out.println(result3);
                JSONArray obj = new JSONArray(result3);
                System.out.println(obj.length());
                for (int i=0; i<obj.length()-1; i++){
                    JSONObject coms = obj.getJSONObject(i);
                    System.out.println(coms.getString("ctext"));
                    list.add(coms.getString("ctext"));
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        final ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), R.layout.textfinal,list);
        listview.setAdapter(adapter);
        writecomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nc = newcomment.getText().toString();
                try{
                    JSONObject commdata = new JSONObject();
                    commdata.accumulate("bnum",bnum);
                    commdata.accumulate("webmail",WEBMAIL.getInstance().getData());
                    commdata.accumulate("ctext",nc);
                    result2 = new JSONTASK().execute("putcomment",commdata.toString()).get();
                    if (result2.equals("1")){
                        list.add(0,nc);
                        newcomment.setText(null);
                    } else{
                    }
                    adapter.notifyDataSetChanged();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        return view;
    }
}