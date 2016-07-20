package com.example.castiel.clashofclanmulti.clashofclanmulti;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by castiel on 7/4/16.
 */
public class Control {

    public static String account = "acc";
    public static String accountName = "accName";
    public static String widget = "com/example/castiel/clashofclanmulti/widget";


    public static String[] accounts = { "shared_prefs00", "shared_prefs01", "shared_prefs02",
                                        "shared_prefs03", "shared_prefs04", "shared_prefs05",
                                        "shared_prefs06", "shared_prefs07", "shared_prefs08",
                                        "shared_prefs09", "shared_prefs10", "shared_prefs11",
                                        "shared_prefs12", "shared_prefs13", "shared_prefs14",
                                        "shared_prefs15" };

    public static String backupAccount = "shared_prefs_backup";


    public static String[] getListAccountsEnabled(Context context){
        ArrayList<String> result = new ArrayList<>();
        for ( int i = 0; i<accounts.length; i++ ){
            if ( Control.isEnabled(context, accounts[i]) ){
                result.add(accounts[i]);
            }
        }
        if ( result.size() > 0 )
            return result.toArray(new String[result.size()]);
        else
            return null;
    }

    public static boolean isEnabled(Context context, String acc){
        SharedPreferences sp = context.getSharedPreferences(Control.account, Context.MODE_PRIVATE);
        return sp.getBoolean(acc, false);
    }

    public static String getFirstDisableAccount(Context context){
        for ( int i = 0; i<accounts.length; i++ ){
            if ( !Control.isEnabled(context, accounts[i])){
                return accounts[i];
            }
        }
        return null;
    }

    public static void disable(Context context, String acc) throws IOException {
        SharedPreferences.Editor sp = context.getSharedPreferences(Control.account, Context.MODE_PRIVATE).edit();
        sp.putBoolean(acc, false).commit();
        sp = context.getSharedPreferences(Control.accountName, Context.MODE_PRIVATE).edit();
        sp.putString(acc, "").commit();
        try {
            CoCInterfaces.removeAccountData(context, acc);
        } catch (InterruptedException e) {
            Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public static void enable(Context context, String acc){
        SharedPreferences.Editor sp = context.getSharedPreferences(Control.account, Context.MODE_PRIVATE).edit();
        sp.putBoolean(acc, true).commit();
    }
    public static void setAccountName(Context context, String acc, String name){
        SharedPreferences.Editor sp = context.getSharedPreferences(Control.accountName, Context.MODE_PRIVATE).edit();
        sp.putString(acc, name).commit();
    }
    public static String getAccountName(Context context, String acc){
        SharedPreferences sp = context.getSharedPreferences(Control.accountName, Context.MODE_PRIVATE);
        return sp.getString(acc, "");
    }


    public static void run(Context context, String acc) throws Exception {
        CoCInterfaces.run(context, acc);
    }
    public static boolean isInstalledCoC(Context context)  {
        try {
            return CoCInterfaces.isInstalledCoC(context);
        } catch (Exception e) {
            Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
        return false;
    }



    //Widget Configture
    public static void addWidget(Context context, int widgetID, String acc){
        SharedPreferences.Editor sp = context.getSharedPreferences(Control.widget, Context.MODE_PRIVATE).edit();
        sp.putString(widgetID+"",acc).commit();
    }
    public static String getWidget(Context context, int widgetID){
        SharedPreferences sp = context.getSharedPreferences(Control.widget, Context.MODE_PRIVATE);
        return sp.getString(widgetID+"", "");
    }
    public static void deleteWidget(Context context, int widgetID){
        SharedPreferences.Editor sp = context.getSharedPreferences(Control.widget, Context.MODE_PRIVATE).edit();
        sp.remove(widgetID+"").commit();
    }

}
