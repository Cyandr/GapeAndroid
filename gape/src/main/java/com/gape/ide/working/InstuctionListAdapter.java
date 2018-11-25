package com.gape.ide.working;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.gape.cyandr.gapeandroid.gape.R;

import java.util.List;

/**
 * Created by cyandr on 2016/9/11 0011.
 */
public class InstuctionListAdapter extends BaseAdapter {
    private List<AtomInstruct> LINS;
    private Context mContext;

    InstuctionListAdapter(Context context, List<AtomInstruct> L) {
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
            LayoutInflater mInflator = LayoutInflater.from(mContext);
            view = mInflator.inflate(R.layout.opration_item, null);
            convertView.textView = view.findViewById(R.id.opration_name);

            convertView.img = view.findViewById(R.id.opration_img);
            view.setTag(convertView);
        } else {
            convertView = (ViewHolder) view.getTag();
        }
        convertView.textView.setText(LINS.get(i).getIns_name());
        // convertView.img.setImageDrawable(mContext.getDrawable(R.drawable.ic_rect1));
        return view;
    }

    private class ViewHolder {
        TextView textView;
        ImageView img;
    }

}
