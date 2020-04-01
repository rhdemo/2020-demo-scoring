package org.rhdemo.scoring.models;

import java.util.ArrayList;
import java.util.List;

public class GuessResult {
    public enum Answer {
        CORRECT,
        WRONG,
        PENDING
    }

    public GuessResult() {
    }

    public GuessResult(List<Object> submittedGuess) {
        this.submittedGuess = new ArrayList<>(submittedGuess);
        this.answer = new ArrayList<>(submittedGuess.size());
        for (int i = 0; i < submittedGuess.size(); i++) this.answer.add(Answer.PENDING);
    }

    private List<Object> submittedGuess;
    private List<Answer> answer;

    public List<Object> getSubmittedGuess() {
        return submittedGuess;
    }

    public void setSubmittedGuess(List<Object> submittedGuess) {
        this.submittedGuess = submittedGuess;
    }

    public List<Answer> getAnswer() {
        return answer;
    }

    public void setAnswer(List<Answer> answer) {
        this.answer = answer;
    }
}
