package com.example.castiel.clashofclanmulti;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.castiel.clashofclanmulti.R;
import com.example.castiel.clashofclanmulti.clashofclanmulti.Control;
import com.example.castiel.clashofclanmulti.clashofclanmulti.Shell;

public class MainActivity extends AppCompatActivity {

    private ListView listView = null;
    private Button add = null;
    private account_list account_list = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.listView = (ListView) this.findViewById(R.id.activity_main_listView);
        this.add = (Button)this.findViewById(R.id.activity_main_Add);

        this.account_list = new account_list(this);
        this.listView.setAdapter(this.account_list);

        this.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String newAccount = Control.getFirstDisableAccount(view.getContext());
                if ( newAccount == null ){
                    Toast.makeText(view.getContext(), "Limit 15 accounts", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    Intent intent = new Intent(view.getContext(), Add_or_Rename.class);
                    intent.putExtra(Control.account, newAccount);
                    intent.putExtra(Add_or_Rename.Label, Add_or_Rename.Add);
                    view.getContext().startActivity(intent);
                }

            }
        });




        if (!Shell.rootCheck()){
            new AlertDialog.Builder(this)
                    .setTitle("ROOT REQUIRED!")
                    .setMessage("You need a rooted device to run this application.\nIf you don't know what root is, please search for \"How to root android\" in the Internet." )
                    .setPositiveButton( android.R.string.ok, null )
                    .setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            MainActivity.this.finish();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .show();
        }
        if ( !Control.isInstalledCoC(this) ){
            new AlertDialog.Builder(this)
                    .setTitle("ClashOfClans not found!")
                    .setMessage("Are you sure ClashOfClans has been installed?" )
                    .setPositiveButton( android.R.string.ok, null )
                    .setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            MainActivity.this.finish();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .show();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if ( this.account_list != null ){
            this.account_list.updateAccountList();
        }
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
        if (id == R.id.action_about ){
            Intent i = new Intent(this, about.class);
            this.startActivity(i);
        }
        if (id == R.id.action_exit) {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }


}
