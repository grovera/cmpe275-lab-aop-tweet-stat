package edu.sjsu.cmpe275.aop.tweet.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.aspectj.lang.annotation.Around;

import java.io.IOException;
import java.lang.reflect.Method;

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
		System.out.println("ENTERED AROUND$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
		System.out.println("DEFAULT try");
		while(retryCount <= 3)
		{
			try {
				method.invoke(joinPoint.getTarget(), joinPoint.getArgs());
				retryCount=0;
				break;
			}
			catch (Throwable e) {
				retryCount+=1;
				System.out.println("NETWORK ERROR !!!!!!!!!!!!!!!!");
				if(retryCount > 3)
				{
					//e.printStackTrace();
					retryCount=0;
					throw new IOException("All 3 retries failed!");
				}
				System.out.println("Try # " + retryCount);
			}
		}
		System.out.println("EXITED AROUND XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		return joinPoint.proceed();
	}
}
