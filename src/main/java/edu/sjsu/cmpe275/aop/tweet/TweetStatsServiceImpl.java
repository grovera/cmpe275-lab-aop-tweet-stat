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
	public static String mostProductiveUser = null;

	public static Map<String, Integer> productiveUserMap = new TreeMap<String, Integer>();
	public static Map<String, Set<String>> followedUserMap = new HashMap<String, Set<String>>();
	public static Map<String, Set<String>> blockedUserMap = new HashMap<String, Set<String>>();
	public static Map<Integer, String> messageIdUserMap = new HashMap<Integer, String>();
	public static Map<Integer, String> messageRepoMap = new HashMap<Integer, String>();

	public static Map<String, Integer> userRetweetIdMap = new HashMap<String, Integer>();
	public static Map<Integer, Integer> retweetMessageMap = new HashMap<Integer, Integer>();

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



