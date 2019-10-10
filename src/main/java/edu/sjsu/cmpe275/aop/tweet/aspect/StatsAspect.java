package edu.sjsu.cmpe275.aop.tweet.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import edu.sjsu.cmpe275.aop.tweet.TweetStatsServiceImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Aspect
@Order(0)
public class StatsAspect {
    /***
     * Following is a dummy implementation of this aspect.
     * You are expected to provide an actual implementation based on the requirements, including adding/removing advices as needed.
     */

	@Autowired TweetStatsServiceImpl stats;
	
	@AfterReturning(pointcut="execution(public * edu.sjsu.cmpe275.aop.tweet.TweetService.tweet(..))", returning="result")
	public void afterTweet(JoinPoint joinPoint, Object result) {
		//System.out.printf("After the executuion of the metohd %s\n", joinPoint.getSignature().getName());

		String tweetingUser = (String) joinPoint.getArgs()[0];
		String tweetedMessage = (String) joinPoint.getArgs()[1];

		//save message data
		TweetStatsServiceImpl.messageIdUserMap.put((Integer) result, tweetingUser);
		TweetStatsServiceImpl.messageRepoMap.put((Integer) result, tweetedMessage);

		if(tweetedMessage.length() > TweetStatsServiceImpl.lengthOfLongestTweet)
		{
			TweetStatsServiceImpl.lengthOfLongestTweet = tweetedMessage.length();
		}

		//update PopularMessage

		//Update productiveUserMap
		int tempTotalMessageLength = TweetStatsServiceImpl.productiveUserMap.containsKey(tweetingUser) ? TweetStatsServiceImpl.productiveUserMap.get(tweetingUser) : 0;
		TweetStatsServiceImpl.productiveUserMap.put(tweetingUser, (tempTotalMessageLength + tweetedMessage.length()));

		//Update most mostProductiveUser if needed
		if(TweetStatsServiceImpl.mostProductiveUser != null)
		{
			if(!TweetStatsServiceImpl.mostProductiveUser.equals(tweetingUser))
			{
				if(TweetStatsServiceImpl.productiveUserMap.get(tweetingUser) > TweetStatsServiceImpl.productiveUserMap.get(TweetStatsServiceImpl.mostProductiveUser))
				{
					TweetStatsServiceImpl.mostProductiveUser = tweetingUser;
				}
				//if there is tie
				else if(TweetStatsServiceImpl.productiveUserMap.get(tweetingUser) == TweetStatsServiceImpl.productiveUserMap.get(TweetStatsServiceImpl.mostProductiveUser))
				{
					TweetStatsServiceImpl.mostProductiveUser = TweetStatsServiceImpl.mostProductiveUser.compareTo(tweetingUser) > 0 ? tweetingUser : TweetStatsServiceImpl.mostProductiveUser;
				}
			}
		}
		else{
			TweetStatsServiceImpl.mostProductiveUser = tweetingUser;
		}
	}

	@AfterReturning(pointcut="execution(public * edu.sjsu.cmpe275.aop.tweet.TweetService.retweet(..))", returning="result")
	public void afterRetweet(JoinPoint joinPoint, Object result) {
		String tweetingUser = (String) joinPoint.getArgs()[0];
		int messageId = (Integer) joinPoint.getArgs()[1];
		//save message data
		//TweetStatsServiceImpl.userRetweetIdMap.put(tweetingUser, (Integer) result);
		//TweetStatsServiceImpl.retweetMessageMap.put((Integer) result, messageId);

		//update PopularMessage
	}

	@AfterReturning(pointcut="execution(public * edu.sjsu.cmpe275.aop.tweet.TweetService.follow(..))", returning="result")
	public void afterFollow(JoinPoint joinPoint, Object result) {
		//Add followee to the follower map
		String follower = (String) joinPoint.getArgs()[0];
		String followee = (String) joinPoint.getArgs()[1];
		Set<String> followerList = new HashSet<String>();
		if(TweetStatsServiceImpl.followedUserMap.containsKey(follower))
		{
			followerList.addAll(TweetStatsServiceImpl.followedUserMap.get(follower));
		}
		followerList.add(followee);
		TweetStatsServiceImpl.followedUserMap.put(follower, followerList);

		//Update most mostFollowedUser if needed
		if(TweetStatsServiceImpl.mostFollowedUser != null)
		{
			if(!TweetStatsServiceImpl.mostFollowedUser.equals(follower))
			{
				if(followerList.size() > TweetStatsServiceImpl.followedUserMap.get(TweetStatsServiceImpl.mostFollowedUser).size()){
					TweetStatsServiceImpl.mostFollowedUser = follower;
				}
				//if there is tie
				else if(followerList.size() == TweetStatsServiceImpl.followedUserMap.get(TweetStatsServiceImpl.mostFollowedUser).size()){
					TweetStatsServiceImpl.mostFollowedUser = TweetStatsServiceImpl.mostFollowedUser.compareTo(follower) > 0 ? follower : TweetStatsServiceImpl.mostFollowedUser;
				}
			}
		}
		else{
			TweetStatsServiceImpl.mostFollowedUser = follower;
		}
	}

	@AfterReturning(pointcut="execution(public * edu.sjsu.cmpe275.aop.tweet.TweetService.block(..))", returning="result")
	public void afterBlock(JoinPoint joinPoint, Object result) {
		//Add followee to the block map
		String user = (String) joinPoint.getArgs()[0];
		String followee = (String) joinPoint.getArgs()[1];
		Set<String> blockList = new HashSet<String>();
		if(TweetStatsServiceImpl.blockedUserMap.containsKey(user))
		{
			blockList.addAll(TweetStatsServiceImpl.blockedUserMap.get(user));
		}
		blockList.add(followee);
		TweetStatsServiceImpl.blockedUserMap.put(user, blockList);

		//
		/*for (int id: TweetStatsServiceImpl.messageIdUserMap.keySet()){
			String value = TweetStatsServiceImpl.messageIdUserMap.get(id);
			System.out.println(id + " " + value);
		}*/
	}

}
