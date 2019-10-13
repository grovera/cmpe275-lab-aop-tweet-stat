package edu.sjsu.cmpe275.aop.tweet;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.security.AccessControlException;

public class App {
    public static void main(String[] args) {
        /***
         * Following is a dummy implementation of App to demonstrate bean creation with Application context.
         * You may make changes to suit your need, but this file is NOT part of the submission.
         */

    	ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("context.xml");
        TweetService tweeter = (TweetService) ctx.getBean("tweetService");
        TweetStatsService stats = (TweetStatsService) ctx.getBean("tweetStatsService");

        try {
           /* tweeter.follow("alex", "animesh");
            tweeter.follow("bob", "alex");
            tweeter.follow("bob", "erb");
            tweeter.follow("alex", "Animesh");
            tweeter.follow("alex", "bob");
            int msg0 = tweeter.tweet("alex", "first tweet");
            int msg1 = tweeter.tweet("bob", "first tweet");
            int msg2 = tweeter.tweet("bob", "second tweet");
            tweeter.retweet("bob", msg0);
            tweeter.block("alex", "bob");*/
            //tweeter.follow("sameFollow", "sameFollow");
            //tweeter.block("sameBlock", "sameBlock");

            int msg0 = tweeter.tweet("alex", "first tweet");
            tweeter.follow("bob", "alex");
            int msg1 = tweeter.retweet("alex", msg0);
            tweeter.block("alex", "bob");

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("STATS --- Most productive user: " + stats.getMostProductiveUser());
        System.out.println("STATS --- Most popular user: " + stats.getMostFollowedUser());
        System.out.println("STATS --- Length of the longest tweet: " + stats.getLengthOfLongestTweet());
        System.out.println("STATS --- Most popular message: " + stats.getMostPopularMessage());
        ctx.close();
    }
}
