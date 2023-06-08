package com.example.final_test.PlantsFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.final_test.R;

public class hibiscus extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_hibiscus);

        //put code to go back to personfragment fragment
        //Toolbar toolbar = findViewById(R.id.toolbar);
        getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home) { finish(); }

        return super.onOptionsItemSelected(item);
    }
}