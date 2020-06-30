package com.dchealth.hl7demo.jaxb;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class BingliParser {
    public static void main(String[] args) throws ParserConfigurationException, SAXException {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ClinicalDocument.class);//创建JAXBContext对象，注意一定要传@XmlRootElement的所标记的类的类
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();//
            SAXParserFactory sax = SAXParserFactory.newInstance();
            sax.setNamespaceAware(false);
            XMLReader xmlReader = sax.newSAXParser().getXMLReader();
            FileReader reader = new FileReader(new File("bingli.xml"));
            SAXSource saxSource = new SAXSource(xmlReader, new InputSource(reader));
            ClinicalDocument document = (ClinicalDocument) unmarshaller.unmarshal(saxSource);//将xml转换成实体对象
            System.out.println(document.getRealmcode().getCode());//输出ID属性
            System.out.println(document.getRealmcode().getValue());//输出ID属性
        } catch (JAXBException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
