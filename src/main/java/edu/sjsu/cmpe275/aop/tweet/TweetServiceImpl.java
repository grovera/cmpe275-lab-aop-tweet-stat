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
	private int y = 1;
	private int z = 1;

    public int tweet(String user, String message) throws IllegalArgumentException, IOException {
		/*if ((message.length() > 140) || user == null || user.length() == 0 ||  message == null || message.length() == 0) {
			throw new IllegalArgumentException();
		}*/
		int temp = getMessageId();
		if(temp == 0){
			throw new IOException();
		}
		else {
			System.out.printf("User %s tweeted message: %s\n", user, message);
			return temp;
		}
    }

    public void follow(String follower, String followee) throws IOException {
		int temp = y;
		y++;
		if(temp>=3){
			System.out.printf("User %s followed user %s \n", follower, followee);
			y=1;
		}
		else {
			throw new IOException();
		}
    }

	public void block(String user, String follower) throws IOException {
		int temp = z;
		z++;
		if(temp>=3){
			System.out.printf("User %s blocked user %s \n", user, follower);
			z=1;
		}
		else {
			throw new IOException();
		}
	}

	public int retweet(String user, int messageId)
			throws AccessControlException, IllegalArgumentException, IOException {
		/*if (user == null || user.length() == 0) {
			throw new IllegalArgumentException();
		}*/
		int temp = getMessageId();
		if(temp == 0){
			throw new IOException();
		}
		else {
			System.out.printf("User %s retweeted message.\n", user);
			return temp;
		}
	}

	private int getMessageId() {
		int temp = x;
		x++;

		Random rand = new Random();
		int n = rand.nextInt(10);
		if(x>=5){
			return temp;
		}
		else {
			return 0;
		}
	}
}
