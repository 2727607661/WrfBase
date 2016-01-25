package com.wrf.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * an Enhanced abstract BaseAdapter .
 *
 * 
 */
public abstract class AbstractBaseAdapter<T,V> extends BaseAdapter {


    private LayoutInflater mInflater;
    private List<T> mDatas;
    protected Context mContext;


    public AbstractBaseAdapter(Context mContext) {
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : (mDatas.isEmpty() ? 0 : mDatas.size());
    }

    @Override
    public T getItem(int i) {
        return mDatas == null ? null : mDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public void setData(List<T> data) {
        mDatas = data;
        notifyDataSetChanged();
    }

    public void appendData(List<T> appendedData) {
        if (mDatas != null)
            mDatas.addAll(appendedData);
        notifyDataSetChanged();
    }

    public void clearData() {
        mDatas.clear();
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

         V viewHolder = null;
        if (view == null) {


            viewHolder = getViewHolder();
            view = mInflater.inflate(getItemLayoutId(), null);
            onCreateItemView(position, viewHolder, view);

            view.setTag(viewHolder);
        } else {
            viewHolder = (V) view.getTag();
        }

        onCreateItemData(view, viewHolder, position);

        return view;
    }


    /**
     * Retrieve the current item layou id.
     *
     * @return
     */
    protected abstract int getItemLayoutId();


    /**
     * Retrieve an instance of ViewHolder which was inherited from baseViewHolder
     *
     * @return
     */
    protected abstract V getViewHolder();

    /**
     * Called when the item view was first created.
     *
     * @param position
     * @param viewHolder
     * @param view
     */
    protected abstract void onCreateItemView(int position, V viewHolder, View view);

    /**
     * Called when the item view was filled with data.
     *
     * @param view
     * @param viewHolder
     * @param position
     */
    protected abstract void onCreateItemData(View view, V viewHolder, int position);
    
    
}