package it.unimore.brosds.speechrecognizer;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.transition.Explode;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    public final static String TITLE="it.unimore.brosds.speechrecognizer.TITLE";
    public final static String DAY="it.unimore.brosds.speechrecognizer.DAY";
    public final static String MONTH="it.unimore.brosds.speechrecognizer.MONTH";
    public final static String START_TIME="it.unimore.brosds.speechrecognizer.START_TIME";

    private TextView txtSpeechInput;
    private ImageButton btnSpeak;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtSpeechInput = (TextView) findViewById(R.id.txtSpeechInput);
        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);
        btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });
        txtSpeechInput.setText("evento barbiere il 2 marzo dalle 10");

    }

    /**
     * Showing google speech input dialog
     * */
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String[] all_words = result.get(0).toLowerCase().split(" ");
                    String title=null;
                    String day=null;
                    String month=null;
                    String start_time=null;

                    if(all_words[0].equals("evento"))
                    {
                        title = all_words[1];
                    }

                    if(all_words[2].equals("il"))
                    {
                        day = all_words[3];
                        month = all_words[4];
                    }

                    if(all_words[5].equals("dalle"))
                    {
                       start_time = all_words[6];
                    }


                    Intent intent = new Intent(this, ShowEventRecap.class);
                    intent.putExtra(TITLE, title);
                    intent.putExtra(DAY, day);
                    intent.putExtra(MONTH, month);
                    intent.putExtra(START_TIME, start_time);
                    getWindow().setExitTransition(new Explode());

                    startActivity(intent,
                            ActivityOptions
                                    .makeSceneTransitionAnimation(this).toBundle());
                }
                break;
            }

        }
    }
}