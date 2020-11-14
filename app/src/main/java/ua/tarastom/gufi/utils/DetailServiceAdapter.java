package ua.tarastom.gufi.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ua.tarastom.gufi.R;
import ua.tarastom.gufi.model.ServiceInterface;

public class DetailServiceAdapter extends RecyclerView.Adapter<DetailServiceAdapter.ServiceViewHolder> {

    private List<ServiceInterface> serviceItem;

    public DetailServiceAdapter(List<ServiceInterface> serviceItem) {
        this.serviceItem = serviceItem;
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        serviceItem.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(ServiceInterface item, int position) {
        serviceItem.add(position, item);
        notifyItemInserted(position);
    }

    public void setServiceItems(List<ServiceInterface> serviceItem) {
        this.serviceItem = serviceItem;
        notifyDataSetChanged();
    }

    public List<ServiceInterface> getServiceItems() {
        return serviceItem;
    }

    private void addServiceItems(ArrayList<ServiceInterface> serviceItem) {
        this.serviceItem.addAll(serviceItem);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_master_studio, parent, false);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {
        ServiceInterface serviceInterface = serviceItem.get(position);
        holder.nameMaster.setText(serviceInterface.getName());
        holder.current_category.setText(serviceInterface.getCategory());
        holder.numberPhoneMaster.setText(serviceInterface.getNumber());
        holder.payment.setText(serviceInterface.getPayment());
        holder.businessHours.setText(serviceInterface.getBusinessHours());
//        holder.person_photo.setText(serviceInterface.getNumber());
    }

    @Override
    public int getItemCount() {
        return serviceItem.size();
    }


    class ServiceViewHolder extends RecyclerView.ViewHolder {
        private TextView current_category;
        private TextView nameMaster;
        private TextView numberPhoneMaster;
        private ImageView person_photo;
        private TextView payment;
        private TextView businessHours;

        public ServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            current_category = itemView.findViewById(R.id.current_category);
            nameMaster = itemView.findViewById(R.id.person_name);
            numberPhoneMaster = itemView.findViewById(R.id.person_phone);
            person_photo = itemView.findViewById(R.id.person_photo);
            payment = itemView.findViewById(R.id.textViewPayment);
            businessHours = itemView.findViewById(R.id.textViewBusinessHours);
        }
    }
}
