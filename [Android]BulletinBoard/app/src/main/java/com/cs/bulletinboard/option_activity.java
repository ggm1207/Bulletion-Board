package com.cs.bulletinboard;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

public class option_activity extends Fragment {
    RadioButton gongzzok, zazzok, giksa, front, it, gong6;

    public static option_activity newInstance() {
        return new option_activity();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.options,container,false);
        TextView nick = (TextView)view.findViewById(R.id.nick);
        TextView snum = (TextView)view.findViewById(R.id.snum);
        TextView labs = (TextView)view.findViewById(R.id.labs);
        final TextView loc = (TextView)view.findViewById(R.id.loc);
        final Button btn = (Button)view.findViewById(R.id.btn);
        Button btn1 = (Button)view.findViewById(R.id.btn1);
        final Button btn2 = (Button)view.findViewById(R.id.btn2);

        gongzzok = (RadioButton) view.findViewById(R.id.gongzzok);
        zazzok = (RadioButton) view.findViewById(R.id.zazzok);
        giksa = (RadioButton) view.findViewById(R.id.giksa);
        it = (RadioButton) view.findViewById(R.id.it);
        front = (RadioButton) view.findViewById(R.id.front);
        gong6 = (RadioButton) view.findViewById(R.id.gong6);
        final RadioGroup rg2 = (RadioGroup)view.findViewById(R.id.rgroup2);

        String location;


        nick.setText(NICKNAME.getInstance().getData());
        snum.setText(SNUM.getInstance().getData());
        labs.setText(PROFESSOR.getInstance().getData() + "교수님 산하 학부생");
        loc.setText(LOCATE.getInstance().getData());

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),worldcup.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rg2.setVisibility(View.VISIBLE);
                btn2.setVisibility(View.VISIBLE);
            }
        });

        gongzzok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LOCATE.getInstance().setData("공쪽"); Toast.makeText(getActivity(),LOCATE.getInstance().getData(),Toast.LENGTH_SHORT).show();
            }
        });
        zazzok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LOCATE.getInstance().setData("자쪽"); Toast.makeText(getActivity(),LOCATE.getInstance().getData(),Toast.LENGTH_SHORT).show();
            }
        });
        front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LOCATE.getInstance().setData("정문"); Toast.makeText(getActivity(),LOCATE.getInstance().getData(),Toast.LENGTH_SHORT).show();
            }
        });
        giksa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LOCATE.getInstance().setData("긱사"); Toast.makeText(getActivity(),LOCATE.getInstance().getData(),Toast.LENGTH_SHORT).show();
            }
        });
        it.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LOCATE.getInstance().setData("한빛"); Toast.makeText(getActivity(),LOCATE.getInstance().getData(),Toast.LENGTH_SHORT).show();
            }
        });
        gong6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LOCATE.getInstance().setData("공6"); Toast.makeText(getActivity(),LOCATE.getInstance().getData(),Toast.LENGTH_SHORT).show();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String locs = LOCATE.getInstance().getData();
                loc.setText(locs);
                changelocation(locs);
                rg2.setVisibility(View.INVISIBLE);
                btn2.setVisibility(View.INVISIBLE);
            }
        });
        return view;
    }

    public void changelocation(String s){
        try{
            JSONObject loc = new JSONObject();
            loc.accumulate("loc",s);
            loc.accumulate("webmail",WEBMAIL.getInstance().getData());
            String result = new JSONTASK().execute("changelocation",loc.toString()).get();
            if (result.equals("1")){
                Toast.makeText(getActivity(),"성공적으로 변경되었습니다.",Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(),"데이터 전송 실패",Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }


}