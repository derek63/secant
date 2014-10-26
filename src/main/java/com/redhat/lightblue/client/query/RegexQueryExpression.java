package com.redhat.lightblue.client.query;

/**
 * Created by bmiller on 10/10/14.
 */
public class RegexQueryExpression implements QueryExpression {
    private String fieldName;
    private String pattern;
    private Boolean isCaseInsensitive;
    private Boolean isExtended;
    private Boolean isMultiline;
    private Boolean isDotAll;

    public RegexQueryExpression(String fieldName, String pattern, Boolean isCaseInsensitive, Boolean isExtended, Boolean isMultiline, Boolean isDotAll) {
        this.fieldName = fieldName;
        this.pattern = pattern;
        this.isCaseInsensitive = isCaseInsensitive;
        this.isExtended = isExtended;
        this.isMultiline = isMultiline;
        this.isDotAll = isDotAll;
    }

    public String toJson() {
        StringBuilder json = new StringBuilder("{");
        json.append("\"field\":");
        json.append("\"").append(fieldName).append("\",");
        json.append("\"pattern\":");
        json.append("\"").append(pattern).append("\",");
        json.append("\"caseInsensitive\":");
        json.append("\"").append(isCaseInsensitive).append("\",");
        json.append("\"extended\":");
        json.append("\"").append(isExtended).append("\",");
        json.append("\"multiline\":");
        json.append("\"").append(isMultiline).append("\",");
        json.append("\"dotall\":");
        json.append("\"").append(isDotAll).append("\"");
        json.append("}");

        return json.toString();
    }

    @Override
    public String toString() {
        return this.toJson();
    }
}
