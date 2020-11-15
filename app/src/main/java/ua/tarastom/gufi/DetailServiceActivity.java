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

import ua.tarastom.gufi.model.Service;
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
            List<Service> listMasters = getMastersOrStudios(getAllServices(), mainNameService, "Студия");
            setRecyclerview_detail_service(listMasters, mainNameService);
            textViewLabelStudios.setTypeface(Typeface.DEFAULT_BOLD);
            textViewLabelStudios.setTextSize(16);
            textViewLabelStudios.setTextColor(ContextCompat.getColor(DetailServiceActivity.this, R.color.black));
            textViewLabelMasters.setTypeface(Typeface.DEFAULT);
            textViewLabelMasters.setTextSize(12);
            textViewLabelMasters.setTextColor(ContextCompat.getColor(DetailServiceActivity.this, R.color.black_light));

        });
        textViewLabelMasters = findViewById(R.id.textViewLabelMasters);
        textViewLabelMasters.setOnClickListener(view -> {
            List<Service> listStudios = getMastersOrStudios(getAllServices(), mainNameService, "Мастер");
            setRecyclerview_detail_service(listStudios, mainNameService);
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

        setRecyclerview_detail_service(getMastersOrStudios(getAllServices(), mainNameService, "Мастер"), mainNameService);
    }

    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                ConstraintLayout coordinatorLayout = viewHolder.itemView.findViewById(R.id.coordinatorLayout);
                final int position = viewHolder.getAdapterPosition();
                final Service item = adapter.getServiceItems().get(position);
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

    private void setRecyclerview_detail_service(List<Service> services, String mainNameService) {
        recyclerview_detail_service = findViewById(R.id.recyclerview_detail_service);
        adapter = new DetailServiceAdapter(services);
        adapter.setServiceItems(services);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerview_detail_service.setLayoutManager(layoutManager);

        adapter.setOnProfileClickListener(position -> {
            Intent intent = new Intent(DetailServiceActivity.this, ProfileActivity.class);
            Service service = adapter.getServiceItems().get(position);
            intent.putExtra("nameServiceItem", service.getName());
            intent.putExtra("mainNameService", mainNameService);
            startActivity(intent);
        });

        recyclerview_detail_service.setAdapter(adapter);
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerview_detail_service.getContext(),
//                layoutManager.getOrientation());
//        recyclerview_detail_service.addItemDecoration(dividerItemDecoration);
        enableSwipeToDeleteAndUndo();
    }


    private List<Service> getAllServices() {
        //TODO реализовать получение списка с БД

        List<Service> allServices = new ArrayList<>();
        allServices.add(new Service(
        1, "Макияж", "Петр", "Мастер",
                "Петренко", "мужской", "067456982", "http://test",
                "Полная оплата", "С 08:00 до 16:00 в будни", "Есть выезд к клиенту на дом"
        ));

        allServices.add(new Service(
        1, "Макияж", "Компания1", "Студия",
                "", "", "067456982", "http://test",
                "Полная оплата", "С 08:00 до 16:00 в будни", "Есть выезд к клиенту на дом"
        ));


        return allServices;
    }

    private List<Service> getMastersOrStudios(List<Service> allServices, String mainNameService, String classTypeToReturn) {
        List<Service> resultList = new ArrayList<>();
        for (Service service : allServices) {
            if (service.getItem().equals(classTypeToReturn) && service.getCategory().equals(mainNameService)) {
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