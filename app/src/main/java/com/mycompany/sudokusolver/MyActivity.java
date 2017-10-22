package com.mycompany.sudokusolver;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyActivity extends AppCompatActivity {
    TextView[][] matrix;
    GridLayout layout;

    SudokuSolver s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        s = new SudokuSolver(9, 9);
        layout = (GridLayout) findViewById(R.id.gRlayout);
        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(1);
        matrix = new TextView[9][9];
        for(int i = 0; i < 9; i++){
            for(int k = 0; k < 9; k++){
                TextView myEditText = new EditText(this);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                if(k == 2 || k == 5){
                    if(i == 2 || i == 5){
                        params.setMargins(4,4,13,13);
                    } else {
                        params.setMargins(4,4,13,4);
                    }
                } else if(i == 2 || i == 5)  {
                    params.setMargins(4,4,4,13);
                } else {
                    params.setMargins(4, 4, 4, 4);
                }
                DisplayMetrics dm = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(dm);

                int width = dm.widthPixels;
                int height = dm.heightPixels; //används aldrig, där in-case jag blir förvirrad
                params.height=(int) (width*.1); //sätter varje ruta till en fyrkant med sidan
                params.width=(int) (width*.1);  //10% av skärmens bredd -> en rad täcker nästan skärmen

                myEditText.setLayoutParams(params);
                myEditText.setTextSize(((int)(width*.01))+1);
                myEditText.setGravity(Gravity.CENTER_HORIZONTAL);

                //HÄR ÄNDRAS FÄRG AV RUTORNA! första if-satsen är mittenrutan, sedan behandlas
                //de närliggande rutorna och till sist behandlas hörnen.
                if(i > 2 && i < 6 && k > 2 && k < 6){
                    myEditText.setBackgroundColor(Color.rgb(255, 210, 120)); // orange
                } else if((i > 2 && i < 6) || (k > 2 && k < 6)) {
                    myEditText.setBackgroundColor(Color.rgb(180, 220, 200)); // gråish
                } else {
                    myEditText.setBackgroundColor(Color.rgb(255, 210, 120)); // orange
                }

                myEditText.setFilters(filterArray);
                myEditText.setInputType(InputType.TYPE_CLASS_NUMBER);

                layout.addView(myEditText);
                matrix[i][k] = myEditText;
                myEditText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

            }
        }
    }

    public void clearButton(View view){
        for(int i = 0; i < 9; i++){
            for(int k = 0; k < 9; k++){
                matrix[i][k].setText("");
                ((EditText)layout.getChildAt(i+k)).setText("");

            }
        }
        s.clear();
    }

    public void solveButton(View view){
        boolean solved;
        for(int i = 0; i < 9; i++){
            for(int k = 0; k < 9; k++){
                String string = ((EditText)layout.getChildAt(9*i+k)).getText().toString();
                int nbr;
                try{
                    nbr = Integer.parseInt(string);
                } catch(Exception e) {
                    nbr = 0;
                }

                s.setValue(i, k, nbr);
            }
        }
        solved = s.solve();
        if(solved){
            for(int i = 0; i < 9; i++) {
                for(int k = 0; k < 9; k++) {
                    int nbr;
                    nbr = s.getValue(i, k);
                    matrix[i][k].setText(Integer.toString(nbr));
                    ((EditText)layout.getChildAt(9*i+k)).setText(Integer.toString(nbr));
                }
            }
        } else {
            displayPop();
        }
        s.clear();
    }

    public void displayPop(){
        startActivity(new Intent(MyActivity.this, Pop.class));
    }




}
