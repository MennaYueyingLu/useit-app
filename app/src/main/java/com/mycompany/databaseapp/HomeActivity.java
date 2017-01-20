package com.mycompany.databaseapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void onClickStartDatabaseListActivity(View view) {
        Intent intent = new Intent(this, DatabaseListActivity.class);
        startActivity(intent);
    }

    public void onClickStartBigMapActivity(View view) {
        Intent intent = new Intent(this, MapBoxActivity.class);
        startActivity(intent);
    }
}
