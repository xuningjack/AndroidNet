package com.jack.okhttp.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;



/**
 * Json解析工具类
 */
public class JsonHelper {

    private static Gson gson = new Gson();

    /**
     * 解析单个实体
     * @param jsonData
     * @param type
     * @param <T>
     * @return
     */
	@SuppressWarnings("unchecked")
	public static <T> T getResult(String jsonData, TypeToken<T> type) {
		return (T) gson.fromJson(jsonData, type.getType());
	}

    /**
     * 解析单个实体
     * @param jsonData
     * @param type
     * @param <T>
     * @return
     */
	public static <T> T getResult(String jsonData, Class<T> type) {
		return gson.fromJson(jsonData, type);
	}

    /**
     * 获得json字符串对应的数据List
     * @param jsonData json数据源
     * @param type 实体类的类型  如Drafts.class
     * @param <T> 实体类的类型
     * @return
     */
    public static<T> List<T> getResultList(String jsonData, T type){
        Type listType = new TypeToken<List<T>>(){}.getType();
        return gson.fromJson(jsonData, listType);
    }
}
