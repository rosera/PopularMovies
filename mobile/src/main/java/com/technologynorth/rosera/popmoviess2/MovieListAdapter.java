package com.technologynorth.rosera.popmoviess2;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by rosera on 11/07/16.
 */

public class MovieListAdapter extends ArrayAdapter<Poster> {

    private static final String TAG_NAME = MovieListAdapter.class.getSimpleName();

    Activity mActivity;
    ArrayList<Poster> mPosters;

    public MovieListAdapter(Activity context,
                            ArrayList<Poster> Posters) {

        super(context, 0, Posters) ;

        this.mActivity  = context;
        this.mPosters   = Posters;
    }

    /*
     * Name: ViewHolder
     * Comment: Design pattern for information retrieval
     */
    private static class ViewHolder {
        ImageView mImageViewFilm;
    }


    @Override
    public int getCount() {
        return mPosters.size();
    }

    @Override
    public Poster getItem(int position) {
        return mPosters.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Name: getView
     * Comment: Populate the GridView item
     * Tasks:
     * 1. If view not seen previously
     * 2.   Inflate the view
     * 3.   Inflate the imageView
     * 4.   setTag - hold the new information
     * 5. Else
     * 6.   getTag - access the existing information
     * 7. Fetch and load the image into the inflated imageView
     */

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder  filmViewHolder;

        if (view == null) {
            // TODO: Inflate the GridView
            view = LayoutInflater.from(mActivity).inflate(R.layout.gridview_movie_detail, null);
            filmViewHolder = new ViewHolder();

            // TODO: Inflate the ImageView
            filmViewHolder.mImageViewFilm   = (ImageView) view.findViewById(R.id.imageViewFilm);
            view.setTag(filmViewHolder);
        } else {

            filmViewHolder = (ViewHolder) view.getTag();
        }

        // TODO: Use Picasso to fetch and load images into the ImageView
        Picasso.with(mActivity)
                .load(mPosters.get(position).mThumbnail)
//                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(filmViewHolder.mImageViewFilm);

        return view;
    }
}
