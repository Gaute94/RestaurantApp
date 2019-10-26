package com.example.s326197mappe2;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLInput;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DBHandler extends SQLiteOpenHelper {

    //*****FRIENDS DATABASE*****//
    private static String TABLE_FRIENDS = "Friends";
    private static String KEY_ID = "_ID";


    //*****RESTAURANT DATABASE*****//
    private static String TABLE_RESTAURANTS = "Restaurants";
    private static String RESTAURANT_KEY_ID = "_ID";
    private static String ADDRESS = "Address";
    private static String TYPE = "Type";

    //********BOOKING**********//
    private static String TABLE_BOOKING = "Bookings";
    private static String RESTAURANT = "Restaurant";
    private static String DATE = "Date";
    private static String TABLE_BOOKING_FRIENDS = "Booking_Friends";

    //******COMMON ****//
    private static String KEY_NAME = "Name";
    private static String KEY_PH_NO = "Telephone";
    private static int DATABASE_VERSION = 20;
    private static String DATABASE_NAME = "Restaurant Planner";

    private static final String TAG = "DBHandler";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        //Create Friends Table
        String CREATE_FRIENDS_TABLE = "CREATE TABLE " + TABLE_FRIENDS + "(" + KEY_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT," + KEY_PH_NO + " TEXT" + ")";
        Log.d("SQL", CREATE_FRIENDS_TABLE);
        db.execSQL(CREATE_FRIENDS_TABLE);

        //Create Restaurant Table
        String CREATE_RESTAURANTS_TABLE = "CREATE TABLE " + TABLE_RESTAURANTS + "(" + RESTAURANT_KEY_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT," + KEY_PH_NO + " TEXT," + ADDRESS + " TEXT," + TYPE + " TEXT" + ")";
        Log.d("SQL", CREATE_RESTAURANTS_TABLE);
        db.execSQL(CREATE_RESTAURANTS_TABLE);


        String CREATE_BOOKING_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_BOOKING + "(" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DATE + " DATETIME," +
                "Restaurant_id INTEGER, " +
                "FOREIGN KEY(Restaurant_id) REFERENCES Restaurants(" + RESTAURANT_KEY_ID + ")" +
                ")";
        Log.d("SQL", CREATE_BOOKING_TABLE);
        db.execSQL(CREATE_BOOKING_TABLE);


        String CREATE_BOOKING_FRIENDS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_BOOKING_FRIENDS + "(" +
                "booking_ID INTEGER, " +
                "friends_ID INTEGER," +
                "FOREIGN KEY(booking_ID) REFERENCES Booking(" + KEY_ID + "), " +
                "FOREIGN KEY(friends_ID) REFERENCES Friends(" + KEY_ID + ")" +
                ")";

        Log.d("SQL", CREATE_BOOKING_FRIENDS_TABLE);
        db.execSQL(CREATE_BOOKING_FRIENDS_TABLE);


        addFriend(db, new Friend("Chandler Bing", "55532321"));
        addFriend(db, new Friend("Monica Geller", "55532322"));
        addFriend(db, new Friend("Chandler Bing", "55532321"));
        addFriend(db, new Friend("Monica Geller", "55532322"));
        addFriend(db, new Friend("Chandler Bing", "55532321"));
        addFriend(db, new Friend("Monica Geller", "55532322"));
        addFriend(db, new Friend("Chandler Bing", "55532321"));
        addFriend(db, new Friend("Monica Geller", "55532322"));
        addFriend(db, new Friend("Chandler Bing", "55532321"));
        addFriend(db, new Friend("Monica Geller", "55532322"));
        addFriend(db, new Friend("Chandler Bing", "55532321"));
        addFriend(db, new Friend("Monica Geller", "55532322"));



//        List<Friend> friends = new ArrayList<>();
//        friends.add(friend);
//        friends.add(friend2);
        addRestaurant(db, new Restaurant("Pizza4U", "That Way 3", "91919191", "Pizza"));
        addRestaurant(db, new Restaurant("El Castell", "That Way 4", "91988891", "Pizza"));
        addRestaurant(db, new Restaurant("Burger King", "That Way 5", "94449191", "Burger"));

