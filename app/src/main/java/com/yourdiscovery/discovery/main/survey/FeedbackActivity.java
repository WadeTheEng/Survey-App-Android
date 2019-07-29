package com.yourdiscovery.discovery.main.survey;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.yourdiscovery.discovery.GlobalConst;
import com.yourdiscovery.discovery.R;
import com.yourdiscovery.discovery.main.MainMenuActivity;

import java.sql.BatchUpdateException;

public class FeedbackActivity extends NotifyReceiveActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        Button _btnFeedback = (Button)findViewById(R.id.feedback_btn_rlike);
        _btnFeedback.setOnClickListener(this);
        _btnFeedback = (Button)findViewById(R.id.feedback_btn_likeit);
        _btnFeedback.setOnClickListener(this);
        _btnFeedback = (Button)findViewById(R.id.feedback_btn_neutral);
        _btnFeedback.setOnClickListener(this);
        _btnFeedback = (Button)findViewById(R.id.feedback_btn_didntlike);
        _btnFeedback.setOnClickListener(this);
        _btnFeedback = (Button)findViewById(R.id.feedback_btn_rdidntlike);
        _btnFeedback.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_feedback, menu);
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

    public void onClick(View view) {
        Button _btnCur = (Button)view;
        //Log.d("kch", (String) _btnCur.getText());
        Survey.objCurSurvey.feedBack = (String)_btnCur.getText();
        Intent _intFeedback = new Intent(this, SurveyResultActivity.class);
        startActivity(_intFeedback);
    }
}
