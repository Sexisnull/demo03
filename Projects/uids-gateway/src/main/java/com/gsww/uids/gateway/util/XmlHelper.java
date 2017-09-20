package com.gsww.uids.gateway.util;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
/**
 * Created on 2014-6-21 Title:
 * <p/>
 * Description:
 * <p/>
 * Copyright: Copyright GSWW (c) 2014
 * <p/>
 * Company: wanwei.com
 * <p/>
 * 
 * @author wangcf
 * @version 1.0
 */
public class XmlHelper {
	//创建一个新的Document
	public static Document createDocument() {
        DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder=factory.newDocumentBuilder();
            return builder.newDocument();//新建DOM
        }catch(ParserConfigurationException e) {
            logger.error("创建Document失败:"+e);
            return null;
        }
    }
	//创建根结点，并返回
	public static Element createRootElement(Document doc, String rootTagName) {    
		if(doc.getDocumentElement()==null) {
			logger.debug("create root element '"+rootTagName+"' success.");
			Element root=doc.createElement(rootTagName);
			doc.appendChild(root);
			return root;
		}
		logger.warn("this dom's root element is exist,create fail.");
		return doc.getDocumentElement();
	}
	
	//获取父结点parent的所有子结点
	public static NodeList getNodeList(Element parent) {
		return parent.getChildNodes();
	}

	//在父结点中查询指定名称的结点集
	public static Element[] getElementsByName(Element parent, String name) {
		ArrayList resList = new ArrayList();
		NodeList nl = getNodeList(parent);
		for (int i = 0; i < nl.getLength(); i++) {
			Node nd = nl.item(i);
			if (nd.getNodeName().equals(name)) {
				resList.add(nd);
			}
		}
		Element[] res = new Element[resList.size()];
		for (int i = 0; i < resList.size(); i++) {
			res[0] = (Element) resList.get(i);
		}
		logger.debug(parent.getNodeName() + "'s children of " + name
				+ "'s num:" + res.length);
		return res;
	}

	//获取指定Element的名称
	public static String getElementName(Element element) {
		return element.getNodeName();
	}

	//获取指定Element的值
	public static String getElementValue(Element element) {
		NodeList nl = element.getChildNodes();
		for (int i = 0; i < nl.getLength(); i++) {
			if (nl.item(i).getNodeType() == Node.TEXT_NODE)// 是一个Text Node
			{
				logger.debug(element.getNodeName() + " has a Text Node.");
				return element.getFirstChild().getNodeValue();
			}
		}
		logger.error(element.getNodeName() + " hasn't a Text Node.");
		return null;
	}

	//获取指定Element的属性attr的值
	public static String getElementAttr(Element element, String attr) {
		return element.getAttribute(attr);
	}
	//将xml转化为String
	public static String toString(Document document) {
        String result = null;
        if (document != null) {
            StringWriter strWtr = new StringWriter();
            StreamResult strResult = new StreamResult(strWtr);
            TransformerFactory tfac = TransformerFactory.newInstance();
            try
            {
                Transformer t = tfac.newTransformer();
                t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                t.setOutputProperty(OutputKeys.INDENT, "yes");
                t.setOutputProperty(OutputKeys.METHOD, "xml"); // xml, html,// text
               // t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
                t.transform(new DOMSource(document.getDocumentElement()), strResult);
            }
            catch (Exception e)
            {
                System.err.println("XML.toString(Document): " + e);
            }
            result = strResult.getWriter().toString();
        }

        return result;
    }
	//设置指定Element的值
	public static void setElementValue(Element element, String val) {
		Node node = element.getOwnerDocument().createTextNode(val);
		NodeList nl = element.getChildNodes();
		for (int i = 0; i < nl.getLength(); i++) {
			Node nd = nl.item(i);
			if (nd.getNodeType() == Node.TEXT_NODE)// 是一个Text Node
			{
				nd.setNodeValue(val);
				logger.debug("modify " + element.getNodeName()
						+ "'s node value succe.");
				return;
			}
		}
		logger.debug("new " + element.getNodeName() + "'s node value succe.");
		element.appendChild(node);
	}

	//设置结点Element的属性
	public static void setElementAttr(Element element, String attr, String attrVal) {
		element.setAttribute(attr, attrVal);
	}

	//在parent下增加结点child
	public static void addElement(Element parent, Element child) {
		parent.appendChild(child);
	}

	//在parent下增加字符串tagName生成的结点
	public static void addElement(Element parent, String tagName) {
		Document doc = parent.getOwnerDocument();
		Element child = doc.createElement(tagName);
		parent.appendChild(child);
	}

	//在parent下增加tagName的Text结点，且值为text
	public static void addElement(Element parent, String tagName, String text) {
		Document doc = parent.getOwnerDocument();
		Element child = doc.createElement(tagName);
		setElementValue(child, text);
		parent.appendChild(child);
	}

	//将父结点parent下的名称为tagName的结点移除
	public static void removeElement(Element parent, String tagName) {
		logger.debug("remove " + parent.getNodeName()
				+ "'s children by tagName " + tagName + " begin...");
		NodeList nl = parent.getChildNodes();
		for (int i = 0; i < nl.getLength(); i++) {
			Node nd = nl.item(i);
			if (nd.getNodeName().equals(tagName)) {
				parent.removeChild(nd);
				logger.debug("remove child '" + nd + "' success.");
			}
		}
		logger.debug("remove " + parent.getNodeName()
				+ "'s children by tagName " + tagName + " end.");
	}
	
	//传入xml，节点名称，返回节点的值
	public static String strParseXML(String xmlStr, String nodeName) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			StringReader sr = new StringReader(xmlStr); 
			InputSource is = new InputSource(sr); 
			Document doc = builder.parse(is);
			if (doc.getElementsByTagName(nodeName).item(0).getFirstChild() != null) {
				return doc.getElementsByTagName(nodeName).item(0).getFirstChild().getNodeValue();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/* 全局变量 */
	static Logger logger = Logger.getLogger("XmlOper");
}