package com.example.guest.myrestaurants2.models;

import org.parceler.Parcel;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by jensese on 11/28/16.
 */

// to use Parcelor (to pass data between fragments by serializing and deserializing them), annotate the class with the @Parcel decorator.
@Parcel
public class Restaurant {
    String mName;
    String mPhone;
    String mWebsite;
    double mRating;
    String mImageUrl;
    ArrayList<String> mAddress = new ArrayList<>();
    double mLatitude;
    double mLongitude;
    ArrayList<String> mCategories = new ArrayList<>();

//    to use Parcelor, create a public constructor with no arguments for the Parcelor annotation library.
    public Restaurant() {}

    public Restaurant(String name, String phone, String website, double rating, String imageUrl, ArrayList<String> address, double latitude, double longitude, ArrayList<String> categories) {
        this.mName = name;
        this.mPhone = phone;
        this.mWebsite = website;
        this.mRating = rating;
        this.mImageUrl = getLargeImageUrl(imageUrl);
        this.mAddress = address;
        this.mLatitude = latitude;
        this.mLongitude = longitude;
        this.mCategories = categories;
    }

    public String getName() {
        return mName;
    }

    public String getPhone() {
        return mPhone;
    }

    public String getWebsite() {
        return  mWebsite;
    }

    public double getRating() {
        return mRating;
    }

    public String getImageUrl(){
        return mImageUrl;
    }

    public ArrayList<String> getAddress() {
        return mAddress;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public ArrayList<String> getCategories() {
        return mCategories;
    }

//    Here, our new method getLargeImageUrl() simply replaces the last characters in the image's filepath with the characters that correspond to the higher-quality version. If we run the app again we'll notice the image quality is much, much better.
//    Don't forget to resize your images, too!! in RestaurantDetailFragment
    public String getLargeImageUrl(String imageUrl) {
        String largeImageUrl = imageUrl.substring(0, imageUrl.length() - 6).concat("o.jpg");
        return largeImageUrl;
    }
}
