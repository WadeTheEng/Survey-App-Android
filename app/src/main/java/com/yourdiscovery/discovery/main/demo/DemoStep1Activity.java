package com.yourdiscovery.discovery.main.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.yourdiscovery.discovery.R;
import com.yourdiscovery.discovery.main.survey.NotifyReceiveActivity;
import com.yourdiscovery.discovery.main.survey.SurveyInfoActivity;

public class DemoStep1Activity extends NotifyReceiveActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_step1);
        Button _btnBegin = (Button)findViewById(R.id.demo_btn_start);
        _btnBegin.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_demo_step1, menu);
        return true;
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

    @Override
    public void onClick(View view) {
        Intent _intDiscovery = new Intent(this, SurveyInfoActivity.class);
        _intDiscovery.putExtra("isDemo",true);
        startActivity(_intDiscovery);
    }
}
