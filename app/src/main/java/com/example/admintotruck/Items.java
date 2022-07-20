package com.example.admintotruck;


public class  Items {


    String name, desc, price, imageurl;

    Items() {

    }


    public Items(String name, String desc, String price, String imageurl) {
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.imageurl = imageurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}

