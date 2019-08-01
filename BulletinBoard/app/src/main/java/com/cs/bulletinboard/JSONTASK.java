package com.cs.bulletinboard;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import com.sun.mail.imap.protocol.BODY;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class JSONTASK extends AsyncTask<String,Void,String> {//<시작파라미터,진행상태,서버로받은데이터를 리턴할때사용하는타입>
    @Override

    protected String doInBackground(String... params) {//background에서 동작한다는메소드 //...은 파라미터가 배열처럼 넘어온다는 뜻 한개도될수있고 여러개될수도있음(가변적
        String result=null;

        BufferedReader reader = null;
        try {
            String url = "http://114.70.234.115:8000/"+ params[0];
            //String url = "http://10.0.2.2:8000/"+ params[0];

            String Body = params[1];
            URL URLObject = new URL(url);
            HttpURLConnection Conn = (HttpURLConnection)URLObject.openConnection();

            Conn.setReadTimeout(100000); //10초동안 서버로부터 반응없으면 에러
            Conn.setConnectTimeout(15000); //접속하는 커넥션 타임 15초동안 접속안되면 접속안되는 것으로 간주 (ms)

            Conn.setRequestMethod("POST");//POST방식으로 보냄
            Conn.setRequestProperty("Cache-Control", "no-cache");//캐시 설정
            Conn.setRequestProperty("Content-Type", "application/json");//application JSON 형식으로 전송
            Conn.setRequestProperty("Accept", "text/html");//서버에 response 데이터를 html로 받음

            Conn.setDoInput(true); //안드로이드가 서버로부터 받는거를 트루
            Conn.setDoOutput(true); //안드로이드가 서버로 보내는거를 트루
            Conn.connect();

            OutputStream OutStream = Conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(OutStream));

            writer.write(Body);
            writer.flush();
            writer.close();

            InputStream stream = Conn.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();

            String line = "";
            while((line = reader.readLine()) != null){
                buffer.append(line);
            }


            //result = Builder.toString();//빌더를 차곡차곡쌓아서 result에 넣는다.
            //Toast.makeText(this.Parent, Body.toString(), Toast.LENGTH_LONG).show();

            result = buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}