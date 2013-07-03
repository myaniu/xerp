package com.nutzside.codegen;

import httl.Engine;
import httl.Template;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.lang.Files;
import org.nutz.lang.stream.StringOutputStream;

public class CodeGenerator {

	final static private String PACKAGE = "com.xcms";
	final static private String REQUESTPATH = "xcms";
	final static private String HTMLPATH = "xcms";

	private Map<String, Object> root;
	Template template;
	Engine engine;
	private StringBuilder others1;
	private StringBuilder others2;
	private StringBuilder others3;

	public static void main(String[] args) throws Exception {

		CodeGenerator t = new CodeGenerator();
		t.genAllCode();
	}

	public CodeGenerator() {
		engine = Engine.getEngine("httl_creater.properties");
		others1 = new StringBuilder();
		others2 = new StringBuilder();
		others3 = new StringBuilder();
	}

	private void genAllCode() throws ClassNotFoundException, IOException {
		String[] nameArr = getDomainClassNameArr();
		for (int i = 0; i < nameArr.length; i++) {
			Class<?> classf = Class.forName(PACKAGE + ".domain."
					+ nameArr[i].substring(0, nameArr[i].lastIndexOf(".")));
			genEntityCode(classf);
		}
		System.out.println("\n请将以下内容拷贝到相应的文件中：\n");
		System.out.println(others1.append("\n").append(others2).append("\n")
				.append(others3));
	}

	private void genEntityCode(Class<?> domainClass) throws IOException {
		System.out.println(domainClass.getPackage().getName());
		/* 创建数据模型 */
		root = new HashMap<String, Object>();
		root.put("package", PACKAGE);
		root.put("domainPackage", domainClass.getPackage().getName());
		root.put("Domain", domainClass.getSimpleName());
		root.put("requestPath", REQUESTPATH);
		root.put("domainFields", getDomainFields(domainClass));
		root.put("htmlPath", HTMLPATH);
		/* 分别根据模板，写入文件或输出 */
		tempToFile("${Domain}Service.java.httl", getServiceSrcPath(domainClass));
		tempToFile("${Domain}Module.java.httl", getModuleSrcPath(domainClass));
		tempToFile("${Domain}_manager.html.httl", getHtmlPath(domainClass));
		tempPrint("others1.httl", others1);
		tempPrint("others2.httl", others2);
		tempPrint("others3.httl", others3);
	}

	private void tempToFile(String tempFileName, String filePath)
			throws IOException {
		boolean isCreat = Files.createNewFile(new File(filePath));
		if (isCreat) {
			File f = new File(filePath);
			Writer out = new BufferedWriter(new FileWriter(f, true));
			try {
				template = engine.getTemplate(tempFileName);
				template.render(root, out);

				out.flush();
				System.out.println("文件写入完成：" + filePath);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("文件写入失败，文件已存在：" + filePath);
		}
	}

	private void tempPrint(String tempFileName, StringBuilder sb)
			throws IOException {
		OutputStream os = new StringOutputStream(sb);
		Writer out = new OutputStreamWriter(os);
		try {
			template = engine.getTemplate(tempFileName);
			template.render(root, out);
			out.flush();
			sb.append("\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private List<String> getDomainFields(Class<?> domainClass) {
		List<String> fields = new ArrayList<String>();
		Field[] fieldArr = domainClass.getDeclaredFields();
		for (Field filed : fieldArr) {
			String filedName = filed.getName();
			fields.add(filedName);
		}
		return fields;
	}

	private String[] getDomainClassNameArr() {
		File f = new File("src\\main\\java\\" + PACKAGE.replace('.', '\\')
				+ "\\domain\\");
		if (f.isDirectory()) {
			return f.list();
		} else {
			return null;
		}
	}

	private String getServiceSrcPath(Class<?> domainClass) {
		return "src\\main\\java\\" + PACKAGE.replace('.', '\\') + "\\service\\"
				+ domainClass.getSimpleName() + "Service.java";
	}

	private String getModuleSrcPath(Class<?> domainClass) {
		return "src\\main\\java\\" + PACKAGE.replace('.', '\\') + "\\module\\"
				+ domainClass.getSimpleName() + "Module.java";
	}

	private String getHtmlPath(Class<?> domainClass) {
		return "src\\main\\webapp\\WEB-INF\\" + HTMLPATH + "\\"
				+ domainClass.getSimpleName().toLowerCase() + "_manager.httl";
	}
}