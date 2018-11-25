package com.gape.ide.working;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.gape.cyandr.gapeandroid.gape.R;

import java.util.List;

/**
 * Created by cyandr on 2016/9/11 0011.
 */
public class InAdapterManageDb extends BaseAdapter {
    List<AtomInstruct> LINS;
    LayoutInflater mInflator;
    Context mContext;

    InAdapterManageDb(Context context, List<AtomInstruct> L) {
        LINS = L;
        mContext = context;
    }

    @Override
    public int getCount() {
        return LINS.size();
    }

    @Override
    public Object getItem(int i) {
        return LINS.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder convertView = new ViewHolder();
        if (view == null) {
            mInflator = LayoutInflater.from(mContext);
            view = mInflator.inflate(R.layout.ins_list, null);
            convertView.type = view.findViewById(R.id.insList_item_type);
            convertView.name = view.findViewById(R.id.insList_item_name);
            convertView.value = view.findViewById(R.id.insList_item_value);
            convertView.info = view.findViewById(R.id.insList_item_info);
            view.setTag(convertView);
        } else {
            convertView = (ViewHolder) view.getTag();
        }
        int id = LINS.get(i).getIns_ID();
        convertView.type.setText(LINS.get(i).getIns_type());
        convertView.name.setText(LINS.get(i).getIns_name());
        convertView.value.setText(LINS.get(i).getIns_Value());
        convertView.info.setText(LINS.get(i).getIns_Info());
        return view;
    }

    private class ViewHolder {
        TextView type;
        TextView name;
        TextView value;
        TextView info;
    }

}
