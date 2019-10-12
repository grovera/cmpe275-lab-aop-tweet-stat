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

            int msg = tweeter.tweet("alex", "first tweet");
            tweeter.follow("bob", "alex");
            int msg2 = tweeter.tweet("alex", "second tweet");
            tweeter.block("alex", "bob");
            int msg3 = tweeter.tweet("alex", "Third tweet");
            tweeter.retweet("alex", msg3);

            /*System.out.println("*******************************TESTING PERMISSION ASPECT*******************************");
            try {
                int msg = tweeter.tweet("alex", "The two Giuliani-linked defendants, Igor Fruman and Lev Parnas, were detained at Dulles International Airport outside Washington on Wednesday evening. They were booked on a flight to Frankfurt, Germany, to connect to another flight, according to a law enforcement source.");
                System.out.println("Permission Test #1 FAILED! XXXXXX XXXXXXXX XXXXXX XXXXXXX XXXXXXXX XXXXXXX XXXXXXXX XXXXXXX XXXXXX");
            }
            catch(IllegalArgumentException e){
                System.out.println("Permission Test #1 passed");
            }
            try {
                stats.resetStatsAndSystem();
                int msg = tweeter.tweet("alex", "");
                System.out.println("Permission Test #2 FAILED! XXXXXX XXXXXXXX XXXXXX XXXXXXX XXXXXXXX XXXXXXX XXXXXXXX XXXXXXX XXXXXX");
            }
            catch(IllegalArgumentException e){
                System.out.println("PermissionTest #2 passed");
            }

            try {
                stats.resetStatsAndSystem();
                int msg = tweeter.tweet(null, "first tweet");
                System.out.println("Permission Test #3 FAILED! XXXXXX XXXXXXXX XXXXXX XXXXXXX XXXXXXXX XXXXXXX XXXXXXXX XXXXXXX XXXXXX");
            }
            catch(IllegalArgumentException e){
                System.out.println("Permission Test #3 passed");
            }

            try {
                stats.resetStatsAndSystem();
                int msg = tweeter.tweet("alex", "first tweet");
                tweeter.retweet("", 55);
                System.out.println("Permission Test #4 FAILED! XXXXXX XXXXXXXX XXXXXX XXXXXXX XXXXXXXX XXXXXXX XXXXXXXX XXXXXXX XXXXXX");
            }
            catch(IllegalArgumentException e){
                System.out.println("Permission Test #4 passed");
            }

            try {
                stats.resetStatsAndSystem();
                int msg = tweeter.tweet("alex", "first tweet");
                tweeter.retweet("bob", 55);
                System.out.println("Permission Test #5 FAILED! XXXXXX XXXXXXXX XXXXXX XXXXXXX XXXXXXXX XXXXXXX XXXXXXXX XXXXXXX XXXXXX");
            }
            catch(AccessControlException e){
                System.out.println("Permission est #5 passed");
            }

            try {
                stats.resetStatsAndSystem();
                int msg = tweeter.tweet("alex", "first tweet");
                tweeter.retweet("bob", msg);
                System.out.println("Permission Test #6 FAILED! XXXXXX XXXXXXXX XXXXXX XXXXXXX XXXXXXXX XXXXXXX XXXXXXXX XXXXXXX XXXXXX");
            }
            catch(AccessControlException e){
                System.out.println("Permission Test #6 passed");
            }

            try {
                stats.resetStatsAndSystem();
                int msg = tweeter.tweet("alex", "first tweet");
                tweeter.follow("bob", "alex");
                tweeter.block("alex", "bob");
                tweeter.retweet("bob", msg);
                System.out.println("Permission Test #7 FAILED! XXXXXX XXXXXXXX XXXXXX XXXXXXX XXXXXXXX XXXXXXX XXXXXXXX XXXXXXX XXXXXX");
            }
            catch(AccessControlException e){
                System.out.println("Permission Test #7 passed");
            }

            try {
                stats.resetStatsAndSystem();
                tweeter.follow("alex", "");
                System.out.println("Permission Test #8 FAILED! XXXXXX XXXXXXXX XXXXXX XXXXXXX XXXXXXXX XXXXXXX XXXXXXXX XXXXXXX XXXXXX");
            }
            catch(IllegalArgumentException e){
                System.out.println("Permission Test #8 passed");
            }

            //valid
            stats.resetStatsAndSystem();
            //int msg = tweeter.tweet("alex", "first tweet");

            *//*int msg = tweeter.tweet("alex", "first tweet");
            tweeter.follow("alex", "bob");
            tweeter.retweet("bob", msg);*//*

            *//*int msg = tweeter.tweet("alex", "first tweet");
            tweeter.retweet("alex", msg);*//*

            *//*try {
                int msg = tweeter.tweet("alex", "first tweet");
                tweeter.follow("bob", "alex");
                tweeter.retweet("bob", msg);
                System.out.println("Permission Test #9 FAILED! XXXXXX XXXXXXXX XXXXXX XXXXXXX XXXXXXXX XXXXXXX XXXXXXXX XXXXXXX XXXXXX");
            }
            catch(IllegalArgumentException e){
                System.out.println("Permission Test #9 passed");
            }*//*

            System.out.println("*******************************TESTING RETRY ASPECT*******************************");
            *//*tweeter.follow("alex", "animesh");
            int msg0 = tweeter.tweet("alex", "first tweet");
            int msg1 = tweeter.tweet("bob", "first tweet");
            int msg2 = tweeter.tweet("bob", "second tweet");
            tweeter.follow("bob", "alex");
            tweeter.follow("bob", "erb");
            tweeter.follow("alex", "Animesh");
            tweeter.follow("alex", "bob");
            tweeter.retweet("bob", msg);
            tweeter.block("alex", "bob");*//*
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
            tweeter.follow("bob", "alex");
            tweeter.follow("Animesh", "alex");
            tweeter.follow("Erb", "bob");
            tweeter.follow("Erb", "alex");
            tweeter.follow("Peter", "bob");
            tweeter.follow("Chris", "bob");
            tweeter.follow("Hannah", "bob");
            tweeter.follow("Suusy", "Erb");
            tweeter.follow("sarah", "rachael");
            tweeter.follow("mo", "rachael");
            tweeter.follow("Fatima", "rachael");
            tweeter.follow("alex", "rachael");
            tweeter.follow("susan", "ark");
            tweeter.follow("Susan", "ark");
            tweeter.follow("Kirpal", "ark");
            tweeter.follow("Manohar", "ark");
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
            *//*System.out.println("STATS --- Most popular message: " + stats.getMostPopularMessage());
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
