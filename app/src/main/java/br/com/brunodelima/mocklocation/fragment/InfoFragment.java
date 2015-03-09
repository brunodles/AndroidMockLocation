package br.com.brunodelima.mocklocation.fragment;

import android.app.Activity;
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
public class InfoFragment extends Fragment {

    private static final String TAG = "InfoFragment";

    private TextView ip;
    private Button startService;
    private Button stopService;
    private TextView mockLocationStatus;
    private Button enableMockLocations;
    private View.OnClickListener onStartClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onStartClick();
        }
    };
    private View.OnClickListener onStopClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onStopClick();
        }
    };
    private View.OnClickListener onEnableClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onEnableClick();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, null);
        ip = ((TextView) view.findViewById(R.id.ip));
        startService = (Button) view.findViewById(R.id.startService);
        stopService = (Button) view.findViewById(R.id.stopService);
        mockLocationStatus = (TextView) view.findViewById(R.id.mockLocationStatus);
        enableMockLocations = (Button) view.findViewById(R.id.enableMockLocations);

        startService.setOnClickListener(onStartClickListener);
        stopService.setOnClickListener(onStopClickListener);
        enableMockLocations.setOnClickListener(onEnableClickListener);


        return view;
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
}
