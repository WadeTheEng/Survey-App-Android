package com.yourdiscovery.discovery;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.util.Patterns;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.yourdiscovery.discovery.main.survey.ResultInfo;
import com.yourdiscovery.discovery.main.survey.helper.xml.plist.PListXMLHandler;
import com.yourdiscovery.discovery.main.survey.helper.xml.plist.PListXMLParser;
import com.yourdiscovery.discovery.main.survey.helper.xml.plist.domain.Array;
import com.yourdiscovery.discovery.main.survey.helper.xml.plist.domain.Dict;
import com.yourdiscovery.discovery.main.survey.helper.xml.plist.domain.PList;
import com.yourdiscovery.discovery.main.survey.helper.xml.plist.domain.PListObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by user1 on 2/13/2017.
 */
public class GlobalConst {

    static final public String URL_APIServer = "http://yourdiscoveryoffice.com/index/public/api/";

    //static final public String URL_APIServer = "http://192.168.56.1/Laravel/public/api/";
    static final public String URL_Server = "http://yourdiscoveryoffice.com/index/public/";
    static final public String URL_Contact = "http://yourdiscoveryoffice.com/index/public/";

    static final public String URL_BecomeAff  = "https://salesdiscovery.samcart.com/affiliates/signup";
    static final public String URL_Upgrade  = "http://www.thesalesdiscovery.com/upgrade";
    static final public String URL_Learnmore = "http://www.thesalesdiscovery.com";
    static final public String URL_Free = "http://www.thesalesdiscovery.com/free-trial";
    static final public String Mail_Contact  = "techsupport@thesalesdiscovery.com";
    static final public String[] arrAnimals ={"Otter","Owl","Eagle","Wolf","Tiger","Bear","Penguin","Raven","Dolphin","Bulldog","Labrador","Fox","Turtle","Kangaroo","Lion","Panther","Porcupine"};

    static final public List<ResultInfo> ArrResultInfo = new ArrayList<>();
    static final public String FinishMessageFilter = "Finish_Activity";

    static public void initGlobal(Context aContext){
        // Init Result Info
        Array _arrRoot = (Array) GlobalConst.getPlist(aContext,R.raw.result);
        for (int i = 0; i < _arrRoot.size(); i++) {
            Dict result = (Dict)_arrRoot.get(i);
            Array _arrCriticals = result.getConfigurationArray("critical");

            ResultInfo _resInfo = new ResultInfo(result.getConfiguration("type").getValue(),
                    result.getConfiguration("fullname").getValue(),
                    result.getConfiguration("desc").getValue(),
                    _arrCriticals);
            ArrResultInfo.add(_resInfo);
        }

    }

    static public void openWebsite(Context aContext, String aUrl){
        Intent _intBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse(aUrl));
        aContext.startActivity(_intBrowser);
    }

    static public int getTutorialThumb(Context context, String aStrName){
        String _strResourceName = "tutorial_thumb_" + aStrName.toLowerCase();
        int drawableResourceId = context.getResources().getIdentifier(_strResourceName, "drawable", context.getPackageName());
        return drawableResourceId;
    }

    static public int getTutorialDetail(Context context, String aStrName){
        String _strResourceName = "tutorial_detail_" + aStrName.toLowerCase();
        int drawableResourceId = context.getResources().getIdentifier(_strResourceName, "drawable", context.getPackageName());
        return drawableResourceId;
    }

    static public int getResultImage(Context context, String aStrName){
        String _strResourceName = "result_" + aStrName.toLowerCase();
        int drawableResourceId = context.getResources().getIdentifier(_strResourceName, "drawable", context.getPackageName());
        return drawableResourceId;
    }
    static public boolean validateEmailAddress(String emailAddress){
        if(emailAddress == null || emailAddress.length() == 0)
            return false;
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(emailAddress).matches();
    }

    public static boolean validateFirstName( String firstName )
    {
        return firstName.matches("[A-Z][a-zA-Z]*");
    } // end method validateFirstName

    // validate last name
    public static boolean validateLastName( String lastName )
    {
        return lastName.matches("[a-zA-z]+([ '-][a-zA-Z]+)*");
    } // end method validateLastName

    public static PListObject getPlist(Context aContext, int aReID){
        try
        {
            InputStream _inputRaw = aContext.getResources().openRawResource(aReID);

            PListXMLParser parser;
            parser = new PListXMLParser();
            PListXMLHandler handler = new PListXMLHandler();
            parser.setHandler(handler);
            parser.parse(_inputRaw);
            PList actualPList = ((PListXMLHandler) parser.getHandler()).getPlist();
            return actualPList.getRootElement();
        }
        catch(IOException e){
            Log.d("input exception", e.getLocalizedMessage());
        }
        return null;
    }

    public static int getUserID(Context aContext){
        SharedPreferences preferences = aContext.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        return preferences.getInt("userID", -1);
    }

    public static void setUserID(Context aContext,int aID){
        SharedPreferences preferences = aContext.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("userID", aID);
        editor.commit();
    }

    public static String getUserKey(Context aContext){
        SharedPreferences preferences = aContext.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        return preferences.getString("userKey","");
    }

    public static void setUserKey(Context aContext,String aKey){
        SharedPreferences preferences = aContext.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("userKey", aKey);
        editor.commit();
    }

    public static String getPasscode(Context aContext){
        SharedPreferences preferences = aContext.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        return preferences.getString("passcode",null);
        //return "1234";
    }

    public static void setPasscode(Context aContext,String aPasscode){
        SharedPreferences preferences = aContext.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("passcode", aPasscode);
        editor.commit();
    }

    public static void sendMailToContact(Context aContext){
        String[] addresses = {GlobalConst.Mail_Contact};
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, "");
        if (intent.resolveActivity(aContext.getPackageManager()) != null) {
            aContext.startActivity(intent);
        }
    }
}
