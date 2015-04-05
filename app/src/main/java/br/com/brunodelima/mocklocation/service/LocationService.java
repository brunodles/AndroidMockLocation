package br.com.brunodelima.mocklocation.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import java.io.IOException;
import java.net.SocketException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.brunodelima.mocklocation.R;
import br.com.brunodelima.mocklocation.activity.MainActivity;
import br.com.brunodelima.socket.Broadcast;
import br.com.brunodelima.socket.Protocol;
import br.com.brunodelima.socket.SocketServer;
import br.com.brunodelima.socket.StringProtocol;

public class LocationService extends Service {

    public static final String PROVIDER = "socket";
    private static final String TAG = "SocketService";
    //    public static final String PROVIDER = LocationManager.GPS_PROVIDER;
    private static final int NOTIFICATION_ID = 1000;
    private SocketServer socketServer;
    private Broadcast.Server broadcastServer;
    private LocationManager locationManager;
    Protocol protocol = new StringProtocol() {

        @Override
        public String processMessage(String message) {
            Location location = MessageResolver.resolveLocation(message);
            if (location != null) {
                try {
                    locationManager.setTestProviderLocation(PROVIDER, location);
                } catch (Exception e) {
                    Log.e(TAG, "processMessage ", e);
                }
                return "Location Received.";
            } else {
                return "Unknow message.";
            }
        }

        @Override
        public boolean isBreakMessage(String message) {
            return "QUIT".equalsIgnoreCase(message);
        }
    };

    private static Location locationFromString(String message) {
        String[] split = message.split(",");
        double lat = Double.valueOf(split[0]);
        double lng = Double.valueOf(split[1]);
//            double zoom = Double.valueOf(split[2]);

        Location location = new Location(PROVIDER);
        location.setLatitude(lat);
        location.setLongitude(lng);
        location.setAccuracy(5f);
        location.setTime(System.currentTimeMillis());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
            location.setElapsedRealtimeNanos(SystemClock.elapsedRealtime());
        return location;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        try {
            locationManager.addTestProvider(PROVIDER, false, true, false, false, false, false, false, 1, 10);
        } catch (Exception e) {
            Log.e(TAG, "onCreate Provider may existis.", e);
        }
        try {
            locationManager.setTestProviderEnabled(PROVIDER, true);
        } catch (Exception e) {
            Log.e(TAG, "onCreate can't enable as a test provider", e);
        }

//        protocol = new DummyProtocol();
        try {
            socketServer = new SocketServer(30123, protocol);
            socketServer.start();
            Log.d(TAG, "onCreate serverStarted");
        } catch (IOException e) {
            Log.e(TAG, "onCreate error on start socket server", e);
        }
        try {
            broadcastServer = Broadcast.getNewServer("locations", "230.0.0.1", 30122);
            broadcastServer.start();
        } catch (SocketException e) {
            Log.e(TAG, "onCreate error on start broadcast server", e);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        showNotification();
        return super.onStartCommand(intent, flags, startId);
    }

    private void showNotification() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(false)
                .setContentTitle(String.format("%s is Running", getString(R.string.app_name)))
                .setContentIntent(pendingIntent)
                .setOngoing(true);

        Notification notification = builder
                .build();

        // Send the notification.
        getNotificationManager().notify(NOTIFICATION_ID, notification);
    }

    public NotificationManagerCompat getNotificationManager() {
        return NotificationManagerCompat.from(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        locationManager.removeTestProvider(PROVIDER);

        if (socketServer != null)
            socketServer.interrupt();
        getNotificationManager().cancel(NOTIFICATION_ID);
        super.onDestroy();
    }

    private static abstract class MessageResolver {
        private MessageResolver next;

        public static Location resolveLocation(String message) {
            GoogleMapsUrlMessageResolver resolverChain = new GoogleMapsUrlMessageResolver();
            resolverChain.addNext(new OnlyLocationMessageResolver());
            return resolverChain.resolve(message);
        }

        public MessageResolver addNext(MessageResolver next) {
            this.next = next;
            return next;
        }

        abstract Location getLocation(String message) throws CantResolveException;

        final Location resolve(String message) {
            try {
                return getLocation(message);
            } catch (CantResolveException e) {
                return callNext(message);
            }
        }

        private Location callNext(String message) {
            if (next == null)
                return null;
            return next.resolve(message);
        }

        public static class CantResolveException extends Exception {
        }
    }

    private static class GoogleMapsUrlMessageResolver extends MessageResolver {

        private static final String REGEX = "https?.*google.*\\@(.*)";
        private static final Pattern pattern = Pattern.compile(REGEX);

        @Override
        Location getLocation(String message) throws CantResolveException {
            Matcher matcher = pattern.matcher(message);
            if (!matcher.find())
                throw new CantResolveException();
            return locationFromString(matcher.group(1));
        }
    }

    private static class OnlyLocationMessageResolver extends MessageResolver {

        @Override
        Location getLocation(String message) throws CantResolveException {
            try {
                return locationFromString(message);
            } catch (Exception e) {
                throw new CantResolveException();
            }
        }
    }

}
