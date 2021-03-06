package com.mangobloggerandroid.app.util;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mangobloggerandroid.app.model.HomeItem;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * Created by ujjawal on 12/9/17.
 * Set of some useful Utils
 */

public class AppUtils {

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void startShareIntent(Context context, String shareBody) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        context.startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    public static double mapValueFromRangeToRange(double value, double fromLow, double fromHigh, double toLow, double toHigh) {
        return toLow + ((value - fromLow) / (fromHigh - fromLow) * (toHigh - toLow));
    }

    public static double clamp(double value, double low, double high) {
        return Math.min(Math.max(value, low), high);
    }

    private static String DateFormatter(String pattern, int i) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.ENGLISH);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, i);
        return sdf.format(cal.getTime());
    }

    public static String getCurrentDate() {
        return DateFormatter("dd-MMM-yy", 0);
    }


    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static void setList(Context context, String key, List<HomeItem> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        PreferenceUtil.writeDataString(context, key, json);
    }


    public static List<HomeItem> getListFromSharedPreferences(Context context, String key){
        Gson gson = new Gson();
        List<HomeItem> productFromShared = new ArrayList<>();
        String jsonPreferences = PreferenceUtil.getStringData(context, key);

        Type type = new TypeToken<List<HomeItem>>() {}.getType();
        productFromShared = gson.fromJson(jsonPreferences, type);

        return productFromShared;
    }

    public static String getCurrentWeek() {
        Calendar calender = Calendar.getInstance();
        return ("Week" + calender.get(Calendar.WEEK_OF_YEAR));
    }






}
