package com.example.castiel.clashofclanmulti.clashofclanmulti;

import android.content.Context;
import android.content.pm.PackageManager;

import java.io.IOException;
import java.util.Scanner;

/**
 * Created by castiel on 3/23/16.
 */
public class CoCInterfaces {

    private static String infomation="";

    private static String appDomain = "com.supercell.clashofclans";

    private static void getInfo(Context context) throws IOException, InterruptedException {
        Scanner output = Shell.SuExecute1("ls -la /data/data/" + appDomain + "/" );
        String tmp = "";
        while(output.hasNextLine()){
            tmp = tmp + "\n" + output.nextLine();
        }
        if ( tmp.indexOf("shared_prefs") >= 0 ){
            CoCInterfaces.infomation=tmp;
        }
    }

    private static boolean isLinked(Context context) throws IOException, InterruptedException {
        CoCInterfaces.getInfo(context);
        if ( infomation.length() != 0 && infomation.indexOf("->")!=-1 )
            return true;
        return false;
    }

    private static void Link(Context context, String acc ) throws IOException, InterruptedException {
        if ( !CoCInterfaces.isBackuped(context) )
            CoCInterfaces.Backup(context);
        Shell.SuExecute2("rm -r /data/data/"+appDomain+"/shared_prefs");
        Shell.SuExecute2("ln -s /data/data/"+appDomain+"/" + acc + " " + "/data/data/"+appDomain+"/shared_prefs");
    }

    private static boolean isBackuped(Context context) throws IOException, InterruptedException {
        CoCInterfaces.getInfo(context);
        if ( infomation.length() != 0 && infomation.indexOf(Control.backupAccount)!=-1 )
            return true;
        return false;
    }
    private static void Backup(Context context) throws IOException, InterruptedException {
        CoCInterfaces.ClearData(context);
        Shell.SuExecute2("cp -rp" + " " + "/data/data/" + appDomain + "/shared_prefs" + " " + "/data/data/" + appDomain + "/" + Control.backupAccount );
        Shell.SuExecute2("rm -r /data/data/"+appDomain+"/shared_prefs");
    }

    private static void startCoC() throws IOException {
        Runtime.getRuntime().exec("am start -n com.supercell.clashofclans/com.supercell.clashofclans.GameApp");
    }

    private static void createFolder(Context context, String acc) throws IOException, InterruptedException {
        if ( !CoCInterfaces.isBackuped(context)){
            CoCInterfaces.Backup(context);
        }
        //Shell.SuExecute2("rm /data/data/"+appDomain+"/"+ Control.backupAccount+"/*");
        //Shell.SuExecute2("cp -rp" + " " + "/data/data/" + appDomain + "/"+ Control.backupAccount + " " + "/data/data/" + appDomain + "/" + acc );
        Shell.SuExecute2("mkdir" + " " + "/data/data/" + appDomain + "/"+ acc );
        Shell.SuExecute2("chmod 777" + " " + "/data/data/" + appDomain + "/"+ acc );
    }

    private static boolean isFolderExists(Context context, String acc) throws IOException, InterruptedException {
        CoCInterfaces.getInfo(context);
        if ( infomation.length() != 0 && infomation.indexOf(acc)!=-1 )
            return true;
        return false;
    }



    /// PUBILC METHOD
    public static boolean isInstalledCoC(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(CoCInterfaces.appDomain, PackageManager.GET_ACTIVITIES);
            return true;
        }
        catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static void ClearData(Context context) throws IOException, InterruptedException {
        Shell.SuExecute2("rm /data/data/"+appDomain+"/shared_prefs/*");
    }
    public static void Forcestop(Context context) throws IOException, InterruptedException {
        Shell.SuExecute2("am force-stop "+appDomain);
    }


    public static void Restore(Context context) throws IOException, InterruptedException {
        if ( isBackuped(context) ){
            Shell.SuExecute2("rm -r /data/data/"+appDomain+"/shared_prefs");
            Shell.SuExecute2("ln -s /data/data/"+appDomain+"/" + Control.backupAccount + " " + "/data/data/"+appDomain+"/shared_prefs");
            CoCInterfaces.ClearData(context);
        }
    }

    public static void removeAccountData(Context context, String acc) throws IOException, InterruptedException {
        CoCInterfaces.Link(context, acc);
        CoCInterfaces.ClearData(context);
        String[] accounts = Control.getListAccountsEnabled(context);
        if ( accounts == null )
            CoCInterfaces.Link(context, Control.backupAccount);
        else
            CoCInterfaces.Link(context, accounts[0]);
    }

    public static void run(Context context, String acc) throws Exception{
        if ( !CoCInterfaces.isBackuped(context)){
            CoCInterfaces.Backup(context);
        }


        if ( !CoCInterfaces.isFolderExists(context, acc) )
            CoCInterfaces.createFolder(context,acc);

        CoCInterfaces.Forcestop(context);

        CoCInterfaces.Link(context,acc);

        CoCInterfaces.startCoC();

    }



}
