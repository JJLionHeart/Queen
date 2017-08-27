package dramaqueens.queen;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {


    protected ImageView animation_view;
    protected Animation animation;
    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        animation_view = (ImageView) findViewById(R.id.LoadingImageView);
        int[] frames = getResourceId("beea", 45);
        final Intent intent = new Intent(this, beeText_Main.class);
        animation = new Animation(50,frames, animation_view, 46, this, new Runnable(){
            @Override
                    public void run() {
                    startActivity(intent);
            }
        }, 2);
        (new Thread(animation)).start();
    }

    private int[] getResourceId(String base_name, int number){
        int output[] = new int[number+1];
        for(int i = 0; i <= number; i++) {
            output[i] = getResources().getIdentifier(base_name+Integer.toString(i), "drawable", getPackageName());
        }
        return output;
    }


}
