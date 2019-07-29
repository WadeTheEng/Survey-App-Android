package com.yourdiscovery.discovery.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.yourdiscovery.discovery.BaseActivity;
import com.yourdiscovery.discovery.GlobalConst;
import com.yourdiscovery.discovery.R;
import com.yourdiscovery.discovery.main.network.APIManager;
import com.yourdiscovery.discovery.main.network.ApiCallBack;
import com.yourdiscovery.discovery.main.network.ApiResponse;

/**
 * Created by user1 on 2/23/2017.
 */
public class SetupSupportActivity extends BaseActivity implements View.OnClickListener  {

    public String strCurPasscode="";
    public EditText txtDigit1,txtDigit2,txtDigit3,txtDigit4;
    public EditText txtPasscode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_support);

        Button _btnTmp = (Button)findViewById(R.id.setupsupport_btn_contactsupport);
        _btnTmp.setOnClickListener(this);
        _btnTmp = (Button)findViewById(R.id.setupsupport_btn_setpasscode);
        _btnTmp.setOnClickListener(this);

        txtDigit1 = (EditText)findViewById(R.id.setupsupport_passcode_txt_1);
        txtDigit2 = (EditText)findViewById(R.id.setupsupport_passcode_txt_2);
        txtDigit3 = (EditText)findViewById(R.id.setupsupport_passcode_txt_3);
        txtDigit4 = (EditText)findViewById(R.id.setupsupport_passcode_txt_4);

        txtPasscode = (EditText)findViewById(R.id.setupsupport_txt_passcode);
        if(txtPasscode.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
        final SetupSupportActivity _this = this;
        txtPasscode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                strCurPasscode = charSequence.toString();
                if(strCurPasscode.length() == 5) {
                    strCurPasscode = strCurPasscode.substring(3, 4);
                    txtPasscode.setText(strCurPasscode);
                }

                if (strCurPasscode.length() == 1) {
                    _this.txtDigit1.setText("1");
                    _this.txtDigit2.setText("");
                    _this.txtDigit3.setText("");
                    _this.txtDigit4.setText("");
                }
                if (strCurPasscode.length() == 2) {
                    _this.txtDigit2.setText("2");
                }
                if (strCurPasscode.length() == 3) {
                    _this.txtDigit3.setText("3");
                }
                if (strCurPasscode.length() == 4) {
                    _this.txtDigit4.setText("4");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
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

        if(view.getId() == R.id.setupsupport_btn_setpasscode){
            if(strCurPasscode.length() == 4){
                GlobalConst.setPasscode(this,strCurPasscode);
                Toast.makeText(this, getString(R.string.string_msg_settedpasscode), Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, getString(R.string.string_msg_inputpasscode), Toast.LENGTH_SHORT).show();
            }
        }
        else{
            GlobalConst.sendMailToContact(this);
        }

    }
}
