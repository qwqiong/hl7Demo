package com.dchealth.hl7demo.sax;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

public class SAX {
    public static void main(String[] args) {
        //1、获取一个SAXParserFactory的实例
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            //2、通过factory获取SAXParser的实例
            SAXParser parser = factory.newSAXParser();
            //3、创建SAXParserHandler对象
            SAXParserHandler handler = new SAXParserHandler();
            parser.parse("books.xml", handler);
        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException | org.xml.sax.SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
