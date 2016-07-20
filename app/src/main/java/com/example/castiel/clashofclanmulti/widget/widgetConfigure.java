package com.example.castiel.clashofclanmulti.widget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import clashofclanmulti.Control;

/**
 * Created by castiel on 7/5/16.
 */
public class widgetConfigure extends Activity {

    private class widgetTargetSelection extends BaseAdapter{

        String[] accounts = null;
        String[] accountNames = null;
        Context context;
        public widgetTargetSelection(Context context){
            this.context = context;
            this.accounts = Control.getListAccountsEnabled(context);
            if ( this.accounts != null ){
                this.accountNames = new String[this.accounts.length];
                for ( int i = 0; i<accounts.length; i ++ ){
                    this.accountNames[i] = Control.getAccountName(context, accounts[i]);
                }
            }

        }

        @Override
        public int getCount() {
            if ( accounts != null )
                return accounts.length;
            else
                return 0;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(com.example.castiel.clashofclanmulti.R.layout.select_widget_target_row_view, viewGroup, false);
            TextView tv = (TextView) row.findViewById(com.example.castiel.clashofclanmulti.R.id.select_widget_target_row_view_accountName);
            tv.setText(this.accountNames[i]);
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Control.addWidget(view.getContext(), widgetConfigure.this.mAppWidgetId, accounts[i]);
                    Log.d(widgetConfigure.this.getClass().getName(),mAppWidgetId + ":" + accounts[i]);
                    widgetProvider.onUpdateByConfig(view.getContext(), AppWidgetManager.getInstance(view.getContext()),
                            widgetConfigure.this.mAppWidgetId, accounts[i]);

                    Intent resultValue = new Intent();
                    resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
                    setResult(RESULT_OK, resultValue);
                    finish();
                }
            });
            return row;
        }
    }



    private static String sharedPreferencesKey = "WIDGET";
    private static int mode = Context.MODE_PRIVATE;

    private  int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    private ListView listView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setResult(Activity.RESULT_CANCELED);

        Intent intent = this.getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }

        this.setContentView(com.example.castiel.clashofclanmulti.R.layout.widget_config);

        this.listView = (ListView) this.findViewById(com.example.castiel.clashofclanmulti.R.id.widgetConfig_listView);
        this.listView.setAdapter(new widgetTargetSelection(this.getApplicationContext()));
    }

}
