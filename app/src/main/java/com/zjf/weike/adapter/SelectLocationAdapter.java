package com.zjf.weike.adapter;

import android.content.Context;

import com.amap.api.services.core.PoiItem;
import com.zjf.weike.R;

import java.util.List;

/**
 * @author :ZJF
 * @version : 2016-12-27 下午 5:25
 */

public class SelectLocationAdapter extends CRecyclerViewAdapter<PoiItem> {

    private String title;

    public void setTitle(String title) {
        this.title = title;
    }

    public SelectLocationAdapter(Context context, List<PoiItem> data, int... itemLayoutIds) {
        super(context, data, itemLayoutIds);
    }

    @Override
    protected void setConvertView(CRecyclerViewViewHolder holder, PoiItem item, int position) {
        holder.setText(R.id.item_name, item.getTitle());
        holder.setImageResource(R.id.item_check,
                R.drawable.ic_check_circle_white_24dp);
        if (title != null) {
            if (title.equals(item.getTitle())) {
                holder.setImageResource(R.id.item_check,
                        R.drawable.ic_check_circle_yellow_400_24dp);
            }
        }
    }
}
