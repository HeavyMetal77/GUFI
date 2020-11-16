package ua.tarastom.gufi;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

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
        }else {
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