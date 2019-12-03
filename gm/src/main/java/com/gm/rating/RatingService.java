package com.gm.rating;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    public List<Rating> getAll(){
        List<Rating> ratingList = new ArrayList<>();
        Iterable<Rating> ratings = ratingRepository.findAll();
        for (Rating rating : ratings) {
            ratingList.add(rating);
        }
        return ratingList;
    }

    public Rating create(Rating rating){
        if (rating != null) {
            System.out.println("Creating nem rating: " + rating.getTextRating());
            return ratingRepository.save(rating);
        } else{
            return null;
        }
    }

}
