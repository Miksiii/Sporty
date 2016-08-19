package serenity.rs.sporty;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ActivityGoogleMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String eventLon, eventLat, eventTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        getExtrasFromPreviousActivity();
    }

    public void getExtrasFromPreviousActivity()
    {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            eventLat = extras.getString("latitude");
            eventLon = extras.getString("longitude");
            eventTitle = extras.getString("title");
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

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(Integer.parseInt(eventLat), Integer.parseInt(eventLon));
        mMap.addMarker(new MarkerOptions().position(sydney).title(eventTitle));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
