package com.yourdiscovery.discovery.main.survey;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.yourdiscovery.discovery.GlobalConst;
import com.yourdiscovery.discovery.R;

/**
 * Created by user1 on 2/22/2017.
 */
public class ResultPasscodeDialog extends Dialog {
    public SurveyResultActivity parentAcitivy;
    public String strCurPasscode="";
    public EditText txtDigit1,txtDigit2,txtDigit3,txtDigit4;
    public EditText txtPasscode;
    public ResultPasscodeDialog(SurveyResultActivity parent) {
        super(parent);
        // TODO Auto-generated constructor stub
        this.parentAcitivy = parent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dailog_survey_passcode);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);//full screen
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));//transparent
        txtDigit1 = (EditText)findViewById(R.id.dialog_passcode_txt_1);
        txtDigit2 = (EditText)findViewById(R.id.dialog_passcode_txt_2);
        txtDigit3 = (EditText)findViewById(R.id.dialog_passcode_txt_3);
        txtDigit4 = (EditText)findViewById(R.id.dialog_passcode_txt_4);

        txtPasscode = (EditText)findViewById(R.id.dailog_survey_txt_passcode);
        if(txtPasscode.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
        final ResultPasscodeDialog _thisDlg = this;
        txtPasscode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                strCurPasscode = charSequence.toString();
                if( strCurPasscode.length() == 1){
                    _thisDlg.txtDigit1.setText("1");
                }
                if( strCurPasscode.length() == 2){
                    _thisDlg.txtDigit2.setText("2");
                }
                if( strCurPasscode.length() == 3){
                    _thisDlg.txtDigit3.setText("3");
                }
                if( strCurPasscode.length() == 4){
                    _thisDlg.txtDigit4.setText("4");
                    if(strCurPasscode.equals(GlobalConst.getPasscode(_thisDlg.parentAcitivy))){
                        _thisDlg.dismiss();
                        parentAcitivy.sendFinishMessage();
                        return;
                    }
                    txtPasscode.setText("");
                    strCurPasscode = "";
                    _thisDlg.txtDigit1.setText("");
                    _thisDlg.txtDigit2.setText("");
                    _thisDlg.txtDigit3.setText("");
                    _thisDlg.txtDigit4.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


}
