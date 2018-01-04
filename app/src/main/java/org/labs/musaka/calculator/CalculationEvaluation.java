package org.labs.musaka.calculator;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rimogardino on 1/2/18.
 */

public final class CalculationEvaluation {
    public static double result;
    private static ArrayList<String> reservedCarecters = new ArrayList<>();

    public static String evalStatement(String statement) {
        reservedCarecters.add("*");
        reservedCarecters.add("/");
        reservedCarecters.add("+");
        reservedCarecters.add("-");


        Object[] tempSplit = operSplit(statement);
        ArrayList<Double> operants = (ArrayList<Double>) tempSplit[0];
        ArrayList<String> operations = (ArrayList<String>) tempSplit[1];
        result = calculate(operants,operations);

        return String.valueOf(result);
    }

    private static double calculate(ArrayList<Double> operants, ArrayList<String> operations) {

        for (int i=0;i<reservedCarecters.size();i++) {
            if (operations.contains(reservedCarecters.get(i))) {
                int indexOfOperation = operations.indexOf(reservedCarecters.get(i));
                result = subCalculate(new Double[]{operants.get(indexOfOperation),operants.get(indexOfOperation+1)},reservedCarecters.get(i));
                operants.remove(indexOfOperation+1);
                operants.remove(indexOfOperation);
                operations.remove(indexOfOperation);
                operants.add(indexOfOperation,result);

                result = calculate(operants,operations);
            }
        }


        return result;
    }


    private static double subCalculate(Double[] operants, String operation) {
        double subResult;
/*        if (operants.length < 2) {
            subResult = 0;
            return subResult;
        }*/
        switch (operation) {
            case "+":
                subResult = operants[0] + operants[1];
                break;
            case "-":
                subResult = operants[0] - operants[1];
                break;
            case "*":
                subResult = operants[0] * operants[1];
                break;
            case "/":
                subResult = operants[0] / operants[1];
                break;
            default:
                subResult = 0;
                break;

        }

        return subResult;
    }


    @NonNull
    private static Object[] operSplit(String statement) {
        ArrayList<Double> operants = new ArrayList<>();
        ArrayList<String> operations = new ArrayList<>();

        String buffer = "";
        for (int i=0;i<statement.length();i++) {
            if (reservedCarecters.contains(String.valueOf(statement.charAt(i)))) {
                operants.add(Double.valueOf(buffer));
                buffer = "";
                operations.add(String.valueOf(statement.charAt(i)));
            } else{
                buffer = buffer + String.valueOf(statement.charAt(i));
            }

        }

        if (!buffer.equals("")) {
            operants.add(Double.valueOf(buffer));
        }

        return new Object[] {operants,operations};
    }


}
