package com.tsystems.javaschool.tasks.calculator;

import java.util.List;
import java.util.LinkedList;
import java.text.DecimalFormat;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Calculator {
    /**
     * Evaluate statement represented as string.
     *
     * @param statement mathematical statement containing digits, '.' (dot) as decimal mark,
     *                  parentheses, operations signs '+', '-', '*', '/'<br>
     *                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
     * @return string value containing result of evaluation or null if statement is invalid
     */
    public String evaluate(String statement) {
        if (statement == null || statement.isEmpty()) return null;
        LinkedList <String> tokens = getTokens(statement);
        if ( tokens == null ) return null;

        // A sequential search and an evaluation of the "clean" expressions (without parenthesis)
        // that are enclosed in parenthesis.
        while(tokens.contains(")")) {
            int closeIndex = tokens.indexOf(")");
            int openIndex = closeIndex - 1;
            for ( ; !tokens.get(openIndex).equals("("); openIndex--) {
                if (openIndex < 0) return null; // There is no paired right parenthesis.
            }
            String resultOfEval = evalualeWithoutPars(tokens.subList(openIndex+1, closeIndex));
            if (resultOfEval == null) return null;   // An expression in the parenthesis cannot be evaluated.
            tokens.set(openIndex, resultOfEval);
            tokens.subList(openIndex+1, closeIndex+1).clear();
        }

        String result = evalualeWithoutPars(tokens);
        if (result == null) return null;
        DecimalFormat df = new DecimalFormat("0.####");
        return (df.format(Double.parseDouble(result)));
    }

    // Returns LinkedList<String> with tokens (numbers, valid operators, parenthesis).
    // If statement can't be split to valid tokens returns null.
    private LinkedList<String> getTokens(String statement) {
        LinkedList <String> tokens = new LinkedList<>();
        Pattern p = Pattern.compile("(\\d+(\\.\\d+)?|[)(]|[-+*\\/])");
        Matcher m = p.matcher(statement);
        int pos = 0;
        while (m.find()) {
            if (m.start() != pos) return null;    // Invalid format of the token.
            tokens.add(m.group());
            pos = m.end();
        }
        if (pos != statement.length()) return null;   // Invalid format of the token.
        return tokens;
    }

    // Returns the result of an expression evaluation that doesn't contain any parenthesis.
    private String evalualeWithoutPars(List<String> tokensWithoutPars) {
        if (tokensWithoutPars == null || tokensWithoutPars.isEmpty()) return null;
        LinkedList<String> tokens = new LinkedList<>(tokensWithoutPars);
        // A mathematical expression can start with unary "-" or "+", e.g. "-3*7+4".
        // Next if-statement has written for correct handling of this case.
        if (tokens.get(0).matches("[-+]")) {
            tokens.set(1, tokens.get(0) + tokens.get(1));
            tokens.remove(0);
        }
        if (tokens.size()%2 == 0) return null;  // Since now all operators in the token list are binary
                                                // there must be odd number of tokens in the list.
        double res;
        if (tokens.size() == 1) return tokens.get(0);
        // This for-loop used for calculation of multiplication and division operations according to operator precedence.
        for (int i = 0; i < tokens.size() - 1; ) {
            switch (tokens.get(i + 1)) {
                case "*":
                    res = Double.parseDouble(tokens.get(i)) * Double.parseDouble(tokens.get(i + 2));
                    break;
                case "/":
                    double divisor = Double.parseDouble(tokens.get(i + 2));
                    if (divisor == 0) return null;
                    res = Double.parseDouble(tokens.get(i)) / Double.parseDouble(tokens.get(i + 2));
                    break;
                default:
                    i += 2;
                    continue;
            }
            tokens.set(i, String.valueOf(res));
            tokens.subList(i+1,i+3).clear();
        }
        if (tokens.size() == 1) return tokens.get(0);
        // This for-loop used for calculation of addition and subtraction operations according to operator precedence.
        for (int i = 0; i < tokens.size() - 1; ) {
            switch (tokens.get(i + 1)) {
                case "+":
                    res = Double.parseDouble(tokens.get(i)) + Double.parseDouble(tokens.get(i + 2));
                    break;
                case "-":
                    res = Double.parseDouble(tokens.get(i)) - Double.parseDouble(tokens.get(i + 2));
                    break;
                default:
                    return null;
            }
            tokens.set(i, String.valueOf(res));
            tokens.subList(i+1,i+3).clear();
        }
        return tokens.get(0);
    }
}
