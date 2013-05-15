package com.google.code.insect.workflow.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for net.rubyeye.insect.workflow.test");
		// $JUnit-BEGIN$
		suite.addTestSuite(CondtionTest.class);
		suite.addTestSuite(BasicWorkFlowTest.class);
		suite.addTestSuite(BasicWorkFlowManagerXmlTest.class);
		suite.addTestSuite(CompositeProcessTest.class);
		// $JUnit-END$
		return suite;
	}

}
