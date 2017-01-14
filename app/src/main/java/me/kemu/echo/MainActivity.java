package me.kemu.echo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

import me.kemu.echo.Config.Config;

public class MainActivity extends AppCompatActivity {
    String appId = Config.APP_ID;
    Context context = MainActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initSpeech();
    }

    private void initSpeech() {
        SpeechUtility.createUtility(context, SpeechConstant.APPID + appId);
    }
}
