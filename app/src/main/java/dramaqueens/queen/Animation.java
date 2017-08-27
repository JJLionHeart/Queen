package dramaqueens.queen;

import android.app.Activity;
import android.widget.ImageView;

/**
 * Created by jj_lo on 26/08/2017.
 */

public class Animation implements Runnable {
    private int[] frames;
    private int sleep_time;
    private boolean active = true;
    private ImageView reference;
    private int index = 0;
    private int frame_number;
    private Activity act;
    private Runnable Callback;
    private int num_max;
    public Animation(int sleep_time, int[] frames, ImageView reference, int frame_number,
                     Activity act, Runnable Callback, int num_max){
        this.frames = frames;
        this.sleep_time = sleep_time;
        this.reference = reference;
        this.frame_number = frame_number;
        this.act = act;
        this.Callback = Callback;
        this.num_max = num_max;
    }



    @Override
    public void run() {
        while(active) {
            act.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    reference.setImageResource(frames[index]);
                }


            });
            index++;
            if(index % frame_number == 0){
                index = 0;
                num_max--;
                if(num_max == 0){
                    act.runOnUiThread(Callback);
                }
            }
            index %= frame_number;
            try {
                Thread.sleep(sleep_time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }
}
