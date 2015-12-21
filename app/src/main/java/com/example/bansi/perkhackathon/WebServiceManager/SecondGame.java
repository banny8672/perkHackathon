package com.example.bansi.perkhackathon.WebServiceManager;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bansi.perkhackathon.MainActivity;
import com.example.bansi.perkhackathon.R;
import com.perk.perksdk.PerkManager;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by bansi on 21-Dec-15.
 */
public class SecondGame extends AppCompatActivity implements View.OnClickListener{

    TextView colorName, totalScore;
    LinearLayout layout;
    int TIME = 500;
    final Handler myHandler = new Handler();
    String colorHexaal = null;
    String colorTemp = null;
    int counter =0,clickCounter=0;
    Timer myTimer;
    Timer myTimer1;

    String key = "eea0808205bf432a3244860ea9b3286d8386ec18";
    String eventId = "3717fc06368491f70834ad9e100db704b1d1c782";

    final Handler myHandler1 = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mind_game);

        PerkManager.startSession(SecondGame.this, key);
        layout = (LinearLayout) findViewById(R.id.linear_layout);
        colorName = (TextView) findViewById(R.id.color_name);
        totalScore = (TextView) findViewById(R.id.total_score);

        layout.setOnClickListener(this);

        myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
            UpdateLayout();
            }
        }, 0, 200);

        myTimer1 = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                UpdateGUI();
            }
        }, 0, 200);


    }

    private void UpdateLayout(){

        myHandler1.post(new Runnable() {
            @Override
            public void run() {
                int randNum = randInt(0,9);
                if(randNum == 0){
                    colorHexaal = "#00FF00";
                    layout.setBackgroundColor(Color.parseColor("#00FF00"));
                }else if(randNum == 1){
                    colorHexaal = "#1A237E";
                    layout.setBackgroundColor(Color.parseColor("#1A237E"));
                }else if(randNum == 2){
                    colorHexaal = "#00FF00";
                    layout.setBackgroundColor(Color.parseColor("#FF0000"));
                }else if(randNum == 3){
                    colorHexaal = "#ff8000";
                    layout.setBackgroundColor(Color.parseColor("#ff8000"));
                }else if(randNum == 4){
                    colorHexaal = "#00FF00";
                    layout.setBackgroundColor(Color.parseColor("#00FF00"));
                }
                else if(randNum == 5){
                    colorHexaal = "#00FF00";
                    layout.setBackgroundColor(Color.parseColor("#00FF00"));
                }
                else if(randNum == 6){
                    colorHexaal = "#1A237E";
                    layout.setBackgroundColor(Color.parseColor("#1A237E"));

                }else if(randNum == 7){
                    colorHexaal = "#1A237E";
                    layout.setBackgroundColor(Color.parseColor("#1A237E"));

                }else if(randNum == 8){
                    colorHexaal = "#ff8000";
                    layout.setBackgroundColor(Color.parseColor("#ff8000"));

                }else if(randNum == 9){
                    colorHexaal = "#ff8000";
                    layout.setBackgroundColor(Color.parseColor("#ff8000"));

                }

            }
        });
    }

    private void UpdateGUI() {

        myHandler.post(new Runnable() {
            @Override
            public void run() {
                int randNum = randInt(0,9);
                if(randNum == 0){
                    colorTemp = "#00FF00";
                    colorName.setText("GREEN");

                }else if(randNum == 1){
                    colorTemp = "#00FF00";
                    colorName.setText("GREEN");
                }else if(randNum == 2){
                    colorTemp = "#00FF00";
                    colorName.setText("GREEN");
                }else if(randNum == 3){
                    colorTemp = "#ff8000";
                    colorName.setText("YELLOW");
                }else if(randNum == 4){
                    colorTemp = "#ff8000";
                    colorName.setText("YELLOW");
                }
                else if(randNum == 5){
                    colorTemp = "#FF0000";
                    colorName.setText("RED");
                }
                else if(randNum == 6){
                    colorTemp = "#00FF00";
                    colorName.setText("GREEN");

                }else if(randNum == 7){
                    colorTemp = "#FF0000";
                    colorName.setText("RED");

                }else if (randNum == 8){
                    colorTemp = "#1A237E";
                    colorName.setText("BLUE");

                }else if(randNum == 9){
                    colorTemp = "#1A237E";
                    colorName.setText("BLUE");

                }
            }
        });
    }

    public int randInt(int min, int max) {

        Random rand = new Random();

        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    @Override
    public void onClick(View v) {
        String colorNameTV  = colorName.getText().toString();

        int color = Color.TRANSPARENT;
        String colorName= null;
        if(v == layout){
            clickCounter = clickCounter+1;
            if(clickCounter <15){

                if(colorTemp.equalsIgnoreCase(colorHexaal)){
                    counter +=1;
                    //Toast.makeText(SecondGame.this,"HIIIIIII",Toast.LENGTH_SHORT).show();
                    totalScore.setText("Score: "+counter);

                    if(counter%3 == 0){
                        PerkManager.trackEvent(SecondGame.this, key, eventId, true, null);
                    }
                }
            }else{

                myTimer.cancel();
                myTimer1.cancel();
                Toast.makeText(SecondGame.this,"You are running out of click. please try again later",Toast.LENGTH_SHORT).show();
            }

        }

    }
}