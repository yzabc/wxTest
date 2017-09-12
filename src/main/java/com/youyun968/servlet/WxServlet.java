package com.youyun968.servlet;

import com.youyun968.entity.TextMessage;
import com.youyun968.util.CheckUtil;
import com.youyun968.util.MessageUtil;
import org.dom4j.DocumentException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

/**
 * Created by DELL on 2017/9/12.
 */
@WebServlet(urlPatterns = "/wx")
public class WxServlet extends HttpServlet {
    @java.lang.Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String signature =req.getParameter("signature");
        String timestamp =req.getParameter("timestamp");
        String nonce =req.getParameter("nonce");
        String echostr =req.getParameter("echostr");
        PrintWriter writer = resp.getWriter();
        if (CheckUtil.checkSignature(signature,timestamp,nonce)){
            writer.print(echostr);
        }
    }

    /**
     * 对话开发为post请求
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter writer = resp.getWriter();
        try {
            Map<String, String> map = MessageUtil.xmlToMap(req);
            String fromUserName = map.get("FromUserName");
            String toUserName = map.get("ToUserName");
            String msgType = map.get("MsgType");
            String content = map.get("Content");
            String s=null;
            if ("text".equals(msgType)){
                TextMessage textMessage = new TextMessage();
                textMessage.setToUserName(fromUserName);
                textMessage.setFromUserName(toUserName);
                textMessage.setMsgType("text");
                textMessage.setCreateTime(new Date().getTime());
                textMessage.setContent("您发送的消息为:"+content);
                s = MessageUtil.textMessageToXml(textMessage);
                System.out.println(s);
            }
            writer.print(s);
        } catch (DocumentException e) {
            e.printStackTrace();
        }finally {
            writer.close();
        }


    }
}
