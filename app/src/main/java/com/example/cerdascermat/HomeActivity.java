package com.example.cerdascermat;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.cerdascermat.fragment.AccountFragment;
import com.example.cerdascermat.fragment.HomeFragment;
import com.example.cerdascermat.fragment.LatihanSoalFragment;
import com.example.cerdascermat.fragment.MateriFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
     FirebaseAuth firebaseAuth;
     ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        actionBar = getSupportActionBar();

        firebaseAuth = FirebaseAuth.getInstance();

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        loadFragment(new HomeFragment());

    }
    private void checkUserStatus(){
        FirebaseUser user = firebaseAuth.getCurrentUser();

         if (user != null){

         }else {
             startActivity(new Intent(HomeActivity.this, MainActivity.class));
             finish();
         }

    }

        @Override
        protected void onStart(){
        checkUserStatus();
            super.onStart();
        }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment;
        switch (menuItem.getItemId()){
            case R.id.navigation_home:
                actionBar.setTitle("Home");
                fragment = new HomeFragment();
                loadFragment(fragment);
                return true;
            case R.id.navigation_materi:
                actionBar.setTitle("Materi");
                fragment = new MateriFragment();
                loadFragment(fragment);
                return true;
            case R.id.navigation_latihan:
                actionBar.setTitle("Latihan");
                fragment = new LatihanSoalFragment();
                loadFragment(fragment);
                return true;
            case R.id.navigation_akun:
                actionBar.setTitle("Account");
                fragment = new AccountFragment();
                loadFragment(fragment);
                return true;
        }
        return false;
    }

    public void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
