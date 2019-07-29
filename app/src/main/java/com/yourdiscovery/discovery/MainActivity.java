package com.yourdiscovery.discovery;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.yourdiscovery.discovery.main.LoginActivity;
import com.yourdiscovery.discovery.main.MainMenuActivity;
import com.yourdiscovery.discovery.main.demo.DemoStep1Activity;
import com.yourdiscovery.discovery.main.demo.EndDemoActivity;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GlobalConst.initGlobal(this);
        if(GlobalConst.getUserID(this) != -1){
            //goto MainMenu activity directly
            Intent _intentForMainMenu = new Intent(this, MainMenuActivity.class);
            startActivity(_intentForMainMenu);
        }
        initButtonEvents();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    private void initButtonEvents(){
        //Login Button
        Button _btnLogin = (Button)findViewById(R.id.main_btn_login);
        _btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent _intentLogin = new Intent(view.getContext(), LoginActivity.class);
                startActivity(_intentLogin);
            }
        });

        //Demo Button
        final Context _context = this;
        Button _btnDemo = (Button)findViewById(R.id.main_btn_demo);
        _btnDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent _intDemo1 = new Intent(_context, DemoStep1Activity.class);
                startActivity(_intDemo1);
            }
        });
    }

}
