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
 * XML������
 * 
 */

public class XmlUtil
{

    /**
     * ����xml�ڵ��ȡ����ڵ�
     * 
     * @param ����˵��
     *            Element ��xml��һ���ڵ�
     * @param ����˵��
     *            String ���ڵ�����
     * @return ����ֵ��List<Node>�����ӽڵ��list
     * @exception �쳣��ע�ͳ�ʲô�����»�����ʲô�����쳣
     * @see �ο���JavaDoc
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
     * ����xml�ڵ��ȡ����ڵ�
     * 
     * @param ����˵��
     *            Element ��xml��һ���ڵ�
     * @param ����˵��
     *            String ���ڵ�����
     * @return ����ֵ��Element �ӽڵ�
     * @exception �쳣��ע�ͳ�ʲô�����»�����ʲô�����쳣
     * @see �ο���JavaDoc
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
     * ����xml�ڵ��ȡ����ڵ�ֵ
     * 
     * @param ����˵��
     *            Element ��xml��һ���ڵ�
     * @param ����˵��
     *            String ���ڵ�����
     * @return ����ֵ������ڵ��ֵ
     * @exception �쳣��ע�ͳ�ʲô�����»�����ʲô�����쳣
     * @see �ο���JavaDoc
     */
    public static String getChildElementValueByTagName(Element ele, String childEleName)
    {

        Element child = getChildElementByTagName(ele, childEleName);
        return (child != null ? getTextValue(child) : null);
    }

    /**
     * ����xml�ڵ��ȡ�ڵ�ֵ
     * 
     * @param ����˵��
     *            Element ��xml��һ���ڵ�
     * @return ����ֵ���ڵ��ֵ
     * @exception �쳣��ע�ͳ�ʲô�����»�����ʲô�����쳣
     * @see �ο���JavaDoc
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
     * ���ݶ�����ַ�������һ��xml��document����
     * 
     * @param data XML�����ַ���
     * @return ����ֵ��Document xml��domԪ��
     * @exception �쳣��ע�ͳ�ʲô�����»�����ʲô�����쳣
     * @see �ο���JavaDoc
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
     * ����xml�ڵ㣬���ڵ����֡��ӽڵ����ֻ�ȡ����ڵ�
     * 
     * @param ����˵��
     *            Document ��xml��һ���ڵ�
     * @param ����˵��
     *            parentName �����ڵ�����
     * @param ����˵��
     *            eleName ���ӽڵ�����
     * @return ����ֵ���ڵ��Ӧ��valueֵ
     * @exception �쳣��ע�ͳ�ʲô�����»�����ʲô�����쳣
     * @see �ο���JavaDoc
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
     * ����xml�ڵ㣬�ڵ������ȡ���ڵ�
     * 
     * @param ����˵��
     *            Document ��xml��һ���ڵ�
     * @param ����˵��
     *            eleName ���ӽڵ�����
     * @return ����ֵ���ڵ��Ӧ��valueֵ
     * @exception �쳣��ע�ͳ�ʲô�����»�����ʲô�����쳣
     * @see �ο���JavaDoc
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
     * ����xml�ڵ㣬���ڵ����֡��ӽڵ����ֻ�ȡ����ڵ�
     * 
     * @param ����˵��
     *            Element ��xml��һ���ڵ�
     * @param ����˵��
     *            parentName �����ڵ�����
     * @param ����˵��
     *            eleName ���ӽڵ�����
     * @return ����ֵ���ڵ��Ӧ��valueֵ
     * @exception �쳣��ע�ͳ�ʲô�����»�����ʲô�����쳣
     * @see �ο���JavaDoc
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
     * ����xml�ڵ㣬���ڵ����֡��ӽڵ����ֻ�ȡ���ӽڵ�
     * 
     * @param element xml��һ���ڵ�
     * @param parentName ���ڵ�����
     * @param ����˵��
     *            eleName ���ӽڵ�����
     * @return ����ֵ���ڵ��Ӧ��valueֵ
     * @exception �쳣��ע�ͳ�ʲô�����»�����ʲô�����쳣
     * @see �ο���JavaDoc
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
     * ����xml�ڵ㣬���ڵ����֡��ӽڵ����ֻ�ȡ���ӽڵ�
     * 
     * @param ����˵��
     *            Element ��xml��һ���ڵ�
     * @param ����˵��
     *            parentName �����ڵ�����
     * @param ����˵��
     *            eleName ���ӽڵ�����
     * @return ����ֵ��List<String>�ڵ��Ӧ��valueֵ
     * @exception �쳣��ע�ͳ�ʲô�����»�����ʲô�����쳣
     * @see �ο���JavaDoc
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
     * ����xml�ڵ㣬���ڵ����֡��ӽڵ����ֻ�ȡ���ӽڵ�
     * 
     * @param ����˵��
     *            Element ��xml��һ���ڵ�
     * @param ����˵��
     *            parentName �����ڵ�����
     * @param ����˵��
     *            eleName ���ӽڵ�����
     * @return ����ֵ��List<Node>�ڵ��Ӧ��valueֵ
     * @exception �쳣��ע�ͳ�ʲô�����»�����ʲô�����쳣
     * @see �ο���JavaDoc
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
     * ����xml�ڵ��ȡ����ڵ�
     * 
     * @param ����˵��
     *            Element ��xml��һ���ڵ�
     * @param ����˵��
     *            String ���ڵ�����
     * @return ����ֵ��List<String>�����ӽڵ��list
     * @exception �쳣��ע�ͳ�ʲô�����»�����ʲô�����쳣
     * @see �ο���JavaDoc
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
     * ����xml�ڵ��ȡ��Ӧ��������
     * 
     * @param ����˵��
     *            Element ��xml��һ���ڵ�
     * @param ����˵��
     *            String ����������
     * @return ����ֵ��String ������ֵ
     * @exception �쳣��ע�ͳ�ʲô�����»�����ʲô�����쳣
     * @see �ο���JavaDoc
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
     * ���ַ����������ַ�ת��ΪXML�ڵ��ʽ
     * 
     * @param src Դ�ַ���
     * ��Ҫת�������������
     * <table>
     * <tr><td>ת��ǰ�ַ�</td><td>ת����ַ���</td></tr>
     * <tr><td>&lt;</td><td>&amp;lt;</td></tr>
     * <tr><td>&gt;</td><td>&amp;gt;</td></tr>
     * <tr><td>&amp;</td><td>&amp;amp;</td></tr>
     * <tr><td>'</td><td>&amp;apos;</td></tr>
     * <tr><td>&quot;</td><td>&amp;quot;</td></tr>
     * </table>
     * @return ����ֵ��ת�����ַ���
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
     * ��escape�����ַ����ָ���ԭ�����ַ�����ʽ����escape�����෴��
     * 
     * @param src Ҫ�ظ����ַ���
     * @return ����ֵ��ת��ǰ���ַ���
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
                    if(src.length() - i < DICT[j+1].length())   // ��ֹ����ȡ�Ӵ�Խ��
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
     * ��XML�ı����Ϊ�в�θеĸ�ʽ
     * 
     * @param src Ҫ��ʽ����XML�ַ���
     * @return ����ֵ���в�θе�XML��ʽ�ı�
     */
    public static String formatted(String src)
    {
        return src;
    }
}
