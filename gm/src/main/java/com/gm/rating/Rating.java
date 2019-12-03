package com.gm.rating;


import javax.persistence.*;

@Entity
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "rating_id")
    private long id;

    @Column(name = "textRating")
    private String textRating;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTextRating() {
        return textRating;
    }

    public void setTextRating(String textRating) {
        this.textRating = textRating;
    }
}
