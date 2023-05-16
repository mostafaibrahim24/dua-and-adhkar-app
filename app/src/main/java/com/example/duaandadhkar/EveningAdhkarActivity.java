package com.example.duaandadhkar;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class EveningAdhkarActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private MyDatabaseHelper myDatabaseHelper;

    private final int numOfAudios=22;
    private int[] audiosSeekPos = new int[numOfAudios];

    private int[] audiosIds = new int[numOfAudios];

    //buttons ids
    private int[] playButtonsIds = new int[numOfAudios];
    private int[] pauseButtonsIds = new int[numOfAudios];
    private int[] stopButtonsIds = new int[numOfAudios];
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evening_adhkar_activity);
        myDatabaseHelper = new MyDatabaseHelper(this);
        mediaPlayer = new MediaPlayer();

        audiosIds = new int[]{R.raw.audio_01,R.raw.audio_02,R.raw.e_audio_03,R.raw.e_audio_04,R.raw.audio_05,
                              R.raw.e_audio_06,R.raw.e_audio_07,R.raw.audio_08,R.raw.audio_09,R.raw.audio_10,
                              R.raw.audio_11,R.raw.audio_12,R.raw.audio_13,R.raw.audio_14,R.raw.e_audio_15,
                              R.raw.e_audio_16,R.raw.audio_17,R.raw.audio_18,R.raw.audio_19,R.raw.audio_20,R.raw.audio_21,R.raw.audio_22};
        playButtonsIds = new int[]{R.id.playButton01,R.id.playButton02,R.id.playButton03,R.id.playButton04,R.id.playButton05,
                                   R.id.playButton06,R.id.playButton07,R.id.playButton08,R.id.playButton09,R.id.playButton10,
                                   R.id.playButton11,R.id.playButton12,R.id.playButton13,R.id.playButton14,R.id.playButton15,
                                   R.id.playButton16,R.id.playButton17,R.id.playButton18,R.id.playButton19,R.id.playButton20,R.id.playButton21,R.id.playButton22};
        pauseButtonsIds = new int[]{R.id.pauseButton01,R.id.pauseButton02,R.id.pauseButton03,R.id.pauseButton04,R.id.pauseButton05,
                R.id.pauseButton06,R.id.pauseButton07,R.id.pauseButton08,R.id.pauseButton09,R.id.pauseButton10,
                R.id.pauseButton11,R.id.pauseButton12,R.id.pauseButton13,R.id.pauseButton14,R.id.pauseButton15,
                R.id.pauseButton16,R.id.pauseButton17,R.id.pauseButton18,R.id.pauseButton19,R.id.pauseButton20,R.id.pauseButton21,R.id.pauseButton22};
        stopButtonsIds = new int[]{R.id.stopButton01,R.id.stopButton02,R.id.stopButton03,R.id.stopButton04,R.id.stopButton05,
                R.id.stopButton06,R.id.stopButton07,R.id.stopButton08,R.id.stopButton09,R.id.stopButton10,
                R.id.stopButton11,R.id.stopButton12,R.id.stopButton13,R.id.stopButton14,R.id.stopButton15,
                R.id.stopButton16,R.id.stopButton17,R.id.stopButton18,R.id.stopButton19,R.id.stopButton20,R.id.stopButton21,R.id.stopButton22};

        for (int i = 0; i < numOfAudios; i++) {
            InputStream inputStream = getResources().openRawResource(audiosIds[i]);
            try {
                byte[] audioData = new byte[inputStream.available()];
                String audioName = String.format("%02d",i+1);
                audioName="audio_"+audioName;
                if(i+1 == 3 || i+1 == 4 || i+1 == 6 || i+1 == 7 || i+1 == 15 || i+1 == 16){
                    audioName="e_"+audioName;
                }
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
                byte[] audioData;
                if(setNumberIndex+1 == 3 || setNumberIndex+1 == 4 || setNumberIndex+1 == 6 || setNumberIndex+1 == 7 || setNumberIndex+1 == 15 || setNumberIndex+1 == 16){
                    audioData = myDatabaseHelper.getBlobData("e_audio_" + setNumber);
                }else{
                    audioData = myDatabaseHelper.getBlobData("audio_" + setNumber);
                }
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