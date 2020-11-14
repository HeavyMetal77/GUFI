package ua.tarastom.gufi.model;

import android.widget.ImageView;

public abstract class ServiceItem {
    private String category;
    private String nameServiceItem;
    private String numberPhone;
    private ImageView imgProfilePicPath;
    private String payment;
    private String businessHours;

    public ServiceItem(String category, String nameServiceItem, String numberPhone, ImageView imgProfilePicPath, String payment, String businessHours) {
        this.category = category;
        this.nameServiceItem = nameServiceItem;
        this.numberPhone = numberPhone;
        this.imgProfilePicPath = imgProfilePicPath;
        this.payment = payment;
        this.businessHours = businessHours;
    }

    public String getNameServiceItem() {
        return nameServiceItem;
    }

    public void setNameServiceItem(String nameServiceItem) {
        this.nameServiceItem = nameServiceItem;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public ImageView getImgProfilePic() {
        return imgProfilePicPath;
    }

    public void setImgProfilePic(ImageView imgProfilePic) {
        this.imgProfilePicPath = imgProfilePic;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getBusinessHours() {
        return businessHours;
    }

    public void setBusinessHours(String businessHours) {
        this.businessHours = businessHours;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
