package com.haikuowuya.bl.util;

import com.haikuowuya.bl.URLConstants;
import com.haikuowuya.bl.model.SearchLine;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
     * 将的html文件转换为想要的数据
     * @param document
     * @return
     */
    public static LinkedList<SearchLine> htmlToSearchLine(Document document)
    {
        final LinkedList<SearchLine> searchLines = new LinkedList<>();
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
                                SearchLine searchLine = new SearchLine();
                                String href;
                                String lineName;
                                String lineNo;
                                Element aElement = tdElements.get(0).getElementsByTag("a").get(0);
                                lineNo = aElement.text();
                                href = URLConstants.BUS_LINE_QUERY_PREFIX + aElement.attr("href");
                                lineName = tdElements.get(1).text();
                                /*lineNo = 1 href = http://www.szjt.gov.cn/BusQuery/APTSLine.aspx?cid=175ecd8d-c39d-4116-83ff-109b946d7cb4&LineGuid=9d090af5-c5c6-4db8-b34e-2e8af4f63216&LineInfo=1(公交一路新村首末站) lineName = 公交一路新村首末站*/
                                // SoutUtils.out("lineNo = " + lineNo+ " href = " + href + " lineName = " + lineName );
                                searchLine.lineNo = lineNo;
                                searchLine.lineName = lineName;
                                searchLine.lineHref = href;
                                searchLines.add(searchLine);
                            }
                        }
                    }
                }
            }
        }
        return  searchLines;
    }
}
