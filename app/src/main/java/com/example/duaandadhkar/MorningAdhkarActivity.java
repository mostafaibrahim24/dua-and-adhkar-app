package com.example.duaandadhkar;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.MediaDataSource;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Objects;

public class MorningAdhkarActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private MyDatabaseHelper myDatabaseHelper;

    private final int numOfAudios=2;
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
        setContentView(R.layout.activity_morning_adhkar_activity);
        myDatabaseHelper = new MyDatabaseHelper(this);
        mediaPlayer = new MediaPlayer();

        audiosIds = new int[]{R.raw.audio_01,
                              R.raw.audio_02};
        playButtonsIds = new int[]{R.id.playButton01,R.id.playButton02};
        pauseButtonsIds = new int[]{R.id.pauseButton01,R.id.pauseButton02};
        stopButtonsIds = new int[]{R.id.stopButton01,R.id.stopButton02};

        for (int i = 0; i < numOfAudios; i++) {
            InputStream inputStream = getResources().openRawResource(audiosIds[i]);
            try {
                byte[] audioData = new byte[inputStream.available()];
                String audioName = String.format(Locale.ENGLISH,"audio_%2d",i);
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
                byte[] audioData = myDatabaseHelper.getBlobData("audio_" + setNumber);
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