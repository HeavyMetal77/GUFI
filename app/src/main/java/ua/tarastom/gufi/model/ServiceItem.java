package ua.tarastom.gufi.model;

public abstract class ServiceItem {
    private String nameServiceItem;
    private String numberPhone;
    private String imgProfilePicPath;
    private String payment;
    private String businessHours;

    public ServiceItem(String nameServiceItem, String numberPhone, String imgProfilePicPath, String payment, String businessHours) {
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

    public String getImgProfilePic() {
        return imgProfilePicPath;
    }

    public void setImgProfilePic(String imgProfilePic) {
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
}
