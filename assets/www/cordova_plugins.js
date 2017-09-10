cordova.define('cordova/plugin_list', function(require, exports, module) {
	module.exports = [
		{
			"file" : "plugins/cordova-plugin-lpapi/lpapi.js",	// js文件
			"id" : "cordova-plugin-lpapi.LPAPI",				// js cordova.define的id
			"clobbers" : [
				"LPAPI"											// js 调用时的方法名
			]
		}
	];
	module.exports.metadata =
	// TOP OF METADATA
	{
		"cordova-plugin-whitelist" : "1.3.2"
	};
	// BOTTOM OF METADATA
});