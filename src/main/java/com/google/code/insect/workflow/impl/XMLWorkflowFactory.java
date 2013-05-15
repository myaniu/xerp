package com.google.code.insect.workflow.impl;


import org.w3c.dom.*;

import com.google.code.insect.workflow.ActivityHandler;
import com.google.code.insect.workflow.Condition;
import com.google.code.insect.workflow.ConditionHandler;
import com.google.code.insect.workflow.Conditional;
import com.google.code.insect.workflow.Place;
import com.google.code.insect.workflow.Resource;
import com.google.code.insect.workflow.Transition;
import com.google.code.insect.workflow.WorkFlow;
import com.google.code.insect.workflow.WorkFlowDAO;
import com.google.code.insect.workflow.WorkFlowFactory;
import com.google.code.insect.workflow.comm.FactoryException;
import com.google.code.insect.workflow.comm.InvalidTimerResourceException;
import com.google.code.insect.workflow.comm.TransitionType;
import com.google.code.insect.workflow.config.Configuration;
import com.google.code.insect.workflow.config.DefaultConfiguration;
import com.google.code.insect.workflow.util.FileConfig;
import com.google.code.insect.workflow.util.XMLUtil;

import java.io.*;

import java.math.BigDecimal;
import java.net.URL;

import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.*;

/**
 * @author dennis
 */
public class XMLWorkflowFactory implements WorkFlowFactory, Serializable {

	private static final long serialVersionUID = 452755218478437087L;

	protected WorkFlowDAO workFlowDAO;

	protected Configuration configuration;

	protected Map<String, WorkflowConfig> nameWorkflows;

	protected Map<String, WorkFlow> workflows;

	protected Map<Long, String> idToName;

	protected transient boolean reload;

	public XMLWorkflowFactory() {
		this.nameWorkflows = new HashMap<String, WorkflowConfig>();
		this.idToName = new HashMap<Long, String>();
		this.workflows = new HashMap<String, WorkFlow>();
	}

	public WorkFlowDAO getWorkFlowDAO() {
		return workFlowDAO;
	}

	public void setWorkFlowDAO(WorkFlowDAO workFlowDAO) {
		this.workFlowDAO = workFlowDAO;
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public String[] getWorkflowNames() {
		int i = 0;
		String[] res = new String[nameWorkflows.keySet().size()];
		Iterator it = nameWorkflows.keySet().iterator();

		while (it.hasNext()) {
			res[i++] = (String) it.next();
		}

		return res;
	}

	public WorkFlow getWorkFlow(long id) {
		String wf_name = idToName.get(id);
		if (wf_name == null)
			throw new FactoryException("找到指定的流程文件，id:" + id);
		WorkflowConfig config = nameWorkflows.get(wf_name);
		if (config == null)
			throw new FactoryException("找到指定的流程文件，name:" + wf_name);
		File now = FileConfig.getFile(config.location);
		if (now.lastModified() > config.lastModified) {
			config.file = now;
			config.lastModified = now.lastModified();
			return parseWorkFlowXml(config.file, config.id, config.name);
		}
		WorkFlow wf = workflows.get(wf_name);
		if (wf == null)
			wf = parseWorkFlowXml(config.file, config.id, config.name);
		if (wf == null)
			throw new FactoryException("找到指定的流程文件，name:" + wf_name);
		return wf;
	}

	public WorkFlow parseWorkFlowXml(File file, long wf_id, String wf_name) {
		WorkFlow wf = null;
		try {
			InputStream is = new FileInputStream(file);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);

			DocumentBuilder db;

			try {
				db = dbf.newDocumentBuilder();
			} catch (ParserConfigurationException e) {
				throw new FactoryException("创建document builder错误", e);
			}

			Document doc = db.parse(is);

			Element root = (Element) doc.getElementsByTagName("workflow").item(
					0);
			List<Element> list = XMLUtil.getChildElements(root, "node");

			wf = new WorkFlow();
			wf.setId(wf_id);
			wf.setName(wf_name);

			// 用于存储所有的place
			Map<Long, Place> placesStore = new HashMap<Long, Place>();

			for (int i = 0; i < list.size(); i++) {
				Element node = list.get(i);

				String handlerClass = null;
				if (XMLUtil.getChildElement(node, "handler") != null)
					handlerClass = XMLUtil.getChildElement(node, "handler")
							.getAttribute("class");
				if (!XMLUtil.checkAttribute(node, "id"))
					throw new FactoryException("节点id不能为空");
				if (!XMLUtil.checkAttribute(node, "name"))
					throw new FactoryException("节点name不能为空");
				Transition transition = parseTransition(wf, node.getAttribute(
						"type").trim(),
						Long.parseLong(node.getAttribute("id")), node
								.getAttribute("name"));

				Element resource = XMLUtil.getChildElement(node, "resource");
				if (resource != null) {
					if (!XMLUtil.checkAttribute(resource, "class"))
						throw new FactoryException("Transition"
								+ transition.getName() + "的Resource没有指定class");
					try {
						Class resourceClass = Class.forName(resource
								.getAttribute("class"));
						if (resourceClass.getName().equals(
								"com.google.code.insect.workflow.impl.User")) {
							transition.setResource(parseUserResource(resource));
						} else if (resourceClass.getName().equals(
								"com.google.code.insect.workflow.impl.Group")) {
							Group group = parseGroupResource(resource);
							transition.setResource(group);
						} else if (resourceClass
								.getName()
								.equals(
										"com.google.code.insect.workflow.impl.DelayTimerResource")) {
							transition
									.setResource(parseDelayTimerResource(resource));
						} else if (resourceClass
								.getName()
								.equals(
										"com.google.code.insect.workflow.impl.FixTimerResource")) {
							transition
									.setResource(parseFixTimerResource(resource));

						} else {
							Resource res = (Resource) resourceClass
									.newInstance();
							transition.setResource(res);
						}
					} catch (Exception e) {
						throw new FactoryException("创建Resource失败!", e);
					}
				}

				parseConditions(node, transition);

				parseActivityHandler(handlerClass, transition);

				parseInputs(placesStore, transition, XMLUtil.getChildElements(
						XMLUtil.getChildElement(node, "inputs"), "place"));

				parseOutputs(placesStore, transition, XMLUtil.getChildElements(
						XMLUtil.getChildElement(node, "outputs"), "place"));
			}
		} catch (Exception e) {
			throw new FactoryException("解析流程定义文件出错:" + file.getName(), e);
		}
		workflows.put(wf_name, wf);
		return wf;
	}

