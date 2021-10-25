package com.takeme.tm;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
/*import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;*/
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
/*import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.*;*/
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.*;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    String TAG = MapsActivity.class.getSimpleName();
    String address, addr;
    String address1;
    String address2;
    float distance=0;
    //int distance=0;
    Double lat1,lat2,long1,long2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Bundle bund = getIntent().getExtras();
        final String from=bund.getString("From");
        final String to=bund.getString("To");
        final String name=bund.getString("Name");

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
            checkMessagePermission();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Button currloc = (Button) findViewById(R.id.currlocation);
        currloc.setOnClickListener(new View.OnClickListener() {
            //8939733489
            // "XOLOBER ALERT!!: Hello! we found you a person to share your ride. He is at "
            @Override
            public void onClick(View v) {
                sendSMS("8939733489",addr );
                Toast.makeText(MapsActivity.this,name+" has been informed. You will be notified as soon as he accepts your ride",Toast.LENGTH_SHORT).show();


            }
        });

        final EditText etxtsrc = (EditText) findViewById(R.id.source);
        final EditText  etxtdst = (EditText) findViewById(R.id.destination);
        final Button showpath = (Button)findViewById(R.id.path);
        final Button endride = (Button) findViewById(R.id.endride);
        etxtdst.setText(to);
        etxtsrc.setText(from);
        showpath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                address1 = etxtsrc.getText().toString();
                address2 = etxtdst.getText().toString();
                getlatlong(address1);
                getlatlong2(address2);
                showpath();

            }
        });


        endride.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent f = new Intent(MapsActivity.this, feedback.class);
                Bundle bu=new Bundle();
                bu.putFloat("distance",distance);
                bu.putString("Name",name);
                f.putExtras(bu);
                startActivity(f);

            }
        });
    }

    public void showpath() {

        MarkerOptions options1 = new MarkerOptions();
        MarkerOptions options2 = new MarkerOptions();
        LatLng latLng1 = new LatLng(lat1,long1);
        LatLng latLng2 = new LatLng(lat2,long2);
        Location start = new Location("source");
        start.setLatitude(lat1);
        start.setLongitude(long1);
        Location dest = new Location("destination");
        dest.setLatitude(lat2);
        dest.setLongitude(long2);
        distance = start.distanceTo(dest);
        distance/= 1000;
        //distance=Integer.parseInt(String.valueOf(dist));
        Log.d(TAG, "distance between two locations in KM'S is " + distance);
        options1.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        options1.position(latLng1);
        options2.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        options2.position(latLng2);
        mMap.addMarker(options1);
        mMap.addMarker(options2);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng1));

        String url = getMapsApiDirectionsUrl();
        ReadTask downloadTask = new ReadTask();
        downloadTask.execute(url);

    }

    private String getMapsApiDirectionsUrl() {
        // Origin of route
        String str_origin = "origin=" + lat1 + "," + long1;

        // Destination of route
        String str_dest = "destination=" + lat2 + "," + long2;


        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;

        return url;
    }

    public void getlatlong(String addr)
    {
        Geocoder coder = new Geocoder(this);
        try {
            List<Address> address = coder.getFromLocationName(addr, 5);
            if (address==null)
            {
                Log.d(TAG, "no address  found");
            }
            Address add = address.get(0);
            lat1 = add.getLatitude();
            long1 = add.getLongitude();
            Log.d(TAG, "latitude : " + add.getLatitude());
            Log.d(TAG, "longitude : " + add.getLatitude());


        }
        catch (IOException e)
        {
        }
    }

    public void getlatlong2(String addr)
    {
        Geocoder coder = new Geocoder(this);
        try {
            List<Address> address = coder.getFromLocationName(addr, 5);
            if (address==null)
            {
                Log.d(TAG, "no address  found");
            }
            Address add = address.get(0);
            lat2 = add.getLatitude();
            long2 = add.getLongitude();
            Log.d(TAG, "latitude : " + add.getLatitude());
            Log.d(TAG, "longitude : " + add.getLatitude());


        }
        catch (IOException e)
        {
        }
    }
    public void sendSMS(String phonenumber, String message)
    {
        Log.d(TAG, "inside message");
        SmsManager sms = SmsManager.getDefault();
        Log.d(TAG, "sent message");
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.SEND_SMS)
                    == PackageManager.PERMISSION_GRANTED) {
                sms.sendTextMessage(phonenumber, null, message, null, null);
            }
        }
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
        // mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        }
        else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG,"Location updates");
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (com.google.android.gms.location.LocationListener) this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
        Log.d(TAG, "location is changed");
        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }


        String address = addresses.get(0).getAddressLine(0);
        // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        if (addresses != null) {
            Address returnedAddress = addresses.get(0);
            StringBuilder strReturnedAddress = new StringBuilder("");

            for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
            }
            addr = strReturnedAddress.toString();
        }

        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        String country = addresses.get(0).getCountryName();
        String postalCode = addresses.get(0).getPostalCode();
        String knownName = addresses.get(0).getFeatureName();
        Log.d(TAG, "address is : " + address);
        Log.d(TAG, "original address is : " + addr);
        Log.d(TAG, "city is : " + city);
        Log.d(TAG, "state is : " + state);
        Log.d(TAG, "country is : " + country);
        Log.d(TAG, "postal code is : " + postalCode);
        Log.d(TAG, "Known code is : " + knownName);


        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

        //stop location updates
        /*if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }*/

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public static final int MY_PERMISSIONS_REQUEST_MESSAGE = 100;
    public boolean checkMessagePermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_MESSAGE);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_MESSAGE);
            }
            return false;
        } else {
            return true;
        }
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }

    private class ReadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... url) {
            String data = "";
            try {
                HttpConnection http = new HttpConnection();
                data = http.readUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            new ParserTask().execute(result);
        }
    }

    private class ParserTask extends
            AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(
                String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                PathJSONParser parser = new PathJSONParser();
                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> routes) {
            ArrayList<LatLng> points = null;
            PolylineOptions polyLineOptions = null;

            // traversing through routes
            for (int i = 0; i < routes.size(); i++) {
                points = new ArrayList<LatLng>();
                polyLineOptions = new PolylineOptions();
                List<HashMap<String, String>> path = routes.get(i);

                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                polyLineOptions.addAll(points);
                polyLineOptions.width(10);
                polyLineOptions.color(Color.parseColor(	"#8b4513" ));
            }

            mMap.addPolyline(polyLineOptions);
        }
    }
}
