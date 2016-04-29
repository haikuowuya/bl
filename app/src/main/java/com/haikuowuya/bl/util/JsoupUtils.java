package com.haikuowuya.bl.util;

import com.haikuowuya.bl.Constants;
import com.haikuowuya.bl.URLConstants;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.DataUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * 说明:
 * 日期:2016/4/15
 * 时间:10:55
 * 创建者：hkwy
 * 修改者：
 **/
public class JsoupUtils
{

    public static Document searchFuzzyLineDocument(String lineNo)
    {
        Document document = null;
        try
        {
            LinkedHashMap<String, String> linkedHashMap = fetchPostParams();
            linkedHashMap.put(Constants.LINE_NO, lineNo);
            SoutUtils.out("searchFuzzyLineDocument lineHref = " + URLConstants.WEB_LINE_SEARCH);
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(URLConstants.WEB_LINE_SEARCH).openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            OutputStream outputStream = httpURLConnection.getOutputStream();
            String postStr = mapToString(linkedHashMap);

            outputStream.write(postStr.getBytes());
            int responseCode = httpURLConnection.getResponseCode();
            if (httpURLConnection != null && responseCode == 200)
            {
                InputStream inputStream = httpURLConnection.getInputStream();
                document = DataUtil.load(inputStream, "utf-8", "");
            }
//            document = getRequestConnect(URLConstants.LINE_SEARCH).data(linkedHashMap).post();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return document;
    }
    public  static  String mapToString(HashMap<String, String> map)
    {
        StringBuffer stringBuffer = new StringBuffer();
        if(null != map &&!map.isEmpty())
        {
            for (String key : map.keySet())
            {
                stringBuffer.append(key);
                stringBuffer.append("=");
                stringBuffer.append(map.get(key));
                stringBuffer.append("&");
            }
            stringBuffer.deleteCharAt(stringBuffer.length()-1);
        }
        SoutUtils.out("post提交参数 = " + stringBuffer );
        return  stringBuffer.toString();
    }

    private static LinkedHashMap<String, String> fetchPostParams()
    {
        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>();
        try
        {
            SoutUtils.out("fetchPostParams lineHref = " + URLConstants.WEB_LINE_SEARCH);
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(URLConstants.WEB_LINE_SEARCH).openConnection();
            int responseCode = httpURLConnection.getResponseCode();
            Document document = null;
            if (httpURLConnection != null && responseCode == 200)
            {
                InputStream inputStream = httpURLConnection.getInputStream();
                document = DataUtil.load(inputStream, "utf-8", "");
            }
            if (null != document)
            {
                Elements elements = document.getElementsByTag("input");
                if (null != elements && elements.size() > 0)
                {
                    for (Element element : elements)
                    {
                        String name = element.attr("name");
                        String value = element.attr("value");
                        SoutUtils.out("name = " + name + " value = " + value);
                        linkedHashMap.put(name, value);
                    }
                }
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return linkedHashMap;
    }



    private static Connection getRequestConnect(String url)
    {
        return getRequestConnect(url, 40 * 1000);
    }

    private static Connection getRequestConnect(String url, int timeOut)
    {
        return Jsoup.connect(URLConstants.LINE_SEARCH).timeout(timeOut);
    }
}