	private FixTimerResource parseFixTimerResource(Element resource) {
		if (!XMLUtil.checkAttribute(resource, "at"))
			throw new InvalidTimerResourceException("FixTimerResource没有设置触发时间点");
		String at = resource.getAttribute("at");
		boolean auto_next = true;
		if (XMLUtil.checkAttribute(resource, "repeat"))
			auto_next = Boolean
					.parseBoolean(resource.getAttribute("auto-next"));
		FixTimerResource fixTimerResource = new FixTimerResource(at, auto_next);
		return fixTimerResource;
	}

	private DelayTimerResource parseDelayTimerResource(Element resource) {
		if (!XMLUtil.checkAttribute(resource, "delay"))
			throw new InvalidTimerResourceException(
					"DelayTimerResource没有设置延迟时间");
		if (!XMLUtil.checkAttribute(resource, "unit"))
			throw new InvalidTimerResourceException(
					"DelayTimerResource没有设置时间单元");
		long delay = Long.parseLong(resource.getAttribute("delay"));
		String unit = resource.getAttribute("unit");
		TimeUnit timeUnit = parseTimeUnit(unit);
		DelayTimerResource delayTimerResource = null;
		if (!XMLUtil.checkAttribute(resource, "pool-size"))
			delayTimerResource = new DelayTimerResource(delay, timeUnit);
		else
			delayTimerResource = new DelayTimerResource(Integer
					.parseInt(resource.getAttribute("pool-size")), delay,
					timeUnit);
		return delayTimerResource;
	}

	private Group parseGroupResource(Element resource) {
		Group group = new Group();
		if (XMLUtil.checkAttribute(resource, "name"))
			group.setName(resource.getAttribute("name"));
		if (XMLUtil.checkAttribute(resource, "id"))
			group.setId(Long.parseLong(resource.getAttribute("id")));
		return group;
	}

	private User parseUserResource(Element resource) {
		User user = new User();
		if (XMLUtil.checkAttribute(resource, "name"))
			user.setName(resource.getAttribute("name"));
		if (XMLUtil.checkAttribute(resource, "id"))
			user.setId(Long.parseLong(resource.getAttribute("id")));
		return user;
	}

	private TimeUnit parseTimeUnit(String unit) {
		TimeUnit timeUnit = null;
		if (unit.equalsIgnoreCase("second") || unit.equalsIgnoreCase("seconds")) {
			timeUnit = TimeUnit.SECONDS;
		} else if (unit.equalsIgnoreCase("MILLISECOND")
				|| unit.equalsIgnoreCase("MILLISECONDS")) {
			timeUnit = TimeUnit.MILLISECONDS;
		} else if (unit.equalsIgnoreCase("MICROSECOND")
				|| unit.equalsIgnoreCase("MICROSECONDS")) {
			timeUnit = TimeUnit.MICROSECONDS;
		} else
			throw new InvalidTimerResourceException("未知的时间单位:" + unit);
		return timeUnit;
	}

