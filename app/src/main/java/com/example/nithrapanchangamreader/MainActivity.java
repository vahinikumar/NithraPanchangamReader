package com.example.nithrapanchangamreader;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textView = new TextView(this);
        textView.setText("Nithra Panchangam Reader");
        textView.setTextSize(24);
        textView.setPadding(30, 30, 30, 30);

        setContentView(textView);
    }
}
