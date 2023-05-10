package com.example.duaandadhkar;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.CursorWindow;
import android.media.MediaDataSource;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Objects;

public class RuquiyaActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private MyDatabaseHelper myDatabaseHelper;

    private String audiosState;
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruquiya);
//        myDatabaseHelper = new MyDatabaseHelper(this);
        mediaPlayer = new MediaPlayer();
//        InputStream inputStream = getResources().openRawResource(R.raw.ruquiya);
//
//        try {
//            byte[] audioData = new byte[inputStream.available()];
//            String audioName = "ruquiya";
//            myDatabaseHelper.insertBlobData(audioData,audioName);
//            audiosState="OFF";
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        audiosState = "OFF";
        ImageButton playButton = findViewById(R.id.playButton);
        ImageButton pauseButton = findViewById(R.id.pauseButton);
        pauseButton.setEnabled(false);
        pauseButton.setImageDrawable(getDrawable(R.drawable.baseline_pause_circle_disable_45));
        ImageButton stopButton = findViewById(R.id.stopButton);
        stopButton.setEnabled(false);
        stopButton.setImageDrawable(getDrawable(R.drawable.baseline_stop_circle_disable_45));
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    public void playAudio(View view){
        //view is play button
        ImageButton playButton = (ImageButton) view;
        // pause button
        ImageButton pauseButton = findViewById(R.id.pauseButton);
        //stop button
        ImageButton stopButton = findViewById(R.id.stopButton);
        /*
        PLAY
        * */
        if(Objects.equals(audiosState, "PAUSE")){
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
            }
        }else if(Objects.equals(audiosState, "OFF")) {
            try {
//                byte[] audioData = myDatabaseHelper.getBlobData("ruquiya");
//                ByteArrayInputStream inputStream = new ByteArrayInputStream(audioData);
                mediaPlayer.setDataSource(this, Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.ruquiya));
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                playButton.setEnabled(true);
                playButton.setImageDrawable(getDrawable(R.drawable.baseline_play_circle_45));

                pauseButton.setEnabled(false);
                pauseButton.setImageDrawable(getDrawable(R.drawable.baseline_pause_circle_disable_45));

                stopButton.setEnabled(false);
                stopButton.setImageDrawable(getDrawable(R.drawable.baseline_stop_circle_disable_45));
            }
        });

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

        //play button
        ImageButton playButton = findViewById(R.id.playButton);
        //stop button
        ImageButton stopButton = findViewById(R.id.stopButton);
        /*
        pause the audio
        * */
        mediaPlayer.pause();
        audiosState="PAUSE";

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
        //play button
        ImageButton playButton = findViewById(R.id.playButton);
        // pause button
        ImageButton pauseButton = findViewById(R.id.pauseButton);

        /*
        stop the audio (reset to initial state)
        * */
        mediaPlayer.stop();
        audiosState="OFF";
        mediaPlayer = new MediaPlayer();

        playButton.setEnabled(true);
        playButton.setImageDrawable(getDrawable(R.drawable.baseline_play_circle_45));

        pauseButton.setEnabled(false);
        pauseButton.setImageDrawable(getDrawable(R.drawable.baseline_pause_circle_disable_45));

        stopButton.setEnabled(false);
        stopButton.setImageDrawable(getDrawable(R.drawable.baseline_stop_circle_disable_45));
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}