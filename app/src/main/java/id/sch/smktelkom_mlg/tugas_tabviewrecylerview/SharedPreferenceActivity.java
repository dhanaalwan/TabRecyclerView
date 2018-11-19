package id.sch.smktelkom_mlg.tugas_tabviewrecylerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SharedPreferenceActivity extends AppCompatActivity {

    EditText edt_nama, edt_email, edt_alamat;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preference);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("User Profile");

        edt_nama = findViewById(R.id.edt_nama);
        edt_email = findViewById(R.id.edt_email);
        edt_alamat = findViewById(R.id.edt_alamat);
        button = findViewById(R.id.button);

        edt_nama.setText(Preference.getKeyNama(getApplicationContext()));
        edt_email.setText(Preference.getKeyEmail(getApplicationContext()));
        edt_alamat.setText(Preference.getKeyAlamat(getApplicationContext()));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (button.getText().equals("save")) {
                    Preference.setKeyNama(getApplicationContext(), edt_nama.getText().toString());
                    Preference.setKeyEmail(getApplicationContext(), edt_email.getText().toString());
                    Preference.setKeyAlamat(getApplicationContext(), edt_alamat.getText().toString());

                    button.setText("edit");
                    edt_nama.setEnabled(false);
                    edt_email.setEnabled(false);
                    edt_alamat.setEnabled(false);
                } else {
                    edt_nama.setEnabled(true);
                    edt_email.setEnabled(true);
                    edt_alamat.setEnabled(true);
                    button.setText("save");
                }
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
