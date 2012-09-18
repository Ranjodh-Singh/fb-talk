package com.fbTalk;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MyFriendList extends Activity {

	TextView questionCounter;
	TextView questionText;
	RadioGroup radioGroup ;
	Button nextButton;
	Button backButton;
	Chronometer quizTimer;
	private QuizApplication quizapp;
	private QuizData quizData;
	private Cursor cursor;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_friend_list);
         quizTimer=(Chronometer)findViewById(R.id.quizClockCounter);
         quizTimer.start();
         questionCounter= (TextView)findViewById(R.id.questionCounter);
         questionText= (TextView)findViewById(R.id.questionText);
    	 radioGroup = (RadioGroup)findViewById(R.id.radioGroup1);
    	 nextButton = (Button)findViewById(R.id.nextButton);
    	 backButton = (Button)findViewById(R.id.backButton);
    	 quizapp = (QuizApplication)getApplication();
    	 this.quizData=quizapp.getQuizData();
    	 cursor = quizData.getALLQuestionUpdatesOrderByCreatedAT();
    	 
    	 
    }

    public List<QuizDBObject> getQuizDbObjectList(Cursor c ){
    	List<QuizDBObject> quizDBObjectList = new ArrayList<QuizDBObject>();
    	c.moveToFirst();
    	for (c.moveToFirst();!c.isAfterLast();c.moveToNext() ) {
    		
			
		}
    	
    	
    	return quizDBObjectList ;
    }
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_my_friend_list, menu);
        return true;
    }
    
}
