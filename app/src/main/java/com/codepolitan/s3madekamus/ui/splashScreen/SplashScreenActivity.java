package com.codepolitan.s3madekamus.ui.splashScreen;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.codepolitan.s3madekamus.R;
import com.codepolitan.s3madekamus.helper.KamusHelper;
import com.codepolitan.s3madekamus.helper.PrefManager;
import com.codepolitan.s3madekamus.model.KamusModel;
import com.codepolitan.s3madekamus.ui.main.MainActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashScreenActivity extends AppCompatActivity {
    @BindView(R.id.tv_logo)
    TextView tv_logo;
    @BindView(R.id.progress_bar)
    ProgressBar progress_bar;
    @BindView(R.id.tv_load)
    TextView tv_load;

    PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);

        tv_logo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
//            }
//        }, 3000);
        new LoadData().execute();
    }

    private class LoadData extends AsyncTask<Void, Integer, Void> {
        KamusHelper kamusHelper;
        double progress;
        double maxprogress = 100;

        @Override
        protected void onPreExecute() {
            kamusHelper = new KamusHelper(SplashScreenActivity.this);
            prefManager = new PrefManager(SplashScreenActivity.this);
        }

        @Override
        protected Void doInBackground(Void... params) {
            Boolean firstRun = prefManager.getFirstRun();
            if (firstRun) {
                List<KamusModel> kamusEnglish = preLoadRaw(R.raw.english_indonesia);
                List<KamusModel> kamusIndonesia = preLoadRaw(R.raw.indonesia_english);

                publishProgress((int) progress);

                kamusHelper.open();

                Double progressMaxInsert = 100.0;
                Double progressDiff = (progressMaxInsert - progress) / (kamusEnglish.size() + kamusIndonesia.size());

                kamusHelper.insertTransaction(kamusEnglish, true);
                progress += progressDiff;
                publishProgress((int) progress);

                kamusHelper.insertTransaction(kamusIndonesia, false);
                progress += progressDiff;
                publishProgress((int) progress);

                kamusHelper.close();

                publishProgress((int) maxprogress);

            } else {
                tv_load.setVisibility(View.INVISIBLE);
                try {
                    synchronized (this) {
                        this.wait(1000);
                        publishProgress(50);

                        this.wait(300);
                        publishProgress((int) maxprogress);
                    }
                } catch (Exception ignored) {
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progress_bar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void result) {
            Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
            startActivity(i);

            finish();
        }
    }

    public ArrayList<KamusModel> preLoadRaw(int data) {
        ArrayList<KamusModel> kamusModels = new ArrayList<>();
        BufferedReader reader;

        try {
            Resources res = getResources();
            InputStream raw_dict = res.openRawResource(data);

            reader = new BufferedReader(new InputStreamReader(raw_dict));
            String line = null;

            do {
                line = reader.readLine();
                String[] splitstr = line.split("\t");
                KamusModel kamusModel;
                kamusModel = new KamusModel(splitstr[0], splitstr[1]);
                kamusModels.add(kamusModel);
            } while (line != null);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return kamusModels;
    }
}
