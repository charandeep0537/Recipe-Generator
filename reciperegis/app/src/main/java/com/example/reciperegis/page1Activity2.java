package com.example.reciperegis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class page1Activity2 extends AppCompatActivity {
    TextView tv;
    ImageView imageView,imgView,iv;
    Button btnfruit,btnveg;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page12);
        imageView= (ImageView) findViewById(R.id.img);
        imgView= (ImageView) findViewById(R.id.cheffruit);
        tv= (TextView) findViewById(R.id.tv);
        btnveg= (Button) findViewById(R.id.veg);
        iv= (ImageView) findViewById(R.id.profileIcon);
        iv.setOnClickListener(view -> {
            Intent intent=new Intent(page1Activity2.this, ProfileActivity2.class);
            startActivity(intent);
        });
        btnfruit= (Button) findViewById(R.id.fru);
        btnveg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(page1Activity2.this, vegeActivity2.class);
                startActivity(intent);
            }
        });
        btnfruit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(page1Activity2.this, FruitActivity2.class);
                startActivity(i);
            }
        });
        
    }
}