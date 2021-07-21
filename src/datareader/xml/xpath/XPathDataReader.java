//Подготовка xml-документа с возможностью проверки соответствия его xml-схеме,
//и получение данных из подготовленного xml-документа посредством XPath-выражений

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datareader.xml.xpath;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author TitarX
 */
public class XPathDataReader
{

    private Document xmlDocument;

    private XPathDataReader(Document xmlDocument)
    {
        this.xmlDocument=xmlDocument;
    }

    //Создать объект этого класса, содержащий xml-документ из xml-файла
    public static XPathDataReader newInstance(File xmlFile) throws ParserConfigurationException,SAXException,IOException
    {
        Document xmlDocument=DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile);
        return new XPathDataReader(xmlDocument);
    }

    //Создать объект этого класса, содержащий xml-документ из строки
    public static XPathDataReader newInstance(String xmlString) throws ParserConfigurationException,SAXException,IOException
    {
        Document xmlDocument=DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(xmlString)));
        return new XPathDataReader(xmlDocument);
    }

    //Создать объект этого класса, содержащий xml-документ из xml-файла,
    //проверив соответствие xml-схеме из xsd-файла
    public static XPathDataReader newInstance(File xmlFile,File schemaFile) throws ParserConfigurationException,SAXException,IOException
    {
        SchemaFactory schemaFactory=SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        Schema schema=schemaFactory.newSchema(schemaFile);
        Validator validator=schema.newValidator();
        Source source=new StreamSource(xmlFile);
        validator.validate(source);

        Document xmlDocument=DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile);
        return new XPathDataReader(xmlDocument);
    }

    //Создать объект этого класса, содержащий xml-документ из строки,
    //проверив соответствие xml-схеме из xsd-файла
    public static XPathDataReader newInstance(String xmlStrng,File schemaFile) throws ParserConfigurationException,SAXException,IOException
    {
        SchemaFactory schemaFactory=SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        Schema schema=schemaFactory.newSchema(schemaFile);
        Validator validator=schema.newValidator();
        Source source=new StreamSource(new StringReader(xmlStrng));
        validator.validate(source);

        Document xmlDocument=DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(xmlStrng)));
        return new XPathDataReader(xmlDocument);
    }

    //Создать объект этого класса, содержащий xml-документ из xml-файла,
    //проверив соответствие xml-схеме из строки
    public static XPathDataReader newInstance(File xmlFile,String schemaString) throws ParserConfigurationException,SAXException,IOException
    {
        SchemaFactory schemaFactory=SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        Schema schema=schemaFactory.newSchema(new StreamSource(new StringReader(schemaString)));
        Validator validator=schema.newValidator();
        Source source=new StreamSource(xmlFile);
        validator.validate(source);

        Document xmlDocument=DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile);
        return new XPathDataReader(xmlDocument);
    }

    //Создать объект этого класса, содержащий xml-документ из строки,
    //проверив соответствие xml-схеме из строки
    public static XPathDataReader newInstance(String xmlStrng,String schemaString) throws ParserConfigurationException,SAXException,IOException
    {
        SchemaFactory schemaFactory=SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        Schema schema=schemaFactory.newSchema(new StreamSource(new StringReader(schemaString)));
        Validator validator=schema.newValidator();
        Source source=new StreamSource(new StringReader(xmlStrng));
        validator.validate(source);

        Document xmlDocument=DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(xmlStrng)));
        return new XPathDataReader(xmlDocument);
    }

    //Получить данные из xml-документа этого класса по XPath-выражению
    public String readData(String xPathString) throws XPathExpressionException
    {
        XPath xPath=XPathFactory.newInstance().newXPath();
        XPathExpression xPathExpression=xPath.compile(xPathString);
        String result=xPathExpression.evaluate(xmlDocument);

        return result;
    }
}
