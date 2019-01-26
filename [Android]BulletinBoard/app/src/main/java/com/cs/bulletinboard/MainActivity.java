package com.cs.bulletinboard;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends FragmentActivity {

    private BottomNavigationView bottomNavigationView;
    private Fragment lists = List_activity.newInstance();
    private Fragment options = option_activity.newInstance();
    private Fragment randoms = random_activity.newInstance();
    private Fragment chats = realchat_activity.newInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.content, List_activity.newInstance()).commit();

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView_main_menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        // 어떤 메뉴 아이템이 터치되었는지 확인합니다.
                        switch (item.getItemId()) {
                            case R.id.menuitem_bottombar_list:
                                replaceFragment(lists);
                                deletenickname();
                                return true;

                            case R.id.menuitem_bottombar_chat:
                                replaceFragment(chats);
                                return true;

                            case R.id.menuitem_bottombar_friend:
                                replaceFragment(randoms);
                                deletenickname();
                                return true;

                            case R.id.menuitem_bottombar_option:
                                replaceFragment(options);
                                deletenickname();
                                return true;
                        }
                        return false;
                    }
                });
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, fragment).commit();
    }

    public void onBackPressed()
    {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content);
        if (fragment instanceof writing_activity)
        { replaceFragment(new List_activity()); }
        else if (fragment instanceof read){ // 오류 아직 read 가 fragment가 아니라서
            replaceFragment(new List_activity());
        } else{
            super.onBackPressed();
        }
    }
    public void deletenickname(){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        Query aaa = databaseReference.child("realchatclientnum").orderByChild("userName").equalTo(NICKNAME.getInstance().getData());
        aaa.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    dataSnapshot1.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



}