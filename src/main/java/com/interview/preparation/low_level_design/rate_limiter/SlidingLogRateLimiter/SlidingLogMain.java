package com.interview.preparation.low_level_design.rate_limiter.SlidingLogRateLimiter;

import com.interview.preparation.low_level_design.movie_ticket_booking.model.Theatre;
import com.interview.preparation.low_level_design.rate_limiter.SlidingLogRateLimiter.Repository.BucketCreatorRepository;
import com.interview.preparation.low_level_design.rate_limiter.SlidingLogRateLimiter.Service.BucketCreatorService;
import com.interview.preparation.low_level_design.rate_limiter.exception.UserNotFoundException;
import com.interview.preparation.low_level_design.rate_limiter.model.User;
import com.interview.preparation.low_level_design.rate_limiter.model.UserProfile;

public class SlidingLogMain {
    private static BucketCreatorRepository bucketCreatorRepository;
    private static BucketCreatorService bucketCreatorService;

    public static void main(String[] args) throws UserNotFoundException, InterruptedException {
        bucketCreatorRepository = new BucketCreatorRepository();
        bucketCreatorService = new BucketCreatorService(bucketCreatorRepository);

        User user1 = new User(new UserProfile("akashgupta9578@gmail.com","akash","gupta"));
        bucketCreatorService.createUserBucket(user1.getId(), 100 ,2);

        bucketCreatorService.accessApplication(user1.getId());
        bucketCreatorService.accessApplication(user1.getId());
        bucketCreatorService.accessApplication(user1.getId());
        bucketCreatorService.accessApplication(user1.getId());

    }
}