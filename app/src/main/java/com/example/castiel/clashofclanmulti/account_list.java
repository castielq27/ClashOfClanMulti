package com.example.castiel.clashofclanmulti;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import com.castiel.clashofclanmulti.R;
import com.example.castiel.clashofclanmulti.clashofclanmulti.Control;

/**
 * Created by castiel on 7/5/16.
 */
public class account_list extends BaseAdapter {

    private String[] accounts = new String[0];
    private String[] accountNames = new String[0];
    private Activity activity = null;

    public account_list(Activity activity){
        this.activity = activity;
        this.accounts = Control.getListAccountsEnabled(activity);
        if ( this.accounts != null){
            this.accountNames = new String[this.accounts.length];
            for ( int i = 0; i<accounts.length; i ++ ){
                this.accountNames[i] = Control.getAccountName(activity, accounts[i]);
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
        LayoutInflater inflater = (LayoutInflater) this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.row_view, viewGroup, false);
        TextView tv = (TextView) row.findViewById(R.id.row_view_accountName);
        tv.setText(this.accountNames[i]);
        Button run = (Button) row.findViewById(R.id.row_view_run);
        Button rename = (Button) row.findViewById(R.id.row_view_rename);
        Button delete = (Button) row.findViewById(R.id.row_view_delete);
        run.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //Control.run(view.getContext(), account_list.this.accounts[i]);
                    Intent intent = new Intent(view.getContext(), Load_CoC.class);
                    intent.putExtra(Control.account,account_list.this.accounts[i]);
                    view.getContext().startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(view.getContext(), e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
        rename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Add_or_Rename.class);
                intent.putExtra(Control.account, account_list.this.accounts[i]);
                intent.putExtra(Add_or_Rename.Label, Add_or_Rename.Rename);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().startActivity(intent);
                account_list.this.updateAccountList();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                new AlertDialog.Builder(account_list.this.activity)
                        .setTitle("Delete account")
                        .setMessage("Are you sure you want to delete [" + account_list.this.accountNames[i] +"] ?" )
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    Control.disable(activity, account_list.this.accounts[i]);
                                } catch (IOException e) {
                                    Toast.makeText(view.getContext(), e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                }
                                account_list.this.updateAccountList();
                            }
                        }).setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
        return row;
    }

    public void updateAccountList(){
        this.accounts = Control.getListAccountsEnabled(activity);
        if ( this.accounts != null ){
            this.accountNames = new String[this.accounts.length];
            for ( int i = 0; i<accounts.length; i ++ ){
                this.accountNames[i] = Control.getAccountName(activity, accounts[i]);
            }
            this.notifyDataSetChanged();
        }

    }
}
