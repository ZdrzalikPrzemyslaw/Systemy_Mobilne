package tech.szymanskazdrzalik.zad_5;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends YouTubeBaseActivity {

    private final Map<String, String> videosMap = new HashMap<String, String>() {
        {
            put("Young Igi - Neandertalczyk", "BRodTWirKRM");
            put("FTIMS CG Showreel", "Il1WDvX-qrg");
            put("Tymek - Pomarańcze", "8M3VWV02T00");
            put("Tymek - Beautiful", "CT7xfy8Nwrc");
        }
    };
    private YouTubePlayerView mYouTubePlayerView;
    private YouTubePlayer.OnInitializedListener onInitializedListener;
    private YouTubePlayer mYouTubePlayer;
    private String videoKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.mYouTubePlayerView = findViewById(R.id.youtube_view);

        this.onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                try {
                    youTubePlayer.loadVideo(videosMap.get(videoKey));
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
            alertDialogBuilder.setSingleChoiceItems(videosMap.keySet().toArray(new String[0]), 0,
                    (dialog, which) -> {
                        this.videoKey = ((CheckedTextView)((AlertDialog) dialog).getListView().getChildAt(which)).getText().toString();
                        System.out.println(this.videoKey);
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        } else {
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