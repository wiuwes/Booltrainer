package com.example.booltrainer.alglog;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;



public class InfixToPostfix {

    // A utility function to return
    // precedence of a given operator
    // Higher returned value means
    // higher precedence
    static int Prec(char ch)
    {
        switch (ch) {
        case '@':
        case '#':
            return 1;

        case '+':
        case '%':
            return 2;

        case '*':
            return 3;
        case '-':
            return 4;
        }
        return -1;
    }

    public static String infixToPostfix(String exp)
    {
        String result = new String("");

        Deque<Character> stack
            = new ArrayDeque<Character>();

        for (int i = 0; i < exp.length(); ++i) {
            char c = exp.charAt(i);

            if (Character.isLetterOrDigit(c))
                result += c;

            else if (c == '(')
                stack.push(c);

            else if (c == ')') {
                while (!stack.isEmpty()
                       && stack.peek() != '(') {
                    result += stack.peek();
                    stack.pop();
                }

                stack.pop();
            }else{
                while (!stack.isEmpty()
                       && Prec(c) <= Prec(stack.peek())) {

                    result += stack.peek();
                    stack.pop();
                }
                stack.push(c);
            }
        }

        while (!stack.isEmpty()) {
            if (stack.peek() == '(')
                return "Invalid Expression";
            result += stack.peek();
            stack.pop();
        }

        return result;
    }
    static boolean isOperator(char c)
    {
        return c == '+' || c == '*' || c == '@' || c == '%' || c == '#' || c =='-' || c == ')' || c == '(';
    }
    public static boolean isCorrect(String s){
      s = toMySymbols(s);
      int right = 0;
      int left = 0;
      char[] exp = s.toCharArray();
      int l = exp.length;
      if (l == 1){
        if(isOperator(exp[0])){
          return false;
        }
      }else if(l == 2) {
          if(!(!isOperator(exp[1]) && (exp[0] == '-'))){
            return false;
          }
      }else{
        if ((!isOperator(exp[0]) || (exp[0] == '-') || (exp[0] == '('))
        && (!isOperator(exp[l-1]) || (exp[l-1] == ')'))){
          for(int i = 0;i < l-1;i++){
            if(!isOperator(exp[i])){
              if(!(isOperator(exp[i+1])&&(exp[i+1]!='(')&&(exp[i+1]!='-'))){
                System.out.println("1");
                return false;
              }
            }else if(exp[i]=='('){
                left++;
              if(!(!isOperator(exp[i+1]) || (exp[i+1]=='(') || (exp[i+1]=='-'))){
                System.out.println("2");
                return false;
              }
            }else if(exp[i]==')'){
                right++;
              if (!(isOperator(exp[i+1]) && (exp[i+1]!='(') && (exp[i+1]!='-'))){
                System.out.println("3");
                return false;
              }
            }else{
              if (!(!isOperator(exp[i+1])|| (exp[i+1]=='(') || (exp[i+1]=='-'))){
                System.out.println(i);
                System.out.println(4);
                return false;
              }
            }
          }
        }else{
          return false;
        }
      }
      if (exp[l-1] == ')'){
          right++;
      }
      return (true && (right == left));
    }
    public static String toMySymbols(String s){
      String[] smass = s.split("");
      s = "";
      String[] oper = new String[] {"∨","∧","→","≡","⊕","¬"};
      char[] oper2 = new char[] {'+','*','@','#','%','-'};
      boolean isOper = true;
      int i,j;
      for(i = 0;i < smass.length;i++){
        for(j = 0;j < 6;j++){
          if(smass[i].equals(oper[j])){
            isOper = true;
            break;
          }else{
            isOper = false;
          }
        }
        if(isOper){
          s += oper2[j];
        }else{
          s += smass[i];
        }
      }
      return s;
    }
}
