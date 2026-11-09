package com.borrowbox.app.data.repository;

import androidx.lifecycle.LiveData;
import com.borrowbox.app.data.local.RatingDao;
import com.borrowbox.app.model.Rating;
import com.borrowbox.app.util.AppExecutors;
import java.util.List;

public class LocalRatingRepository implements RatingRepository {

    private final RatingDao ratingDao;
    private final AppExecutors executors;

    public LocalRatingRepository(RatingDao ratingDao, AppExecutors executors) {
        this.ratingDao = ratingDao;
        this.executors = executors;
    }

    @Override
    public LiveData<List<Rating>> getRatingsForUserLive(long userId) {
        return ratingDao.getRatingsForUserLive(userId);
    }

    @Override
    public void insertRating(Rating rating) {
        executors.diskIO().execute(() -> ratingDao.insert(rating));
    }
}
