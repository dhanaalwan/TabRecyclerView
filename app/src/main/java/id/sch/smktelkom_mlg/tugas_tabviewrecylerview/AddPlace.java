package id.sch.smktelkom_mlg.tugas_tabviewrecylerview;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import id.sch.smktelkom_mlg.tugas_tabviewrecylerview.model.Places;
import id.sch.smktelkom_mlg.tugas_tabviewrecylerview.realm.RealmHelperFavorite;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class AddPlace extends AppCompatActivity {
    public static final int REQUEST_IMAGE = 100;
    public static final int REQUEST_PERMISSION = 200;
    private final String TAG = "AddPlace";
    EditText et_nama, et_latitude, et_longitude, et_alamat, et_image_name;
    ImageView image;
    Button button, take_picture;
    Realm realm;
    RealmHelperFavorite realmHelper;
    private String imageFilePath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Point");

        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(configuration);
        realmHelper = new RealmHelperFavorite(realm);

        et_nama = findViewById(R.id.et_nama);
        et_latitude = findViewById(R.id.et_latitude);
        et_longitude = findViewById(R.id.et_longitude);
        et_alamat = findViewById(R.id.et_alamat);
        et_image_name = findViewById(R.id.et_image_name);
        image = findViewById(R.id.image);
        take_picture = findViewById(R.id.button_take_picture);
        button = findViewById(R.id.button);

        et_latitude.setText(String.valueOf(getIntent().getExtras().getDouble("LATITUDE")));
        et_longitude.setText(String.valueOf(getIntent().getExtras().getDouble("LONGITUDE")));

        take_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(AddPlace.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                        PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(AddPlace.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            REQUEST_PERMISSION);
                    Toast.makeText(getApplicationContext(), "Tidak Bisa :(", Toast.LENGTH_SHORT).show();
                } else {
                    openCameraIntent();
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Places places = new Places();
                    places.setNama(et_nama.getText().toString());
                    places.setLatitude(Double.parseDouble(et_latitude.getText().toString()));
                    places.setLongitude(Double.parseDouble(et_longitude.getText().toString()));
                    places.setAlamat(et_alamat.getText().toString().isEmpty() ? "" : et_alamat.getText().toString().trim());
                    places.setImage(imageFilePath);

                    realmHelper = new RealmHelperFavorite(realm);
                    realmHelper.save_place(places);
                    Toast.makeText(getApplicationContext(), "Point Added", Toast.LENGTH_SHORT).show();
                    finish();
                } catch (Exception e) {
                    //Toast.makeText(getApplicationContext(), "Meal already added before", Toast.LENGTH_SHORT).show();
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

    private void openCameraIntent() {
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(getPackageManager()) != null) {

            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            Uri photoUri = FileProvider.getUriForFile(this, getPackageName() + ".provider", photoFile);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(pictureIntent, REQUEST_IMAGE);
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        imageFilePath = image.getAbsolutePath();

        return image;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Thanks for granting Permission", Toast.LENGTH_SHORT).show();
                openCameraIntent();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                image.setVisibility(View.VISIBLE);
                image.setImageURI(Uri.parse(imageFilePath));
                et_image_name.setText(imageFilePath.substring(imageFilePath.lastIndexOf("/") + 1));
                //edt_image_name.setText(getFileName(Uri.parse(imageFilePath)));
                Log.d("FilePath", imageFilePath);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "You cancelled the operation", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String getFileName(Uri uri) {
        String path = getRealPathFromURI(AddPlace.this, uri);
        String filename = path.substring(path.lastIndexOf("/") + 1);
        String fileWOExtension;
        if (filename.indexOf(".") > 0) {
            fileWOExtension = filename.substring(0, filename.lastIndexOf("."));
        } else {
            fileWOExtension = filename;
        }
        Log.d(TAG, "Real Path: " + path);
        Log.d(TAG, "Filename With Extension: " + filename);
        Log.d(TAG, "File Without Extension: " + fileWOExtension);

        return fileWOExtension;
    }

    private String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } catch (Exception e) {
            Log.e(TAG, "getRealPathFromURI Exception : " + e.toString());
            return "";
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}