package ua.tarastom.gufi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.UUID;

import ua.tarastom.gufi.model.Service;

public class EditProfileActivity extends AppCompatActivity {
    private final String nameCollection = "services";
    private String idServices;
    private EditText editTextEditName;
    private EditText editTextEditSurname;
    private EditText editTextEditNumberPhone;
    private EditText editTextEditSex;
    private EditText editTextAboutMe;
    private Service service;
    private FirebaseFirestore db;
    private boolean isStudio;
    private Boolean createNewService;
    private String mainNameService;
    private ImageView imageViewLoadPortfolio_1;
    private ImageView imageViewLoadPortfolio_2;
    private ImageView imageViewLoadPortfolio_3;
    private ImageView imageViewLoadPortfolio_4;
    private ImageView imageViewLoadPortfolio_5;
    private ImageView imageViewLoadPortfolio_6;
    private final int PICK_IMAGE_REQUEST = 71;
    private Uri filePath;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Intent intent = getIntent();
        if (intent.hasExtra("idServices")) {
            idServices = intent.getStringExtra("idServices");
        }
        if (intent.hasExtra("createNewService") && intent.hasExtra("mainNameService")) {
            createNewService = intent.getBooleanExtra("createNewService", false);
            mainNameService = intent.getStringExtra("mainNameService");
        }

        db = FirebaseFirestore.getInstance();
        if (createNewService == null) {
            db.collection(nameCollection).document(idServices)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            service = task.getResult().toObject(Service.class);
                            setFieldsProfile(service);
                        }
                    });
        } else {
            String item;
            if (!createNewService) {
                item = "Мастер";
            } else {
                item = "Студия";
            }
            service = new Service(mainNameService, "", item,
                    "", "", "", "",
                    "", "", "");
            setFieldsProfile(service);
        }

        imageViewLoadPortfolio_1 = findViewById(R.id.imageViewLoadPortfolio_1);
        imageViewLoadPortfolio_2 = findViewById(R.id.imageViewLoadPortfolio_2);
        imageViewLoadPortfolio_3 = findViewById(R.id.imageViewLoadPortfolio_3);
        imageViewLoadPortfolio_4 = findViewById(R.id.imageViewLoadPortfolio_4);
        imageViewLoadPortfolio_5 = findViewById(R.id.imageViewLoadPortfolio_5);
        imageViewLoadPortfolio_6 = findViewById(R.id.imageViewLoadPortfolio_6);
        imageViewLoadPortfolio_1.setOnClickListener(view -> chooseImage());
        imageViewLoadPortfolio_2.setOnClickListener(view -> chooseImage());
        imageViewLoadPortfolio_3.setOnClickListener(view -> chooseImage());
        imageViewLoadPortfolio_4.setOnClickListener(view -> uploadImage());
        imageViewLoadPortfolio_5.setOnClickListener(view -> uploadImage());
        imageViewLoadPortfolio_6.setOnClickListener(view -> uploadImage());
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Выбрать фотографию"), PICK_IMAGE_REQUEST);
    }

    private void uploadImage() {
        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(taskSnapshot -> {
                        progressDialog.dismiss();
                        Toast.makeText(EditProfileActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        progressDialog.dismiss();
                        Toast.makeText(EditProfileActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    })
                    .addOnProgressListener(taskSnapshot -> {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        progressDialog.setMessage("Uploaded " + (int) progress + "%");
                    });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageViewLoadPortfolio_2.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setFieldsProfile(Service serviceItem) {
        editTextEditName = findViewById(R.id.editTextEditName);
        editTextEditSurname = findViewById(R.id.editTextEditSurname);
        editTextEditNumberPhone = findViewById(R.id.editTextEditNumberPhone);
        editTextEditSex = findViewById(R.id.editTextEditSex);
        editTextAboutMe = findViewById(R.id.editTextAboutMe);

        editTextEditName.setText(serviceItem.getName());
        editTextEditSurname.setText(serviceItem.getSurname());
        editTextEditNumberPhone.setText(serviceItem.getNumberPhone());
        editTextEditSex.setText(serviceItem.getSex());
        editTextAboutMe.setText(serviceItem.getAboutMe());
    }

    public void backToProfile() {
        Editable editName = editTextEditName.getText();
        Editable editSurname = editTextEditSurname.getText();
        Editable editNumberPhone = editTextEditNumberPhone.getText();
        Editable editSex = editTextEditSex.getText();
        Editable aboutMe = editTextAboutMe.getText();

        service.setName(editName.toString().trim());
        service.setSurname(editSurname.toString().trim());
        service.setNumberPhone(editNumberPhone.toString().trim());
        service.setSex(editSex.toString().trim());
        service.setAboutMe(aboutMe.toString());

        if (editName.toString().isEmpty() || editNumberPhone.toString().isEmpty()) {
            Toast.makeText(EditProfileActivity.this, "Профиль не создан! Заполните поля!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(EditProfileActivity.this, DetailServiceActivity.class);
            intent.putExtra("mainNameService", service.getCategory());
            startActivity(intent);
            finish();
        } else {
            if (idServices != null) {
                db.collection(nameCollection).document(idServices)
                        .set(service)
                        .addOnSuccessListener(aVoid -> Toast.makeText(EditProfileActivity.this, "Профиль успешно обновлен!", Toast.LENGTH_SHORT).show())
                        .addOnFailureListener(e -> Toast.makeText(EditProfileActivity.this, "Ошибка обновления профиля!", Toast.LENGTH_SHORT).show());
            } else {
                db.collection("services").add(service)
                        .addOnSuccessListener(aVoid -> Toast.makeText(EditProfileActivity.this, "Профиль успешно создан!", Toast.LENGTH_SHORT).show())
                        .addOnFailureListener(e -> Toast.makeText(EditProfileActivity.this, "Ошибка создания профиля!", Toast.LENGTH_SHORT).show());
            }
            Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
            intent.putExtra("nameServiceItem", editName.toString());
            intent.putExtra("surnameServiceItem", editSurname.toString());
            intent.putExtra("mainNameService", service.getCategory());
            startActivity(intent);
            finish();
        }
    }

    public void backToProfile(View view) {
        backToProfile();
    }

    @Override
    public void onBackPressed() {
        backToProfile();
    }
}