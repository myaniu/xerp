package com.nutzside.workflow;

import java.util.HashMap;
import java.util.Map;

//main入口类   一个简单的workflow 模型
public class FlowTurnMain {

	Object it = new Object();
	@SuppressWarnings("rawtypes")
	Map<String, TaskNode> map = new HashMap<String, TaskNode>();

	FlowTurnMain() {
		map.put("张三", new TaskNode<String>("ancd", "张三"));
		map.put("李四", new TaskNode<String>("abcd", "李四"));
	}

	public void gaveItToTaskNode(String TaskNode, Object it)
			throws FlowTurnNullException {
		String[] tn = TaskNode.split(",");
		for (String t : tn) {
			@SuppressWarnings("unchecked")
			TaskNode<Object> obj = map.get(t);
			if (obj == null) {
				throw new FlowTurnNullException("对象" + t + "为空，请重新填写！");
			}
			obj.setIt(it);
		}
	}

	/**
	 * 例子
	 * 
	 * @param args
	 * @throws FlowTurnNullException
	 */
	public static void main(String[] args) throws FlowTurnNullException {
		FlowTurnMain ftm = new FlowTurnMain();
		FlowObj obj = new FlowObj();
		obj.setId("王五");
		obj.setContent("请假十天");

		// 王五操作 流转 张三
		ftm.gaveItToTaskNode("张三", obj);
		FlowObj fobj = (FlowObj) ftm.map.get("张三").getIt();
		System.out.println("张三看:" + (fobj).getId() + " , " + fobj.getContent());

		// 张三传给李四
		ftm.gaveItToTaskNode("李四", obj);
		FlowObj flobj = (FlowObj) ftm.map.get("李四").getIt();
		System.out.println("李四看:" + (flobj).getId() + " , "
				+ flobj.getContent());
	}
}



class FlowTran{
	
}


// 流转对象，内容
class FlowObj {

	String id;
	String content;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String toString() {
		return this.content;
	}
}

// 异常处理
class FlowTurnNullException extends Exception {
	/** 
   *  
   */
	private static final long serialVersionUID = 1L;

	FlowTurnNullException(String msg) {
		super(msg);
	}

	FlowTurnNullException() {
		super("流转对象为空，请重新填写!");
	}
}

// 节点
class TaskNode<T> {
	T it;
	String name;

	TaskNode(T it, String name) {
		this.it = it;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public T getIt() {
		return it;
	}

	public void setIt(T it) {
		this.it = it;
	}
}