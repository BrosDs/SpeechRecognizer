package it.unimore.brosds.speechrecognizer;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.EditText;


public class ShowEventRecap extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_event_recap);

        Intent intent = getIntent();
        String title = intent.getStringExtra(MainActivity.TITLE);
        String day = intent.getStringExtra(MainActivity.DAY);
        String month = intent.getStringExtra(MainActivity.MONTH);
        String start_time = intent.getStringExtra(MainActivity.START_TIME);

        EditText titolo = (EditText) findViewById(R.id.titolo_insert);
        titolo.setText(title);

        EditText giorno = (EditText) findViewById(R.id.giorno_insert);
        giorno.setText(day);

        EditText mese = (EditText) findViewById(R.id.mese_insert);
        mese.setText(month);

        EditText ora_inizio = (EditText) findViewById(R.id.ora_insert);
        ora_inizio.setText(start_time);

    }


}
