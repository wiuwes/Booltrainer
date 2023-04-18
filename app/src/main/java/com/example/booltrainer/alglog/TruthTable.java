package com.example.booltrainer.alglog;

public class TruthTable {
    private static boolean[][] newTruthTable(int n) {
        int rows = (int) Math.pow(2,n);
        boolean[][] Values = new boolean[rows][n];
        for (int i=0; i<rows; i++) {
            for (int j=n-1; j>=0; j--) {
                if (((i/(int) Math.pow(2, j))%2) == 0) {
                    Values[i][j] = false;
                }else {
                    Values[i][j] = true;
                }
            }
        }
        return Values;
    }
    public static boolean[] check(expression exp,String[] letters){
        boolean[][] inValues = newTruthTable(letters.length);
        boolean[] outValues = new boolean[inValues.length];
        for(int i = 0;i<outValues.length;i++){
            outValues[i] = whatValue(exp,letters,inValues[i]);
        }
        return outValues;
    }
    public static boolean whatValue(expression exp, String[] letters, boolean[] Values){
        boolean value = true;
        boolean value1,value2;
        if(exp.isExp()){
            value1 = whatValue(exp.getExp1(), letters, Values);
            value2 = whatValue(exp.getExp2(), letters, Values);
            String sim = exp.getSim();
            if(sim.equals("∨")) {
                value = value1 || value2;
            }else if(sim.equals("∧")){
                value = value1 && value2;
            }else if(sim.equals("→")){
                value = !value1 || value2;
            } else if (sim.equals("≡")) {
                value = value1 == value2;
            } else if (sim.equals("⊕")) {
                value = value1 ^ value2;
            }
        }else{
            if(exp.isNegative()){
                value = !whatValue(exp.getExp1(), letters, Values);
            }else{
                String letter = exp.getSim();
                for(int i = 0;i < letters.length;i++) {
                    if (letter.equals(letters[i])) {
                        value = Values[i];
                        break;
                    }
                }
                if(letter.equals("0")){
                    value = false;
                } else if (letter.equals("1")) {
                    value = true;
                }
            }
        }
        return value;
    }
}

