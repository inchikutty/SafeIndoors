package com.inchikutty.SafeIndoors;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.IndoorBuilding;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Intent indoorIntent = getIntent();
        String username = indoorIntent.getStringExtra("username");
        RecyclerView recyclerView;
        Toast.makeText(getApplicationContext(), username, Toast.LENGTH_LONG).show();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //// Get a RequestQueue
        RequestQueue queue = VolleySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
        //// Add a request (in this example, called stringRequest) to your RequestQueue.
        //VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        //TransferLearningModel model =
         //       new TransferLearningModel(
         //               new AssetModelLoader(getActivity(), "path/to/your/model/under/assets"),
         //               Arrays.asList("Class A", "Class B", "Class C", "Class D"));
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker for park place mall Tucson.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     *
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng parkPlace = new LatLng(32.2194581, -110.8676057);
        mMap.addMarker(new MarkerOptions().position(parkPlace).title("Marker in Park Place Mall Tucson"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(parkPlace, 18));
        IndoorBuilding building = mMap.getFocusedBuilding();
        if (building != null) {
            building.getLevels().get(building.getActiveLevelIndex());
        }
    }

    public String getLoggedInUser(){
        //Mocking this for demo purposes by reading from app's input field.

        return "F1";
    }
    public Boolean isUserExposed( String userid ){
        //call API to google contact tracing and return value accordingly
        //For now use two ids to mock results
        if( userid == "F1") {
            return true;
        }else if(userid == "F2") {
            return false;
        }
        else{
            return false;
        }
    }
    public String getInfectionTestAppointment(String userid ){
        //Book appointment using connected clinics near user's location
        //for now mock results
        return "May 4 2020, Covid Lab Tucson 9:00 AM MST";
    }
    public Boolean getInfectionTestResult(String userid ){
        //get Test results in boolean form from connected clinics near user's location
        //for now mock results
        if(userid == "F1") {
            return true;
        }else if (userid == "F2"){
            return false;
        }else{
            return false;
        }
    }

    public String getUserStatus( String userid ){
        if( isUserExposed(userid)){
            return "exposed";
        }else{
            return "not exposed";
        }

    }
     public String setUserStatus( String userid ){
         if (getInfectionTestResult(userid )){
            return "exposed";
         }
         else{
             return "not exposed";
         }
     }

    private void MakeVolleyConnection() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "https://reqres.in/api/users?page=1", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray dataArray = response.getJSONArray("data");
                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject userData = dataArray.getJSONObject(i);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MapsActivity.this, ""+error.networkResponse,Toast.LENGTH_SHORT).show();
            }
        });

        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

}
