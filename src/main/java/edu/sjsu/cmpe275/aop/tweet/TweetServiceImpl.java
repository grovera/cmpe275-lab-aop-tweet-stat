package edu.sjsu.cmpe275.aop.tweet;

import java.io.IOException;
import java.security.AccessControlException;
import java.util.Random;

public class TweetServiceImpl implements TweetService {

    /***
     * Following is a dummy implementation.
     * You can tweak the implementation to suit your need, but this file is NOT part of the submission.
     */
    private int x = 0;

    public int tweet(String user, String message) throws IllegalArgumentException, IOException {
		if ((message.length() > 140) || user == null || user.length() == 0 ||  message == null || message.length() == 0) {
			throw new IllegalArgumentException();
		}
    	System.out.printf("User %s tweeted message: %s\n", user, message);
		int temp = x;
		x++;
		return temp;
    }

    public void follow(String follower, String followee) throws IOException {
       	System.out.printf("User %s followed user %s \n", follower, followee);
    }

	public void block(String user, String follower) throws IOException {
       	System.out.printf("User %s blocked user %s \n", user, follower);		
	}

	public int retweet(String user, int messageId)
			throws AccessControlException, IllegalArgumentException, IOException {
		if (user == null || user.length() == 0) {
			throw new IllegalArgumentException();
		}

		return 0;
	}

	public int generateRandomNumber() {
		Random rand = new Random();
		int n = rand.nextInt(500000);
		return n;
	}
}
