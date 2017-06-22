package com.geoffledak.signupapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.geoffledak.signupapp.R;
import com.geoffledak.signupapp.utils.PrefKeys;

/**
 * Created by geoff on 6/21/17.
 */

public class ImageDetailDetailFragment extends Fragment {

    View mView;
    ImageView mImage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_avatar_detail, container, false);
        mImage = (ImageView) mView.findViewById(R.id.detail_image);

        String imageUrl = getArguments().getString(PrefKeys.KEY_IMAGE_URL);

        if( getArguments().containsKey(PrefKeys.KEY_IS_AVATAR_URL) ) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Avatar");
            Glide.with(getContext()).load(imageUrl).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(mImage);
        }
        else {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Image Details");
            Glide.with(getContext()).load(imageUrl).into(mImage);
        }

        return mView;
    }
}
