package com.technologynorth.rosera.popmoviess2;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.technologynorth.rosera.popmoviess2.dummy.DummyContent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * A fragment representing a single Movie detail screen.
 * This fragment is either contained in a {@link MovieListActivity}
 * in two-pane mode (on tablets) or a {@link MovieDetailActivity}
 * on handsets.
 */
public class MovieDetailFragment extends Fragment {

    private static final String TAG_NAME = MovieDetailFragment.class.getSimpleName();
    private static final String TMDB_API_KEY = BuildConfig.TMDB_API_KEY;
    private static final String SER_TOKEN = "SER_TOKEN";

    final String WATCH_YOUTUBE = "https://www.youtube.com/watch?v=";

    final int TMDB_MOVIES = 0;
    final int TMDB_TRAILERS = 1;
    final int TMDB_REVIEWS = 2;
    final int TMDB_SIMILAR = 3;

    private static View rootView;

    private ArrayList<Film> mFilmInformation    = null;

    // TODO: Do this initialisation in a method
    private String mID          = "";
    private String mPoster      = "";
    private String mTitle       = "";
    private String mYear        = "";
    private String mRuntime     = "";
    private String mTrailer1    = "";
    private String mTrailer2    = "";
    private String mThumbnail   = "";
    private String mOverview    = "";
    private String mRating      = "";
    private String mTrailer1_name = "";
    private String mTrailer2_name = "";
    private String mMinPoster1 = "";
    private String mMinPoster2 = "";
    private String mMinPoster3 = "";
    private String mMinPoster4 = "";
    private String mMinPoster1_title = "";
    private String mMinPoster2_title = "";
    private String mMinPoster3_title = "";
    private String mMinPoster4_title = "";
    private String mMinID1 = "";
    private String mMinID2 = "";
    private String mMinID3 = "";
    private String mMinID4 = "";

    private int mReviewArticles = 0;
    private int mCurrentArticle = 0;

    private String mReviewAuthor1 = "";
    private String mReviewContent1 = "";
    private String mReviewAuthor2 = "";
    private String mReviewContent2 = "";
    private String mReviewAuthor3 = "";
    private String mReviewContent3 = "";
    private String mReviewAuthor4 = "";
    private String mReviewContent4 = "";

    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "ID";
    public static final String ARG_ITEM_URI = "URI";
    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MovieDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mID = getArguments().getString(ARG_ITEM_ID);
        }

        if (getArguments().containsKey(ARG_ITEM_URI)) {
            mPoster = getArguments().getString(ARG_ITEM_URI);
        }

        // Todo: Allocate memory for the film information
        if (mFilmInformation == null)
            mFilmInformation = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        rootView = inflater.inflate(R.layout.movie_detail, container, false);
        rootView = inflater.inflate(R.layout.movie_detail, container, false);


