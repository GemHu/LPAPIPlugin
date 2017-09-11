package com.dothantech.lpapi.cordova;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.dothantech.lpapi.LPAPI;
import com.dothantech.lpapi.LPAPI.Callback;
import com.dothantech.printer.IDzPrinter.PrintProgress;
import com.dothantech.printer.IDzPrinter.PrinterAddress;
import com.dothantech.printer.IDzPrinter.PrinterInfo;
import com.dothantech.printer.IDzPrinter.PrinterState;
import com.dothantech.printer.IDzPrinter.ProgressInfo;

public class LPAPIPlugin extends CordovaPlugin {

	private static final String GET_ALL_PRINTERS = "getAllPrinters";
	private static final String GET_PRINTER_NAME = "getPrinterName";
	private static final String IS_PRINTER_OPENED = "isPrinterOpened";
	private static final String OPEN_PRINTER = "openPrinter";
	private static final String REOPEN_PRINTER = "reopenPrinter";
	private static final String CLOSE_PRINTER = "closePrinter";
	private static final String GET_HORIZONTAL_ALIGNMENT = "getHorizontalAlignment";
	private static final String SET_HORIZONTAL_ALIGNMENT = "setItemHorizontalAlignment";
	private static final String GET_VERTICAL_ALIGNMENT = "getItemVerticalAlignment";
	private static final String SET_VERTICAL_ALIGNMENT = "setItemVerticalAlignment";
	private static final String GET_ORIENTATION = "getItemOrientation";
	private static final String SET_ORIENTATION = "setItemOrientation";
	private static final String GET_PEN_ALIGNMENT = "getItemPenAlignment";
	private static final String SET_PEN_ALIGNMENT = "setItemPenAlignment";
	private static final String START_JOB = "startJob";
	private static final String END_JOB = "endJob";
	private static final String COMMIT_JOB = "commitJob";
	private static final String ABORT_JOB = "abortJob";
	private static final String START_PAGE = "startPage";
	private static final String END_PAGE = "endPage";
	private static final String CANCEL = "cancel";
	private static final String DRAW_TEXT = "drawText";
	private static final String DRAW_BARCODE = "draw1DBarcode";
	private static final String DRAW_QRCODE = "draw2DQRCode";
	private static final String DRAW_PDF417 = "draw2DPdf417";
	private static final String DRAW_LINE = "drawLine";
	private static final String DRAW_DASH_LINE = "drawDashLine";
	private static final String DRAW_DASH_LINE2 = "drawDashLine2";
	private static final String DRAW_DASH_LINE4 = "drawDashLine4";
	private static final String DRAW_RECTANGLE = "drawRectangle";
	private static final String DRAW_ROUND_RECTANGLE = "drawRoundRectangle";
	private static final String DRAW_ELLIPSE = "drawEllipse";
	private static final String DRAW_CIRCLE = "drawCircle";
	private static final String FILL_RECTANGLE = "fillRectangle";
	private static final String FILL_ROUND_RECTANGLE = "fillRoundRectangle";
	private static final String FILL_ELLIPSE = "fillEllipse";
	private static final String FILL_CIRCLE = "fillCircle";
	private static final String DRAW_IMAGE = "drawImage";

	// 常用参数字符串常量
	private static final String PARAMS_PRINTER_NAME = "printerName";
	private static final String PARAMS_ALIGNMENT = "alignment";
	private static final String PARAMS_ORIENTATION = "orientation";
	private static final String PARAMS_TEXT = "text";
	private static final String PARAMS_X = "x";
	private static final String PARAMS_X1 = "x1";
	private static final String PARAMS_X2 = "x2";
	private static final String PARAMS_Y = "y";
	private static final String PARAMS_Y1 = "y1";
	private static final String PARAMS_Y2 = "y2";
	private static final String PARAMS_LINE_WIDTH = "lineWidth";
	private static final String PARAMS_WIDTH = "width";
	private static final String PARAMS_HEIGHT = "height";
	private static final String PARAMS_FONT_HEIGHT = "fontHeight";
	private static final String PARAMS_FONT_STYLE = "fontStyle";
	private static final String PARAMS_IMAGE_FILE = "imageFile";
	private static final String PARAMS_RADIUS = "radius";
	private static final String PARAMS_TYPE = "type";
	private static final String PARAMS_TEXT_HEIGHT = "textHeight";
	private static final String PARAMS_DASH_LEN = "dashLen";
	private static final String PARAMS_DASH_LEN1 = "dashLen1";
	private static final String PARAMS_DASH_LEN2 = "dashLen2";
	private static final String PARAMS_DASH_LEN3 = "dashLen3";
	private static final String PARAMS_DASH_LEN4 = "dashLen4";
	private static final String PARAMS_CORNER_WIDTH = "cornerWidth";
	private static final String PARAMS_CORNER_HEIGHT = "cornerHeight";

