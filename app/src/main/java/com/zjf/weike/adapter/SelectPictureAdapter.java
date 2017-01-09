package com.zjf.weike.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;

import com.zjf.weike.R;
import com.zjf.weike.bean.ImageDetial;
import com.zjf.weike.impl.OnPictureSelectListener;
import com.zjf.weike.util.SC;
import com.zjf.weike.view.activity.SelectPictureActivity;
import com.zjf.weike.view.activity.ShowPictureActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :ZJF
 * @version : 2016-12-22 下午 4:59
 */

public class SelectPictureAdapter extends CRecyclerViewAdapter<String> {

    private OnPictureSelectListener mListener;
    private ArrayList<String> mAlreadhave;
    private float width;


    public void setListener(OnPictureSelectListener listener) {
        mListener = listener;
    }

    public ArrayList<String> getAlreadhave() {
        return mAlreadhave;
    }

    public void setAlreadhave(ArrayList<String> alreadhave) {
        mAlreadhave = alreadhave;
    }

    public SelectPictureAdapter(Context context, List<String> data, int... itemLayoutIds) {
        super(context, data, itemLayoutIds);
        width = context.getResources().getDisplayMetrics().widthPixels / 3.0f;
    }


    @Override
    protected void setConvertView(CRecyclerViewViewHolder holder, final String item, int position) {
        CheckBox view = holder.getView(R.id.item_check);
        if (mAlreadhave.contains(item)) {
            view.setChecked(true);
        } else {
            view.setChecked(false);
        }
        final View picture = holder.getView(R.id.item_photo);
        holder.setImageByUrl(R.id.item_photo, item, (int) width, (int) width)
                .setOnclickListener(R.id.item_photo, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int[] screenLocation = new int[2];
                        picture.getLocationOnScreen(screenLocation);
                        ImageDetial detial = new ImageDetial(screenLocation,
                                picture.getWidth(), picture.getHeight(), item);
                        Intent intent = new Intent(mContext, ShowPictureActivity.class);
                        intent.putExtra(SC.IMAGE_DETIAL, detial);
                        mContext.startActivity(intent);
                        ((Activity) mContext).overridePendingTransition(0, 0);
                    }
                })
                .setOnclickListener(R.id.item_check, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CheckBox box = (CheckBox) v;
                        boolean checked = box.isChecked();
                        //点击监听时，checked与点之前反转了一次
                        if (!checked) {//原来是选中
                            mAlreadhave.remove(item);
                            box.setChecked(false);
                        } else {//原来是未选中
                            if (mAlreadhave.size() < SC.PICTURESELECTED_COUNT) {//还可以选
                                mAlreadhave.add(item);
                                box.setChecked(true);
                            } else {
                                box.setChecked(false);
                                ((SelectPictureActivity) mContext).showSnakBar(mContext.getString(R.string.ninepicture), 1);
                            }
                        }
                        if (mListener != null) {
                            mListener.pictureSelect(mAlreadhave.size());
                        }
                    }
                });
    }
}
