package edu.sjsu.cmpe275.aop.tweet;

import java.io.IOException;
import java.security.AccessControlException;

public class TweetServiceImpl implements TweetService {

    /***
     * Following is a dummy implementation.
     * You can tweak the implementation to suit your need, but this file is NOT part of the submission.
     */

    public int tweet(String user, String message) throws IllegalArgumentException, IOException {
		if ((message.length() > 140) || user == null || user.isEmpty() ||  message == null || message.isEmpty()) {
			throw new IllegalArgumentException();
		}
    	System.out.printf("User %s tweeted message: %s\n", user, message);
    	return 0;
    }

    public void follow(String follower, String followee) throws IOException {
       	System.out.printf("User %s followed user %s \n", follower, followee);
    }

	public void block(String user, String follower) throws IOException {
       	System.out.printf("User %s blocked user %s \n", user, follower);		
	}

	public int retweet(String user, int messageId)
			throws AccessControlException, IllegalArgumentException, IOException {
		if (user == null || user.isEmpty()) {
			throw new IllegalArgumentException();
		}

		return 0;
	}

}
