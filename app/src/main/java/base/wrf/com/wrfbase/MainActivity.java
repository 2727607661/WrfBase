package base.wrf.com.wrfbase;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.wrf.simple.SimpleFragmentTableActivity;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        startActivity(new Intent(this,SimpleFragmentTableActivity.class));
    }
}
