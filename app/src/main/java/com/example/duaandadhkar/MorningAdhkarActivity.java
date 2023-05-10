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
import java.util.Objects;

public class MorningAdhkarActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private MyDatabaseHelper myDatabaseHelper;

    private final int numOfAudios=1;
    // STATE: OFF, PAUSE
    private int[] audiosNums;
    private String[] audiosState = new String[1];
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morning_adhkar_activity);
        myDatabaseHelper = new MyDatabaseHelper(this);
        mediaPlayer = new MediaPlayer();
        InputStream inputStream = getResources().openRawResource(R.raw.audio_01);

        try {
            byte[] audioData1 = new byte[inputStream.available()];
            String audioName1 = "audio_01";
            inputStream.read(audioData1);
            myDatabaseHelper.insertBlobData(audioData1,audioName1);
            audiosState[0]="OFF";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ImageButton playButton01 = findViewById(R.id.playButton01);
        ImageButton pauseButton01 = findViewById(R.id.pauseButton01);
        pauseButton01.setEnabled(false);
        pauseButton01.setImageDrawable(getDrawable(R.drawable.baseline_pause_circle_disable_45));
        ImageButton stopButton01 = findViewById(R.id.stopButton01);
        stopButton01.setEnabled(false);
        stopButton01.setImageDrawable(getDrawable(R.drawable.baseline_stop_circle_disable_45));
        /////////////////////////////////////////////////////////////////



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
        PLAY
        * */

        if(Objects.equals(audiosState[0], "PAUSE")){
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
            }
        }else if(Objects.equals(audiosState[0], "OFF")) {
            try {
                //            mediaPlayer.setDataSource(getApplicationContext(), Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.audio_01));
                byte[] audioData = myDatabaseHelper.getBlobData("audio_" + setNumber);
                ByteArrayInputStream inputStream = new ByteArrayInputStream(audioData);
                mediaPlayer.setDataSource(
                        new MediaDataSource() {
                            @Override
                            public int readAt(long position, byte[] buffer, int offset, int size) throws IOException {
                                if (position >= audioData.length) {
                                    return -1;
                                }
                                int length = (int) Math.min(size, audioData.length - position);
                                System.arraycopy(audioData, (int) position, buffer, offset, length);
                                return length;
                            }

                            @Override
                            public long getSize() throws IOException {
                                return audioData.length;
                            }

                            @Override
                            public void close() throws IOException {
                                // Nothing to do here
                            }
                        }
                );
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
        mediaPlayer.pause();
        audiosState[0]="PAUSE";

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
        mediaPlayer.stop();
        audiosState[0]="OFF";
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