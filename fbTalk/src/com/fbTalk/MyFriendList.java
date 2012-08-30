package com.fbTalk;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MyFriendList extends Activity {

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
