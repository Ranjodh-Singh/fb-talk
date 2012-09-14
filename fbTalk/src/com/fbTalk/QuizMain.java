package com.fbTalk;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class QuizMain extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_quiz_main, menu);
        return true;
    }
}
