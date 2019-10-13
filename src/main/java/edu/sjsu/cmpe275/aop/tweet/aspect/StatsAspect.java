package edu.sjsu.cmpe275.aop.tweet.aspect;

import edu.sjsu.cmpe275.aop.tweet.TweetStatsServiceImpl;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import java.util.HashSet;
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

		//Update productiveUserMap
		int tempTotalMessageLength = TweetStatsServiceImpl.productiveUserMap.containsKey(tweetingUser) ? TweetStatsServiceImpl.productiveUserMap.get(tweetingUser) : 0;
		TweetStatsServiceImpl.productiveUserMap.put(tweetingUser, (tempTotalMessageLength + tweetedMessage.length()));

		//Update most mostProductiveUser if needed
		if(TweetStatsServiceImpl.mostProductiveUser != null)
		{
			if(!TweetStatsServiceImpl.mostProductiveUser.equals(tweetingUser))
			{
				int x = TweetStatsServiceImpl.productiveUserMap.get(tweetingUser);
				int y = TweetStatsServiceImpl.productiveUserMap.get(TweetStatsServiceImpl.mostProductiveUser);
				if(TweetStatsServiceImpl.productiveUserMap.get(tweetingUser) > TweetStatsServiceImpl.productiveUserMap.get(TweetStatsServiceImpl.mostProductiveUser))
				{
					TweetStatsServiceImpl.mostProductiveUser = tweetingUser;
				}
				//if there is tie
				else if(TweetStatsServiceImpl.productiveUserMap.get(tweetingUser).equals(TweetStatsServiceImpl.productiveUserMap.get(TweetStatsServiceImpl.mostProductiveUser)))
				{
					TweetStatsServiceImpl.mostProductiveUser = TweetStatsServiceImpl.mostProductiveUser.compareTo(tweetingUser) > 0 ? tweetingUser : TweetStatsServiceImpl.mostProductiveUser;
				}
			}
		}
		else{
			TweetStatsServiceImpl.mostProductiveUser = tweetingUser;
		}

		//update messageSharingMap
		updateMessageSharingMap(tweetingUser, (Integer) result);

		//update PopularMessage
		if(TweetStatsServiceImpl.mostPopularMessage == null ||
				(TweetStatsServiceImpl.messageSharingMap.get((Integer) result).size() > TweetStatsServiceImpl.messageSharingMap.get(TweetStatsServiceImpl.mostPopularMessageId).size()) ||
				(TweetStatsServiceImpl.messageSharingMap.get((Integer) result).size() == TweetStatsServiceImpl.messageSharingMap.get(TweetStatsServiceImpl.mostPopularMessageId).size() &&
						TweetStatsServiceImpl.mostPopularMessage.compareTo(tweetedMessage) > 0))
		{
			if(TweetStatsServiceImpl.messageSharingMap.get((Integer) result).size() > 0 ){
				TweetStatsServiceImpl.mostPopularMessage = tweetedMessage;
				TweetStatsServiceImpl.mostPopularMessageId = (Integer) result;
			}
		}
	}

	@AfterReturning(pointcut="execution(public * edu.sjsu.cmpe275.aop.tweet.TweetService.retweet(..))", returning="result")
	public void afterRetweet(JoinPoint joinPoint, Object result) {
		String tweetingUser = (String) joinPoint.getArgs()[0];
		int messageId = (Integer) joinPoint.getArgs()[1];

		//save message data
		TweetStatsServiceImpl.messageIdUserMap.put((Integer) result, tweetingUser);
		if(TweetStatsServiceImpl.retweetedMessageParentMap.containsKey(messageId)){
			TweetStatsServiceImpl.retweetedMessageParentMap.put((Integer) result, TweetStatsServiceImpl.retweetedMessageParentMap.get(messageId));
		}
		else{
			TweetStatsServiceImpl.retweetedMessageParentMap.put((Integer) result, messageId);
		}

		//update messageSharingMap
		updateMessageSharingMap(tweetingUser, TweetStatsServiceImpl.retweetedMessageParentMap.get((Integer) result));

		//update PopularMessage
		if(TweetStatsServiceImpl.messageSharingMap.get(TweetStatsServiceImpl.retweetedMessageParentMap.get((Integer) result)).size() >
				TweetStatsServiceImpl.messageSharingMap.get(TweetStatsServiceImpl.mostPopularMessageId).size() ||
				(TweetStatsServiceImpl.messageSharingMap.get(TweetStatsServiceImpl.retweetedMessageParentMap.get((Integer) result)).size() ==
						TweetStatsServiceImpl.messageSharingMap.get(TweetStatsServiceImpl.mostPopularMessageId).size() &&
						TweetStatsServiceImpl.mostPopularMessage.compareTo(TweetStatsServiceImpl.messageRepoMap.get(TweetStatsServiceImpl.retweetedMessageParentMap.get((Integer) result))) > 0))
		{
			TweetStatsServiceImpl.mostPopularMessage = TweetStatsServiceImpl.messageRepoMap.get(TweetStatsServiceImpl.retweetedMessageParentMap.get((Integer) result));
			TweetStatsServiceImpl.mostPopularMessageId = TweetStatsServiceImpl.retweetedMessageParentMap.get((Integer) result);
		}
	}

	@AfterReturning(pointcut="execution(public * edu.sjsu.cmpe275.aop.tweet.TweetService.follow(..))", returning="result")
	public void afterFollow(JoinPoint joinPoint, Object result) {
		//Add follower to the follower map
		String follower = (String) joinPoint.getArgs()[0];
		String followee = (String) joinPoint.getArgs()[1];
		Set<String> followerList = new HashSet<String>();
		if(TweetStatsServiceImpl.followedUserMap.containsKey(followee))
		{
			followerList.addAll(TweetStatsServiceImpl.followedUserMap.get(followee));
		}
		followerList.add(follower);
		TweetStatsServiceImpl.followedUserMap.put(followee, followerList);

		//Update most mostFollowedUser if needed
		if(TweetStatsServiceImpl.mostFollowedUser != null)
		{
			if(!TweetStatsServiceImpl.mostFollowedUser.equals(followee))
			{
				if(followerList.size() > TweetStatsServiceImpl.followedUserMap.get(TweetStatsServiceImpl.mostFollowedUser).size()){
					TweetStatsServiceImpl.mostFollowedUser = followee;
				}
				//if there is tie
				else if(followerList.size() == TweetStatsServiceImpl.followedUserMap.get(TweetStatsServiceImpl.mostFollowedUser).size()){
					TweetStatsServiceImpl.mostFollowedUser = TweetStatsServiceImpl.mostFollowedUser.compareTo(followee) > 0 ? followee : TweetStatsServiceImpl.mostFollowedUser;
				}
			}
		}
		else{
			TweetStatsServiceImpl.mostFollowedUser = followee;
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
	}

	private void updateMessageSharingMap(String user, int messageId)
	{
		Set<String> sharingEnabledFollowers = new HashSet<String>();
		if(TweetStatsServiceImpl.followedUserMap.containsKey(user)){
			sharingEnabledFollowers.addAll(TweetStatsServiceImpl.followedUserMap.get(user));
			if(TweetStatsServiceImpl.blockedUserMap.containsKey(user)){
				sharingEnabledFollowers.removeAll(TweetStatsServiceImpl.blockedUserMap.get(user));
			}
		}
		//in case of retweet (also add follower which could have been blocked by the original owner of the tweet)
		if(TweetStatsServiceImpl.messageSharingMap.containsKey(messageId))
		{
			sharingEnabledFollowers.addAll(TweetStatsServiceImpl.messageSharingMap.get(messageId));
		}
		//remove original owner if they follow one who retweets
		sharingEnabledFollowers.remove(TweetStatsServiceImpl.messageIdUserMap.get(messageId));

		TweetStatsServiceImpl.messageSharingMap.put(messageId, sharingEnabledFollowers);
	}
}
