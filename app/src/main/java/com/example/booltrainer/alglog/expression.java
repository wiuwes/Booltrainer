package com.example.booltrainer.alglog;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Stack;
import java.util.Random;
import static java.lang.Math.max;

public class expression{
  private static Random random = new Random();
  public expression exp1;
  public expression exp2;
  private String sim;
  private int length;

  public expression(String sim, expression exp1, expression exp2, int length){
    this.sim = sim;
    this.exp1 = exp1;
    this.exp2 = exp2;
    this.length = length + 1;
  }
/**Получение значений из exp*/
  public String getSim(){
    return this.sim;
  }

  public expression getExp1(){
    return this.exp1;
  }

  public expression getExp2(){
    return this.exp2;
  }

  public int getLength(){
    return this.length;
  }
/** Проверка на тип */
  public boolean isExp(){
    return ((this.exp1 != null) && (this.exp2 != null));
  }
  public boolean isNegative(){
    return (this.sim == "¬");
  }
/** */
  public int how_deep(){
    if(this.length != 0) {
      return random.nextInt(this.length);
    }else {
      return 0;
    }
  }
  public void new_length(){
    if (this.isNegative()){
      this.length = this.exp1.length + 1;
    }else{
      this.length = max(this.exp1.length,this.exp2.length) + 1;
    }
  }
/** */
public expression return_self(){
    expression exp1,exp2;
    if (this.exp1 != null){
      exp1 = this.exp1.return_self();
    }else{
      exp1 = this.exp1;
    }
    if (this.exp2 != null){
      exp2 = this.exp2.return_self();
    }else{
      exp2 = this.exp2;
    }
    return new expression(this.sim,exp1,exp2,this.length - 1);
  }
/** */
  interface for_dive{
    void func(expression n);
  }

  private void dive(for_dive func,int how_deep){
    boolean isexp1 = this.exp1 != null;
    boolean isexp2 = this.exp2 != null;
    if ((isexp1) && (isexp2)){
      int l1 = this.exp1.length;
      int l2 = this.exp2.length;
      if ((l1 >= how_deep) && (l2 >= how_deep)){
        if (random.nextBoolean()){
          func.func(this.exp1);
        }else{
          func.func(this.exp2);
        }
      }else if(l1 >= how_deep){
        func.func(this.exp1);
      }else if(l2 >= how_deep){
        func.func(this.exp2);
      }
    }else if(isexp1){
      func.func(this.exp1);
    }else if(isexp2){
      func.func(this.exp2);
    }
  }
/** */
  public List<List<Integer>> path_to_figure(String figure,List<Integer> full_path){
    boolean isexp1;
    if (this.exp2 != null){
      isexp1 = (this.exp1.isExp() || this.exp1.isNegative());
    }else{
      isexp1 = false;
    }
    boolean isexp2;
    if (this.exp2 != null){
      isexp2 = (this.exp2.isExp() || this.exp2.isNegative());
    }else{
      isexp2 = false;
    }
    List<List<Integer>> ret_paths = new ArrayList<List<Integer>>();
    if (isexp1 && isexp2){
        List<List<Integer>> exp1 = this.exp1.path_to_figure(figure,for_expression.return_new_list(full_path,1));
        List<List<Integer>> exp2 = this.exp2.path_to_figure(figure,for_expression.return_new_list(full_path,2));
        ret_paths.addAll(exp1);
        ret_paths.addAll(exp2);
    }else if(isexp1){
      if ((this.exp2 != null) && (this.exp2.sim == figure)){
        List<List<Integer>> exp2 = new ArrayList<List<Integer>>();
        exp2.add(for_expression.return_new_list(full_path,2));
        List<List<Integer>> exp1 = this.exp1.path_to_figure(figure,for_expression.return_new_list(full_path,1));
        ret_paths.addAll(exp1);
        ret_paths.addAll(exp2);
      }else{
        ret_paths = this.exp1.path_to_figure(figure,for_expression.return_new_list(full_path,1));
      }
    }else if(isexp2){
      if ((this.exp1 != null) && (this.exp1.sim == figure)){
        List<List<Integer>> exp1 = new ArrayList<List<Integer>>();
        exp1.add(for_expression.return_new_list(full_path,1));
        List<List<Integer>> exp2 = this.exp2.path_to_figure(figure,for_expression.return_new_list(full_path,2));
        ret_paths.addAll(exp1);
        ret_paths.addAll(exp2);
      }else{
        ret_paths = this.exp2.path_to_figure(figure,for_expression.return_new_list(full_path,2));
      }
    }else{
      boolean isfigure1;
      boolean isfigure2;
      if (this.exp1 != null){
        isfigure1 = this.exp1.sim == figure;
      }else{
        isfigure1 = false;
      }
      if (this.exp2 != null){
          isfigure2 = this.exp2.sim == figure;
      }else{
          isfigure2 = false;
      }
      if (isfigure1 && isfigure2){
        ret_paths.add(for_expression.return_new_list(full_path,1));
        ret_paths.add(for_expression.return_new_list(full_path,2));
      }else if(isfigure1){
        ret_paths.add(for_expression.return_new_list(full_path,1));
      }else if(isfigure2){
        ret_paths.add(for_expression.return_new_list(full_path,2));
      }
    }
    return ret_paths;
  }

