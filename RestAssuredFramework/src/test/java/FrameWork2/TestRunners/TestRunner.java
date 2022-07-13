package FrameWork2.TestRunners;

import org.junit.runner.JUnitCore;

import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import FrameWork2.TestClasses.TC01;



public class TestRunner {
	
   public static void main(String[] args) {
	   
      Result result = JUnitCore.runClasses(TC01.class);
		
      for (Failure failure : result.getFailures()) {
         System.out.println(failure.toString());
      }
		
      System.out.println(result.wasSuccessful());
   }
   
}
