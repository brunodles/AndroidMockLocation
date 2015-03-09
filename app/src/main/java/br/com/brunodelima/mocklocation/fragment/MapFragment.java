package br.com.brunodelima.mocklocation.fragment;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.LinkedHashMap;
import java.util.List;

public class MapFragment extends SupportMapFragment {
    private static final String TAG = "MapFragment";
    public LinkedHashMap<String, Marker> markers = new LinkedHashMap<>();
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    LocationListener listener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
//            String provider = location.getProvider();
            Log.d(TAG, "onLocationChanged provider " + location.getProvider());
//            Marker marker = markers.get(provider);
//            if (marker != null) {
//                marker.setPosition(locationToLatLng(location));
//            }
            if (mMap != null)
                updateMarkers();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };
    private LocationManager locationManager;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpMapIfNeeded();

        Criteria criteria = new Criteria();
        locationManager.requestLocationUpdates(0l, 0f, criteria, listener, Looper.getMainLooper());
    }

    @Override
    public void onStop() {
        locationManager.removeUpdates(listener);
        super.onStop();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser)
            setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        if (mMap == null)
            mMap = this.getMap();
        if (mMap != null)
            setUpMap();
    }

    private void setUpMap() {
        updateMarkers();
//        addProviderMarker(LocationManager.PASSIVE_PROVIDER, BitmapDescriptorFactory.HUE_YELLOW);

        CameraUpdate cameraUpdate = CameraUpdateFactory
                .newLatLngZoom(
                        locationToLatLng(locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER)),
                        17);
        mMap.animateCamera(cameraUpdate);
    }

    private void updateMarkers() {
        mMap.clear();
        markers.clear();

        List<String> providerList = locationManager.getAllProviders();
        int providerListSize = providerList.size();
//        Log.d(TAG, "setUpMap providerListSize " + providerListSize);
        for (int i = 0; i < providerListSize; i++) {
            String provider = providerList.get(i);
            double factor = i;
            factor /= (providerListSize + 1);
            float hue = (float) (360f * factor);
//            Log.d(TAG, String.format("setUpMap provider: %s, hue: %s, factor: %s.", provider, hue, factor));
            if (LocationManager.PASSIVE_PROVIDER.equals(provider))
                continue;
            Marker marker = addProviderMarker(provider, hue);
            markers.put(provider, marker);
        }
    }


    private com.google.android.gms.maps.model.Marker addProviderMarker(String provider, float hue) {
        Location lastKnownLocation = locationManager.getLastKnownLocation(provider);
        if (lastKnownLocation == null)
            return null;

        LatLng latLng = locationToLatLng(lastKnownLocation);
        MarkerOptions markerOptions = new MarkerOptions()
                .icon(BitmapDescriptorFactory.defaultMarker(hue))
                .position(latLng)
                .title(provider);
        return mMap.addMarker(markerOptions);
    }

    private LatLng locationToLatLng(Location lastKnownLocation) {
        return new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
    }
}
