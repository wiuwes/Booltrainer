// Java code to convert infix expression to postfix
package com.example.booltrainer.alglog;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;



public class ExpressionTree {

    static boolean isOperator(char c)
    {
        return c == '+' || c == '*' || c == '@' || c == '%' || c == '#';
    }

    static String translate(char c){
      switch(c){
        case '+':
          return "∨";
        case '*':
          return "∧";
        case '@':
          return "→";
        case '#':
          return "≡";
        case '%':
          return "⊕";
        case '-':
          return "¬";
      }
      return "";
    }

    public static expression buildTree(String postfix)
    {
        postfix = InfixToPostfix.infixToPostfix(InfixToPostfix.toMySymbols(postfix));
        Stack<expression> stack = new Stack<expression>();

        for (int i = 0; i < postfix.length(); i++) {
            char c = postfix.charAt(i);

            if (isOperator(c)) {
                expression exp2 = stack.pop();
                expression exp1 = stack.pop();
                expression exp = new expression(translate(c),null,null,-1);
                exp.exp1 = exp1;
                exp.exp2 = exp2;
                exp.new_length();
                stack.push(exp);
            }
            else if(c == '-'){
                expression exp1 = stack.pop();
                expression exp = new expression("¬",null,null,-1);
                exp.exp1 = exp1;
                exp.new_length();
                stack.push(exp);
            }else {
                expression exp = new expression(c+"",null,null,-1);
                stack.push(exp);
            }
        }

        expression root = stack.peek();
        stack.pop();
        return root;
    }
}
