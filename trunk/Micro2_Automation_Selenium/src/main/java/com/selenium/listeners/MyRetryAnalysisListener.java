package com.selenium.listeners;


import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

/**
* @ClassName: MyRetryAnalysisListener
* @Description: TODO
* @author alterhu2020@gmail.com
* @date Feb 21, 2014 5:27:55 PM
* 
* refer:http://stackoverflow.com/questions/7803691/how-to-optimize-testng-and-seleniums-tests
* 
*/

public class MyRetryAnalysisListener implements ITestListener {

	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		//System.out.println("retry method on Started:"+context.getCurrentXmlTest().getName());
	}


	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		//System.out.println("retry method on onTestStart:"+result.getTestContext().getCurrentXmlTest().getName());

	}
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		//System.out.println("retry method on onTestFailedButWithinSuccessPercentage:"+result.getTestContext().getCurrentXmlTest().getName());
	}

	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		Reporter.setCurrentTestResult(result);
	//	String myresult="getTestName:"+result.getTestName()+",getName:"+result.getName()+",getCurrentInvocationCount:"+
		//     result.getMethod().getCurrentInvocationCount()+","+result.getTestContext().getName();
		//boolean status=result.getMethod().getRetryAnalyzer().retry(result);
		//System.out.println("retry method on onTestFailure:,result is:"+myresult);

	}

	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		//System.out.println("retry method on onTestSkipped:"+result.getTestContext().getCurrentXmlTest().getName());
		//result.setStatus(result.FAILURE);
		Reporter.setCurrentTestResult(result);
	}


	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		System.out.println("retry method on onTestSuccess:"+result.getTestContext().getCurrentXmlTest().getName());
	//	result.setStatus(result.SUCCESS);
		Reporter.setCurrentTestResult(result);

	}
	
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
	//	System.out.println("retry method on finished:"+context.getCurrentXmlTest().getName());
		
	/*	for(int i=0;i<context.getAllTestMethods().length;i++){
			if(context.getAllTestMethods()[i].getCurrentInvocationCount()==RetryFail.m_maxRetries+1){
				if (context.getFailedTests().getResults(context.getAllTestMethods()[i]).size() == RetryFail.m_maxRetries+1|| context.getPassedTests().getResults(context.getAllTestMethods()[i]).size() == 1){
					
						context.getFailedTests().removeResult(context.getAllTestMethods()[i]);
				}
			}
		}*/
		
	//	int runmethodcount=context.getFailedTests().size();
		//int passedcount=context.getPassedTests().size();
		//int runtime=runmethodcount+passedcount;
		
		//context.setAttribute(arg0, arg1);
		
		int methodcount=context.getAllTestMethods().length;
		for(int i=0;i<methodcount;i++){
			String output=context.getAllTestMethods()[i].getMethodName()+" ,count is: " +context.getAllTestMethods()[i].getCurrentInvocationCount();
		    System.out.println(output);
		}
		/*
		 Set<ITestResult> all=context.getFailedTests().getAllResults();
		  for(ITestResult result:all){
			   all.remove(result);
		  }*/
		
		/*
		 List<ITestNGMethod> failsToRemove = new ArrayList<ITestNGMethod>();
		 IResultMap failedtests = context.getFailedTests();
		 
		 IResultMap skippedtests = context.getSkippedTests();
		 IResultMap passedtests = context.getPassedTests();

		  for(ITestResult result :  context.getFailedTests().getAllResults())
		  {
		    long failedResultTime = result.getEndMillis();          

		    for(ITestResult resultToCheck : context.getSkippedTests().getAllResults())
		    {
		        if(failedResultTime == resultToCheck.getEndMillis())
		        {
		            failsToRemove.add(resultToCheck.getMethod());
		            break;
		        }
		    }

		    for(ITestResult resultToCheck : context.getPassedTests().getAllResults())
		    {
		        if(failedResultTime == resultToCheck.getEndMillis())
		        {
		            failsToRemove.add(resultToCheck.getMethod());
		            break;
		        }
		    }           
		  }
          System.out.println("Failed test is:"+failsToRemove.size());
		  for(ITestNGMethod method : failsToRemove)
		  {
			  failedtests.removeResult(method);
		  }  
		  
		  */

		
		
	}


}
