package com.tap.schoolplatform.models.academic.tasks;

public class Answer {

    private String text;
    private final boolean correct;

    public Answer(String text, boolean correct) {
        this.text = text;
        this.correct = correct;
    }

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    public boolean isCorrect() {
        return correct;
    }

    @Override
    public String toString() {
        return text;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return text.equals(answer.text) && correct == answer.correct;
    }
}
