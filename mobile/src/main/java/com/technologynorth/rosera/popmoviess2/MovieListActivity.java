package com.technologynorth.rosera.popmoviess2;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * An activity representing a list of Movies. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link MovieDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class MovieListActivity extends AppCompatActivity {

    private static final String TAG_NAME = MovieListActivity.class.getSimpleName();

    // TODO: Use a valid The Movies DB KEY from the gradle.properties file
    private static final String API_KEY = BuildConfig.TMDB_API_KEY;
    private static final String SER_TOKEN = "SER_TOKEN";

    private ArrayAdapter<Poster>    mMovieAdapter       = null;
    private GridView                mGridView           = null;
    private ArrayList<Poster>       mPosterInformation  = null;
    private String                  mScreenDensity      = null;


    // TODO: Refactor flags / Save the sort preference in saveInstanceState
    private String                  mSortOrder          = "popular";
    private boolean                 mSortOrderPref      = false;


    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        // Todo: Allocate memory for the film information
        if (mPosterInformation == null) {
            mPosterInformation = new ArrayList<>();
        }

        // TODO: Add GridView
        // Get a reference to the ListView and attach the adapter to it
        mGridView = (GridView) findViewById(R.id.movie_list);

        // TODO: Check network/internet status
        if (getOnlineStatus()) {

            // TODO: Check the screen density
            getScreenDensity();

            // TODO: Call sort setting before making the API request
            setSortMovieAPI(mSortOrderPref);

            // TODO: Call to populate the film information
            onRequestMovieAPI();
        }
        else {
            // No network connection currently available
//            Toast.makeText(getActivity(),
            Toast.makeText(getApplicationContext(),
                    "Please check your network connection", Toast.LENGTH_SHORT).show();
        }

        // Todo: Populate the adapter by instantiating the custom adapter
        mMovieAdapter = new MovieListAdapter(this, mPosterInformation);

        // Todo: Set the listViewMovies content to the adapter
        mGridView.setAdapter((mMovieAdapter));

        // Todo: Add an onItemClick method to display the movie details
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // TODO: Pass the selected film information
                Poster myFilm = mPosterInformation.get(position);

                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(MovieDetailFragment.ARG_ITEM_ID, myFilm.getID());
                    MovieDetailFragment fragment = new MovieDetailFragment();
                    fragment.setArguments(arguments);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.movie_detail_container, fragment)
                            .commit();
                } else {
                    // TODO: Call new activity for the details screen
                    Intent intent = new Intent(getApplicationContext(), MovieDetailActivity.class)
                            .putExtra("URI", myFilm.getThumbnail())
                            .putExtra("ID", myFilm.getID());

                    startActivity(intent);
                }
            }
        });


