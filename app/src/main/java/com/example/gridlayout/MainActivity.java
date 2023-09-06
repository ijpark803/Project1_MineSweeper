package com.example.gridlayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private static final int COLUMN_COUNT = 10;
    private static final int numFlags = 4;
    ImageButton butt;

    // save the TextViews of all cells in an array, so later on,
    // when a TextView is clicked, we know which cell it is
    private ArrayList<TextView> cell_tvs;

    private int dpToPixel(int dp) {
        float density = Resources.getSystem().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cell_tvs = new ArrayList<TextView>();

        Random rand = new Random();
        Set mine_rows =new HashSet<Integer>();
        Set mine_cols =new HashSet<Integer>();
        while(mine_rows.size() < numFlags){
            //random generate rows and cols for min position
            int temp = rand.nextInt(12);
            mine_rows.add(temp);
        }
        while(mine_cols.size() < numFlags) {
            //random generate rows and cols for min position
            int temp = rand.nextInt(10);
            mine_cols.add(temp);
        }
        System.out.println(mine_rows);
        System.out.println(mine_cols);

        //set the text of the number of flags
        TextView numberTextView = findViewById(R.id.numFlagsText);
        numberTextView.setText(String.valueOf(numFlags));

        //set the text of timer
        TextView timerText = findViewById(R.id.timerText);
        timerText.setText(String.valueOf(10));


        // Method (2): add four dynamically created cells
        GridLayout grid = (GridLayout) findViewById(R.id.gridLayout01);
        for (int i = 0; i<12; i++) {
            for (int j=0; j<10; j++) {
                TextView tv = new TextView(this);
                tv.setHeight( dpToPixel(32) );
                tv.setWidth( dpToPixel(32) );
                tv.setTextSize( 16 );//dpToPixel(32) );
                tv.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
                tv.setTextColor(Color.parseColor("lime"));
                tv.setBackgroundColor(Color.parseColor("lime"));
                tv.setOnClickListener(this::onClickTV);
//                tv.setText("@string/mine");

                GridLayout.LayoutParams lp = new GridLayout.LayoutParams();
               // lp.setMargins(dpToPixel(1), dpToPixel(2), dpToPixel(2), dpToPixel(2));
                lp.rowSpec = GridLayout.spec(i);
                lp.columnSpec = GridLayout.spec(j);
                lp.leftMargin = 2;
                lp.rightMargin = 2;
                lp.bottomMargin = 2;
                grid.addView(tv, lp);

                cell_tvs.add(tv);
            }
        }
    }

    private int findIndexOfCellTextView(TextView tv) {
        for (int n=0; n<cell_tvs.size(); n++) {
            if (cell_tvs.get(n) == tv)
                return n;
        }
        return -1;
    }

    public void onClickTV(View view){
        TextView tv = (TextView) view;
        int n = findIndexOfCellTextView(tv);
        int i = n/COLUMN_COUNT;
        int j = n%COLUMN_COUNT;
        tv.setText(String.valueOf(i)+String.valueOf(j));
        if (tv.getCurrentTextColor() == Color.parseColor("lime")) {
            //this means that player is flagging
            if(currentMode() == true){

            }
            else{
                //add code
            }
            tv.setTextColor(Color.GRAY);
            tv.setBackgroundColor(Color.GRAY);
        }
        else {
            tv.setTextColor(Color.parseColor("lime"));
            tv.setBackgroundColor(Color.parseColor("lime"));
        }
    }
    public boolean currentMode(){
        ToggleButton toggleButton = findViewById(R.id.modeToggleButton);
        if(toggleButton.isChecked()){
            return true;
        }
        else return false;
    }


}