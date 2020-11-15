package ua.tarastom.gufi;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Intent intent = getIntent();
        if (intent.hasExtra("idServices")) {
            idServices = intent.getStringExtra("idServices");
        }

        db = FirebaseFirestore.getInstance();
        db.collection(nameCollection).document(idServices)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        service = task.getResult().toObject(Service.class);
                            setFieldsProfile(service);
                    }
                });
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

        service.setName(editName.toString());
        service.setSurname(editSurname.toString());
        service.setNumberPhone(editNumberPhone.toString());
        service.setSex(editSex.toString());
        service.setAboutMe(aboutMe.toString());

        db.collection(nameCollection).document(idServices)
                .set(service)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(EditProfileActivity.this, "Профиль успешно обновлен!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditProfileActivity.this, "Ошибка обновления профиля!", Toast.LENGTH_SHORT).show();
                    }
                });

        Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
        intent.putExtra("nameServiceItem", editName.toString());
        intent.putExtra("surnameServiceItem", editSurname.toString());
        intent.putExtra("mainNameService", service.getCategory());
        startActivity(intent);
    }

    public void backToProfile(View view) {
        backToProfile();
    }

    @Override
    public void onBackPressed() {
        backToProfile();
    }
}