	private void parseConditions(Element node, Conditional transition) {
		Conditions rootCondition = null;
		List<Element> subCompositeConditions = XMLUtil.getChildElements(node,
				"conditions");
		List<Element> subConditions = XMLUtil.getChildElements(node,
				"condition");
		parseSingleCondition(rootCondition, subConditions);

		parseCompositeCondition(transition, rootCondition,
				subCompositeConditions);
	}

	private void parseCompositeCondition(Conditional transition,
			Conditions rootCondition, List<Element> subCompositeConditions) {
		for (int j = 0; j < subCompositeConditions.size(); j++) {
			Element e = subCompositeConditions.get(j);
			Conditions subRootCondition = new Conditions();
			// 非根节点conditions
			if (rootCondition != null) {
				rootCondition.add(subRootCondition);
				rootCondition = subRootCondition;
			} else {
				// 根节点conditions
				rootCondition = subRootCondition;
				transition.setCondition(subRootCondition);
			}
			if (XMLUtil.checkAttribute(e, "type"))
				subRootCondition.setType(e.getAttribute("type"));
			parseCompositeCondition(transition, subRootCondition, XMLUtil
					.getChildElements(e, "conditions"));
			parseSingleCondition(rootCondition, XMLUtil.getChildElements(e,
					"condition"));
		}

	}

	private void parseSingleCondition(Conditions rootCondition,
			List<Element> subConditions) {
		for (int j = 0; j < subConditions.size(); j++) {
			Element e = subConditions.get(j);
			if (XMLUtil.checkAttribute(e, "class")) {
				try {
					SimpleCondition simpleCondition = new SimpleCondition();
					simpleCondition.setHandler((ConditionHandler) Class
							.forName(e.getAttribute("class").trim())
							.newInstance());
					simpleCondition.setVariableName(e
							.getAttribute("variable-name"));
					if (XMLUtil.checkAttribute(e, "value"))
						simpleCondition.setValue(e.getAttribute("value"));
					rootCondition.add(simpleCondition);
				} catch (Exception exp) {
					throw new FactoryException("创建ConditionHandler发生错误", exp);
				}
			} else {
				SimpleCondition simpleCondition = new SimpleCondition();
				if (XMLUtil.checkAttribute(e, "exp"))
					simpleCondition.setExpression(e.getAttribute("exp"));
				else {
					simpleCondition.setExpression(XMLUtil
							.getChildText(e, "exp").trim());
				}
				if (XMLUtil.checkAttribute(e, "value"))
					simpleCondition.setValue(new BigDecimal(e
							.getAttribute("value")));
				simpleCondition
						.setVariableName(e.getAttribute("variable-name"));
				rootCondition.add(simpleCondition);
			}
		}
	}

	private Transition parseTransition(WorkFlow wf, String type, long id,
			String name) {
		Transition transition = null;
		if (type == null || type.length() == 0) {
			transition = new Transition(id, name);
		} else if (type.equals("start")) {
			transition = new StartTransition(id, name);
			wf.setStart(transition);
		} else if (type.equals("end")) {
			transition = new EndTransition(id, name);
			wf.setEnd(transition);
		} else if (type.equals("and-split")) {
			transition = new AndSplitTransition(id, name);
		} else if (type.equals("and-join")) {
			transition = new AndJoinTransition(id, name);
		} else if (type.equals("or-split")) {
			transition = new OrSplitTransition(id, name);
		} else if (type.equals("or-join")) {
			transition = new OrJoinTransition(id, name);
		} else
			throw new FactoryException("未知类型的节点  " + type);

		wf.addTransition(transition);
		return transition;
	}

	private void parseActivityHandler(String handlerClass, Transition transition) {
		if (handlerClass != null) {
			try {
				transition.setActivityHandler((ActivityHandler) Class.forName(
						handlerClass).newInstance());
			} catch (Exception e) {
				throw new FactoryException("创建ActivityHandler错误", e);
			}
		}
	}

	private void parseOutputs(Map<Long, Place> placesStore,
			Transition transition, List<Element> outputs) {
		for (int j = 0; j < outputs.size(); j++) {
			Element e = outputs.get(j);
			long placeId = Long.parseLong(e.getAttribute("id"));
			if (placesStore.containsKey(placeId)) {
				if (placesStore.get(placeId).getCondition() == null)
					parseConditions(e, placesStore.get(placeId));
				transition.addOutputPlace(placesStore.get(placeId));
			} else {
				Place place = new Place(placeId);
				parseConditions(e, place);
				transition.addOutputPlace(place);
				placesStore.put(placeId, place);
			}
		}
	}

