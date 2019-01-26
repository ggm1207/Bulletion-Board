package com.cs.bulletinboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONObject;

public class worldcup  extends Activity {

    int data = 63; //맨처음 128 64 빼고 시작
    int imgbutton1data =0;
    int imgbutton2data =0;
    int datanum = 7; //맨처음 2개빼고시작
    int temp =0;
    ImageButton imgbutton1;
    ImageButton imgbutton2;
    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.worldcup);

        imgbutton1 = (ImageButton)findViewById(R.id.imgbutton1);
        imgbutton2 = (ImageButton)findViewById(R.id.imgbutton2);

        imgbutton1data =128;
        imgbutton2data = 64;
        imgbutton1.setImageResource(R.drawable.p_01);
        imgbutton2.setImageResource(R.drawable.p_02);

        imgbutton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datanum--;
                if (datanum == 0) //데이터 넘겨주고 끝 마지막까지 남은 교수
                {
                    String professor= null;
                    int returnvalue=0;
                    while(imgbutton1data!=0)
                    {
                        imgbutton1data/=2;
                        returnvalue++;
                    }
                    professor = String.valueOf(returnvalue);
                    setProfessor(professor);
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                if(imgbutton1data>imgbutton2data)
                    temp = imgbutton2data/2;
                else
                    temp = imgbutton1data/2;
                while((temp & data)==0 && temp != 0)
                    temp/=2;
                imgbutton2data = temp;

                switch(imgbutton2data)
                {
                    case 128:
                        imgbutton2.setImageResource(R.drawable.p_01);
                        break;
                    case 64:
                        imgbutton2.setImageResource(R.drawable.p_02);
                        break;
                    case 32:
                        imgbutton2.setImageResource(R.drawable.p_03);
                        break;
                    case 16:
                        imgbutton2.setImageResource(R.drawable.p_04);
                        break;
                    case 8:
                        imgbutton2.setImageResource(R.drawable.p_05);
                        break;
                    case 4:
                        imgbutton2.setImageResource(R.drawable.p_06);
                        break;
                    case 2:
                        imgbutton2.setImageResource(R.drawable.p_07);
                        break;
                    case 1:
                        imgbutton2.setImageResource(R.drawable.p_08);
                        break;

                }


            }
        });

        imgbutton2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                datanum--;
                if(datanum==0) //데이터 넘겨주고 끝 마지막까지 남은 교수님
                {
                    String professor= null;
                    int returnvalue=0;
                    while(imgbutton2data!=0)
                    {
                        imgbutton2data/=2;
                        returnvalue++;
                    }
                    professor = String.valueOf(returnvalue);
                    setProfessor(professor);
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                if(imgbutton1data>imgbutton2data)
                    temp = imgbutton2data/2;
                else
                    temp = imgbutton1data/2;
                while((temp & data)==0 && temp!=0)
                    temp/=2;
                imgbutton1data = temp;

                switch(imgbutton1data)
                {
                    case 128:
                        imgbutton1.setImageResource(R.drawable.p_01);
                        break;
                    case 64:
                        imgbutton1.setImageResource(R.drawable.p_02);
                        break;
                    case 32:
                        imgbutton1.setImageResource(R.drawable.p_03);
                        break;
                    case 16:
                        imgbutton1.setImageResource(R.drawable.p_04);
                        break;
                    case 8:
                        imgbutton1.setImageResource(R.drawable.p_05);
                        break;
                    case 4:
                        imgbutton1.setImageResource(R.drawable.p_06);
                        break;
                    case 2:
                        imgbutton1.setImageResource(R.drawable.p_07);
                        break;
                    case 1:
                        imgbutton1.setImageResource(R.drawable.p_08);
                        break;
                }
            }
        });
    }

    public void onImgButton1Clicked(View v)
    {

    }

    public void onImgButton2Clicked(View v)
    {

    }
    // 김종민 8 김진호 7 문양세 6 이상민 5 이창기 4 임현승 3 최미정 2 최형진 1
    public void setProfessor(String a){
        if (a.equals("1")){
            PROFESSOR.getInstance().setData("최형진");
        } else if(a.equals("2")){
            PROFESSOR.getInstance().setData("최미정");
        } else if(a.equals("3")){
            PROFESSOR.getInstance().setData("임현승");
        } else if(a.equals("4")){
            PROFESSOR.getInstance().setData("이창기");
        } else if(a.equals("5")){
            PROFESSOR.getInstance().setData("이상민");
        } else if(a.equals("6")){
            PROFESSOR.getInstance().setData("문양세");
        } else if(a.equals("7")){
            PROFESSOR.getInstance().setData("김진호");
        } else {
            PROFESSOR.getInstance().setData("김종민");
        }
        try{
            String result;
            JSONObject professor = new JSONObject();
            professor.accumulate("professor",PROFESSOR.getInstance().getData());
            professor.accumulate("webmail",WEBMAIL.getInstance().getData());
            result = new JSONTASK().execute("changeprofessor",professor.toString()).get();
            if(result.equals("1")){
                Toast.makeText(getBaseContext(),"대단하신 분을 고르셨군요!",Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getBaseContext(),"데이터 전송 실패",Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e){
            e.printStackTrace();
        }


    }


}
