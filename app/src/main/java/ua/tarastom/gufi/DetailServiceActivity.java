package ua.tarastom.gufi;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import ua.tarastom.gufi.model.Master;
import ua.tarastom.gufi.model.ServiceInterface;
import ua.tarastom.gufi.model.Studio;
import ua.tarastom.gufi.utils.DetailServiceAdapter;
import ua.tarastom.gufi.utils.SwipeToDeleteCallback;

public class DetailServiceActivity extends AppCompatActivity {
    private TextView textViewHeaderSubCatalog;
    private TextView textViewLabelStudios;
    private TextView textViewLabelMasters;
    private ImageView imageViewArrow;
    private RecyclerView recyclerview_detail_service;
    private DetailServiceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_service);
        String mainNameService = getIntent().getStringExtra("mainNameService");
        textViewHeaderSubCatalog = findViewById(R.id.textViewHeaderSubCatalog);

        //получаю список всех студий и мастеров
        textViewLabelStudios = findViewById(R.id.textViewLabelStudios);
        textViewLabelStudios.setOnClickListener(view -> {
            List<ServiceInterface> listMasters = getMastersOrStudios(getAllServices(), mainNameService, "Студия");
            setRecyclerview_detail_service(listMasters);
            textViewLabelStudios.setTypeface(Typeface.DEFAULT_BOLD);
            textViewLabelStudios.setTextSize(16);
            textViewLabelStudios.setTextColor(ContextCompat.getColor(DetailServiceActivity.this, R.color.black));
            textViewLabelMasters.setTypeface(Typeface.DEFAULT);
            textViewLabelMasters.setTextSize(12);
            textViewLabelMasters.setTextColor(ContextCompat.getColor(DetailServiceActivity.this, R.color.black_light));

        });
        textViewLabelMasters = findViewById(R.id.textViewLabelMasters);
        textViewLabelMasters.setOnClickListener(view -> {
            List<ServiceInterface> listStudios = getMastersOrStudios(getAllServices(), mainNameService, "Мастер");
            setRecyclerview_detail_service(listStudios);
            textViewLabelMasters.setTypeface(Typeface.DEFAULT_BOLD);
            textViewLabelMasters.setTextSize(16);
            textViewLabelMasters.setTextColor(ContextCompat.getColor(DetailServiceActivity.this, R.color.black));
            textViewLabelStudios.setTypeface(Typeface.DEFAULT);
            textViewLabelStudios.setTextSize(12);
            textViewLabelStudios.setTextColor(ContextCompat.getColor(DetailServiceActivity.this, R.color.black_light));
        });
        textViewHeaderSubCatalog.setText(mainNameService);

        imageViewArrow = findViewById(R.id.imageViewArrow);
        imageViewArrow.setOnClickListener(view -> {
            Intent intent = new Intent(DetailServiceActivity.this, ServiceCatalogActivity.class);
            startActivity(intent);
            finish();
        });

        setRecyclerview_detail_service(getMastersOrStudios(getAllServices(), mainNameService, "Мастер"));
    }

    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                ConstraintLayout coordinatorLayout = viewHolder.itemView.findViewById(R.id.coordinatorLayout);
                final int position = viewHolder.getAdapterPosition();
                final ServiceInterface item = adapter.getServiceItems().get(position);
                adapter.removeItem(position);
                Snackbar snackbar = Snackbar.make(coordinatorLayout, "Объект удален из списка.", Snackbar.LENGTH_LONG);
                snackbar.setAction("ОТМЕНИТЬ УДАЛЕНИЕ", view -> {
                    adapter.restoreItem(item, position);
                    recyclerview_detail_service.scrollToPosition(position);
                });
                snackbar.setActionTextColor(Color.RED);
                snackbar.show();
            }
        };
        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(recyclerview_detail_service);
    }

    private void setRecyclerview_detail_service(List<ServiceInterface> services) {
        recyclerview_detail_service = findViewById(R.id.recyclerview_detail_service);
        adapter = new DetailServiceAdapter(services);
        adapter.setServiceItems(services);
        recyclerview_detail_service.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerview_detail_service.setAdapter(adapter);
        enableSwipeToDeleteAndUndo();
    }


    private List<ServiceInterface> getAllServices() {
        //TODO реализовать получение списка с БД

        List<ServiceInterface> allServices = new ArrayList<>();
        allServices.add(new Master("Макияж", "Петренко Петр1", "067-985-24-86", null, "Полная оплата", "С 08:00 до 16:00 в будни"));
        allServices.add(new Master("Маникюр, педикюр", "Петренко Петр2", "067-985-24-86", null, "Полная оплата", "С 08:00 до 16:00 в будни"));
        allServices.add(new Master("Маникюр, педикюр", "Петренко Петр3", "067-985-24-86", null, "Полная оплата", "С 08:00 до 16:00 в будни"));
        allServices.add(new Master("Макияж", "Петренко Петр4", "067-985-24-86", null, "Полная оплата", "С 08:00 до 16:00 в будни"));
        allServices.add(new Master("Макияж", "Петренко Петр5", "067-985-24-86", null, "Полная оплата", "С 08:00 до 16:00 в будни"));
        allServices.add(new Master("Макияж", "Петренко Петр6", "067-985-24-86", null, "Полная оплата", "С 08:00 до 16:00 в будни"));
        allServices.add(new Master("Макияж", "Петренко Петр7", "067-985-24-86", null, "Полная оплата", "С 08:00 до 16:00 в будни"));
        allServices.add(new Master("Маникюр, педикюр", "Петренко Петр8", "067-985-24-86", null, "Полная оплата", "С 08:00 до 16:00 в будни"));
        allServices.add(new Master("Макияж", "Петренко Петр9", "067-985-24-86", null, "Полная оплата", "С 08:00 до 16:00 в будни"));
        allServices.add(new Master("Макияж", "Петренко Петр10", "067-985-24-86", null, "Полная оплата", "С 08:00 до 16:00 в будни"));
        allServices.add(new Studio("Макияж", "Компания1", "067-985-24-86", null, "Полная оплата", "С 08:00 до 16:00 в будни"));
        allServices.add(new Studio("Маникюр, педикюр", "Компания2", "067-985-24-86", null, "Полная оплата", "С 08:00 до 16:00 в будни"));
        allServices.add(new Studio("Маникюр, педикюр", "Компания3", "067-985-24-86", null, "Полная оплата", "С 08:00 до 16:00 в будни"));
        allServices.add(new Studio("Макияж", "Компания4", "067-985-24-86", null, "Полная оплата", "С 08:00 до 16:00 в будни"));
        allServices.add(new Studio("Макияж", "Компания5", "067-985-24-86", null, "Полная оплата", "С 08:00 до 16:00 в будни"));
        allServices.add(new Studio("Макияж", "Компания6", "067-985-24-86", null, "Полная оплата", "С 08:00 до 16:00 в будни"));
        allServices.add(new Studio("Макияж", "Компания7", "067-985-24-86", null, "Полная оплата", "С 08:00 до 16:00 в будни"));
        allServices.add(new Studio("Макияж", "Компания8", "067-985-24-86", null, "Полная оплата", "С 08:00 до 16:00 в будни"));
        allServices.add(new Studio("Маникюр, педикюр", "Компания9", "067-985-24-86", null, "Полная оплата", "С 08:00 до 16:00 в будни"));
        allServices.add(new Studio("Макияж", "Компания10", "067-985-24-86", null, "Полная оплата", "С 08:00 до 16:00 в будни"));
        allServices.add(new Studio("Макияж", "Компания11", "067-985-24-86", null, "Полная оплата", "С 08:00 до 16:00 в будни"));
        allServices.add(new Studio("Макияж", "Компания12", "067-985-24-86", null, "Полная оплата", "С 08:00 до 16:00 в будни"));

        return allServices;
    }

    private List<ServiceInterface> getMastersOrStudios(List<ServiceInterface> allServices, String mainNameService, String classTypeToReturn) {
        List<ServiceInterface> resultList = new ArrayList<>();
        for (ServiceInterface service : allServices) {
            if (service.getTypeService().equals(classTypeToReturn) && service.getCategory().equals(mainNameService)) {
                resultList.add(service);
            }
        }
        return resultList;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(DetailServiceActivity.this, ServiceCatalogActivity.class);
        startActivity(intent);
        finish();
    }

}