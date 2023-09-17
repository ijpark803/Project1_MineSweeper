package com.example.gridlayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    public static final int RESULT_RESTART = 1;
    private static final int COLUMN_COUNT = 10;
    private static final int numFlags = 4;
    private static int placedFlags = 4;
    Game g = new Game();
    private int clock = 0;
    private boolean running = false;
    private boolean win = false;
    private int clicks = 0;
    public boolean lastClick = false;
    ArrayList<Integer> mine_r;
    ArrayList<Integer> mine_c;

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
        Set mine_rows = new HashSet<Integer>();
        Set mine_cols = new HashSet<Integer>();
        while (mine_rows.size() < numFlags) {
            //random generate rows and cols for min position
            int temp = rand.nextInt(12);
            mine_rows.add(temp);
        }
        while (mine_cols.size() < numFlags) {
            //random generate rows and cols for min position
            int temp = rand.nextInt(10);
            mine_cols.add(temp);
        }
        //convert to arraylist so that i can use indexOf() or contains()
        mine_r = new ArrayList<>(mine_rows);
        mine_c = new ArrayList<>(mine_cols);

        //for testing
        for (int i = 0; i < 4; i++) {
            System.out.println(Integer.toString(mine_r.get(i)) + "," + Integer.toString(mine_c.get(i)));
        }

        //timer
        if (savedInstanceState != null) {
            clock = savedInstanceState.getInt("clock");
            running = savedInstanceState.getBoolean("running");
        }
        runTimer();
        running = true;

        //set the text of the number of flags
        TextView numberTextView = findViewById(R.id.numFlagsText);
        numberTextView.setText(String.valueOf(placedFlags));

        //set the text of timer
        TextView timerText = findViewById(R.id.timerText);
        timerText.setText(String.valueOf(10));

        // Method (2): add four dynamically created cells
        GridLayout grid = (GridLayout) findViewById(R.id.gridLayout01);
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 10; j++) {
                TextView tv = new TextView(this);

                tv.setHeight(dpToPixel(32));
                tv.setWidth(dpToPixel(32));
                tv.setTextSize(16);//dpToPixel(32) );
                tv.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
                tv.setTextColor(Color.parseColor("lime"));
                tv.setBackgroundColor(Color.parseColor("lime"));
                tv.setOnClickListener(this::onClickTV);

                GridLayout.LayoutParams lp = new GridLayout.LayoutParams();
                // lp.setMargins(dpToPixel(1), dpToPixel(2), dpToPixel(2), dpToPixel(2));
                lp.rowSpec = GridLayout.spec(i);
                lp.columnSpec = GridLayout.spec(j);
                lp.leftMargin = 2;
                lp.rightMargin = 2;
                lp.bottomMargin = 2;
                grid.addView(tv, lp);

                cell_tvs.add(tv);

                boolean bomb = false;
                if (mine_r.indexOf(i) != -1) {
                    int idx = mine_r.indexOf(i);
                    if (mine_c.get(idx) == j) {
                        bomb = true;
                        //System.out.println("bomb at " +  Integer.toString(i) +  Integer.toString(j));
                    }
                }

                Cell temp = new Cell(i, j, false, false, bomb, tv);
                g.addCell(temp);

            }
        }
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("clock", clock);
        savedInstanceState.putBoolean("running", running);
        if (lastClick == true) {
            running = false;
        }
    }

    private void runTimer() {
        final TextView timeView = (TextView) findViewById(R.id.timerText);
        final Handler handler = new Handler();

        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = clock / 3600;
                int minutes = (clock % 3600) / 60;
                int seconds = clock % 60;
                String time = String.format("%d:%02d:%02d", hours, minutes, seconds);
                timeView.setText(Integer.toString(clock));

                if (running) {
                    clock++;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

    private int findIndexOfCellTextView(TextView tv) {
        for (int n = 0; n < cell_tvs.size(); n++) {
            if (cell_tvs.get(n) == tv)
                return n;
        }
        return -1;
    }

    public void onClickTV(View view) {
        TextView tv = (TextView) view;
        int n = findIndexOfCellTextView(tv);
        int i = n / COLUMN_COUNT;
        int j = n % COLUMN_COUNT;
        //tv.setText(String.valueOf(i)+String.valueOf(j));
        Cell curr = g.findCell(i, j);

        if (tv.getCurrentTextColor() == Color.parseColor("lime")) {
            //this means that player is flagging
            if (currentMode() == true) {
//                if(placedFlags == 0){
//                    boolean res = checkWin();
//                    return;
//                }
                //already flagged, so unflag
                if (curr.isFlagged == true) {
                    if(curr.hasMine == true) win = true;
                    placedFlags++;
                    curr.isFlagged = false;
                    tv.setText("");
                    tv.setBackgroundColor(Color.parseColor("lime"));
                    return;
                }

                //already visible
                if (curr.isVisible == true) return;
                //cell can be flagged
                placedFlags--;
                if(curr.hasMine == true) win = false;
                TextView numberTextView = findViewById(R.id.numFlagsText);
                numberTextView.setText(String.valueOf(placedFlags));
                String flagText = getResources().getString(R.string.flag);
                tv.setText(flagText);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                tv.setPadding(0, 4, 0, 0);
                curr.isFlagged = true;
                if(placedFlags == 0){
                    showBombs();
                    running = false;
                    lastClick = true;
                    //wait for a click then check win

                }
            }
            //digging mode
            else {
                if(curr.isFlagged) return;
                clicks++;
                if (curr.hasMine == true) {
                    String bombtext = getResources().getString(R.string.mine);
                    tv.setBackgroundColor(Color.RED);
                    tv.setText(bombtext); // For example, display a 'B' to indicate a bomb
                    win = false;
                    showBombs();
                    lastClick = true;
                    running = false;
                    //check for a click
                    return;
                }
                revealCells(i, j);
            }

        }
    }

    public boolean currentMode() {
        ToggleButton toggleButton = findViewById(R.id.modeToggleButton);
        if (toggleButton.isChecked()) {
            return true;
        } else return false;
    }

    private void revealCells(int row, int col) {
        Cell curr = g.findCell(row, col);
        TextView tv = curr.tv;
        if(curr.isFlagged == true && clicks == 1){
            curr.isFlagged = false;
            tv.setText("");
        }
        if (curr.isVisible) return;
        curr.isVisible = true;
        // Display the number of adjacent bombs, or recursively reveal neighbors if there are none
        int adjacentBombs = countAdjacentBombs(row, col);
        tv.setBackgroundColor(Color.GRAY);
        tv.setTextColor(Color.WHITE);
        if (adjacentBombs > 0) {
            tv.setText(String.valueOf(adjacentBombs));
            return;
        } else {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {

                    int newRow = row + i;
                    int newCol = col + j;
                    //System.out.println(newRow + " " + newCol);
                    if (isValid(newRow, newCol)) {
                        //System.out.println("revealing " + newRow + newCol);
                        revealCells(newRow, newCol);
                    }
                }
            }
        }


    }

    private int countAdjacentBombs(int row, int col) {
        Cell curr = g.findCell(row, col);
        if (curr.hasMine == true) return -1;
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int newRow = row + i;
                int newCol = col + j;
                if (isValid(newRow, newCol)) {
                    Cell neighbor = g.findCell(newRow, newCol);
                    if (neighbor.hasMine == true) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    boolean isValid(int r, int c) {
        if (r < 0 || c < 0 || r >= 12 || c >= 10) return false;
        return true;
    }

    void checkWin(){


        Intent intent = new Intent(this, ResultPage.class);
        intent.putExtra("GAME_WON", win);
        TextView timeView = (TextView) findViewById(R.id.timerText);
        String temp = timeView.getText().toString();
        intent.putExtra("seconds", temp);
        System.out.println(temp + " plz");
        intent.putExtra("time", temp);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent);
        finish();
    }

    void showBombs(){
        for(int i = 0 ;i < numFlags; i ++){
            Cell bomb = g.findCell(mine_r.get(i), mine_c.get(i));
            TextView tv = bomb.tv;
            if(bomb.isFlagged == false) win = false;
            else{
                win = true;
            }
            String bombtext = getResources().getString(R.string.mine);
            tv.setBackgroundColor(Color.RED);
            tv.setText(bombtext);
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        if(lastClick == true) {
            checkWin();
        }

        // Return true to indicate that the event has been handled
        return true;
    }

}

class Game {
    ArrayList<Cell> bank;

    public Game() {
        ArrayList<Cell> carr = new ArrayList<Cell>();
        this.bank = carr;
    }

    public void addCell(Cell c) {
        bank.add(c);
    }

    public Cell findCell(int r, int c) {
        for (int i = 0; i < bank.size(); i++) {
            Cell temp = bank.get(i);
            if (temp.r == r && temp.c == c) return temp;
        }
        return null;
    }


}

class Cell {
    boolean isFlagged;
    boolean isVisible;
    boolean hasMine;
    int r;
    int c;
    TextView tv;

    public Cell(int r, int c, boolean isFlagged, boolean isVisible, boolean hasMine, TextView tv) {
        this.r = r;
        this.c = c;
        this.isFlagged = isFlagged;
        this.isVisible = isVisible;
        this.hasMine = hasMine;
        this.tv = tv;
    }

}


