package com.planet.obcmobiles;

import android.content.Context;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by planet on 11/5/15.
 */
public class ApplicationTracker {


        public enum Target {
            APP,

        }

        private static ApplicationTracker sInstance;

        public static synchronized void initialize(Context context) {
            if (sInstance != null) {
                throw new IllegalStateException("Extra call to initialize analytics trackers");
            }

            sInstance = new ApplicationTracker(context);
        }

        public static synchronized ApplicationTracker getInstance() {
            if (sInstance == null) {
                throw new IllegalStateException("Call initialize() before getInstance()");
            }

            return sInstance;
        }

        private final Map<Target, Tracker> mTrackers = new HashMap<Target, Tracker>();
        private final Context mContext;

        /**
         * Don't instantiate directly - use {@link #getInstance()} instead.
         */
        private ApplicationTracker(Context context) {
            mContext = context.getApplicationContext();
        }

        public synchronized Tracker get(Target target) {
            if (!mTrackers.containsKey(target)) {
                Tracker tracker;
                switch (target) {
                    case APP:
                        tracker = GoogleAnalytics.getInstance(mContext).newTracker(R.xml.app_tracker);
                        break;
                    default:
                        throw new IllegalArgumentException("Unhandled analytics target " + target);
                }
                mTrackers.put(target, tracker);
            }

            return mTrackers.get(target);
        }
    }

