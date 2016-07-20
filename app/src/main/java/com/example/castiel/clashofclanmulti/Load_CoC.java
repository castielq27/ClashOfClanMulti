package com.example.castiel.clashofclanmulti;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import Model.CoCInterfaces;
import Model.Constant;

public class Load_CoC extends AppCompatActivity {

    private TextView accountDisplay = null;
    private String accountName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load_coc);
        Intent data = this.getIntent();
        if ( data == null )
            this.finish();

        this.accountName = data.getStringExtra(Constant.acc);
        if ( this.accountName == null )
            this.finish();

        this.accountDisplay = (TextView) this.findViewById(R.id.load_coc_AccountName);
        this.accountDisplay.setText(this.accountName);

        try {
            if ( CoCInterfaces.isInstalledCoC(this.getApplicationContext())){
                CoCInterfaces.run(this.getApplicationContext(), this.accountName);
            } else
                Toast.makeText(this.getApplicationContext(),"Chua cai dat COC",Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.finish();
        }

    }

}
