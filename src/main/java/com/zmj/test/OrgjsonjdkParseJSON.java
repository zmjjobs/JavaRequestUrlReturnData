package com.zmj.test;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OrgjsonjdkParseJSON {
	public static void main(String[] args) {
		System.out.println("********************数组里面有多个对象*****************");
		String jsonObjectArrayStr = "[{\"amount\":\"88888\",\"unitcode\":\"02\",\"ddate\":\"2015-02-11\",\"localamount\":\"87611757.25\",\"subjcode\":\"1003\",\"balanorient\":\"借\"},{\"amount\":\"99999\",\"unitcode\":\"02\",\"ddate\":\"2015-03-11\",\"localamount\":\"87611757.25\",\"subjcode\":\"1003\",\"balanorient\":\"贷\"}]";
		try {
			JSONArray ja = new JSONArray(jsonObjectArrayStr);
			for (int i = 0; i < ja.length(); i++) {
				JSONObject jo = ja.getJSONObject(i);
				System.out.println("amount=" + jo.getString("amount"));
				System.out.println("localamount=" + jo.getString("localamount"));
				System.out.println("balanorient=" + jo.getString("balanorient"));
				System.out.println("ddate=" + jo.getString("ddate"));
				System.out.println("unitcode=" + jo.getString("unitcode"));
				System.out.println("subjcode=" + jo.getString("subjcode"));
				System.out.println("-------------------------------------");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		System.out.println("********************纯一个对象*****************");
		String jsonObjectStr = "{\"amount\":\"88888\",\"unitcode\":\"02\",\"ddate\":\"2015-02-11\",\"localamount\":\"87611757.25\",\"subjcode\":\"1003\",\"balanorient\":\"借\"}";
		try {
			JSONObject jo = new JSONObject(jsonObjectStr);
			System.out.println("amount=" + jo.getString("amount"));
			System.out.println("localamount=" + jo.getString("localamount"));
			System.out.println("balanorient=" + jo.getString("balanorient"));
			System.out.println("ddate=" + jo.getString("ddate"));
			System.out.println("unitcode=" + jo.getString("unitcode"));
			System.out.println("subjcode=" + jo.getString("subjcode"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		System.out.println("********************对象里面有数组*****************");
		String jsonArrayObjectStr = "{\"names\":[\"张三\",\"李四\"]}";
		try {
			JSONObject jo = new JSONObject(jsonArrayObjectStr);
			JSONArray jsonArray = jo.getJSONArray("names");
			for (int i = 0; i < jsonArray.length(); i++) {
				System.out.println(jsonArray.getString(i));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
