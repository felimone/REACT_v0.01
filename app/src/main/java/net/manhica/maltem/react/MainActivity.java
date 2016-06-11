package net.manhica.maltem.react;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import net.manhica.maltem.react.service.ServiceResponse;
import net.manhica.maltem.react.service.SyncEntities;

public class MainActivity extends AppCompatActivity implements ServiceResponse{

    private Button bt_sync;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_sync = (Button) findViewById(R.id.bt_main_sync_db);
        progressDialog = new ProgressDialog(this);

        bt_sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                syncDatabase();
            }
        });
    }

    public void syncDatabase(){
        new SyncEntities(getApplicationContext(), 1, this, progressDialog).execute();
    }

    @Override
    public String serviceFinish(String result) {
        return null;
    }
}
