package edu.sjsu.cmpe275.aop;

import edu.sjsu.cmpe275.aop.tweet.TweetService;
import edu.sjsu.cmpe275.aop.tweet.TweetStatsService;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.security.AccessControlException;

public class TweetTest {

    /***
     * These are dummy test cases. You may add test cases based on your own need.
     */

    ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("context.xml");
    TweetService tweeter = (TweetService) ctx.getBean("tweetService");
    TweetStatsService stats = (TweetStatsService) ctx.getBean("tweetStatsService");

    @Test(expected = IllegalArgumentException.class)
    public void test_PA_tweet_exceedCharLimit() throws IOException {
        stats.resetStatsAndSystem();
        int msg = tweeter.tweet("alex", "The two Giuliani-linked defendants, Igor Fruman and Lev Parnas, were detained at Dulles International Airport outside Washington on Wednesday evening. They were booked on a flight to Frankfurt, Germany, to connect to another flight, according to a law enforcement source.");
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_PA_tweet_emptyMessage() throws IOException {
        stats.resetStatsAndSystem();
        int msg = tweeter.tweet("alex", "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_PA_tweet_nullUser() throws IOException {
        stats.resetStatsAndSystem();
        int msg = tweeter.tweet(null, "first tweet");
    }

    @Test
    public void test_PA_tweet() throws IOException {
        stats.resetStatsAndSystem();
        int msg = tweeter.tweet("alex", "first tweet");
        assertNotNull(msg);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_PA_retweet_nullUser() throws IOException {
        stats.resetStatsAndSystem();
        int msg = tweeter.tweet("alex", "first tweet");
        tweeter.retweet("", 55);
    }

    @Test(expected = AccessControlException.class)
    public void test_PA_retweet_absentMessage() throws IOException {
        stats.resetStatsAndSystem();
        int msg = tweeter.tweet("alex", "first tweet");
        tweeter.retweet("bob", 55);
    }

    @Test(expected = AccessControlException.class)
    public void test_PA_retweet_strangerMessage() throws IOException {
        stats.resetStatsAndSystem();
        int msg = tweeter.tweet("alex", "first tweet");
        tweeter.retweet("bob", msg);
    }

    @Test(expected = AccessControlException.class)
    public void test_PA_retweet_blockedUser() throws IOException {
        stats.resetStatsAndSystem();
        int msg = tweeter.tweet("alex", "first tweet");
        tweeter.follow("bob", "alex");
        tweeter.block("alex", "bob");
        tweeter.retweet("bob", msg);
    }

    @Test
    public void test_PA_retweet() throws IOException {
        stats.resetStatsAndSystem();
        tweeter.follow("bob", "alex");
        int msg = tweeter.tweet("alex", "first tweet");
        tweeter.retweet("bob", msg);
        assertNotNull(msg);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_PA_follow_nullFollower() throws IOException {
        stats.resetStatsAndSystem();
        tweeter.follow(null, "alex");
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_PA_follow_emptyFollowee() throws IOException {
        stats.resetStatsAndSystem();
        tweeter.follow("alex", "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_PA_follow_himself() throws IOException {
        stats.resetStatsAndSystem();
        tweeter.follow("alex", "alex");
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_PA_block_nullFollowee() throws IOException {
        stats.resetStatsAndSystem();
        tweeter.block("bob", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_PA_block_emptyFollowee() throws IOException {
        stats.resetStatsAndSystem();
        tweeter.block("", "bob");
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_PA_block_himself() throws IOException {
        stats.resetStatsAndSystem();
        tweeter.block("bob", "bob");
    }

    @Test(expected = AccessControlException.class)
    public void test_PA_retweet_beforeFollow() throws IOException {
        stats.resetStatsAndSystem();
        int msg = tweeter.tweet("alex", "first tweet");
        tweeter.follow("bob", "alex");
        int msg2 = tweeter.tweet("alex", "second tweet");
        tweeter.block("alex", "bob");
        int msg3 = tweeter.tweet("alex", "Third tweet");
        tweeter.retweet("bob", msg);
    }

    @Test
    public void test_PA_retweet_afterFollowingBeforeBlock() throws IOException {
        stats.resetStatsAndSystem();
        int msg = tweeter.tweet("alex", "first tweet");
        tweeter.follow("bob", "alex");
        int msg2 = tweeter.tweet("alex", "second tweet");
        tweeter.block("alex", "bob");
        int msg3 = tweeter.tweet("alex", "Third tweet");
        assertNotNull(tweeter.retweet("bob", msg2));
    }

    @Test(expected = AccessControlException.class)
    public void test_PA_retweet_afterBlocked() throws IOException {
        stats.resetStatsAndSystem();
        int msg = tweeter.tweet("alex", "first tweet");
        tweeter.follow("bob", "alex");
        int msg2 = tweeter.tweet("alex", "second tweet");
        tweeter.block("alex", "bob");
        int msg3 = tweeter.tweet("alex", "Third tweet");
        tweeter.retweet("bob", msg3);
    }

    @Test
    public void test_PA_retweet_ownMessage() throws IOException {
        stats.resetStatsAndSystem();
        int msg = tweeter.tweet("alex", "first tweet");
        tweeter.follow("bob", "alex");
        int msg2 = tweeter.tweet("alex", "second tweet");
        tweeter.block("alex", "bob");
        int msg3 = tweeter.tweet("alex", "Third tweet");
        assertNotNull(tweeter.retweet("alex", msg2));
    }

    @Test
    public void test_stats_reset() throws IOException {
        stats.resetStatsAndSystem();
        assertEquals(0, stats.getLengthOfLongestTweet());
        assertNull(stats.getMostFollowedUser());
        assertNull(stats.getMostPopularMessage());
        assertNull(stats.getMostProductiveUser());
    }

    @Test
    public void test_stats_lengthOfLongestTweet() throws IOException {
        stats.resetStatsAndSystem();
        int msg0 = tweeter.tweet("alex", "first tweet");
        int msg1 = tweeter.tweet("bob", "first tweet");
        int msg2 = tweeter.tweet("bob", "second tweet");
        int msg3 = tweeter.tweet("Animesh", "This is the longest Tweet.");
        int msg4 = tweeter.tweet("bob", "Third tweet tweet");
        assertEquals(26, stats.getLengthOfLongestTweet());
    }

    @Test
    public void test_stats_mostFollowedUser() throws IOException {
        stats.resetStatsAndSystem();
        tweeter.follow("bob", "alex");
        tweeter.follow("Animesh", "alex");
        tweeter.follow("Erb", "bob");
        tweeter.follow("Manohar", "ark");
        assertEquals("alex", stats.getMostFollowedUser());
    }

    @Test
    public void test_stats_mostFollowedUserWithTie() throws IOException {
        stats.resetStatsAndSystem();
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
        assertEquals("ark", stats.getMostFollowedUser());
    }

    @Test
    public void test_stats_mostFollowedUserWithBlockedFollower() throws IOException {
        stats.resetStatsAndSystem();
        tweeter.follow("bob", "alex");
        tweeter.follow("Animesh", "alex");
        tweeter.follow("Erb", "bob");
        tweeter.follow("Manohar", "ark");
        tweeter.follow("Peter", "bob");
        tweeter.follow("Susan", "bob");
        tweeter.block("bob", "Peter");
        tweeter.block("bob", "Erb");
        assertEquals("bob", stats.getMostFollowedUser());
    }

    @Test
    public void test_stats_mostFollowedUserWithNoFollower() throws IOException {
        stats.resetStatsAndSystem();
        int msg0 = tweeter.tweet("alex", "first tweet");
        int msg1 = tweeter.tweet("bob", "first tweet");
        int msg2 = tweeter.tweet("bob", "second tweet");
        assertNull(stats.getMostFollowedUser());
    }

    @Test
    public void test_stats_mostPopularMessage() throws IOException {
        stats.resetStatsAndSystem();
        tweeter.follow("Animesh", "bob");
        tweeter.follow("Erb", "ark");
        tweeter.follow("Animesh", "ark");
        tweeter.follow("Erb", "alex");
        int msg101 = tweeter.tweet("bob", "bob's most popular tweet");
        int msg102 = tweeter.tweet("ark", "ark's most popular tweet");
        int msg103 = tweeter.tweet("alex", "alex's most popular tweet");
        assertEquals("ark's most popular tweet", stats.getMostPopularMessage());
    }

    @Test
    public void test_stats_mostPopularMessageWithRetweets() throws IOException {
        /*stats.resetStatsAndSystem();
        tweeter.follow("Animesh", "bob");
        tweeter.follow("Erb", "ark");
        tweeter.follow("Animesh", "ark");
        tweeter.follow("Erb", "alex");
        int msg101 = tweeter.tweet("bob", "bob's most popular tweet");
        int msg102 = tweeter.tweet("ark", "ark's most popular tweet");
        int msg103 = tweeter.tweet("alex", "alex's most popular tweet");
        assertEquals("ark's most popular tweet", stats.getMostPopularMessage());*/
    }

    @Test
    public void test_stats_mostPopularMessageWithCircularRetweeets() throws IOException {
        /*stats.resetStatsAndSystem();
        tweeter.follow("Animesh", "bob");
        tweeter.follow("Erb", "ark");
        tweeter.follow("Animesh", "ark");
        tweeter.follow("Erb", "alex");
        int msg101 = tweeter.tweet("bob", "bob's most popular tweet");
        int msg102 = tweeter.tweet("ark", "ark's most popular tweet");
        int msg103 = tweeter.tweet("alex", "alex's most popular tweet");
        assertEquals("ark's most popular tweet", stats.getMostPopularMessage());*/
    }

    @Test
    public void test_stats_mostProductiveUser() throws IOException {
        stats.resetStatsAndSystem();
        int msg0 = tweeter.tweet("alex", "first tweet");
        int msg1 = tweeter.tweet("bob", "first tweet");
        int msg2 = tweeter.tweet("bob", "second tweet");
        int msg3 = tweeter.tweet("Animesh", "This is the longest Tweet.");
        int msg4 = tweeter.tweet("bob", "Third tweet tweet");
        assertEquals("bob", stats.getMostProductiveUser());
    }

    @Test
    public void test_stats_mostProductiveUserWithTie() throws IOException {
        stats.resetStatsAndSystem();
        int msg0 = tweeter.tweet("alex", "first tweet");
        int msg1 = tweeter.tweet("coby", "first tweet");
        int msg2 = tweeter.tweet("bob", "first tweet");
        int msg3 = tweeter.tweet("Animesh", "This is the longest Tweet.");
        int msg4 = tweeter.tweet("coby", "Third tweet tweet");
        int msg5 = tweeter.tweet("bob", "Third tweet tweet");
        assertEquals("bob", stats.getMostProductiveUser());
    }

    @Test
    public void test_stats_mostProductiveUserWithNoTweet() throws IOException {
        stats.resetStatsAndSystem();
        tweeter.follow("bob", "alex");
        tweeter.follow("Animesh", "alex");
        tweeter.follow("Erb", "bob");
        assertNull(stats.getMostProductiveUser());
    }

    @Test
    public void test_stats_mostProductiveUserWithRetweet() throws IOException {
        stats.resetStatsAndSystem();
        int msg0 = tweeter.tweet("alex", "first tweet");
        int msg1 = tweeter.tweet("coby", "first tweet");
        int msg2 = tweeter.tweet("bob", "first tweet");
        tweeter.follow("bob", "Animesh");
        int msg3 = tweeter.tweet("Animesh", "This is the longest Tweet.");
        int msg4 = tweeter.tweet("coby", "Third tweet tweet");
        int msg5 = tweeter.retweet("bob", msg3);
        assertEquals("coby", stats.getMostProductiveUser());
    }

    /*@Test
    public void test_retry_tweet() throws IOException {
        stats.resetStatsAndSystem();
        int msg0 = tweeter.tweet("alex", "first tweet");
        int msg1 = tweeter.tweet("coby", "first tweet");
        int msg2 = tweeter.tweet("bob", "first tweet");
        tweeter.follow("bob", "Animesh");
        int msg3 = tweeter.tweet("Animesh", "This is the longest Tweet.");
        int msg4 = tweeter.tweet("coby", "Third tweet tweet");
        int msg5 = tweeter.retweet("bob", msg3);
        assertEquals("coby", stats.getMostProductiveUser());
    }*/
}