package edu.sjsu.cmpe275.aop.tweet;

import java.util.*;

public class TweetStatsServiceImpl implements TweetStatsService {
    /***
     * Following is a dummy implementation.
     * You are expected to provide an actual implementation based on the requirements.
     */

    public static int lengthOfLongestTweet = 0;
	public static String mostFollowedUser = null;
	public static String mostPopularMessage = null;
	//public static int mostPopularMessageId = 0;
	public static String mostProductiveUser = null;

	public static Map<String, Integer> productiveUserMap = new TreeMap<String, Integer>();
	public static Map<String, Set<String>> followedUserMap = new HashMap<String, Set<String>>();
	public static Map<String, Set<String>> blockedUserMap = new HashMap<String, Set<String>>();
	public static Map<Integer, Set<String>> messageSharingMap = new HashMap<Integer, Set<String>>();
	//map to store returned message id and tweeting user (could be retweet also)
	public static Map<Integer, String> messageIdUserMap = new HashMap<Integer, String>();
	public static Map<Integer, String> messageRepoMap = new HashMap<Integer, String>();

	//map to link retweeted message id to the top parent message id
	public static Map<Integer, Integer> retweetedMessageParentMap = new HashMap<Integer, Integer>();

	//public static Map<Integer, Integer> messageFollowerMap = new HashMap<Integer, Integer>();
	//public static Map<Integer, Integer> retweetOriginalOwnerMap = new HashMap<Integer, Integer>();

	public void resetStatsAndSystem() {
		lengthOfLongestTweet = 0;
		mostFollowedUser = null;
		mostPopularMessage = null;
		mostProductiveUser = null;
		if(!productiveUserMap.isEmpty()){
			productiveUserMap.clear();
		}
		if(!followedUserMap.isEmpty()){
			followedUserMap.clear();
		}
		if(!blockedUserMap.isEmpty()){
			blockedUserMap.clear();
		}
	}

	public int getLengthOfLongestTweet() {
		return lengthOfLongestTweet;
	}

	public String getMostFollowedUser() {
		return mostFollowedUser;
	}

	public String getMostPopularMessage() {
		return mostPopularMessage;
	}

	public String getMostProductiveUser() {
		return mostProductiveUser;
	}
}



