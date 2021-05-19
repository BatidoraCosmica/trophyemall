package com.example.trophyemall.ui;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.example.trophyemall.R;
import com.example.trophyemall.model.Post;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission_group.CAMERA;

public class CreateActivity extends AppCompatActivity {
    public static final String EXTRA_POST = "com.example.trophyemall.ui.CreateActivity.Datos";
    private static final int STATUS_CODE_SELECCION_IMAGEN = 300;
    private static final int MY_PERMISSIONS = 100;

    ConstraintLayout cl;
    Spinner spType;
    EditText etDesc;
    ImageView img;
    Button btnSave;
    Button btnCancel;
    private Uri uriFoto = null;
    private int id = -1;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        cl = findViewById(R.id.cl);
        spType = findViewById(R.id.spCreateType);
        etDesc = findViewById(R.id.etCreateDesc);
        img = findViewById(R.id.ivCreateImage);
        btnSave = findViewById(R.id.btnCreateAccept);
        btnCancel = findViewById(R.id.btnCreateCancel);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser fbUser = auth.getCurrentUser();
        String User = fbUser.getDisplayName();

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.valoresSpinner, android.R.layout.simple_spinner_item);
        spType.setAdapter(arrayAdapter);
        spType.setSelection(arrayAdapter.getPosition("5"));

        img.setOnClickListener(l -> {
            if (noNecesarioSolicitarPermisos()) {
                muestraOpcionesImagen();
            }
        });

        btnSave.setOnClickListener(l -> {
            try {
                Post post = new Post(User, spType.getSelectedItem().toString(), etDesc.getText().toString());

                if (uriFoto != null) {
                    post.setFotoUri(uriFoto.toString());
                }
                if (id != -1) post.setId(id);
                setResult(RESULT_OK, getIntent().putExtra(EXTRA_POST, post));
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        btnCancel.setOnClickListener(l -> {
            setResult(RESULT_CANCELED);
            finish();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case STATUS_CODE_SELECCION_IMAGEN:
                    uriFoto = data.getData();
                    muestraFoto();
                    break;
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean noNecesarioSolicitarPermisos() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;
        if ((checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) && (checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED))
            return true;
        if (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE) || shouldShowRequestPermissionRationale(CAMERA)) {
            Snackbar.make(cl, "Recuperar la foto requiere permisos...", Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, v -> requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MY_PERMISSIONS));
        } else {
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MY_PERMISSIONS);
        }

        return false;
    }

    private void elegirGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).setType("image/*");
        startActivityForResult(intent.createChooser(intent, "Seleccione imagen"), STATUS_CODE_SELECCION_IMAGEN);
    }


    private void muestraFoto() {
        Glide.with(this).load(uriFoto).into(img);
    }

    private void muestraOpcionesImagen() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(new String[]{"Tomar foto",
                "Elegir de la galería",
                getString(android.R.string.cancel)}, (dialog, which) -> { if (which == 1) elegirGaleria(); dialog.dismiss(); }).show();
    }
}