package com.dchealth.hl7demo.sax;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SAXTest {
    private static void read() {
        //获取一个SAXParserFactory实例
        SAXParserFactory factory = SAXParserFactory.newInstance();
        //通过factory获取SAXParser实例
        try {
            SAXParser parser = factory.newSAXParser();
            //创建SAXParserHandler对象
            SAXParserHandler handler = new SAXParserHandler();
            parser.parse("books.xml", handler);
            System.out.println("~！~！~！共有" + handler.getBookList().size()
                    + "本书");
            for (Book book : handler.getBookList()) {
                System.out.println(book.getId());
                System.out.println(book.getName());
                System.out.println(book.getAuthor());
                System.out.println(book.getYear());
                System.out.println(book.getPrice());
                System.out.println(book.getLanguage());
                System.out.println("----finish----");
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void write() throws TransformerConfigurationException, IOException {
        SAXTransformerFactory tff = (SAXTransformerFactory)SAXTransformerFactory.newInstance();
        TransformerHandler handler = tff.newTransformerHandler();
        Transformer tr = handler.getTransformer();
        //设置xml的编码
        tr.setOutputProperty(OutputKeys.ENCODING,"UTF-8");
//设置xml是否换行
        tr.setOutputProperty(OutputKeys.INDENT,"yes");
        File file = new File("book1.xml");
        if(!file.exists()){
            file.createNewFile();
        }
        Result result = new StreamResult(new FileOutputStream(file));
        handler.setResult(result);
    }

    public static void main(String...args) throws IOException, TransformerConfigurationException {
        read();
        write();
    }

}