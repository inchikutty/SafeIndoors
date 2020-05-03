package com.inchikutty.SafeIndoors;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleySingleton  {
    private static VolleySingleton singletonInstance = null;
    private static Context ctx;
    private RequestQueue requestQueue;

    private VolleySingleton(Context context) {
        ctx = context;
        requestQueue = Volley.newRequestQueue(context);
    }

    public static synchronized VolleySingleton getInstance(Context context) {

        if (singletonInstance == null) {
            singletonInstance = new VolleySingleton(context);
        }
        return singletonInstance;
    }

    public RequestQueue getRequestQueue() {
        // getApplicationContext() is key, it keeps you from leaking the
        // Activity or BroadcastReceiver if someone passes one in.
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        return this.requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

}

