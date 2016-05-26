package Model;

import android.content.Context;
import android.util.Log;

import com.example.castiel.clashofclanmulti.MainActivity;
import com.example.castiel.clashofclanmulti.R;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by castiel on 3/23/16.
 */
public class CoCInterfaces {
    private static String infomation="";
    private static void getInfo(Context context) throws IOException {
        Scanner output = Shell.SuExecute1("ls -la /data/data/");
        String tmp;
        while(output.hasNextLine()){
            tmp = output.nextLine();
            if ( tmp.indexOf(context.getString(R.string.CoC_domain_name)) != -1 ){
                CoCInterfaces.infomation=tmp;
                return;
            }
        }

    }
    public static boolean isInstalledCoC(Context context) throws IOException {
        CoCInterfaces.getInfo(context);
        if ( infomation.length() == 0 )
            return false;
        return true;
    }

    public static boolean isLinked(Context context) throws IOException {
        CoCInterfaces.getInfo(context);
        if ( infomation.length() != 0 && infomation.indexOf("->")!=-1 )
            return true;
        return false;

    }

    public static boolean isBackuped(Context context) throws IOException {
        Scanner output = Shell.SuExecute1("ls -la /data/data/");
        while(output.hasNextLine()){
            if ( output.nextLine().indexOf(context.getString(R.string.CoC_backup_folder)) != -1 ){
                return true;
            }
        }
        return false;
    }

    public static void ClearData(Context context) throws IOException {
        Shell.SuExecute2("pm clear " + context.getString(R.string.CoC_domain_name));
    }
    public static void Forcestop(Context context) throws IOException {
        Shell.SuExecute2("am force-stop "+context.getString(R.string.CoC_domain_name));
    }

    public static void Backup(Context context) throws IOException {
        Shell.SuExecute2("cp -rp" + " " + "/data/data/" + context.getString(R.string.CoC_domain_name) + " " + "/data/data/" + context.getString(R.string.CoC_backup_folder));
    }
    public static void Restore(Context context) throws IOException {
        if ( isLinked(context) ){
            Shell.SuExecute2("rm /data/data/"+context.getString(R.string.CoC_domain_name));
            Shell.SuExecute2("mv /data/data/"+context.getString(R.string.CoC_backup_folder) + " " + "/data/data/" + context.getString(R.string.CoC_domain_name));
            Shell.SuExecute2("rm -r /data/data/"+context.getString(R.string.CoC_data_multi_1));
            Shell.SuExecute2("rm -r /data/data/"+context.getString(R.string.CoC_data_multi_2));
        }
    }

    public static void CreateMultiData(Context context) throws IOException {
        if ( !isBackuped(context) )
            Backup(context);
        Shell.SuExecute2("cp -rp /data/data/"+context.getString(R.string.CoC_backup_folder)+" "+"/data/data/"+context.getString(R.string.CoC_data_multi_1));
        Shell.SuExecute2("cp -rp /data/data/"+context.getString(R.string.CoC_backup_folder)+" "+"/data/data/"+context.getString(R.string.CoC_data_multi_2));
        Shell.SuExecute2("rm -r /data/data/"+context.getString(R.string.CoC_domain_name));
        Shell.SuExecute2("ln -s /data/data/"+context.getString(R.string.CoC_data_multi_1)+" "+"/data/data/"+context.getString(R.string.CoC_domain_name));
    }

    public static void UseDataMulti1(Context context) throws Exception {
        if ( !isLinked(context) )
            throw new Exception("Data hasn't been linked!");
        else {
            Shell.SuExecute2("rm /data/data/"+context.getString(R.string.CoC_domain_name));
            Shell.SuExecute2("ln -s /data/data/"+context.getString(R.string.CoC_data_multi_1)+" "+"/data/data/"+context.getString(R.string.CoC_domain_name));
        }
    }

    public static void UseDataMulti2(Context context) throws Exception {
        if ( !isLinked(context) )
            throw new Exception("Data hasn't been linked!");
        else {
            Shell.SuExecute2("rm /data/data/"+context.getString(R.string.CoC_domain_name));
            Shell.SuExecute2("ln -s /data/data/"+context.getString(R.string.CoC_data_multi_2)+" "+"/data/data/"+context.getString(R.string.CoC_domain_name));
        }
    }

    public static void startCoC() throws IOException {
        Runtime.getRuntime().exec("am start -n com.supercell.clashofclans/com.supercell.clashofclans.GameApp");
    }

}
