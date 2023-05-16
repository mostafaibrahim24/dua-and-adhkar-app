package com.example.duaandadhkar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); //For night mode theme
        setContentView(R.layout.activity_main);
    }

    public void launchMorningAdhkar(View view){
        Intent intent = new Intent(this, MorningAdhkarActivity.class);
        startActivity(intent);
    }
    public void launchEveningAdhkar(View view){
        Intent intent = new Intent(this, EveningAdhkarActivity.class);
        startActivity(intent);
    }

    public void launchRuquiya(View view){
        Intent intent = new Intent(this, RuquiyaActivity.class);
        startActivity(intent);
    }
    public void launch40Dua(View view){
        Intent intent = new Intent(this, DuaBeginsWithRabbanaActivity.class);
        startActivity(intent);
    }
    public void launchMissedRakahProcedure(View view){
        Intent intent = new Intent(this, MissedRakahProcedureActivity.class);
        startActivity(intent);
    }

    public void launchHajjAndUmrah(View view){
        Intent intent = new Intent(this, HajjAndUmrahActivity.class);
        startActivity(intent);
    }


}