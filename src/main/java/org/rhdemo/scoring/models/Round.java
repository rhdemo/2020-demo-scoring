package org.rhdemo.scoring.models;

import java.util.LinkedList;
import java.util.List;

public class Round {
    private int id;
    private String version = "1";
    private String name;
    private String description;
    private List<Object> price;
    private List<Integer> choices;
    private List<AnswerFormat> answers;
    private String image;

    public Round() {
    }

    public Round(Round round) {
        this.id = round.id;
        this.version = round.version;
        this.name = round.name;
        this.description = round.description;
        this.price = round.price;
        this.answers = round.answers;
        this.image = round.image;
        this.choices = new LinkedList<>(round.choices);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Round id(int id) {
        this.id = id;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Round name(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Round description(String description) {
        this.description = description;
        return this;
    }

    public List<Object> getPrice() {
        return price;
    }

    public void setPrice(List<Object> price) {
        this.price = price;
    }
    public Round price(Object... prices) {
        this.price = new LinkedList();
        for (Object p : prices) this.price.add(p);
        return this;
    }

    public List<AnswerFormat> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerFormat> answers) {
        this.answers = answers;
    }
    public Round answers(AnswerFormat... formats) {
        this.answers = new LinkedList<>();
        for (AnswerFormat f : formats) this.answers.add(f);
        return this;
    }

    public List<Integer> getChoices() {
        return choices;
    }

    public void setChoices(List<Integer> choices) {
        this.choices = choices;
    }
    public Round choices(int... picks) {
        this.choices = new LinkedList<>();
        for (int p : picks) this.choices.add(p);
        return this;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public Round image(String image) {
        this.image = image;
        return this;
    }
}
