package com.example.castiel.clashofclanmulti;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.castiel.clashofclanmulti.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import com.example.castiel.clashofclanmulti.clashofclanmulti.Control;

public class Load_CoC extends AppCompatActivity {

    private TextView accountDisplay = null;
    private String account = "";

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load_coc);


        MobileAds.initialize(this, this.getString(R.string.Ads_Banner_ID));
        mAdView = (AdView) findViewById(R.id.ad_view);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);


        Intent intent = this.getIntent();
        //this.account = intent.getStringExtra(Control.account);
        this.account = intent.getExtras().getString(Control.account);
        if ( this.account == null )
            this.finish();

        this.accountDisplay = (TextView) this.findViewById(R.id.load_coc_AccountName);
        this.accountDisplay.setText(Control.getAccountName(this.getApplicationContext(),account));
        //Toast.makeText(this.getApplicationContext(),account+":"+Control.getAccountName(this.getApplicationContext(),account),Toast.LENGTH_LONG).show();
        Log.d(Load_CoC.class.getName(), account + ":" + Control.getAccountName(this.getApplicationContext(),account));

        try {
            if ( Control.isInstalledCoC(this.getApplicationContext())){
                Control.run(this.getApplicationContext(), this.account);
            } else {
                //Toast.makeText(this.getApplicationContext(),"Chua cai dat COC",Toast.LENGTH_LONG).show();
                //this.finish();
                if ( !Control.isInstalledCoC(this) ){
                    new AlertDialog.Builder(this)
                            .setTitle("ClashOfClans not found!")
                            .setMessage("Are you sure ClashOfClans has been installed?" )
                            .setPositiveButton( android.R.string.ok, null )
                            .setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialogInterface) {
                                    Load_CoC.this.finish();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.finish();
        }


    }

    @Override
    protected void onPause() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onPause();
        this.finish();
    }
}
