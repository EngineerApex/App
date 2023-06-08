package com.example.final_test;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.final_test.CameraFragment.CameraFragment;
import com.example.final_test.DiseaseFragment.DiseaseFragment;
import com.example.final_test.PlantsFragment.PlantsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    CameraFragment cameraFragment = new CameraFragment();
    PlantsFragment plantsFragment = new PlantsFragment();
    DiseaseFragment diseaseFragment = new DiseaseFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R .layout.activity_main);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.top_bar)));

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Color of the top Action Bar
        getSupportFragmentManager().beginTransaction().replace(R.id.container, cameraFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch(item.getItemId()){
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, cameraFragment).commit();
                        return true;

                    case R.id.plants: //-
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, plantsFragment).commit();

                        return true;

                    case R.id.diseases: //-
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, diseaseFragment).commit();
                        return true;
                }
                return false;
            }
        });



    }
}