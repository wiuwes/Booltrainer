package com.example.booltrainer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void openExercise(View view) {
        Intent intent = new Intent(MainActivity.this,ExerciseActivity.class);
        if(((RadioButton)findViewById(R.id.simple)).isChecked()){
            intent.putExtra("complication",1);
        }else if(((RadioButton)findViewById(R.id.middle)).isChecked()){
            intent.putExtra("complication",2);
        } else if (((RadioButton)findViewById(R.id.hard)).isChecked()) {
            intent.putExtra("complication",3);
        }else{
            System.out.println("ты пидр");
        }
        //intent.putExtra("getOperations",operations);
        startActivity(intent);
    }
}