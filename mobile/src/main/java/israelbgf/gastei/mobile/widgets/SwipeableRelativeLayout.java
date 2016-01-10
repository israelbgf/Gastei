package israelbgf.gastei.mobile.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * @author Gal Rom (Original code) http://stackoverflow.com/questions/6645537/how-to-detect-the-swipe-left-or-right-in-android
 * @author Israel Fonseca (Tweaks)
 */
public class SwipeableRelativeLayout extends RelativeLayout {


    static final int MIN_DISTANCE = 50;

    private float x1, x2;
    private SwipeListener swipeListener;


    public SwipeableRelativeLayout(Context context) {
        super(context);
    }
    public SwipeableRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public SwipeableRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) { super(context, attrs, defStyleAttr); }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {

        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = motionEvent.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = motionEvent.getX();
                float deltaX = x2 - x1;
                if (Math.abs(deltaX) > MIN_DISTANCE) {
                    if (deltaX < 0) {
                        if (swipeListener != null)
                            swipeListener.onSwipeRightToLeft();
                    }
                    if (deltaX > 0) {
                        if (swipeListener != null)
                            swipeListener.onSwipeLeftToRight();
                    }
                }
                break;
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    public interface SwipeListener {
        void onSwipeRightToLeft();
        void onSwipeLeftToRight();
    }

    public void setOnSwipeListener(SwipeListener listener) {
        this.swipeListener = listener;
    }
}
