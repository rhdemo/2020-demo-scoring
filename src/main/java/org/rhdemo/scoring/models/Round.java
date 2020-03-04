package org.rhdemo.scoring.models;

import java.util.List;

public class Round {
    private int id;
    private String name;
    private String description;
    private List<Object> price;
    private List<Integer> choices;
    private List<AnswerFormat> answers;
    private String image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Object> getPrice() {
        return price;
    }

    public void setPrice(List<Object> price) {
        this.price = price;
    }

    public List<AnswerFormat> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerFormat> answers) {
        this.answers = answers;
    }

    public List<Integer> getChoices() {
        return choices;
    }

    public void setChoices(List<Integer> choices) {
        this.choices = choices;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
