package com.example.sevenweather.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sevenweather.model.City;
import com.example.sevenweather.model.County;
import com.example.sevenweather.model.Province;

public class SevenWeatherDB {
	/*
	 * 数据库名
	 * */
	public static final String DB_NAME = "seven_weather";
	/*
	 * 数据库版本
	 * */
	public static final int VERSION = 1;
	
	private static SevenWeatherDB sevenWeatherDB;
	
	private static SQLiteDatabase db;
	/*
	 * 将构造方法私有化
	 * */
	private SevenWeatherDB(Context context){
		SevenWeatherOpenHelper helper = new SevenWeatherOpenHelper(context,DB_NAME,null,VERSION);
		db = helper.getWritableDatabase();
	};
	
	/*
	 * 获取CoolWeatherDB实例
	 * */
	public synchronized static SevenWeatherDB getInstance(Context context){
		if(sevenWeatherDB==null){
			sevenWeatherDB = new SevenWeatherDB(context);
		}
		return sevenWeatherDB;
	}
	
	/*
	 * 将Provice实例存储到数据库
	 * */
	public void saveProvince(Province province){
		if(province!=null){
			ContentValues values = new ContentValues();
			values.put("province_name", province.getProvince_name());
			values.put("province_code",province.getProvince_code());
			db.insert("Province", null, values);
		}
	}
	
	/*
	 * 从数据库读取全国所有的省份信息
	 * */
	public List<Province> loadProvince(){
		List<Province> list = new ArrayList<Province>();
		Cursor cursor = db.query("Province", null, null, null, null, null, null);
		if(cursor.moveToFirst()){
			do{
				Province province = new Province();
				province.setId(cursor.getInt(cursor.getColumnIndex("id")));
				province.setProvince_name(cursor.getString(cursor.getColumnIndex("province_name")));
				province.setProvince_code(cursor.getString(cursor.getColumnIndex("province_code")));
				list.add(province);
			}while(cursor.moveToNext());
		}
		if(cursor!=null){
			cursor.close();
		}
		return list;
	}
	
	/*
	 * 将City实例存储到数据库
	 * */
	public void savaCity(City city){
		if(city!=null){
			ContentValues values = new ContentValues();
			values.put("city_name", city.getCity_name());
			values.put("city_code", city.getCity_code());
			values.put("province_Id",city.getProvinceId());
			db.insert("City", null, values);
		}
	}
	
	/*
	 * 从数据库读取某省下的所有城市信息
	 * */
	public List<City> loadCity(int provinceId){
		List<City> list = new ArrayList<City>();
		Cursor cursor = db.query("City", null, "province_Id=?", new String[]{String.valueOf(provinceId)}, null, null, null);
		if(cursor.moveToFirst()){
			do{
				City city = new City();
				city.setId(cursor.getInt(cursor.getColumnIndex("id")));
				city.setCity_name(cursor.getString(cursor.getColumnIndex("city_name")));
				city.setCity_code(cursor.getString(cursor.getColumnIndex("city_code")));
				city.setProvinceId(provinceId);
				list.add(city);
			}while(cursor.moveToNext());
		}
		if(cursor!=null){
			cursor.close();
		}
		return list;
	}
	
	
	public void saveCounty(County county){
		if(county!=null){
			ContentValues values = new ContentValues();
			values.put("county_name", county.getCounty_name());
			values.put("county_code", county.getCounty_code());
			values.put("city_id", county.getCityId());
			db.insert("County", null, values);
		}
	}
	
	public List<County> loadCounty(int cityId){
		List<County> list = new ArrayList<County>();
		Cursor cursor = db.query("County", null, "city_id=?", new String[]{String.valueOf(cityId)}, null, null, null);
		if(cursor.moveToFirst()){
			do{
				County county = new County();
				county.setId(cursor.getInt(cursor.getColumnIndex("id")));
				county.setCounty_name(cursor.getString(cursor.getColumnIndex("county_name")));
				county.setCounty_code(cursor.getString(cursor.getColumnIndex("county_code")));
				county.setCityId(cityId);
				list.add(county);
			}while(cursor.moveToNext());
		}
		if(cursor!=null){
			cursor.close();
		}
		return list;
	}
}
