package com.example.thomas.kotr_android;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainMenuActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        ImageView knightLogoView = (ImageView)findViewById(R.id.knightLogoView);
        ((AnimationDrawable) knightLogoView.getBackground()).start();

        Button arcadeModeButton = (Button) findViewById(R.id.arcadeButton);

        arcadeModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, ArcadeModeActivity.class);
                startActivity(intent);
            }
        });

        Button timeAttackModeButton = (Button) findViewById(R.id.timeAttackButton);

        timeAttackModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, TimeAttackModeActivity.class);
                startActivity(intent);
            }
        });
    }
}
