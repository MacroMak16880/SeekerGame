package com.example.sunrainy.minerseeker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

public class Game extends AppCompatActivity {

    private static final int NUM_COLS = 6;
    private static int Num_ROWS=4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        
        populatebuttons();
    }

    private void populatebuttons() {
        TableLayout table=(TableLayout) findViewById(R.id.tableforbtns);
        for(int row = 0; row< Num_ROWS;row++){
            TableRow tableRow=new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f
            ));
            table.addView(tableRow);

            for(int col=0;col <NUM_COLS;col++){
                Button button=new Button(this);
                button.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f));
                tableRow.addView(button);

            }
        }

    }

    public static Intent makeIntent(Context context) {
        return new Intent(context,Game.class);
    }
}
