package edu.sjsu.cmpe275.aop.tweet;

import java.io.IOException;
import java.security.AccessControlException;

public interface TweetService {
	// Please do NOT change this file.

	/**
	 * @throws IllegalArgumentException if the message is more than 140 characters
	 *                                  as measured by string length, or any
	 *                                  parameter is null or empty.
	 * @throws IOException              if there is a network failure.
	 * @returns a unique message ID
	 */
	int tweet(String user, String message) throws IllegalArgumentException, IOException;

	/**
	 * Retweets a message with the given message ID. The given user must be
	 * currently successfully following the current sender of the message in order
	 * to retweet it. As a special case, one is allowed to retweet his own message,
	 * which will end of with a different message ID.
	 * 
	 * 
	 * @throws IllegalArgumentException if any parameter is null or empty.
	 * @throws IOException              if there is a network failure.
	 * @throws AccessControlException   if the given user is not following the
	 *                                  sender of the given message, or the sender
	 *                                  has blocked the given user, or the given
	 *                                  message does not exist.
	 * @returns a unique message ID, different from given messageId parameter.
	 */
	int retweet(String user, int messageId) throws AccessControlException, IllegalArgumentException, IOException;

	/**
	 * If Alice follows Bob, and Bob has not blocked Alice before, any future
	 * message that Bob tweets or retweets after this are shared with Alice. If at
	 * any point Bob blocks Alice, the sharing after blocking will be stopped.
	 * 
	 * @throws IllegalArgumentException if either parameter is null or empty, or
	 *                                  when a user attempts to follow himself.
	 * @throws IOException              if there is a network failure.
	 */
	void follow(String follower, String followee) throws IllegalArgumentException, IOException;

	/**
	 * @throws IllegalArgumentException if either parameter is null or empty, or
	 *                                  when a user attempts to block himself.
	 * @throws IOException              if there is a network failure.
	 */
	void block(String user, String followee) throws IOException, IOException;
}
