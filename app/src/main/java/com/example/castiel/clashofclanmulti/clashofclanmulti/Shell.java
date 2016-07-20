package com.example.castiel.clashofclanmulti.clashofclanmulti;

import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by castiel on 3/23/16.
 */
public class Shell {

    /*private static Process p = null;
    private static DataInputStream in;
    private static DataOutputStream out;

    private static void init() throws IOException {
        Shell.p = Runtime.getRuntime().exec("su");
        Shell.in = new DataInputStream( p.getInputStream());
        Shell.out = new DataOutputStream(p.getOutputStream());
    }
    private static void destroy() throws IOException {

        Shell.in.close();
        Shell.out.close();
        Shell.p.destroy();
    }*/

    public static Scanner SuExecute1(String cmd) throws IOException, InterruptedException {

        //if ( Shell.p == null || Shell.in == null || Shell.out == null ){
            //Shell.init();
        //}
        Process p = Runtime.getRuntime().exec("su");
        DataOutputStream out = new DataOutputStream(p.getOutputStream());
        DataInputStream in = new DataInputStream(p.getInputStream());
        //Shell.out.writeChars(cmd+"\n");
        out.writeChars(cmd+"\n");

        StringBuilder data = new StringBuilder();
        int count = 0;
        byte[] buffer = new byte[512];

        Log.d(Shell.class.getName(),"SuExecute1 : " + cmd);

        while(true){
            count = in.read(buffer);// count == size of buffer;
            String temp = new String(buffer);
            data.append(temp);
            //Log.d("com.example.castiel.com.example.castiel.clashofclanmulti.clashofclanmulti",count+"<-------------");
            if ( count < 512 ){// count == size data < max size buffer 512 --> la luot truyen du lieu cuoi cung !!!!!
                break;
            }
        }
        Log.d(Shell.class.getName(),data.toString());


        out.writeChars("exit\n");
        out.flush();
        p.waitFor();

        out.close();
        in.close();
        p.destroy();
        return new Scanner(data.toString());
    }

    public static void SuExecute2(String cmd) throws IOException, InterruptedException {
        //if ( Shell.p == null || Shell.in == null || Shell.out == null ){
            //Shell.init();
       // }
        Process p = Runtime.getRuntime().exec("su");
        DataOutputStream out = new DataOutputStream(p.getOutputStream());
        DataInputStream in = new DataInputStream(p.getInputStream());

        //Shell.out.writeChars(cmd+"\n");
        out.writeChars(cmd+"\n");
        Log.d(Shell.class.getName(), "SuExecute2 : "+cmd);
        out.writeChars("exit\n");
        out.flush();

        p.waitFor();

        out.close();
        in.close();
        p.destroy();
        return;
    }

    public static boolean rootCheck(){

        String buildTags = android.os.Build.TAGS;
        if (buildTags != null && buildTags.contains("test-keys")) {
            return true;
        }
        File f = new File("/system/app/Superuser.apk");
        if ( f.exists() ){
            return true;
        }

        return Shell.isLocatedSU();
        //return true;
    }
    private static boolean  isLocatedSU(){
        Process p = null;
        try{
            if ( new Scanner((p = Runtime.getRuntime().exec("which su")).getInputStream()).hasNext() )
                return true;
            p.destroy();
            if ( new Scanner((p = Runtime.getRuntime().exec("/system/bin/which su")).getInputStream()).hasNext() )
                return true;
            p.destroy();
            if ( new Scanner((p = Runtime.getRuntime().exec("/system/xbin/which su")).getInputStream()).hasNext() )
                return true;
            p.destroy();
        } catch (IOException e) {

        } finally {
            if ( p != null )
                p.destroy();
            return false;
        }

        //return true;
    }


}
