package com.sys.comm.dingtalk;


import com.sys.comm.dingtalk.message.Message;
import com.sys.comm.util.HttpClientUtil;
import com.sys.comm.util.JsonResult;
import org.apache.http.HttpStatus;

import java.io.IOException;

/**
 * Created by dustin on 2017/3/17.
 */
public class DingtalkChatbotClient {
//    SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(3000).build();
//
//    HttpClient httpclient = HttpClients.custom().setDefaultSocketConfig(socketConfig).build();

    public JsonResult send(String webhook, Message message) throws IOException{
        return  HttpClientUtil.sendHttpPostJson(webhook, message.toJsonString(),HttpStatus.SC_OK);
    }

}


