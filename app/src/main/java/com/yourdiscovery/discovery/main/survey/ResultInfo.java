package com.yourdiscovery.discovery.main.survey;

import com.yourdiscovery.discovery.main.survey.helper.xml.plist.domain.*;
import com.yourdiscovery.discovery.main.survey.helper.xml.plist.domain.Integer;
import java.lang.String;
/**
 * Created by user1 on 2/14/2017.
 */
public class ResultInfo extends Object {

    public String strType;
    public String strDesc;
    public String strFullName;
    int arrCritical[];

    public ResultInfo(String aType, String aFullName, String aDesc, Array aArrCrit){
        strType = aType;
        strDesc = aDesc;
        strFullName = aFullName;
        arrCritical = new int[4];
        for (int i = 0; i < aArrCrit.size(); i++){
            com.yourdiscovery.discovery.main.survey.helper.xml.plist.domain.Integer _aCrit = (Integer)aArrCrit.get(i);
            arrCritical[i] = _aCrit.getValue();
        }
    }

}
