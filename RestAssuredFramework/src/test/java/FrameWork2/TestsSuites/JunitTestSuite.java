package FrameWork2.TestsSuites;

import org.junit.runner.RunWith;

import org.junit.runners.Suite;

import FrameWork2.TestClasses.TC01;
import FrameWork2.TestClasses.TC02;
import FrameWork2.TestClasses.TC03;
import FrameWork2.TestClasses.TC04;



//JUnit Suite Test
@RunWith(Suite.class)

@Suite.SuiteClasses({ 
   TC01.class,TC02.class,TC03.class,TC04.class
})

public class JunitTestSuite {

}
