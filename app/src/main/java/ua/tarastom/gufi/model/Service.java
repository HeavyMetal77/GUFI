package ua.tarastom.gufi.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "services")
public class Service{

    @PrimaryKey
    private int idService;
    private String category;
    private String name;

    private String item; //указывает мастер или студия

    private String surname;

    private String sex;
    private String numberPhone;

    private String imgProfilePicPath;
    private String payment;
    private String businessHours;
    private String aboutMe;

    public Service() {
    }

    public Service(String category, String name, String item, String surname,
                   String sex, String numberPhone, String imgProfilePicPath,
                   String payment, String businessHours, String aboutMe) {
        this.category = category;
        this.name = name;
        this.item = item;
        this.surname = surname;
        this.sex = sex;
        this.numberPhone = numberPhone;
        this.imgProfilePicPath = imgProfilePicPath;
        this.payment = payment;
        this.businessHours = businessHours;
        this.aboutMe = aboutMe;
    }

    public Service(int idService, String category, String name, String item,
                   String surname, String sex, String numberPhone, String imgProfilePicPath,
                   String payment, String businessHours, String aboutMe) {
        this.idService = idService;
        this.category = category;
        this.name = name;
        this.item = item;
        this.surname = surname;
        this.sex = sex;
        this.numberPhone = numberPhone;
        this.imgProfilePicPath = imgProfilePicPath;
        this.payment = payment;
        this.businessHours = businessHours;
        this.aboutMe = aboutMe;
    }

    public int getIdService() {
        return idService;
    }

    public void setIdService(int idService) {
        this.idService = idService;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getImgProfilePicPath() {
        return imgProfilePicPath;
    }

    public void setImgProfilePicPath(String imgProfilePicPath) {
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

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }
}
