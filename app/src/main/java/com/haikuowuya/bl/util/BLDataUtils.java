package com.haikuowuya.bl.util;

import android.text.TextUtils;
import android.webkit.URLUtil;

import com.haikuowuya.bl.Constants;
import com.haikuowuya.bl.URLConstants;
import com.haikuowuya.bl.model.LineStopItem;
import com.haikuowuya.bl.model.SearchLine;
import com.haikuowuya.bl.model.SearchLineItem;
import com.haikuowuya.bl.model.StopItem;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 * 说明:
 * 日期:2016/4/15
 * 时间:19:04
 * 创建者：hkwy
 * 修改者：
 **/
public class BLDataUtils
{
    /***
     * 从HTMl文档中获取查询公交线路的post提交参数
     * @param document
     * @param lineNo
     * @return
     */
    public  static LinkedHashMap<String, String> fetchPostParamsFromDocument(Document document, String lineNo)
    {
        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>();
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
        linkedHashMap.put(Constants.LINE_NO,lineNo);
        return linkedHashMap;
    }
    /***
     * 将的html文件转换为想要的数据
     * @param document
     * @return
     */
    public static LinkedList<SearchLineItem> htmlToSearchLine(Document document)
    {
        final LinkedList<SearchLineItem> searchLines = new LinkedList<>();
        if (null != document)
        {
            Elements bodyElement = document.getElementsByTag("tbody");
            if(bodyElement != null && !bodyElement.isEmpty())
            {
                Element element = bodyElement.get(0);
                Elements trElements = element.getElementsByTag("tr");
                if (null != trElements && !trElements.isEmpty())
                {
                    if (trElements.size() > 1)
                    {
                        for (Element tmpElement : trElements)
                        {
                            Elements tdElements = tmpElement.getElementsByTag("td");
                            /* <td><a href="APTSLine.aspx?cid=175ecd8d-c39d-4116-83ff-109b946d7cb4&amp;LineGuid=9d090af5-c5c6-4db8-b34e-2e8af4f63216&amp;LineInfo=1(公交一路新村首末站)">1</a></td><td>公交一路新村首末站</td> */
                            // SoutUtils.out(tdElements);
                            if (null != tdElements && tdElements.size() == 2)
                            {
                            //    SearchLine searchLine = new SearchLine();
                                SearchLineItem searchLineItem = new SearchLineItem();
                                String href;
                                String lineName;
                                String lineNo;
                                Element aElement = tdElements.get(0).getElementsByTag("a").get(0);
                                lineNo = aElement.text();
                                href = URLConstants.BUS_LINE_QUERY_PREFIX + aElement.attr("href");
                                lineName = tdElements.get(1).text();
                                /*lineNo = 1 href = http://www.szjt.gov.cn/BusQuery/APTSLine.aspx?cid=175ecd8d-c39d-4116-83ff-109b946d7cb4&LineGuid=9d090af5-c5c6-4db8-b34e-2e8af4f63216&LineInfo=1(公交一路新村首末站) lineName = 公交一路新村首末站*/
                                 SoutUtils.out("lineNo = " + lineNo+ " href = " + href + " lineName = " + lineName );
//                                searchLine.lineNo = lineNo;
//                                searchLine.lineName = lineName;
//                                searchLine.lineHref = href;
                                searchLineItem.LName = lineNo;
                                searchLineItem.LDirection = lineName;
                                searchLineItem.Guid =getGuidFromUrl(href);
                                searchLines.add(searchLineItem);
                            }
                        }
                    }
                }
            }
        }
        return  searchLines;
    }

    /***
     * 从获取的网址中解析出想要的GUID
     * @param href
     * @return
     */
    private static String getGuidFromUrl(String href)
    {
        String result = "";
        /*http://www.szjt.gov.cn/BusQuery/APTSLine.aspx?cid=175ecd8d-c39d-4116-83ff-109b946d7cb4&LineGuid=9d090af5-c5c6-4db8-b34e-2e8af4f63216&LineInfo=1(公交一路新村首末站) lineName = 公交一路新村首末站*/
        if(!TextUtils.isEmpty(href) && href.contains(Constants.AMP))
        {
            for(String str: href.split(Constants.AMP))
            {
                if(str.contains(Constants.LINE_GUID))
                {
                    result = str.replace(Constants.LINE_GUID,"");
                }
            }
        }
        return result;
    }

