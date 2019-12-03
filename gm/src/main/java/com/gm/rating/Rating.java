package com.gm.rating;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gm.subscription.Subscription;
import org.apache.commons.collections4.queue.CircularFifoQueue;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

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
