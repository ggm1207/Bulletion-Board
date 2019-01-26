package com.cs.bulletinboard;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {
    LayoutInflater inflater = null;
    private ArrayList<ItemData> m_oData = null;
    private int nListCnt = 0;

    public ListAdapter(ArrayList<ItemData> _oData)
    {
        m_oData = _oData;
        nListCnt = m_oData.size();
    }

    @Override
    public int getCount()
    {
        Log.i("TAG", "getCount");
        return nListCnt;
    }

    @Override
    public Object getItem(int position)
    {
        return null;
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            final Context context = parent.getContext();
            if (inflater == null)
            {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            convertView = inflater.inflate(R.layout.item_custom, parent, false);
        }

        TextView oTextTitle = (TextView) convertView.findViewById(R.id.textTitle);
        TextView oTextDate = (TextView) convertView.findViewById(R.id.textDate);
        TextView oTextcnum = (TextView) convertView.findViewById(R.id.textnum);
        TextView oTextdata = (TextView) convertView.findViewById(R.id.textdaaa);

        oTextTitle.setText(m_oData.get(position).bTitle);
        oTextDate.setText(m_oData.get(position).bDate);
        oTextcnum.setText(m_oData.get(position).bCnum);
        oTextdata.setText(m_oData.get(position).bdata);

        convertView.setTag(""+position);
        return convertView;
    }
}