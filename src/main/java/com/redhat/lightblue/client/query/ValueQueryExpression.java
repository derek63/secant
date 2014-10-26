package com.redhat.lightblue.client.query;

import com.redhat.lightblue.client.enums.ExpressionOperation;

/**
 * Created by bmiller on 10/10/14.
 */
public class ValueQueryExpression implements QueryExpression {
    private String field;
    private String rValue;
    private String operator;

    public ValueQueryExpression(String expression) {
        String[] parts = expression.split("\\s");
        field = parts[0];
        operator = parts[1];
        rValue = parts[2];
    }

    public ValueQueryExpression(String field, ExpressionOperation operation, String rValue) {
        this(field + " " + operation.toString() + " " + rValue);
    }

    public String toJson() {
        StringBuilder json = new StringBuilder("{\"field\":");
        json.append("\"").append(field).append("\",");
        json.append("\"op\":");
        json.append("\"").append(operator).append("\",");
        json.append("\"rvalue\":");
        json.append("\"").append(rValue).append("\"");
        json.append("}");
        return json.toString();
    }

    @Override
    public String toString() {
        return toJson();
    }

    public static ValueQueryExpression withValue(String expression){
        return new ValueQueryExpression(expression);
    }
}
