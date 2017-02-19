package com.technologynorth.rosera.popmoviess2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by rosera on 19/02/17.
 */

public class MediaListAdapter  extends RecyclerView.Adapter<MediaListAdapter.ViewHolder>{

    private ArrayList<Media>    mMediaInformation;
    private int                 mRowLayout;
    private Context             mContext;

    // TODO : Add click listener for RecyclerView media items
//    ItemClickListener    mediaItemClickListener;

   /*
    * Class Name: ViewHolder
    * Description: Implement a view holder pattern
    *
    */

    public static class ViewHolder extends RecyclerView.ViewHolder {
//        LinearLayout    mediaLayout;
        TextView        mediaTitle;
        ImageView       mediaThumbnail;


       /*
        * Name: ViewHolder
        * @return void
        *  Description: Initiate the view holder class values
        *
        */

        public ViewHolder (View v) {
            super(v);

//            mediaLayout     = (RelativeLayout) v.findViewById(R.layout.recycler_media_item);
            mediaTitle      = (TextView)v.findViewById(R.id.textViewTitle);
            mediaThumbnail  = (ImageView)v.findViewById(R.id.imageViewMedia);
        }
    }

   /*
    * Name: MediaAdapter
    * @return void
    *  Description: Initiate the values for the adapter
    *
    */

    public MediaListAdapter(ArrayList<Media> mediaInformation, int rowLayout, Context context) {
        this.mMediaInformation   = mediaInformation;
        this.mRowLayout          = rowLayout;
        this.mContext            = context;
    }

    /*
     * Name: UdacityAdapter.UdacityViewHolder
     * @return View (UdacityViewHolder)
     *  Description: Initiate the view
     *
     */

    @Override
    public MediaListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mRowLayout, parent, false);

        final ViewHolder mViewHolder = new ViewHolder(view);

        return mViewHolder;
    }

    /*
     * Name: onBindViewHolder
     * @return void
     *  Description: Populate the view
     *  Comment: Will need to refactored, once I decide how to model the TMDB data.
     *
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        // Load on-screen title into the RecyclerView
        holder.mediaTitle.setText(mMediaInformation.get(position).getTitle() );

        // Load on-screen thumbnail into the RecyclerView
        Picasso.with(mContext)
                .load(mMediaInformation.get(position).getThumbnail())
                .into(holder.mediaThumbnail);

    }

   /*
    * Name: getItemCount
    * @return int
    *  Description: Find the size of the items passed for the tutorials object
    *
    */

    @Override
    public int getItemCount() {
        return mMediaInformation.size();
    }


            /*
     * Utility function
     * Name: extractYouTubeId
     * Description: Get the id from the YouTube URI
     *
     */

    public String extractYouTubeId(String url) throws MalformedURLException {
        String query = new URL(url).getQuery();
        String[] param = query.split("&");
        String id = null;
        for (String row : param) {
            String[] param1 = row.split("=");
            if (param1[0].equals("v")) {
                id = param1[1];
            }
        }
        return id;
    }

}
