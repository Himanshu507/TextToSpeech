package com.prag.texttospeech;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private TextToSpeech mTTS;
    private EditText mEditText;
    private SeekBar mSeekBarPitch;
    private SeekBar mSeekBarSpeed;
    private Button mButtonSpeak;
    String[] permissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    boolean engvoicebool = false, hindivoicebool = true;
    boolean malevoicebool = false, femalevoicebool = true;

    boolean check= false;
    String Filename = "";

    TextView englang, hindilang, femalevoice, malevoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        check = checkPermissions();

        mButtonSpeak = findViewById(R.id.button_speak);
        englang = findViewById(R.id.englishlang);
        hindilang = findViewById(R.id.hindilang);
        femalevoice = findViewById(R.id.femalevoice);
        malevoice = findViewById(R.id.malevoice);


        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = mTTS.setLanguage(new Locale("hin"));
                    //int result = mTTS.setLanguage(Locale.ENGLISH);

                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported");
                    } else {
                        mButtonSpeak.setEnabled(true);
                    }
                } else {
                    Log.e("TTS", "Initialization failed");
                }
            }
        });

        englang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                engvoicebool = true;
                hindivoicebool = false;

                englang.setTypeface(Typeface.DEFAULT_BOLD);;
                hindilang.setTypeface(Typeface.DEFAULT);

                mTTS = new TextToSpeech(view.getContext(), new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if (status == TextToSpeech.SUCCESS) {
                            //int result = mTTS.setLanguage(new Locale("hin"));
                            int result = mTTS.setLanguage(Locale.ENGLISH);
                            if (result == TextToSpeech.LANG_MISSING_DATA
                                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                Log.e("TTS", "Language not supported");
                            } else {
                                if (femalevoicebool)
                                mButtonSpeak.setEnabled(true);
                                else {
                                    Set<String> a=new HashSet<>();
                                    a.add("male");//here you can give male if you want to select male voice.
                                    Voice v=new Voice("en-us-x-sfg#male_2-local",new Locale("en","US"),400,200,true,a);
                                    mTTS.setVoice(v);
                                    mButtonSpeak.setEnabled(true);
                                }
                            }
                        } else {
                            Log.e("TTS", "Initialization failed");
                        }
                    }
                });
            }
        });


        hindilang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                engvoicebool = false;
                hindivoicebool = true;

                hindilang.setTypeface(Typeface.DEFAULT_BOLD);;
                englang.setTypeface(Typeface.DEFAULT);

                mTTS = new TextToSpeech(view.getContext(), new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if (status == TextToSpeech.SUCCESS) {
                            int result = mTTS.setLanguage(new Locale("hin"));
                            //int result = mTTS.setLanguage(Locale.ENGLISH);
                            if (result == TextToSpeech.LANG_MISSING_DATA
                                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                Log.e("TTS", "Language not supported");
                            } else {
                                if (femalevoicebool)
                                    mButtonSpeak.setEnabled(true);
                                else {
                                    Set<String> a=new HashSet<>();
                                    a.add("male");//here you can give male if you want to select male voice.
                                    Voice v=new Voice("en-us-x-sfg#male_2-local",new Locale("hin"),400,200,true,a);
                                    mTTS.setVoice(v);
                                    mButtonSpeak.setEnabled(true);
                                }
                            }
                        } else {
                            Log.e("TTS", "Initialization failed");
                        }
                    }
                });
            }
        });


        malevoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                femalevoicebool = false;
                malevoicebool = true;
                malevoice.setTypeface(Typeface.DEFAULT_BOLD);
                femalevoice.setTypeface(Typeface.DEFAULT);

                    Set<String> a=new HashSet<>();
                    a.add("male");//here you can give male if you want to select male voice.
                    Voice v=new Voice("en-us-x-sfg#male_2-local",new Locale("en","US"),400,200,true,a);
                    mTTS.setVoice(v);


            }
        });

        femalevoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                femalevoice.setTypeface(Typeface.DEFAULT_BOLD);
                malevoice.setTypeface(Typeface.DEFAULT);
                malevoicebool = false;
                femalevoicebool = true;
                if (hindivoicebool){
                    Set<String> a=new HashSet<>();
                    a.add("female");//here you can give male if you want to select mail voice.
                    Voice v=new Voice("en-us-x-sfg#female_2-local",new Locale("hin"),400,200,true,a);
                    mTTS.setVoice(v);
                }else {
                    Set<String> a=new HashSet<>();
                    a.add("female");//here you can give male if you want to select mail voice.
                    Voice v=new Voice("en-us-x-sfg#female_2-local",new Locale("en","US"),400,200,true,a);
                    mTTS.setVoice(v);
                }
            }
        });


        mEditText = findViewById(R.id.edit_text);
        mSeekBarPitch = findViewById(R.id.seek_bar_pitch);
        mSeekBarSpeed = findViewById(R.id.seek_bar_speed);

        mButtonSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Filename = mEditText.getText().toString().trim();
                if (Filename.isEmpty()){
                    mEditText.setError("Please write something for speech");
                    mEditText.setFocusable(true);
                }else {
                    speak();
                    if (check){
                        speakText(Filename);
                    }else {
                        check = checkPermissions();
                        Toast.makeText(getApplicationContext(),"Please give Write Permission, For saving this audio.",Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }

    private void speak() {
        String text = mEditText.getText().toString();
        float pitch = (float) mSeekBarPitch.getProgress() / 50;
        if (pitch < 0.1) pitch = 0.1f;
        float speed = (float) mSeekBarSpeed.getProgress() / 50;
        if (speed < 0.1) speed = 0.1f;

        mTTS.setPitch(pitch);
        mTTS.setSpeechRate(speed);

        mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    protected void onDestroy() {
        if (mTTS != null) {
            mTTS.stop();
            mTTS.shutdown();
        }

        super.onDestroy();
    }

    private void speakText(String text) {
        String state = Environment.getExternalStorageState();
        boolean mExternalStorageWriteable = false;
        boolean mExternalStorageAvailable = false;
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // Can read and write the media
            mExternalStorageAvailable = mExternalStorageWriteable = true;

        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            // Can only read the media
            mExternalStorageAvailable = true;
            mExternalStorageWriteable = false;
        } else {
            // Can't read or write
            mExternalStorageAvailable = mExternalStorageWriteable = false;
        }
        File root = android.os.Environment.getExternalStorageDirectory();
        File dir = new File(root.getAbsolutePath() + "/download");
        dir.mkdirs();
        File file = new File(dir, Filename+".mp3");
        int test = mTTS.synthesizeToFile((CharSequence) text, null, file,
                "tts");

        Toast.makeText(getApplicationContext(),"Your Text is saved in Downlaods folder",Toast.LENGTH_SHORT).show();
    }


    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 100);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // do something
            }
            return;
        }
    }

}
