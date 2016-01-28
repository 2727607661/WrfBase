package base.wrf.com.wrfbase;

import android.os.Bundle;

import com.wrf.base.BaseActivity;


public class MainActivity extends BaseActivity {
    public static boolean isForeground = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        startActivity(new Intent(this,SimpleFragmentTableActivity.class));
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
}
