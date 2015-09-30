package com.sundarsxyntekinc.geolocation;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;


public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener, LocationListener{

    private final String LOG_TAG = "SundarTestLocationApp";
    private TextView txtOutput1;
    private TextView txtOutput2;
    private TextView txtOutput3;
    private TextView txtOutput4;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGoogleApiClient = new GoogleApiClient.Builder(this).addApi(LocationServices.API).addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();
        txtOutput1 = (TextView)findViewById(R.id.txtOutput1);
        txtOutput2 = (TextView)findViewById(R.id.txtOutput2);
        txtOutput3 = (TextView)findViewById(R.id.txtOutput3);
        txtOutput4 = (TextView)findViewById(R.id.txtOutput4);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onStart(){
        super.onStart();
        //Connect the Client
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop(){
        super.onStop();
        //Disconnect the Client
        if(mGoogleApiClient.isConnected())
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnected(Bundle bundle){

        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(10000);

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

    }

    @Override
    public void onLocationChanged(Location location){
        Log.i(LOG_TAG,location.toString());
        txtOutput1.setText(Double.toString(location.getLatitude()));
        txtOutput2.setText(Double.toString(location.getLongitude()));
    }

    @Override
    public void onConnectionSuspended(int i){
        Log.i(LOG_TAG, "GoogleApiClient connection has been suspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult){
        Log.i(LOG_TAG,"GoogleApiClient connection has failed");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
