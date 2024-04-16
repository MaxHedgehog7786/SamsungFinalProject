package com.example.authentification;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AllAdsFragment extends Fragment {

    private RecyclerView recyclerViewAds;
    private List<Ad> adList;
    private AdsAdapter adsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_ads, container, false);

        recyclerViewAds = view.findViewById(R.id.recycler_view_ads);
        recyclerViewAds.setLayoutManager(new LinearLayoutManager(getContext()));
        adList = new ArrayList<>();
        adsAdapter = new AdsAdapter(getContext(), adList);
        recyclerViewAds.setAdapter(adsAdapter);

        // Загрузка всех объявлений из Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("ads")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Ad ad = document.toObject(Ad.class);
                            adList.add(ad);
                        }
                        adsAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getContext(), "Ошибка загрузки объявлений", Toast.LENGTH_SHORT).show();
                    }
                });

        return view;
    }
}
