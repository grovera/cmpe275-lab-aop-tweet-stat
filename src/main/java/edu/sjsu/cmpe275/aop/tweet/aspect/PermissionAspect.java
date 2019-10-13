package edu.sjsu.cmpe275.aop.tweet.aspect;

import edu.sjsu.cmpe275.aop.tweet.TweetStatsServiceImpl;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;

import java.security.AccessControlException;
import java.util.HashSet;
import java.util.Set;

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
			if(!TweetStatsServiceImpl.messageIdUserMap.keySet().contains(messageId)) {
				throw new AccessControlException("An access control violation was attempted. MessageId provided does not exist.");
			}
			else
			{
				String tweetOwner = TweetStatsServiceImpl.messageIdUserMap.get(messageId);
				String tweetOriginalOwner = TweetStatsServiceImpl.retweetedMessageParentMap.containsKey(messageId) ?
							TweetStatsServiceImpl.messageIdUserMap.get(TweetStatsServiceImpl.retweetedMessageParentMap.get(messageId)) : null;
				int parentMessageId = TweetStatsServiceImpl.messageSharingMap.containsKey(messageId) ? messageId : TweetStatsServiceImpl.retweetedMessageParentMap.get(messageId);
				Set<String> currentSharedUsers = new HashSet<String>();
				currentSharedUsers.addAll(TweetStatsServiceImpl.messageSharingMap.get(parentMessageId));
				if(tweetOriginalOwner != null){
					currentSharedUsers.add(tweetOriginalOwner);
				}
				if (!currentSharedUsers.contains(tweetingUser) && !tweetOwner.equals(tweetingUser)) {
					throw new AccessControlException("An access control violation was attempted. User does not have access to this Tweet");
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
