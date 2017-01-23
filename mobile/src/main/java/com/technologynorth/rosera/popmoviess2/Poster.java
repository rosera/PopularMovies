package com.technologynorth.rosera.popmoviess2;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rosera on 11/07/16.
 */
public class Poster implements Parcelable {
    // Debug TAG
    private static final String TAG_NAME = Poster.class.getSimpleName();

    // TODO: Add member variables
    String mID;
    String mThumbnail;
    String mTitle;
    double mRating;


    /*
     * Name: Poster
     * Comment: Constructor
     */
    public Poster(String strID, String strThumbnail, String strTitle, double dRating) {
        this.mID            = strID;
        this.mThumbnail     = strThumbnail;
        this.mTitle         = strTitle;
        this.mRating        = dRating;
    }

    // TODO: Add the getter methods

    /*
     * Get the ID of the movie
     */
    public String getID() {
        return mID;
    }

    /*
     * Get the thumbnail for the poster image
     */
    public String getThumbnail() {
        return mThumbnail;
    }


    public String getTitle() { return mTitle; }

    public double getRating() { return mRating; }


    // TODO: Add parcelable methods
    private Poster(Parcel in) {
        this.mID                = in.readString();
        this.mThumbnail         = in.readString();
        this.mTitle             = in.readString();
        this.mRating            = in.readDouble();
    }

    /*
     * Parcelable functions
     */
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mID);
        dest.writeString(this.mThumbnail);
        dest.writeString(this.mTitle);
        dest.writeDouble(this.mRating);
    }

    public final Parcelable.Creator<Poster> CREATOR = new Parcelable.Creator<Poster>() {
        @Override
        public Poster createFromParcel(Parcel parcel) {
            return new Poster(parcel);
        }

        @Override
        public Poster[] newArray(int i) {
            return new Poster[i];
        }
    };
}