//        View recyclerView = findViewById(R.id.movie_list);
//        assert recyclerView != null;
//        setupRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.movie_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.most_popular:
                setSortMovieAPI(false);
                return true;

            case R.id.highest_rated:
                setSortMovieAPI(true);
                return true;

            default:
                break;
        }

        // TODO: Dont forget to store the state

        return super.onOptionsItemSelected(item);
    }


    /*
     * Name: getOnlineStatus
     * @return boolean - flag to indicate network status
     *  False:  Offline
     *  True:   Online
     * Description: Check on the device network status
     * Comment: Standard method on which to check the network availability
     *          Ensure required permissions have been added to Android.Manifest
     */

    public boolean getOnlineStatus() {
//        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    /*
     * @Name: getScreenDensity
     * @return void
     *      "w92", "w154", "w185", "w342", "w500", "w780", or "original".
     * @Description: Check the screen density
     */

    public void getScreenDensity() {
        DisplayMetrics metrics = new DisplayMetrics();
//        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int density = metrics.densityDpi;

        if (density < 280)
            mScreenDensity = "w185/";
        else if (density < 480)
            mScreenDensity = "w342/";
        else if (density < 570)
            mScreenDensity = "w500/";
        else
            mScreenDensity = "w780/";
    }

    public void setSortMovieAPI(boolean sortFlag) {

        int sortMovieAPI;

        // TODO: Refactor this method
        // to use boolean rather than int

        if (!sortFlag) {
            sortMovieAPI = 0;
        } else {
            sortMovieAPI = 1;
        }

        switch(sortMovieAPI) {

            // TODO: sort_by=popularity.desc
            // TODO: sort_by=vote_average.desc

            // TODO: Update method to remove two sort order variables

            case 0:
//                mSortOrder = "sort_by=popularity.desc";
                mSortOrder = "popular";
                mSortOrderPref = false;
                // TODO: Check network/internet status
                if (getOnlineStatus()) {
                    // TODO: Call to populate the film information
                    onRequestMovieAPI();
                }
                else {
                    // No network connection currently available
//                    Toast.makeText(getActivity(),
                    Toast.makeText(getApplicationContext(),
                            "Please check your network connection", Toast.LENGTH_SHORT).show();
                }
                break;

            case 1:
//                mSortOrder = "sort_by=vote_average.desc";
//                mSortOrder = "sort_by=top_rated.desc";
                mSortOrder = "top_rated";
                mSortOrderPref = true;

                // TODO: Check network/internet status
                if (getOnlineStatus()) {
                    // TODO: Call to populate the film information
                    onRequestMovieAPI();
                }
                else {
                    // No network connection currently available
//                    Toast.makeText(getActivity(),
                    Toast.makeText(getApplicationContext(),
                            "Please check your network connection", Toast.LENGTH_SHORT).show();
                }

                break;
            default:
                break;
        }
    }

    /*
     * Name: onRequestMovieAPI
     * Comment: Access MovieAPI using the Volley library
     * Tasks:
     * 1. Check Network/Internet available
     * 2. Find "results" object and store it in an JSONArray
     * 3. Iterate through the JSONArray
     * 4.   Read item contents and add to the movie array
     * 5. Send a dataset change notification to update the Adapter view
     * 6. Handle Exceptions
     *
     */

    private void onRequestMovieAPI() {
        // TODO: Add the relevant mFilmAPI Query
        final String MOVIE_API_URI = "http://api.themoviedb.org/3/movie/" ;

        // TODO: Add a valid API KEY
        final String MOVIE_API_KEY = "?api_key=" + API_KEY;

        // TODO: sort_by=popularity.desc/popular
        // TODO: sort_by=vote_average.desc/top_rated

        // TODO: Alter setting - "w92", "w154", "w185", "w342", "w500", "w780", or "original".
        // TODO: Add a setting to allow the user to select image size
        final String MOVIE_IMAGE_URI = "http://image.tmdb.org/t/p/";

        final JsonObjectRequest mJsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, MOVIE_API_URI+mSortOrder+MOVIE_API_KEY, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // TODO: Get the response array
                            JSONArray jsonArray = response.getJSONArray("results");
                            String id;
                            String title;
                            double  rating;
                            String poster_uri;

                            // TODO: Clear existing information
                            mPosterInformation.clear();

                            // TODO: Loop through the array
                            for (int i=0; i < jsonArray.length(); i++) {
                                // TODO: Get the movie object
                                JSONObject movie = jsonArray.getJSONObject(i);

                                // Get the required details: ID + poster_path
                                id = movie.getString("id");
                                title = movie.getString("title");
                                rating = movie.getDouble("vote_average");
                                poster_uri = movie.getString("poster_path");

                                // TODO: Add to movie structure
                                mPosterInformation.add(new Poster(id,
                                        MOVIE_IMAGE_URI+mScreenDensity+poster_uri, title, rating));
                            }

                            // TODO: Notify a data set change
                            mMovieAdapter.notifyDataSetChanged();

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
        Volley.newRequestQueue(getApplicationContext()).add(mJsonObjectRequest);
    }

}
