package utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.NonNull;

public class VideoPlayerView extends FrameLayout {
    private VideoView mVideoView;
    private int lastWidth;
    private int lastHeight;
    private boolean mIsFullScreen;

    public VideoPlayerView(@NonNull Context context) {
        super(context);
    }

    public void play(String url) {
        mVideoView = new VideoView(getContext());
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        addView(mVideoView, params);

        MediaController controller = new MediaController(getContext());
        mVideoView.setMediaController(controller);

        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                Log.d("Video", "onPrepared");
            }
        });

        mVideoView.setVideoPath(url);
        mVideoView.start();
    }

    public void pause() {
        mVideoView.pause();
    }

    public void seekTo(int second) {
        mVideoView.seekTo(second);
    }

    public void start() {
        mVideoView.start();
    }

    public void stop() {
        mVideoView.stopPlayback();
        removeView(mVideoView);
    }

    public int getCurrentPosition() {
        return mVideoView.getCurrentPosition();
    }

    public void fullScreen() {
        if (mIsFullScreen) {
            return;
        }
        Activity activity = (Activity) getContext();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                ViewGroup.LayoutParams params = getLayoutParams();
                lastWidth = params.width;
                lastHeight = params.height;

                params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                params.height = ViewGroup.LayoutParams.MATCH_PARENT;

                setLayoutParams(params);
                mIsFullScreen = true;
            }
        });
    }

    public void exitFullScreen() {
        if (!mIsFullScreen) {
            return;
        }
        Activity activity = (Activity) getContext();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                ViewGroup.LayoutParams params = getLayoutParams();

                params.width = lastWidth;
                params.height = lastHeight;

                setLayoutParams(params);
                mIsFullScreen = false;
            }
        });
    }

    public void onBackPressed() {
        if(mIsFullScreen == true) {
            exitFullScreen();
        }
    }
}
