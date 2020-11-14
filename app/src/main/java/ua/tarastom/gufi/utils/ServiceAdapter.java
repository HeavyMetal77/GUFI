package ua.tarastom.gufi.utils;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ua.tarastom.gufi.DetailServiceActivity;
import ua.tarastom.gufi.R;
import ua.tarastom.gufi.model.Service;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder> {

    private List<Service> serviceItems;
    private int screenWidth;

    public ServiceAdapter() {
        serviceItems = new ArrayList<>();
    }


    public void setServiceItems(List<Service> serviceItems) {
        this.serviceItems = serviceItems;
        notifyDataSetChanged();
    }

    public List<Service> getServiceItems() {
        return serviceItems;
    }

    private void addServiceItems(ArrayList<Service> serviceItems) {
        this.serviceItems.addAll(serviceItems);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_item, parent, false);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {
        Service service = serviceItems.get(position);
        holder.button_service.setText(service.getMainNameService());
        holder.button_service.setWidth(screenWidth/3-30);
        holder.button_service.setHeight(screenWidth/3-30);
        holder.button_service.setOnClickListener(view -> {
            Intent intent = new Intent(holder.itemView.getContext(), DetailServiceActivity.class);
            Service service1 = serviceItems.get(position);
            intent.putExtra("mainNameService", service1.getMainNameService());
            holder.itemView.getContext().startActivity(intent);
        });
        holder.imageViewIconHeart.setOnClickListener(view -> {
            Integer integer = (Integer) holder.imageViewIconHeart.getTag();
            integer = integer == null ? 0 : integer;
            switch(integer) {
                case R.drawable.heart_icon_1:
                    holder.imageViewIconHeart.setImageDrawable(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.heart_icon_2));
                    holder.imageViewIconHeart.setTag(R.drawable.heart_icon_2);
                    break;
                case R.drawable.heart_icon_2:
                default:
                    holder.imageViewIconHeart.setImageDrawable(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.heart_icon_1));
                    holder.imageViewIconHeart.setTag(R.drawable.heart_icon_1);
                    break;
            }
        });
    }

    @Override
    public int getItemCount() {
        return serviceItems.size();
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    class ServiceViewHolder extends RecyclerView.ViewHolder {
        private final Button button_service;
        private final ImageView imageViewIconHeart;

        public ServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            button_service = itemView.findViewById(R.id.button_service);
            imageViewIconHeart = itemView.findViewById(R.id.imageViewIconHeart);

        }
    }
}
