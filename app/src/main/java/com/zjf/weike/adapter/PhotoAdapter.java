package com.zjf.weike.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zjf.weike.R;
import com.zjf.weike.bean.ImageDetial;
import com.zjf.weike.util.SC;
import com.zjf.weike.view.activity.ShowPictureActivity;

import java.util.List;

/**
 * @author :ZJF
 * @version : 2016-12-21 下午 8:04
 */

public class PhotoAdapter extends CRecyclerViewAdapter<String> {

    private float width;

    public PhotoAdapter(Context context, List<String> data, int... itemLayoutIds) {
        super(context, data, itemLayoutIds);
        width = context.getResources().getDisplayMetrics().widthPixels / 3.0f;
    }

    @Override
    protected void setConvertView(final CRecyclerViewViewHolder holder, final String item, int position) {
        Glide.with(mContext)
                .load(item)
                .override((int) width, (int) width)
                .into(((ImageView) holder.getView(R.id.item_photo)));
        holder.setOnclickListener(R.id.item_delete, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItemData(holder.getAdapterPosition());
            }
        }).setOnclickListener(R.id.item_photo, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] screenLocation = new int[2];
                v.getLocationOnScreen(screenLocation);
                ImageDetial detial = new ImageDetial(screenLocation,
                        v.getWidth(), v.getHeight(), item);
                Intent intent = new Intent(mContext, ShowPictureActivity.class);
                intent.putExtra(SC.IMAGE_DETIAL, detial);
                mContext.startActivity(intent);
                ((Activity) mContext).overridePendingTransition(0, 0);
            }
        });
    }
}
