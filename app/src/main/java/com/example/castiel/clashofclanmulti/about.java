package com.example.castiel.clashofclanmulti;

import android.app.Activity;
import android.os.Bundle;

import com.castiel.clashofclanmulti.R;

/**
 * Created by castiel on 7/7/16.
 */
public class about extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);


    }

    @Override
    protected void onPause() {
        super.onPause();
        this.finish();
    }
}
