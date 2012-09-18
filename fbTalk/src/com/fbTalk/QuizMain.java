package com.fbTalk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class QuizMain extends Activity {
	Button startButton;
	Context context;
	QuizApplication quizapp = (QuizApplication)getApplication();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_main);
        context=getApplicationContext();
        startButton=(Button)findViewById(R.id.startButton);
        startButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				if(quizapp.getQuizData().getALLQuestionUpdatesOrderByCreatedAT().getCount() < 30){
				setProgressBarIndeterminateVisibility(true);
				quizapp.fetchQuizQuestions();
				setProgressBarIndeterminate(false);
				}
				Intent intent = new Intent();
				intent.setClass(context, MyFriendList.class);
				startActivity(intent);
				
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_quiz_main, menu);
        return true;
    }
}
