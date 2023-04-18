package com.example.booltrainer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.booltrainer.alglog.expression;
import com.example.booltrainer.alglog.for_expression;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainExpression extends Thread{
    TextView textView;
    LinearLayout for_letters;

    EditText editText;
    Context context;

    ArrayList<expression> MainExp;
    ArrayList<String> operations;

    int complication;

    interface expfuncs{
        public void func(expression n);
    }

    interface startexp{
        public expression func();
    }

    ArrayList<expfuncs> useActions;
    ArrayList<expfuncs> additilogicop;
    ArrayList<expfuncs> replaceonelet;
    ArrayList<expfuncs> replacetwolet;
    ArrayList<expfuncs> replacebool;

    ArrayList<String> usedletters;
    public MainExpression(TextView textView,LinearLayout for_letters,Context context, EditText editText,ArrayList<String> operations,int complication,ArrayList<String> usedletters,ArrayList<expression> MainExp){
        this.textView = textView;
        this.for_letters = for_letters;
        this.context = context;
        this.editText = editText;
        this.operations = operations;
        this.complication = complication;
        this.usedletters = usedletters;
        this.MainExp = MainExp;
    }
    public void run(){
        for_expression letters = new for_expression();
        this.replaceonelet = new ArrayList<expfuncs>(Arrays.asList(
                (n)-> n.Law_of_reflexivity_or(n.how_deep()),//A на A
                (n)-> n.Law_of_reflexivity_and(n.how_deep()),//A на A
                (n)-> n.Law_of_null_and_one_1(n.how_deep()),//A на 0 и A
                (n)-> n.Law_of_null_and_one_2(n.how_deep()),//A на 1 и A
                (n)-> n.Law_of_Neg_of_Neg(n.how_deep()),//A на A
                (n)-> n.Law_of_absorption1(letters,n.how_deep()),//A на A и B
                (n)-> n.Law_of_absorption2(letters,n.how_deep())//A на A и B
                ));
        this.replacebool = new ArrayList<expfuncs>(Arrays.asList(
                (n)-> n.Law_of_null_and_one_3(letters),//0 на A и 0
                (n)-> n.Law_of_null_and_one_4(letters),//1 на A и 0
                (n)-> n.Law_of_Non_Contradiction(letters),//0 на A
                (n)-> n.Law_of_Excluded_3(letters)// 1 на A
        ));
        this.additilogicop = new ArrayList<expfuncs>(Arrays.asList(
                (n)-> n.implication(),
                (n)-> n.equivalence(),
                (n)-> n.xor()
        ));
        this.replacetwolet = new ArrayList<expfuncs>(Arrays.asList(
                (n)-> n.Law_of_absorption3()//A и B на A и B
        ));
        this.useActions = new ArrayList<expfuncs>();
        ArrayList<startexp> simplexpfuncs = new ArrayList<startexp>();
        Random random = new Random();
        switch (this.complication){
            case 1:
                simplexpfuncs.add((startexp) () -> for_expression.make_value(letters.new_latter()));
                simplexpfuncs.add((startexp) () -> for_expression.make_value(Integer.toString(random.nextInt(2))));
                simplexpfuncs.add((startexp) (() -> new expression(for_expression.easyexps[random.nextInt(for_expression.easyexps.length)],for_expression.make_value(letters.new_latter()),for_expression.make_value(letters.new_latter()),0)));
                break;
            case 2:
                simplexpfuncs.add((startexp) () -> letters.full_easy_exp());
                simplexpfuncs.add((startexp) (() -> new expression(for_expression.easyexps[random.nextInt(for_expression.easyexps.length)],for_expression.make_value(letters.new_latter()),for_expression.make_value(letters.new_latter()),0)));
                simplexpfuncs.add((startexp) (() -> new expression(for_expression.hardexps[random.nextInt(for_expression.hardexps.length)],for_expression.make_value(letters.new_latter()),for_expression.make_value(letters.new_latter()),0)));
                break;
            case 3:
                simplexpfuncs.add((startexp) () -> letters.full_easy_exp());
                simplexpfuncs.add((startexp) (() -> new expression(for_expression.hardexps[random.nextInt(for_expression.hardexps.length)],for_expression.make_value(letters.new_latter()),for_expression.make_value(letters.new_latter()),0)));
                break;
        }
        expression exp = simplexpfuncs.get(random.nextInt(simplexpfuncs.size())).func();
        this.MainExp.add(exp.return_self());
        operations.add(letters.printexp(exp));
        String result;
        int how_much = 0;
        switch(this.complication){
            case 1:
                useActions.addAll(replaceonelet);
                useActions.addAll(replacebool);
                how_much = 3;
                break;
            case 2:
                useActions.addAll(replaceonelet);
                useActions.addAll(replacebool);
                useActions.addAll(additilogicop);
                how_much = 4;
                break;
            case 3:
                useActions.addAll(replaceonelet);
                useActions.addAll(replacebool);
                useActions.addAll(additilogicop);
                how_much = 7;
                break;
        }
        int length = useActions.size();
        for(int i = 0;i < how_much;i++){
            useActions.get(random.nextInt(length)).func(exp);
            result = letters.printexp(exp);
            if(!operations.get(i).equals(result)) {
                operations.add(letters.printexp(exp));
            }else{
                i--;
            }
        }
        for(int i = 0;i < letters.used_letters.size();i++){
            this.usedletters.add(letters.used_letters.get(i));
        }
        this.MainExp.add(exp);
        this.textView.post(new Runnable() {
            public void run() {
                textView.setText(letters.printexp(exp)+ " = ");
            }
        });
        this.for_letters.post(new Runnable() {
            @SuppressLint("ResourceType")
            public void run() {
                for(int i = 0;i < letters.used_letters.size();i++) {
                    Button button = new Button(context);
                    button.setText(letters.used_letters.get(i));
                    button.setTextSize(26);
                    System.out.println(letters.used_letters.get(i));
                    Resources resources = context.getResources();
                    int btn_height = (int)resources.getDimension(R.dimen.btn_height);
                    int btn_weight = (int)resources.getDimension(R.dimen.btn_weight);
                    for_letters.addView(button,new RelativeLayout.LayoutParams(btn_weight,btn_height));
                    button.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            Button button = (Button)view;
                            int index = editText.getSelectionStart();
                            editText.getText().insert(index, button.getText());
                        }
                    });
                }
            }
        });
    }
}
