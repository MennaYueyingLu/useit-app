package com.mycompany.databaseapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.SphericalUtil;

public class SmallMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_small_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        String stringPlaceLat = getInfo(DatabaseListActivity.nameClickedName, "place_cor_lat");
        double placeLat = Double.parseDouble(stringPlaceLat);
        String stringPlaceLon = getInfo(DatabaseListActivity.nameClickedName, "place_cor_lon");
        double placeLon = Double.parseDouble(stringPlaceLon);
        Log.d("Location", "Place Lat is " + placeLat);
        Log.d("Location", "Place Lon is " + placeLon);
        Log.d("Location", "User Lat is " + HomeActivity.userLat);
        Log.d("Location", "User Lon is " + HomeActivity.userLng);
        LatLng frauenkircheLatLon = new LatLng(51.0519,13.7415);
        LatLng placeLatLon = new LatLng(placeLat, placeLon);

        LatLng userLatLon = new LatLng(HomeActivity.userLat,HomeActivity.userLng);

        mMap.addMarker(new MarkerOptions().position(placeLatLon).title(getInfo(DatabaseListActivity.nameClickedName, "place_name")));
        Log.d("Location", "User userLatLon is " + userLatLon);
        mMap.addCircle(new CircleOptions()
                .center(userLatLon)
                .fillColor(0xffffd500)
                .strokeColor(0xfff0790f)
                .strokeWidth(3f)
                .radius(50));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                frauenkircheLatLon, 13));
    }

    public void onClickStartHomeActivity(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public void onClickStartDatabaseListActivity(View view) {
        Intent intent = new Intent(this, DatabaseListActivity.class);
        startActivity(intent);
    }


    // From PlaceInformation Activity

    SQLiteDatabase database = null;
    Cursor dbCursor;
    DatabaseHelper dbHelper = new DatabaseHelper(this);

    public String getInfo(String clickedPlaceName, String inputInfo) {

        database = dbHelper.getDataBase();
        Cursor cursor = null;
        String output = " ";
        try {
            cursor = database.rawQuery("SELECT " + inputInfo + " FROM PLACES WHERE place_name=?", new String[]{clickedPlaceName + ""});

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                output = cursor.getString(cursor.getColumnIndex(inputInfo));
            }

            return output;
        } finally {
            if (database != null) {
                dbHelper.close();
            }
        }

    }

    public void setViewText(String elementId, String sql) {
        // TextView text = (TextView) findViewById(R.id.+ elementId);
        // text.setText(getInfo(DatabaseListActivity.nameClickedName,sql));
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("SmallMap Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