  public void deep_to_figure(List<Integer> path, for_dive func){
      if(path.size() == 1){
        int where = path.get(0);
        if (where == 1){
          func.func(this.exp1);
        }else if (where == 2) {
          func.func(this.exp2);
        }else if (where == 0) {
          func.func(this);
        }
      }else if(path.size() == 0){
        System.out.println("fuck up");
      }else{
        int where = path.get(0);
        path.remove(0);
        if(where == 1){
          this.exp1.deep_to_figure(path,func);
          this.exp1.new_length();
        }else if(where == 2){
          this.exp2.deep_to_figure(path,func);
          this.exp2.new_length();
        }else if(where == 0){
          this.deep_to_figure(path,func);
        }
      }
  }

/**----------------------------------------------------------------*/
  public List<List<Integer>> path_to_sim(String sim, List<Integer> full_path){
    boolean isexp1 = (this.exp1.isExp() || this.exp1.isNegative());
    boolean isexp2;
    List<List<Integer>> ret_paths = new ArrayList<List<Integer>>();
    if (this.exp2 != null){
      isexp2 = (this.exp2.isExp() || this.exp2.isNegative());
    }else{
      isexp2 = false;
    }
    if (isexp1 && isexp2){
      ret_paths.addAll(this.exp1.path_to_sim(sim,for_expression.return_new_list(full_path,1)));
      ret_paths.addAll(this.exp2.path_to_sim(sim,for_expression.return_new_list(full_path,2)));
    }else if (isexp1){
      ret_paths.addAll(this.exp1.path_to_sim(sim,for_expression.return_new_list(full_path,1)));
    }else if (isexp2){
      ret_paths.addAll(this.exp2.path_to_sim(sim,for_expression.return_new_list(full_path,2)));
    }
    if (this.sim == sim){
      ret_paths.add(full_path);
    }
    return ret_paths;
  }
/**----------------------------------------------------------------*/
  private void Laws_of_reflexivity(String sim){
    boolean variant = random.nextBoolean() || this.isNegative();
    expression exp = this.return_self();
    this.exp1 = exp;
    this.exp2 = exp.return_self();
    this.sim = sim;
    this.new_length();
  }
  public void Law_of_reflexivity_or(int how_deep){
    if (how_deep == 0){
      this.Laws_of_reflexivity("∨");
    }else{
      for_dive func = (n)-> n.Law_of_reflexivity_or(how_deep - 1);
      this.dive(func,how_deep - 1);
      this.new_length();
    }
  }
  public void Law_of_reflexivity_and(int how_deep){
    if (how_deep == 0){
      this.Laws_of_reflexivity("∧");
    }else{
      for_dive func = (n)-> n.Law_of_reflexivity_and(how_deep - 1);
      this.dive(func,how_deep - 1);
      this.new_length();
    }
  }
/**----------------------------------------------------------------*/
  private void Laws_of_null_and_one(String sim,String figure){
    expression exp_figure = for_expression.make_value(figure);
    expression exp = this.return_self();
    this.sim = sim;
    if(random.nextBoolean()){
      this.exp1 = exp;
      this.exp2 = exp_figure;
    }else{
      this.exp1 = exp_figure;
      this.exp2 = exp;
    }
    this.new_length();
  }
  public void Law_of_null_and_one_1(int how_deep){
    if (how_deep == 0){
      this.Laws_of_null_and_one("∨","0");
    }else{
      for_dive func = (n)-> n.Law_of_null_and_one_1(how_deep - 1);
      this.dive(func,how_deep - 1);
      this.new_length();
    }
  }
  public void Law_of_null_and_one_2(int how_deep){
    if (how_deep == 0){
      this.Laws_of_null_and_one("∧","1");
    }else{
      for_dive func = (n)-> n.Law_of_null_and_one_2(how_deep - 1);
      this.dive(func,how_deep - 1);
      this.new_length();
    }
  }

