package com.haikuowuya.bl.model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

/**
 * 说明:
 * 日期:2016/4/23
 * 时间:14:52
 * 创建者：hkwy
 * 修改者：
 **/
public class BaseSearchLineModel extends BaseItem
{
    public String errorCode;
    public String errorMsg;
    public JsonObject data;

    public LinkedList<SearchLineModel> dataToList()
    {
        LinkedList<SearchLineModel> searchLineModels = null;
        if (null != data)
        {
            JsonArray jsonArray = data.getAsJsonArray("list");
            if(jsonArray != null )
            {
                Type typeOfT = new TypeToken<LinkedList<SearchLineModel>>()
                {
                }.getType();
                searchLineModels = new Gson().fromJson(jsonArray.toString(), typeOfT);
            }
        }
        return  searchLineModels;
    }
}
