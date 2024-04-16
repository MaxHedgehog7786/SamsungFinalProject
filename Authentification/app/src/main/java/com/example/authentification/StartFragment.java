package com.example.authentification;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class StartFragment extends Fragment {

    Button reg, vkhod;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.start_fragment, container, false);
        return view;


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        reg = view.findViewById(R.id.registration);
        vkhod = view.findViewById(R.id.vkhod);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegFragment regFragment = new RegFragment();
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.start_page, regFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        vkhod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VkhodFragment vkhodFragment = new VkhodFragment();
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.start_page, vkhodFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }
}
