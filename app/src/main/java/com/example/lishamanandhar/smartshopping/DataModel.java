package com.example.lishamanandhar.smartshopping;

/**
 * Created by LishaManandhar on 3/8/2018.
 */

public class DataModel {

    private String name;
    private String image;
    private String price;
    private String manufacture_date;
    private String expiriy_date;
    private String description;
    private String section;

    public DataModel(){

    }

    public DataModel(String name , String image, String price , String manufacture_date , String expiriy_date , String description , String section ){
        this.name = name;
        this.image = image;
        this.price = price;
        this.manufacture_date = manufacture_date;
        this.expiriy_date = expiriy_date;
        this.description = description;
        this.section = section;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getManufacture_date() {
        return manufacture_date;
    }

    public void setManufacture_date(String manufacture_date) {
        this.manufacture_date = manufacture_date;
    }

    public String getExpiriy_date() {
        return expiriy_date;
    }

    public void setExpiriy_date(String expiriy_date) {
        this.expiriy_date = expiriy_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }
}
