package com.example.gridlayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int dpToPixel(int dp) {
        float density = Resources.getSystem().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // (1) adding four statically created cells
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

        // (2) adding four dynamically created cells
        GridLayout grid = (GridLayout) findViewById(R.id.gridLayout01);
        grid.setRowCount(4);
        for (int i = 2; i<=3; i++) {
            for (int j=0; j<=1; j++) {
                TextView tv = new TextView(this);
                tv.setHeight( dpToPixel(64) );
                tv.setWidth( dpToPixel(64) );
                tv.setText(String.valueOf(i)+String.valueOf(j));
                tv.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
                tv.setTextColor(Color.GRAY);
                tv.setBackgroundColor(Color.GRAY);
                tv.setOnClickListener(this::onClickTV);

                GridLayout.LayoutParams lp = new GridLayout.LayoutParams();
                lp.setMargins(dpToPixel(2), dpToPixel(2), dpToPixel(2), dpToPixel(2));
                lp.rowSpec = GridLayout.spec(i);
                lp.columnSpec = GridLayout.spec(j);

                grid.addView(tv, lp);
            }
        }

        // (3) adding four dynamically created cells with LayoutInflater
        grid.setRowCount(6);
        LayoutInflater li = LayoutInflater.from(this);
        for (int i = 4; i<=5; i++) {
            for (int j=0; j<=1; j++) {
                TextView tv = (TextView) li.inflate(R.layout.custom_cell_layout, grid, false);
                tv.setText(String.valueOf(i)+String.valueOf(j));
                tv.setTextColor(Color.GRAY);
                tv.setBackgroundColor(Color.GRAY);
                tv.setOnClickListener(this::onClickTV);

                GridLayout.LayoutParams lp = (GridLayout.LayoutParams) tv.getLayoutParams();
                lp.rowSpec = GridLayout.spec(i);
                lp.columnSpec = GridLayout.spec(j);

                grid.addView(tv, lp);

            }
        }

    }

    public void onClickTV(View view){
        TextView tv = (TextView) view;
        if (tv.getCurrentTextColor() == Color.GRAY) {
            // make it visible
            tv.setTextColor(Color.GREEN);
            tv.setBackgroundColor(Color.LTGRAY);
        }
        else {
            // make it invisible
            tv.setTextColor(Color.GRAY);
            tv.setBackgroundColor(Color.GRAY);
        }
    }
}