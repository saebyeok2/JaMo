package com.example.jamofront.Littleprince;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jamofront.R;

public class Little_Prince_ReadingTutorial_Activity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lp_reading_tutorial);

        Button startbutton = findViewById(R.id.start_btn);

        startbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Littleprince_reading1_Activity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }

        });
    }
}