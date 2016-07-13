package com.technologynorth.rosera.popmoviess2;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rosera on 11/07/16.
 */
public class Film implements Parcelable {

    // TODO: Debug TAG
    private static final String TAG_NAME = Film.class.getSimpleName();

    // TODO: Add member variables
    String mID;
    String mTitle;
    String mYear;
    String mTrailerPrimaryUri;
    String mTrailerSecondaryUri;
    String mThumbnail;
    String mOverview;
    String mRating;

//    private List<Film> films;

    /*
     * Name: Film
     * Comment: Constructor
     */
    public Film(String strID, String strTitle, String strYear, String strTrailer, String strThumbnail, String strOverview, String strRating) {
        this.mID                = strID;
        this.mTitle             = strTitle;
        this.mYear              = strYear;
        this.mTrailerPrimaryUri = strTrailer;
        this.mThumbnail         = strThumbnail;
        this.mOverview          = strOverview;
        this.mRating            = strRating;
    }

    /*
     * Get the ID of the movie
     */
    public String getID() {
        return mID;
    }

    /*
     * Get the title of the movie
     */
    public String getTitle() {
        return mTitle;
    }

    /*
     * Get the year of the movie
     */
    public String getYear() {
        return mYear;
    }


    /*
 * Get the trailer Uri
 */
    public String getTrailerPrimaryUri() {
        return mTrailerPrimaryUri;
    }


    /*
     * Get the trailer Uri
     */
    public String getTrailerSecondaryUri() {
        return mTrailerSecondaryUri;
    }


    /*
     * Get the thumbnail for the poster image
     */
    public String getThumbnail() {
        return mThumbnail;
    }


    /*
     * Get the film overview
     */
    public String getOverview() {
        return mOverview;
    }

    /*
     * Get the film rating
     */
    public String getRating() {
        return mRating;
    }

    private Film(Parcel in) {
        this.mID                = in.readString();
        this.mTitle             = in.readString();
        this.mYear              = in.readString();
        this.mTrailerPrimaryUri = in.readString();
        this.mThumbnail         = in.readString();
        this.mOverview          = in.readString();
        this.mRating            = in.readString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mID);
        dest.writeString(this.mTitle);
        dest.writeString(this.mYear);
        dest.writeString(this.mTrailerPrimaryUri);
        dest.writeString(this.mThumbnail);
        dest.writeString(this.mOverview);
        dest.writeString(this.mRating);
    }

    public final Parcelable.Creator<Film> CREATOR = new Parcelable.Creator<Film>() {
        @Override
        public Film createFromParcel(Parcel parcel) {
            return new Film(parcel);
        }

        @Override
        public Film[] newArray(int i) {
            return new Film[i];
        }
    };
}

