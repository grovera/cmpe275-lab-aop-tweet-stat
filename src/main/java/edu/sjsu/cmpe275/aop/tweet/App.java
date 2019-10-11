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
            System.out.println("*******************************TESTING PERMISSION ASPECT*******************************");
            try {
                int msg = tweeter.tweet("alex", "The two Giuliani-linked defendants, Igor Fruman and Lev Parnas, were detained at Dulles International Airport outside Washington on Wednesday evening. They were booked on a flight to Frankfurt, Germany, to connect to another flight, according to a law enforcement source.");
            }
            catch(IllegalArgumentException e){
                System.out.println("Permission Test #1 passed");
            }
            //Test 1
            try {
                stats.resetStatsAndSystem();
                int msg = tweeter.tweet("alex", "");
            }
            catch(IllegalArgumentException e){
                System.out.println("PermissionTest #2 passed");
            }

            try {
                stats.resetStatsAndSystem();
                int msg = tweeter.tweet(null, "first tweet");
            }
            catch(IllegalArgumentException e){
                System.out.println("Permission Test #3 passed");
            }

            try {
                stats.resetStatsAndSystem();
                int msg = tweeter.tweet("alex", "first tweet");
                tweeter.retweet("", 55);
            }
            catch(IllegalArgumentException e){
                System.out.println("Permission Test #4 passed");
            }

            try {
                stats.resetStatsAndSystem();
                int msg = tweeter.tweet("alex", "first tweet");
                tweeter.retweet("bob", 55);
            }
            catch(AccessControlException e){
                System.out.println("Permission est #5 passed");
            }

            try {
                stats.resetStatsAndSystem();
                int msg = tweeter.tweet("alex", "first tweet");
                tweeter.retweet("bob", msg);
            }
            catch(AccessControlException e){
                System.out.println("Permission Test #6 passed");
            }

            try {
                stats.resetStatsAndSystem();
                int msg = tweeter.tweet("alex", "first tweet");
                tweeter.follow("alex", "bob");
                tweeter.block("alex", "bob");
                tweeter.retweet("bob", msg);
            }
            catch(AccessControlException e){
                System.out.println("Permission Test #7 passed");
            }

            try {
                stats.resetStatsAndSystem();
                tweeter.follow("alex", "");
            }
            catch(IllegalArgumentException e){
                System.out.println("Permission Test #8 passed");
            }

            //valid
            stats.resetStatsAndSystem();
            //int msg = tweeter.tweet("alex", "first tweet");

            /*int msg = tweeter.tweet("alex", "first tweet");
            tweeter.follow("alex", "bob");
            tweeter.retweet("bob", msg);*/

            /*int msg = tweeter.tweet("alex", "first tweet");
            tweeter.retweet("alex", msg);*/

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
            System.out.println("STATS --- Length of the longest tweet: " + stats.getLengthOfLongestTweet());
            if(stats.getLengthOfLongestTweet() == 26){
                System.out.println("Stats Test #1 passed");
            }
            else{
                System.out.println("Stats Test #1 FAILED! XXXXXX XXXXXXXX XXXXXX XXXXXXX XXXXXXXX XXXXXXX XXXXXXXX XXXXXXX XXXXXX");
            }

            //Test #2
            tweeter.follow("alex", "bob");
            tweeter.follow("alex", "Animesh");
            tweeter.follow("bob", "Erb");
            tweeter.follow("alex", "Erb");
            tweeter.follow("bob", "Peter");
            tweeter.follow("bob", "Chris");
            tweeter.follow("bob", "Henry");
            tweeter.follow("Erb", "Peter");
            tweeter.follow("rachael", "Peter");
            tweeter.follow("rachael", "Sussy");
            tweeter.follow("rachael", "Hannah");
            tweeter.follow("rachael", "Monty");
            tweeter.follow("ark", "Peter");
            tweeter.follow("ark", "Sussy");
            tweeter.follow("ark", "Hannah");
            tweeter.follow("ark", "Monty");
            System.out.println("STATS --- Most popular user: " + stats.getMostFollowedUser());
            if(stats.getMostFollowedUser().equals("ark")){
                System.out.println("Stats Test #2 passed");
            }
            else{
                System.out.println("Stats Test #2 FAILED! XXXXXX XXXXXXXX XXXXXX XXXXXXX XXXXXXXX XXXXXXX XXXXXXXX XXXXXXX XXXXXX");
            }

            //Test #3
            System.out.println("STATS --- Most productive user: " + stats.getMostProductiveUser());
            if(stats.getMostProductiveUser().equals("bob")){
                System.out.println("Stats Test #3 passed");
            }
            else{
                System.out.println("Stats Test #3 FAILED! XXXXXX XXXXXXXX XXXXXX XXXXXXX XXXXXXXX XXXXXXX XXXXXXXX XXXXXXX XXXXXX");
            }

            //Test #4
            /*System.out.println("STATS --- Most popular message: " + stats.getMostPopularMessage());
            if(stats.getMostPopularMessage().equals("bob")){
                System.out.println("Stats Test #4 passed");
            }
            else{
                System.out.println("Stats Test #4 FAILED! XXXXXX XXXXXXXX XXXXXX XXXXXXX XXXXXXXX XXXXXXX XXXXXXXX XXXXXXX XXXXXX");
            }*/
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        /*System.out.println("STATS --- Most productive user: " + stats.getMostProductiveUser());
        System.out.println("STATS --- Most popular user: " + stats.getMostFollowedUser());
        System.out.println("STATS --- Length of the longest tweet: " + stats.getLengthOfLongestTweet());
        System.out.println("STATS --- Most popular message: " + stats.getMostPopularMessage());*/
        ctx.close();
    }
}
