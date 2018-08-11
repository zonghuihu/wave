package com.example.administrator.wave;

import java.util.Timer;
import java.util.TimerTask;
import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import com.github.mikephil.charting.charts.LineChart;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.SurfaceHolder.Callback;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ScrollView;

import static com.example.administrator.wave.R.id.scrollView;
import static com.example.administrator.wave.R.id.show;

public class MainActivity extends Activity{

    private SurfaceHolder holder;
    private Paint paint;
    final int HEIGHT=10000;
    final int WIDTH=10000;
    final int X_OFFSET =5;
    private int cx = 80;
    //实际的Y轴的位置
    int centerY = HEIGHT /2;
    Timer timer = new Timer();
    TimerTask task = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        //setContentView(R.layout.activity_main);
        final SurfaceView surface = (SurfaceView)findViewById(show);
        //初始化SurfaceHolder对象
        holder = surface.getHolder();
        paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(100);
//onCreate()中findView：
       final  ScrollView mScrollView_showMessages=(ScrollView)findViewById(R.id.scrollView1);

        //scrollView scroll = (scrollView)findViewById(R.id.)
        Button sin =(Button)findViewById(R.id.sin);
        Button cos =(Button)findViewById(R.id.cos);

        OnClickListener listener = (new OnClickListener() {

            @Override
            public void onClick(final View source) {
                // TODO Auto-generated method stub
                drawBack(holder);
                cx = X_OFFSET;
                if(task != null){
                    task.cancel();
                }

                task = new TimerTask() {

                    @Override
                    public void run() {
                  //      int cy = source.getId() == R.id.sin ? centerY -(int)(100 * Math.sin((cx -5) *2 * Math.PI/150)):
                               // centerY -(int)(100 * Math.cos((cx-5)*2*Math.PI/150));
                       while (true) {
                        int cy = cx ;
                       Canvas canvas = holder.lockCanvas(new Rect(0,0,10000,10000));//取得一块区域对应的Canvas控制权

                           canvas.drawPoint(cx, cy, paint);
                          cx++;
//在需要的地方，让ScrollView滚动至指定位置
                           mScrollView_showMessages.scrollTo(0,surface.getBottom());

                        if(cx >WIDTH){

                           task.cancel();
                           task = null;

                       }
                        holder.unlockCanvasAndPost(canvas);
                    } }
                };
                timer.schedule(task, 0,30);

            }
        });

        sin.setOnClickListener(listener);
        cos.setOnClickListener(listener);
mScrollView_showMessages.setOnClickListener(listener);
        holder.addCallback(new Callback() {
            public void surfaceChanged(SurfaceHolder holder,int format,int width,int height){
                drawBack(holder);
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                // TODO Auto-generated method stub
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                // TODO Auto-generated method stub
                timer.cancel();
            }

        });


    }


    private void drawBack(SurfaceHolder holder){
        Canvas canvas = holder.lockCanvas();
        //绘制白色背景
        canvas.drawColor(Color.BLUE);
        Paint p = new Paint();
        p.setColor(Color.BLACK);
        p.setStrokeWidth(2);

        //绘制坐标轴
        canvas.drawLine(X_OFFSET, 10000, WIDTH, 10000, p);//画线，参数一起始点的x轴位置，参数二起始点的y轴位置，参数三终点的x轴水平位置，参数四y轴垂直位置，最后一个参数为Paint 画刷对象。
        canvas.drawLine(X_OFFSET, 40, X_OFFSET, HEIGHT, p);
        holder.unlockCanvasAndPost(canvas);
        holder.lockCanvas(new Rect(0,0,0,0));
        holder.unlockCanvasAndPost(canvas);

    }


}