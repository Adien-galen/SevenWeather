package com.example.sevenweather.util;

public interface HttpCallbackListener {
	void onFinish(String response);
	void onError(Exception e);
}
