package com.dothantech.lpapi.cordova;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONException;
import org.json.JSONObject;

import com.dothantech.lpapi.cordova.LPAPIBridge.IResponse;

public class LPAPIPlugin extends CordovaPlugin {

	private static final String ACTION_GET_ALL_PRINTER = "getAllPrinters";
	private static final String ACTION_GET_ALL_PRINTER_ADDRESS = "getAllPrinterAddresses";
	private static final String ACTION_GET_PRINTER_NAME = "getPrinterName";
	private static final String ACTION_IS_PRINTER_OPENED = "isPrinterOpened";
	private static final String ACTION_OPEN_PRINTER = "openPrinter";
	private static final String ACTION_REOPEN_PRINTER = "reopenPrinter";
	private static final String ACTION_CLOSE_PRINTER = "closePrinter";
	private static final String ACTION_GET_HORIZONTAL_ALIGNMENT = "getItemHorizontalAlignment";
	private static final String ACTION_SET_HORIZONTAL_ALIGNMENT = "setItemHorizontalAlignment";
	private static final String ACTION_GET_VERTICAL_ALIGNMENT = "getItemVerticalAlignment";
	private static final String ACTION_SET_VERTICAL_ALIGNMENT = "setItemVerticalAlignment";
	private static final String ACTION_GET_ORIENTATION = "getItemOrientation";
	private static final String ACTION_SET_ORIENTATION = "setItemOrientation";
	private static final String ACTION_GET_PEN_ALIGNMENT = "getItemPenAlignment";
	private static final String ACTION_SET_PEN_ALIGNMENT = "setItemPenAlignment";
	private static final String ACTION_START_JOB = "startJob";
	private static final String ACTION_END_JOB = "endJob";
	private static final String ACTION_COMMIT_JOB = "commitJob";
	private static final String ACTION_ABORT_JOB = "abortJob";
	private static final String ACTION_START_PAGE = "startPage";
	private static final String ACTION_END_PAGE = "endPage";
	private static final String ACTION_GET_JOBPAGES = "getJobPages";
	private static final String ACTION_PRINT_IMAGE = "printImage";
	private static final String ACTION_CANCEL = "cancel";
	
	private static final String ACTION_DRAW_TEXT = "drawText";
	private static final String ACTION_DRAW_RICH_TEXT = "drawRichText";
	private static final String ACTION_DRAW_BARCODE = "draw1DBarcode";
	private static final String ACTION_DRAW_QRCODE = "draw2DQRCode";
	private static final String ACTION_DRAW_PDF417 = "draw2DPdf417";
	private static final String ACTION_DRAW_LINE = "drawLine";
	private static final String ACTION_DRAW_DASH_LINE = "drawDashLine";
	private static final String ACTION_DRAW_RECTANGLE = "drawRectangle";
	private static final String ACTION_DRAW_ROUND_RECTANGLE = "drawRoundRectangle";
	private static final String ACTION_DRAW_ELLIPSE = "drawEllipse";
	private static final String ACTION_DRAW_CIRCLE = "drawCircle";
	private static final String ACTION_FILL_RECTANGLE = "fillRectangle";
	private static final String ACTION_FILL_ROUND_RECTANGLE = "fillRoundRectangle";
	private static final String ACTION_FILL_ELLIPSE = "fillEllipse";
	private static final String ACTION_FILL_CIRCLE = "fillCircle";
	private static final String ACTION_DRAW_IMAGE = "drawImage";

	private LPAPIBridge mApi;
	
	@Override
	public void initialize(CordovaInterface cordova, CordovaWebView webView) {
		super.initialize(cordova, webView);
		this.mApi = new LPAPIBridge();
	}
	
