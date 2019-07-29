package com.yourdiscovery.discovery.main;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.yourdiscovery.discovery.BaseActivity;
import com.yourdiscovery.discovery.GlobalConst;
import com.yourdiscovery.discovery.R;

import java.util.ArrayList;
import java.util.List;

public class TutorialActivity extends BaseActivity {

    List<TutorialListData> arrListData = new ArrayList<TutorialListData>();
    TutorialListAdapter adtTutorialList;
    private ListView lvTutorial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        lvTutorial = (ListView)findViewById(R.id.tutorial_list_main);
        setListData();
        adtTutorialList = new TutorialListAdapter(this,arrListData);
        lvTutorial.setAdapter(adtTutorialList);
    }

    private void setListData(){
        arrListData.add(new TutorialListData(TutorialListType.ThreeItem));
        arrListData.add(new TutorialListData(TutorialListType.ThreeItem));
        arrListData.add(new TutorialListData(TutorialListType.ThreeItem));
        arrListData.add(new TutorialListData(TutorialListType.ThreeItem));
        arrListData.add(new TutorialListData(TutorialListType.ThreeItem));
        arrListData.add(new TutorialListData(TutorialListType.TwoItem));
        arrListData.add(new TutorialListData(TutorialListType.GoMainMenu));
        arrListData.add(new TutorialListData(TutorialListType.GoTrainingSite));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tutorial, menu);
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

    public void gotoMainmenu(){
        finish();
    }

    public void gotoTraining(){
        GlobalConst.openWebsite(this,GlobalConst.URL_Learnmore);
    }

    public void gotoTutorialDetail(int aPos){
        Intent _intTutDetail = new Intent(this, TutorialDetailActivity.class);
        _intTutDetail.putExtra("detResource",GlobalConst.getTutorialDetail(this,GlobalConst.arrAnimals[aPos]));
        startActivity(_intTutDetail);
    }

    private enum TutorialListType{
        ThreeItem,
        TwoItem,
        GoMainMenu,
        GoTrainingSite
    }

    protected class TutorialListData extends Object{
        public TutorialListType itemType;
        public TutorialListData(TutorialListType aType){
            this.itemType = aType;
        }
    }

    protected class TutorialListAdapter extends BaseAdapter {

        private Context mContext;
        private LayoutInflater inflater;
        private List<TutorialListData> arrMenuItems;

        public TutorialListAdapter(Context context, List<TutorialListData> aMenuItems){
            this.mContext = context;
            this.arrMenuItems = aMenuItems;
            inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return arrMenuItems.size();
        }

        @Override
        public Object getItem(int location) {
            return arrMenuItems.get(location);
        }

        @Override
        public long getItemId(int position){
            return position;
        }

        @Override
        public View getView(int position,View convertView, ViewGroup parent){
            final TutorialListData _data = arrMenuItems.get(position);

            Button _btnMenu;
            switch (_data.itemType){
                case ThreeItem:
                    if(convertView == null || convertView.getId() != R.id.tutorial_layout_3item)
                         convertView = inflater.inflate(R.layout.tutorial_thumb_3item_row,null);

                    set3ItemRow(position, convertView);
                    break;
                case TwoItem:
                    if(convertView == null || convertView.getId() != R.id.tutorial_layout_2item)
                        convertView = inflater.inflate(R.layout.tutorial_thumb_2item_row,null);
                    set2ItemRow(position,convertView);
                    break;
                case GoMainMenu:
                    if(convertView == null || convertView.getId() != R.id.tutorial_layout_menu)
                        convertView = inflater.inflate(R.layout.tutorial_menu_row,null);

                    _btnMenu = (Button) convertView.findViewById(R.id.tutorialmenu_btn_menu);
                    _btnMenu.setText(getString(R.string.string_tutorial_mainmenu));
                    _btnMenu.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View view) {
                            TutorialActivity _activity = (TutorialActivity)mContext;
                            _activity.gotoMainmenu();
                        }
                    });

                    break;
                case GoTrainingSite:
                    if(convertView == null || convertView.getId() != R.id.tutorial_layout_menu)
                        convertView = inflater.inflate(R.layout.tutorial_menu_row,null);
                    _btnMenu = (Button) convertView.findViewById(R.id.tutorialmenu_btn_menu);
                    _btnMenu.setText(getString(R.string.string_tutorial_gototraining));
                    _btnMenu.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View view) {
                            TutorialActivity _activity = (TutorialActivity)mContext;
                            _activity.gotoTraining();
                        }
                    });
                    break;
            }
            //_btnMenu.setEnabled(false);
            return convertView;
        }

        private void set3ItemRow(final int aPosition,View convertView){
            ImageView _ivFirst = (ImageView)convertView.findViewById(R.id.tutorial_3item_iv_1);
            _ivFirst.setImageResource(GlobalConst.getTutorialThumb(mContext, GlobalConst.arrAnimals[aPosition *3]));
            _ivFirst.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TutorialActivity _activity = (TutorialActivity) mContext;
                    _activity.gotoTutorialDetail(3 * aPosition);
                }
            });

            ImageView _ivSecond = (ImageView)convertView.findViewById(R.id.tutorial_3item_iv_2);
            _ivSecond.setImageResource(GlobalConst.getTutorialThumb(mContext, GlobalConst.arrAnimals[aPosition *3 + 1]));
            _ivSecond.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TutorialActivity _activity = (TutorialActivity) mContext;
                    _activity.gotoTutorialDetail(3 * aPosition + 1);
                }
            });

            ImageView _ivThird = (ImageView)convertView.findViewById(R.id.tutorial_3item_iv_3);
            _ivThird.setImageResource(GlobalConst.getTutorialThumb(mContext, GlobalConst.arrAnimals[aPosition *3 + 2]));

            _ivThird.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TutorialActivity _activity = (TutorialActivity) mContext;
                    _activity.gotoTutorialDetail(3 * aPosition + 2);
                }
            });
        }

        private void set2ItemRow(final int aPosition,View convertView){
            ImageView _ivFirst = (ImageView)convertView.findViewById(R.id.tutorial_2item_iv_1);
            _ivFirst.setImageResource(GlobalConst.getTutorialThumb(mContext, GlobalConst.arrAnimals[aPosition *3]));
            _ivFirst.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TutorialActivity _activity = (TutorialActivity) mContext;
                    _activity.gotoTutorialDetail(3 * aPosition);
                }
            });

            ImageView _ivSecond = (ImageView)convertView.findViewById(R.id.tutorial_2item_iv_2);
            _ivSecond.setImageResource(GlobalConst.getTutorialThumb(mContext, GlobalConst.arrAnimals[aPosition *3 + 1]));
            _ivSecond.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TutorialActivity _activity = (TutorialActivity) mContext;
                    _activity.gotoTutorialDetail(3 * aPosition + 1);
                }
            });

        }

    }
}
