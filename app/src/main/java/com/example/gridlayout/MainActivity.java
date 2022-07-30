package com.example.gridlayout;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv00 = (TextView) findViewById(R.id.textView00);
        TextView tv01 = (TextView) findViewById(R.id.textView01);
        TextView tv10 = (TextView) findViewById(R.id.textView10);
        TextView tv11 = (TextView) findViewById(R.id.textView11);

        tv00.setTextColor(Color.GRAY);
        tv00.setBackgroundColor(Color.GRAY);
        tv00.setOnClickListener(this::onClickTV);

        tv01.setTextColor(Color.GRAY);
        tv01.setBackgroundColor(Color.GRAY);
        tv01.setOnClickListener(this::onClickTV);

        tv10.setTextColor(Color.GRAY);
        tv10.setBackgroundColor(Color.GRAY);
        tv10.setOnClickListener(this::onClickTV);

        tv11.setTextColor(Color.GRAY);
        tv11.setBackgroundColor(Color.GRAY);
        tv11.setOnClickListener(this::onClickTV);

    }

    public void onClickTV(View view){
        TextView tv = (TextView) view;
        if (tv.getCurrentTextColor() == Color.GRAY) {
            tv.setTextColor(Color.GREEN);
            tv.setBackgroundColor(Color.LTGRAY);
        }
        else {
            tv.setTextColor(Color.GRAY);
            tv.setBackgroundColor(Color.GRAY);
        }
    }
}