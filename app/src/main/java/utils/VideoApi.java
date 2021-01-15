package utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.dsbridage_learning.R;

import org.json.JSONException;
import org.json.JSONObject;

import wendu.dsbridge.CompletionHandler;

public class VideoApi {
    private Context mContext;

    /*标记播放器是否attach 上*/
    private boolean mIsAttach;

    private static VideoPlayerView mVideoView;

    public VideoApi(Context context) {
        mContext = context;
        mVideoView = new VideoPlayerView(context);
    }

    public static boolean onBackPressed() {
        if (mVideoView != null) {
            mVideoView.onBackPressed();
        }
        return true;
    }


    @JavascriptInterface
    public String start(Object msg) {
        if (mIsAttach) {
            return "video already start";
        }
        String url = "";
        int width = 0, height = 0;
        try {
            JSONObject videoInfo = new JSONObject((String)msg);
            url = videoInfo.getString("url");
            width = videoInfo.getInt("width");
            height = videoInfo.getInt("height");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        /*
        * js 线程不能操作ui
        * */
        Activity activity = (Activity) mContext;
        int finalWidth = width;
        int finalHeight = height;
        String finalUrl = url;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                FrameLayout contentView = activity.findViewById(R.id.root_view);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(finalWidth, finalHeight);
                contentView.addView(mVideoView, params);
                mIsAttach = true;
                mVideoView.play(finalUrl);
            }
        });
        return "start";
    }

    @JavascriptInterface
    public void pause(Object msg) {
        mVideoView.pause();
    }

    @JavascriptInterface
    public void stop(Object msg) {
        mVideoView.stop();
    }

    @JavascriptInterface
    public void resume(Object msg) {
        mVideoView.start();
    }

    @JavascriptInterface
    public void seek(Object msg) {
        mVideoView.seekTo((Integer.parseInt(msg.toString())));
    }

    @JavascriptInterface
    public void fullScreen(Object msg) {
        mVideoView.fullScreen();
    }

    @JavascriptInterface
    public void exitFullScreen(Object msg) {
        mVideoView.exitFullScreen();
    }
}
