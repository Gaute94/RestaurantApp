package com.example.s326197mappe2;

public class Restaurant {

    private long id;
    private String name;
    private String address;
    private String telephone;
    private String type;

    public Restaurant(){}

    public Restaurant(String name, String address, String telephone, String type){
        this.name = name;
        this.address = address;
        this.telephone = telephone;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getId(){
        return this.id;
    }

    public void setId(long id){
        this.id = id;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", telephone='" + telephone + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