//        Date date = new Date();
//        Booking booking = new Booking(new Restaurant("McDonalds", "That Way 6", "94449191", "Burger"),  friends, date);
//        addBooking(db, booking, friends);
//        Log.d("DBHandler", "Date: " + date);
//        Log.d("DBHandler", "Booking Restaurant Name: " + booking.getRestaurant().getName());
//        for(Friend friend1 : friends){
//            Log.d("DBHandler", "Booking Friend List, Added: " + friend1.getName());
//        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int il) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FRIENDS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTAURANTS);
        onCreate(db);
    }

    public void addFriend(Friend friend) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, friend.getName());
        values.put(KEY_PH_NO, friend.getTelephone());
        db.insert(TABLE_FRIENDS, null, values);
        db.close();
    }



    public void addFriend(SQLiteDatabase db, Friend friend){
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, friend.getName());
        values.put(KEY_PH_NO, friend.getTelephone());
        db.insert(TABLE_FRIENDS, null, values);

        Log.d("DBHandler", "Added friend: " + friend);
    }

    public int editFriend(Friend friend){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, friend.getName());
        values.put(KEY_PH_NO, friend.getTelephone());
        int updated = db.update(TABLE_FRIENDS, values, KEY_ID + "= ?",
                new String[]{String.valueOf(friend.getId())});
        db.close();
        return updated;
    }

    public List<Friend> findAllFriends(){
        List<Friend> friendsList = new ArrayList<Friend>();
        String selectQuery = "SELECT * FROM " + TABLE_FRIENDS;
        try(SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null)) {
            if (cursor.moveToFirst()) {
                do {
                    Friend friend = new Friend();
                    friend.setId(cursor.getInt(0));
                    friend.setName(cursor.getString(1));
                    friend.setTelephone(cursor.getString(2));
                    friendsList.add(friend);
                } while (cursor.moveToNext());
            }
        }
        return friendsList;
    }

    public Friend findFriend(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_FRIENDS, new String[] {
                        KEY_ID, KEY_NAME, KEY_PH_NO}, KEY_ID + "= ?",
                new String[] {String.valueOf(id)}, null, null, null, null);
        if(cursor != null) cursor.moveToFirst();
        Friend friend = new
                Friend(Long.parseLong(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        cursor.close();
        db.close();
        return friend;
    }

//    public String showListOfFriends(){
//        StringBuilder friends = new StringBuilder();
//
//        List<Friend> friendList = findAllFriends();
//
//        for(Friend friend : friendList){
//            String name = friend.getName();
//            friends.append(name);
//            friends.append(", ");
//        }
//        return friends.toString();
//    }

    public void delete(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FRIENDS, KEY_ID + "= ?",
                new String[] {Long.toString(id)});
        db.close();
    }
//
//    public int oppdaterKontakt(Kontakt kontakt){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(KEY_NAME, kontakt.getNavn());
//        values.put(KEY_PH_NO, kontakt.getTelefon());
//        int endret = db.update(TABLE_KONTAKTER, values, KEY_ID + "= ?",
//                new String[]{String.valueOf(kontakt.get_ID())});
//        db.close();
//        return endret;
//    }

    public int finnAntallKontakter() {
        String sql = "SELECT * FROM " + TABLE_FRIENDS;
        try (SQLiteDatabase db = this.getWritableDatabase();
             Cursor cursor = db.rawQuery(sql, null)){
            return cursor.getCount();
        }
    }
