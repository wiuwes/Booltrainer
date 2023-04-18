package com.example.booltrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.view.View;
import android.widget.TextView;
import android.os.Bundle;
import android.widget.Toast;

import com.example.booltrainer.alglog.ExpressionTree;
import com.example.booltrainer.alglog.InfixToPostfix;
import com.example.booltrainer.alglog.TruthTable;
import com.example.booltrainer.alglog.expression;
import com.example.booltrainer.alglog.for_expression;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.*;

public class ExerciseActivity extends AppCompatActivity {
    EditText newSolve;
    LinearLayout topWindow;

    ArrayList<String> operations;

    ArrayList<String> usedletters;

    ArrayList<expression> MainExp;
    LinearLayout Keyboard;

    RelativeLayout.LayoutParams KeyboardParam;
    int NumberSolve = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise);
        topWindow = findViewById(R.id.topWindow);

        newSolve = findViewById(R.id.newSolve);
        newSolve.setShowSoftInputOnFocus(false);

        Keyboard = findViewById(R.id.Keyboard);

        KeyboardParam = (RelativeLayout.LayoutParams) Keyboard.getLayoutParams();

        int complication = (int) getIntent().getExtras().get("complication");
        TextView textView = findViewById(R.id.text);
        LinearLayout for_letters = findViewById(R.id.field_for_letters);
        operations = new ArrayList<String>();
        usedletters = new ArrayList<String>();
        MainExp = new ArrayList<expression>();
        MainExpression newExpression = new MainExpression(textView,for_letters, this,newSolve,operations,complication,usedletters,MainExp);
        newExpression.start();
    }

    public void inNewSolve(View view){
        Button button = (Button)view;
        String text = (String) button.getText();

        int index = newSolve.getSelectionStart();
        newSolve.getText().insert(index, text);
    }

    public void backspace(View view){
        int length = newSolve.getText().length();
        if (length > 0) {
            int index = newSolve.getSelectionStart();
            newSolve.getText().delete(index - 1, index);
        };
    }
    public void clear(View view){
        newSolve.getText().clear();
    }

    public void NextSolve(View view){
        String expression = newSolve.getText().toString();
        if (!expression.equals("")) {
            if(InfixToPostfix.isCorrect(expression)){
                if(Arrays.equals(TruthTable.check(ExpressionTree.buildTree(expression), this.usedletters.toArray(new String[this.usedletters.size()])),TruthTable.check(MainExp.get(1), this.usedletters.toArray(new String[this.usedletters.size()])))) {
                    TextView textView = new TextView(this);
                    textView.setTextSize(23);
                    textView.setId(++NumberSolve);
                    System.out.println(this.usedletters);
                    textView.setText(expression + " = ");
                    clear(view);
                    topWindow.addView(textView, NumberSolve);
                    System.out.println(for_expression.printexp(MainExp.get(0)));
                    if(for_expression.printexp(MainExp.get(0)).length() >= for_expression.printexp(ExpressionTree.buildTree(expression)).length()){
                        Intent intent = new Intent(ExerciseActivity.this, WinActivity.class);
                        startActivity(intent);
                    }
                }else{
                    Toast toast = Toast.makeText(this, "Выражение не равно исходному!",Toast.LENGTH_LONG);
                    toast.show();
                }
            }else{
                Toast toast = Toast.makeText(this, "Выражение некорректно!",Toast.LENGTH_LONG);
                toast.show();
            }
            /**
            expression += " = ";
            TextView textView = new TextView(this);
            textView.setTextSize(23);
            textView.setId(++NumberSolve);
            textView.setText(expression);
            clear(view);
            topWindow.addView(textView, NumberSolve);
             */
        }
    }
  
    public void backSolve(View view){
        if(NumberSolve > 0) {
            clear(view);
            TextView textView = findViewById(NumberSolve);
            newSolve.setText(textView.getText());
            topWindow.removeViewAt(NumberSolve--);
        }
    }

    public void getResult(View view) {
        Intent intent = new Intent(ExerciseActivity.this, ResultActivity.class);
        intent.putExtra("getOperations",operations);
        startActivity(intent);
    }

    public void fullClean(View view){
        for(int i = 1;i < NumberSolve + 1;NumberSolve--){
            topWindow.removeViewAt(i);
        }
    }

    public void opencloseKeyboard(View view){
        if(KeyboardParam.height != 0){
            KeyboardParam.height = 0;
        }else {
            KeyboardParam.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
        }
        Keyboard.setLayoutParams(KeyboardParam);
    }

    public void back(View view){
        finish();
    }

}