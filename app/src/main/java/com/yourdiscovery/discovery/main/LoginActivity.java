package com.yourdiscovery.discovery.main;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yourdiscovery.discovery.BaseActivity;
import com.yourdiscovery.discovery.GlobalConst;
import com.yourdiscovery.discovery.R;
import com.yourdiscovery.discovery.main.demo.EndDemoActivity;
import com.yourdiscovery.discovery.main.network.APIManager;
import com.yourdiscovery.discovery.main.network.ApiCallBack;
import com.yourdiscovery.discovery.main.network.ApiResponse;
import com.bigkoo.svprogresshud.SVProgressHUD;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private static final int MY_PERMISSIONS_REQUEST = 11;
    EditText txtUsername,txtPassword;
    SVProgressHUD hudLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button _btnLogin = (Button)findViewById(R.id.login_btn_login);
        _btnLogin.setOnClickListener(this);

        Button _btnFree = (Button)findViewById(R.id.login_btn_freetrial);
        _btnFree.setOnClickListener(this);

        txtUsername = (EditText)findViewById(R.id.login_txt_username);
        txtPassword = (EditText)findViewById(R.id.login_txt_password);

        hudLogin = new SVProgressHUD(this);
        checkPermissions();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

        if(view.getId() == R.id.login_btn_login){
            if (txtUsername.getText().length() == 0){
                Toast.makeText(this, getString(R.string.string_msg_correcteusername), Toast.LENGTH_SHORT).show();
                return;
            }
            if (txtPassword.getText().length() == 0){
                Toast.makeText(this, getString(R.string.string_msg_correctepassword), Toast.LENGTH_SHORT).show();
                return;
            }
            final Context aContext = this;
            hudLogin.show();
            APIManager.reqLogin(txtUsername.getText().toString(), txtPassword.getText().toString(), new ApiCallBack() {
                @Override
                public void onSuccess(ApiResponse response) {
                    hudLogin.dismiss();
                    if (response.bSuccess == 1) {
                        GlobalConst.setUserID(aContext,response.nId);
                        GlobalConst.setUserKey(aContext,response.strUserKey);
                        Intent _intentForMainMenu = new Intent(aContext, MainMenuActivity.class);
                        startActivity(_intentForMainMenu);
                    }
                    else{
                        Toast.makeText(aContext, response.strError, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure() {
                    hudLogin.dismiss();
                    Toast.makeText(aContext, getString(R.string.string_msg_network_cantconnect), Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            //Intent _intEndDemo = new Intent(this, EndDemoActivity.class);
            //startActivity(_intEndDemo);
            GlobalConst.openWebsite(this, GlobalConst.URL_Free);
        }

    }

    public void checkPermissions() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {

            // No explanation needed, we can request the permission.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET},
                    MY_PERMISSIONS_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    Toast.makeText(this, R.string.msg_not_allowed_permission, Toast.LENGTH_SHORT).show();
                    //finish();
                }

            }
        }
    }
}
