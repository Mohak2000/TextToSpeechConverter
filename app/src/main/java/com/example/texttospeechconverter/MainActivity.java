package com.example.texttospeechconverter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements  TextToSpeech.OnInitListener {

    private static final int TTS_ENGINE_REQUEST = 101;
    private TextToSpeech textToSpeech;
    private EditText textforspeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textforspeech=findViewById(R.id.search_text);
    }

    public void toSpeech(View view) {

        Intent checkIntent = new Intent();
        checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkIntent, TTS_ENGINE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TTS_ENGINE_REQUEST && resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
            textToSpeech = new TextToSpeech(this, this);
        } else {
            Intent installIntent = new Intent();
            installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
            startActivity(installIntent);
        }
    }


    @Override
    public void onInit(int status) {

        if(status==TextToSpeech.SUCCESS)
        {


            String data=textforspeech.getText().toString();
            int speakstatue=textToSpeech.speak(data,TextToSpeech.QUEUE_FLUSH,null);

            if(speakstatue==TextToSpeech.ERROR)
            {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(this, "Error !!", Toast.LENGTH_SHORT).show();
        }

    }
}
