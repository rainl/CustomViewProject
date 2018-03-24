package com.test.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.test.view.widget.RippleView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RippleView rippleView = findViewById(R.id.mRippleView);
        rippleView.startAnimation();
    }
}
