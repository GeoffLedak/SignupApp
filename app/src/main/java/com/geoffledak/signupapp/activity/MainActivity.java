package com.geoffledak.signupapp.activity;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.geoffledak.signupapp.R;
import com.geoffledak.signupapp.fragment.ProfileEditFragment;


public class MainActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().addOnBackStackChangedListener(this);
        shouldShowBackButton();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_content_container, new ProfileEditFragment()).commit();
    }

    @Override
    public void onBackStackChanged() {
        shouldShowBackButton();
    }

    @Override
    public boolean onSupportNavigateUp() {
        getSupportFragmentManager().popBackStack();
        return true;
    }

    private void shouldShowBackButton() {
        boolean hasBack = getSupportFragmentManager().getBackStackEntryCount() > 0;
        getSupportActionBar().setDisplayHomeAsUpEnabled(hasBack);
    }

}
