package com.wrf.simple;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.wrf.utils.LogUtils;
import com.wrf.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

import base.wrf.com.wrfbase.R;


/***
 * 简单的下拉刷新
 *
 */
public class SimplePulltorefreshListViewActivity extends Activity {

    private PullToRefreshListView listview;
    private List<String> list;
    private SimpleListAdapter adapte ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_pulltorefresh_list_view);

        listview = (PullToRefreshListView) findViewById(R.id.listview);
        listview.setMode(PullToRefreshBase.Mode.BOTH);

        adapte = new SimpleListAdapter(this);
        list = new ArrayList<String>();
        list.add("http://img.article.pchome.net/00/35/62/34/pic_lib/wm/Zhiwu37.jpg");
        list.add("http://www.33.la/uploads/201405sy/xiaoxi/32.jpg");
        list.add("http://pic38.nipic.com/20140215/12359647_224250481137_2.jpg");
        list.add("http://b.zol-img.com.cn/desk/bizhi/image/1/960x600/1349947347213.jpeg");
        list.add("http://pic38.nipic.com/20140215/12359647_224249690129_2.jpg");
        adapte.setData(list);
        listview.setAdapter(adapte);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UiUtils.gotoImagesBrowse(SimplePulltorefreshListViewActivity.this, list, position);
            }
        });


        listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                LogUtils.log("下拉");
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                LogUtils.log("上拉");
            }
        });
    }
}
