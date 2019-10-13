package edu.sjsu.cmpe275.aop.tweet.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;

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
		Object result = null;
		while(retryCount <=3){
			try {
				result = joinPoint.proceed();
				retryCount = 0;
				break;
			} catch (IOException e) {
				retryCount += 1;
				if(retryCount>3){
					System.out.printf("Network Failure! Aborted the executuion of the metohd %s after 3 tries\n", joinPoint.getSignature().getName());
					retryCount = 0;
					throw e;
				}
				System.out.println("Network Failure! Retrying.......  try# " + retryCount);
				continue;
			}
		}
		return result;
	}
}
