package com.dothantech.lpapi.cordova;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONException;
import org.json.JSONObject;

import com.dothantech.lpapi.LPAPI;

public class LPAPIPlugin extends CordovaPlugin {

	private LPAPI mApi;
	
	@Override
	public void initialize(CordovaInterface cordova, CordovaWebView webView) {
		super.initialize(cordova, webView);
		this.mApi = LPAPI.Factory.createInstance();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
//		this.mApi.closePrinter();
	}
	
	@Override
	public boolean execute(String action, String rawArgs, CallbackContext callbackContext) throws JSONException {
		JSONObject args = new JSONObject(rawArgs);
		
		return super.execute(action, rawArgs, callbackContext);
	}
	
}
