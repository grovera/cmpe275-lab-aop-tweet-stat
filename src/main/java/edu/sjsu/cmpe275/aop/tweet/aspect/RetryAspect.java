package edu.sjsu.cmpe275.aop.tweet.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.aspectj.lang.annotation.Around;

@Aspect
@Order(1)
public class RetryAspect {

	private static int retryCount = 0;
    /***
     * Following is a dummy implementation of this aspect.
     * You are expected to provide an actual implementation based on the requirements, including adding/removing advices as needed.
     * @throws Throwable 
     */

    /*@AfterThrowing(pointcut="execution(public * edu.sjsu.cmpe275.aop.tweet.TweetService.*(..))", throwing = "error")
	public void ifNetworkFailure(ProceedingJoinPoint joinPoint, Throwable error) throws Throwable {
		try {
			if(error.toString().equalsIgnoreCase("java.io.IOException"))
			{
				System.out.println("Network Failure! retrying.....");
				retryCount+=1;
				if(retryCount <= 3)
				{
					System.out.println("Try # " + retryCount);
					//joinPoint.getTarget().getClass().getName();
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
			System.out.printf("Aborted the executuion of the metohd %s\n", joinPoint.getSignature().getName());
			throw e;
		}
	}*/

	/*@Around("execution(public int edu.sjsu.cmpe275.aop.tweet.TweetService.*tweet(..))")
	public int dummyAdviceOne(ProceedingJoinPoint joinPoint) throws Throwable {
		System.out.printf("Prior to the executuion of the metohd %s\n", joinPoint.getSignature().getName());
		Integer result = null;
		try {
			result = (Integer) joinPoint.proceed();
			System.out.printf("Finished the executuion of the metohd %s with result %s\n", joinPoint.getSignature().getName(), result);
		} catch (Throwable e) {
			e.printStackTrace();
			System.out.printf("Aborted the executuion of the metohd %s\n", joinPoint.getSignature().getName());
			throw e;
		}
		return result.intValue();
	}*/

}
