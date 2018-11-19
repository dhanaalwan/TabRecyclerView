package id.sch.smktelkom_mlg.tugas_tabviewrecylerview;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

import id.sch.smktelkom_mlg.tugas_tabviewrecylerview.adapter.CustomInfoWindowGoogleMap;
import id.sch.smktelkom_mlg.tugas_tabviewrecylerview.model.InfoWindowData;
import id.sch.smktelkom_mlg.tugas_tabviewrecylerview.model.Places;
import id.sch.smktelkom_mlg.tugas_tabviewrecylerview.realm.RealmHelperFavorite;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MapsActivity extends Fragment implements OnMapReadyCallback, LocationListener {

    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 1;
    Marker m;
    Realm realm;
    RealmHelperFavorite realmHelper;
    private GoogleMap mMap;
    private LocationManager locationManager;
    private Double global_latitude, global_longitude;
    private Boolean init = true;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_maps, container, false);

        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(configuration);
        realmHelper = new RealmHelperFavorite(realm);
        //GPS
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_FINE_LOCATION);
        }
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 10, this);

        //locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        //locationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 3000,10, this);
        //GPS


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Button btnChangeMapHybrid = view.findViewById(R.id.change_map_hybrid);
        btnChangeMapHybrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                //mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                Polyline line = mMap.addPolyline(new PolylineOptions()
                        .add(new LatLng(51.5, -0.1), new LatLng(40.7, -74.0))
                        .width(5)
                        .color(Color.BLUE));
            }
        });


        Button btnChangeMap = view.findViewById(R.id.change_map_normal);
        btnChangeMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            }
        });

        Button btnReCenter = view.findViewById(R.id.recenter);
        btnReCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    m.remove();
                    LatLng area = new LatLng(global_latitude, global_longitude);
                    MarkerOptions marker = new MarkerOptions().title("We are here")
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).position(area);
                    m = mMap.addMarker(marker);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(area));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Latitude " + global_latitude, Toast.LENGTH_LONG).show();
                }
            }
        });

        Button btnAddWarung = view.findViewById(R.id.add_warung);
        btnAddWarung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addWarungActivity = new Intent(getContext(), AddPlace.class);
                addWarungActivity.putExtra("LATITUDE", global_latitude);
                addWarungActivity.putExtra("LONGITUDE", global_longitude);
                startActivity(addWarungActivity);

            }
        });
        return view;
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        if (requestCode == MY_PERMISSIONS_REQUEST_FINE_LOCATION && grantResults.length > 0) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
//                if (ActivityCompat.checkSelfPermission(
//                        getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                        && ActivityCompat.checkSelfPermission(getContext(),
//                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                    // TODO: Consider calling
//                    //    ActivityCompat#requestPermissions
//                    // here to request the missing permissions, and then overriding
//                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                    //                                          int[] grantResults)
//                    // to handle the case where the user grants the permission. See the documentation
//                    // for ActivityCompat#requestPermissions for more details.
//                    return;
//                }
//                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 10, this);
//            }
//        }
//    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        CustomInfoWindowGoogleMap customInfoWindow = new CustomInfoWindowGoogleMap(getContext());
        mMap.setInfoWindowAdapter(customInfoWindow);
        load_point();

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Toast.makeText(getContext(), latLng.latitude + ", " + latLng.longitude, Toast.LENGTH_SHORT).show();
            }
        });

        mMap.setBuildingsEnabled(true);
        mMap.setIndoorEnabled(true);
        mMap.setTrafficEnabled(true);
        UiSettings mUiSettings = mMap.getUiSettings();
        mUiSettings.setZoomControlsEnabled(true);
        mUiSettings.setCompassEnabled(true);
        mUiSettings.setMyLocationButtonEnabled(true);
        mUiSettings.setScrollGesturesEnabled(true);
        mUiSettings.setZoomGesturesEnabled(true);
        mUiSettings.setTiltGesturesEnabled(true);
        mUiSettings.setRotateGesturesEnabled(true);

//        InfoWindowData info = new InfoWindowData();
//        info.setImage("ic_action_name");
//        info.setHotel("Hotel : excellent hotels available");
//        info.setFood("Food : all types of restaurants available");
//        info.setTransport("Reach the site by bus, car and train.");

        MarkerOptions marker = new MarkerOptions().title("init").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).position(new LatLng(-34, 151));
        m = mMap.addMarker(marker);
    }

    @Override
    public void onLocationChanged(Location location) {

        m.remove();

        String str = "Latitude: " + location.getLatitude() + "Longitude: " + location.getLongitude();
        Toast.makeText(getContext(), str, Toast.LENGTH_LONG).show();
        global_latitude = location.getLatitude();
        global_longitude = location.getLongitude();


        InfoWindowData info = new InfoWindowData();
        info.setImage("ic_action_name");
        info.setHotel("Hotel : excellent hotels available");
        info.setFood("Food : all types of restaurants available");
        info.setTransport("Reach the site by bus, car and train.");


        LatLng area = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions marker = new MarkerOptions().title("We are here").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).position(area);
        m = mMap.addMarker(marker);
        m.setTag(info);

        if (init) {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(area));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(14.0f));
            init = false;
        }

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
    public void onResume() {

        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_FINE_LOCATION);
        } else {
            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 10, this);
        }

        if (!init) {
            load_point();
        }
        super.onResume();
    }

    private void load_point() {

        List<Places> place = realmHelper.getAllPlaces();
        for (Places places : place) {
            InfoWindowData info = new InfoWindowData();
            info.setImage(places.getImage());
            info.setFood(places.getNama());
            info.setTransport(places.getAlamat());
            info.setHotel(String.valueOf(places.getLatitude() + ", " + String.valueOf(places.getLongitude())));

            MarkerOptions marker_place = new MarkerOptions().title(places.getNama()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).position(new LatLng(places.getLatitude(), places.getLongitude()));
            Marker mp = mMap.addMarker(marker_place);
            mp.setTag(info);

        }
    }
}
