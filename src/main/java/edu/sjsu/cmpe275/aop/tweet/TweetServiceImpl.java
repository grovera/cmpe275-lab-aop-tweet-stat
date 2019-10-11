package edu.sjsu.cmpe275.aop.tweet;

import java.io.IOException;
import java.security.AccessControlException;
import java.util.Random;

public class TweetServiceImpl implements TweetService {

    /***
     * Following is a dummy implementation.
     * You can tweak the implementation to suit your need, but this file is NOT part of the submission.
     */
    private int x = 1;

    public int tweet(String user, String message) throws IllegalArgumentException, IOException {
		if ((message.length() > 140) || user == null || user.length() == 0 ||  message == null || message.length() == 0) {
			throw new IllegalArgumentException();
		}
		int temp = getMessageId();
		if(temp == 0){
			throw new IOException("test exception");
		}
		else {
			System.out.printf("User %s tweeted message: %s\n", user, message);
			return temp;
		}
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
		System.out.printf("User %s retweeted message.\n", user);

		return 0;
	}

	private int getMessageId() {
		int temp = x;
		x++;

		Random rand = new Random();
		int n = rand.nextInt(10);
		if(n%2==1){
			//return 0;
			return temp;
		}
		else {
			return temp;
		}
	}
}
