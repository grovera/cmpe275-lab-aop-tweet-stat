package edu.sjsu.cmpe275.aop.tweet;

import java.io.IOException;
import java.security.AccessControlException;

public class TweetServiceImpl implements TweetService {

    /***
     * Following is a dummy implementation.
     * You can tweak the implementation to suit your need, but this file is NOT part of the submission.
     */

	@Override
    public int tweet(String user, String message) throws IllegalArgumentException, IOException {
    	System.out.printf("User %s tweeted message: %s\n", user, message);
    	return 0;
    }

	@Override
    public void follow(String follower, String followee) throws IOException {
       	System.out.printf("User %s followed user %s \n", follower, followee);
    }

	@Override
	public void block(String user, String follower) throws IOException {
       	System.out.printf("User %s blocked user %s \n", user, follower);		
	}

	@Override
	public int retweet(String user, int messageId)
			throws AccessControlException, IllegalArgumentException, IOException {
		// TODO Auto-generated method stub
		return 0;
	}

}
