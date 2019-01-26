package com.cs.bulletinboard;

import android.app.Activity;
import android.database.DataSetObserver;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class realchat_activity extends Fragment {
    public static realchat_activity newInstance() {
        return new realchat_activity();
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();


    ListView listView;
    EditText editText;
    Button sendButton;
    TextView chatnum;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        View view1 = inflater.inflate(R.layout.realchat,container,false);
        listView = (ListView)view1.findViewById(R.id.listView);
        editText = (EditText)view1.findViewById(R.id.realeditchat);
        sendButton = (Button)view1.findViewById(R.id.btn);
        chatnum = (TextView)view1.findViewById(R.id.chatnum1);

        final String userName = NICKNAME.getInstance().getData();
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),R.layout.simple_item_list_1,android.R.id.text1);
        final ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getActivity(),R.layout.simple_item_list_1,android.R.id.text1);

        listView.setAdapter(adapter);
        listView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);

        Nickdata nick = new Nickdata(NICKNAME.getInstance().getData(), "trash");

        databaseReference.child("realchatclientnum").push().setValue(nick);
        databaseReference.child("realchatclientnum").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot2, String s) {
                Nickdata aa = dataSnapshot2.getValue(Nickdata.class);  // chatData를 가져오고
                adapter1.add(aa.getUserName()); // adapter에 추가합니다.
                chatnum.setText(String.valueOf(adapter1.getCount()));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Nickdata aa = dataSnapshot.getValue(Nickdata.class);
                adapter1.remove(aa.getUserName());
                chatnum.setText(String.valueOf(adapter1.getCount()));
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chatData chatDatas = new chatData(userName, editText.getText().toString());  // 유저 이름과 메세지로 chatData 만들기
                databaseReference.child("message").push().setValue(chatDatas);  // 기본 database 하위 message라는 child에 chatData를 list로 만들기
                editText.setText("");
            }
        });

        final ArrayList<ItemData> oData = new ArrayList<>();

        databaseReference.child("message").addChildEventListener(new ChildEventListener() {  // message는 child의 이벤트를 수신합니다.
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                chatData chatDatas = dataSnapshot.getValue(chatData.class);  // chatData를 가져오고
                adapter.add(chatDatas.getUserName() + ": " + chatDatas.getMessage() + "  "); // adapter에 추가합니다.
                adapter.registerDataSetObserver(new DataSetObserver() {
                    @Override
                    public void onChanged() {
                        super.onChanged();
                        listView.setSelection(adapter.getCount()-1);
                    }
                });
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
        return view1;
    }
}