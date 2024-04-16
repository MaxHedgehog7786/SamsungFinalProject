package com.example.authentification;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainPage extends Fragment {
    private BottomNavigationView bottomNavigationView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_page, container, false);
        bottomNavigationView = view.findViewById(R.id.bottom_navigation);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        AllAdsFragment defaultFragment = new AllAdsFragment();
        fragmentTransaction.replace(R.id.fragment_container, defaultFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        return view;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            if(item.getItemId() == R.id.nav_create_ad){
                selectedFragment = new CreateAdFragment();
            } else if(item.getItemId() == R.id.nav_all_ads){
                selectedFragment = new AllAdsFragment();
            } else if (item.getItemId() == R.id.nav_my_ads){
                selectedFragment = new UserAdsFragment();
            }
            if (selectedFragment != null) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, selectedFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
            return true;
        }
    };



}
