package com.example.authentification;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class UserAdsFragment extends Fragment {

    private ListView adsListView;
    private ArrayAdapter<String> adapter;
    private List<String> adsList;
    private List<DocumentSnapshot> adsDocuments;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_ads, container, false);

        adsListView = view.findViewById(R.id.ads_list_view);
        adsList = new ArrayList<>();
        adsDocuments = new ArrayList<>();
        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, adsList);
        adsListView.setAdapter(adapter);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            db = FirebaseFirestore.getInstance();
            CollectionReference adsRef = db.collection("ads");

            // Получение объявлений, созданных текущим пользователем
            adsRef.whereEqualTo("userId", userId)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            adsList.clear();
                            adsDocuments.clear();
                            for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                                adsList.add(document.getString("title"));
                                adsDocuments.add(document);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("TAG", "Error getting documents: " + e.getMessage());
                        }
                    });

            // Обработчик нажатия на элемент списка
            adsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    showDeleteDialog(position);
                }
            });
        }

        return view;
    }

    // Метод для отображения диалога подтверждения удаления объявления
    private void showDeleteDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Удаление объявления")
                .setMessage("Вы уверены, что хотите удалить это объявление?")
                .setPositiveButton("Удалить", (dialog, which) -> {
                    deleteAd(position);
                })
                .setNegativeButton("Отмена", null)
                .show();
    }

    // Метод для удаления объявления
    private void deleteAd(int position) {
        DocumentSnapshot document = adsDocuments.get(position);
        String adId = document.getId();
        db.collection("ads").document(adId)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        adsList.remove(position);
                        adsDocuments.remove(position);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(requireContext(), "Объявление успешно удалено", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("TAG", "Error deleting document", e);
                        Toast.makeText(requireContext(), "Ошибка при удалении объявления", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
