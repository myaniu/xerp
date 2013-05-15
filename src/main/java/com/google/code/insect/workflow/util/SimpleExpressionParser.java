package com.google.code.insect.workflow.util;

import java.math.BigDecimal;

/**
 * 
 * 北京联信永益科技有限公司　　版权所有
 * @version: 1.0 　$Id:$
 */
/**
 * <p>
 * 类说明：用于表达式与实际值的比较
 * </p>
 * <p>
 * 注意事项：
 * </p>
 * 
 * <pre></pre>
 * 
 * <p>
 * 创建日期：Aug 6, 2007 10:18:58 AM
 * </p>
 * <p>
 * 文件名:ExpressionParser.java
 * </p>
 * 
 * @author:庄晓丹
 * @version $Id:$
 */
public class SimpleExpressionParser {
	public static transient boolean DEBUG = false;

	private static SimpleExpressionParser parser = new SimpleExpressionParser();

	private SimpleExpressionParser() {

	}

	public static SimpleExpressionParser getInstance() {
		return parser;
	}

	public boolean fireRule(String expression, BigDecimal fact) {
		traceCalculate("\nexpression:" + expression);
		expression = expression.replace("\n|\r", "").trim();
		char[] chars = expression.toCharArray();
		return parseExpression(fact, chars);
	}

	/**
	 * @param fact
	 * @param operatorsStack
	 * @param operandsStack
	 * @param chars
	 * @param operand
	 * @param operator
	 * @return
	 */
	private boolean parseExpression(BigDecimal fact, char[] chars) {
		boolean result = true;
		String operand = "";
		String operator = "";
		Stack operatorsStack = new Stack();
		Stack operandsStack = new Stack();
		for (int i = 0; i < chars.length; i++) {
			char token = chars[i];
			traceCalculate("token:" + token);
			if (Character.isDigit(token) || token == '.') {
				if (!operator.equals("")) {
					traceCalculate("push operator:" + operator);
					// 将操作符放入操作符号栈
					operatorsStack.push(operator);
					operator = "";

				}
				operand += token;
				result = checkTail(fact, operatorsStack, operandsStack,
						chars.length, operand, result, i);
				continue;
			} else if (Character.isLetter(token)) {
				if (!operator.equals("")) {
					traceCalculate("push operator:" + operator);
					// 将操作符放入操作符号栈
					operatorsStack.push(operator);
					operator = "";
				}
				operand = String.valueOf(token);
				result = checkTail(fact, operatorsStack, operandsStack,
						chars.length, operand, result, i);
				// 将操作数放入操作数栈
				operandsStack.push(operand);
				traceCalculate("push operand:" + token);
				operand = "";
				continue;
			} else {
				if (!operatorsStack.isEmpty() && !operandsStack.isEmpty()) {
					// 当前操作数是字母(变量)，已存入栈，因此需要取出
					if (operand.equals("")) {
						operand = (String) operandsStack.pop();
						result = result
								&& calculatePerfomance(operatorsStack,
										operandsStack, operand, fact);
						// 当前操作数是数字
					} else {
						result = result
								&& calculatePerfomance(operatorsStack,
										operandsStack, operand, fact);

					}
				}

				if (!operand.equals("")) {
					result = checkTail(fact, operatorsStack, operandsStack,
							chars.length, operand, result, i);
					// 将操作数放入操作数栈
					operandsStack.push(operand);
					traceCalculate("push2 operand:" + operand);
					operand = "";
				}

				operator += token;
				continue;
			}

		}
		return result;
	}

	/**
	 * 判断是否已经到表达式尾端，如果是，计算
	 * 
	 * @param fact
	 * @param operatorsStack
	 * @param operandsStack
	 * @param chars
	 * @param operand
	 * @param result
	 * @param i
	 * @return
	 */
	private boolean checkTail(BigDecimal fact, Stack operatorsStack,
			Stack operandsStack, int chars_length, String operand,
			boolean result, int i) {
		if (i == chars_length - 1) {
			result = result
					&& calculatePerfomance(operatorsStack, operandsStack,
							operand, fact);
		}
		return result;
	}

	private void displayStack(String name, Stack stack) {
		if (DEBUG) {
			for (int i = 0; i < stack.pool.size(); i++)
				System.out.println(name + stack.pool.get(i));
		}
	}

	private boolean calculatePerfomance(Stack operatorsStack,
			Stack operandsStack, String currentOperand, BigDecimal fact) {
		traceCalculate("开始计算");
		displayStack("operators stack:", operatorsStack);
		displayStack("operands stack:", operandsStack);
		traceCalculate("currentOperand:" + currentOperand);
		String operator = (String) operatorsStack.pop();
		traceCalculate("pop operator:" + operator);
		BigDecimal lastOperand = coverOperandToDouble((String) operandsStack
				.pop(), fact);
		traceCalculate("last operand:" + lastOperand);
		BigDecimal nextOperand = coverOperandToDouble(currentOperand, fact);
		traceCalculate("next operand:" + nextOperand);
		boolean result = true;
		if (operator.equals("=="))
			return lastOperand.compareTo(nextOperand) == 0;
		else if (operator.equals("!="))
			return lastOperand.compareTo(nextOperand) != 0;
		char[] operators = operator.toCharArray();
		for (int i = 0; i < operators.length; i++) {
			switch (operators[i]) {
			case '<':
				result = result && (lastOperand.compareTo(nextOperand) == -1);
				break;
			case '=':
				// result为false，也就是小于，大于符号不满足的时候，判断等号是否成立
				if (!result)
					result = (lastOperand.compareTo(nextOperand) == 0);
				break;
			case '>':
				result = result && (lastOperand.compareTo(nextOperand) == 1);
				break;
			}
		}
		return result;

	}

	/**
	 * 用于debug
	 */
	private void traceCalculate(String info) {
		if (DEBUG)
			System.out.println(info);
	}

	private BigDecimal coverOperandToDouble(String operand, BigDecimal fact) {
		// 如果是字母，也就是变量，返回fact变量
		if (Character.isLetter(operand.toCharArray()[0])
				|| ((operand.toCharArray()[0]) == '_')) {

			return fact;
		} else
			return new BigDecimal(operand);
	}
}

/**
 * 
 * 北京联信永益科技有限公司 版权所有
 * 
 * @version: 1.0 $Id:$
 */
/**
 * <p>
 * 类说明：栈的实现，用于计算表达式
 * </p>
 * <p>
 * 注意事项：
 * </p>
 * 
 * <pre></pre>
 * 
 * <p>
 * 创建日期：Aug 6, 2007 8:30:07 AM
 * </p>
 * <p>
 * 文件名:KHT2Manager.java
 * </p>
 * 
 * @author:yanxh
 * @version $Id:$
 */
class Stack {
	protected java.util.ArrayList pool = new java.util.ArrayList();

	public Stack() {
	}

	public Stack(int n) {
		pool.ensureCapacity(n);
	}

	public void clear() {
		pool.clear();
	}

	public boolean isEmpty() {
		return pool.isEmpty();
	}

	public int size() {
		return pool.size();
	}

	public Object topEl() {
		if (isEmpty())
			throw new java.util.EmptyStackException();
		return pool.get(pool.size() - 1);
	}

	public Object pop() {
		if (isEmpty())
			throw new java.util.EmptyStackException();
		return pool.remove(pool.size() - 1);
	}

	public void push(Object el) {
		pool.add(el);
	}

	public String toString() {
		return pool.toString();
	}
}
