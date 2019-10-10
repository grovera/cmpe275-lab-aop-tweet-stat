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
		System.out.printf("Permission check before the executuion of the metohd %s\n", joinPoint.getSignature().getName());
		String tweetingUser = (String) joinPoint.getArgs()[0];
		String message = (String) joinPoint.getArgs()[1];
		if ((message.length() > 140) || tweetingUser == null || tweetingUser.length() == 0 ||  message == null || message.length() == 0) {
			throw new IllegalArgumentException();
		}
	}

	@Before("execution(public int edu.sjsu.cmpe275.aop.tweet.TweetService.retweet(..))")
	public void validateRetweet(JoinPoint joinPoint) {
		System.out.printf("Permission check before the executuion of the metohd %s\n", joinPoint.getSignature().getName());
		String tweetingUser = (String) joinPoint.getArgs()[0];
		int messageId = (Integer) joinPoint.getArgs()[1];
		if (tweetingUser == null || tweetingUser.length() == 0) {
			throw new IllegalArgumentException();
		}
		else {
			String tweetOwner = TweetStatsServiceImpl.messageIdUserMap.get(messageId);
			boolean isAccessControlException = false;
			if(!TweetStatsServiceImpl.messageIdUserMap.keySet().contains(messageId)) {
				isAccessControlException = true;
			}
			else
			{
				if ((!TweetStatsServiceImpl.followedUserMap.get(tweetOwner).contains(tweetingUser) && !tweetOwner.equals(tweetingUser))) {
					isAccessControlException = true;
				}
				if(TweetStatsServiceImpl.blockedUserMap.containsKey(tweetOwner) && TweetStatsServiceImpl.blockedUserMap.get(tweetOwner).contains(tweetingUser)) {
					isAccessControlException = true;
				}
			}

			/*System.out.println(tweetOwner+"----"+tweetingUser);
			System.out.println(TweetStatsServiceImpl.followedUserMap.get(tweetOwner).toString());
			System.out.println(!TweetStatsServiceImpl.messageIdUserMap.keySet().contains(messageId) );
			System.out.println((!TweetStatsServiceImpl.followedUserMap.get(tweetOwner).contains(tweetingUser) && !tweetOwner.equals(tweetingUser)));
			System.out.println(TweetStatsServiceImpl.blockedUserMap.containsKey(tweetOwner) && TweetStatsServiceImpl.blockedUserMap.get(tweetOwner).contains(tweetingUser));*/
			if(isAccessControlException){
				throw new AccessControlException("An access control violation was attempted.");
			}
		}
	}

	@Before("execution(public void edu.sjsu.cmpe275.aop.tweet.TweetService.follow(..))")
	public void validateFollow(JoinPoint joinPoint) {
		System.out.printf("Permission check before the executuion of the metohd %s\n", joinPoint.getSignature().getName());
		String follower = (String) joinPoint.getArgs()[0];
		String followee = (String) joinPoint.getArgs()[1];
		if (follower == null || follower.length() == 0 ||  followee == null || followee.length() == 0 || follower.equals(followee)) {
			throw new IllegalArgumentException();
		}
	}

	@Before("execution(public void edu.sjsu.cmpe275.aop.tweet.TweetService.block(..))")
	public void validateBlock(JoinPoint joinPoint) {
		System.out.printf("Permission check before the executuion of the metohd %s\n", joinPoint.getSignature().getName());
		String user = (String) joinPoint.getArgs()[0];
		String followee = (String) joinPoint.getArgs()[1];
		if (user == null || user.length() == 0 ||  followee == null || followee.length() == 0 || user.equals(followee)) {
			throw new IllegalArgumentException();
		}
	}
}