	@Override
	public boolean execute(String action, String rawArgs, final CallbackContext callbackContext) throws JSONException {
		JSONObject params = (rawArgs == null ? new JSONObject() : new JSONObject(rawArgs));
		
		if (ACTION_GET_ALL_PRINTER.equalsIgnoreCase(action)) {
			callbackContext.success(this.mApi.getAllPrinters(params));
			return true;
		} else if (ACTION_GET_PRINTER_NAME.equalsIgnoreCase(action)) {
			callbackContext.success(this.mApi.getPrinterName());
			return true;
		} else if (ACTION_GET_ALL_PRINTER_ADDRESS.equalsIgnoreCase(action)) {
			callbackContext.success(this.mApi.getAllPrinterAddresses(params));
			return true;
		} else if (ACTION_IS_PRINTER_OPENED.equalsIgnoreCase(action)) {
			if (this.mApi.isPrinterOpened())
				callbackContext.success();
			else
				callbackContext.error(0);
			return true;
		} else if (ACTION_OPEN_PRINTER.equalsIgnoreCase(action)) {
			if (!this.mApi.openPrinter(params, new IResponse() {
				@Override
				void success(JSONObject message) {
					callbackContext.success(message);
				}
				@Override
				void success(String message) {
					callbackContext.success(message);
				}
				@Override
				void fail(String message) {
					callbackContext.error(message);
				}
			})) {
				callbackContext.error(0);
			}
			return true;
		} else if (ACTION_REOPEN_PRINTER.equalsIgnoreCase(action)) {
			if(!this.mApi.reopenPrinter(new IResponse() {
				@Override
				void success(JSONObject message) {
					callbackContext.success(message);
				}
				@Override
				void success(String message) {
					callbackContext.success(message);
				}
				@Override
				void fail(String message) {
					callbackContext.error(message);
				}
			})) {
				callbackContext.error(0);
			}
			return true;
		} else if (ACTION_CLOSE_PRINTER.equalsIgnoreCase(action)) {
			this.mApi.closePrinter();
			return true;
		} else if (ACTION_GET_HORIZONTAL_ALIGNMENT.equalsIgnoreCase(action)) {
			callbackContext.success(this.mApi.getItemHorizontalAlignment());
			return true;
		} else if (ACTION_SET_HORIZONTAL_ALIGNMENT.equalsIgnoreCase(action)) {
			if (this.mApi.setItemHorizontalAlignment(params))
				callbackContext.success();
			else 
				callbackContext.error(LPAPIBridge.ERROR_PARAM_ERROR);
			return true;
		} else if (ACTION_GET_VERTICAL_ALIGNMENT.equalsIgnoreCase(action)) {
			callbackContext.success(this.mApi.getItemVerticalAlignment());
			return true;
		} else if (ACTION_SET_VERTICAL_ALIGNMENT.equalsIgnoreCase(action)) {
			if (this.mApi.setItemVerticalAlignment(params))
				callbackContext.success();
			else 
				callbackContext.error(LPAPIBridge.ERROR_PARAM_ERROR);
			return true;
		} else if (ACTION_GET_ORIENTATION.equalsIgnoreCase(action)) {
			callbackContext.success(this.mApi.getItemOrientation());
			return true;
		} else if (ACTION_SET_ORIENTATION.equalsIgnoreCase(action)) {
			if (this.mApi.setItemOrientation(params))
				callbackContext.success();
			else 
				callbackContext.error(LPAPIBridge.ERROR_PARAM_ERROR);
			return true;
		} else if (ACTION_GET_PEN_ALIGNMENT.equalsIgnoreCase(action)) {
			callbackContext.success(this.mApi.getItemPenAlignment());
			return true;
		} else if (ACTION_SET_PEN_ALIGNMENT.equalsIgnoreCase(action)) {
			if (this.mApi.setItemPenAlignment(params))
				callbackContext.success();
			else 
				callbackContext.error(LPAPIBridge.ERROR_PARAM_ERROR);
			return true;
		}
		// 打印任务
		else if (ACTION_START_JOB.equalsIgnoreCase(action)) {
			if (this.mApi.startJob(params))
				callbackContext.success();
			else
				callbackContext.error(0);
			return true;
		} else if (ACTION_END_JOB.equalsIgnoreCase(action)) {
			this.mApi.endJob();
			return true;
		} else if (ACTION_ABORT_JOB.equalsIgnoreCase(action)) {
			this.mApi.abortJob();
			return true;
		} else if (ACTION_COMMIT_JOB.equalsIgnoreCase(action)) {
			if (!this.mApi.commitJob(params, new IResponse() {
				@Override
				void success(String message) {
					callbackContext.success(message);
				}
				@Override
				void fail(String message) {
					callbackContext.error(message);
				}
			})) {
				callbackContext.error("OK");
			} else {
				callbackContext.success();
			}
			return true;
		} else if (ACTION_GET_JOBPAGES.equalsIgnoreCase(action)) {
			callbackContext.success(this.mApi.getJobPages());
			return true;
		} else if (ACTION_PRINT_IMAGE.equalsIgnoreCase(action)) {
			if (!this.mApi.printImage(params, new IResponse() {
				@Override
				void success(String message) {
					callbackContext.success(message);
				}
				@Override
				void fail(String message) {
					callbackContext.error(message);
				}
			})) {
				callbackContext.error("");
			}
			return true;
		}
		// 分页
		else if (ACTION_START_PAGE.equalsIgnoreCase(action)) {
			if (this.mApi.startPage())
				callbackContext.success();
			else
				callbackContext.error(0);
			return true;
		} else if (ACTION_END_PAGE.equalsIgnoreCase(action)) {
			this.mApi.endPage();
			return true;
		} else if (ACTION_CANCEL.equalsIgnoreCase(action)) {
			this.mApi.cancel();
			return true;
		}
		// 打印对象的绘制
		else if (ACTION_DRAW_TEXT.equalsIgnoreCase(action)) {
			this.mApi.drawText(params);
			return true;
		} else if (ACTION_DRAW_RICH_TEXT.equalsIgnoreCase(action)) {
			this.mApi.drawRichText(params);
			return true;
		} else if (ACTION_DRAW_LINE.equalsIgnoreCase(action)) {
			this.mApi.drawLine(params);
			return true;
		} else if (ACTION_DRAW_DASH_LINE.equalsIgnoreCase(action)) {
			this.mApi.drawDashLine(params);
			return true;
		} else if (ACTION_DRAW_RECTANGLE.equalsIgnoreCase(action)) {
			this.mApi.drawRectangle(params);
			return true;
		} else if (ACTION_FILL_RECTANGLE.equalsIgnoreCase(action)) {
			this.mApi.fillRectangle(params);
			return true;
		} else if (ACTION_DRAW_ROUND_RECTANGLE.equalsIgnoreCase(action)) {
			this.mApi.drawRoundRectangle(params);
			return true;
		} else if (ACTION_FILL_ROUND_RECTANGLE.equalsIgnoreCase(action)) {
			this.mApi.fillRoundRectangle(params);
			return true;
		} else if (ACTION_DRAW_ELLIPSE.equalsIgnoreCase(action)) {
			this.mApi.drawEllipse(params);
			return true;
		} else if (ACTION_FILL_ELLIPSE.equalsIgnoreCase(action)) {
			this.mApi.fillEllipse(params);
			return true;
		} else if (ACTION_DRAW_CIRCLE.equalsIgnoreCase(action)) {
			this.mApi.drawCircle(params);
			return true;
		} else if (ACTION_FILL_CIRCLE.equalsIgnoreCase(action)) {
			this.mApi.fillCircle(params);
			return true;
		} else if (ACTION_DRAW_BARCODE.equalsIgnoreCase(action)) {
			this.mApi.draw1DBarcode(params);
			return true;
		} else if (ACTION_DRAW_QRCODE.equalsIgnoreCase(action)) {
			this.mApi.draw2DQRCode(params);
			return true;
		} else if (ACTION_DRAW_PDF417.equalsIgnoreCase(action)) {
			this.mApi.draw2DPdf417(params);
			return true;
		} else if (ACTION_DRAW_IMAGE.equalsIgnoreCase(action)) {
			this.mApi.drawImage(params);
			return true;
		}

		return super.execute(action, rawArgs, callbackContext);
	}

}
