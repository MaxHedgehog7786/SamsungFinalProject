package com.example.authentification;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.HashMap;
import java.util.Map;

public class CreateAdFragment extends Fragment {
    AppCompatButton createAdButton;
    EditText titleEditText, authorEditText,descriptionEditText;
    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_ad_layout, container, false);

        titleEditText = view.findViewById(R.id.edit_text_title);
        authorEditText = view.findViewById(R.id.edit_text_author);
        descriptionEditText = view.findViewById(R.id.edit_text_description);
        createAdButton = view.findViewById(R.id.button_create_ad);

        mAuth = FirebaseAuth.getInstance();

        createAdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAd();
            }
        });

        return view;
    }

    private void createAd() {
        String title = titleEditText.getText().toString();
        String author = authorEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        String userEmail = mAuth.getCurrentUser().getEmail();

        Map<String, Object> ad = new HashMap<>();
        ad.put("title", title);
        ad.put("author", author);
        ad.put("description", description);
        ad.put("userEmail", userEmail);

        // Добавляем объявление в коллекцию "ads" в Cloud Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("ads")
                .add(ad)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                        // Объявление успешно добавлено
                        Toast.makeText(requireContext(), "Объявление успешно создано!", Toast.LENGTH_SHORT).show();
                        titleEditText.setText("");
                        authorEditText.setText("");
                        descriptionEditText.setText("");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error adding document", e);
                        // Возникла ошибка при добавлении объявления
                        // Вы можете обработать эту ошибку, например, отобразить сообщение об ошибке
                    }
                });
    }
}

