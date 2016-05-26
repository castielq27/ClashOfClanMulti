package Model;

import android.util.Log;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by castiel on 3/23/16.
 */
public class Shell {

    private static Process p = null;
    private static DataInputStream in;
    private static DataOutputStream out;

    private static void init() throws IOException {
        Shell.p = Runtime.getRuntime().exec("su");
        Shell.in = new DataInputStream( p.getInputStream());
        Shell.out = new DataOutputStream(p.getOutputStream());
    }
    public static Scanner SuExecute1(String cmd) throws IOException {
        if ( Shell.p == null || Shell.in == null || Shell.out == null ){
            Shell.init();
        }
        Shell.out.writeChars(cmd+"\n");
        StringBuilder data = new StringBuilder();
        int count = 0;
        byte[] buffer = new byte[512];
        Log.d("com.example.castiel.clashofclanmulti",cmd);
        while(true){
            count = in.read(buffer);// count == size of buffer;
            String temp = new String(buffer);
            data.append(temp);
            //Log.d("com.example.castiel.clashofclanmulti",count+"<-------------");
            if ( count < 512 ){// count == size data < max size buffer 512 --> la luot truyen du lieu cuoi cung !!!!!
                break;
            }
        }
        Log.d("com.example.castiel.clashofclanmulti",data.toString());
        return new Scanner(data.toString());
    }

    public static void SuExecute2(String cmd) throws IOException {
        if ( Shell.p == null || Shell.in == null || Shell.out == null ){
            Shell.init();
        }
        Shell.out.writeChars(cmd+"\n");
        //Log.d("com.example.castiel.clashofclanmulti","void"+cmd);
        return;
    }


}
