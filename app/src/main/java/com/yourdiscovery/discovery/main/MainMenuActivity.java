package com.yourdiscovery.discovery.main;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.yourdiscovery.discovery.BaseActivity;
import com.yourdiscovery.discovery.GlobalConst;
import com.yourdiscovery.discovery.R;
import com.yourdiscovery.discovery.main.survey.SurveyInfoActivity;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class MainMenuActivity extends BaseActivity {

    List<MenuData> arrMenuData = new ArrayList<MenuData>();
    MainMenuAdapter adtMenuList;
    private ListView lvMainMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main_menu);

        lvMainMenu = (ListView)findViewById(R.id.mainmenu_list_main);
        setMenuData();
        adtMenuList = new MainMenuAdapter(this,arrMenuData);
        lvMainMenu.setAdapter(adtMenuList);

    }



    private void setMenuData(){
        arrMenuData.add(new MenuData(getString(R.string.menu_startdisc), MenuType.StartOnDiscovery));
        arrMenuData.add(new MenuData(getString(R.string.menu_emaildisc), MenuType.EmailTo));
        arrMenuData.add(new MenuData(getString(R.string.menu_search), MenuType.SearchResult));
        arrMenuData.add(new MenuData(getString(R.string.menu_tutorial),MenuType.TypeTutorial));
        arrMenuData.add(new MenuData(getString(R.string.menu_support), MenuType.SetupSupport));
        arrMenuData.add(new MenuData(getString(R.string.menu_becomeaffiliate), MenuType.BecomeAffiliate));
        arrMenuData.add(new MenuData(getString(R.string.menu_logout), MenuType.Logout));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
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

    public void onMenuItemSelected(MenuType aID){
        //Log.i("kch", String.valueOf(aID));
        switch (aID){
            case StartOnDiscovery:
                Intent _intDiscovery = new Intent(this, SurveyInfoActivity.class);
                _intDiscovery.putExtra("isDemo",false);
                startActivity(_intDiscovery);
                break;
            case EmailTo:
                this.sendSurveyTo();
                break;
            case SearchResult:
                Intent _intSearch = new Intent(this, SearchResultActivity.class);
                startActivity(_intSearch);
                break;
            case TypeTutorial:
                Intent _intTutorial = new Intent(this, TutorialActivity.class);
                startActivity(_intTutorial);
                break;
            case SetupSupport:
                Intent _intSupport = new Intent(this, SetupSupportActivity.class);
                startActivity(_intSupport);
                break;
            case BecomeAffiliate:
                GlobalConst.openWebsite(this, GlobalConst.URL_BecomeAff);
                break;
            case Logout:
                GlobalConst.setUserID(this,-1);
                finish();
                break;
        }

    }

    public void sendSurveyTo(){
        String _strContent =  GlobalConst.URL_Server + "survey?id=" + GlobalConst.getUserID(this);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.sendsurvey_mail_subject));
        intent.putExtra(Intent.EXTRA_TEXT, _strContent);
        if (intent.resolveActivity(this.getPackageManager()) != null) {
            this.startActivity(intent);
        }
    }

    private enum MenuType{
        StartOnDiscovery(0),
        EmailTo(1),
        SearchResult(2),
        TypeTutorial(3),
        SetupSupport(4),
        BecomeAffiliate(5),
        Logout(6);

        public int nID;
        MenuType(int aID){
            nID = aID;
        }

    }

   protected class MenuData extends Object{
        public String strTitle;
        public MenuType nID;


        public MenuData(String aTitle,MenuType aID){
            this.strTitle = aTitle;
            this.nID = aID;
        }
    }



    protected class MainMenuAdapter extends BaseAdapter{

        private Context mContext;
        private LayoutInflater inflater;
        private List<MenuData> arrMenuItems;

        public MainMenuAdapter(Context context, List<MenuData> aMenuItems){
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
            if(convertView == null){
                convertView = inflater.inflate(R.layout.mainmenu_row,null);
            }

            final MenuData _data = arrMenuItems.get(position);

            Button _btnMenu = (Button) convertView.findViewById(R.id.mainmenurow_btn_menu);
            _btnMenu.setText(_data.strTitle);

            _btnMenu.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    MainMenuActivity _activity = (MainMenuActivity)mContext;
                    _activity.onMenuItemSelected(_data.nID);
                }
            });

            //_btnMenu.setEnabled(false);
            return convertView;
        }

    }
}
