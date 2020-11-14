package ua.tarastom.gufi.model;

public class Master extends ServiceItem{
    private String nameMaster;
    private String surNameMaster;
    private String numberPhoneMaster;
    private String imgProfilePicPath;
    private String payment;
    private String businessHours;

    public Master(String nameServiceItem, String numberPhone, String imgProfilePicPath, String payment, String businessHours) {
        super(nameServiceItem, numberPhone, imgProfilePicPath, payment, businessHours);
    }

    public Master(String nameServiceItem, String numberPhone, String imgProfilePicPath, String payment, String businessHours, String surNameMaster) {
        super(nameServiceItem, numberPhone, imgProfilePicPath, payment, businessHours);
        this.surNameMaster = surNameMaster;
    }

    public String getNameMaster() {
        return nameMaster;
    }

    public void setNameMaster(String nameMaster) {
        this.nameMaster = nameMaster;
    }

    public String getSurNameMaster() {
        return surNameMaster;
    }

    public void setSurNameMaster(String surNameMaster) {
        this.surNameMaster = surNameMaster;
    }

    public String getNumberPhoneMaster() {
        return numberPhoneMaster;
    }

    public void setNumberPhoneMaster(String numberPhoneMaster) {
        this.numberPhoneMaster = numberPhoneMaster;
    }

    public String getImgProfilePic() {
        return imgProfilePicPath;
    }

    public void setImgProfilePic(String imgProfilePicPath) {
        this.imgProfilePicPath = imgProfilePicPath;
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
