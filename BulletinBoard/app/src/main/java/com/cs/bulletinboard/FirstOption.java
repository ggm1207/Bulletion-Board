package com.cs.bulletinboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONObject;

public class FirstOption extends Activity{
    EditText nickname;
    RadioGroup rg1, rg2;
    Button send;
    RadioButton btn11, btn12, btn13, btn14, btn15, btn16, btn17, btn18, btn19, btn20, gongzzok, zazzok, giksa, front, it, gong6;
    public String a = "0";
    public String s = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fisrtoption);

        String webmail = WEBMAIL.getInstance().getData();

        nickname = (EditText)findViewById(R.id.nickname);
        rg1 = (RadioGroup)findViewById(R.id.rgroup1);
        rg2 = (RadioGroup)findViewById(R.id.rgroup2);
        send = (Button)findViewById(R.id.sendoption);

        btn13 = (RadioButton) findViewById(R.id.btn13);
        btn14 = (RadioButton) findViewById(R.id.btn14);
        btn15 = (RadioButton) findViewById(R.id.btn15);
        btn16 = (RadioButton) findViewById(R.id.btn16);
        btn17 = (RadioButton) findViewById(R.id.btn17);
        btn18 = (RadioButton) findViewById(R.id.btn18);

        gongzzok = (RadioButton) findViewById(R.id.gongzzok);
        zazzok = (RadioButton) findViewById(R.id.zazzok);
        giksa = (RadioButton) findViewById(R.id.giksa);
        it = (RadioButton) findViewById(R.id.it);
        front = (RadioButton) findViewById(R.id.front);
        gong6 = (RadioButton) findViewById(R.id.gong6);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (rg1.getCheckedRadioButtonId()){
                    case R.id.btn13: a = "13"; break;
                    case R.id.btn14: a = "14"; break;
                    case R.id.btn15: a = "15"; break;
                    case R.id.btn16: a = "16"; break;
                    case R.id.btn17: a = "17"; break;
                    case R.id.btn18: a = "18"; break;
                    default: break;
                }

                switch (rg2.getCheckedRadioButtonId()) {
                    case R.id.gongzzok: s = "공쪽"; break;
                    case R.id.zazzok: s = "자쪽"; break;
                    case R.id.front: s = "정문"; break;
                    case R.id.giksa: s = "긱사"; break;
                    case R.id.it: s = "한빛"; break;
                    case R.id.gong6: s = "공6"; break;
                    default: break;
                }

                String nick = nickname.getText().toString().replaceAll(" ",""); // 10자 제한
                if (a.equals("0") || s.equals("0") || nick.equals("")){
                    Toast.makeText(getBaseContext(),"정보를 다 채워주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        NICKNAME.getInstance().setData(nick);
                        LOCATE.getInstance().setData(s);
                        SNUM.getInstance().setData(a);
                        PROFESSOR.getInstance().setData("무소속");
                        JSONObject author = new JSONObject();
                        author.accumulate("webmail",WEBMAIL.getInstance().getData());
                        author.accumulate("nickname", nick);
                        author.accumulate("location", s);
                        author.accumulate("snum", a);
                        String result = new JSONTASK().execute("putauthor1",author.toString()).get();
                        if(result.equals("1")){
                            Toast.makeText(getBaseContext(),"환영합니다!",Toast.LENGTH_SHORT).show();
                            Intent intents = new Intent(getBaseContext(),MainActivity.class);
                            startActivity(intents);
                            finish();
                        } else {
                            Toast.makeText(getBaseContext(),"닉네임 중복 오류일껄요? 아마",Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e){
                        Toast.makeText(getBaseContext(),"전송 오류", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