  public void Law_of_null_and_one_3(for_expression letters){
    for_dive func = (n)-> n.Laws_of_null_and_one("∧",letters.new_latter());
    List<List<Integer>> multiple_paths = for_expression.no_empty(this.path_to_figure("0",for_expression.return_new_list(new ArrayList<Integer>(),0)));
    List<Integer> path = multiple_paths.get(random.nextInt(multiple_paths.size()));
    System.out.println(path);
    this.deep_to_figure(path,func);
  }

  public void Law_of_null_and_one_4(for_expression letters){
    for_dive func = (n)-> n.Laws_of_null_and_one("∨",letters.new_latter());
    List<List<Integer>> multiple_paths = for_expression.no_empty(this.path_to_figure("1",for_expression.return_new_list(new ArrayList<Integer>(),0)));
    List<Integer> path = multiple_paths.get(random.nextInt(multiple_paths.size()));
    System.out.println(path);
    this.deep_to_figure(path,func);
  }
/**----------------------------------------------------------------*/
  public void Law_of_figure_to_letter(String sim,for_expression letters){
      String letter = letters.new_latter();
      expression exp = for_expression.make_value(letter);
      expression nonexp = for_expression.returnNONexpression(for_expression.make_value(letter));
      this.sim = sim;
      if (random.nextBoolean()){
        this.exp1 = exp;
        this.exp2 = nonexp;
      }else{
        this.exp2 = exp;
        this.exp1 = nonexp;
      }
      this.new_length();
  }
  public void Law_of_Non_Contradiction(for_expression letters){
    for_dive func = (n)-> n.Law_of_figure_to_letter("∧",letters);
    List<List<Integer>> multiple_paths = for_expression.no_empty(this.path_to_figure("0",for_expression.return_new_list(new ArrayList<Integer>(),0)));
    List<Integer> path = multiple_paths.get(random.nextInt(multiple_paths.size()));
    System.out.println(path);
    this.deep_to_figure(path,func);
  }
  public void Law_of_Excluded_3(for_expression letters){
    for_dive func = (n)-> n.Law_of_figure_to_letter("∨",letters);
    List<List<Integer>> multiple_paths = for_expression.no_empty(this.path_to_figure("1",for_expression.return_new_list(new ArrayList<Integer>(),0)));
    List<Integer> path = multiple_paths.get(random.nextInt(multiple_paths.size()));
    System.out.println(path);
    this.deep_to_figure(path,func);
  }
/**----------------------------------------------------------------*/
  public void Law_of_Neg_of_Neg(int how_deep){
    if(how_deep == 0){
      expression nonexp = for_expression.returnNONexpression(this.return_self());
      this.sim = "¬";
      this.exp1 = nonexp;
      this.exp2 = null;
      this.new_length();
    }else{
      for_dive func = (n)-> n.Law_of_Neg_of_Neg(how_deep - 1);
      this.dive(func,how_deep - 1);
      this.new_length();
    }
  }
/**----------------------------------------------------------------*/
  public void Laws_of_absorption(for_expression letters,String sim1, String sim2){
    this.Laws_of_reflexivity(sim1);
    if (random.nextBoolean()){
      this.exp1.Laws_of_null_and_one(sim2,letters.new_latter());
    }else{
      this.exp2.Laws_of_null_and_one(sim2,letters.new_latter());
    }
  }
  public void Law_of_absorption1(for_expression letters,int how_deep){
    if (how_deep == 0){
      this.Laws_of_absorption(letters,"∨","∧");
    }else{
      for_dive func = (n)-> n.Law_of_absorption1(letters,how_deep - 1);
      this.dive(func,how_deep - 1);
      this.new_length();
    }
  }
  public void Law_of_absorption2(for_expression letters,int how_deep){
    if (how_deep == 0){
      this.Laws_of_absorption(letters,"∧","∨");
    }else{
      for_dive func = (n)-> n.Law_of_absorption2(letters,how_deep - 1);
      this.dive(func,how_deep - 1);
      this.new_length();
    }
  }
/**----------------------------------------------------------------*/
  public void for_Law_of_absorption3(){
    if (random.nextBoolean()){
      exp1 = for_expression.returnNONexpression(this.exp1.return_self());
      exp2 = this.exp2.return_self();
      this.exp2 = new expression("∧",exp1,exp2,Math.max(exp1.length,exp2.length));
      this.new_length();
    }else{
      exp2 = for_expression.returnNONexpression(this.exp2.return_self());
      exp1 = this.exp1.return_self();
      this.exp1 = new expression("∧",exp1,exp2,Math.max(exp1.length,exp2.length));
      this.new_length();
    }
  }
  public void Law_of_absorption3(){
    for_dive func = (n)-> n.for_Law_of_absorption3();
    List<List<Integer>> multiple_paths = for_expression.no_empty(this.path_to_sim("∨",for_expression.return_new_list(new ArrayList<Integer>(),0)));
    List<Integer> path = multiple_paths.get(random.nextInt(multiple_paths.size()));
    System.out.println(path);
    this.deep_to_figure(path,func);
  }
/**----------------------------------------------------------------*/
  public void for_implication(){
    this.exp1 = for_expression.returnNONexpression(this.exp1.return_self());
    this.sim = "∨";
  }
  public void implication(){
    for_dive func = (n)-> n.for_implication();
    List<List<Integer>> multiple_paths = for_expression.no_empty(this.path_to_sim("→",for_expression.return_new_list(new ArrayList<Integer>(),0)));
    List<Integer> path = multiple_paths.get(random.nextInt(multiple_paths.size()));
    System.out.println(path);
    this.deep_to_figure(path,func);
  }

