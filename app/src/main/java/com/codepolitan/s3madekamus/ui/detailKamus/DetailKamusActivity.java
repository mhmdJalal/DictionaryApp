package com.codepolitan.s3madekamus.ui.detailKamus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.codepolitan.s3madekamus.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailKamusActivity extends AppCompatActivity {
    @BindView(R.id.tv_word)
    TextView tv_word;
    @BindView(R.id.tv_translate)
    TextView tv_translate;

    private String word, translate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kamus);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Detail Kamus");

        word = getIntent().getStringExtra("word");
        translate = getIntent().getStringExtra("translate");

        tv_word.setText(word);
        tv_translate.setText(translate);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
