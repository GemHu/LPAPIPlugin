cordova.define("cordova-plugin-lpapi.LPAPI",
	function (require, exports, module) {
		var exec = require("cordova/exec");
		/**
		 * 方法字符串常量；
		 */
		var Actions = {
			GET_ALL_PRINTERS: "getAllPrinters",
			GET_PRINTER_NAME: "getPrinterName",
			IS_PRINTER_OPENED: "isPrinterOpened",
			OPEN_PRINTER: "openPrinter",
			REOPEN_PRINTER: "reopenPrinter",
			CLOSE_PRINTER: "closePrinter",

			GET_HORIZONTAL_ALIGNMENT: "getHorizontalAlignment",
			SET_HORIZONTAL_ALIGNMENT: "setItemHorizontalAlignment",
			GET_VERTICAL_ALIGNMENT: "getItemVerticalAlignment",
			SET_VERTICAL_ALIGNMENT: "setItemVerticalAlignment",
			GET_ORIENTATION: "getItemOrientation",
			SET_ORIENTATION: "setItemOrientation",
			GET_PEN_ALIGNMENT: "getItemPenAlignment",
			SET_PEN_ALIGNMENT: "setItemPenAlignment",

			START_JOB: "startJob",
			END_JOB: "endJob",
			COMMIT_JOB: "commitJob",
			ABORT_JOB: "abortJob",
			START_PAGE: "startPage",
			END_PAGE: "endPage",
			CANCEL: "cancel",
			DRAW_TEXT: "drawText",
			DRAW_BARCODE: "draw1DBarcode",
			DRAW_QRCODE: "draw2DQRCode",
			DRAW_PDF417: "draw2DPdf417",
			DRAW_LINE: "drawLine",
			DRAW_DASH_LINE: "drawDashLine",
			DRAW_DASH_LINE2: "drawDashLine2",
			DRAW_DASH_LINE4: "drawDashLine4",

			DRAW_RECTANGLE: "drawRectangle",
			DRAW_ROUND_RECTANGLE: "drawRoundRectangle",
			DRAW_ELLIPSE: "drawEllipse",
			DRAW_CIRCLE: "drawCircle",
			FILL_RECTANGLE: "fillRectangle",
			FILL_ROUND_RECTANGLE: "fillRoundRectangle",
			FILL_ELLIPSE: "fillEllipse",
			FILL_CIRCLE: "fillCircle",

			DRAW_IMAGE: "drawImage"
		};

		var api = {};
		var serviceName = "LPAPIPlugin";
		api.getAllPrinters = function (success, fail) {
			exec(success || function(args){}, fail || function(args){}, serviceName, actions.GET_ALL_PRINTERS, {});
		};
		api.getPrinterName = function (success, fail) {
			exec(success || function(args){}, fail || function(args){}, serviceName, actions.GET_PRINTER_NAME, {});
		};
		api.isPrinterOpened = function (success, fail) {
			exec(success || function(args){}, fail || function(args){}, serviceName, actions.IS_PRINTER_OPENED, {});
		};
		api.openPrinter = function (printerName, success, fail) {
			exec(success || function(args){}, fail || function(args){}, serviceName, actions.OPEN_PRINTER, {});
		};
		api.reopenPrinter = function (success, fail) {
			exec(success || function(args){}, fail || function(args){}, serviceName, actions.REOPEN_PRINTER, {});
		};
		api.closePrinter = function () {
			exec(function(args){}, function(args){}, serviceName, actions.CLOSE_PRINTER, {});
		};
		// 参数获取与设置
		api.getItemHorizontalAlignment = function (success, fail) {
			exec(success || function(args){}, fail || function(args){}, serviceName, actions.GET_HORIZONTAL_ALIGNMENT, {});
		};
		api.setItemHorizontalAlignment = function (horizontalAlignment) {
			exec(function(args){}, function(args){}, serviceName, actions.SET_HORIZONTAL_ALIGNMENT, {
				alignment : horizontalAlignment
			});
		};
		api.getItemVerticalAlignment = function (success, fail) {
			exec(success || function(args){}, fail || function(args){}, serviceName, actions.GET_VERTICAL_ALIGNMENT, {});
		};
		api.setItemVerticalAlignment = function (verticalAlignment) {
			exec(function(args){}, function(args){}, serviceName, actions.SET_VERTICAL_ALIGNMENT, {
				alignment : verticalAlignment
			});
		};
		api.getItemOrientation = function (success, fail) {
			exec(success || function(args){}, fail || function(args){}, serviceName, actions.GET_ORIENTATION, {});
		};
		api.setItemOrientation = function (orientation) {
			exec(function(args){}, function(args){}, serviceName, actions.SET_ORIENTATION, {
				orientation : orientation
			});
		};
		api.getItemPenAlignment = function (success, fail) {
			exec(success || function(args){}, fail || function(args){}, serviceName, actions.GET_PEN_ALIGNMENT, {});
		};
		api.setItemPenAlignment = function (penAlignment) {
			exec(function () {}, function () {}, serviceName, Actions.SET_PEN_ALIGNMENT, {
				alignment : penAlignment
			});
		};
		// 打印任务及打印页面
		api.startJob = function (args, success, fail) {
			exec(success || function () {}, fail || function () {}, serviceName, Actions.START_JOB, args);
		};
		api.endJob = function () {
			exec(function () {}, function () {}, serviceName, Actions.END_JOB, {});
		};
		api.commitJob = function (success, fail) {
			exec(success || function () {}, fail || function () {}, serviceName, Actions.COMMIT_JOB, {});
		};
		api.abortJob = function () {
			exec(function () {}, function () {}, serviceName, Actions.ABORT_JOB, {});
		};
		api.startPage = function (success, fail) {
			exec(success || function(){}, fail || function () {}, serviceName, Actions.START_PAGE, {});
		};
		api.endPage = function () {
			exec(function () {}, function () {}, serviceName, Actions.END_PAGE, {});
		};
		api.cancel = function () {
			exec(function () {}, function () {}, serviceName, Actions.CANCEL, {});
		};
		// 打印对象的绘制
		api.drawText = function (args) {
			exec(function () {}, function () {}, serviceName, Actions.DRAW_TEXT, args);
		};
		api.draw1DBarcode = function (args) {
			exec(function () {}, function () {}, serviceName, Actions.DRAW_BARCODE, args);
		};
		api.draw2DPdf417 = function (args) {
			exec(function () {}, function () {}, serviceName, Actions.DRAW_PDF417, args);
		};
		api.draw2DQRCode = function (args) {
			exec(function () {}, function () {}, serviceName, Actions.DRAW_QRCODE, args);
		};
		api.drawLine = function (x1, y1, x2, y2, lineWidth) {
			exec(function () {}, function () {}, serviceName, Actions.DRAW_LINE, {
				x1 : (x1 || 0),
				y1 : (y1 || 0),
				x2 : (x2 || 0),
				y2 : (y2 || 0),
				lineWidth : (lineWidth || 0)
			});
		};
		api.drawDashLine = function (x1, y1, x2, y2, lineWidth, dashLen) {
			exec(function () {}, function () {}, serviceName, Actions.DRAW_DASH_LINE, {
				x1 : (x1 || 0),
				y1 : (y1 || 0),
				x2 : (x2 || 0),
				y2 : (y2 || 0),
				lineWidth : (lineWidth || 0),
				dashLen : (dashLen || [0.5, 0.5])
			});
		};
		api.drawDashLine2 = function (x1, y1, x2, y2, lineWidth, dashLen1, dashLen2) {
			exec(function () {}, function () {}, serviceName, Actions.DRAW_DASH_LINE2, {
				x1 : (x1 || 0),
				y1 : (y1 || 0),
				x2 : (x2 || 0),
				y2 : (y2 || 0),
				lineWidth : (lineWidth || 0),
				dashLen1 : (dashLen1 || 0.5),
				dashLen2 : (dashLen2 || 0.5)
			});
		};
		api.drawDashLine4 = function (x1, y1, x2, y2, lineWidth, dashLen1, dashLen2, dashLen3, dashLen4) {
			exec(function () {}, function () {}, serviceName, Actions.DRAW_DASH_LINE4, {
				x1 : (x1 || 0),
				y1 : (y1 || 0),
				x2 : (x2 || 0),
				y2 : (y2 || 0),
				lineWidth : (lineWidth || 0),
				dashLen1 : (dashLen1 || 0.5),
				dashLen2 : (dashLen2 || 0.5),
				dashLen3 : (dashLen3 || 0.5),
				dashLen4 : (dashLen4 || 0.5)
			});
		};
		api.drawRectangle = function (x, y, width, height, lineWidth) {
			exec(function () {}, function () {}, serviceName, Actions.DRAW_RECTANGLE, {
				x : (x || 0),
				y : (y || 0),
				width : (width || 0),
				height : (height || 0),
				lineWidth : (lineWidth || 0.3)
			});
		};
		api.drawRoundRectangle = function (x, y, width, height, cornerWidth, cornerHeight, lineWidth) {
			exec(function () {}, function () {}, serviceName, Actions.drDRAW_ROUND_RECTANGLE, {
				x : (x || 0),
				y : (y || 0),
				width : (width || 0),
				height : (height || 0),
				cornerWidth : (cornerWidth || 0),
				cornerHeight : (cornerHeight || 0),
				lineWidth : (lineWidth || 0.3)
			});
		};
		api.drawEllipse = function (x, y, width, height, lineWidth) {
			exec(function () {}, function () {}, serviceName, Actions.DRAW_ELLIPSE, {
				x : (x || 0), 
				y : (y || 0),
				width : (width || 0),
				height : (height || 0),
				lineWidth : (lineWidth || 0.3)
			});
		};
		api.drawCircle = function (x, y, radius, lineWidth) {
			exec(function () {}, function () {}, serviceName, Actions.DRAW_CIRCLE, {
				x : (x || 0),
				y : (y || 0),
				radius : (radius || 0),
				lineWidth : (lineWidth || 0.3)
			});
		};
		api.fillRectangle = function (x, y, width, height) {
			exec(function () {}, function () {}, serviceName, Actions.FILL_RECTANGLE, {
				x : (x || 0),
				y : (y || 0),
				width : (width || 0),
				height : (height || 0)
			});
		};
		api.fillRoundRectangle = function (x, y, width, height, cornerWidth, cornerHeight) {
			exec(function () {}, function () {}, serviceName, Actions.FILL_ROUND_RECTANGLE, {
				x : (x || 0),
				y : (y || 0),
				width : (width || 0),
				height : (height || 0),
				cornerWidth : (cornerWidth || 0),
				cornerHeight : (cornerHeight || 0)
			});
		};
		api.fillEllipse = function (x, y, width, height) {
			exec(function () {}, function () {}, serviceName, Actions.FILL_ELLIPSE, {
				x : (x || 0),
				y : (y || 0),
				width : (width || 0),
				height : (height || 0)
			});
		};
		api.fillCircle = function (x, y, radius) {
			if (!radius) return;
			exec(function () {}, function () {}, serviceName, Actions.FILL_CIRCLE, {
				x : (x || 0),
				y : (y || 0),
				radius : radius
			});
		};
		api.drawImage = function (imageFile, x, y, width, height, threshold) {
			if (!imageFile) return;
			exec(function () {}, function () {}, serviceName, Actions.DRAW_IMAGE, {
				imageFile : imageFile,
				x : (x || 0),
				y : (y || 0),
				width : (width || 0),
				height : (height || 0),
				threshold : (threshold || 192)
			});
		};

		module.exports = api;
	});