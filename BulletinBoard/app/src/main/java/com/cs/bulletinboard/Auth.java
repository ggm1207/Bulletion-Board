package com.cs.bulletinboard;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Random;




public class Auth extends Activity implements View.OnClickListener, Dialog.OnCancelListener {

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_CONTACTS = 101;
    String myNumber = null;
    String webmail = null;
    Random random = new Random();
    final int randomNum = random.nextInt(900000)+100000; // 테스트할 6자리 인증번호

    EditText authEmail;
    Button authBtn;

    /*Dialog에 관련된 필드*/

    LayoutInflater dialog; //LayoutInflater
    View dialogLayout; //layout을 담을 View
    Dialog authDialog; //dialog 객체


    /*카운트 다운 타이머에 관련된 필드*/

    TextView time_counter; //시간을 보여주는 TextView
    EditText emailAuth_number; //인증 번호를 입력 하는 칸
    Button emailAuth_btn; // 인증버튼
    CountDownTimer countDownTimer;
    final int MILLISINFUTURE = 300 * 1000; //총 시간 (300초 = 5분)
    final int COUNT_DOWN_INTERVAL = 1000; //onTick 메소드를 호출할 간격 (1초)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        authEmail = (EditText) findViewById(R.id.authEmail);
        authBtn = (Button) findViewById(R.id.authBtn);
        authBtn.setOnClickListener(this);

        int permissionResult = checkSelfPermission(Manifest.permission.READ_PHONE_STATE);

