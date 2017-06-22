package com.geoffledak.signupapp.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.geoffledak.signupapp.R;
import com.geoffledak.signupapp.fragment.ImageDetailDetailFragment;
import com.geoffledak.signupapp.model.ImageItem;
import com.geoffledak.signupapp.utils.PrefKeys;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by geoff on 6/21/17.
 */

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.CustomViewHolder> {

    Context mContext;
    private List<ImageItem> mImageItemList;


    public FoodAdapter(Context context) {
        mContext = context;
        mImageItemList = new ArrayList<>();
    }

    public FoodAdapter(Context context, List<ImageItem> imageItemList) {
        mContext = context;
        mImageItemList = imageItemList;
    }

    public void setItemList(List<ImageItem> imageItemList) {
        mImageItemList = imageItemList;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_list_item, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {

        SpannableString tagsHeading = new SpannableString("Tags: ");
        tagsHeading.setSpan(new StyleSpan(Typeface.BOLD), 0, tagsHeading.length(), 0);

        holder.mImageTags.setText("");
        holder.mImageTags.append(tagsHeading);
        holder.mImageTags.append(mImageItemList.get(position).getTags());

        String imageUrl = mImageItemList.get(position).getMedia().getM();
        Glide.with(mContext).load(imageUrl).into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        if (mImageItemList == null)
            return 0;
        else
            return mImageItemList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView mImageView;
        TextView mImageTags;

        public CustomViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            mImageView = (ImageView) view.findViewById(R.id.image);
            mImageTags = (TextView) view.findViewById(R.id.image_tags);
        }


        @Override
        public void onClick(View v) {

            Bundle args = new Bundle();
            args.putString(PrefKeys.KEY_IMAGE_URL, mImageItemList.get(getAdapterPosition()).getMedia().getM());

            ImageDetailDetailFragment fragment = new ImageDetailDetailFragment();
            fragment.setArguments(args);

            ((AppCompatActivity) mContext).getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.main_content_container, fragment).commit();
        }
    }

}
