package me.kemu.echo;

/**
 * 语音合成,将一段文字合成为语音,可以设置不同音色、语速和语调的声音。
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Synthesizer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synthesizer);
    }
}
