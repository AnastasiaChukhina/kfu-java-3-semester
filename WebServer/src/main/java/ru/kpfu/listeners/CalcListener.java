package ru.kpfu.listeners;

import ru.kpfu.exceptions.InvalidRequestException;
import ru.kpfu.exceptions.UnknownActionException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CalcListener extends AbstractServerEventListener {
    private final String QUERY_START_SYMBOL = "?";
    private final String QUERY_END_SYMBOL = " ";
    private final String AMPERSAND = "&";
    private final String METHOD = "GET";

    @Override
    public void handle(String data) throws IOException {
        if (isValidRequest(data) && isValidMethod(data)) {
            String action = checkAction(data);
            Double result;
            if (action != null)
                result = execute(action, data);
            else
                throw new UnknownActionException("Unknown action.");
            this.server.sendResponse(String.format("%s%.3f", "The result is: ", result));
        } else throw new InvalidRequestException("InvalidRequest");
    }

    private String checkAction(String data) {
        List<String> actions = getActions();
        for (String s : actions) {
            if (data.contains("/" + s)) return s;
        }
        return null;
    }

    private Double execute(String action, String data) {
        double[] numbers = parseData(data);
        double num1 = numbers[0];
        double num2 = numbers[1];
        switch (action) {
            case Calculator.SUM:
                return num1 + num2;
            case Calculator.SUBTRACT:
                return num1 - num2;
            case Calculator.MULTIPLY:
                return num1 * num2;
            case Calculator.DIVIDE:
                if (num2 != 0) return num1 / num2;
                else throw new IllegalArgumentException("Denominator can't be equal to zero.");
            default:
                return null;
        }
    }

    private double[] parseData(String data) {
        String[] params = getParams();
        double[] numbers = new double[2];
        int iStart = data.lastIndexOf(QUERY_START_SYMBOL) + 1;
        int iEnd = data.lastIndexOf(QUERY_END_SYMBOL);
        int iDel = data.indexOf(AMPERSAND);
        numbers[0] = parseNumber(data.substring(iStart, iDel++), params[0] + "=");
        numbers[1] = parseNumber(data.substring(iDel, iEnd), params[1] + "=");
        return numbers;
    }

    private String[] getParams() {
        return new String[]{"a", "b"};
    }

    private double parseNumber(String substring, String delimiter) {
        return Double.parseDouble(substring.split(delimiter)[1]);
    }

    private boolean isValidMethod(String data) {
        return data.split(QUERY_END_SYMBOL)[0].equals(METHOD);
    }

    private boolean isValidRequest(String data) {
        return data.contains(QUERY_START_SYMBOL) &&
                data.indexOf(AMPERSAND) == data.lastIndexOf(AMPERSAND);
    }

    private List<String> getActions() {
        List<String> actions = new ArrayList<>();
        actions.add(Calculator.SUM);
        actions.add(Calculator.SUBTRACT);
        actions.add(Calculator.MULTIPLY);
        actions.add(Calculator.DIVIDE);
        return actions;
    }
}
