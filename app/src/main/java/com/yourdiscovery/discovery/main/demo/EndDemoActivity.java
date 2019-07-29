package com.yourdiscovery.discovery.main.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.yourdiscovery.discovery.BaseActivity;
import com.yourdiscovery.discovery.GlobalConst;
import com.yourdiscovery.discovery.R;
import com.yourdiscovery.discovery.main.survey.NotifyReceiveActivity;
import com.yourdiscovery.discovery.main.survey.SurveyInfoActivity;

public class EndDemoActivity extends BaseActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_demo);
        Button _btnHome = (Button)findViewById(R.id.demo_btn_home);
        _btnHome.setOnClickListener(this);

        Button _btnLearn = (Button)findViewById(R.id.demo_btn_learn);
        _btnLearn.setOnClickListener(this);

        Button _btnUpgrade = (Button)findViewById(R.id.demo_btn_upgrade);
        _btnUpgrade.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_end_demo, menu);
        return true;
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.demo_btn_home){
            finish();
        }

        if(view.getId()==R.id.demo_btn_learn){
            GlobalConst.openWebsite(this, GlobalConst.URL_Learnmore);
        }

        if(view.getId()==R.id.demo_btn_upgrade){
            GlobalConst.openWebsite(this, GlobalConst.URL_Upgrade);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
