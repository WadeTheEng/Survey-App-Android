package com.yourdiscovery.discovery.main;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.yourdiscovery.discovery.BaseActivity;
import com.yourdiscovery.discovery.GlobalConst;
import com.yourdiscovery.discovery.R;
import com.yourdiscovery.discovery.main.network.APIManager;
import com.yourdiscovery.discovery.main.network.ApiCallBack;
import com.yourdiscovery.discovery.main.network.ApiResponse;
import com.yourdiscovery.discovery.main.network.SearchResponse;
import com.yourdiscovery.discovery.main.survey.ResultInfo;

public class SearchResultActivity extends BaseActivity implements View.OnClickListener {

    EditText editFullName,editEmail;
    EditText txtType;
    ImageView ivType;
    SVProgressHUD hudSearch;
    ResultInfo curResult = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        Button _btnFind = (Button)findViewById(R.id.searchresult_btn_findit);
        _btnFind.setOnClickListener(this);
        editFullName = (EditText)findViewById(R.id.searchresult_edit_fullname);
        editEmail = (EditText)findViewById(R.id.searchresult_edit_email);
        txtType = (EditText)findViewById(R.id.searchresult_txt_typeresult);
        ivType = (ImageView)findViewById(R.id.searchresult_iv_type);
        ivType.setOnClickListener(this);
        hudSearch = new SVProgressHUD(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_result, menu);
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
        if(view == ivType){
            if(curResult == null)
                return;
            Intent _intTutDetail = new Intent(this, TutorialDetailActivity.class);
            _intTutDetail.putExtra("detResource",GlobalConst.getTutorialDetail(this,curResult.strType));
            startActivity(_intTutDetail);
        }
        else{

            if(!GlobalConst.validateEmailAddress(editEmail.getText().toString()) && editFullName.getText().toString().length() == 0){
                Toast.makeText(this, getString(R.string.string_msg_inputemailorname), Toast.LENGTH_SHORT).show();
                return;
            }

            final Context aContext = this;
            hudSearch.show();
            APIManager.reqSearchClient(String.valueOf(GlobalConst.getUserID(this)),GlobalConst.getUserKey(this),
                    editFullName.getText().toString(),
                    editEmail.getText().toString(), new ApiCallBack() {
                        @Override
                        public void onSuccess(ApiResponse response) {
                            hudSearch.dismiss();
                            SearchResponse _searchRes = (SearchResponse)response;
                            if (_searchRes.bSuccess == 1) {
                                ResultInfo _result = GlobalConst.ArrResultInfo.get(_searchRes.nType);
                                curResult = _result;
                                txtType.setText(_result.strFullName);
                                ivType.setImageResource(GlobalConst.getTutorialThumb(aContext,_result.strType));

                            } else {
                                Toast.makeText(aContext, response.strError, Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure() {
                            hudSearch.dismiss();
                            Toast.makeText(aContext, getString(R.string.string_msg_network_cantconnect), Toast.LENGTH_SHORT).show();
                        }
                    }
            );
        }

    }
}
