package com.zjf.weike.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.zjf.weike.App;
import com.zjf.weike.R;
import com.zjf.weike.adapter.CRecyclerViewAdapter;
import com.zjf.weike.adapter.CRecyclerViewViewHolder;
import com.zjf.weike.adapter.SelectLocationAdapter;
import com.zjf.weike.view.activity.SelectLocationActivity;
import com.zjf.weike.view.fragment.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class POIFragment extends BaseFragment {

    private PoiSearch.Query mQuery;
    private PoiSearch mSearch;
    private String cityCode;
    private String poiType;
    private int currentPage = 0;
    private double latitude, longitude;
    private String title;

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public void setPoiType(String poiType) {
        this.poiType = poiType;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public POIFragment() {
        // Required empty public constructor
    }

    private RecyclerView mRecyclerView;
    private TextView mEmptyView;
    private SelectLocationAdapter mAdapter;
    private List<PoiItem> mPoiItems;

    @Override
    public void initVariables() {
        mQuery = new PoiSearch.Query("", poiType, cityCode);
        mQuery.setPageSize(8);// 设置每页最多返回多少条poiitem
        mQuery.setPageNum(currentPage);//设置查询页码
        mSearch = new PoiSearch(getContext(), mQuery);
        mSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(latitude,
                longitude), 800));//设置周边搜索的中心点以及半径
        mPoiItems = new ArrayList<>();
        mAdapter = new SelectLocationAdapter(getContext(), mPoiItems, R.layout.loaction_item);
        if (TextUtils.isEmpty(title)) {
            mAdapter.setTitle(title);
        }
    }


    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_poi, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mEmptyView = (TextView) view.findViewById(R.id.emptyView);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        mAdapter.setEmptyView(mEmptyView);
        mRecyclerView.setAdapter(mAdapter);

        return view;

    }

    @Override
    public void initListener() {
        mSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
            @Override
            public void onPoiSearched(PoiResult result, int rCode) {
                if (rCode == 1000) {
                    if (result != null && result.getQuery() != null) {// 搜索poi的结果
                        if (result.getQuery().equals(mQuery)) {// 是否是同一条
                            // 取得搜索到的poiitems有多少页
                            List<PoiItem> poiItems = result.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                            mAdapter.addNewData(poiItems);
                        }
                    } else {
                        ((SelectLocationActivity) getActivity()).
                                showSnakBar(App.getStringRes(R.string.netfailure), 1);
                    }
                } else {
                    ((SelectLocationActivity) getActivity())
                            .showSnakBar(App.getStringRes(R.string.netfailure), 1);
                }
            }

            @Override
            public void onPoiItemSearched(PoiItem item, int i) {

            }
        });

        mAdapter.setOnItemClickListener(new CRecyclerViewAdapter.OnItemClickListener<PoiItem>() {
            @Override
            public void onItemClick(View view, CRecyclerViewViewHolder holder, PoiItem item) {
                ((SelectLocationActivity) getActivity()).setSubTitle(item.getTitle());
            }
        });

    }

    @Override
    public void loaderData() {
        mSearch.searchPOIAsyn();
    }

    public void notifyCheck(String chooseName) {
        this.title = chooseName;
        if (mAdapter != null) {
            mAdapter.setTitle(chooseName);
            mAdapter.notifyDataSetChanged();
        }
    }

}
