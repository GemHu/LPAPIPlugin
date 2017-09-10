package com.dothantech.lpapi.cordova;

import org.apache.cordova.CordovaActivity;

import android.os.Bundle;

public class MainActivity extends CordovaActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		loadUrl(launchUrl);
	}
}
