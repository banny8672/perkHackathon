package com.example.bansi.perkhackathon.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bansi.perkhackathon.MainActivity;
import com.example.bansi.perkhackathon.Models.Question;
import com.example.bansi.perkhackathon.R;
import com.perk.perksdk.PerkManager;

import java.util.List;

/**
 * Created by bansi on 20-Dec-15.
 */
public class QuestionPagerAdapter extends android.support.v4.view.PagerAdapter {

    LayoutInflater inflater;
    Context mContext;
    private List<Question> questionList;

    TextView question;
    RadioButton ans1,ans2,ans3,ans4;
    RadioGroup radioGroup;
    ImageView backgroungImg;
    CardView resultCardView;
    TextView resultRightAns,resultWrongAns;
    RelativeLayout questionContainer;
    LinearLayout ll;
    Button perkButton;
    String key = "eea0808205bf432a3244860ea9b3286d8386ec18";

    public QuestionPagerAdapter(Context mContext,List<Question> questions){

        this.mContext = mContext;
        this.questionList =questions;
    }
    @Override
    public int getCount() {
        return questionList.size()+1;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View item = inflater.inflate(R.layout.question_layout,container,false);

        questionContainer = (RelativeLayout) item.findViewById(R.id.question_containers);
        backgroungImg = (ImageView) item.findViewById(R.id.background_img);
        question = (TextView) item.findViewById(R.id.question);
        radioGroup = (RadioGroup) item.findViewById(R.id.radio_group);
        ans1 = (RadioButton) item.findViewById(R.id.opt_one);
        ans2 = (RadioButton) item.findViewById(R.id.opt_two);
        ans3 = (RadioButton) item.findViewById(R.id.opt_three);
        ans4 = (RadioButton) item.findViewById(R.id.opt_four);

        perkButton = (Button) item.findViewById(R.id.perkButton);
        ll = (LinearLayout) item.findViewById(R.id.result_ll);
        resultCardView = (CardView) item.findViewById(R.id.card_view);
        resultRightAns = (TextView) item.findViewById(R.id.right_ans1);
        resultWrongAns = (TextView) item.findViewById(R.id.wrong_ans);

        perkButton.setOnClickListener(perkCLickListner);
        if(position != questionList.size()) {
            questionContainer.setVisibility(View.VISIBLE);
            resultCardView.setVisibility(View.GONE);
            ll.setVisibility(View.GONE);

            Question question1 = questionList.get(position);
            question.setText(question1.getQuestion());
            ans1.setText(question1.getOption1());
            ans2.setText(question1.getOption2());
            ans3.setText(question1.getOption3());
            ans4.setText(question1.getOption4());
            if (questionList.get(position).getAnsStatus() == 1) {

                if (ans1.getText().toString().equalsIgnoreCase(questionList.get(position).getCheckedAns())) {
                    ans1.setChecked(true);
                } else if (ans2.getText().toString().equalsIgnoreCase(questionList.get(position).getCheckedAns())) {
                    ans2.setChecked(true);

                } else if (ans3.getText().toString().equalsIgnoreCase(questionList.get(position).getCheckedAns())) {
                    ans3.setChecked(true);

                } else if (ans4.getText().toString().equalsIgnoreCase(questionList.get(position).getCheckedAns())) {
                    ans4.setChecked(true);

                }
                //item.setBackgroundColor(Color.parseColor("#C5CAE9"));
                backgroungImg.setImageResource(R.drawable.smile);

            } else if (questionList.get(position).getAnsStatus() == 2) {

                if (ans1.getText().toString().equalsIgnoreCase(questionList.get(position).getCheckedAns())) {
                    ans1.setChecked(true);
                } else if (ans2.getText().toString().equalsIgnoreCase(questionList.get(position).getCheckedAns())) {
                    ans2.setChecked(true);

                } else if (ans3.getText().toString().equalsIgnoreCase(questionList.get(position).getCheckedAns())) {
                    ans3.setChecked(true);

                } else if (ans4.getText().toString().equalsIgnoreCase(questionList.get(position).getCheckedAns())) {
                    ans4.setChecked(true);

                }
                //item.setBackgroundColor(Color.parseColor("#EF9A9A"));
                backgroungImg.setImageResource(R.drawable.tearfil_smiley);
            }
        }else{
            questionContainer.setVisibility(View.GONE);
            resultCardView.setVisibility(View.VISIBLE);
            ll.setVisibility(View.VISIBLE);
            ((MainActivity) mContext).setFooterVisibility();

            resultRightAns.setText("Right ans: " + ((MainActivity) mContext).getRightAns());
            resultWrongAns.setText("Wrong ans: "+((MainActivity)mContext).getWrongAns());
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton rb = (RadioButton) item.findViewById(checkedId);
                ImageView iv = (ImageView) item.findViewById(R.id.background_img);
                questionList.get(position).setCheckedAns(rb.getText().toString());

                if (validSelectedAns(rb.getText().toString(), position)) {

                    //item.setBackgroundColor(Color.parseColor("#C5CAE9"));
                    iv.setImageResource(R.drawable.smile);
                    questionList.get(position).setAnsStatus(1);
                    ((MainActivity)mContext).setRightAns(true, position);
                } else {

                    questionList.get(position).setAnsStatus(2);
                    ((MainActivity)mContext).setRightAns(false, position);
                  //  item.setBackgroundColor(Color.parseColor("#EF9A9A"));
                    iv.setImageResource(R.drawable.tearfil_smiley);
                }
            }
        });

        ((ViewPager) container).addView(item);

        return item;
    }

    View.OnClickListener perkCLickListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            PerkManager.showPortal(mContext,key);
        }
    };
    private boolean validSelectedAns(String text,int position) {

        return questionList.get(position).getAns().equalsIgnoreCase(text);

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);
    }

}
