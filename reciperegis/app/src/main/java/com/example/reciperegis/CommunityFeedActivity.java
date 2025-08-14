package com.example.reciperegis;

import android.os.Bundle;
import android.widget.TextView;
import com.google.firebase.firestore.FirebaseFirestore;


import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class CommunityFeedActivity extends AppCompatActivity {

    private TextView feedTextView;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_feed); // This must match layout file name

        feedTextView = findViewById(R.id.feedTextView); // This ID must exist in the layout XML

        db = FirebaseFirestore.getInstance();

        db.collection("recipes")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    StringBuilder builder = new StringBuilder();
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        builder.append("🍽 ").append(doc.getString("title")).append("\n")
                                .append(doc.getString("content")).append("\n\n");
                    }
                    feedTextView.setText(builder.toString());
                });
    }
}
