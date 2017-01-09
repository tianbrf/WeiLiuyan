package com.zjf.weike.adapter;

import android.content.Context;

import com.zjf.weike.R;
import com.zjf.weike.bean.ImageFolder;

import java.util.List;

/**
 * @author :ZJF
 * @version : 2016-12-22 下午 5:06
 */

public class SelectAblumAdapter extends CRecyclerViewAdapter<ImageFolder> {

    public SelectAblumAdapter(Context context, List<ImageFolder> data, int... itemLayoutIds) {
        super(context, data, itemLayoutIds);
    }

    @Override
    protected void setConvertView(CRecyclerViewViewHolder holder, ImageFolder item, int position) {
        holder.setImageByUrl(R.id.ablum_cover, item.getFirstImagePath());
        holder.setText(R.id.ablum_name, item.getName());
        holder.setText(R.id.ablum_count, item.getCount() + "");
    }
}
