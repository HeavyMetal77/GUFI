package ua.tarastom.gufi.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import ua.tarastom.gufi.R;
import ua.tarastom.gufi.model.Service;

public class DetailServiceAdapter extends RecyclerView.Adapter<DetailServiceAdapter.ServiceViewHolder> {

    private List<Service> serviceItem;
    private OnProfileClickListener onProfileClickListener;

    public DetailServiceAdapter(List<Service> serviceItem) {
        this.serviceItem = serviceItem;
    }
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    public interface OnProfileClickListener {
        void onProfileClick(int position);
    }

    public void setOnProfileClickListener(OnProfileClickListener onProfileClickListener) {
        this.onProfileClickListener = onProfileClickListener;
    }

    public void removeItem(int position) {
        serviceItem.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Service item, int position) {
        serviceItem.add(position, item);
        notifyItemInserted(position);
    }

    public void setServiceItems(List<Service> serviceItem) {
        this.serviceItem = serviceItem;
        notifyDataSetChanged();
    }

    public List<Service> getServiceItems() {
        return serviceItem;
    }

    private void addServiceItems(ArrayList<Service> serviceItem) {
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
        Service service = serviceItem.get(position);
        String name = service.getName() + " " + service.getSurname();
        holder.nameMaster.setText(name);
        holder.current_category.setText(service.getCategory());
        holder.numberPhoneMaster.setText(service.getNumberPhone());
        holder.payment.setText(service.getPayment());
        holder.businessHours.setText(service.getBusinessHours());
//        holder.person_photo.setText(service.getNumber());
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

            itemView.setOnClickListener(view -> {
                if (onProfileClickListener != null) {
                    onProfileClickListener.onProfileClick(getAdapterPosition());
                }
            });
        }
    }
}
