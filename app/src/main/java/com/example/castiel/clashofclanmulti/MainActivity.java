package com.example.castiel.clashofclanmulti;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.IOException;
import java.util.Scanner;

import Model.CoCInterfaces;
import Model.Shell;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void khoitao(View view){
        try {
            if ( CoCInterfaces.isLinked(this.getApplicationContext()) ){
                Toast.makeText(this.getApplicationContext(),"Da link data\n Du lieu da dc khoi tao!",Toast.LENGTH_LONG).show();
                return;
            }
            else{
                CoCInterfaces.Forcestop(this.getApplicationContext());
                CoCInterfaces.ClearData(this.getApplicationContext());
                CoCInterfaces.CreateMultiData(this.getApplicationContext());
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void phuchoi(View view){
        try {
            if ( !CoCInterfaces.isBackuped(this.getApplicationContext()) ){
                Toast.makeText(this.getApplicationContext(),"Du lieu chua dc backup !!!!!",Toast.LENGTH_LONG).show();
                return;
            } else {
                CoCInterfaces.Forcestop(this.getApplicationContext());
                CoCInterfaces.Restore(this.getApplicationContext());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void XoaDuLieu1(View view){
        try {
            CoCInterfaces.Forcestop(this.getApplicationContext());
            CoCInterfaces.UseDataMulti1(this.getApplicationContext());
            CoCInterfaces.ClearData(this.getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this.getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    public void XoaDuLieu2(View view){
        try {
            CoCInterfaces.Forcestop(this.getApplicationContext());
            CoCInterfaces.UseDataMulti2(this.getApplicationContext());
            CoCInterfaces.ClearData(this.getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this.getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    public void thoat(View view){
        this.finish();
    }
    public void test(View view){
        //Toast.makeText(this.getApplicationContext(),"Disabled!!!!",Toast.LENGTH_SHORT).show();
        try {
            Scanner output = Shell.SuExecute1("ls -la /data/data/");
            Toast.makeText(this.getApplicationContext(),"Chay!",Toast.LENGTH_SHORT).show();
            while(output.hasNextLine()){
                String tmp = output.nextLine();
                if ( tmp.indexOf("clashofclans") != -1 ){
                    Toast.makeText(this.getApplicationContext(),"--->"+tmp,Toast.LENGTH_SHORT).show();

                } else
                    Toast.makeText(this.getApplicationContext(),tmp,Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(this.getApplicationContext(),"KetThuc",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onPause(){
        super.onPause();
        this.finish();
    }
}
