package com.google.code.insect.workflow.util;

/**
 * 测试表达式计算
 */
import java.math.BigDecimal;

import junit.framework.TestCase;

public class ExpressionCalculateTest extends TestCase {
	String exp1, exp2, exp3, exp4;

	BigDecimal v1, v2, v3, v4, v5;

	SimpleExpressionParser parser = null;

	protected void setUp() throws Exception {
		exp1 = "a<80";
		exp2 = "80<=a<81";
		exp3 = "81<=a<=82";
		exp4 = "a>=90";
		v1 = new BigDecimal("70.0");
		v2 = new BigDecimal("81.2");
		v3 = new BigDecimal("80");
		v4 = new BigDecimal("90");
		v5 = new BigDecimal("92");
		parser = SimpleExpressionParser.getInstance();
	}

	public void testFireRule() throws Exception {
		assertFalse(parser.fireRule(exp1, v4));
		assertTrue(parser.fireRule(exp1, v1));
		assertFalse(parser.fireRule(exp1, v3));
		assertFalse(parser.fireRule(exp2, v2));
		assertTrue(parser.fireRule(exp2, v3));
		assertFalse(parser.fireRule(exp2, new BigDecimal("82")));
		assertTrue(parser.fireRule(exp3, v2));
		assertTrue(parser.fireRule(exp4, v4));
		assertFalse(parser.fireRule(exp4, v1));
		assertTrue(parser.fireRule(exp4, v5));

		assertTrue(parser.fireRule("b==100", new BigDecimal("100")));
		assertTrue(parser.fireRule("c==0.00", new BigDecimal("0")));
		assertTrue(parser.fireRule("60<=c<=80", new BigDecimal("79.9")));
		assertFalse(parser.fireRule("60<=50<=80", new BigDecimal("0.0")));
		assertTrue(parser.fireRule("60<=79<=80", new BigDecimal("0.0")));
		assertFalse(parser.fireRule("60<=99<=80", new BigDecimal("0.0")));

		assertTrue(parser.fireRule("  60<=80<=90<100  ", new BigDecimal("0.0")));
		assertFalse(parser.fireRule("60<=99<=80<100", new BigDecimal("0.0")));
		// parser.DEBUG = true;
		assertTrue(parser.fireRule("b==100.00==100==100.0", new BigDecimal(
				"100")));
		assertTrue(parser.fireRule("a==true".replaceAll("true", "1==1"),
				new BigDecimal("1")));
		SimpleExpressionParser.DEBUG = true;
		assertTrue(parser.fireRule("c!=0.00", new BigDecimal("3")));
	}

}
