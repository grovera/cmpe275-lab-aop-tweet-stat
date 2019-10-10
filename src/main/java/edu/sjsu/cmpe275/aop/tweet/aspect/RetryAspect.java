package edu.sjsu.cmpe275.aop.tweet.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.aspectj.lang.annotation.Around;

import java.io.IOException;

@Aspect
@Order(1)
public class RetryAspect {

	private static int retryCount = 0;
    /***
     * Following is a dummy implementation of this aspect.
     * You are expected to provide an actual implementation based on the requirements, including adding/removing advices as needed.
     * @throws Throwable 
     */

    @Around("execution(public * edu.sjsu.cmpe275.aop.tweet.TweetService.*(..))")
	public Object ifNetworkFailure(ProceedingJoinPoint joinPoint) throws Throwable {
		try {
			Object result = joinPoint.proceed();
			retryCount=0;
			return result;
		}
		catch (IOException a) {
			retryCount+=1;
			System.out.println("Try # " + retryCount);
			try {
				Object result = joinPoint.proceed();
				retryCount=0;
				return result;
			}
			catch (IOException b) {
				retryCount+=1;
				System.out.println("Try # " + retryCount);
				try {
					Object result = joinPoint.proceed();
					retryCount=0;
					return result;
				}
				catch (IOException c) {
					retryCount+=1;
					System.out.println("Try # " + retryCount);
					try {
						Object result = joinPoint.proceed();
						retryCount=0;
						return result;
					}
					catch (IOException e) {
						retryCount=0;
						e.printStackTrace();
						throw new IOException("All 3 retries failed!");
					}
				}
			}
		}

    	/*Object result = new Object();
		while(retryCount <= 3)
		{
			try {
				result = joinPoint.proceed();
				retryCount=0;
				return result;
			}
			catch (IOException e) {
				retryCount+=1;
				System.out.println("Try # " + retryCount);
				e.printStackTrace();
				throw new IOException("All 3 retries failed!");
			}
		}
		return result;*/
	}

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
