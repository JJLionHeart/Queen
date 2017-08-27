package dramaqueens.queen;
import android.app.Activity;
import android.widget.ImageView;
import android.widget.ToggleButton;

/**
 * Created by jj_lo on 26/08/2017.
 */

public class SimpleAnimation implements Runnable {
    private int[] frames;
    private int sleep_time;
    private boolean active = true;
    private ToggleButton reference;
    private int index = 0;
    private int frame_number;
    private Activity act;
    public SimpleAnimation(int sleep_time, int[] frames, ToggleButton reference, int frame_number,
                     Activity act){
        this.frames = frames;
        this.sleep_time = sleep_time;
        this.reference = reference;
        this.frame_number = frame_number;
        this.act = act;

    }


    public void kill(){
        active = false;
    }
    @Override
    public void run() {
        while(active) {
            act.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    reference.setBackgroundResource(frames[index]);
                }


            });
            index++;
            if(index % frame_number == 0){
                index = 0;
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
