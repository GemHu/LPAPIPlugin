// cordova.define("cordova-plugin-lpapi.LPAPI", function(require, exports, module) {
	var exec = require("cordova/exec");
	/**
	 * 方法字符串常量；
	 */
	var Actions = {
		ACTION_GET_ALL_PRINTER : "getAllPrinters",
		ACTION_GET_ALL_PRINTER_ADDRESS : "getAllPrinterAddresses",
		ACTION_GET_PRINTER_NAME : "getPrinterName",
		ACTION_IS_PRINTER_OPENED : "isPrinterOpened",
		ACTION_OPEN_PRINTER : "openPrinter",
		ACTION_REOPEN_PRINTER : "reopenPrinter",
		ACTION_CLOSE_PRINTER : "closePrinter",
		ACTION_GET_HORIZONTAL_ALIGNMENT : "getItemHorizontalAlignment",
		ACTION_SET_HORIZONTAL_ALIGNMENT : "setItemHorizontalAlignment",
		ACTION_GET_VERTICAL_ALIGNMENT : "getItemVerticalAlignment",
		ACTION_SET_VERTICAL_ALIGNMENT : "setItemVerticalAlignment",
		ACTION_GET_ORIENTATION : "getItemOrientation",
		ACTION_SET_ORIENTATION : "setItemOrientation",
		ACTION_GET_PEN_ALIGNMENT : "getItemPenAlignment",
		ACTION_SET_PEN_ALIGNMENT : "setItemPenAlignment",
		ACTION_START_JOB : "startJob",
		ACTION_END_JOB : "endJob",
		ACTION_COMMIT_JOB : "commitJob",
		ACTION_ABORT_JOB : "abortJob",
		ACTION_START_PAGE : "startPage",
		ACTION_END_PAGE : "endPage",
		ACTION_GET_JOBPAGES : "getJobPages",
		ACTION_PRINT_IMAGE : "printImage",
		ACTION_CANCEL : "cancel",

		ACTION_DRAW_TEXT : "drawText",
		ACTION_DRAW_RICH_TEXT : "drawRichText",
		ACTION_DRAW_BARCODE : "draw1DBarcode",
		ACTION_DRAW_QRCODE : "draw2DQRCode",
		ACTION_DRAW_PDF417 : "draw2DPdf417",
		ACTION_DRAW_LINE : "drawLine",
		ACTION_DRAW_DASH_LINE : "drawDashLine",
		ACTION_DRAW_RECTANGLE : "drawRectangle",
		ACTION_DRAW_ROUND_RECTANGLE : "drawRoundRectangle",
		ACTION_DRAW_ELLIPSE : "drawEllipse",
		ACTION_DRAW_CIRCLE : "drawCircle",
		ACTION_FILL_RECTANGLE : "fillRectangle",
		ACTION_FILL_ROUND_RECTANGLE : "fillRoundRectangle",
		ACTION_FILL_ELLIPSE : "fillEllipse",
		ACTION_FILL_CIRCLE : "fillCircle",
		ACTION_DRAW_IMAGE : "drawImage"
	};

	var api = {};
	var serviceName = "LPAPIPlugin";
	api.getAllPrinters = function(args, success) {
		exec(success || function(msg) {}, 	    //
			function(msg) {}, 		    //
			serviceName, Actions.ACTION_GET_ALL_PRINTER, args || {});
	};
	api.getAllPrinterAddresses = function (args, success) {
		exec(success || function (msg) {},		    //
			function (msg) {},			    //
			serviceName, Actions.ACTION_GET_ALL_PRINTER_ADDRESS, args || {});
	};
	api.getPrinterName = function(success) {
		exec(success || function(args) {},		    //
			function(args) {}, 		    //
			serviceName, Actions.ACTION_GET_PRINTER_NAME, {});
	};
	api.isPrinterOpened = function(success, fail) {
		exec(success || function(args) {},		    //
			fail || function(args) {}, 		    //
			serviceName, Actions.ACTION_IS_PRINTER_OPENED, {});
	};
	api.openPrinter = function(args, success, fail) {
		exec(success || function(args) {},		    //
			fail || function(args) {}, 		    //
			serviceName, Actions.ACTION_OPEN_PRINTER, args || {});
	};
	api.reopenPrinter = function(success, fail) {
		exec(success || function(msg) {}, 	    //
			fail || function(msg) {}, 		    //
			serviceName, Actions.ACTION_REOPEN_PRINTER, {});
	};
	api.closePrinter = function() {
		exec(function(msg) {}, 			    //
			function(msg) {}, 				    //
			serviceName, Actions.ACTION_CLOSE_PRINTER, {});
	};
	// 参数获取与设置
	api.getItemHorizontalAlignment = function(success) {
		exec(success || function(msg) {},		//
			function(msg) {}, 			//
			serviceName, Actions.ACTION_GET_HORIZONTAL_ALIGNMENT, {});
	};
	api.setItemHorizontalAlignment = function(args, success, fail) {
		exec(success || function(msg) {},			        //
			fail || function(msg) {}, 			        //
			serviceName, Actions.ACTION_SET_HORIZONTAL_ALIGNMENT, args || {});
	};
	api.getItemVerticalAlignment = function(success) {
		exec(success || function(msg) {},		//
			function(msg) {},			//
			serviceName, Actions.ACTION_GET_VERTICAL_ALIGNMENT, {});
	};
	api.setItemVerticalAlignment = function(args, success, fail) {
		exec(success || function(args) {},			    //
			fail || function(args) {},				    //
			serviceName, Actions.ACTION_SET_VERTICAL_ALIGNMENT, args || {});
	};
	api.getItemOrientation = function(success) {
		exec(success || function(msg) {},		//
			function(msg) {}, 			//
			serviceName, Actions.ACTION_GET_ORIENTATION, {});
	};
	api.setItemOrientation = function(args, success, fail) {
		exec(success || function(msg) {},		            //
			fail || function(msg) {}, 		            //
			serviceName, Actions.ACTION_SET_ORIENTATION, args || {});
	};
	api.getItemPenAlignment = function(success) {
		exec(success || function(msg) {},		//
			function(msg) {}, 			//
			serviceName, Actions.ACTION_GET_PEN_ALIGNMENT, {});
	};
	api.setItemPenAlignment = function(args, success, fail) {
		exec(success || function(msg) {}, 		        //
			fail || function(msg) {},			        //
			serviceName, Actions.ACTION_SET_PEN_ALIGNMENT, args || {});
	};
	// 打印任务及打印页面
	api.startJob = function(args, success, fail) {
		exec(success || function(msg) {},		//
			fail || function(msg) {}, 			//
			serviceName, Actions.ACTION_START_JOB, args);
	};
	api.endJob = function() {
		exec(function(msg) {},			        //
			function(msg) {}, 			        //
			serviceName, Actions.ACTION_END_JOB, {});
	};
	api.commitJob = function(success, fail, callback) {
		exec(function(msg) {
				if (msg === "OK"){
					if (success && typeof success === "function")
						success(msg);
				} else {
					if (callback && typeof callback === "function")
						callback(msg);
				}
			},		//
			function(msg) {
				if (msg === "OK"){
					if (fail && typeof fail === "function")
						fail(msg);
				} else {
					if (callback && typeof callback === "function")
						callback(msg);
				}
			}, 			//
			serviceName, Actions.ACTION_COMMIT_JOB, {});
	};
	api.abortJob = function() {
		exec(function(msg) {},			        //
			function(msg) {}, 			        //
			serviceName, Actions.ACTION_ABORT_JOB, {});
	};
	api.startPage = function(success, fail) {
		exec(success || function(msg) {},		//
			fail || function(msg) {}, 		    //
			serviceName, Actions.ACTION_START_PAGE, {});
	};
	api.endPage = function() {
		exec(function(msg) {},			        //
			function(msg) {}, 			        //
			serviceName, Actions.ACTION_END_PAGE, {});
	};
	api.cancel = function() {
		exec(function(msg) {},			        //
			function(msg) {},			        //
			serviceName, Actions.ACTION_CANCEL, {});
	};
	api.getJobPages = function (success, fail) {
		exec(success || function(msg) {},		//
			fail || function(msg) {}, 			//
			serviceName, Actions.ACTION_GET_JOBPAGES, {});
	};
	api.printImage = function (args, success, fail) {
		exec(success || function(msg) {},		//
			fail || function(msg) {}, 		    //
			serviceName, Actions.ACTION_PRINT_IMAGE, args || {});
	};
	// 打印对象的绘制
	api.drawText = function(args) {
		exec(function(msg) {},			//
			function(msg) {}, 			//
			serviceName, Actions.ACTION_DRAW_TEXT, args || {});
	};
	api.drawRichText = function (args) {
		exec(function (msg) {},		//
			function (msg) {},			//
			serviceName, Actions.ACTION_DRAW_RICH_TEXT, args || {});
	};
	api.draw1DBarcode = function(args) {
		exec(function(msg) {},			//
			function(msg) {},			//
			serviceName, Actions.ACTION_DRAW_BARCODE, args || {});
	};
	api.draw2DPdf417 = function(args) {
		exec(function(msg) {},			//
			function(msg) {},			//
			serviceName, Actions.ACTION_DRAW_PDF417, args || {});
	};
	api.draw2DQRCode = function(args) {
		exec(function(msg) {},			//
			function(msg) {}, 			//
			serviceName, Actions.ACTION_DRAW_QRCODE, args || {});
	};
	api.drawLine = function(args) {
		exec(function(msg) {},			//
			function(msg) {}, 			//
			serviceName, Actions.ACTION_DRAW_LINE, args || {});
	};
	api.drawDashLine = function(args) {
		exec(function(msg) {},			//
			function(msg) {}, 			//
			serviceName, Actions.ACTION_DRAW_DASH_LINE, args || {});
	};
	api.drawRectangle = function(args) {
		exec(function(msg) {},			//
			function(msg) {}, 			//
			serviceName, Actions.ACTION_DRAW_RECTANGLE, args);
	};
	api.drawRoundRectangle = function(args) {
		exec(function(msg) {},			//
			function(msg) {}, 			//
			serviceName, Actions.ACTION_DRAW_ROUND_RECTANGLE, args || {});
	};
	api.drawEllipse = function(args) {
		exec(function(msg) {},			//
			function(msg) {}, 			//
			serviceName, Actions.ACTION_DRAW_ELLIPSE, args || {});
	};
	api.drawCircle = function(args) {
		exec(function(msg) {},			//
			function(msg) {}, 			//
			serviceName, Actions.ACTION_DRAW_CIRCLE, args || {});
	};
	api.fillRectangle = function(args) {
		exec(function(msg) {},			//
			function(msg) {}, 			//
			serviceName, Actions.ACTION_FILL_RECTANGLE, args || {});
	};
	api.fillRoundRectangle = function(args) {
		exec(function(msg) {}, 		//
			function(msg) {}, 			//
			serviceName, Actions.ACTION_FILL_ROUND_RECTANGLE, args || {});
	};
	api.fillEllipse = function(args) {
		exec(function(msg) {},			//
			function(msg) {}, 			//
			serviceName, Actions.ACTION_FILL_ELLIPSE, args || {});
	};
	api.fillCircle = function(args) {
		exec(function(msg) {},			//
			function(msg) {}, 			//
			serviceName, Actions.ACTION_FILL_CIRCLE, args || {});
	};
	api.drawImage = function(args) {
		exec(function(msg) {},			//
			function(msg) {}, 			//
			serviceName, Actions.ACTION_DRAW_IMAGE, args || {});
	};

	module.exports = api;
//	});