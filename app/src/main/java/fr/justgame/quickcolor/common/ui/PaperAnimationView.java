package fr.justgame.quickcolor.common.ui;

import android.animation.TimeAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Random;

import fr.justgame.quickcolor.R;

/**
 * Created by Amrane Ait Zeouay on 12-May-18.
 */

public class PaperAnimationView extends View {

    /**
     * Class representing the state of a star
     */
    private static class Paper {
        private float x;
        private float y;
        private float scale;
        private float alpha;
        private float speed;
    }

    private static final int BASE_SPEED_DP_PER_S = 10;
    private static final int COUNT = 6;
    private static final int SEED = 1337;

    /** The minimum scale of a star */
    private static final float SCALE_MIN_PART = 0.45f;
    /** How much of the scale that's based on randomness */
    private static final float SCALE_RANDOM_PART = 0.55f;
    /** How much of the alpha that's based on the scale of the star */
    private static final float ALPHA_SCALE_PART = 0.5f;
    /** How much of the alpha that's based on randomness */
    private static final float ALPHA_RANDOM_PART = 0.5f;

    private final Paper[] mPapers = new Paper[COUNT];
    private final Random mRnd = new Random(SEED);

    private TimeAnimator mTimeAnimator;
    private Drawable[] mDrawable = new Drawable[COUNT];

    private float mBaseSpeed;
    private float mBaseSize;
    private long mCurrentPlayTime;

    /** @see View#View(Context) */
    public PaperAnimationView(Context context) {
        super(context);
        init();
    }

    /** @see View#View(Context, AttributeSet) */
    public PaperAnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /** @see View#View(Context, AttributeSet, int) */
    public PaperAnimationView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mDrawable[0] = ContextCompat.getDrawable(getContext(), R.drawable.ic_paper_blue);
        mDrawable[1] = ContextCompat.getDrawable(getContext(), R.drawable.ic_paper_green);
        mDrawable[2] = ContextCompat.getDrawable(getContext(), R.drawable.ic_paper_orange);
        mDrawable[3] = ContextCompat.getDrawable(getContext(), R.drawable.ic_paper_purple);
        mDrawable[4] = ContextCompat.getDrawable(getContext(), R.drawable.ic_paper_red);
        mDrawable[5] = ContextCompat.getDrawable(getContext(), R.drawable.ic_paper_yellow);
        mBaseSize = Math.max(mDrawable[0].getIntrinsicWidth(), mDrawable[0].getIntrinsicHeight()) / 2f;
        mBaseSpeed = BASE_SPEED_DP_PER_S * getResources().getDisplayMetrics().density;
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldw, int oldh) {
        super.onSizeChanged(width, height, oldw, oldh);

        // The starting position is dependent on the size of the view,
        // which is why the model is initialized here, when the view is measured.
        for (int i = 0; i < mPapers.length; i++) {
            final Paper star = new Paper();
            initializeStar(star, i, width, height);
            mPapers[i] = star;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        final int viewHeight = getHeight();
        final int viewWidth = getWidth();
        int index = 0;
        for (final Paper paper : mPapers) {
            // Ignore the star if it's outside of the view bounds
            final float starSize = paper.scale * mBaseSize;
            if (paper.x + starSize < 0 || paper.x - starSize > viewWidth) {
                continue;
            }

            // Save the current canvas state
            final int save = canvas.save();

            // Move the canvas to the center of the star
            canvas.translate(paper.x, paper.y);

            // Rotate the canvas based on how far the star has moved
            //final float progress = (paper.y + starSize) / viewHeight;
            //canvas.rotate(360 * progress);

            // Prepare the size and alpha of the drawable
            final int size = Math.round(starSize);
            mDrawable[index].setBounds(-size, -size, size, size);
            mDrawable[index].setAlpha(Math.round(255 * paper.alpha));

            // Draw the star to the canvas
            mDrawable[index].draw(canvas);

            // Restore the canvas to it's previous position and rotation
            canvas.restoreToCount(save);
            index++;
        }
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mTimeAnimator = new TimeAnimator();
        mTimeAnimator.setTimeListener(new TimeAnimator.TimeListener() {
            @Override
            public void onTimeUpdate(TimeAnimator animation, long totalTime, long deltaTime) {
                if (!isLaidOut()) {
                    // Ignore all calls before the view has been measured and laid out.
                    return;
                }
                updateState(deltaTime);
                invalidate();
            }
        });
        mTimeAnimator.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mTimeAnimator.cancel();
        mTimeAnimator.setTimeListener(null);
        mTimeAnimator.removeAllListeners();
        mTimeAnimator = null;
    }

    /**
     * Pause the animation if it's running
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void pause() {
        if (mTimeAnimator != null && mTimeAnimator.isRunning()) {
            // Store the current play time for later.
            mCurrentPlayTime = mTimeAnimator.getCurrentPlayTime();
            mTimeAnimator.pause();
        }
    }

    /**
     * Resume the animation if not already running
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void resume() {
        if (mTimeAnimator != null && mTimeAnimator.isPaused()) {
            mTimeAnimator.start();
            // Why set the current play time?
            // TimeAnimator uses timestamps internally to determine the delta given
            // in the TimeListener. When resumed, the next delta received will the whole
            // pause duration, which might cause a huge jank in the animation.
            // By setting the current play time, it will pick of where it left off.
            mTimeAnimator.setCurrentPlayTime(mCurrentPlayTime);
        }
    }

    /**
     * Progress the animation by moving the stars based on the elapsed time
     * @param deltaMs time delta since the last frame, in millis
     */
    private void updateState(float deltaMs) {
        // Converting to seconds since PX/S constants are easier to understand
        final float deltaSeconds = deltaMs / 10000f;
        final int viewWidth = getWidth();
        final int viewHeight = getHeight();
        int index = 0;

        for (final Paper paper : mPapers) {
            // Move the star based on the elapsed time and it's speed
            //paper.x -= paper.speed * deltaSeconds;
            paper.x -= paper.speed * deltaSeconds;

            // If the star is completely outside of the view bounds after
            // updating it's position, recycle it.
            final float size = paper.scale * mBaseSize;
            if (paper.x + size < 0) {
                initializeStar(paper, index, viewWidth, viewHeight);
                index++;
            }
        }
    }

    /**
     * Initialize the given star by randomizing it's position, scale and alpha
     * @param paper the star to initialize
     * @param viewWidth the view width
     * @param viewHeight the view height
     */
    private void initializeStar(Paper paper, int index, int viewWidth, int viewHeight) {
        // Set the scale based on a min value and a random multiplier
        paper.scale = SCALE_MIN_PART + SCALE_RANDOM_PART * mRnd.nextFloat();

        // Set X to a random value within the width of the view
        float rnd = mRnd.nextFloat();
        paper.x = viewWidth * rnd / (index + 1);
        Log.e("Amrane", "Mrnd "+rnd);

        // Set the Y position
        // Start at the bottom of the view
        paper.y = viewHeight / 2;
        // The Y value is in the center of the star, add the size
        // to make sure it starts outside of the view bound
        paper.x += paper.scale * mBaseSize;
        // Add a random offset to create a small delay before the
        // star appears again.
        paper.x += viewWidth * mRnd.nextFloat() / 4f;

        // The alpha is determined by the scale of the star and a random multiplier.
        paper.alpha = ALPHA_SCALE_PART * paper.scale + ALPHA_RANDOM_PART * mRnd.nextFloat();
        // The bigger and brighter a star is, the faster it moves
        paper.speed = mBaseSpeed * paper.alpha * paper.scale;
    }
}
