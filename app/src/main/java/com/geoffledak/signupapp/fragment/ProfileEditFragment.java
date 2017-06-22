package com.geoffledak.signupapp.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.geoffledak.signupapp.R;
import com.geoffledak.signupapp.utils.PrefKeys;
import com.geoffledak.signupapp.utils.PrefUtils;

/**
 * Created by geoff on 6/21/17.
 */

public class ProfileEditFragment extends Fragment {

    private View mView;

    private EditText mUsername;
    private EditText mPassword;
    private EditText mPasswordAgain;
    private EditText mFirstName;
    private EditText mLastName;
    private EditText mAvatarUrl;
    private EditText mFavoriteFood;
    private Button mButton;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        mUsername = (EditText) mView.findViewById(R.id.edit_username);
        mPassword = (EditText) mView.findViewById(R.id.edit_password);
        mPasswordAgain = (EditText) mView.findViewById(R.id.edit_password_again);
        mFirstName = (EditText) mView.findViewById(R.id.edit_first_name);
        mLastName = (EditText) mView.findViewById(R.id.edit_last_name);
        mAvatarUrl = (EditText) mView.findViewById(R.id.edit_avatar_url);
        mFavoriteFood = (EditText) mView.findViewById(R.id.edit_favorite_food);

        mButton = (Button) mView.findViewById(R.id.save_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSaveButtonClick();
            }
        });

        setSpecialStyling();
        populateEditTexts();

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Edit Profile");

        return mView;
    }


    private void handleSaveButtonClick() {

        boolean validData = false;

        String username = mUsername.getText().toString();
        String password = mPassword.getText().toString();
        String passwordAgain = mPasswordAgain.getText().toString();
        String firstname = mFirstName.getText().toString();
        String lastname = mLastName.getText().toString();
        String avatarUrl = mAvatarUrl.getText().toString();
        String favoriteFood = mFavoriteFood.getText().toString();

        if( TextUtils.isEmpty(username) )
            Toast.makeText(getContext(), "Please enter a username", Toast.LENGTH_SHORT).show();
        else if( TextUtils.isEmpty(password) )
            Toast.makeText(getContext(), "Please enter a password", Toast.LENGTH_SHORT).show();
        else if( TextUtils.isEmpty(passwordAgain) )
            Toast.makeText(getContext(), "Please enter a password again", Toast.LENGTH_SHORT).show();
        else if( !password.equals(passwordAgain) )
            Toast.makeText(getContext(), "Passwords don't match", Toast.LENGTH_SHORT).show();
        else
            validData = true;

        if( validData ) {

            PrefUtils.saveString(getContext(), PrefKeys.KEY_PREFERENCES_USERNAME, username);
            PrefUtils.saveString(getContext(), PrefKeys.KEY_PREFERENCES_PASSWORD, password);
            PrefUtils.saveString(getContext(), PrefKeys.KEY_PREFERENCES_FIRSTNAME, firstname);
            PrefUtils.saveString(getContext(), PrefKeys.KEY_PREFERENCES_LASTNAME, lastname);
            PrefUtils.saveString(getContext(), PrefKeys.KEY_PREFERENCES_AVATAR_URL, avatarUrl);
            PrefUtils.saveString(getContext(), PrefKeys.KEY_PREFERENCES_FAVORITE_FOOD, favoriteFood);

            showProfileView();
        }
    }


    private void showProfileView() {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_content_container, new ProfileViewFragment()).commit();
    }


    private void setSpecialStyling() {

        TextView username = (TextView) mView.findViewById(R.id.username_heading);
        TextView password = (TextView) mView.findViewById(R.id.password_heading);
        TextView passwordAgain = (TextView) mView.findViewById(R.id.password_again_heading);

        username.append(addRedAsterisk("Username:"));
        password.append(addRedAsterisk("Password:"));
        passwordAgain.append(addRedAsterisk("Password again:"));
    }

    private SpannableString addRedAsterisk(String string) {

        string = string + " *";
        SpannableString text = new SpannableString(string);
        text.setSpan(new ForegroundColorSpan(Color.rgb(255, 0, 0)), text.length()-1, text.length(), 0);
        return text;
    }


    private void populateEditTexts() {

        mUsername.setText(PrefUtils.getString(getContext(), PrefKeys.KEY_PREFERENCES_USERNAME));
        mFirstName.setText(PrefUtils.getString(getContext(), PrefKeys.KEY_PREFERENCES_FIRSTNAME));
        mLastName.setText(PrefUtils.getString(getContext(), PrefKeys.KEY_PREFERENCES_LASTNAME));

        if( PrefUtils.keyExists(getContext(), PrefKeys.KEY_PREFERENCES_AVATAR_URL) ) {
            mAvatarUrl.setText(PrefUtils.getString(getContext(), PrefKeys.KEY_PREFERENCES_AVATAR_URL));
        }
        else {
            // if this is the first app launch, default the user's avatar to delicious pizza
            mAvatarUrl.setText("http://www.redbaron.com/timeless/images/00s/pizza-1.png");
            mAvatarUrl.setSelectAllOnFocus(true);
        }

        if( PrefUtils.keyExists(getContext(), PrefKeys.KEY_PREFERENCES_FAVORITE_FOOD) ) {
            mFavoriteFood.setText(PrefUtils.getString(getContext(), PrefKeys.KEY_PREFERENCES_FAVORITE_FOOD));
        }
        else {
            // if this is the first app launch, default the user's favorite food to pancakes
            mFavoriteFood.setText("pancakes");
            mFavoriteFood.setSelectAllOnFocus(true);
        }
    }

}
