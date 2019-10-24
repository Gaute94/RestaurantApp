package com.example.s326197mappe2;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public class Booking {

    private int id;
    private Restaurant restaurant;
    private List<Friend> friendList;
    private Date date;

    public Booking(Restaurant restaurant, List<Friend> friendList, Date date){
        this.restaurant = restaurant;
        this.friendList = friendList;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public List<Friend> getFriendList() {
        return friendList;
    }

    public void setFriendList(List<Friend> friendList) {
        this.friendList = friendList;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
