package com.example.booltrainer.alglog;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class for_expression{
  public List<String> all_letters;
  public List<String> used_letters;
  public static String[] easyexps  = new String[] {"∨","∧"};
  public static String[] hardexps  = new String[] {"→","≡","⊕"};
  private static Random random = new Random();

  public for_expression(){
    all_letters = new ArrayList<>();
    used_letters = new ArrayList<>();
    int charAmount = 'Z' - 'A' + 1;
    for(int i = 0; i < charAmount ; i++){
        all_letters.add(("" + (char)('A' + i)));
    }
  }

  public static expression make_value(String value){
    return new expression(value, null, null, -1);
  }

  public static String printexp(expression exp){
    String sumexp;
    if(exp.isExp()){
          sumexp = "(" + printexp(exp.getExp1()) + exp.getSim() + printexp(exp.getExp2()) + ")";
      }else{
          if(exp.isNegative()){
              sumexp = exp.getSim() + "(" + printexp(exp.getExp1()) + ")";
          }else{
              sumexp = exp.getSim();
          }
      }
      return sumexp;
    }

  public static expression return_negative_exp(expression exp){
    return new expression("¬",exp,null,exp.getLength());
  }

  public String new_latter(){
    int num_new_letter = random.nextInt(this.all_letters.size());
    String new_latter = this.all_letters.get(num_new_letter);
    this.used_letters.add(new_latter);
    this.all_letters.remove(num_new_letter);
    return new_latter;
  }

  public String new_used_latter(){
    return this.used_letters.get(random.nextInt(this.used_letters.size()));
  }

  public expression exp_with_easy_sim(){
    expression newlet = make_value(this.new_latter());
    expression exp = new expression(easyexps[random.nextInt(easyexps.length)],make_value(this.new_latter()),make_value(this.new_latter()),0);
    if(random.nextBoolean()){
      if(random.nextBoolean()){
        exp = new expression(easyexps[random.nextInt(easyexps.length)],exp,newlet,exp.getLength());
      }else{
        exp = new expression(easyexps[random.nextInt(easyexps.length)],newlet,exp,exp.getLength());
      }
    }
    return exp;
  }

  public expression full_easy_exp(){
      expression newlet = make_value(this.new_latter());
      expression expeasysim = this.exp_with_easy_sim();
      expression exp;
      if (random.nextBoolean()){
        exp = new expression(hardexps[random.nextInt(hardexps.length)],newlet,expeasysim,expeasysim.getLength());
      }else{
        exp = new expression(hardexps[random.nextInt(hardexps.length)],expeasysim,newlet,expeasysim.getLength());
      }
      return exp;
  }

  public static List<Integer> return_new_list(List<Integer> list,int new_link){
    List<Integer> full_list = new ArrayList<Integer>();
    full_list.addAll(list);
    full_list.add(new_link);
    return full_list;
  }

  public static List<List<Integer>> no_empty(List<List<Integer>> list){
    if(list.isEmpty()){
      list.add(new ArrayList<Integer>());
    }
    return list;
  }

  public static expression returnNONexpression(expression exp){
      return new expression("¬",exp,null,exp.getLength());
  }
}
