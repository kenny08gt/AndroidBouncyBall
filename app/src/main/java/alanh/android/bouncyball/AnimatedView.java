package alanh.android.bouncyball;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.Toast;

public class AnimatedView extends ImageView {
    private Context mContext;
    int x = -1;
    int y = -1;
    int yB = -1;
    boolean state=true;
    private int xVelocity = 10;
    private int yVelocity = 5;
    private Handler h;
    private final int FRAME_RATE = 30;

    public AnimatedView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        h = new Handler();
    }

    private Runnable r = new Runnable() {
        @Override
        public void run() {
            invalidate();
        }
    };


    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.

        yVelocity = yVelocity - 30;
        System.out.println("Touch event");
        return true;
    }

    protected void onDraw(Canvas c) {

            BitmapDrawable ball = (BitmapDrawable) ResourcesCompat.getDrawable(getResources(), R.drawable.ball, null);
            BitmapDrawable building = (BitmapDrawable) ResourcesCompat.getDrawable(getResources(), R.drawable.building, null);
            //System.out.println("on draw");
        if (state) {
            if (yB + building.getBitmap().getWidth() < 0) {
                yB = this.getWidth();
            } else {
                yB -= 10;
            }
            if (y < 0) {
                //x = this.getWidth() / 2;
                y = 0;
                yVelocity = 0;
            } else {
                //x += xVelocity;
                y += yVelocity;
                //MOVIEMIENTO EN X
               /* if ((x > this.getWidth() - ball.getBitmap().getWidth()) || (x < 0)) {
                    xVelocity = xVelocity * -1;
                }*/
                if (y >= this.getHeight() - ball.getBitmap().getHeight() + 1)
                    yVelocity = 0;
                else
                    yVelocity += 2;
                /*if ((y > this.getHeight() - ball.getBitmap().getHeight()) || (y < 0)) {
                    yVelocity = yVelocity * -1;
                }*/
            }

            c.drawBitmap(ball.getBitmap(), (this.getWidth() - ball.getBitmap().getHeight()) / 2, y, null);
            c.drawBitmap(building.getBitmap(), yB, this.getHeight() - building.getBitmap().getHeight(), null);
            checkCollision((this.getWidth() - ball.getBitmap().getHeight()) / 2, y, ball, yB, this.getHeight() - building.getBitmap().getHeight(), building, c);
            h.postDelayed(r, FRAME_RATE);
        }else{
            c.drawBitmap(ball.getBitmap(), (this.getWidth() - ball.getBitmap().getHeight()) / 2, y, null);
            c.drawBitmap(building.getBitmap(), yB, this.getHeight() - building.getBitmap().getHeight(), null);
        }

    }

    private void checkCollision(int x1,int y1,BitmapDrawable ball, int x2,int y2, BitmapDrawable building,Canvas c) {
        int anchoBall= x1 + ball.getBitmap().getWidth();
        int altoBall=y1+ball.getBitmap().getHeight();

        int anchoBuilding=x2+building.getBitmap().getWidth();
        int altoBuilding=y2+building.getBitmap().getHeight();

        if(anchoBall>=x2 && altoBall>=y2 && x1<=anchoBuilding ){
            System.out.println("colision!");
            Toast.makeText(this.getContext(),"Colision", Toast.LENGTH_SHORT).show();
            state=false;
        }
    }
}