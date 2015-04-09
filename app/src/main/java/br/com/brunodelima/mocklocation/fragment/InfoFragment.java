package br.com.brunodelima.mocklocation.fragment;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import br.com.brunodelima.mocklocation.R;
import br.com.brunodelima.mocklocation.service.LocationService;


/**
 * Created by dev on 07/01/2015.
 */
public class InfoFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "InfoFragment";

    private TextView ip;
    private Button startService;
    private Button stopService;
    private TextView mockLocationStatus;
    private Button enableMockLocations;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, null);
        ip = ((TextView) view.findViewById(R.id.ip));
        startService = (Button) view.findViewById(R.id.startService);
        stopService = (Button) view.findViewById(R.id.stopService);
        mockLocationStatus = (TextView) view.findViewById(R.id.mockLocationStatus);
        enableMockLocations = (Button) view.findViewById(R.id.enableMockLocations);

        startService.setOnClickListener(this);
        stopService.setOnClickListener(this);
        enableMockLocations.setOnClickListener(this);

        return view;
    }

    private boolean isMockLocationEnabled() {
        return Settings.Secure.getString(getActivity().getContentResolver(),
                Settings.Secure.ALLOW_MOCK_LOCATION).equals("1");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    public void onResume() {
        super.onResume();
//            String myIpAddress = InetAddress.getLocalHost().getHostAddress();
        WifiManager wm = (WifiManager) getActivity().getSystemService(Context.WIFI_SERVICE);
        String myIpAddress = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        ip.setText(myIpAddress);

        updateVisibility();
    }

    public void onStartClick() {
        Log.d(TAG, "onStartClick ");
        FragmentActivity activity = getActivity();
        Intent serviceIntent = new Intent(activity, LocationService.class);
        activity.startService(serviceIntent);
    }

    public void onStopClick() {
        Log.d(TAG, "onStopClick ");
        FragmentActivity activity = getActivity();
        Intent serviceIntent = new Intent(activity, LocationService.class);
        activity.stopService(serviceIntent);
    }

    private void onEnableClick() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onClick(View v) {
        if (startService == v)
            onStartClick();
        else if (stopService == v)
            onStopClick();
        else if (enableMockLocations == v)
            onEnableClick();
        updateVisibility();
    }

    private void updateVisibility() {
        boolean mockLocationEnabled = isMockLocationEnabled();
        boolean serviceRunning = isMyServiceRunning(LocationService.class, getActivity());
        enableMockLocations.setVisibility(mockLocationEnabled ? View.GONE : View.VISIBLE);
        startService.setEnabled(!serviceRunning && mockLocationEnabled);
        stopService.setEnabled(serviceRunning && mockLocationEnabled);
    }


    private boolean isMyServiceRunning(Class<?> serviceClass, Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE))
            if (serviceClass.getName().equals(service.service.getClassName()))
                return true;
        return false;
    }
}
