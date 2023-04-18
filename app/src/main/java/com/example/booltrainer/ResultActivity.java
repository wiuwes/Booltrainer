package com.example.booltrainer;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        LinearLayout mainLayout = findViewById(R.id.mainLayout);
        Bundle arguments = getIntent().getExtras();
        ArrayList<String> operations = (ArrayList<String>) arguments.get("getOperations");
        int length = operations.size();
        for(int i = 0;i < length;i++){
            TextView textView = new TextView(this);
            textView.setText(Integer.toString(length - i) + ") " +operations.get(i));
            textView.setTextSize(26);
            LinearLayout.LayoutParams margin = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            margin.setMargins(0,50,0,50);
            mainLayout.addView(textView,0,margin);
        }
    }

    public void back(View view){
        finish();
    }
}
