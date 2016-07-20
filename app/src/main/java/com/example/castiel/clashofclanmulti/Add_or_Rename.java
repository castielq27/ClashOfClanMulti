package com.example.castiel.clashofclanmulti;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.castiel.clashofclanmulti.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import com.example.castiel.clashofclanmulti.clashofclanmulti.Control;

/**
 * Created by castiel on 7/5/16.
 */
public class Add_or_Rename extends Activity {

    public static String Add = "Add";
    public static String Rename = "Rename";
    public static String Label = "Label";


    private String labelButton = "";
    Button add_or_rename = null;
    Button cancel = null;
    EditText accountName = null;

    private String account = "";



    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.add_or_rename);

        MobileAds.initialize(this, this.getString(R.string.Ads_Banner_ID));
        mAdView = (AdView) findViewById(R.id.ad_view);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);


        this.accountName = (EditText) this.findViewById(R.id.add_editText_accountName);
        this.add_or_rename = (Button) this.findViewById(R.id.add_btn_add_or_rename);
        this.cancel = (Button) this.findViewById(R.id.add_btn_cancel);

        Intent intent = this.getIntent();
        this.account = intent.getStringExtra(Control.account);
        this.labelButton = intent.getStringExtra(Add_or_Rename.Label);

        if ( this.account == null || this.account.length() <= 0 || this.labelButton == null || this.labelButton.length() <= 0 ){
            this.finish();
        }


        this.add_or_rename.setText( this.labelButton );
        this.accountName.setText( Control.getAccountName(this.getApplicationContext(), this.account));


        this.add_or_rename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( Add_or_Rename.this.accountName.getText().toString().length() <= 0 ){
                    Toast.makeText(view.getContext(),"Name empty!",Toast.LENGTH_LONG).show();
                    return;
                } else {
                    Control.enable(view.getContext(),Add_or_Rename.this.account);
                    Control.setAccountName(view.getContext(), Add_or_Rename.this.account, Add_or_Rename.this.accountName.getText().toString());
                    Add_or_Rename.this.finish();
                }
            }
        });
        this.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Add_or_Rename.this.finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if ( this.mAdView != null ){
            this.mAdView.destroy();
        }
    }
}
