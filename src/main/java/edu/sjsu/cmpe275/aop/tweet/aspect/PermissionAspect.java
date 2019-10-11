package edu.sjsu.cmpe275.aop.tweet.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;

import edu.sjsu.cmpe275.aop.tweet.TweetStatsServiceImpl;

import java.security.AccessControlException;

@Aspect
@Order(2)
public class PermissionAspect {

    @Before("execution(public int edu.sjsu.cmpe275.aop.tweet.TweetService.tweet(..))")
	public void validateTweet(JoinPoint joinPoint) {
		System.out.printf("Permission check before the execution of the method %s\n", joinPoint.getSignature().getName());
		String tweetingUser = (String) joinPoint.getArgs()[0];
		String message = (String) joinPoint.getArgs()[1];
		if ((message.length() > 140) || tweetingUser == null || tweetingUser.length() == 0 ||  message == null || message.length() == 0) {
			throw new IllegalArgumentException("Arguments are not valid");
		}
	}

	@Before("execution(public int edu.sjsu.cmpe275.aop.tweet.TweetService.retweet(..))")
	public void validateRetweet(JoinPoint joinPoint) {
		System.out.printf("Permission check before the executuion of the metohd %s\n", joinPoint.getSignature().getName());
		String tweetingUser = (String) joinPoint.getArgs()[0];
		int messageId = (Integer) joinPoint.getArgs()[1];
		if (tweetingUser == null || tweetingUser.length() == 0) {
			throw new IllegalArgumentException("Arguments are not valid");
		}
		else {
			String tweetOwner = TweetStatsServiceImpl.messageIdUserMap.get(messageId);
			if(!TweetStatsServiceImpl.messageIdUserMap.keySet().contains(messageId)) {
				throw new AccessControlException("An access control violation was attempted. MessageId provided does not exist.");
			}
			else
			{
				if (((!TweetStatsServiceImpl.followedUserMap.containsKey(tweetOwner) || !TweetStatsServiceImpl.followedUserMap.get(tweetOwner).contains(tweetingUser)) && !tweetOwner.equals(tweetingUser))) {
					throw new AccessControlException("An access control violation was attempted. User does not have access to this Tweet");
				}
				if(TweetStatsServiceImpl.blockedUserMap.containsKey(tweetOwner) && TweetStatsServiceImpl.blockedUserMap.get(tweetOwner).contains(tweetingUser)) {
					throw new AccessControlException("An access control violation was attempted. User if blocked by the Tweet author");
				}
			}
		}
	}

	@Before("execution(public void edu.sjsu.cmpe275.aop.tweet.TweetService.follow(..)) || execution(public void edu.sjsu.cmpe275.aop.tweet.TweetService.block(..))" )
	public void validateFollowAndBlock(JoinPoint joinPoint) {
		System.out.printf("Permission check before the executuion of the metohd %s\n", joinPoint.getSignature().getName());
		String follower = (String) joinPoint.getArgs()[0];
		String followee = (String) joinPoint.getArgs()[1];
		if (follower == null || follower.length() == 0 ||  followee == null || followee.length() == 0 || follower.equals(followee)) {
			throw new IllegalArgumentException("Arguments cannot be null or empty.");
		}
	}
}
