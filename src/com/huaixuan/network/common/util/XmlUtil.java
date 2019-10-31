/*
 * Hundsun Inc.
 * Copyright (c) 2006-2009 All Rights Reserved.
 *
 * Author     :shengyong
 * Version    :1.0
 * Create Date:2011-3-8
 */
package com.huaixuan.network.common.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.EntityReference;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * XML工具类
 * 
 */

public class XmlUtil
{

    /**
     * 根据xml节点获取子类节点
     * 
     * @param 参数说明
     *            Element ：xml的一个节点
     * @param 参数说明
     *            String ：节点名字
     * @return 返回值：List<Node>包含子节点的list
     * @exception 异常：注释出什么条件下会引发什么样的异常
     * @see 参考的JavaDoc
     */
    public static List<Node> getChildElementsByTagName(Element ele, String childEleName)
    {

        NodeList nl = ele.getChildNodes();
        List<Node> childEles = new ArrayList<Node>();
        for (int i = 0; i < nl.getLength(); i++)
        {
            Node node = nl.item(i);
            if (node instanceof Element && childEleName.equals(node.getNodeName())
                    || childEleName.equals(node.getLocalName()))
            {
                childEles.add(node);
            }
        }
        return childEles;
    }

    /**
     * 根据xml节点获取子类节点
     * 
     * @param 参数说明
     *            Element ：xml的一个节点
     * @param 参数说明
     *            String ：节点名字
     * @return 返回值：Element 子节点
     * @exception 异常：注释出什么条件下会引发什么样的异常
     * @see 参考的JavaDoc
     */
    public static Element getChildElementByTagName(Element ele, String childEleName)
    {

        NodeList nl = ele.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++)
        {
            Node node = nl.item(i);
            if (node instanceof Element && childEleName.equals(node.getNodeName())
                    || childEleName.equals(node.getLocalName()))
            {
                return (Element) node;
            }
        }
        return null;
    }

    /**
     * 根据xml节点获取子类节点值
     * 
     * @param 参数说明
     *            Element ：xml的一个节点
     * @param 参数说明
     *            String ：节点名字
     * @return 返回值：子类节点的值
     * @exception 异常：注释出什么条件下会引发什么样的异常
     * @see 参考的JavaDoc
     */
    public static String getChildElementValueByTagName(Element ele, String childEleName)
    {

        Element child = getChildElementByTagName(ele, childEleName);
        return (child != null ? getTextValue(child) : null);
    }

    /**
     * 根据xml节点获取节点值
     * 
     * @param 参数说明
     *            Element ：xml的一个节点
     * @return 返回值：节点的值
     * @exception 异常：注释出什么条件下会引发什么样的异常
     * @see 参考的JavaDoc
     */
    public static String getTextValue(Element valueEle)
    {

        StringBuffer value = new StringBuffer();
        NodeList nl = valueEle.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++)
        {
            Node item = nl.item(i);
            if ((item instanceof CharacterData && !(item instanceof Comment)) || item instanceof EntityReference)
            {
                value.append(item.getNodeValue());
            }
        }
        return value.toString().trim();
    }

    /**
     * 根据读入的字符串生成一个xml的document对象
     * 
     * @param data XML数据字符串
     * @return 返回值：Document xml的dom元素
     * @exception 异常：注释出什么条件下会引发什么样的异常
     * @see 参考的JavaDoc
     */
    public static Document createDocument(byte[] data) throws RuntimeException
    {

        DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
        InputStream input = null;
        try
        {
            DocumentBuilder p = f.newDocumentBuilder();
            input = new ByteArrayInputStream(data);
            return p.parse(input);
        }
        catch (ParserConfigurationException e)
        {
            throw new RuntimeException(e);
        }
        catch (SAXException e)
        {
            throw new RuntimeException(e);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据xml节点，父节点名字、子节点名字获取子类节点
     * 
     * @param 参数说明
     *            Document ：xml的一个节点
     * @param 参数说明
     *            parentName ：父节点名字
     * @param 参数说明
     *            eleName ：子节点名字
     * @return 返回值：节点对应的value值
     * @exception 异常：注释出什么条件下会引发什么样的异常
     * @see 参考的JavaDoc
     */
    public static String getElementValueByTagName(Document doc, String parentName, String eleName)
    {

        NodeList nl = doc.getElementsByTagName(parentName);
        if (null == nl)
        {
            return null;
        }
        Node item = nl.item(0);
        return getChildElementValueByTagName((Element) item, eleName);
    }

    /**
     * 根据xml节点，节点的名称取到节点
     * 
     * @param 参数说明
     *            Document ：xml的一个节点
     * @param 参数说明
     *            eleName ：子节点名字
     * @return 返回值：节点对应的value值
     * @exception 异常：注释出什么条件下会引发什么样的异常
     * @see 参考的JavaDoc
     */
    public static Element getElementByTagName(Document doc, String eleName)
    {

        NodeList nl = doc.getElementsByTagName(eleName);
        if (null == nl)
        {
            return null;
        }
        Node item = nl.item(0);
        return (Element) item;
    }    
    
    /**
     * 根据xml节点，父节点名字、子节点名字获取子类节点
     * 
     * @param 参数说明
     *            Element ：xml的一个节点
     * @param 参数说明
     *            parentName ：父节点名字
     * @param 参数说明
     *            eleName ：子节点名字
     * @return 返回值：节点对应的value值
     * @exception 异常：注释出什么条件下会引发什么样的异常
     * @see 参考的JavaDoc
     */
    public static String getGrandSonElementValueByTagName(Element element, String parentName, String eleName)
    {

        NodeList nl = element.getElementsByTagName(parentName);
        if (null == nl)
        {
            return null;
        }
        Node item = nl.item(0);
        return getChildElementValueByTagName((Element) item, eleName);
    }

    /**
     * 根据xml节点，父节点名字、子节点名字获取孙子节点
     * 
     * @param element xml的一个节点
     * @param parentName 父节点名字
     * @param 参数说明
     *            eleName ：子节点名字
     * @return 返回值：节点对应的value值
     * @exception 异常：注释出什么条件下会引发什么样的异常
     * @see 参考的JavaDoc
     */
    public static Element getGrandSonElementByTagName(Element element, String parentName, String eleName)
    {

        NodeList nl = element.getElementsByTagName(parentName);
        if (null == nl)
        {
            return null;
        }
        Node item = nl.item(0);
        return getChildElementByTagName((Element) item, eleName);
    }

    /**
     * 根据xml节点，父节点名字、子节点名字获取孙子节点
     * 
     * @param 参数说明
     *            Element ：xml的一个节点
     * @param 参数说明
     *            parentName ：父节点名字
     * @param 参数说明
     *            eleName ：子节点名字
     * @return 返回值：List<String>节点对应的value值
     * @exception 异常：注释出什么条件下会引发什么样的异常
     * @see 参考的JavaDoc
     */
    public static List<String> getGrandSonListValueByTagName(Element element, String parentName, String eleName)
    {

        NodeList nl = element.getElementsByTagName(parentName);
        if (null == nl)
        {
            return null;
        }
        Node item = nl.item(0);
        if (null == item)
        {
            return null;
        }
        NodeList subNodeList = item.getChildNodes();
        List<String> childEles = new ArrayList<String>();
        Node node = null;
        for (int i = 0; i < subNodeList.getLength(); i++)
        {
            node = subNodeList.item(i);

            if (node != null)
            {
                if (node instanceof Element && eleName.equals(node.getNodeName())
                        || eleName.equals(node.getLocalName()))
                {
                    childEles.add(getTextValue((Element) node));
                }
            }
        }

        return childEles;
    }

    /**
     * 根据xml节点，父节点名字、子节点名字获取孙子节点
     * 
     * @param 参数说明
     *            Element ：xml的一个节点
     * @param 参数说明
     *            parentName ：父节点名字
     * @param 参数说明
     *            eleName ：子节点名字
     * @return 返回值：List<Node>节点对应的value值
     * @exception 异常：注释出什么条件下会引发什么样的异常
     * @see 参考的JavaDoc
     */
    public static List<Node> getGrandSonElementsByTagName(Element ele, String parentName, String eleName)
    {

        NodeList nl = ele.getElementsByTagName(parentName);
        if (null == nl)
        {
            return null;
        }
        Node item = nl.item(0);
        if (null == item)
        {
            return null;
        }
        NodeList subNodeList = item.getChildNodes();
        List<Node> childEles = new ArrayList<Node>();
        Node node = null;
        for (int i = 0; i < subNodeList.getLength(); i++)
        {
            node = subNodeList.item(i);

            if (node != null)
            {
                if (node instanceof Element && eleName.equals(node.getNodeName())
                        || eleName.equals(node.getLocalName()))
                {
                    childEles.add(node);
                }
            }
        }

        return childEles;
    }

    /**
     * 根据xml节点获取子类节点
     * 
     * @param 参数说明
     *            Element ：xml的一个节点
     * @param 参数说明
     *            String ：节点名字
     * @return 返回值：List<String>包含子节点的list
     * @exception 异常：注释出什么条件下会引发什么样的异常
     * @see 参考的JavaDoc
     */
    public static List<String> getChildListValuesByTagName(Element ele, String childEleName)
    {

        NodeList nl = ele.getChildNodes();
        List<String> childEles = new ArrayList<String>();
        for (int i = 0; i < nl.getLength(); i++)
        {
            Node node = nl.item(i);
            if (node instanceof Element && childEleName.equals(node.getNodeName())
                    || childEleName.equals(node.getLocalName()))
            {
                childEles.add(getTextValue((Element) node));
            }
        }
        return childEles;
    }

    /**
     * 根据xml节点获取对应属性内容
     * 
     * @param 参数说明
     *            Element ：xml的一个节点
     * @param 参数说明
     *            String ：属性名字
     * @return 返回值：String 的属性值
     * @exception 异常：注释出什么条件下会引发什么样的异常
     * @see 参考的JavaDoc
     */
    public static String getAttrValuesByName(Element ele, String attributeName)
    {
        return ele.getAttribute(attributeName);
    }
    
//    public static void main(String[] args)
//    {
//      System.out.println(escape("abc<div>hao'123\"&abc"));
//      System.out.println(unescape("abc&lt;div&gt;hao&apos;123&quot;&amp;abc&a"));
//    }

    final static String[] DICT = new String[]{
            /* char -> String*/
            "<",        "&lt;",
            ">",        "&gt;",
            "&",        "&amp;",
            "'",        "&apos;",
            "\"",       "&quot;"
            };

    /**
     * 将字符串内特殊字符转义为XML节点格式
     * 
     * @param src 源字符串
     * 主要转义以下五个符号
     * <table>
     * <tr><td>转义前字符</td><td>转义后字符串</td></tr>
     * <tr><td>&lt;</td><td>&amp;lt;</td></tr>
     * <tr><td>&gt;</td><td>&amp;gt;</td></tr>
     * <tr><td>&amp;</td><td>&amp;amp;</td></tr>
     * <tr><td>'</td><td>&amp;apos;</td></tr>
     * <tr><td>&quot;</td><td>&amp;quot;</td></tr>
     * </table>
     * @return 返回值：转义后的字符串
     * @see #unescape(String)
     */
    public static String escape(String src)
    {
        if(src == null)
            return null;
        StringBuffer result = new StringBuffer();
        for(int i = 0; i < src.length(); i++)
        {
            boolean replaced = false;
            for(int j = 0; j < DICT.length; j += 2)
            {
                if(src.charAt(i) == DICT[j].charAt(0))
                {
                    result.append(DICT[j + 1]);
                    replaced = true;
                    break;
                }
            }
            if(!replaced)
            {
                result.append(src.charAt(i));
            }
        }
        return result.toString();
    }

    /**
     * 将escape过的字符串恢复成原来的字符串格式，跟escape功能相反。
     * 
     * @param src 要回复的字符串
     * @return 返回值：转义前的字符串
     * @see #escape(String)
     */
    public static String unescape(String src)
    {
        if(src == null)
            return null;
        StringBuffer result = new StringBuffer();
        for(int i = 0; i < src.length(); i++)
        {
            boolean replaced = false;
            if(src.charAt(i) == '&')
            {
                for(int j = 0; j < DICT.length; j += 2)
                {
                    if(src.length() - i < DICT[j+1].length())   // 防止下面取子串越界
                    {
                        continue;
                    }
                    if(DICT[j+1].equals(src.substring(i, i + DICT[j+1].length())))
                    {
                        result.append(DICT[j]);
                        i += DICT[j + 1].length() - 1;
                        replaced = true;
                        break;
                    }
                }
            }
            if(!replaced)
            {
                result.append(src.charAt(i));
            }
        }
        return result.toString();
    }

    /**
     * @deprecated
     * 将XML文本输出为有层次感的格式
     * 
     * @param src 要格式化的XML字符串
     * @return 返回值：有层次感的XML格式文本
     */
    public static String formatted(String src)
    {
        return src;
    }
}
