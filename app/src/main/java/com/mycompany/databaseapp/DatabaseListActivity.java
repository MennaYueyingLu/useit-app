package com.mycompany.databaseapp;

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
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DatabaseListActivity extends ListActivity {
    SQLiteDatabase database = null;
    Cursor dbCursor;
    DatabaseHelper dbHelper = new DatabaseHelper(this);
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_list);
        queryDataFromDatabase();
    }
    public void queryDataFromDatabase() {
        try {
            dbHelper.createDataBase();
        } catch (IOException ioe) {
        }
        List<String> list_values = new ArrayList<String>();
        try {
            database = dbHelper.getDataBase();
            dbCursor = database.rawQuery("SELECT place_name FROM Places;",
                    null);
            dbCursor.moveToFirst();
            int index = dbCursor.getColumnIndex("place_name");
            while (!dbCursor.isAfterLast()) {
                String record = dbCursor.getString(index).trim();
                list_values.add(record);
                dbCursor.moveToNext();
            }
        } finally {
            if (database != null) {
                dbHelper.close();
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.list_item, list_values);
        setListAdapter(adapter);

    }
    public void onClickAddDataRecord(View view) {
    }
    public void onClickUpdateList(View view) {
    }
    // HOW TO GET THE TEXT OF THE BUTTON PRESSED, AND PASS THIS AS A VARIABLE.
    public static String nameClickedName = "";
    public void onClickName(View v) {
        // 1) Possibly check for instance of first
        TextView b = (TextView) v;
        String buttonText = b.getText().toString();
        Log.d("Name","name clicked name os " + nameClickedName);
        nameClickedName = buttonText;
    }

    public void onClickStartNewActivity(View view) {
        onClickName(view);
        Intent intent = new Intent(this, PlaceInformationActivity.class);
        startActivity(intent);
    }


}


