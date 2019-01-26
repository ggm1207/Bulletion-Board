package com.cs.bulletinboard;



import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class List_activity extends Fragment {
    private Fragment fragment;
    private FragmentManager fragmentManager;
    private ListView m_oListView = null;
    MainActivity activity;

    public static List_activity newInstance(){
        return new List_activity();
    }


    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.lists, container,false);

        Button refresh = (Button)view.findViewById(R.id.refresh);
        Button write = (Button)view.findViewById(R.id.write);
        final ArrayList<ItemData> oData = new ArrayList<>();

        String result = "실패";
        try {
            result = new JSONTASK().execute("getboard_for_list", "trash").get(); // 정보를 받아오기만 함!
            JSONArray obj = new JSONArray(result);
            String[] date;
            String fine = null;

            for (int i=0; i<obj.length(); i++) {
                JSONObject board = obj.getJSONObject(i);
                ItemData oItem = new ItemData();
                oItem.bNum = board.getString("bnum");   // 화면에 출력되지는 않지만, 글 읽고, 글 수정할때 사용할 용도
                oItem.bTitle = board.getString("btitle");
                oItem.bDate = board.getString("btext");
                oItem.bCnum = String.valueOf(Integer.parseInt(board.getString("cc"))-1);
                date = board.getString("bdate").split("T");
                fine = date[0] + " " + date[1].split(".0")[0];
                oItem.bdata = fine;
                oData.add(oItem);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        // ListView, Adapter 생성 및 연결 ------------------------
        m_oListView = (ListView)view.findViewById(R.id.listView);
        final ListAdapter oAdapter = new ListAdapter(oData);
        m_oListView.setAdapter(oAdapter);
        m_oListView.setClickable(true);
        m_oListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                MainActivity activity = (MainActivity)getActivity();
                activity.replaceFragment(new read().newInstance(oData.get(position).bNum));
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ArrayList<ItemData> odData = new ArrayList<>();
                    String result;
                    result = new JSONTASK().execute("getboard_for_list", "trash").get(); // 정보를 받아오기만 함!
                    JSONArray obj = new JSONArray(result);
                    String[] date;
                    String fine = null;
                    for (int i=0; i<obj.length(); i++) {
                        JSONObject board = obj.getJSONObject(i);
                        ItemData oItem = new ItemData();
                        oItem.bNum = board.getString("bnum");   // 화면에 출력되지는 않지만, 글 읽고, 글 수정할때 사용할 용도
                        oItem.bTitle = board.getString("btitle");
                        oItem.bDate = board.getString("btext");
                        oItem.bCnum = String.valueOf(Integer.parseInt(board.getString("cc"))-1);
                        date = board.getString("bdate").split("T");
                        fine = date[0] + " " + date[1].split(".0")[0];
                        oItem.bdata = fine;
                        odData.add(oItem);
                    }
                    ListAdapter ooAdapter = new ListAdapter(odData);
                    m_oListView.setAdapter(ooAdapter);
                    Toast.makeText(getActivity(),"새로 고침 되었습니다.",Toast.LENGTH_SHORT).show();
                } catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

        write.setOnClickListener(new View.OnClickListener() { // 글 작성 버튼
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity)getActivity();
                activity.replaceFragment(new writing_activity());
            }
        });
        return view;
    }
}
