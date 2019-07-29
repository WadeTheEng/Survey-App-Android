package com.yourdiscovery.discovery.main.survey;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yourdiscovery.discovery.GlobalConst;
import com.yourdiscovery.discovery.R;

public class SurveyInfoActivity extends NotifyReceiveActivity {

    EditText txtFirstName,txtLastName,txtEmail;
    public boolean bIsDemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_info);

        Bundle extras = getIntent().getExtras();
        bIsDemo = extras.getBoolean("isDemo");

        Button _btnStart = (Button)findViewById(R.id.surveyinfo_btn_begin);
        _btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _strEmail = txtEmail.getText().toString();
                String _strFirstName = txtFirstName.getText().toString();
                String _strLastName = txtLastName.getText().toString();

                //_strEmail= "kch@hotmail.com";
                //_strFirstName = "Kim";
                //_strLastName = "Chan";

                if(!GlobalConst.validateEmailAddress(_strEmail)){
                    Toast.makeText(SurveyInfoActivity.this, getString(R.string.string_msg_correctemail), Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!GlobalConst.validateFirstName(_strFirstName) || !GlobalConst.validateLastName(_strLastName)){
                    Toast.makeText(SurveyInfoActivity.this, getString(R.string.string_msg_correctename), Toast.LENGTH_SHORT).show();
                    return;
                }

                Survey _survey = new Survey(view.getContext(),_strFirstName,_strLastName,_strEmail);
                _survey.bIsDemo = bIsDemo;

                Intent _intOneAnswer = new Intent(view.getContext(), OneAnswerActivity.class);
                Survey.objCurSurvey = _survey;
                startActivity(_intOneAnswer);
            }
        });

        txtFirstName = (EditText)findViewById(R.id.surveyinfo_txt_firstname);
        txtLastName = (EditText)findViewById(R.id.surveyinfo_txt_lastname);
        txtEmail = (EditText)findViewById(R.id.surveyinfo_txt_email);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_survey_info, menu);
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
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
