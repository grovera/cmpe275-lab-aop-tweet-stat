package edu.sjsu.cmpe275.aop.tweet;

public interface TweetStatsService {
	// Please do NOT change this file.

	/**
	 * Reset all the four measurements. For purpose of this lab, it also resets the
	 * following and blocking records as if the system is starting fresh for any
	 * purpose related to the metrics below.
	 */
	void resetStatsAndSystem();

	/**
	 * @returns the length of longest message a user successfully sent since the
	 *          beginning or last reset. If no messages were successfully tweeted,
	 *          return 0.
	 */
	int getLengthOfLongestTweet();

	/**
	 * @returns the user who is being followed by the biggest number of different
	 *          users since the beginning or last reset. If there is a tie, return
	 *          the 1st of such users based on alphabetical order. If any follower
	 *          has been blocked by the followee, this follower Still count; i.e.,
	 *          Blocking or not does not affect this metric. If someone follows
	 *          him/herself, it does not count. If no users are followed by
	 *          anybody, return null.
	 */
	String getMostFollowedUser();

	/**
	 * @returns the message that has been shared with the biggest number of
	 *          different followers when it is successfully tweaked. If the same
	 *          message (based on string equality) has been tweeted more than once,
	 *          it is considered as different message in each tweeting for this
	 *          purpose, hence the numbers of followers for different tweeting
	 *          actions will not be added together.
	 */
	String getMostPopularMessage();

	/**
	 * The most productive user is determined by the total length of all the
	 * messages successfully tweeted since the beginning or last reset. If there is
	 * a tie, return the 1st of such users based on alphabetical order. If no users
	 * successfully tweeted, return null. Retweet does not count for this purpose.
	 * 
	 * @returns the most productive user.
	 */
	String getMostProductiveUser();

}