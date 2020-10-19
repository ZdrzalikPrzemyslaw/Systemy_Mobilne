package tech.szymanskazdrzalik.zad_5;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends YouTubeBaseActivity {

    private YouTubePlayerView mYouTubePlayerView;
    private YouTubePlayer.OnInitializedListener onInitializedListener;
    private YouTubePlayer mYouTubePlayer;

    private List<List<String>> videos = new ArrayList<List<String>>() {
        {
            add(new ArrayList<String>(){
                {
                    add("Young Igi - Neandertalczyk");
                    add("FTIMS CG Showreel");
                    add("Tymek - Pomarańcze");
                    add("Tymek - Beautiful");
                }
            });
            add(new ArrayList<String>(){
                {
                    add("BRodTWirKRM");
                    add("Il1WDvX-qrg");
                    add("8M3VWV02T00");
                    add("CT7xfy8Nwrc");
                }
            });


        }
    };

    private int chosenVideo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.mYouTubePlayerView = findViewById(R.id.youtube_view);

        this.onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                try {
                    youTubePlayer.loadVideo(videos.get(1).get(chosenVideo));
                    MainActivity.this.mYouTubePlayer = youTubePlayer;
                } catch (Throwable t) {
                    Toast.makeText(MainActivity.this, "LOAD ERROR", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(MainActivity.this, "LOAD ERROR", Toast.LENGTH_SHORT).show();
            }
        };
    }

    public void mPlayVideoButtonOnClick(View v) {
        if (this.mYouTubePlayer == null) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Choose Video");
            alertDialogBuilder.setPositiveButton("Choose", (dialog, which) -> {
                mYouTubePlayerView.initialize("AIzaSyDDdVleA3IorMODeObfAqslgcSEpzNjkag", onInitializedListener);
                dialog.dismiss();
            });
            alertDialogBuilder.setSingleChoiceItems(videos.get(0).toArray(new String[0]), 0,
                    (dialog, which) -> this.chosenVideo = which);
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
        else {
            this.mYouTubePlayer.play();
        }
    }

    public void mPauseVideoButtonOnClick(View v) {
        if (this.mYouTubePlayer != null) {
            this.mYouTubePlayer.pause();
        }
    }

    public void mStopVideoButtonOnClick(View v) {
        if (this.mYouTubePlayer != null) {
            this.mYouTubePlayer.release();
            this.mYouTubePlayer = null;
        }
    }
}