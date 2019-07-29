package com.yourdiscovery.discovery.main.survey;

import android.content.Context;
import android.util.Log;

import com.yourdiscovery.discovery.GlobalConst;
import com.yourdiscovery.discovery.R;
import com.yourdiscovery.discovery.main.survey.helper.xml.plist.PListXMLHandler;
import com.yourdiscovery.discovery.main.survey.helper.xml.plist.PListXMLParser;
import com.yourdiscovery.discovery.main.survey.helper.xml.plist.domain.Array;
import com.yourdiscovery.discovery.main.survey.helper.xml.plist.domain.Dict;
import com.yourdiscovery.discovery.main.survey.helper.xml.plist.domain.PList;
import com.yourdiscovery.discovery.main.survey.helper.xml.plist.domain.PListObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by user1 on 2/14/2017.
 */
public class Survey extends  Object{

    public static Survey objCurSurvey;

    public String feedBack;
    public String firstName;
    public String lastName;
    public String email;
    public String fullName;
    List<Question> arrQuestion;
    public int curQuestIndex = 0;

    public boolean bIsDemo = false;

    public String getFullName(){
        return firstName + " " + lastName;
    }

    public enum QuestionType{
        QT_General(1),
        QT_Special(2);
        private final int code;

        QuestionType(int code){
            this.code = code;
        }

        public int getCode(){
            return this.code;
        }

    }

    public enum AnswerType{
        AT_Red(1),
        AT_Orange(2),
        AT_Green(3),
        AT_Purple(4),
        AT_Cafe(5),
        AT_Blue(6),
        AT_Pink(7),
        AT_LightBlue(8);
        private final int code;
        AnswerType(int code){
            this.code = code;
        }

        public int getCode(){
            return this.code;
        }
    }

    protected class Answer extends Object {

        public AnswerType type;
        public String answerDesc;
        public boolean bSelected;
        public Answer(AnswerType aType, String aDesc, Boolean aSelected) {
            this.type = aType;
            answerDesc = aDesc;
            bSelected = aSelected;
        }

    }
    protected class Question extends Object {

        public QuestionType type;
        public String questionDesc;
        public List<Answer> arrAnswerDesc;

        public Question(QuestionType aType, String aDesc, List<Answer> aArrAnsDesc) {
            type = aType;
            questionDesc = aDesc;
            arrAnswerDesc = aArrAnsDesc;
        }
    }
        QuestionType getQuestionType(Integer aCode){
            switch (aCode) {
                case 1:
                    return QuestionType.QT_General;
                case 2:
                    return QuestionType.QT_Special;
            }
            return  QuestionType.QT_General;
        }

        AnswerType getAnswerType(Integer aCode){
            switch (aCode){
                case 1:
                    return AnswerType.AT_Red;
                case 2:
                    return AnswerType.AT_Orange;
                case 3:
                    return AnswerType.AT_Green;
                case 4:
                    return AnswerType.AT_Purple;
                case 5:
                    return AnswerType.AT_Cafe;

                case 6:
                    return AnswerType.AT_Blue;

                case 7:
                    return AnswerType.AT_Pink;

                case 8:
                    return AnswerType.AT_LightBlue;

            }
            return AnswerType.AT_Blue;
        }

    Survey(Context aContext, String aFName, String aLName, String aEmail){

        firstName = aFName;
        lastName = aLName;
        email = aEmail;
        feedBack = "";

        arrQuestion = new ArrayList<>();
        Array _arrRoot = (Array) GlobalConst.getPlist(aContext,R.raw.questions);
        for (int i = 0; i < _arrRoot.size(); i++) {
             Dict question = (Dict)_arrRoot.get(i);
             List<Answer> arrStAnswer = new ArrayList<>();
             Array _arrAnswers = question.getConfigurationArray("answers");

            for (int j = 0; j < _arrAnswers.size(); j++) {
                Dict answer = (Dict)_arrAnswers.get(j);
                Answer _stAnswer = new Answer(getAnswerType(answer.getConfigurationInteger("type").getValue()),
                        answer.getConfiguration("answer").getValue(),false );
                //let _stAnswer = Answer(type: AnswerType(rawValue: answer["type"] as! Int)!, answerDesc: answer["answer"] as! String, bSelected: false)
                arrStAnswer.add(_stAnswer);
            }
            Question _stQuest = new Question(getQuestionType(question.getConfigurationInteger("type").getValue()),
                    question.getConfiguration("question").getValue(),
                    arrStAnswer);
            arrQuestion.add(_stQuest);
            //let _stQuest : Question = Question(type: QuestionType(rawValue:question["type"] as! Int)! , questionDesc: question["question"] as! String, arrAnswerDesc: arrStAnswer)
            //arrQuestion.append(_stQuest)
        }
        Collections.shuffle(arrQuestion);

    }

    public Question getCurQuestion(){
        return arrQuestion.get(curQuestIndex);
    }

    public void setCurAnswer(int ansIndex){
        boolean bFirst;
        if(ansIndex == 0){
            bFirst = true;
        }
        else{
            bFirst = false;
        }
        arrQuestion.get(curQuestIndex).arrAnswerDesc.get(0).bSelected = bFirst;
        arrQuestion.get(curQuestIndex).arrAnswerDesc.get(1).bSelected = !bFirst;
    }

    public boolean gotoNextQuestion(){
        if (curQuestIndex == 29){
            return false;
        }

        curQuestIndex += 1;
        return true;
    }

    public boolean gotoPreviousQuestion(){
        if (curQuestIndex == 0){
            return false;
        }

        curQuestIndex -= 1;
        return true;
    }

    public int getResultNo(){
        //return 1

        int[]_arrAnsCount = {0,0,0,0,0,0,0,0}; //8 types
        for (Question question:arrQuestion) {
            for (Answer answer:question.arrAnswerDesc) {
                if (answer.bSelected){
                    _arrAnsCount [answer.type.getCode() - 1] += 1;
                }
            }
        }

        for (int i = 0; i < 16; i++){
            ResultInfo _oneResInfo = GlobalConst.ArrResultInfo.get(i);
            int[] _arrCritical = _oneResInfo.arrCritical;
            int _nPassCnt = 0;
            for(int criticalNo:_arrCritical){
                int _nCritCnt;
                if (criticalNo == 5 || criticalNo == 6){
                    _nCritCnt = 5;
                }
                else{
                    _nCritCnt = 4;
                }
                if (_arrAnsCount[criticalNo-1] >= _nCritCnt){
                    _nPassCnt+=1;
                }
            }
            if (_nPassCnt == 4){
                return i;
            }
        }
        return 16;
    }

    public ResultInfo getResultString(int resNo){
        return GlobalConst.ArrResultInfo.get(resNo);
    }
}