//
//    public Kontakt finnKontakt(int id){
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.query(TABLE_KONTAKTER, new String[] {
//                        KEY_ID, KEY_NAME, KEY_PH_NO}, KEY_ID + "= ?",
//                new String[] {String.valueOf(id)}, null, null, null, null);
//        if(cursor != null) cursor.moveToFirst();
//        Kontakt kontakt = new
//                Kontakt(Long.parseLong(cursor.getString(0)),
//                cursor.getString(1), cursor.getString(2));
//        cursor.close();
//        db.close();
//        return kontakt;
//    }


    /**************    RESTAURANT   ***************/
    public void addRestaurant(Restaurant restaurant) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, restaurant.getName());
        values.put(KEY_PH_NO, restaurant.getTelephone());
        values.put(ADDRESS, restaurant.getAddress());
        values.put(TYPE, restaurant.getType());
        db.insert(TABLE_RESTAURANTS, null, values);
        db.close();
    }

    public void addRestaurant(SQLiteDatabase db, Restaurant restaurant){
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, restaurant.getName());
        values.put(KEY_PH_NO, restaurant.getTelephone());
        values.put(ADDRESS, restaurant.getAddress());
        values.put(TYPE, restaurant.getType());
        db.insert(TABLE_RESTAURANTS, null, values);


        Log.d("DBHandler", "Type: " + restaurant.getType());
        Log.d("DBHandler", "Added restaurant: " + restaurant);
    }


    public List<Restaurant> findAllRestaurants() {
        List<Restaurant> restaurantsList = new ArrayList<Restaurant>();
        String selectQuery = "SELECT * FROM " + TABLE_RESTAURANTS;
        try(SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null)) {
            if (cursor.moveToFirst()) {
                do {
                    Restaurant restaurant = new Restaurant();
                    restaurant.setId(cursor.getInt(0));
                    restaurant.setName(cursor.getString(1));
                    restaurant.setTelephone(cursor.getString(2));
                    restaurant.setAddress(cursor.getString(3));
                    restaurant.setType(cursor.getString(4));
                    restaurantsList.add(restaurant);


                } while (cursor.moveToNext());
            }
        }
        return restaurantsList;
    }

    public Restaurant getRestaurant(long id){
        String select = "SELECT * FROM " + TABLE_RESTAURANTS + " WHERE " + KEY_ID + " = " + id;
        Log.d("SQL: ", select);
        try(SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(select, null)) {
            if (cursor.moveToFirst() && !cursor.isAfterLast()) {

                Restaurant restaurant = new Restaurant();
                restaurant.setId(cursor.getInt(0));
                restaurant.setName(cursor.getString(1));
                restaurant.setTelephone(cursor.getString(2));
                restaurant.setAddress(cursor.getString(3));
                restaurant.setType(cursor.getString(4));

                return restaurant;
            }
            return null;
        }
    }

    public int editRestaurant(Restaurant restaurant){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, restaurant.getName());
        values.put(KEY_PH_NO, restaurant.getTelephone());
        values.put(ADDRESS, restaurant.getAddress());
        values.put(TYPE, restaurant.getType());
        int updated = db.update(TABLE_RESTAURANTS, values, KEY_ID + "= ?",
                new String[]{String.valueOf(restaurant.getId())});
        db.close();
        return updated;
    }

    public void deleteRestaurant(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RESTAURANTS, KEY_ID + "= ?",
                new String[] {Long.toString(id)});
        db.close();
    }



    /**************BOOKING*******************/

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.getDefault());

    public void addBooking(SQLiteDatabase db, Booking booking, List<Friend> friendList){
        ContentValues values = new ContentValues();
        Log.d("BOOKING", "Inside addBooking");
        values.put(DATE, dateFormat.format(booking.getDate()));
        values.put("restaurant_id", booking.getRestaurant().getId());
        booking.setId(db.insert(TABLE_BOOKING, null, values));

        ContentValues values1 = new ContentValues();

        Log.d("BOOKING", "Booking date: " + booking.getDate());
        Log.d("BOOKING", "Booking RestaurantName: " + booking.getRestaurant().getName());
        for(Friend friend : friendList){
            Log.d("BOOKING", "Booking Friend Name: " + friend.getName());
        }

        for(Friend friend : friendList) {
            values1.put("booking_ID", booking.getId());
            values1.put("friends_ID", friend.getId());
            db.insert(TABLE_BOOKING_FRIENDS, null, values1);
            Log.d("BOOKING", "Booking Friend ID: " + friend.getId());
        }

        Log.d("DBHandler", "Added Booking: " + booking);
    }

    public List<Friend> getFriendBookings(Booking booking) {
        Log.d("DBHandler", "currently inside getFriendBookings ");
        List<Friend> friends = new ArrayList<>();
        String select = "SELECT " + TABLE_FRIENDS + ".*" + " FROM " + TABLE_FRIENDS + ", " + TABLE_BOOKING_FRIENDS + " WHERE " + TABLE_FRIENDS +"."+KEY_ID+
                " = " + TABLE_BOOKING_FRIENDS+".friends_ID AND " + TABLE_BOOKING_FRIENDS +".booking_ID = " + booking.getId();


        Log.d("SQL: ", "getFriendBookings: SQL " + select);

        try (SQLiteDatabase db = this.getWritableDatabase();
             Cursor cursor = db.rawQuery(select, null)) {
            Log.d("TRY", "INSIDE TRY");
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast())
                {

                    Friend friend = new Friend();
                    friend.setId(cursor.getInt(0));
                    friend.setName(cursor.getString(1));
                    friend.setTelephone(cursor.getString(2));
                    friends.add(friend);
                    Log.d("CURSOR-NAME: ", cursor.getString(1));
                    cursor.moveToNext();
                }
            }
        }

        Log.d("SQL", "getFriendBookings: FRIENDS " + friends);

        return friends;
    }

    public List<Booking> findAllBookings() {
        Log.d("DBHandler", "currently inside findAllBookings ");
        List<Booking> bookingList = new ArrayList<Booking>();
        String selectQuery = "SELECT * FROM " + TABLE_BOOKING;
        try(SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null)) {
            if (cursor.moveToFirst()) {
                do {
                    Booking booking = new Booking();
                    booking.setId(cursor.getInt(0));
                    booking.setDate(dateFormat.parse(cursor.getString(1)));
                    booking.setRestaurant(getRestaurant(cursor.getInt(2)));
                    booking.setFriendList(getFriendBookings(booking));
                } while (cursor.moveToNext());
            }
        }catch (java.text.ParseException e){
            e.printStackTrace();
        }
        return bookingList;
    }
}
