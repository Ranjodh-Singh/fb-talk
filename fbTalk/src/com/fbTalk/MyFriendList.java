package com.fbTalk;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MyFriendList extends Activity {

	TextView questionCounter= (TextView)findViewById(R.id.questionCounter);
	TextView questionText= (TextView)findViewById(R.id.questionText);
	RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radioGroup1);
	Button nextButton = (Button)findViewById(R.id.nextButton);
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_friend_list);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_my_friend_list, menu);
        return true;
    }
}
