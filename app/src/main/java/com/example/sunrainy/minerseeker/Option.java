package com.example.sunrainy.minerseeker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Option extends AppCompatActivity {
    private int savedBoardHeight;
    private int savedBoardWidth;
    private int savedNumOfMines;
    private int numOfStartedGames;
    private int bestScore;
    private String boardSizeInStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
        setupOPtionBackbtn();
        loadSavedSettings();
        setupOkayBtn();
        setupResetBestBtn();
        setupResetNumPlayedBtn();
        createNumOfMineRadioBtns();
        createSizeOfBoardRadioBtns();
        updateTextView();
    }
    private void setupResetBestBtn() {
        Button btn = (Button)findViewById(R.id.btnResetBestScore);
        btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                clearBestScore();
                bestScore = savedBoardHeight*savedBoardWidth+1;
                updateTextView();
            }
        });
    }

    private void setupResetNumPlayedBtn(){
        Button btn = (Button)findViewById(R.id.btnResetNumPlayed);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                clearNumOfGameStarted();
                numOfStartedGames = 0;
                updateTextView();
            }
        });
    }

    private void clearNumOfGameStarted() {
        SharedPreferences settings = getSharedPreferences(MainActivity.PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(Game.PLAYED_GAME_KEY);
        editor.apply();
    }


    private void updateTextView() {
        TextView txtNumPlayed = (TextView)findViewById(R.id.txtOptionsNumOfGamePlayed);
        TextView txtBestScore = (TextView)findViewById(R.id.txtOptionsBestRecord);
        txtNumPlayed.setText(getString(R.string.number_of_games_played_so_far, numOfStartedGames));
        if(bestScore > savedBoardWidth*savedBoardHeight)
            txtBestScore.setText(getString(R.string.least_scan_record, getString(R.string.not_found)));
        else
            txtBestScore.setText(getString(R.string.least_scan_record, ""+bestScore));
    }

    private void loadSavedSettings() {
        SharedPreferences settings = getSharedPreferences(MainActivity.PREFS_NAME, Context.MODE_PRIVATE);
        savedBoardHeight = settings.getInt(MainActivity.BOARD_HEIGHT_KEY, 4);
        savedBoardWidth = settings.getInt(MainActivity.BOARD_WIDTH_KEY, 6);
        savedNumOfMines = settings.getInt(MainActivity.MINE_NUMBER_KEY, 6);
        numOfStartedGames = settings.getInt(Game.PLAYED_GAME_KEY, 0);
        bestScore = settings.getInt(Game.BEST_SCORE_PREFIX+savedNumOfMines+'-'+savedBoardHeight+'x'+savedBoardWidth, savedBoardHeight*savedBoardWidth+1);
    }

    private void createSizeOfBoardRadioBtns() {
        RadioGroup grp = (RadioGroup)findViewById(R.id.rdgrpSizeOfBoard);
        String[] sizesOfBoard = getResources().getStringArray(R.array.board_size);
        for (String aSizesOfBoard : sizesOfBoard) {
            RadioButton btn = new RadioButton(this);
            btn.setText(aSizesOfBoard);
            btn.setShadowLayer(3.0f, 1.0f, 1.0f, Color.BLACK);
            grp.addView(btn);
            if(aSizesOfBoard.equals(savedBoardHeight+"x"+savedBoardWidth)){
                btn.setChecked(true);
            }
        }
    }


    private void setupOkayBtn() {
        Button btn = (Button)findViewById(R.id.btnOptionsOkay);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                RadioGroup grpMine = (RadioGroup)findViewById(R.id.rdgrpNumOfMines);
                RadioGroup grpBoardSize = (RadioGroup)findViewById(R.id.rdgrpSizeOfBoard);
                int selectedMineId = grpMine.getCheckedRadioButtonId();
                int selectedBoardSizeId = grpBoardSize.getCheckedRadioButtonId();
                if(selectedMineId !=-1) {
                    RadioButton selectedMineBtn = (RadioButton) findViewById(selectedMineId);
                    savedNumOfMines = Integer.parseInt(selectedMineBtn.getText().toString());
                }
                if(selectedBoardSizeId != -1){
                    RadioButton selectedBoardSizeBtn = (RadioButton)findViewById(selectedBoardSizeId);
                    boardSizeInStr = selectedBoardSizeBtn.getText().toString();
                    String sizeOfBoardInArray[] = boardSizeInStr.split("x");
                    savedBoardHeight = Integer.parseInt(sizeOfBoardInArray[0]);
                    savedBoardWidth = Integer.parseInt(sizeOfBoardInArray[1]);
                }
                saveSettings();
                finish();
            }
        });
    }

    private void saveSettings(){
        SharedPreferences settings = getSharedPreferences(MainActivity.PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(MainActivity.BOARD_HEIGHT_KEY, savedBoardHeight);
        editor.putInt(MainActivity.BOARD_WIDTH_KEY, savedBoardWidth);
        editor.putInt(MainActivity.MINE_NUMBER_KEY, savedNumOfMines);
        editor.apply();
    }

    private void clearBestScore(){
        SharedPreferences settings = getSharedPreferences(MainActivity.PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.putInt(MainActivity.BOARD_HEIGHT_KEY, savedBoardHeight);
        editor.putInt(MainActivity.BOARD_WIDTH_KEY, savedBoardWidth);
        editor.putInt(MainActivity.MINE_NUMBER_KEY, savedNumOfMines);
        editor.putInt(Game.PLAYED_GAME_KEY, numOfStartedGames);
        editor.apply();
    }

    private void createNumOfMineRadioBtns() {
        RadioGroup grp = (RadioGroup)findViewById(R.id.rdgrpNumOfMines);
        int[] arrNumOfMines = getResources().getIntArray(R.array.number_of_mines);
        for (int numOfMine : arrNumOfMines) {
            RadioButton btn = new RadioButton(this);
            btn.setText(numOfMine + "");
            btn.setShadowLayer(3.0f, 1.0f, 1.0f, Color.BLACK);
            grp.addView(btn);
            if(numOfMine == savedNumOfMines) {
                btn.setChecked(true);
            }
        }
    }




    public static Intent makeIntent(Context context) {
        return new Intent(context,Option.class);
    }
    private void setupOPtionBackbtn() {
        Button btn=(Button) findViewById(R.id.optionbackbtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Option.this,"you Cicked back button", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
}
