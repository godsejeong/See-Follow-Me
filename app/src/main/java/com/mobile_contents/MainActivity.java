package com.mobile_contents;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks{
    TextView mainText;
    Button mainbtn;
    Intent intent;
    SpeechRecognizer recognizer;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainText = (TextView) findViewById(R.id.mainText);
        mainbtn = (Button) findViewById(R.id.mainBtn);

        if(EasyPermissions.hasPermissions(this, android.Manifest.permission.RECORD_AUDIO)) {
            SoundOn();
        } else {
        EasyPermissions.requestPermissions(this,"음성인식을 하려면 권한이 필요합니다.",200,android.Manifest.permission.RECORD_AUDIO);
        }
    }

    public void SoundOn(){
        intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");
        Log.e("음성인식","onclick");
        recognizer = SpeechRecognizer.createSpeechRecognizer(context);
        recognizer.setRecognitionListener(listener);

        mainbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recognizer.startListening(intent);
            }
        });
    }

    //음성인식 리스너
    private RecognitionListener listener = new RecognitionListener() {
        //음성 인식 준비가 되었으면
        @Override
        public void onReadyForSpeech(Bundle bundle) {

        }
        //입력이 시작되면
        @Override
        public void onBeginningOfSpeech() {

        }
        //입력 소리 변경 시
        @Override
        public void onRmsChanged(float v) {

        }
        //더 많은 소리를 받을 때
        @Override
        public void onBufferReceived(byte[] bytes) {

        }
        //음성 입력이 끝났으면
        @Override
        public void onEndOfSpeech() {

        }
        //에러가 발생하면
        @Override
        public void onError(int i) {

        }
        //음성 인식 결과 받음
        @Override
        public void onResults(Bundle results) {
            Log.e("음성인식","성공");
            String key = SpeechRecognizer.RESULTS_RECOGNITION;
            ArrayList<String> result = results.getStringArrayList(key);
            String[] rs = new String[result.size()];
            result.toArray(rs);
            mainText.setText("" + rs[0]);

        }
        //인식 결과의 일부가 유효할 때
        @Override
        public void onPartialResults(Bundle bundle) {

        }
        //미래의 이벤트를 추가하기 위해 미리 예약되어진 함수
        @Override
        public void onEvent(int i, Bundle bundle) {

        }
    };

    //EsayPermission
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if(requestCode == 200){
            SoundOn();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Toast.makeText(this,"권한이 없습니다",Toast.LENGTH_SHORT).show();
    }
}
