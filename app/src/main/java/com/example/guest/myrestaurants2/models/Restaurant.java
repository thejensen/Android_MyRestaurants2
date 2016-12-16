package com.example.guest.myrestaurants2.models;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jensese on 11/28/16.
 */

// to use Parcelor (to pass data between fragments by serializing and deserializing them), annotate the class with the @Parcel decorator.
@Parcel
public class Restaurant {
    String name;
    String phone;
    String website;
    double rating;
    String imageUrl;
    List<String> address = new ArrayList<>();
    double latitude;
    double longitude;
    List<String> categories = new ArrayList<>();
    String pushId;
    String index;

    //    to use Parcelor, create a public constructor with no arguments for the Parcelor annotation library.
    public Restaurant() {}

    public Restaurant(String name, String phone, String website, double rating, String imageUrl, ArrayList<String> address, double latitude, double longitude, ArrayList<String> categories) {
        this.name = name;
        this.phone = phone;
        this.website = website;
        this.rating = rating;
        this.imageUrl = getLargeImageUrl(imageUrl);
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.categories = categories;
        this.index = "not_specified";
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getWebsite() {
        return website;
    }

    public double getRating() {
        return rating;
    }

    public String getImageUrl(){
        return imageUrl;
    }

    public List<String> getAddress() {
        return address;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public List<String> getCategories() {
        return categories;
    }

//    Here, our new method getLargeImageUrl() simply replaces the last characters in the image's filepath with the characters that correspond to the higher-quality version. If we run the app again we'll notice the image quality is much, much better.
//    Don't forget to resize your images, too!! in RestaurantDetailFragment
    public String getLargeImageUrl(String imageUrl) {
        String largeImageUrl = imageUrl.substring(0, imageUrl.length() - 6).concat("o.jpg");
        return largeImageUrl;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }


//    Instead of int, we give each restaurant a string index so that we may set the initial value to a string key in our object constructor. Alphabetically, numbers come before letters. So anytime we add a brand new restaurant to our list, it will receive the default string index value. Then, when we re-order our restaurants with our new drag-and-drop feature, we will overwrite this string index with a numerical index. So, any ordered restaurants will come in their numerical order, and new restaurants we add will automatically be added to the end of the list.
    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }
}