	private LPAPI mApi;
	private CallbackContext openPrinterCallback;

	@Override
	public void initialize(CordovaInterface cordova, CordovaWebView webView) {
		super.initialize(cordova, webView);
		this.mApi = LPAPI.Factory.createInstance(new Callback() {

			@Override
			public void onStateChange(PrinterAddress arg0, PrinterState arg1) {
				if (openPrinterCallback == null)
					return;
				if (arg1 == PrinterState.Connected || arg1 == PrinterState.Connected2) {
					openPrinterCallback.success();
				}
			}

			@Override
			public void onProgressInfo(ProgressInfo arg0, Object arg1) {
			}

			@Override
			public void onPrinterDiscovery(PrinterAddress arg0, PrinterInfo arg1) {
			}

			@Override
			public void onPrintProgress(PrinterAddress arg0, Object arg1, PrintProgress arg2, Object arg3) {
			}
		});
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
//		this.mApi.closePrinter();
	}

	@Override
	public boolean execute(String action, String rawArgs, CallbackContext callbackContext) throws JSONException {
		JSONObject args = (rawArgs == null ? new JSONObject() : new JSONObject(rawArgs));
		if (GET_ALL_PRINTERS.equalsIgnoreCase(action)) {
			callbackContext.success(this.mApi.getAllPrinters());
		} else if (GET_PRINTER_NAME.equalsIgnoreCase(action)) {
			callbackContext.success(this.mApi.getPrinterName());
		} else if (IS_PRINTER_OPENED.equalsIgnoreCase(action)) {
			if (this.mApi.isPrinterOpened())
				callbackContext.success();
		} else if (OPEN_PRINTER.equalsIgnoreCase(action)) {
			this.openPrinterCallback = callbackContext;
			this.mApi.openPrinter(args.optString(PARAMS_PRINTER_NAME));
		} else if (REOPEN_PRINTER.equalsIgnoreCase(action)) {
			this.openPrinterCallback = callbackContext;
			this.mApi.reopenPrinter();
		} else if (CLOSE_PRINTER.equalsIgnoreCase(action)) {
			this.mApi.closePrinter();
		} else if (GET_HORIZONTAL_ALIGNMENT.equalsIgnoreCase(action)) {
			callbackContext.success(this.mApi.getItemHorizontalAlignment());
		} else if (SET_HORIZONTAL_ALIGNMENT.equalsIgnoreCase(action)) {
			this.mApi.setItemHorizontalAlignment(args.optInt(PARAMS_ALIGNMENT));
		} else if (GET_VERTICAL_ALIGNMENT.equalsIgnoreCase(action)) {
			callbackContext.success(this.mApi.getItemVerticalAlignment());
		} else if (SET_VERTICAL_ALIGNMENT.equalsIgnoreCase(action)) {
			this.mApi.setItemVerticalAlignment(args.optInt(PARAMS_ALIGNMENT));
		} else if (GET_ORIENTATION.equalsIgnoreCase(action)) {
			callbackContext.success(this.mApi.getItemOrientation());
		} else if (SET_ORIENTATION.equalsIgnoreCase(action)) {
			this.mApi.setItemOrientation(args.optInt(PARAMS_ORIENTATION));
		} else if (GET_PEN_ALIGNMENT.equalsIgnoreCase(action)) {
			callbackContext.success(this.mApi.getItemPenAlignment());
		} else if (SET_PEN_ALIGNMENT.equalsIgnoreCase(action)) {
			this.mApi.setItemPenAlignment(args.optInt(PARAMS_ALIGNMENT));
		}
		// 打印任务
		else if (START_JOB.equalsIgnoreCase(action)) {
			if (this.mApi.startJob(args.optDouble(PARAMS_WIDTH), args.optDouble(PARAMS_HEIGHT), args.optInt(PARAMS_ORIENTATION)))
				callbackContext.success();
			else
				callbackContext.equals(0);
		} else if (END_JOB.equalsIgnoreCase(action)) {
			this.mApi.endJob();
		} else if (ABORT_JOB.equalsIgnoreCase(action)) {
			this.mApi.abortJob();
		} else if (COMMIT_JOB.equalsIgnoreCase(action)) {
			if (this.mApi.commitJob())
				callbackContext.success();
			else
				callbackContext.error(0);
		}
		// 分页
		else if (START_PAGE.equalsIgnoreCase(action)) {
			if (this.mApi.startPage())
				callbackContext.success();
			else
				callbackContext.error(0);
		} else if (END_PAGE.equalsIgnoreCase(action)) {
			this.mApi.endPage();
		} else if (CANCEL.equalsIgnoreCase(action)) {
			this.mApi.cancel();
		}
		// 打印对象的绘制
		else if (DRAW_TEXT.equalsIgnoreCase(action)) {
			this.mApi.drawText(args.optString(PARAMS_TEXT), args.optDouble(PARAMS_X), args.optDouble(PARAMS_Y),	//
							args.optDouble(PARAMS_WIDTH), args.optDouble(PARAMS_HEIGHT),						//
							args.optDouble(PARAMS_FONT_HEIGHT), args.optInt(PARAMS_FONT_STYLE));
		} else if (DRAW_LINE.equalsIgnoreCase(action)) {
			this.mApi.drawLine(args.optDouble(PARAMS_X1), args.optDouble(PARAMS_Y1), args.optDouble(PARAMS_X2), //
							args.optDouble(PARAMS_Y2), args.optDouble(PARAMS_LINE_WIDTH));
		} else if (DRAW_DASH_LINE.equalsIgnoreCase(action)) {
			JSONArray array = args.optJSONArray(PARAMS_DASH_LEN);
			if (array != null && array.length() > 0){
				double dashLen[] = new double[array.length()];
				for (int i = 0; i < array.length(); i++) {
					dashLen[i] = array.optDouble(i);
				}
				this.mApi.drawDashLine(args.optDouble(PARAMS_X1), args.optDouble(PARAMS_Y1), args.optDouble(PARAMS_X2), //
								args.optDouble(PARAMS_Y2), args.optDouble(PARAMS_LINE_WIDTH), dashLen, dashLen.length);
			}
		} else if (DRAW_DASH_LINE2.equalsIgnoreCase(action)) {
			this.mApi.drawDashLine2(args.optDouble(PARAMS_X1), args.optDouble(PARAMS_Y1), args.optDouble(PARAMS_X2), //
							args.optDouble(PARAMS_Y2), args.optDouble(PARAMS_LINE_WIDTH), args.optDouble(PARAMS_DASH_LEN1), // 
							args.optDouble(PARAMS_DASH_LEN2));
		} else if (DRAW_DASH_LINE4.equalsIgnoreCase(action)) {
			this.mApi.drawDashLine4(args.optDouble(PARAMS_X1), args.optDouble(PARAMS_Y1), args.optDouble(PARAMS_X2), //
							args.optDouble(PARAMS_Y2), args.optDouble(PARAMS_LINE_WIDTH), args.optDouble(PARAMS_DASH_LEN1), // 
							args.optDouble(PARAMS_DASH_LEN2), args.optDouble(PARAMS_DASH_LEN3), args.optDouble(PARAMS_DASH_LEN4));
		} else if (DRAW_RECTANGLE.equalsIgnoreCase(action)) {
			this.mApi.drawRectangle(args.optDouble(PARAMS_X), args.optDouble(PARAMS_Y), args.optDouble(PARAMS_WIDTH), // 
							args.optDouble(PARAMS_HEIGHT), args.optDouble(PARAMS_LINE_WIDTH));
		} else if (FILL_RECTANGLE.equalsIgnoreCase(action)) {
			this.mApi.fillRectangle(args.optDouble(PARAMS_X), args.optDouble(PARAMS_Y), args.optDouble(PARAMS_WIDTH), // 
							args.optDouble(PARAMS_HEIGHT));
		} else if (DRAW_ROUND_RECTANGLE.equalsIgnoreCase(action)) {
			this.mApi.drawRoundRectangle(args.optDouble(PARAMS_X), args.optDouble(PARAMS_Y), args.optDouble(PARAMS_WIDTH), // 
							args.optDouble(PARAMS_HEIGHT), args.optDouble(PARAMS_CORNER_WIDTH), args.optDouble(PARAMS_CORNER_HEIGHT), args.optDouble(PARAMS_LINE_WIDTH));
		} else if (FILL_ROUND_RECTANGLE.equalsIgnoreCase(action)) {
			this.mApi.fillRoundRectangle(args.optDouble(PARAMS_X), args.optDouble(PARAMS_Y), args.optDouble(PARAMS_WIDTH), // 
							args.optDouble(PARAMS_HEIGHT), args.optDouble(PARAMS_CORNER_WIDTH), args.optDouble(PARAMS_CORNER_HEIGHT));
		} else if (DRAW_ELLIPSE.equalsIgnoreCase(action)) {
			this.mApi.drawEllipse(args.optDouble(PARAMS_X), args.optDouble(PARAMS_Y), args.optDouble(PARAMS_WIDTH), // 
							args.optDouble(PARAMS_HEIGHT), args.optDouble(PARAMS_LINE_WIDTH));
		} else if (FILL_ELLIPSE.equalsIgnoreCase(action)) {
			this.mApi.fillEllipse(args.optDouble(PARAMS_X), args.optDouble(PARAMS_Y), args.optDouble(PARAMS_WIDTH), // 
							args.optDouble(PARAMS_HEIGHT));
		} else if (DRAW_CIRCLE.equalsIgnoreCase(action)) {
			this.mApi.drawCircle(args.optDouble(PARAMS_X), args.optDouble(PARAMS_Y), args.optDouble(PARAMS_RADIUS), // 
							args.optDouble(PARAMS_LINE_WIDTH));
		} else if (FILL_CIRCLE.equalsIgnoreCase(action)) {
			this.mApi.fillCircle(args.optDouble(PARAMS_X), args.optDouble(PARAMS_Y), args.optDouble(PARAMS_RADIUS));
		} else if (DRAW_BARCODE.equalsIgnoreCase(action)) {
			this.mApi.draw1DBarcode(args.optString(PARAMS_TEXT), args.optInt(PARAMS_TYPE), args.optDouble(PARAMS_X), // 
							args.optDouble(PARAMS_Y), args.optDouble(PARAMS_WIDTH), args.optDouble(PARAMS_HEIGHT), // 
							args.optDouble(PARAMS_TEXT_HEIGHT));
		} else if (DRAW_QRCODE.equalsIgnoreCase(action)) {
			this.mApi.draw2DQRCode(args.optString(PARAMS_TEXT), args.optDouble(PARAMS_X), args.optDouble(PARAMS_Y), // 
							args.optDouble(PARAMS_WIDTH));
		} else if (DRAW_PDF417.equalsIgnoreCase(action)) {
			this.mApi.draw2DPdf417(args.optString(PARAMS_TEXT), args.optDouble(PARAMS_X), args.optDouble(PARAMS_Y), // 
							args.optDouble(PARAMS_WIDTH), args.optDouble(PARAMS_HEIGHT));
		} else if (DRAW_IMAGE.equalsIgnoreCase(action)) {
			this.mApi.drawImage(args.optString(PARAMS_IMAGE_FILE), args.optDouble(PARAMS_X), args.optDouble(PARAMS_Y), // 
							args.optDouble(PARAMS_WIDTH), args.optDouble(PARAMS_HEIGHT));
		}

		return super.execute(action, rawArgs, callbackContext);
	}

}
