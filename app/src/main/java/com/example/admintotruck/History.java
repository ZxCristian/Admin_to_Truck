package com.example.admintotruck;

public class History {
    String name;
    String price;
    String quantity;
    String imageurl;
    String subtotal;
    String fullname;
    String street;
    String phonenum;
    String Landmark;
    String RDC;

    History(){

    }

    public History(String name, String price, String quantity, String imageurl, String subtotal, String fullname, String street, String phonenum, String landmark, String RDC) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.imageurl = imageurl;
        this.subtotal = subtotal;
        this.fullname = fullname;
        this.street = street;
        this.phonenum = phonenum;
        Landmark = landmark;
        this.RDC = RDC;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public String getLandmark() {
        return Landmark;
    }

    public void setLandmark(String landmark) {
        Landmark = landmark;
    }

    public String getRDC() {
        return RDC;
    }

    public void setRDC(String RDC) {
        this.RDC = RDC;
    }
}
