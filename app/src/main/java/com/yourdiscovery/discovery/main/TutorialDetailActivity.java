package com.yourdiscovery.discovery.main;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.yourdiscovery.discovery.BaseActivity;
import com.yourdiscovery.discovery.R;

public class TutorialDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_detail);

        Bundle extras = getIntent().getExtras();
        int _nResID = extras.getInt("detResource");

        ImageView _ivMain = (ImageView)findViewById(R.id.tutorial_detail_iv_main);
        _ivMain.setImageResource(_nResID);
        final AppCompatActivity _activiyThis = this;
        _ivMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _activiyThis.finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tutorial_detail, menu);
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
}
