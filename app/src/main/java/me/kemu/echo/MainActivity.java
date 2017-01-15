package me.kemu.echo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

import me.kemu.echo.Config.Config;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    String appId = Config.APP_ID;
    Context context = MainActivity.this;

    Button openRecognizer = null;
    Button openSynthesizer = null;
    Button openUnderstander = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initSpeech();

        openRecognizer = (Button) findViewById(R.id.open_recognizer);
        openSynthesizer = (Button) findViewById(R.id.open_synthesizer);
        openUnderstander = (Button) findViewById(R.id.open_understander);

        openRecognizer.setOnClickListener(this);
        openSynthesizer.setOnClickListener(this);
        openUnderstander.setOnClickListener(this);
    }

    private void initSpeech() {
        StringBuffer param = new StringBuffer();
        param.append(SpeechConstant.APPID + "=" + appId);
        param.append(',');
        param.append(SpeechConstant.ENGINE_MODE + "=" + SpeechConstant.MODE_MSC);

        SpeechUtility.createUtility(context, param.toString());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.open_recognizer:
                Intent intent1 = new Intent(context, Recognizer.class);
                startActivity(intent1);
                break;
            case R.id.open_synthesizer:
                Intent intent2 = new Intent(context, Synthesizer.class);
                startActivity(intent2);
                break;
            case R.id.open_understander:
                Intent intent3 = new Intent(context, Understander.class);
                startActivity(intent3);
                break;
        }
    }
}
