package com.example.gridlayout;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


public class ResultPage extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    //private ActivityResultPageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState== null) {
            setContentView(R.layout.activity_main2);

            Button restartButton = findViewById(R.id.button_first);
            System.out.println(restartButton);
            restartButton.setOnClickListener(new View.OnClickListener() {
                                                 @Override
                                                 public void onClick(View v) {
                                                     finish();
                                                 }

                                             }
            );

            Intent intent = getIntent();
            boolean won = intent.getBooleanExtra("GAME_WON", false);
            String seconds = intent.getStringExtra("seconds");
            System.out.println(seconds + " seconds");
            TextView resultTextView = findViewById(R.id.textview_first);

            String winning_msg = "";
            if (won) {
                winning_msg = "won";
            } else {
                winning_msg = "lost";
            }

            resultTextView.setText("Used " + seconds + " seconds.\n You " + winning_msg + ". ");
        }
    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_result_page);
//        return NavigationUI.navigateUp(navController, appBarConfiguration)
//                || super.onSupportNavigateUp();
//    }
}