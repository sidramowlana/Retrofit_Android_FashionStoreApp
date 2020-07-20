package com.example.fashionstoreapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.fashionstoreapp.DTO.Responses.LoginResponse;
import com.example.fashionstoreapp.Fragments.HomeFragment;
import com.example.fashionstoreapp.R;
import com.example.fashionstoreapp.Storage.SharedPreferenceManager;
import com.example.fashionstoreapp.databinding.ActivityAdminMainBinding;
import com.google.android.material.navigation.NavigationView;





public class AdminMainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {

    ActivityAdminMainBinding activityAdminMainBinding;
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAdminMainBinding = ActivityAdminMainBinding.inflate(getLayoutInflater());
        View view = activityAdminMainBinding.getRoot();
        setContentView(view);
        toolbar = activityAdminMainBinding.appNavBar.tbToolBar;
        setSupportActionBar(toolbar);

        LoginResponse loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(this).getUser();
        NavigationView navigationView = activityAdminMainBinding.navigationView;
        onNavigationItemSelected(navigationView.getMenu().findItem(R.id.navAdminHome));

        navigationView.setCheckedItem(R.id.navAdminHome);
        View navHeaderview = navigationView.getHeaderView(0);
        TextView navUserName = navHeaderview.findViewById(R.id.tvUserName);
        navUserName.setText(loginResponse.getUsername());

        TextView navEmail = navHeaderview.findViewById(R.id.tvUserEmail);
        navEmail.setText(loginResponse.getEmail());

        NavigationView navView = activityAdminMainBinding.navigationView;
        DrawerLayout drawerLayout = activityAdminMainBinding.drawerLayout;
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.opens, R.string.closes);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navView.setNavigationItemSelectedListener(this);

    }

    public void onLogout() {
        SharedPreferenceManager.getSharedPreferenceInstance(this).clear();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        NavigationView navigationView = findViewById(R.id.navigationView);
        DrawerLayout drawerLayout = activityAdminMainBinding.drawerLayout;
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.opens, R.string.closes);

        if (toggle.isDrawerIndicatorEnabled()
                && toggle.onOptionsItemSelected(item))
            return true;
        switch (item.getItemId()) {
            case R.id.navAdminHome:
                onBackPressed();
                return true;
//            case R.id.navCart:
//                onNavigationItemSelected(navigationView.getMenu().findItem(R.id.navCart));
//                navigationView.setCheckedItem(R.id.navCart);
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Fragment current_fragment = getSupportFragmentManager().findFragmentById(R.id.frameLayout);
        if (activityAdminMainBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            activityAdminMainBinding.drawerLayout.closeDrawer(GravityCompat.START);
        }
        else if (current_fragment instanceof HomeFragment) {
            super.onBackPressed();
//            finish();
        }
        else {
            NavigationView navigationView = findViewById(R.id.navigationView);
            onNavigationItemSelected(navigationView.getMenu().findItem(R.id.navAdminHome));
            navigationView.setCheckedItem(R.id.navAdminHome);

        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = new Fragment();

        //fragments are created according to user demand
        if (id == R.id.navAdminHome) {
            fragment = new HomeFragment();
//        } else if (id == R.id.navAdminCategory) {
//            fragment = new WishlistFragment();
//        } else if (id == R.id.navAdminInquiry) {
//            fragment = new CartFragment();
//        } else if (id == R.id.navAdminOrders) {
//            fragment = new ProfileFragment();
//        } else if (id == R.id.navAdminOrders) {
//            fragment = new OrdersFragment();
        } else if (id == R.id.navAdminLogout) {
            onLogout();
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }

        activityAdminMainBinding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}
