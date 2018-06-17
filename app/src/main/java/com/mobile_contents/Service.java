package com.mobile_contents;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class Service extends android.app.Service {
    Intent intent;
    SpeechRecognizer recognizer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SoundOn();
        // 서비스에서 가장 먼저 호출됨(최초에 한번만)
        Log.d("test", "서비스의 onCreate");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 서비스가 호출될 때마다 실행
        Log.d("test", "서비스의 onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // 서비스가 종료될 때 실행
        Log.d("test", "서비스의 onDestroy");

    }

    public void SoundOn() {
        Log.e("sound", "On");
            intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");
            recognizer = SpeechRecognizer.createSpeechRecognizer(this);
            recognizer.setRecognitionListener(listener);
            }

a
        //음성인식 리스너
       private RecognitionListener listener = new RecognitionListener() {
            //음성 인식 준비가 되었으면
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            //입력이 시작되면
            @Override
            public void onBeginningOfSpeech() {
                Log.e("2", "2");
            }

            //입력 소리 변경 시
            @Override
            public void onRmsChanged(float v) {
                Log.e("3", "3");
            }

            //더 많은 소리를 받을 때
            @Override
            public void onBufferReceived(byte[] bytes) {
                Log.e("4", "4");
            }

            //음성 입력이 끝났으면
            @Override
            public void onEndOfSpeech() {
                Log.e("5", "5");
            }

            //에러가 발생하면
            @Override
            public void onError(int i) {
                Log.e("6", "6");
            }

            //음성 인식 결과 받음
            @Override
            public void onResults(Bundle results) {
                Log.e("음성인식", "성공");
                String key = SpeechRecognizer.RESULTS_RECOGNITION;
                ArrayList<String> result = results.getStringArrayList(key);
                String[] rs = new String[result.size()];

                if (result.toArray(rs).equals("안녕") || result.toArray(rs).equals("헬로")) {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.MAIN");
                    intent.addCategory("android.intent.category.HOME");
                    intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS | Intent.FLAG_ACTIVITY_FORWARD_RESULT | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                    startActivity(intent);
                }
            }

            //인식 결과의 일부가 유효할 때
            @Override
            public void onPartialResults(Bundle bundle) {
                Log.e("7", "7");
            }

            //미래의 이벤트를 추가하기 위해 미리 예약되어진 함수
            @Override
            public void onEvent(int i, Bundle bundle) {
                Log.e("8", "8");
            }
        };
    }
}