//        View view = getActivity().setContentView(R.layout.activity_movie_detail);
//        view = getActivity().getView();
//        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab_test);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        // TODO: Add listener for buttons
        Button btnTrailer1 = (Button) rootView.findViewById(R.id.btnTrailer1);
        btnTrailer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Play Youtube trailer
                YouTubeTrailer(mTrailer1);
            }
        });

        Button btnTrailer2 = (Button) rootView.findViewById(R.id.btnTrailer2);
        btnTrailer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Play Youtube trailer
                YouTubeTrailer(mTrailer2);
            }
        });

        Button btnNext = (Button) rootView.findViewById(R.id.btnReviewNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Show the next review
                setReviewButtons(true);
            }
        });

        Button btnPrevious = (Button) rootView.findViewById(R.id.btnReviewPrevious);
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Show the previous review
                setReviewButtons(false);
            }
        });


        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        // TODO: Check we have a internet connection

        // TODO: Call TMDB API with the ID
        onRequestMovieAPI(mID, TMDB_TRAILERS);

        onRequestMovieReviews(mID, TMDB_REVIEWS);

        onRequestMovieSimilar(mID, TMDB_SIMILAR);
    }

        /*
     * @Name: getScreenDensity
     * @return void
     *      "w92", "w154", "w185", "w342", "w500", "w780", or "original".
     * @Description: Check the screen density
     */

    public String getScreenDensity() {
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int density = metrics.densityDpi;

        String  strScreenDensity;

        if (density < 280)
            strScreenDensity = "w185/";
        else if (density < 480)
            strScreenDensity = "w342/";
        else if (density < 570)
            strScreenDensity = "w500/";
        else
            strScreenDensity = "w780/";

        return strScreenDensity;
    }

        /*
     * Name: onRequestMovieAPI
     * Comment: Access MovieAPI using the Volley library
     * Tasks:
     * 1. Check Network/Internet available
     * 2. Find "results" object and store it in an JSONArray
     * 3. Iterate through the JSONArray
     * 4.   Read item contents and add to the movie array
     * 5. Send a data set change notification to update the Adapter view
     * 6. Handle Exceptions
     *
     */

    private void onRequestMovieAPI(String mID, int searchType) {
        // TODO: Stage 2: mFilmAPI Query
        final String MOVIE_API_URI = "http://api.themoviedb.org/3/movie/" ;
        final String MOVIE_API_KEY = "?api_key=" + TMDB_API_KEY;
        final String MOVIE_API_VIDEO = "&append_to_response=videos";
        final String MOVIE_API_REVIEWS = "/reviews";
        final String MOVIE_API_SIMILAR = "/similar";
        String strQueryTMDB = "";


        // TODO: Make this a method
        switch (searchType){
            case TMDB_MOVIES: // Movie
                // Get the Sort Order perference

                // Store the api Query
//                strQueryTMDB =  MOVIE_API_URI+mSortOrder+MOVIE_API_KEY;
                break;
            case TMDB_TRAILERS: // Trailers
                strQueryTMDB = MOVIE_API_URI+mID+MOVIE_API_KEY+MOVIE_API_VIDEO;
                break;

            case TMDB_REVIEWS: // Reviews
                strQueryTMDB = MOVIE_API_URI+mID+MOVIE_API_REVIEWS+MOVIE_API_KEY;
                break;

            case TMDB_SIMILAR: // Similar
                strQueryTMDB = MOVIE_API_URI+mID+MOVIE_API_SIMILAR+MOVIE_API_KEY;
                break;

            default:
                break;
        }

        // TODO: sort_by=popularity.desc/popular
        // TODO: sort_by=vote_average.desc/top_rated

        // TODO: Alter setting - "w92", "w154", "w185", "w342", "w500", "w780", or "original".
        // TODO: Add a setting to allow the user to select image size
        final String MOVIE_IMAGE_URI = "http://image.tmdb.org/t/p/";

    /*
     *  JSON Request - Volley JSON example
     */

        // TODO: Request movie information based on ID passed from MovieFragment

        final JsonObjectRequest mJsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, strQueryTMDB, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            // TODO: Make this a method
                            JSONObject videos = response.getJSONObject("videos");
                            JSONArray jsonArray = videos.getJSONArray("results");

                            // TODO: Only take the top two videos
                            // IF Videos are required

                            mTitle = response.getString("original_title");
                            mYear = response.getString("release_date");
                            mOverview = response.getString("overview");
                            mRuntime = response.getString("runtime") + " mins";
                            mThumbnail = MOVIE_IMAGE_URI + getScreenDensity() + response.getString("poster_path");
                            mRating = response.getString("vote_average") + "/10";


                            for (int i=0; i<jsonArray.length(); i++) {
                                JSONObject video = jsonArray.getJSONObject(i);


                                switch(i) {
                                    case 0:
                                        mTrailer1 = WATCH_YOUTUBE + video.getString("key");
                                        mTrailer1_name = video.getString("name");
                                        break;
                                    case 1:
                                        mTrailer2 = WATCH_YOUTUBE + video.getString("key");
                                        mTrailer2_name = video.getString("name");
                                        break;
                                    default:
                                        break;
                                }
                            }

                            // TODO: populate the detail fragment
                            populateMovieDetails();

                        } catch (JSONException ex) {
                            ex.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Log.i("JSON", error.getMessage());
                    }
                });

        // Queue the async request
        Volley.newRequestQueue(getActivity()).add(mJsonObjectRequest);
    }


    private void onRequestMovieReviews(String mID, int searchType) {
        // TODO: Stage 2: mFilmAPI Query
        final String MOVIE_API_URI = "http://api.themoviedb.org/3/movie/" ;
        final String MOVIE_API_KEY = "?api_key=" + TMDB_API_KEY;
        final String MOVIE_API_VIDEO = "&append_to_response=videos";
        final String MOVIE_API_REVIEWS = "/reviews";
        final String MOVIE_API_SIMILAR = "/similar";
        String strQueryTMDB = "";

        // TODO: Make this a method
        switch (searchType){
            case TMDB_MOVIES: // Movie
                // Get the Sort Order perference

                // Store the api Query
//                strQueryTMDB =  MOVIE_API_URI+mSortOrder+MOVIE_API_KEY;
                break;
            case TMDB_TRAILERS: // Trailers
                strQueryTMDB = MOVIE_API_URI+mID+MOVIE_API_KEY+MOVIE_API_VIDEO;
                break;

            case TMDB_REVIEWS: // Reviews
                strQueryTMDB = MOVIE_API_URI+mID+MOVIE_API_REVIEWS+MOVIE_API_KEY;
                break;

            case TMDB_SIMILAR: // Similar
                strQueryTMDB = MOVIE_API_URI+mID+MOVIE_API_SIMILAR+MOVIE_API_KEY;
                break;

            default:
                break;
        }

        // TODO: sort_by=popularity.desc/popular
        // TODO: sort_by=vote_average.desc/top_rated

        // TODO: Alter setting - "w92", "w154", "w185", "w342", "w500", "w780", or "original".
        // TODO: Add a setting to allow the user to select image size
        final String MOVIE_IMAGE_URI = "http://image.tmdb.org/t/p/";

    /*
     *  JSON Request - Volley JSON example
     */

        // TODO: Request movie information based on ID passed from MovieFragment

        final JsonObjectRequest mJsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, strQueryTMDB, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            // TODO: Make this a method
                            JSONArray jsonArray = response.getJSONArray("results");

                            // Store the number of reviews
                            mReviewArticles = jsonArray.length();

                            // Limit the number of reviews to 4 only
                            if (mReviewArticles > 4)
                                mReviewArticles = 4;

                            // Only get the first four images
                            for (int i=0; i<jsonArray.length(); i++) {
                                JSONObject review = jsonArray.getJSONObject(i);

                                switch(i) {
                                    case 0:
                                        mReviewAuthor1 = review.getString("author");
                                        mReviewContent1 = review.getString("content");
                                        break;
                                    case 1:
                                        mReviewAuthor2 = review.getString("author");
                                        mReviewContent2 = review.getString("content");
                                        break;

                                    case 2:
                                        mReviewAuthor3 = review.getString("author");
                                        mReviewContent3 = review.getString("content");
                                        break;

                                    case 3:
                                        mReviewAuthor4 = review.getString("author");
                                        mReviewContent4 = review.getString("content");
                                        break;
                                    default:
                                        break;
                                }
                            }

                            // TODO: populate the detail fragment
                            populateReviewDetails(0);

                        } catch (JSONException ex) {
                            ex.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Log.i("JSON", error.getMessage());
                    }
                });

        // Queue the async request
        Volley.newRequestQueue(getActivity()).add(mJsonObjectRequest);
    }





    private void onRequestMovieSimilar(String mID, int searchType) {
        // TODO: Stage 2: mFilmAPI Query
        final String MOVIE_API_URI = "http://api.themoviedb.org/3/movie/" ;
        final String MOVIE_API_KEY = "?api_key=" + TMDB_API_KEY;
        final String MOVIE_API_VIDEO = "&append_to_response=videos";
        final String MOVIE_API_REVIEWS = "/reviews";
        final String MOVIE_API_SIMILAR = "/similar";
        String strQueryTMDB = "";

        // TODO: Make this a method
        switch (searchType){
            case TMDB_MOVIES: // Movie
                // Get the Sort Order perference

                // Store the api Query
//                strQueryTMDB =  MOVIE_API_URI+mSortOrder+MOVIE_API_KEY;
                break;
            case TMDB_TRAILERS: // Trailers
                strQueryTMDB = MOVIE_API_URI+mID+MOVIE_API_KEY+MOVIE_API_VIDEO;
                break;

            case TMDB_REVIEWS: // Reviews
                strQueryTMDB = MOVIE_API_URI+mID+MOVIE_API_REVIEWS+MOVIE_API_KEY;
                break;

            case TMDB_SIMILAR: // Similar
                strQueryTMDB = MOVIE_API_URI+mID+MOVIE_API_SIMILAR+MOVIE_API_KEY;
                break;

            default:
                break;
        }

        // TODO: sort_by=popularity.desc/popular
        // TODO: sort_by=vote_average.desc/top_rated

        // TODO: Alter setting - "w92", "w154", "w185", "w342", "w500", "w780", or "original".
        // TODO: Add a setting to allow the user to select image size
        final String MOVIE_IMAGE_URI = "http://image.tmdb.org/t/p/";

    /*
     *  JSON Request - Volley JSON example
     */

        // TODO: Request movie information based on ID passed from MovieFragment

        final JsonObjectRequest mJsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, strQueryTMDB, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            // TODO: Make this a method
                            JSONArray jsonArray = response.getJSONArray("results");

                            // Only get the first four images
                            for (int i=0; i<jsonArray.length(); i++) {
                                JSONObject video = jsonArray.getJSONObject(i);

                                switch(i) {
                                    case 0:
                                        mMinPoster1 = MOVIE_IMAGE_URI + "w185" + video.getString("poster_path");
                                        mMinPoster1_title = video.getString("title");
                                        mMinID1 = video.getString("id");
                                        break;
                                    case 1:
                                        mMinPoster2 = MOVIE_IMAGE_URI + "w185" + video.getString("poster_path");
                                        mMinPoster2_title = video.getString("title");
                                        mMinID2 = video.getString("id");
                                        break;

                                    case 2:
                                        mMinPoster3 = MOVIE_IMAGE_URI + "w185" + video.getString("poster_path");
                                        mMinPoster3_title = video.getString("title");
                                        mMinID3 = video.getString("id");
                                        break;

                                    case 3:
                                        mMinPoster4 = MOVIE_IMAGE_URI + "w185" + video.getString("poster_path");
                                        mMinPoster4_title = video.getString("title");
                                        mMinID4 = video.getString("id");
                                        break;
                                    default:
                                        break;
                                }
                            }

                            // TODO: populate the detail fragment
                            populateSimilarDetails();

                        } catch (JSONException ex) {
                            ex.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Log.i("JSON", error.getMessage());
                    }
                });

        // Queue the async request
        Volley.newRequestQueue(getActivity()).add(mJsonObjectRequest);
    }



    /*
     * Populate the onscreen controls once the data has been downloaded
     */
    private void populateMovieDetails() {

//        if (rootView == null) {
//            rootView = this.getView();
//        }

        ImageView imageViewThumbnail = (ImageView) rootView.findViewById(R.id.imageViewPoster);
        // TODO: Use Picasso to fetch and load images into the ImageView
        Picasso.with(getActivity())
                .load(mThumbnail)
                .into(imageViewThumbnail);

        // TODO: Get reference to the UI controls
        TextView tvTitle = (TextView) rootView.findViewById(R.id.textViewTitle);
        tvTitle.setText(mTitle);

        TextView tvYear = (TextView) rootView.findViewById(R.id.textViewYear);
        tvYear.setText(mYear);

        TextView tvOverview = (TextView) rootView.findViewById(R.id.textViewOverview);
        tvOverview.setText(mOverview);

        TextView tvRating = (TextView) rootView.findViewById(R.id.textViewRating);
        tvRating.setText(mRating);

        TextView tvRuntime = (TextView) rootView.findViewById(R.id.tvRuntime);
        tvRuntime.setText(mRuntime);

        // TODO: Only show the trailer buttons if videos found
        Button btnTrailer1 = (Button) rootView.findViewById(R.id.btnTrailer1);
        if (mTrailer1.length()==0) {
            btnTrailer1.setText("Trailer-1");
            btnTrailer1.setVisibility(View.INVISIBLE);
        } else {
            btnTrailer1.setText(mTrailer1_name);
            btnTrailer1.setVisibility(View.VISIBLE);
        }

        Button btnTrailer2 = (Button) rootView.findViewById(R.id.btnTrailer2);
        if (mTrailer2.length()==0) {
            btnTrailer2.setText("Trailer-2");
            btnTrailer2.setVisibility(View.INVISIBLE);
        } else {
            btnTrailer2.setText(mTrailer2_name);
            btnTrailer2.setVisibility(View.VISIBLE);
        }
    }

    /*
     * Use an intent to initiate Youtube via Browser or App
     * NB: Add putExtra to force fullscreen on trailer display
     *
     */
    // TODO: Add intent to play video on youtube
    public void YouTubeTrailer(String trailerReference) {

        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(trailerReference));
            intent.putExtra("force_fullscreen", true);
            startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            Log.i("Error:", "Playing video on YouTube Application");
        }
    }

    public void populateReviewDetails(int Review) {

        Button btnReview;
        TextView tvReview = (TextView) rootView.findViewById(R.id.tvReview);
        TextView tvReviewArticles = (TextView) rootView.findViewById(R.id.tvReviewArticles);

        if (mReviewArticles == 0) {
            tvReview.setText("No reviews found for " + mTitle);

            // Reset the counter
            tvReviewArticles.setText("N/A");

            // Dont show the navigation buttons
            btnReview = (Button) rootView.findViewById(R.id.btnReviewNext);
            btnReview.setVisibility(View.INVISIBLE);

            btnReview = (Button) rootView.findViewById(R.id.btnReviewPrevious);
            btnReview.setVisibility(View.INVISIBLE);

        }
        else if (mReviewArticles == 1) {
            // Indicate the number of reviews
            tvReviewArticles.setText("1/"+Integer.toString(mReviewArticles));

            // Show the review
            tvReview = (TextView) rootView.findViewById(R.id.tvReview);
            tvReview.setText(mReviewContent1);

            // Dont show the navigation buttons
            btnReview = (Button) rootView.findViewById(R.id.btnReviewNext);
            btnReview.setVisibility(View.INVISIBLE);

            btnReview = (Button) rootView.findViewById(R.id.btnReviewPrevious);
            btnReview.setVisibility(View.INVISIBLE);
        }
        else {
            // Indicate the number of reviews
            tvReviewArticles.setText("1/"+Integer.toString(mReviewArticles));

            // Show the review
            tvReview = (TextView) rootView.findViewById(R.id.tvReview);
            tvReview.setText(mReviewContent1);

            // Dont show the navigation buttons
            btnReview = (Button) rootView.findViewById(R.id.btnReviewNext);
            btnReview.setVisibility(View.VISIBLE);

            btnReview = (Button) rootView.findViewById(R.id.btnReviewPrevious);
            btnReview.setVisibility(View.VISIBLE);
        }
    }

    public void setReviewButtons(boolean nextArticle) {
        TextView tvReview = (TextView) rootView.findViewById(R.id.tvReview);
        TextView tvReviewArticles = (TextView) rootView.findViewById(R.id.tvReviewArticles);


        if (nextArticle) {

            if (mCurrentArticle < mReviewArticles)
                mCurrentArticle = mCurrentArticle + 1;
        } else {
            if (mCurrentArticle > 0)
                mCurrentArticle = mCurrentArticle - 1;
        }

        // Display the correct article
        switch (mCurrentArticle) {
            case 0:
                tvReview.setText(mReviewContent1);
                tvReviewArticles.setText(Integer.toString(mCurrentArticle+1)+"/"+Integer.toString(mReviewArticles));
                break;

            case 1:
                tvReview.setText(mReviewContent2);
                tvReviewArticles.setText(Integer.toString(mCurrentArticle+1)+"/"+Integer.toString(mReviewArticles));
                break;

            case 2:
                tvReview.setText(mReviewContent3);
                tvReviewArticles.setText(Integer.toString(mCurrentArticle+1)+"/"+Integer.toString(mReviewArticles));
                break;

            case 4:
                tvReview.setText(mReviewContent4);
                tvReviewArticles.setText(Integer.toString(mCurrentArticle)+"/"+Integer.toString(mReviewArticles));
                break;

            default:
                break;
        }
    }

    public void populateSimilarDetails() {
        // TODO: Use Picasso to fetch and load images into the ImageView
        if (mMinPoster1.length() > 0) {
            ImageView imageViewPoster1 = (ImageView) rootView.findViewById(R.id.imageViewMinPoster1);
            Picasso.with(getActivity())
                    .load(mMinPoster1)
                    .into(imageViewPoster1);
        }

        if (mMinPoster2.length() > 0) {
            ImageView imageViewPoster2 = (ImageView) rootView.findViewById(R.id.imageViewMinPoster2);
            Picasso.with(getActivity())
                    .load(mMinPoster2)
                    .into(imageViewPoster2);
        }

        if (mMinPoster3.length() > 0) {
            ImageView imageViewPoster3 = (ImageView) rootView.findViewById(R.id.imageViewMinPoster3);
            Picasso.with(getActivity())
                    .load(mMinPoster3)
                    .into(imageViewPoster3);
        }

        if (mMinPoster4.length() > 0) {
            ImageView imageViewPoster4 = (ImageView) rootView.findViewById(R.id.imageViewMinPoster4);
            Picasso.with(getActivity())
                    .load(mMinPoster4)
                    .into(imageViewPoster4);
        }
    }

}
