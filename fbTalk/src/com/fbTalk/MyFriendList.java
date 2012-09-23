package com.fbTalk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.RadioButton;
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
	QuizDBObject question;
	
	
    
    
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
    	 /*quizapp.setCursor(quizData.getALLQuestionUpdatesOrderByCreatedAT());*/
    	 this.setQuestions();
    	 nextButton.setOnClickListener(btnNext_Listener);
    	 
    	 
    }
	  private View.OnClickListener btnNext_Listener= new View.OnClickListener() {

		  public void onClick(View v) {
			  if(!checkAnswer()) {
				  return ;
				  }
			  if(question.getIndex()==quizapp.QUIZ_LENGTH){
				 //create a new activity to show the result and pass the intent to it. 
				  
			  }
			  else {
				  quizapp.increamentQuestionIndex();
				  Intent i = new Intent(MyFriendList.this, MyFriendList.class);
					startActivity(i);
					finish();
			  }
			  
		  }
	  };

    private void setQuestions() {
    	
    	TextView qIndexLabel= (TextView) findViewById(R.id.questionCounter);
    	qIndexLabel.setText(quizapp.getQuestionIndex());
		//set the question text from current question
		question = quizapp.getQuestionObjectByIndex(quizapp.getQuestionIndex());
        TextView qText = (TextView) findViewById(R.id.questionText);
        qText.setText(question.getQuestion());
        
        //set the available options
        
        RadioButton option1 = (RadioButton) findViewById(R.id.OptionradioButtonA);
        option1.setText(question.getOptionA());
        
        RadioButton option2 = (RadioButton) findViewById(R.id.OptionradioButtonB);
        option2.setText(question.getOptionB());
        
        RadioButton option3 = (RadioButton) findViewById(R.id.OptionradioButtonC);
        option3.setText(question.getOptionC());
        
        RadioButton option4 = (RadioButton) findViewById(R.id.OptionradioButtonD);
        option4.setText(question.getOptionD());
        
	}
    
	/**
	 * 
	 */
	private String getSelectedAnswer() {
		RadioButton c1 = (RadioButton)findViewById(R.id.OptionradioButtonA);
		RadioButton c2 = (RadioButton)findViewById(R.id.OptionradioButtonB);
		RadioButton c3 = (RadioButton)findViewById(R.id.OptionradioButtonC);
		RadioButton c4 = (RadioButton)findViewById(R.id.OptionradioButtonD);
		if (c1.isChecked())
		{
			return c1.getText().toString();
		}
		if (c2.isChecked())
		{
			return c2.getText().toString();
		}
		if (c3.isChecked())
		{
			return c3.getText().toString();
		}
		if (c4.isChecked())
		{
			return c4.getText().toString();
		}
		
		return null;
	}
    
	private boolean checkAnswer() {
		String answer = getSelectedAnswer();
		if (answer==null){
			//Log.d("Questions", "No Checkbox selection made - returning");
			return false;
		}
		else {
			//Log.d("Questions", "Valid Checkbox selection made - check if correct");
			if (question.getAnswer().equalsIgnoreCase(answer))
			{
				//Log.d("Questions", "Correct Answer!");
				quizapp.getCorrectQuestionsList().add(question.getIndex());
			}
			else{
				//Log.d("Questions", "Incorrect Answer!");
				quizapp.getWrongQuestionsList().add(question.getIndex());
			}
			return true;
		}
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_my_friend_list, menu);
        return true;
    }
    
}
