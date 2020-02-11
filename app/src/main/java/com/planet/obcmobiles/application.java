package com.planet.obcmobiles;

/**
 * Created by planet on 11/5/15.
 */

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.ViewConfiguration;

import com.datatheorem.android.trustkit.TrustKit;
import com.datatheorem.android.trustkit.reporting.BackgroundReporter;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.StandardExceptionParser;
import com.google.android.gms.analytics.Tracker;

import java.lang.reflect.Field;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import okhttp3.OkHttpClient;

public class application extends Application {
    private static application mInstance;

    public static synchronized application getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        ApplicationTracker.initialize(this);
        ApplicationTracker.getInstance().get(ApplicationTracker.Target.APP);
        SSLPinning();

        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("SplashScreen");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception ex) {
            // Ignoreder
            //Log.e("dadd", ex.toString());
        }
    }

    public synchronized Tracker getGoogleAnalyticsTracker() {
        ApplicationTracker analyticsTrackers = ApplicationTracker.getInstance();
        return analyticsTrackers.get(ApplicationTracker.Target.APP);
    }


    public void trackScreenView(String screenName) {
        Tracker t = getGoogleAnalyticsTracker();
        t.setScreenName(screenName);
        t.send(new HitBuilders.ScreenViewBuilder().build());
        GoogleAnalytics.getInstance(this).dispatchLocalHits();
    }

    private void SSLPinning() {
        try {

            // Using the default path - res/xml/network_security_config.xml
            TrustKit.initializeWithNetworkSecurityConfiguration(this);
            // OR using a custom resource (TrustKit can't be initialized twice)
            //  TrustKit.initializeWithNetworkSecurityConfiguration(this, R.xml.my_custom_network_security_config);
            URL url = new URL("https://www.datatheorem.com");
           //  URL url = new URL("https://121.241.255.225");
            String serverHostname = url.getHost();

            // HttpsUrlConnection
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setSSLSocketFactory(TrustKit.getInstance().getSSLSocketFactory(serverHostname));


        } catch (Exception e) {
            e.getMessage();
        }
    }


    public void trackException(Exception e) {
        if (e != null) {
            Tracker t = getGoogleAnalyticsTracker();

            t.send(new HitBuilders.ExceptionBuilder()
                    .setDescription(new StandardExceptionParser(this, null)
                            .getDescription(Thread.currentThread().getName(), e))
                    .setFatal(false)
                    .build()
            );
        }
    }


    public void trackEvent(String category, String action, String label) {
        Tracker t = getGoogleAnalyticsTracker();
        t.send(new HitBuilders.EventBuilder().setCategory(category).setAction(action).setLabel(label).build());
    }


}