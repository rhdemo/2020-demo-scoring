package org.rhdemo.scoring.models;

public class AnswerFormat {
    public static final AnswerFormat NUMBER=new AnswerFormat("number");
    public static final AnswerFormat DECIMAL=new AnswerFormat("decimal");

    private String format;

    public AnswerFormat() {
    }

    public AnswerFormat(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
