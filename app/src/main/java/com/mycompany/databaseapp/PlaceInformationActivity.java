package com.mycompany.databaseapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class PlaceInformationActivity extends AppCompatActivity {
    SQLiteDatabase database = null;
    Cursor dbCursor;
    DatabaseHelper dbHelper = new DatabaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_information);
        queryDataFromDatabase();
    }

    public String getPlaceName(String clickedPlaceName) {
        database = dbHelper.getDataBase();
        Cursor cursor = null;
        String outputPlaceName = "";
        try{
            cursor = database.rawQuery("SELECT place_name FROM PLACES WHERE place_name=?", new String[] {clickedPlaceName + ""});

            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                outputPlaceName = cursor.getString(cursor.getColumnIndex("place_name"));
            }

            return outputPlaceName;
        }finally {
            if (database != null) {
                dbHelper.close();
            }
        }
    }

    public String getDescription(String clickedPlaceName) {
        database = dbHelper.getDataBase();
        Cursor cursor = null;
        String outputDescription = "";
        try{
            cursor = database.rawQuery("SELECT place_description FROM PLACES WHERE place_name=?", new String[] {clickedPlaceName + ""});

            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                outputDescription = cursor.getString(cursor.getColumnIndex("place_description"));
            }

            return outputDescription;
        }finally {
            if (database != null) {
                dbHelper.close();
            }
        }
    }

    public String getInfo(String clickedPlaceName, String inputInfo ){

        database = dbHelper.getDataBase();
        Cursor cursor = null;
        String output = " ";
        try{
            cursor = database.rawQuery("SELECT " + inputInfo +  " FROM PLACES WHERE place_name=?", new String[] {clickedPlaceName + ""});

            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                output = cursor.getString(cursor.getColumnIndex(inputInfo));
            }

            return output;
        }finally {
            if (database != null) {
                dbHelper.close();
            }
        }

    }

    public void setViewText(String elementId, String sql){
       // TextView text = (TextView) findViewById(R.id.+ elementId);
       // text.setText(getInfo(DatabaseListActivity.nameClickedName,sql));
    }

    public void queryDataFromDatabase() {
//       Get the text of the button clicked.
        Log.d("Location", "This is a log");
        setViewText("place_info_title","place_name");
        TextView text = (TextView) findViewById(R.id.place_info_title);
        text.setText(getInfo(DatabaseListActivity.nameClickedName,"place_name"));

        TextView textDescription = (TextView) findViewById(R.id.place_info_description);
        textDescription.setText(getInfo(DatabaseListActivity.nameClickedName,"place_description"));
        TextView textAddress = (TextView) findViewById(R.id.place_info_address);
        textAddress.setText(getInfo(DatabaseListActivity.nameClickedName,"place_address"));
        //TextView image = (TextView) findViewById(R.id.place_info_image);
        //textDescription.setText(getDescription(DatabaseListActivity.nameClickedName));
        TextView textLat = (TextView) findViewById(R.id.place_info_lat);
        textLat.setText(getInfo(DatabaseListActivity.nameClickedName,"place_cor_lat"));
        TextView textLon = (TextView) findViewById(R.id.place_info_lon);
        textLon.setText(getInfo(DatabaseListActivity.nameClickedName,"place_cor_lon"));
    }

    public void onClickStartSmallMapActivity(View view) {
        Intent intent = new Intent(this, SmallMapActivity.class);
        startActivity(intent);
    }
}
