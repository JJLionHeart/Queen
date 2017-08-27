package dramaqueens.queen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.api.translate.Language;

import java.util.ArrayList;

public class beeText_Main extends AppCompatActivity implements RecognitionListener, AdapterView.OnItemSelectedListener {
    private String LOG_TAG = "VoiceRecognition";
    private SpeechRecognizer speech = null;
    private Intent recognizerIntent;
    private TextView capturedText;
    private ToggleButton toggleButton;
    protected Chronometer chronos;
    Language origin_language = Language.SPANISH;
    Language target_language = Language.SPANISH;
    private String original_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bee_text__main);
        capturedText = (TextView) findViewById(R.id.transliteratedText);
        speech = SpeechRecognizer.createSpeechRecognizer(this);
        speech.setRecognitionListener(this);
        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es_MX");
        toggleButton = (ToggleButton) findViewById(R.id.micButton);
        chronos = (Chronometer) findViewById(R.id.timeRecorded);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    toggleButton.setBackgroundResource(R.drawable.rsz_micpress);
                    speech.startListening(recognizerIntent);
                    chronos.setBase(SystemClock.elapsedRealtime());
                    chronos.start();
                } else {
                    chronos.stop();
                    toggleButton.setBackgroundResource(R.drawable.rsz_mic);
                    speech.stopListening();
                }
            }
        });


        Spinner spinner2 = (Spinner) findViewById(R.id.target_language);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.language_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(this);

        Button translate = (Button) findViewById(R.id.translate_button);
        translate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(capturedText.getText()!=""){
                    String translated = GTranslate.translatedText(original_text, target_language, origin_language);
                    capturedText.setText(translated);
                }
            }
        });

    }
    private int[] getResourceId(String base_name, int number){
        int output[] = new int[number+1];
        for(int i = 0; i <= number; i++) {
            output[i] = getResources().getIdentifier(base_name+Integer.toString(i), "drawable", getPackageName());
        }
        return output;
    }
    @Override
    public void onReadyForSpeech(Bundle bundle) {
        Log.i(LOG_TAG, "onReadyForSpeech");
    }

    @Override
    public void onBeginningOfSpeech() {
        Log.i(LOG_TAG, "onReadyForSpeech");

    }

    @Override
    public void onRmsChanged(float v) {

    }

    @Override
    public void onBufferReceived(byte[] bytes) {
        Log.i(LOG_TAG, "onBufferReceived: " + bytes);
    }

    @Override
    public void onEndOfSpeech() {
        Log.i(LOG_TAG, "onEndOfSpeech");
        toggleButton.setChecked(false);
    }

    @Override
    public void onError(int errorCode) {
        String errorMessage = getErrorText(errorCode);
        Log.d(LOG_TAG, "FAILED " + errorMessage);
        capturedText.setText(errorMessage);
        chronos.stop();
        toggleButton.setBackgroundResource(R.drawable.rsz_mic);
    }

    @Override
    public void onResults(Bundle bundle) {
        Log.i(LOG_TAG, "onResults");
        ArrayList<String> matches = bundle
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);


        capturedText.setText(matches.get(0));
        original_text = matches.get(0);
    }

    @Override
    public void onPartialResults(Bundle bundle) {
        Log.i(LOG_TAG, "onPartialResults");
    }

    @Override
    public void onEvent(int i, Bundle bundle) {
        Log.i(LOG_TAG, "onEvent");
    }

    public static String getErrorText(int errorCode) {
        String message;
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Audio recording error";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "Client side error";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "Insufficient permissions";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Network error";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Network timeout";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "No match";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "RecognitionService busy";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "error from server";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "No speech input";
                break;
            default:
                message = "Didn't understand, please try again.";
                break;
        }
        return message;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i){
            case 0:
                target_language = Language.SPANISH;
                break;
            case 1:
                target_language = Language.ENGLISH;
                break;
            case 2:
                target_language = Language.FRENCH;
                break;
            case 3:
                target_language = Language.GERMAN;
                break;
            case 4:
                target_language = Language.FINNISH;
                break;

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
