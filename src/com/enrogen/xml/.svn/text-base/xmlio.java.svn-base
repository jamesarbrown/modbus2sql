//////////////////////////////////////////////////////////////////////////
//com.enrogen.xml
//2010 - James A R Brown
//Released under GPL V2
//////////////////////////////////////////////////////////////////////////
package com.enrogen.xml;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

public class xmlio implements xml {

    private Document xmlDocument = null;
    private Document newXmlDocument = null;
    private String filename = null;
    private String newXmlFilename = null;
    private Element elements = null;

    public void setFileName(String fn) {
        filename = fn;
    }

    public void parseXmlFile() {
        try {
            // Create a builder factory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(XML_VALIDATION);

            // Create the builder and parse the file
            xmlDocument = factory.newDocumentBuilder().parse(new File(filename));
        } catch (SAXException e) {
            System.err.println("com.enrogen.xml - parseXmlFile SAXException");
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            System.err.println("com.enrogen.xml - parseXmlFile ParserConfigurationException");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("com.enrogen.xml - parseXmlFile IOException");
            e.printStackTrace();
        }
    }

    public String readXmlTagValue(String MainNode, String SubNode) {
        try {
            //Main Node
            NodeList nodeLst = xmlDocument.getElementsByTagName(MainNode);
            Node fstNode = nodeLst.item(0);
            Element fstElmnt = (Element) fstNode;

            //SubNode
            NodeList fstNmElmntLst = fstElmnt.getElementsByTagName(SubNode);
            Element fstNmElmnt = (Element) fstNmElmntLst.item(0);

            NodeList fstNm = fstNmElmnt.getChildNodes();
            String value = fstNm.item(0).getNodeValue().toString();
            return value;
        } catch (Exception e) {
            System.err.println("com.enrogen.xml - Error readXmlTagValue");
            System.err.println("com.enrogen.xml - MainNode Requested : " + MainNode);
            System.err.println("com.enrogen.xml - SubNode Requested : " + MainNode);
            e.printStackTrace();
            return "";
        }
    }

    public void createNewXmlFile(String filename) {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            newXmlDocument = builder.newDocument();
            newXmlFilename = filename;
        } catch (Exception e) {
            System.err.println("com.enrogen.xml - Error createNewXmlFile");
            System.err.println("com.enrogen.xml - filename : " + filename);
        }
    }

    public void addRootNode(String Node) {
        try {
            Element ElementNode = newXmlDocument.createElement(Node);
            newXmlDocument.appendChild(ElementNode);
        } catch (Exception e) {
            System.err.println("com.enrogen.xml - Error addRootNode");
            System.err.println("com.enrogen.xml - ElementNode: " + Node);
            e.printStackTrace();
        }
    }

    public void addSubNode(String RootNode, String Node) {
        try {
            NodeList nodeLst = newXmlDocument.getElementsByTagName(RootNode);
            Node fstNode = nodeLst.item(0);
            Element rootNode = (Element) fstNode;

            newXmlDocument.removeChild(rootNode);
            Element ElementNode = newXmlDocument.createElement(Node);
            rootNode.appendChild(ElementNode);
            newXmlDocument.appendChild(rootNode);
        } catch (Exception e) {
            System.err.println("com.enrogen.xml - Error addSubNode");
            System.err.println("com.enrogen.xml - RootNode: " + RootNode);
            System.err.println("com.enrogen.xml - SubNode: " + Node);
            e.printStackTrace();
        }
    }

    //Overload
    public void addSubNode(String MainNode, String Node, String Value) {
        try {
            NodeList nodeLst = newXmlDocument.getElementsByTagName(MainNode);
            Node fstNode = nodeLst.item(0);
            Element mainElement = (Element) fstNode;

            //Get the parent node
            Node parentNode = mainElement.getParentNode();
            Element parentElement = (Element) parentNode;
            parentNode.removeChild(mainElement);

            //Create a new Node
            Element subElement = newXmlDocument.createElement(Node);
            Text subText = newXmlDocument.createTextNode(Value);
            subElement.appendChild(subText);
            mainElement.appendChild(subElement);
            parentElement.appendChild(mainElement);

            newXmlDocument.normalizeDocument();
        } catch (Exception e) {
            System.err.println("com.enrogen.xml - Error addSubNode (Overloaded)");
            System.err.println("com.enrogen.xml - RootNode: " + MainNode);
            System.err.println("com.enrogen.xml - SubNode: " + Node);
            System.err.println("com.enrogen.xml - Value: " + Value);
            e.printStackTrace();
        }
    }

    public boolean writeXMLFile() {
        boolean success = false;
        try {//Write the DOM out an XML file
            // Prepare the DOM document for writing
            Source source = new DOMSource(newXmlDocument);

            // Prepare the output file
            File file = new File(newXmlFilename);
            Result result = new StreamResult(file);

            // Write the DOM document to the file
            Transformer xformer = TransformerFactory.newInstance().newTransformer();
            xformer.setOutputProperty(OutputKeys.INDENT, "yes");
            xformer.setOutputProperty(OutputKeys.METHOD, "xml");
            xformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            xformer.transform(source, result);

            success = true;
        } catch (TransformerConfigurationException e) {
            System.err.println("com.enrogen.xml - Error writeXMLFile");
            e.printStackTrace();
        } catch (TransformerException e) {
            System.err.println("com.enrogen.xml - Error writeXMLFile");
            e.printStackTrace();
        }
        return success;
    }

    public List getTagList(String MainNode) {
        NodeList nodeLst = xmlDocument.getElementsByTagName(MainNode).item(0).getChildNodes();
        int count = nodeLst.getLength();
        List tagList = new LinkedList();
        for (int i = 0; i < count; i++) {
            if (nodeLst.item(i) instanceof Element) {
                tagList.add(nodeLst.item(i).getNodeName().toString());
            }
        }
        return tagList;
    }

    public void addValueToNode(String Node, String ValueName, String Value) {
        NodeList nodeLst = newXmlDocument.getElementsByTagName(Node);
        Node node = nodeLst.item(0);
        Element nodeElement = (Element) node;
        nodeElement.setAttribute(ValueName, Value);
    }

    public String getAttribute(String Node, String Attribute) {
        NodeList nodeLst = xmlDocument.getElementsByTagName(Node);
        Node fstNode = nodeLst.item(0);
        Element fstElmnt = (Element) fstNode;
        return fstElmnt.getAttribute(Attribute);
    }
}
