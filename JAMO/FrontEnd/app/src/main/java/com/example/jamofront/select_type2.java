package com.example.jamofront;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jamofront.Littleprince.Littleprince_count1_Activity;

public class select_type2 extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_type2);

        Button type2 = findViewById(R.id.button1);

        type2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Littleprince_count1_Activity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
    }
}
