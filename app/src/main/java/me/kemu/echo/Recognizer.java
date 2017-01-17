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
import android.widget.TextView;
import android.widget.Toast;

import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.ErrorCode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Recognizer extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener, View.OnLongClickListener{
    SpeechRecognizer mIat = null;
    Context context = Recognizer.this;
    Button beginTalk = null;
    TextView textView = null;
    List<RecognizerResult> results = new ArrayList<RecognizerResult>();
    RecognizerListener mRecoListener = null;

    final String TAG = "RecognizerTAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recognizer);

        beginTalk = (Button) findViewById(R.id.begin_talk);
        textView = (TextView) findViewById(R.id.text_view);

        initSpeechRecognizer();
        initRecoListener();

        beginTalk.setOnLongClickListener(this);
//        beginTalk.setOnTouchListener(this);
    }

    private void appendText() {
        String text = "";

        for (RecognizerResult result : results) {
            String textStr = result.getResultString();
//            Log.d(TAG, "appendText: " + result.getResultString());
            try {
                JSONObject resultObj = new JSONObject(textStr);
                String ws = resultObj.getString("ws");
                JSONArray sentences = new JSONArray(ws);

                for (int i = 0; i < sentences.length(); i++) {
                    String cwsStr = sentences.getString(i);
                    JSONArray cws = new JSONArray(cwsStr);
                    for (int j = 0; j < cws.length(); j++) {
                        String word = cws.getString(j);
                        Log.d(TAG, "word >>> " + word);
                    }
                }
//                JSONObject sentenceObj = new JSONObject(sentence);
//                JSONArray words = sentenceObj.getJSONArray("cw");
//
//                for (int i = 0; i < words.length(); i++) {
//                    JSONObject obj = words.getJSONObject(i);
//                    String word = obj.getString("w");
//                    text += word;
//                }

            } catch (JSONException e) {
                Log.e(TAG, "Unexpected JSON Exception", e);
            }
        }
        textView.append("text");
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
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
            public void onResult(RecognizerResult result, boolean b) {
//                Log.d(TAG, "onResult: " + result.getResultString());
                results.add(result);
                if (b) {
                    appendText();
                    results.clear();
                }
            }

            /**
             * 音量改变时的回调
             * @param volume - 音量值,范围 0~30
             * @param data
             */
            @Override
            public void onVolumeChanged(int volume, byte[] data) {
//                Log.d(TAG, "current volume is " + volume);
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