	private void parseInputs(Map<Long, Place> placesStore,
			Transition transition, List<Element> inputs) {
		for (int j = 0; j < inputs.size(); j++) {
			Element e = inputs.get(j);
			long placeId = Long.parseLong(e.getAttribute("id"));
			if (placesStore.containsKey(placeId)) {
				if (placesStore.get(placeId).getCondition() == null)
					parseConditions(e, placesStore.get(placeId));
				transition.addInputPlace(placesStore.get(placeId));
			} else {
				Place place = new Place(placeId);
				parseConditions(e, place);
				transition.addInputPlace(place);
				placesStore.put(placeId, place);
			}
		}
	}

	public WorkFlow getWorkFlow(String name) {
		if (name == null || name.trim().length() == 0)
			throw new IllegalArgumentException("请指定要启动的流程名称或者id");
		WorkflowConfig config = nameWorkflows.get(name);
		if (this.configuration == null)
			throw new FactoryException("没有设置configuration");
		if (config == null)
			throw new FactoryException("找到指定的流程文件，name:" + name);

		File now = FileConfig.getFile(config.location);
		if (now.lastModified() > config.lastModified) {
			config.file = now;
			config.lastModified = now.lastModified();
			return parseWorkFlowXml(config.file, config.id, config.name);
		}

		WorkFlow wf = workflows.get(name);
		if (wf == null)
			wf = parseWorkFlowXml(config.file, config.id, config.name);
		if (wf == null)
			throw new FactoryException("找到指定的流程文件，name:" + name);
		return wf;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public void init() {
		this.nameWorkflows = new HashMap<String, WorkflowConfig>();
		this.idToName = new HashMap<Long, String>();
		this.workflows = new HashMap<String, WorkFlow>();
		InputStream is = this.configuration.getConfigFileStream();

		if (is == null) {
			throw new FactoryException("在classpath找不到配置文件"
					+ this.configuration.getFileName());
		}

		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);

			DocumentBuilder db;

			try {
				db = dbf.newDocumentBuilder();
			} catch (ParserConfigurationException e) {
				throw new FactoryException("创建document builder错误", e);
			}

			Document doc = db.parse(is);

			Element root = (Element) doc.getElementsByTagName("insect").item(0);

			Element workflow_dao = XMLUtil
					.getChildElement(root, "workflow-dao");
			if (workflow_dao == null)
				throw new FactoryException("没有设置workflow-dao");
			String daoClassName = workflow_dao.getAttribute("class");
			if (daoClassName == null || daoClassName.equals(""))
				throw new FactoryException("没有设置workflow-dao的class属性");
			try {
				this.workFlowDAO = (WorkFlowDAO) Class.forName(daoClassName)
						.newInstance();
			} catch (Exception e) {
				throw new FactoryException("创建WorkFlowDAO错误", e);
			}
			Element workflows = XMLUtil.getChildElement(root, "workflows");

			String basedir = getBaseDir(root);
			List list = XMLUtil.getChildElements(workflows, "workflow");
			for (int i = 0; i < list.size(); i++) {
				Element e = (Element) list.get(i);
				WorkflowConfig config = new WorkflowConfig(basedir, Integer
						.parseInt(e.getAttribute("id")), e
						.getAttribute("location"), e.getAttribute("name"));
				if (nameWorkflows.put(e.getAttribute("name"), config) != null) {
					throw new FactoryException("重复的流程名称:"
							+ e.getAttribute("name"));
				}
				idToName.put(Long.parseLong(e.getAttribute("id")), config.name);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new FactoryException("解析insect配置文件出错", e);
		}
	}

	/**
	 * Get where to find workflow XML files.
	 * 
	 * @param root
	 *            The root element of the XML file.
	 * @return The absolute base dir used for finding these files or null.
	 */
	protected String getBaseDir(Element root) {
		String basedir = root.getAttribute("basedir");

		if (basedir.length() == 0) {
			// No base dir defined
			return null;
		}

		if (new File(basedir).isAbsolute()) {
			// An absolute base dir defined
			return basedir;
		} else {
			// Append the current working directory to the relative base dir
			return new File(System.getProperty("user.dir"), basedir)
					.getAbsolutePath();
		}
	}

	// ~ Inner Classes
	// //////////////////////////////////////////////////////////

	static class WorkflowConfig implements Serializable {
		private static final long serialVersionUID = 4939957922893602958L;

		String location;

		int id; // file, URL, service

		long lastModified;

		File file;

		String name;

		public WorkflowConfig(String basedir, int id, String location,
				String name) {
			if (basedir != null)
				location = basedir + location;

			file = FileConfig.getFile(location);
			this.location = location;
			this.lastModified = file.lastModified();
			this.id = id;
			this.name = name;
		}
	}

	public static void main(String[] args) {
		WorkFlowFactory factory = new XMLWorkflowFactory();
		factory.setConfiguration(new DefaultConfiguration());
		factory.init();
		System.out.println(factory.getWorkFlow("test").getStart().getInputs()
				.size());
	}
}
