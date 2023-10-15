package org.example;

import java.util.HashMap;

public class human {
    private final HashMap <Integer,Boolean> AnswersOnQuestion; //содержит пару номер вопроса - ответ пользователя
    private int Question; //содержит номер вопроса
    private int count; //количество верных

    public human() {
        this.Question = 1;
        this.count = 0;
        this.AnswersOnQuestion = new HashMap<Integer, Boolean>();
    }

    public HashMap<Integer, Boolean> getAnswersOnQuestion() {
        return AnswersOnQuestion;
    }

    public int getQuestion() {
        return Question;
    }

    public int getCount() {
        return count;
    }

    public void setAnswersOnQuestion(Boolean bool) {
        AnswersOnQuestion.put(this.Question, bool);
    }

    public void setQuestion(int question) {
        Question = question;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void clear(){
        this.count = 0;
        this.Question = 1;
        this.AnswersOnQuestion.clear();
    }
}
