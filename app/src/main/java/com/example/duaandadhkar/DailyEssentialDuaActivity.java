package com.example.duaandadhkar;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class DailyEssentialDuaActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private MyDatabaseHelper myDatabaseHelper;

    private final int numOfAudios=8;
    private int[] audiosSeekPos = new int[numOfAudios];

    private int[] audiosIds = new int[numOfAudios];

    //buttons ids
    private int[] playButtonsIds = new int[numOfAudios];
    private int[] pauseButtonsIds = new int[numOfAudios];
    private int[] stopButtonsIds = new int[numOfAudios];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_essential_dua);
        myDatabaseHelper = new MyDatabaseHelper(this);
        mediaPlayer = new MediaPlayer();
        audiosIds = new int[]{R.raw.essential_audio_01,R.raw.essential_audio_02,R.raw.essential_audio_03,R.raw.essential_audio_04,R.raw.essential_audio_05,
                R.raw.essential_audio_06,R.raw.essential_audio_07,R.raw.essential_audio_08};
        playButtonsIds = new int[]{R.id.playButton01,R.id.playButton02,R.id.playButton03,R.id.playButton04,R.id.playButton05,
                R.id.playButton06,R.id.playButton07,R.id.playButton08};
        pauseButtonsIds = new int[]{R.id.pauseButton01,R.id.pauseButton02,R.id.pauseButton03,R.id.pauseButton04,R.id.pauseButton05,
                R.id.pauseButton06,R.id.pauseButton07,R.id.pauseButton08};
        stopButtonsIds = new int[]{R.id.stopButton01,R.id.stopButton02,R.id.stopButton03,R.id.stopButton04,R.id.stopButton05,
                R.id.stopButton06,R.id.stopButton07,R.id.stopButton08};
        for (int i = 0; i < numOfAudios; i++) {
            InputStream inputStream = getResources().openRawResource(audiosIds[i]);
            try {
                byte[] audioData = new byte[inputStream.available()];
                String audioName = String.format("%02d",i+1);
                audioName="essential_audio_"+audioName;
                inputStream.read(audioData);
                myDatabaseHelper.insertBlobData(audioData,audioName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        for (int i = 0; i < numOfAudios; i++) {
            ImageButton playButton = findViewById(playButtonsIds[i]);
            ImageButton pauseButton = findViewById(pauseButtonsIds[i]);
            pauseButton.setEnabled(false);
            pauseButton.setImageDrawable(getDrawable(R.drawable.baseline_pause_circle_disable_45));
            ImageButton stopButton = findViewById(stopButtonsIds[i]);
            stopButton.setEnabled(false);
            stopButton.setImageDrawable(getDrawable(R.drawable.baseline_stop_circle_disable_45));
        }

        for (int i = 0; i < numOfAudios; i++) {
            audiosSeekPos[i]=0;
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void playAudio(View view){
        //view is play button
        ImageButton playButton = (ImageButton) view;
        int viewId = view.getId();
        String viewIdStr = getResources().getResourceEntryName(viewId);
        String setNumber = Character.toString(viewIdStr.charAt(viewIdStr.length()-2))+Character.toString(viewIdStr.charAt(viewIdStr.length()-1));
        int setNumberIndex = Integer.parseInt(setNumber)-1;
        // pause button
        ImageButton pauseButton = findViewById(pauseButtonsIds[setNumberIndex]);
        //stop button
        ImageButton stopButton = findViewById(stopButtonsIds[setNumberIndex]);


        try {
            byte[] audioData = myDatabaseHelper.getBlobData("essential_audio_" + setNumber);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(audioData);
            mediaPlayer.setDataSource(new ByteArrayMediaDataSource(audioData));
            mediaPlayer.prepare();
            mediaPlayer.seekTo(audiosSeekPos[setNumberIndex]);
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                audiosSeekPos[setNumberIndex]=0;
                mediaPlayer = new MediaPlayer();
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
        int viewId = view.getId();
        String viewIdStr = getResources().getResourceEntryName(viewId);
        String setNumber = Character.toString(viewIdStr.charAt(viewIdStr.length()-2))+Character.toString(viewIdStr.charAt(viewIdStr.length()-1));
        int setNumberIndex = Integer.parseInt(setNumber)-1;
        //play button
        ImageButton playButton = findViewById(playButtonsIds[setNumberIndex]);
        //stop button
        ImageButton stopButton = findViewById(stopButtonsIds[setNumberIndex]);


        audiosSeekPos[setNumberIndex]=mediaPlayer.getCurrentPosition();
        mediaPlayer.stop();
        mediaPlayer = new MediaPlayer();

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
        int setNumberIndex = Integer.parseInt(setNumber)-1;
        //play button
        ImageButton playButton = findViewById(playButtonsIds[setNumberIndex]);
        // pause button
        ImageButton pauseButton = findViewById(pauseButtonsIds[setNumberIndex]);

        /*
        stop the audio (reset to initial state)
        * */
        audiosSeekPos[setNumberIndex]=0;
        mediaPlayer.stop();

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