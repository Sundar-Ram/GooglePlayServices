package com.sundarsxyntekinc.geolocation;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.content.Context;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener{

    private final String LOG_TAG = "SundarTestLocationApp";
    private TextView txtOutput1;
    private TextView txtOutput2;
    private TextView txtOutput3;
    private TextView txtOutput4;
    private TextView txtOutput5;
    private TextView txtOutput6;

    private GoogleApiClient mGoogleApiClient;
    //private LocationRequest mLocationRequest;
    private Location mLastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGoogleApiClient = new GoogleApiClient.Builder(this).addApi(LocationServices.API).addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();
        txtOutput1 = (TextView)findViewById(R.id.txtOutput1);
        txtOutput2 = (TextView)findViewById(R.id.txtOutput2);
        txtOutput3 = (TextView)findViewById(R.id.txtOutput3);
        txtOutput4 = (TextView)findViewById(R.id.txtOutput4);
        txtOutput5 = (TextView)findViewById(R.id.txtOutput5);
        txtOutput6 = (TextView)findViewById(R.id.txtOutput6);

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
// Single Location Update
    @Override
    public void onConnected(Bundle bundle){

        double latitude, longitude;
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if(mLastLocation!= null){

            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();

            txtOutput1.setText(Double.toString(latitude));
            txtOutput2.setText(Double.toString(longitude));

            //GEOCODER to convert the latittude and longitude to cityname
            //To get city name

            String cityName = null;
            Geocoder gcd = new Geocoder(getApplicationContext(), Locale.getDefault());
            List<Address> addresses;

            try{
                addresses = gcd.getFromLocation(latitude,longitude,1);
                if(addresses.size() > 0) {
                    cityName = addresses.get(0).getLocality();
                    
                    System.out.println(addresses.get(0).getAddressLine(0));
                    System.out.println(addresses.get(0).getAddressLine(1));
                    System.out.println(addresses.get(0).getLocality());
                    System.out.println(addresses.get(0).getLocale());
                }
            }catch (IOException e){
                e.printStackTrace();
            }

            txtOutput5.setText(cityName);



        }
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
