package me.kemu.echo;

/**
 * 将语音转换为文字内容,能识别常见词汇、语句、语气并自动断句。
 */

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.ErrorCode;

public class Recognizer extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener, View.OnLongClickListener{
    SpeechRecognizer mIat = null;
    Context context = Recognizer.this;
    Button beginTalk = null;
    RecognizerListener mRecoListener = null;
    final String TAG = "RecognizerTAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recognizer);

        beginTalk = (Button) findViewById(R.id.begin_talk);

        initSpeechRecognizer();
        initRecoListener();

        beginTalk.setOnLongClickListener(this);
//        beginTalk.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
//        Toast.makeText(context, "on touch", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onLongClick(View v) {
        int ret;
        switch (v.getId()) {
            case R.id.begin_talk:
                ret = mIat.startListening(mRecoListener);
                if (ret == ErrorCode.SUCCESS) {
                    Toast.makeText(context, "识别成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "识别失败，错误码 " + ret , Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(context, "begin", Toast.LENGTH_LONG).show();
                break;
        }
        return false;
    }

    private void initSpeechRecognizer() {
        mIat = SpeechRecognizer.createRecognizer(context, null);

        mIat.setParameter(SpeechConstant.DOMAIN, "iat");
        mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        mIat.setParameter(SpeechConstant.ACCENT, "mandarin ");
    }

    private void initRecoListener() {
        mRecoListener = new RecognizerListener() {
            @Override
            public void onVolumeChanged(int volume, byte[] data) {

            }

            /**
             * 开始录音
             */
            @Override
            public void onBeginOfSpeech() {
                Log.d(TAG, "onBeginOfSpeech");
            }

            /**
             * 结束录音
             */
            @Override
            public void onEndOfSpeech() {
                Log.d(TAG, "onEndOfSpeech");
            }

            @Override
            public void onResult(RecognizerResult recognizerResult, boolean b) {

            }

            @Override
            public void onError(SpeechError speechError) {

            }

            @Override
            public void onEvent(int i, int i1, int i2, Bundle bundle) {

            }
        };
    }
}
