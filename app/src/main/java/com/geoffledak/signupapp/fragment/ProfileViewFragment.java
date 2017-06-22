package com.geoffledak.signupapp.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.geoffledak.signupapp.R;
import com.geoffledak.signupapp.adapter.FoodAdapter;
import com.geoffledak.signupapp.model.SearchResult;
import com.geoffledak.signupapp.utils.PrefKeys;
import com.geoffledak.signupapp.utils.PrefUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by geoff on 6/21/17.
 */

public class ProfileViewFragment extends Fragment {

    private static final String TAG = ProfileViewFragment.class.getSimpleName();

    private View mView;
    private Button mEditButton;
    private ImageView mAvatar;
    private LinearLayout mAvatarContainer;
    private SearchResult mSearchResult;
    private RecyclerView mFoodList;
    private FoodAdapter mFoodAdapter;
    private LinearLayout mEmptyView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_view_profile, container, false);
        mAvatar = (ImageView) mView.findViewById(R.id.avatar);
        mAvatarContainer = (LinearLayout) mView.findViewById(R.id.avatar_container);
        mAvatarContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleAvatarClick();
            }
        });
        mEditButton = (Button) mView.findViewById(R.id.edit_button);
        mEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleEditButtonClick();
            }
        });

        mFoodList = (RecyclerView) mView.findViewById(R.id.food_list);
        mFoodList.setLayoutManager(new LinearLayoutManager(getContext()));

        // this null check makes the list maintain it's proper
        // position when returning from an image detail fragment
        if( mFoodAdapter == null )
            mFoodAdapter = new FoodAdapter(getContext());

        mFoodList.setAdapter(mFoodAdapter);
        mEmptyView = (LinearLayout) mView.findViewById(R.id.empty_view);

        populateProfileInfo();
        populateFoodList();

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Profile");

        return mView;
    }


    private void populateProfileInfo() {

        TextView username = (TextView) mView.findViewById(R.id.username_text);
        TextView firstname = (TextView) mView.findViewById(R.id.first_name_text);
        TextView lastname = (TextView) mView.findViewById(R.id.last_name_text);
        TextView favoriteFood = (TextView) mView.findViewById(R.id.favorite_food_text);

        username.setText(PrefUtils.getString(getContext(), PrefKeys.KEY_PREFERENCES_USERNAME));
        firstname.setText(PrefUtils.getString(getContext(), PrefKeys.KEY_PREFERENCES_FIRSTNAME));
        lastname.setText(PrefUtils.getString(getContext(), PrefKeys.KEY_PREFERENCES_LASTNAME));
        favoriteFood.setText(PrefUtils.getString(getContext(), PrefKeys.KEY_PREFERENCES_FAVORITE_FOOD));

        String avatarUrl = PrefUtils.getString(getContext(), PrefKeys.KEY_PREFERENCES_AVATAR_URL);

        Drawable defaultUserImage = ContextCompat.getDrawable(getContext(), R.drawable.default_user_image);

        if( !avatarUrl.isEmpty() ) {
            Glide.with(getContext()).load(avatarUrl).error(defaultUserImage).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(mAvatar);
        }
        else
            mAvatar.setImageDrawable(defaultUserImage);
    }


    private void populateFoodList() {

        String favoriteFood = PrefUtils.getString(getContext(), PrefKeys.KEY_PREFERENCES_FAVORITE_FOOD);

        if( !favoriteFood.isEmpty() ) {

            mFoodList.setVisibility(View.VISIBLE);
            mEmptyView.setVisibility(View.GONE);
            try {
                loadImageData(favoriteFood);
            } catch (Exception exception) {
                Log.e(TAG, "Failed to load json data!");
            }
        }
        else {
            mFoodList.setVisibility(View.GONE);
            mEmptyView.setVisibility(View.VISIBLE);
            // The cornerstone of any nutritious breakfast
            ImageView tastyBurger = (ImageView) mView.findViewById(R.id.tasty_burger);
            Glide.with(getContext()).load("https://media.giphy.com/media/IOjBHQRLHOjC0/giphy.gif").into(tastyBurger);
        }
    }


    private void loadImageData(String favoriteFood) {

        final OkHttpClient client = new OkHttpClient();
        final Gson gson = new Gson();

        String search = "http://api.flickr.com/services/feeds/photos_public.gne?nojsoncallback=1&tagmode=any&format=json&tags=" + favoriteFood;
        final Request request = new Request.Builder().url(search).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                Type collectionType = new TypeToken<SearchResult>() {}.getType();
                mSearchResult = (SearchResult) gson.fromJson( response.body().charStream(), collectionType );
                populateImageList();
            }
        });
    }


    private void populateImageList() {

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mFoodAdapter.setItemList(mSearchResult.getItems());
                mFoodAdapter.notifyDataSetChanged();
            }
        });
    }


    private void handleEditButtonClick() {

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_content_container, new ProfileEditFragment()).commit();
    }


    private void handleAvatarClick() {

        if( !TextUtils.isEmpty(PrefUtils.getString(getContext(), PrefKeys.KEY_PREFERENCES_AVATAR_URL)) ) {

            Bundle args = new Bundle();
            args.putBoolean(PrefKeys.KEY_IS_AVATAR_URL, true);
            args.putString(PrefKeys.KEY_IMAGE_URL, PrefUtils.getString(getContext(), PrefKeys.KEY_PREFERENCES_AVATAR_URL));

            ImageDetailDetailFragment fragment = new ImageDetailDetailFragment();
            fragment.setArguments(args);

            getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.main_content_container, fragment).commit();
        }
    }

}
