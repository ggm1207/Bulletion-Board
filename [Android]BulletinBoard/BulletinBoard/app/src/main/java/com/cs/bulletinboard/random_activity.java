package com.cs.bulletinboard;


import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

public class random_activity extends Fragment {

    public static random_activity newInstance() {
        return new random_activity();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    Button  btn1, btn2, btn3, btn4, btn5, btn6, sendButton, matching; // 자쪽, 공쪽, 정문, 긱사, 한빛관, 공대6호관, 점심
    EditText editText;
    TextView tv1;
    ListView listView;
    LinearLayout btn7;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.random, container, false);

        btn1 = (Button) view.findViewById(R.id.btn1);
        btn2 = (Button)view.findViewById(R.id.btn2);
        btn3 = (Button)view.findViewById(R.id.btn3);
        btn4 = (Button)view.findViewById(R.id.btn4);
        btn5 = (Button)view.findViewById(R.id.btn5);
        btn6 = (Button)view.findViewById(R.id.btn6);
        btn7 = (LinearLayout) view.findViewById(R.id.btn7);
        tv1 = (TextView) view.findViewById(R.id.tv1);
        sendButton = (Button)view.findViewById(R.id.randombtn);
        editText = (EditText)view.findViewById(R.id.randomedit);
        listView = (ListView)view.findViewById(R.id.listView);
        matching = (Button)view.findViewById(R.id.matching);

        final String username = NICKNAME.getInstance().getData();
        int a = BUTTON.getInstance().getData();
        switch (a){
            case 1: btn1.setBackgroundResource(R.drawable.clicktrue);matching.setClickable(true); break;
            case 2: btn2.setBackgroundResource(R.drawable.clicktrue);matching.setClickable(true); break;
            case 3: btn3.setBackgroundResource(R.drawable.clicktrue);matching.setClickable(true); break;
            case 4: btn4.setBackgroundResource(R.drawable.clicktrue);matching.setClickable(true); break;
            case 5: btn5.setBackgroundResource(R.drawable.clicktrue);matching.setClickable(true); break;
            case 6: btn6.setBackgroundResource(R.drawable.clicktrue);matching.setClickable(true); break;
            case 7: btn7.setBackgroundResource(R.drawable.clicktrue);matching.setClickable(true); break;
            default: break;
        }

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BUTTON.getInstance().setData(1);
                setclickable();
                btn1.setBackgroundResource(R.drawable.clicktrue);
                matching.setClickable(true);
                setChatting("zazzok",username);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BUTTON.getInstance().setData(2);
                setclickable();
                btn2.setBackgroundResource(R.drawable.clicktrue);
                matching.setClickable(true);
                setChatting("gongzzok",username);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BUTTON.getInstance().setData(3);
                setclickable();
                btn3.setBackgroundResource(R.drawable.clicktrue);
                matching.setClickable(true);
                setChatting("front",username);
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BUTTON.getInstance().setData(4);
                setclickable();
                btn4.setBackgroundResource(R.drawable.clicktrue);
                matching.setClickable(true);
                setChatting("giksa",username);
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BUTTON.getInstance().setData(5);
                setclickable();
                btn5.setBackgroundResource(R.drawable.clicktrue);
                matching.setClickable(true);
                setChatting("it",username);
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BUTTON.getInstance().setData(6);
                setclickable();
                btn6.setBackgroundResource(R.drawable.clicktrue);
                matching.setClickable(true);
                setChatting("gong6",username);
            }
        });

        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BUTTON.getInstance().setData(7);
                setclickable();
                btn7.setBackgroundResource(R.drawable.clicktrue);
                matching.setClickable(true);
                setChatting("lunch",username);
            }
        });

        matching.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"곧 오실거에요!",Toast.LENGTH_SHORT).show();
                int wh = BUTTON.getInstance().getData();
                try {
                    JSONObject randnum = new JSONObject();
                    randnum.accumulate("num", wh);
                    String result = new JSONTASK().execute("sendpush",randnum.toString()).get();

                } catch (Exception e){
                    e.printStackTrace();
                }
                // 여기서 wh 숫자로 웹에 보내서 웹에서 이프 엘스 조지고 푸쉬알림 가고 3명 이상 되면 채티방 짜라란
            }
        });
        return view;
    }

    public void falseclickable(){
        btn1.setClickable(false);
        btn2.setClickable(false);
        btn3.setClickable(false);
        btn4.setClickable(false);
        btn5.setClickable(false);
        btn6.setClickable(false);
        btn7.setClickable(false);
    }

    public void setclickable(){
        btn1.setBackgroundResource(R.drawable.gg_line);
        btn2.setBackgroundResource(R.drawable.gg_line);
        btn3.setBackgroundResource(R.drawable.gg_line);
        btn4.setBackgroundResource(R.drawable.gg_line);
        btn5.setBackgroundResource(R.drawable.gg_line);
        btn6.setBackgroundResource(R.drawable.gg_line);
        btn7.setBackgroundResource(R.drawable.gg_line);
    }

    public void setChatting(final String s1, final String userName){
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),R.layout.simple_item_list_1,android.R.id.text1);
        listView.setAdapter(adapter);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chatData chatDatas = new chatData(userName, editText.getText().toString());  // 유저 이름과 메세지로 chatData 만들기
                databaseReference.child(s1).push().setValue(chatDatas);  // 기본 database 하위 message라는 child에 chatData를 list로 만들기
                editText.setText("");
            }
        });

        databaseReference.child(s1).addChildEventListener(new ChildEventListener() {  // message는 child의 이벤트를 수신합니다.
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                chatData chatDatas = dataSnapshot.getValue(chatData.class);  // chatData를 가져오고
                adapter.add(chatDatas.getUserName() + ": " + chatDatas.getMessage());  // adapter에 추가합니다.
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) { }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }
}