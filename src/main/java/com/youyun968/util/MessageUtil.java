package com.youyun968.util;

import com.thoughtworks.xstream.XStream;
import com.youyun968.entity.TextMessage;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by DELL on 2017/9/12.
 */
public class MessageUtil {
    /**
     * xml转map
     * @param req
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    public static Map<String,String> xmlToMap(HttpServletRequest req) throws IOException, DocumentException {
        HashMap<String, String> map = new HashMap<String, String>();
        SAXReader saxReader = new SAXReader();
        ServletInputStream inputStream = req.getInputStream();
        Document document = saxReader.read(inputStream);

        Element rootElement = document.getRootElement();

        List <Element> elements = rootElement.elements();

        for (Element el : elements){
            map.put(el.getName(),el.getText());
        }
        inputStream.close();
        return map;
    }

    /**
     * 文本消息对象转换为xml
     * @param textMessage
     * @return
     */
    public static String textMessageToXml (TextMessage textMessage){
        XStream xStream = new XStream();
        xStream.alias("xml",textMessage.getClass());
        return xStream.toXML(textMessage);
    }
}
