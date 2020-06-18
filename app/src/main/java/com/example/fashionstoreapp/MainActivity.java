package com.example.fashionstoreapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.fashionstoreapp.Activities.LoginActivity;
import com.example.fashionstoreapp.Models.StoresUser;
import com.example.fashionstoreapp.Models.User;
import com.example.fashionstoreapp.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ActivityMainBinding activityMainBinding;
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = activityMainBinding.getRoot();
        setContentView(view);
        toolbar = activityMainBinding.appNavBar.tbToolBar;
        setSupportActionBar(toolbar);
        User user = StoresUser.getCurrentLoggedUser(getApplicationContext());


        NavigationView navigationView = activityMainBinding.navigationView;
        onNavigationItemSelected(navigationView.getMenu().findItem(R.id.navHome));

        navigationView.setCheckedItem(R.id.navHome);
        View navHeaderview = navigationView.getHeaderView(0);
        TextView navUserName = navHeaderview.findViewById(R.id.tvUserName);

        navUserName.setText(user.getUsername());
        TextView navUsername = navHeaderview.findViewById(R.id.tvUserEmail);
        navUsername.setText(user.getEmail());

        NavigationView navView = activityMainBinding.navigationView;
        DrawerLayout drawerLayout = activityMainBinding.drawerLayout;
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.opens, R.string.closes);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navView.setNavigationItemSelectedListener(this);

    }
    public void onLogout() {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.remove(getString(R.string.PASSWORD_KEY));
        editor.remove(LoginActivity.USER);
        editor.remove(LoginActivity.LOGGED_USER);
        editor.apply();

        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        NavigationView navigationView = findViewById(R.id.navigationView);
        DrawerLayout drawerLayout = activityMainBinding.drawerLayout;
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.opens, R.string.closes);

        if (toggle.isDrawerIndicatorEnabled()
                && toggle.onOptionsItemSelected(item))
            return true;
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.navCart:
                onNavigationItemSelected(navigationView.getMenu().findItem(R.id.navCart));
                navigationView.setCheckedItem(R.id.navCart);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Fragment current_fragment = getSupportFragmentManager().findFragmentById(R.id.frameLayout);
        if (activityMainBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            activityMainBinding.drawerLayout.closeDrawer(GravityCompat.START);
        }
//        else if (current_fragment instanceof HomeFragment) {
//            super.onBackPressed();
//        }
        else {
            NavigationView navigationView = findViewById(R.id.navigationView);
            onNavigationItemSelected(navigationView.getMenu().findItem(R.id.navHome));
            navigationView.setCheckedItem(R.id.navHome);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = new Fragment();

        //fragments are created according to user demand
        if (id == R.id.navHome) {
//            fragment = new HomeFragment();
        } else if (id == R.id.navWishlist) {
//            fragment = new WishlistFragment();
        } else if (id == R.id.navCart) {
//            fragment = new CartFragment();
        } else if (id == R.id.navProfile) {
//            fragment = new ProfileFragment();
        } else if (id == R.id.navOrders) {
//            fragment = new OrdersFragment();
        } else if (id == R.id.navLogout) {
            onLogout();
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }

        activityMainBinding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}
