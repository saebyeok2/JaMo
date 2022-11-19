package com.example.jamofront;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class select_theme2 extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_theme2);
        ImageButton next2button = findViewById(R.id.nextbtn2);
        ImageButton back2button=findViewById(R.id.backbtn2);
        ImageButton selecttheme2=findViewById(R.id.themeButton2);

        back2button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),select_theme1.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
        selecttheme2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), select_type1.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });


    }
}