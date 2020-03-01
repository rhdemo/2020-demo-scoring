package org.rhdemo.scoring;

public class Answer extends AnswerFormat {
    private String result;
    private Object number;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Object getNumber() {
        return number;
    }

    public void setNumber(Object number) {
        this.number = number;
    }
}
