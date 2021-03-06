package com.wrf.simple;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.wrf.base.AbstractBaseAdapter;
import com.wrf.utils.ImageLoaderUtil;

import base.wrf.com.wrfbase.R;

/**
 * Created by wrf on 2016/1/26.
 */

public class SimpleListAdapter extends AbstractBaseAdapter<String, SimpleListAdapter.ListViewHolder> {
    public class ListViewHolder {
        public ImageView img;
    }

    public SimpleListAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.simple_listview_adapter;
    }

    @Override
    protected ListViewHolder getViewHolder() {
        return new ListViewHolder();
    }

    @Override
    protected void onCreateItemView(int position, ListViewHolder viewHolder, View view) {
                viewHolder.img= (ImageView) view.findViewById(R.id.iv);
    }

    @Override
    protected void onCreateItemData(View view, ListViewHolder viewHolder, int position) {
        ImageLoaderUtil.setImage(viewHolder.img,getItem(position));
    }




}
