package com.example.trophyemall.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trophyemall.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private FirebaseAuth auth;
    TextView tvUser;
    TextView tvMail;
    ImageView ivIcon;
    ImageView ivClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        tvUser = headerView.findViewById(R.id.tvUsername);
        tvMail = headerView.findViewById(R.id.tvUsermail);
        ivIcon = headerView.findViewById(R.id.ivUsericon);
        ivClose = headerView.findViewById(R.id.ivCloseSesion);
        /**
         * Aquí se recogen los datos del usuario para mostrar el nombre y el correo en el menú que se
         * muestra al deslizar de izquierda a derecha
         */
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        tvUser.setText(user.getDisplayName());
        tvMail.setText(user.getEmail());

        /**
         * Aquí se define que, al pulsar el botón de cerrar sesión, se cierra la sesión y te abre la
         * actividad de iniciar sesión
         */
        ivClose.setOnClickListener(l -> {
            auth.signOut();
            startActivity(new Intent(this, LoginActivity.class));
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_profile)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}