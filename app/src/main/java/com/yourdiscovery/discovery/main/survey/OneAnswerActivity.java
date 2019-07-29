package com.yourdiscovery.discovery.main.survey;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yourdiscovery.discovery.R;

public class OneAnswerActivity extends NotifyReceiveActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();

    }

    public void initUI(){
        final Survey.Question _question = Survey.objCurSurvey.getCurQuestion();
        if(_question.type == Survey.QuestionType.QT_General)
        {
            setContentView(R.layout.activity_one_answer_general);
            TextView _txtSpecQuestion = (TextView)findViewById(R.id.one_answer_general_txt_question2);
            _txtSpecQuestion.setText(_question.questionDesc);
        }
        else{
            setContentView(R.layout.activity_one_answer_special);
        }

        Button _btnAns1 = (Button)findViewById(R.id.one_answer_btn_answer1);
        _btnAns1.setText(_question.arrAnswerDesc.get(0).answerDesc);

        _btnAns1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                OneAnswerActivity _activity = (OneAnswerActivity)view.getContext();
                _activity.onTapAnswer1();
            }
        });

        Button _btnAns2 = (Button)findViewById(R.id.one_answer_btn_answer2);
        _btnAns2.setText(_question.arrAnswerDesc.get(1).answerDesc);
        _btnAns2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OneAnswerActivity _activity = (OneAnswerActivity) view.getContext();
                _activity.onTapAnswer2();
            }
        });

        boolean _bAns1 = false,_bAns2 = false;
        Typeface _normalFace = Typeface.createFromAsset(getAssets(), "fonts/georgia.ttf");
        Typeface _boldFace = Typeface.createFromAsset(getAssets(), "fonts/georgia_bold.ttf");

        _btnAns1.setTypeface(_normalFace);
        _btnAns2.setTypeface(_normalFace);

        if(_question.arrAnswerDesc.get(0).bSelected){
            _bAns1 = true;
            //_btnAns1.setTextColor(red: 0.43, green: 0.84, blue: 1.0, alpha: 1.0);
            _btnAns1.setTypeface(_boldFace);
        }

        if (_question.arrAnswerDesc.get(1).bSelected){
            _btnAns2.setTypeface(_boldFace);
            _bAns2 = true;
            //self.lbAnswer2.textColor = UIColor(red: 0.43, green: 0.84, blue: 1.0, alpha: 1.0)
            //self.lbAnswer2.font = fontBold(ftSize: _ftSize+2)
        }

        _btnAns1.setSelected(_bAns1);
        _btnAns2.setSelected(_bAns2);
    }

    void onTapAnswer1() {
        Survey.objCurSurvey.setCurAnswer(0);
        initUI();
        gotoNextQuestion();
    }

    void onTapAnswer2() {
        Survey.objCurSurvey.setCurAnswer(1);
        initUI();
        gotoNextQuestion();
    }

    void gotoNextQuestion(){
        if (Survey.objCurSurvey.gotoNextQuestion()){
            Intent _intOneAnswer = new Intent(this, OneAnswerActivity.class);
            startActivity(_intOneAnswer);        }
        else{
            //feedback
            Intent _intFeedback = new Intent(this, FeedbackActivity.class);
            startActivity(_intFeedback);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_one_answer, menu);
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
