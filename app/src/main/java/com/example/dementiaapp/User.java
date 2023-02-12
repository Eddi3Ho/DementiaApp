package com.example.dementiaapp;

public class User {

    //Applying Singleton design pattern
    private static User user_instance;
    private static String user_id;
    private static String username;
    private static String email;
    private static String full_name;

    public static User getInstance(){
        if(user_instance == null){
            user_instance = new User();
        }
        return user_instance;
    }

    public User(){

    }

    public static void setUser_id(String user_id){
        User.user_id = user_id;
    }

    public static void setUsername(String username){
        User.username = username;
    }

    public static void setEmail(String email){
        User.email = email;
    }

    public static void setFull_name(String full_name) {
        User.full_name = full_name;
    }

    public static String getUser_id() {
        return user_id;
    }
    public static String getFull_name() {return full_name;}

    public static String getUsername() {
        return username;
    }

    public static String getEmail() {
        return email;
    }
}
