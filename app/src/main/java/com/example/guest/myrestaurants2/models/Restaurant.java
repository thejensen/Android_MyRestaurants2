package com.example.guest.myrestaurants2.models;

import org.parceler.Parcel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jensese on 11/28/16.
 */

// to use Parcelor (to pass data between fragments by serializing and deserializing them), annotate the class with the @Parcel decorator.
@Parcel
public class Restaurant {
    String Name;
    String Phone;
    String Website;
    double Rating;
    String ImageUrl;
    List<String> Address = new ArrayList<>();
    double Latitude;
    double Longitude;
    List<String> Categories = new ArrayList<>();

//    to use Parcelor, create a public constructor with no arguments for the Parcelor annotation library.
    public Restaurant() {}

    public Restaurant(String name, String phone, String website, double rating, String imageUrl, ArrayList<String> address, double latitude, double longitude, ArrayList<String> categories) {
        this.Name = name;
        this.Phone = phone;
        this.Website = website;
        this.Rating = rating;
        this.ImageUrl = getLargeImageUrl(imageUrl);
        this.Address = address;
        this.Latitude = latitude;
        this.Longitude = longitude;
        this.Categories = categories;
    }

    public String getName() {
        return Name;
    }

    public String getPhone() {
        return Phone;
    }

    public String getWebsite() {
        return Website;
    }

    public double getRating() {
        return Rating;
    }

    public String getImageUrl(){
        return ImageUrl;
    }

    public List<String> getAddress() {
        return Address;
    }

    public double getLatitude() {
        return Latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public List<String> getCategories() {
        return Categories;
    }

//    Here, our new method getLargeImageUrl() simply replaces the last characters in the image's filepath with the characters that correspond to the higher-quality version. If we run the app again we'll notice the image quality is much, much better.
//    Don't forget to resize your images, too!! in RestaurantDetailFragment
    public String getLargeImageUrl(String imageUrl) {
        String largeImageUrl = imageUrl.substring(0, imageUrl.length() - 6).concat("o.jpg");
        return largeImageUrl;
    }
}
