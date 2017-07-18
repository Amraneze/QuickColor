package fr.justgame.quickcolor.common;

import android.animation.AnimatorSet;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import fr.justgame.quickcolor.R;
import fr.justgame.quickcolor.common.ui.CommonTextView;
import fr.justgame.quickcolor.game.StartGameActivity;

/**
 * Created by Amrane Ait Zeouay on 18-Jul-17.
 */

public abstract class MainGameActivity extends Activity implements View.OnClickListener {

    protected CommonTextView pointsTextView, levelTextView;
    protected ProgressBar timerProgress;
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
    protected static final int START_TIMER = 200;
    protected static final int FPS = 100;
    protected static final int LEVEL = 25;

    protected Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        handler = new Handler();
    }

    protected void setupProgressView() {
        /*timerProgress = (ProgressBar) findViewById(R.id.progress_bar);
        pointsTextView = (TextView) findViewById(R.id.points_value);
        levelTextView = (TextView) findViewById(R.id.level_value);
        TextView pointsLabel = (TextView) findViewById(R.id.points_label);
        TextView levelsLabel = (TextView) findViewById(R.id.level_label);

        // setting up fonts
        Typeface avenir_black = Typeface.createFromAsset(getAssets(), "fonts/avenir_black.ttf");
        Typeface avenir_book = Typeface.createFromAsset(getAssets(), "fonts/avenir_book.ttf");
        pointsTextView.setTypeface(avenir_black);
        levelTextView.setTypeface(avenir_black);
        pointsLabel.setTypeface(avenir_book);
        levelsLabel.setTypeface(avenir_book);

        // setting up animations
        pointAnim = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.points_animations);
        pointAnim.setTarget(pointsTextView);
        levelAnim = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.level_animations);
        levelAnim.setTarget(levelTextView);*/
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
                            timerProgress.setProgress(timer);
                        }
                    });
                }
                if (gameStart) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            endGame();
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
        levelTextView.setText(Integer.toString(level));
        timerProgress.setProgress(0);
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

        if (points > level * LEVEL) {
            incrementLevel();
            levelTextView.setText(Integer.toString(level));
            levelAnim.start();
        }
    }

    // called when user goes to next level
    public void incrementLevel() {
        level += 1;
        TIMER_DELTA = level;
    }

    // ABSTRACT METHODS
    abstract protected void setColorsOnButtons();

    abstract protected void calculatePoints(View view);
}