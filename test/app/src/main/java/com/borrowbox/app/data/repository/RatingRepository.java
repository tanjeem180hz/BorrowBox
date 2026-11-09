package com.borrowbox.app.data.repository;

import androidx.lifecycle.LiveData;
import com.borrowbox.app.model.Rating;
import java.util.List;

public interface RatingRepository {
    LiveData<List<Rating>> getRatingsForUserLive(long userId);
    void insertRating(Rating rating);
}
