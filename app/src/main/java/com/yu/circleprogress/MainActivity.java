package com.yu.circleprogress;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private CircleProgressView mProgressView;
    private float progress = 6000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressView = findViewById(R.id.circleProgress);
        mProgressView.setProgress(progress / 10000);
        mProgressView.barAnimation();
    }

    public void buttonOnce(View view) {
        progress += 1000;
        mProgressView.setProgress(progress / 10000);
        mProgressView.barAnimation();
    }
}