        if (permissionResult == PackageManager.PERMISSION_DENIED) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_PHONE_STATE)) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(Auth.this);
                dialog.setTitle("권한이 필요합니다.").setMessage("이 기능을 위해 권한이 필요합니다. 계속하시겠습니까?")
                        .setPositiveButton("네", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, 1000);
                            }
                        }).setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Auth.this, "기능을 취소했습니다.", Toast.LENGTH_SHORT).show();
                    }
                }).create().show();
            } else {
                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, 1000);
            }
        } else {

            TelephonyManager mgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            try {
                myNumber = mgr.getLine1Number();
                myNumber = myNumber.replace("+", "");
                PHONENUM.getInstance().setData(myNumber);
                Toast.makeText(Auth.this,"반갑습니다!!", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                    e.printStackTrace();
            }
            try{
                String result = "실패";
                JSONObject phonenum = new JSONObject();
                phonenum.accumulate("phonenum", myNumber);
                result = new JSONTASK().execute("checkauth",phonenum.toString()).get(); // 폰, 웹 or 웹 or x
                if (result.equals("0")){

                } else {
                    JSONArray obj = new JSONArray(result);
                    JSONObject author = obj.getJSONObject(0);
                    WEBMAIL.getInstance().setData(author.getString("webmail"));
                    NICKNAME.getInstance().setData(author.getString("nickname"));
                    LOCATE.getInstance().setData(author.getString("location"));
                    SNUM.getInstance().setData(author.getString("schoolnum"));
                    PROFESSOR.getInstance().setData(author.getString("professor"));

                    JSONObject token = new JSONObject();
                    token.accumulate("token",FirebaseInstanceId.getInstance().getToken());
                    result = new JSONTASK().execute("puttoken",token.toString()).get();

                    Intent intents = new Intent(getBaseContext(),MainActivity.class);
                    startActivity(intents);
                    finish();
                }

            } catch (Exception e){
                e.printStackTrace();
            }
        }

    }


    public void countDownTimer() { //카운트 다운 메소드

        time_counter = (TextView) dialogLayout.findViewById(R.id.emailAuth_time_counter);
        //줄어드는 시간을 나타내는 TextView
        emailAuth_number = (EditText) dialogLayout.findViewById(R.id.emailAuth_number);
        //사용자 인증 번호 입력창
        emailAuth_btn = (Button) dialogLayout.findViewById(R.id.emailAuth_btn);
        //인증하기 버튼


        countDownTimer = new CountDownTimer(MILLISINFUTURE, COUNT_DOWN_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) { //(300초에서 1초 마다 계속 줄어듬)

                long emailAuthCount = millisUntilFinished / 1000;
                Log.d("Alex", emailAuthCount + "");

                if ((emailAuthCount - ((emailAuthCount / 60) * 60)) >= 10) { //초가 10보다 크면 그냥 출력
                    time_counter.setText((emailAuthCount / 60) + " : " + (emailAuthCount - ((emailAuthCount / 60) * 60)));
                } else { //초가 10보다 작으면 앞에 '0' 붙여서 같이 출력. ex) 02,03,04...
                    time_counter.setText((emailAuthCount / 60) + " : 0" + (emailAuthCount - ((emailAuthCount / 60) * 60)));
                }
                //emailAuthCount은 종료까지 남은 시간임. 1분 = 60초 되므로,
                // 분을 나타내기 위해서는 종료까지 남은 총 시간에 60을 나눠주면 그 몫이 분이 된다.
                // 분을 제외하고 남은 초를 나타내기 위해서는, (총 남은 시간 - (분*60) = 남은 초) 로 하면 된다.
            }

            @Override
            public void onFinish() { //시간이 다 되면 다이얼로그 종료
                authDialog.cancel();
            }
        }.start();
        emailAuth_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.authBtn:
                EditText Email = (EditText)findViewById(R.id.authEmail);
                webmail = Email.getText().toString().replaceAll(" ","");
                WEBMAIL.getInstance().setData(webmail);
                String S_Email = webmail.concat("@kangwon.ac.kr");
                GMailSender sender = new GMailSender("auth.mail123@gmail.com","thvmxmdnpdj123"); // SUBSTITUTE HERE
                String S_randomNum = Integer.toString(randomNum);
                try {
                    sender.sendMail(
                            "인증메일",
                            S_randomNum ,
                            "auth.mail123@gmail.com",
                            S_Email
                    );
                   Toast.makeText(this, "이메일 전송 성공", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.e("SendMail", e.getMessage(), e);
                }
                dialog = LayoutInflater.from(this);
                dialogLayout = dialog.inflate(R.layout.auth_dialog, null); // LayoutInflater를 통해 XML에 정의된 Resource들을 View의 형태로 반환 시켜 줌
                authDialog = new Dialog(this); //Dialog 객체 생성
                authDialog.setContentView(dialogLayout); //Dialog에 inflate한 View를 탑재 하여줌
                authDialog.setCanceledOnTouchOutside(false); //Dialog 바깥 부분을 선택해도 닫히지 않게 설정함.
                authDialog.setOnCancelListener(this); //다이얼로그를 닫을 때 일어날 일을 정의하기 위해 onCancelListener 설정
                authDialog.show(); //Dialog를 나타내어 준다.
                countDownTimer();
                break;

            case R.id.emailAuth_btn : //다이얼로그 내의 인증번호 인증 버튼을 눌렀을 시
                int user_answer = Integer.parseInt(emailAuth_number.getText().toString());
                if(user_answer==randomNum){
                    try{
                        String result;
                        String token = FirebaseInstanceId.getInstance().getToken();
                        JSONObject authdata = new JSONObject();
                        authdata.accumulate("phonenum", PHONENUM.getInstance().getData());
                        authdata.accumulate("webmail",webmail);
                        authdata.accumulate("token",token);
                        result = new JSONTASK().execute("putauth",authdata.toString()).get();
                        if (result.equals("1")){
                            Toast.makeText(this, "이메일 인증 성공", Toast.LENGTH_SHORT).show();
                            JSONObject checkweb = new JSONObject();
                            checkweb.accumulate("webmail",webmail);
                            result = new JSONTASK().execute("checkwebmail",checkweb.toString()).get();
                            if (result.equals("1")){
                                Intent intents = new Intent(getBaseContext(),FirstOption.class);
                                countDownTimer.cancel();
                                startActivity(intents);
                                finish();
                            } else {
                                Intent intents = new Intent(getBaseContext(),MainActivity.class);
                                countDownTimer.cancel();
                                startActivity(intents);
                                finish();
                            }
                        } else {
                            Toast.makeText(this,"데이터 전송 실패",Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e){
                        Toast.makeText(this,"예외 발생",Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }else {
                    Toast.makeText(this, "이메일 인증 실패", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        countDownTimer.cancel();
    } //다이얼로그 닫을 때 카운트 다운 타이머의 cancel()메소드 호출


}