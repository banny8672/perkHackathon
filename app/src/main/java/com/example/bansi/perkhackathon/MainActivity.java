package com.example.bansi.perkhackathon;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bansi.perkhackathon.Adapter.QuestionPagerAdapter;
import com.example.bansi.perkhackathon.CallBackInterFace.ResultCallBack;
import com.example.bansi.perkhackathon.Models.Question;
import com.example.bansi.perkhackathon.WebServiceManager.SecondGame;
import com.example.bansi.perkhackathon.WebServiceManager.WebService;
import com.perk.perksdk.PerkInterface;
import com.perk.perksdk.PerkListener;
import com.perk.perksdk.PerkManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private LinearLayout bottomLayout;
    private TextView wrongAns;
    private TextView rightAns;
    private ViewPager pager;
    private QuestionPagerAdapter questionPagerAdapter;
    private List<Question> questionList;
    private WebService webService;
    private int rightNumAns = 0,wrongNumAns=0;
    byte[] questionResult;
    int rightCout = 0,wrongCount = 0;
    String key = "eea0808205bf432a3244860ea9b3286d8386ec18";
    String eventId = "3717fc06368491f70834ad9e100db704b1d1c782";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        bottomLayout  = (LinearLayout) findViewById(R.id.bottom_ll);
        wrongAns = (TextView) findViewById(R.id.wrong_ans);
        rightAns = (TextView) findViewById(R.id.correct_ans);
        pager = (ViewPager) findViewById(R.id.pager);
        webService = new WebService(getApplication());

        PerkManager.startSession(MainActivity.this, key);


        getQustionData();

    }

    private void getQustionData() {
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        webService.getQuestion(new ResultCallBack<List<Question>>() {
            @Override
            public void onResultCallBack(List<Question> object, String exception) {
                if (object != null && exception == null) {

                    questionList = object;
                    //Toast.makeText(getApplication(), "Volley response  " + object.size(), Toast.LENGTH_SHORT).show();
                    questionPagerAdapter = new QuestionPagerAdapter(MainActivity.this, questionList);
                    pager.setAdapter(questionPagerAdapter);
                    wrongAns.setText("Wrong ans: 0");
                    questionResult = new byte[object.size()];
                    rightAns.setText("Right ans: 0");
                    pDialog.dismiss();

                } else {
                    pDialog.dismiss();
                    Toast.makeText(getApplication(), "Network error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void setRightAns(boolean ans,int position){

        if(ans){
            questionResult[position] = 1;

            PerkManager.trackEvent(MainActivity.this, key, eventId, true,null);
        }else{

            questionResult[position] = 2;
        }

        rightWrongCount(position);
        rightAns.setText("Right ans: " + rightCout);
        wrongAns.setText("Wrong ans: " + wrongCount);

    }


    private void rightWrongCount(int index){
        rightCout = 0;
        wrongCount = 0;
        for(int i = 0; i <= index ; i++){

            if(questionResult[i] == 1){
                rightCout++;
            }else if(questionResult[i] == 2){
                wrongCount++;
            }
        }
    }

    PerkInterface perkInterface = new PerkInterface() {
        @Override
        public void showEarningDialog() {
            Toast.makeText(MainActivity.this,"Dialog temp",Toast.LENGTH_SHORT).show();
        }
    };
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            startActivity(new Intent(MainActivity.this, SecondGame.class));
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public int getRightAns(){
        return rightCout;
    }

    public int getWrongAns(){
        return wrongCount;
    }

    public void setFooterVisibility(){

        bottomLayout.setVisibility(View.GONE);

    }
}
