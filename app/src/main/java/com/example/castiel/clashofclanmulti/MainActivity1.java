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

import Model.CoCInterfaces;

public class MainActivity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load);
        try {
            if ( CoCInterfaces.isLinked(this.getApplicationContext()) ){
                CoCInterfaces.Forcestop(this.getApplicationContext());
                CoCInterfaces.UseDataMulti1(this.getApplicationContext());
                CoCInterfaces.startCoC();
            } else
                Toast.makeText(this.getApplicationContext(),"Chua link",Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.finish();
        }

    }

}
