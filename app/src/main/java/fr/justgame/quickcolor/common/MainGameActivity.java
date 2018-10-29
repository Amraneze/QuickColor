package fr.justgame.quickcolor.common;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import butterknife.BindView;
import fr.justgame.quickcolor.R;
import fr.justgame.quickcolor.common.ui.CommonTextView;
import fr.justgame.quickcolor.game.StartGameActivity;

/**
 * Created by Amrane Ait Zeouay on 18-Jul-17.
 */

public abstract class MainGameActivity extends Activity implements View.OnClickListener {

    @BindView(R.id.progress_bar)
    ProgressBar timerProgress;
    @BindView(R.id.tv_score_point)
    CommonTextView pointsTextView;

    //protected CommonTextView pointsTextView, levelTextView;
    //protected ProgressBar timerProgress;
    protected AnimatorSet pointAnim, levelAnim;

    protected int level, points;
    protected boolean gameStart = false;
    protected Runnable runnable;
    protected int timer;

    public enum GameMode { EASY, HARD }
    protected GameMode gameMode;

    public enum GameType { RECTANGLE, CIRCLE }
    protected GameType gameType;

    protected int POINT_INCREMENT;
    protected int TIMER_BUMP;
    protected static int TIMER_DELTA = -1;
    protected static final int START_TIMER = 800;
    protected static final int FPS = 100;
    protected static final int LEVEL = 25;

    protected Handler handler;
    protected Handler uiHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        handler = new Handler();
        uiHandler = new Handler(getMainLooper());
    }

    protected void setupProgressView() {
        // setting up animations
        pointAnim = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.points_animations);
        pointAnim.setTarget(pointsTextView);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        endGame();
    }

    @Override
    protected void onStop() {
        super.onStop();
        gameStart = false;
    }

    protected void setupGameLoop() {
        runnable = new Runnable() {
            @Override
            public void run() {
                while (timer > 0 && gameStart) {
                    synchronized (this) {
                        try {
                            wait(FPS);
                        } catch (InterruptedException e) {
                            Log.i("THREAD ERROR", e.getMessage());
                        }
                        timer = timer + TIMER_DELTA;
                        if (TIMER_DELTA > 0) {
                            TIMER_DELTA = -TIMER_DELTA / TIMER_BUMP;
                        }
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Log.e("Run", "timer "+timer + " TimerDelta "+TIMER_DELTA);
                            LayerDrawable progressBarDrawable = (LayerDrawable) timerProgress.getProgressDrawable();
                            if (timer > 400) {
                                Drawable progressDrawable = progressBarDrawable.getDrawable(1);
                                progressDrawable.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.common_green), PorterDuff.Mode.SRC_IN);
                            } else if (timer < 200) {
                                Drawable progressDrawable = progressBarDrawable.getDrawable(1);
                                progressDrawable.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.splash_screen_red), PorterDuff.Mode.SRC_IN);
                            } else {
                                Drawable progressDrawable = progressBarDrawable.getDrawable(1);
                                progressDrawable.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.common_orange), PorterDuff.Mode.SRC_IN);
                            }
                            timerProgress.setProgress(timer);
                        }
                    });
                }
                if (gameStart) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            onTimeIsUp();
                        }
                    });
                }
            }
        };

    }

    protected void resetGame() {
        gameStart = false;
        level = 1;
        points = 0;

        // update view
        pointsTextView.setText(Integer.toString(points));
        timerProgress.setProgress(800);
    }

    protected void startGame() {
        gameStart = true;
        setColorsOnButtons();
        // start timer
        timer = START_TIMER;
        Thread thread = new Thread(runnable);
        thread.start();
    }

    @Override
    public void startIntentSenderForResult(IntentSender intent, int requestCode, Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags) throws IntentSender.SendIntentException {
        super.startIntentSenderForResult(intent, requestCode, fillInIntent, flagsMask, flagsValues, extraFlags);
    }

    protected void endGame() {
        gameStart = false;
        int highScore = saveAndGetHighScore();
        launchGameOver(highScore);
        finish();
    }

    private int saveAndGetHighScore() {
        SharedPreferences preferences = this.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        int highScore = preferences.getInt("HIGHSCORE", 0);

        if (points > highScore) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("HIGHSCORE", points);
            editor.apply();
            highScore = points;
        }
        return highScore;
    }

    private void launchGameOver(int highScore) {
        // Send data to another activity
        Intent intent = new Intent(this, StartGameActivity.class);
        intent.putExtra("points", points);
        intent.putExtra("level", level);
        intent.putExtra("best", highScore);
        intent.putExtra("newScore", highScore == points);
        intent.putExtra("gameMode", gameMode.name());
        intent.putExtra("gameType", gameType.name());
        startActivity(intent);
    }

    // called on correct guess
    public void updatePoints() {
        points = points + POINT_INCREMENT;
        TIMER_DELTA = -TIMER_BUMP * TIMER_DELTA; // give a timer bump
        pointsTextView.setText(Integer.toString(points));
        pointAnim.start();
        Log.e("updatePoints", "POints "+points + " TIMER_DELTA "+TIMER_DELTA);
        if (points > 50) {
            TIMER_BUMP = 9;
        } else if (points > 100) {
            TIMER_BUMP = 8;
        } else if (points > 200) {
            TIMER_BUMP = 7;
        } else if (points > 300) {
            TIMER_BUMP = 6;
        } else if (points > 400) {
            TIMER_BUMP = 5;
        } else if (points > 500) {
            TIMER_BUMP = 4;
        } else if (points > 600) {
            TIMER_BUMP = 3;
        } else if (points > 700) {
            TIMER_BUMP = 2;
        }
        incrementLevel();
        if(points > 500){
            incrementLevel();
        }
    }

    // called when user goes to next level
    public void incrementLevel() {
        Log.e("incrementLevel", "level "+level +" timer "+TIMER_DELTA);
        level += 1;
        TIMER_DELTA = level;
    }

    //TODO add this
    /*
        if(getApiClient().isConnected())
               Games.Achievements.unlock(getApiClient(),
               getString(R.string.correct_guess_achievement));
      */

    // ABSTRACT METHODS
    protected abstract void setColorsOnButtons();
    protected abstract void calculatePoints(View view);
    protected abstract void onTimeIsUp();
}