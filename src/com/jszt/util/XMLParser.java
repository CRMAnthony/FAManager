package com.jszt.util;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

import com.jszt.client.PassRecord;



public class XMLParser
{
    private static Logger logger = Logger.getLogger(XMLParser.class);
    
    public static PassRecord xmlParser(String xmlDoc) {
        
        StringReader read = new StringReader(xmlDoc);
        
        InputSource source = new InputSource(read);
        //新建SAXBuilder
        SAXBuilder sb = new SAXBuilder();
        PassRecord passRecord = new PassRecord();
        try {
            
            Document doc = sb.build(source);
            //根节点
            Element root = doc.getRootElement();

            if (root.getName().equals("record")) {
                List<?> child = root.getChildren();
                
                Element currentElement = null;
                for (int i = 0; i < child.size(); i++) {
                    currentElement = (Element) child.get(i);
                    String emtName = currentElement.getName();
                    String emtValue = currentElement.getText();
                    if (emtName.equals("device_id")) {
                        passRecord.setDeviceId(emtValue);
                    }
                    if (emtName.equals("time")) {
                        passRecord.setTime(emtValue);
                    }
                    if (emtName.equals("device_type")) {
                        passRecord.setDeviceType(emtValue);
                    }
                    if (emtName.equals("plate")) {
                        passRecord.setPlate(emtValue);
                    }
                    if (emtName.equals("color")) {
                        passRecord.setColor(emtValue);
                    }
                    if (emtName.equals("type")) {
                        passRecord.setType(emtValue);
                    }
                    if (emtName.equals("brand")) {
                        passRecord.setBrand(emtValue);
                    }
                    if (emtName.equals("direction")) {
                        passRecord.setDirection(emtValue);
                    }
                    if (emtName.equals("lane")) {
                        passRecord.setLane(emtValue);
                    }
                    if (emtName.equals("speed")) {
                        passRecord.setSpeed(emtValue);
                    }
                    if (emtName.equals("length")) {
                        passRecord.setLength(emtValue);
                    }
                    if (emtName.equals("url_1")) {
                        passRecord.setUrl1(emtValue);
                    }
                    if (emtName.equals("url_2")) {
                        passRecord.setUrl2(emtValue);
                    }
                    if (emtName.equals("url_3")) {
                        passRecord.setUrl3(emtValue);
                    }
                    
                }
            }  
        } catch (JDOMException e) {
            logger.error("xmlparser error",e);
        } catch (IOException e) {
            logger.error("xmlparser error",e);
        }
        return passRecord;
    }
}