    public static LinkedList<LineStopItem> htmlToLineStop(Document document)
    {
        final LinkedList<LineStopItem> lineStopItems = new LinkedList<>();
        if (null != document)
        {
            Elements bodyElement = document.getElementsByTag("tbody");
            if(bodyElement != null && !bodyElement.isEmpty())
            {
                Element element = bodyElement.get(0);
                Elements trElements = element.getElementsByTag("tr");
                if (null != trElements && !trElements.isEmpty())
                {
                    if (trElements.size() > 1)
                    {
                        for (Element tmpElement : trElements)
                        {
                            Elements tdElements = tmpElement.getElementsByTag("td");
                            /* <td><a href="APTSLine.aspx?cid=175ecd8d-c39d-4116-83ff-109b946d7cb4&amp;LineGuid=9d090af5-c5c6-4db8-b34e-2e8af4f63216&amp;LineInfo=1(公交一路新村首末站)">1</a></td><td>公交一路新村首末站</td> */
                            // SoutUtils.out(tdElements);
                            if (null != tdElements && tdElements.size() == 4)
                            {
                             LineStopItem lineStopItem = new LineStopItem();
                                String href;
                                String lineName;
                                String sCode ;
                                String inTime;
                                Element aElement = tdElements.get(0).getElementsByTag("a").get(0);
                                lineName = aElement.text();
                                href = URLConstants.BUS_LINE_QUERY_PREFIX + aElement.attr("href");
                                sCode = tdElements.get(1).text();
                                inTime = tdElements.get(3).text();
                                /*lineNo = 1 href = http://www.szjt.gov.cn/BusQuery/APTSLine.aspx?cid=175ecd8d-c39d-4116-83ff-109b946d7cb4&LineGuid=9d090af5-c5c6-4db8-b34e-2e8af4f63216&LineInfo=1(公交一路新村首末站) lineName = 公交一路新村首末站*/
                                // SoutUtils.out("lineNo = " + lineNo+ " href = " + href + " lineName = " + lineName );
                                lineStopItem.SCode = sCode;
                                lineStopItem.SName = lineName;
                                lineStopItem.InTime = inTime;
                                if(!TextUtils.isEmpty(inTime))
                                {
                                    lineStopItem.s_num_str = Constants.IN_STOP + "\n" + inTime;
                                }
                                lineStopItems.add(lineStopItem);
                            }
                        }
                    }
                }
            }
        }
        return  lineStopItems;
    }



    public static LinkedList<StopItem> htmlToStop(Document document)
    {
        final LinkedList<StopItem> lineStopItems = new LinkedList<>();
        if (null != document)
        {
            Elements bodyElement = document.getElementsByTag("tbody");
            if(bodyElement != null && !bodyElement.isEmpty())
            {
                Element element = bodyElement.get(0);
                Elements trElements = element.getElementsByTag("tr");
                if (null != trElements && !trElements.isEmpty())
                {
                    if (trElements.size() > 1)
                    {
                        for (Element tmpElement : trElements)
                        {
                            Elements tdElements = tmpElement.getElementsByTag("td");
                            /* <td><a href="APTSLine.aspx?cid=175ecd8d-c39d-4116-83ff-109b946d7cb4&amp;LineGuid=9d090af5-c5c6-4db8-b34e-2e8af4f63216&amp;LineInfo=1(公交一路新村首末站)">1</a></td><td>公交一路新村首末站</td> */
                            // SoutUtils.out(tdElements);
                            if (null != tdElements && tdElements.size() == 4)
                            {
                                LineStopItem lineStopItem = new LineStopItem();
                                StopItem stopItem = new StopItem();
                                String href;
                                String lineName;
                                String sCode ;
                                String inTime;
                                Element aElement = tdElements.get(0).getElementsByTag("a").get(0);
                                lineName = aElement.text();
                                href = URLConstants.BUS_LINE_QUERY_PREFIX + aElement.attr("href");
                                sCode = tdElements.get(1).text();
                                inTime = tdElements.get(3).text();
                                /*lineNo = 1 href = http://www.szjt.gov.cn/BusQuery/APTSLine.aspx?cid=175ecd8d-c39d-4116-83ff-109b946d7cb4&LineGuid=9d090af5-c5c6-4db8-b34e-2e8af4f63216&LineInfo=1(公交一路新村首末站) lineName = 公交一路新村首末站*/
                                // SoutUtils.out("lineNo = " + lineNo+ " href = " + href + " lineName = " + lineName );
                                lineStopItem.SCode = sCode;
                                lineStopItem.SName = lineName;
                                lineStopItem.InTime = inTime;
                                if(!TextUtils.isEmpty(inTime))
                                {
                                    lineStopItem.s_num_str = Constants.IN_STOP + "\n" + inTime;
                                }
                                lineStopItems.add(stopItem);
                            }
                        }
                    }
                }
            }
        }
        return  lineStopItems;
    }
}
