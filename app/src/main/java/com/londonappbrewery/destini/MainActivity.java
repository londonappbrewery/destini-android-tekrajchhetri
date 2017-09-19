package com.londonappbrewery.destini;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.security.PrivateKey;

public class MainActivity extends AppCompatActivity {

    // TODO: Steps 4 & 8 - Declare member variables here:
    Button topButton;
    Button bottomButton;
    TextView story;
    int mstoryIndexTop = 1;
    int progressLeft = 1;
    int progressRight = 0;
    int mstoryIndexBottom = 1;
    int Question;
    int beginLeft = 0;
    int beginRight = 0;

    private Story[] questionBank = new Story[]{
            new Story(R.string.T1_Story,R.string.T1_Ans1,R.string.T1_Ans2),
            new Story(R.string.T2_Story,R.string.T2_Ans1,R.string.T2_Ans2),
            new Story(R.string.T3_Story,R.string.T3_Ans1,R.string.T3_Ans2),
            new Story(R.string.T4_End,R.string.T5_End,R.string.T6_End)

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        // TODO: Step 5 - Wire up the 3 views from the layout to the member variables:
        topButton = (Button)findViewById(R.id.buttonTop);
        bottomButton = (Button)findViewById(R.id.buttonBottom);
        story = (TextView) findViewById(R.id.storyTextView);

        if (savedInstanceState != null){
            progressLeft = savedInstanceState.getInt("progress");
            progressRight = savedInstanceState.getInt("progressRight");
            mstoryIndexTop = savedInstanceState.getInt("mstoryIndexTop");
            mstoryIndexBottom = savedInstanceState.getInt("mstoryIndexBottom");
            beginRight = savedInstanceState.getInt("beginRight");
            beginLeft = savedInstanceState.getInt("beginLeft");


            if(beginLeft == 1){
                leftDirection();

            }else if(beginRight == 1){
                rightDirection();

            }


        }


        // TODO: Steps 6, 7, & 9 - Set a listener on the top button:
        topButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Log.d("last element: "+questionBank.length,"element1: "+questionBank[questionBank.length].getAnswerText1());
                mstoryIndexTop = (mstoryIndexTop +1 )% questionBank.length;
                if(beginRight == 0 && beginLeft >= 0){
                    beginLeft = 1;
                    beginRight = 0;
                    updateStory(beginLeft,beginRight);
                }else{

                    updateStory(beginLeft,beginRight);
                }



            }
        });




        // TODO: Steps 6, 7, & 9 - Set a listener on the bottom button:
        bottomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // Log.d("Bottom: "+mstoryIndexBottom," Top: "+mstoryIndexTop);
                mstoryIndexBottom = (mstoryIndexBottom +1 )% questionBank.length;
                if(beginLeft ==0 && beginRight >=0){
                    beginRight =1;
                    beginLeft = 0;
                    updateStory(beginLeft,beginRight);
                }else {
                    updateStory(beginLeft,beginRight);
                }

                    }
        });


    }

    private void updateStory(int beginLeft, int beginRight){

        progressLeft = (progressLeft +1)% questionBank.length;
        progressRight = (progressRight +1)% questionBank.length;
        if(beginLeft == 1){
            leftDirection();
        }else if(beginRight == 1){
                rightDirection();
        }




    }

    //left direction
    private void leftDirection(){
        if(mstoryIndexTop == 3 && mstoryIndexBottom == 1){
            endTop();
        }else if(mstoryIndexTop >=2 && mstoryIndexBottom >1){
            endBottom();
        }
        else{
            updateAllText(progressLeft);
        }
    }
    //begin in right direction
    private void rightDirection(){
        if(mstoryIndexTop == 1 && mstoryIndexBottom == 2){
            updateAllText(progressRight);
        }else if (mstoryIndexBottom == 2 && mstoryIndexTop == 2){
            updateAllText(progressRight);
        }else if(mstoryIndexTop ==1 && mstoryIndexBottom >2){
            endT4();
        }else if (mstoryIndexTop == 3 && mstoryIndexBottom == 2){
            endTop();
        }else if(mstoryIndexBottom ==3 && mstoryIndexTop == 2){
            endBottom();
        }
    }

    //end T4
    private void endT4(){
        hideButton();
        story.setText(questionBank[questionBank.length - 1].getStoryText());
    }
    //end by clicking top button T6 end
    private void endTop(){
        hideButton();
        story.setText(questionBank[questionBank.length - 1].getAnswerText2());
    }
   // end by clicking bottom button T5 end
    private void endBottom(){
        hideButton();
        story.setText(questionBank[questionBank.length - 1].getAnswerText1());
    }

    private  void  updateAllText(int progress){
        story.setText(questionBank[progress].getStoryText());
        topButton.setText(questionBank[progress].getAnswerText1());
        bottomButton.setText(questionBank[progress].getAnswerText2());
    }

    private void updateOnRotate(int progressLeft){

        updateAllText(progressLeft);
    }

    private void hideButton(){
        topButton.setVisibility(View.GONE);
        bottomButton.setVisibility(View.GONE);
    }


    //saving the state
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("progress",progressLeft);
        outState.putInt("progressRight",progressRight);
        outState.putInt("mstoryIndexTop",mstoryIndexTop);
        outState.putInt("mstoryIndexBottom",mstoryIndexBottom);
        outState.putInt("beginLeft",beginLeft);
        outState.putInt("beginRight",beginRight);
    }
}
