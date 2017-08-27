package dramaqueens.queen;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    protected TextView transliterated_speech;
    protected Button microphone;
    protected final int REQ_SPEECH = 100;
    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        microphone = (Button) findViewById(R.id.Microphone);
        transliterated_speech = (TextView) findViewById(R.id.SpeechOut);
        microphone.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                askSpeech();
            }
        });
    }

    private void askSpeech() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es_MX");
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Bzzzzzzz");
        try {
            startActivityForResult(intent, REQ_SPEECH);
        } catch(ActivityNotFoundException a) {
            
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case REQ_SPEECH:{
                if(resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    transliterated_speech.setText(result.get(0));
                }
            }

        }
    }

}
