package com.example.dilip.sampleproject.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dilip.sampleproject.R;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.dilip.sampleproject.app.AppController;
import com.example.dilip.sampleproject.model.CountryDetails;

import java.util.List;

/**
 * Created by Dilip on 28-05-2016.
 */
public class CustomListAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<CountryDetails> countryItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public CustomListAdapter(Activity activity, List<CountryDetails> countryItems) {
        this.activity = activity;
        this.countryItems = countryItems;
    }

    @Override
    public int getCount() {
        return countryItems.size();
    }

    @Override
    public Object getItem(int location) {
        return countryItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        holder = new ViewHolder();

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        holder.thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.thumbnail);
       holder.title = (TextView) convertView.findViewById(R.id.title);
       holder.description = (TextView) convertView.findViewById(R.id.description);


        // getting movie data for the row
        CountryDetails m = countryItems.get(position);

        // thumbnail image
        holder.thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);

        // title
        holder.title.setText(m.getTitle());

        // description
        holder.description.setText(m.getDescription());


        return convertView;
    }


    public static class ViewHolder {

        public TextView title;
        public TextView description;
        public NetworkImageView thumbNail;

    }
}
