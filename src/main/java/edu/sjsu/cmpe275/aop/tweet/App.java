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

            //valid

            /*int msg = tweeter.tweet("alex", "first tweet");
            tweeter.follow("alex", "bob");
            tweeter.retweet("bob", msg);*/

            /*int msg = tweeter.tweet("alex", "first tweet");
            tweeter.retweet("alex", msg);*/

            /*try {
                int msg = tweeter.tweet("alex", "first tweet");
                tweeter.follow("bob", "alex");
                tweeter.retweet("bob", msg);
                System.out.println("Permission Test #9 FAILED! XXXXXX XXXXXXXX XXXXXX XXXXXXX XXXXXXXX XXXXXXX XXXXXXXX XXXXXXX XXXXXX");
            }
            catch(IllegalArgumentException e){
                System.out.println("Permission Test #9 passed");
            }*/

            System.out.println("*******************************TESTING RETRY ASPECT*******************************");
            /*tweeter.follow("alex", "animesh");
            int msg0 = tweeter.tweet("alex", "first tweet");
            int msg1 = tweeter.tweet("bob", "first tweet");
            int msg2 = tweeter.tweet("bob", "second tweet");
            tweeter.follow("bob", "alex");
            tweeter.follow("bob", "erb");
            tweeter.follow("alex", "Animesh");
            tweeter.follow("alex", "bob");
            tweeter.retweet("bob", msg);
            tweeter.block("alex", "bob");*/
            //tweeter.follow("sameFollow", "sameFollow");
            //tweeter.block("sameBlock", "sameBlock");

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("*******************************TESTING STATS ASPECT*******************************");
        try {
            //Test #1
            stats.resetStatsAndSystem();
            int msg0 = tweeter.tweet("alex", "first tweet");
            int msg1 = tweeter.tweet("bob", "first tweet");
            int msg2 = tweeter.tweet("bob", "second tweet");
            int msg3 = tweeter.tweet("Animesh", "This is the longest Tweet.");
            int msg4 = tweeter.tweet("bob", "Third tweet tweet");



            //Test #4
            int msg101 = tweeter.tweet("bob", "bob's most popular tweet");
            int msg102 = tweeter.tweet("ark", "ark's most popular tweet");
            int msg103 = tweeter.tweet("alex", "alex's most popular tweet");
            tweeter.retweet("Erb", msg101);
            //tweeter.retweet("alex", msg2);
            System.out.println("STATS --- Most popular message: " + stats.getMostPopularMessage());
            if(stats.getMostPopularMessage().equals("bob's most popular tweet")){
                System.out.println("Stats Test #4 passed");
            }
            else{
                System.out.println("Stats Test #4 FAILED! XXXXXX XXXXXXXX XXXXXX XXXXXXX XXXXXXXX XXXXXXX XXXXXXXX XXXXXXX XXXXXX");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("STATS --- Most productive user: " + stats.getMostProductiveUser());
        System.out.println("STATS --- Most popular user: " + stats.getMostFollowedUser());
        System.out.println("STATS --- Length of the longest tweet: " + stats.getLengthOfLongestTweet());
        System.out.println("STATS --- Most popular message: " + stats.getMostPopularMessage());
        ctx.close();
    }
}
