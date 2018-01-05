package org.labs.musaka.calculator;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rimogardino on 1/2/18.
 */

//to do's




public final class CalculationEvaluation {
    public static String result;
    private static boolean defaultBadCase;
    private static ArrayList<String> reservedCarecters = new ArrayList<>();
    public static final String erroneousCalculation = "Err!!";

    public static String evalStatement(String statement) {
        reservedCarecters.add("*");
        reservedCarecters.add("/");
        reservedCarecters.add("+");
        reservedCarecters.add("-");
        defaultBadCase = false;

        Object[] tempSplit = operSplit(statement);
        ArrayList<String> operants = (ArrayList<String>) tempSplit[0];
        //check if the operants represent a calculable thing else return an error
        //attempt to calculate if the calculation goes properly return the result else return an error
        if (isLegitStatement(operants)) {
            result = calculate(operants);
            if (defaultBadCase == true) {
                return erroneousCalculation;
            } else {
                return String.valueOf(result);
            }
        } else {
            return erroneousCalculation;
        }
        //ArrayList<String> operations = (ArrayList<String>) tempSplit[1];

    }


    private static String calculate(ArrayList<String> operants) {

        for (int i=0;i<reservedCarecters.size();i++) {
            if (operants.contains(reservedCarecters.get(i))) {
                int indexOfOperation = operants.indexOf(reservedCarecters.get(i));
                result = subCalculate(new String[]{operants.get(indexOfOperation - 1), operants.get(indexOfOperation + 1)}, reservedCarecters.get(i));
                operants.remove(indexOfOperation+1);
                operants.remove(indexOfOperation);
                operants.remove(indexOfOperation - 1);
                operants.add(indexOfOperation - 1, result);

                result = calculate(operants);
            }
        }


        return result;
    }


    private static String subCalculate(String[] operants, String operation) {
        double subResult = 0;

        double operant1 = Double.valueOf(operants[0]);
        double opernat2 = Double.valueOf(operants[1]);
/*        if (operants.length < 2) {
            subResult = 0;
            return subResult;
        }*/
        switch (operation) {
            case "+":
                subResult = operant1 + opernat2;
                break;
            case "*":
                subResult = operant1 * opernat2;
                break;
            case "/":
                subResult = operant1 / opernat2;
                break;
            default:
                defaultBadCase = true;
                break;

        }

        return String.valueOf(subResult);
    }


    @NonNull
    private static Object[] operSplit(String statement) {
        ArrayList<String> operants = new ArrayList<>();
        //ArrayList<String> operations = new ArrayList<>();

        String buffer = "";
        for (int i=0;i<statement.length();i++) {
            //Deals with positive/negative sign at the first char input
            if (i == 0) {
                if (String.valueOf(statement.charAt(i)).equals("-")) {
                    buffer = "-";
                } else if (String.valueOf(statement.charAt(i)).equals("+")) {
                    //basically do nothing as the default for all numbers is positive
                    buffer = "";
                } else if (reservedCarecters.contains(String.valueOf(statement.charAt(i)))) {
                    buffer = "";
                    operants.add(String.valueOf(statement.charAt(i)));
                }
            }
            //for i>0 checks if the current char is an operation and adds the buffered number if so, also adds "-" as part of the numbers
            else if (reservedCarecters.contains(String.valueOf(statement.charAt(i)))) {
                if (!buffer.isEmpty()) operants.add(buffer);

                if (String.valueOf(statement.charAt(i)).equals("-")) {
                    buffer = "-";
                    if (operants.size() > 0 && !reservedCarecters.contains(operants.get(operants.size() - 1)))
                        operants.add(String.valueOf("+"));
                } else {
                    buffer = "";
                    operants.add(String.valueOf(statement.charAt(i)));
                }
            }
            //fills consecutive numbers/points in the buffer
            else {
                buffer = buffer + String.valueOf(statement.charAt(i));
            }

        }
        //adds the last entry from the buffer
        if (!buffer.equals("")) {
            operants.add(buffer);
        }

        return new Object[]{operants};
    }


    private static boolean isLegitStatement(ArrayList<String> operants) {
        if (operants.size() < 2) return false; //Not enough things to make a calculation
        if (reservedCarecters.contains(operants.get(0)))
            return false; //Cannot begin a statement with an operation
        for (int i = 0; i < operants.size(); i++) {
            if (reservedCarecters.contains(operants.get(i))) {
                if (i != 0 && (reservedCarecters.contains(operants.get(i - 1)) || reservedCarecters.contains(operants.get(i + 1)))) {
                    return false;
                }
            } else if (operants.get(i).contains(".")) {
                String operantI = operants.get(i);
                int countPoints = 0;
                for (int k = 0; k < operantI.length(); k++) {
                    if (operantI.charAt(k) == '.') countPoints++;
                    if (countPoints > 1) return false;
                }
            }
        }

        return true;
    }

}
