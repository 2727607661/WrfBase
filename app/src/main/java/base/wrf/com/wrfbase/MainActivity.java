package base.wrf.com.wrfbase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.umeng.update.UmengUpdateAgent;
import com.wrf.base.BaseActivity;
import com.wrf.drawable.DrawableListActivity;
import com.wrf.simple.HeadLoadingActivity;


public class MainActivity extends BaseActivity implements View.OnClickListener{
    public static boolean isForeground = false;
    private Button btn_1,btn_2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UmengUpdateAgent.setUpdateOnlyWifi(false);
        UmengUpdateAgent.update(this);

        initView();
//        startActivity(new Intent(this, SimpleFragmentTableActivity.class));
    }

    private void initView(){
        btn_1 = $(R.id.btn_1);
        btn_2= $(R.id.btn_2);
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
    }


    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
    }


    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.btn_1:
                startActivity(new Intent(this, DrawableListActivity.class));
                break;
            case R.id.btn_2:
                startActivity(new Intent(this, HeadLoadingActivity.class));
                break;
        }
    }
}
