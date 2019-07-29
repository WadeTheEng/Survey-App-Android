package com.yourdiscovery.discovery.main.survey;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yourdiscovery.discovery.GlobalConst;
import com.yourdiscovery.discovery.R;
import com.yourdiscovery.discovery.main.MainMenuActivity;
import com.yourdiscovery.discovery.main.demo.EndDemoActivity;
import com.yourdiscovery.discovery.main.network.APIManager;
import com.yourdiscovery.discovery.main.network.ApiCallBack;
import com.yourdiscovery.discovery.main.network.ApiResponse;

public class SurveyResultActivity extends NotifyReceiveActivity {

    int nResultNo;
    @Override
    protected void onStart() {
        super.onStart();
        if(Survey.objCurSurvey.bIsDemo)
            return;
        final Context aContext = this;
        APIManager.reqRegisterResult(String.valueOf(GlobalConst.getUserID(this)),GlobalConst.getUserKey(this), Survey.objCurSurvey.getFullName(),
                Survey.objCurSurvey.email, String.valueOf(nResultNo), Survey.objCurSurvey.feedBack, new ApiCallBack() {
                    @Override
                    public void onSuccess(ApiResponse response) {
                        if (response.bSuccess == 1) {
                            Toast.makeText(aContext, "Success", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(aContext, response.strError, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure() {
                        Toast.makeText(aContext, getString(R.string.string_msg_network_cantconnect), Toast.LENGTH_SHORT).show();
                    }
                }
        );

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_result);

        ImageView _ivMain = (ImageView)findViewById(R.id.survey_result_iv_main);
        TextView _txtMain = (TextView)findViewById(R.id.survey_result_txt_desc);

        nResultNo = Survey.objCurSurvey.getResultNo();
        ResultInfo objResultInfo = Survey.objCurSurvey.getResultString(nResultNo);
        _txtMain.setText(objResultInfo.strDesc);
        _ivMain.setImageResource(GlobalConst.getResultImage(this, objResultInfo.strType));
        //_ivMain.setImageResource(R.drawable.result_bear);
        final SurveyResultActivity _thisActivity = this;
        _ivMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Survey.objCurSurvey.bIsDemo){
                    _thisActivity.sendFinishMessage();
                    Intent _intEndDemo = new Intent(_thisActivity, EndDemoActivity.class);
                    startActivity(_intEndDemo);
                }
                else{
                    if (GlobalConst.getPasscode(_thisActivity) != null) {
                        ResultPasscodeDialog _dailog = new ResultPasscodeDialog(_thisActivity);
                        _dailog.show();
                    } else {
                        _thisActivity.sendFinishMessage();
                    }
                }
            }
        });

    }

    public void sendFinishMessage(){
        Intent intent = new Intent(GlobalConst.FinishMessageFilter);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_survey_result, menu);
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

    //disable back
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }



}
