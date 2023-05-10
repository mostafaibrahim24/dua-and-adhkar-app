package com.example.duaandadhkar;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MorningAdhkarActivity extends AppCompatActivity {

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morning_adhkar_activity);
        ImageButton pauseButton = findViewById(R.id.pauseButton01);
        pauseButton.setEnabled(false);
        pauseButton.setImageDrawable(getDrawable(R.drawable.baseline_pause_circle_disable_45));

        ImageButton stopButton = findViewById(R.id.stopButton01);
        stopButton.setEnabled(false);
        stopButton.setImageDrawable(getDrawable(R.drawable.baseline_stop_circle_disable_45));


    }
    @SuppressLint("UseCompatLoadingForDrawables")
    public void playAudio(View view){
        //view is play button
        ImageButton playButton = (ImageButton) view;
        int viewId = view.getId();
        String viewIdStr = getResources().getResourceEntryName(viewId);
        String setNumber = Character.toString(viewIdStr.charAt(viewIdStr.length()-2))+Character.toString(viewIdStr.charAt(viewIdStr.length()-1));
        // pause button
        String pauseButtonIdStr = "pauseButton"+setNumber;

        int pauseButtonId = getResources().getIdentifier(pauseButtonIdStr, "id","com.example.duaandadhkar");
        ImageButton pauseButton = findViewById(pauseButtonId);
        //stop button
        String stopButtonIdStr = "stopButton"+setNumber;
        int stopButtonId = getResources().getIdentifier(stopButtonIdStr, "id","com.example.duaandadhkar");
        ImageButton stopButton = findViewById(stopButtonId);
        /*
        play the audio
        * */
        playButton.setEnabled(false);
        playButton.setImageDrawable(getDrawable(R.drawable.baseline_play_circle_disable_45));

        pauseButton.setEnabled(true);
        pauseButton.setImageDrawable(getDrawable(R.drawable.baseline_pause_circle_45));

        stopButton.setEnabled(true);
        stopButton.setImageDrawable(getDrawable(R.drawable.baseline_stop_circle_45));
    }

    public void pauseAudio(View view){
        //view is pause button
        ImageButton pauseButton = (ImageButton) view;
        int viewId = view.getId();
        String viewIdStr = getResources().getResourceEntryName(viewId);
        String setNumber = Character.toString(viewIdStr.charAt(viewIdStr.length()-2))+Character.toString(viewIdStr.charAt(viewIdStr.length()-1));
        //play button
        String playButtonIdStr = "playButton"+setNumber;

        int playButtonId = getResources().getIdentifier(playButtonIdStr, "id","com.example.duaandadhkar");
        ImageButton playButton = findViewById(playButtonId);
        //stop button
        String stopButtonIdStr = "stopButton"+setNumber;
        int stopButtonId = getResources().getIdentifier(stopButtonIdStr, "id","com.example.duaandadhkar");
        ImageButton stopButton = findViewById(stopButtonId);
        /*
        pause the audio
        * */

        playButton.setEnabled(true);
        playButton.setImageDrawable(getDrawable(R.drawable.baseline_play_circle_45));

        pauseButton.setEnabled(false);
        pauseButton.setImageDrawable(getDrawable(R.drawable.baseline_pause_circle_disable_45));

        stopButton.setEnabled(true);
        stopButton.setImageDrawable(getDrawable(R.drawable.baseline_stop_circle_45));

    }

    public void stopAudio(View view){
        //view is stop button
        ImageButton stopButton = (ImageButton) view;
        int viewId = view.getId();
        String viewIdStr = getResources().getResourceEntryName(viewId);
        String setNumber = Character.toString(viewIdStr.charAt(viewIdStr.length()-2))+Character.toString(viewIdStr.charAt(viewIdStr.length()-1));
        //play button
        String playButtonIdStr = "playButton"+setNumber;
        int playButtonId = getResources().getIdentifier(playButtonIdStr, "id","com.example.duaandadhkar");
        ImageButton playButton = findViewById(playButtonId);
        // pause button
        String pauseButtonIdStr = "pauseButton"+setNumber;

        int pauseButtonId = getResources().getIdentifier(pauseButtonIdStr, "id","com.example.duaandadhkar");
        ImageButton pauseButton = findViewById(pauseButtonId);

        /*
        stop the audio (reset to initial state)
        * */
        playButton.setEnabled(true);
        playButton.setImageDrawable(getDrawable(R.drawable.baseline_play_circle_45));

        pauseButton.setEnabled(false);
        pauseButton.setImageDrawable(getDrawable(R.drawable.baseline_pause_circle_disable_45));

        stopButton.setEnabled(false);
        stopButton.setImageDrawable(getDrawable(R.drawable.baseline_stop_circle_disable_45));
    }
}