  public void for_equivalence(){
    expression exp1 = this.exp1.return_self();
    expression exp2 = this.exp2.return_self();
    expression nonexp1 = for_expression.returnNONexpression(this.exp1.return_self());
    expression nonexp2 = for_expression.returnNONexpression(this.exp2.return_self());
    this.exp1 = new expression("∧",exp1,exp2,Math.max(exp1.length,exp2.length));
    this.exp2 = new expression("∧",nonexp1,nonexp2,Math.max(nonexp1.length,nonexp2.length));
    this.sim = "∨";
    this.new_length();
  }
  public void equivalence(){
    for_dive func = (n)-> n.for_equivalence();
    List<List<Integer>> multiple_paths = for_expression.no_empty(this.path_to_sim("≡",for_expression.return_new_list(new ArrayList<Integer>(),0)));
    List<Integer> path = multiple_paths.get(random.nextInt(multiple_paths.size()));
    System.out.println(path);
    this.deep_to_figure(path,func);
  }

  public void for_xor(){
    expression exp1 = this.exp1.return_self();
    expression exp2 = this.exp2.return_self();
    expression nonexp1 = for_expression.returnNONexpression(this.exp1.return_self());
    expression nonexp2 = for_expression.returnNONexpression(this.exp2.return_self());
    this.exp1 = new expression("∧",nonexp1,exp2,Math.max(nonexp1.length,exp2.length));
    this.exp2 = new expression("∧",exp1,nonexp2,Math.max(exp1.length,nonexp2.length));
    this.sim = "∨";
    this.new_length();
  }
  public void xor(){
    for_dive func = (n)-> n.for_xor();
    List<List<Integer>> multiple_paths = for_expression.no_empty(this.path_to_sim("⊕",for_expression.return_new_list(new ArrayList<Integer>(),0)));
    List<Integer> path = multiple_paths.get(random.nextInt(multiple_paths.size()));
    System.out.println(path);
    this.deep_to_figure(path,func);
  }
}
