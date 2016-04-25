package com.haikuowuya.bl.model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.LinkedList;

/**
 * 说明:
 * 日期:2016/4/23
 * 时间:14:52
 * 创建者：hkwy
 * 修改者：
 **/
public class BaseSearchLineModel extends BaseModel
{

    public JsonObject data;

    public LinkedList<SearchLineItem> dataToList()
    {
        LinkedList<SearchLineItem> searchLineModels = null;
        if (null != data)
        {
            JsonArray jsonArray = data.getAsJsonArray("list");
            if(jsonArray != null )
            {
                Type typeOfT = new TypeToken<LinkedList<SearchLineItem>>()
                {
                }.getType();
                searchLineModels = new Gson().fromJson(jsonArray.toString(), typeOfT);
            }
        }
        return  searchLineModels;
    }
}
