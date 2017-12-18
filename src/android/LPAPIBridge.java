package com.dothantech.lpapi.cordova;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.dothantech.lpapi.LPAPI;
import com.dothantech.lpapi.LPAPI.Callback;
import com.dothantech.printer.IDzPrinter.PrintParamName;
import com.dothantech.printer.IDzPrinter.PrintProgress;
import com.dothantech.printer.IDzPrinter.PrinterAddress;
import com.dothantech.printer.IDzPrinter.PrinterInfo;
import com.dothantech.printer.IDzPrinter.PrinterState;
import com.dothantech.printer.IDzPrinter.ProgressInfo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;

public class LPAPIBridge {
	public static final String PARAMS_NAME = "name";
	public static final String PARAMS_X = "x";
	public static final String PARAMS_X1 = "x1";
	public static final String PARAMS_X2 = "x2";
	public static final String PARAMS_Y = "y";
	public static final String PARAMS_Y1 = "y1";
	public static final String PARAMS_Y2 = "y2";
	public static final String PARAMS_WIDTH = "width";
	public static final String PARAMS_HEIGHT = "height";
	public static final String PARAMS_ORIENTATION = "orientation";
	public static final String PARAMS_CONTENT = "content";
	public static final String PARAMS_TEXT = "text";
	public static final String PARAMS_FONT_HEIGHT = "fontHeight";
	public static final String PARAMS_FONT_STYLE = "fontStyle";
	public static final String PARAMS_TEXT_HEIGHT = "textHeight";
	public static final String PARAMS_LINE_WIDTH = "lineWidth";
	public static final String PARAMS_CORNER = "corner";
	public static final String PARAMS_CORNER_WIDTH = "cornerWidth";
	public static final String PARAMS_CORNER_HEIGHT = "cornerHeight";
	public static final String PARAMS_RADIUS = "radius";
	public static final String PARAMS_DASH_COUNT = "dashCount";
	public static final String PARAMS_DASH_LEN = "dashLen";
	public static final String PARAMS_DASH_LEN1 = "dashLen1";
	public static final String PARAMS_DASH_LEN2 = "dashLen2";
	public static final String PARAMS_DASH_LEN3 = "dashLen3";
	public static final String PARAMS_DASH_LEN4 = "dashLen4";
	public static final String PARAMS_IMAGE = "image";
	public static final String PARAMS_THRESHOLD = "threshold";
	public static final String PARAMS_TYPE = "type";
	public static final String PARAMS_ALIGNMENT = "alignment";

//	public static final String PARAMS_RESULT = "result";
	public static final String ERROR_PARAM_ERROR = "参数错误";

	// 相关默认参数
	private static final double CORNER_DEFAULT = 1.5;
	private static final double LINEWIDTH_DEFAULT = 0.3;
	private static final int THRESHOLD_DEFAULT = 192;

	private static final String REG_IMG_BASE64 = "^data:image/[a-z]{3,4};base64,.*";

	private LPAPI mApi;
	private IResponse mOpenPrinterCallback;
	private IResponse mPrintCallback;

	public static abstract class IResponse {

		void success(String message) {
		};

		void success(JSONObject message) {
		};

		void success(JSONArray message) {
		};

		void fail(String message) {
		};
	}

	public LPAPIBridge() {
		this.mApi = LPAPI.Factory.createInstance(new Callback() {

			@Override
			public void onStateChange(PrinterAddress address, PrinterState state) {
				if (mOpenPrinterCallback == null)
					return;

				try {
					JSONObject result = new JSONObject();
					if (state == PrinterState.Connected || state == PrinterState.Connected2) {
						result.put("shownName", address.shownName);
						result.put("addressType", address.addressType);
						result.put("macAddress", address.macAddress);
						
						mOpenPrinterCallback.success(result);
					}
				} catch (JSONException e) {
				}
			}

			@Override
			public void onProgressInfo(ProgressInfo info, Object addiInfo) {
			}

			@Override
			public void onPrinterDiscovery(PrinterAddress address, PrinterInfo info) {
			}

			@Override
			public void onPrintProgress(PrinterAddress address, Object printData, PrintProgress progress, Object addiInfo) {
				if (mPrintCallback == null)
					return;

				if (progress == PrintProgress.Success) {
					mPrintCallback.success(addiInfo == null ? "" : addiInfo.toString());
				} else if (progress == PrintProgress.Failed) {
					mPrintCallback.fail(addiInfo == null ? "" : addiInfo.toString());
				}
			}
		});
	}

	/**
	 * 获取所有打印机；
	 */
	public String getAllPrinters(JSONObject params) {
		String models = (params == null ? null : params.optString(PARAMS_NAME));
		return mApi.getAllPrinters(models);
	}

	public JSONArray getAllPrinterAddresses(JSONObject params) {
		String models = (params == null ? null : params.optString(PARAMS_NAME));
		List<PrinterAddress> addressList = this.mApi.getAllPrinterAddresses(models);
		JSONArray array = new JSONArray();
		if (addressList != null) {
			for (PrinterAddress item : addressList) {
				JSONObject object = new JSONObject();
				try {
					object.put("addressType", item.addressType);
					object.put("shownName", item.shownName);
					object.put("macAddress", item.macAddress);
					array.put(object);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}

		return array;
	}

	public boolean openPrinter(JSONObject params, IResponse response) {
		this.mOpenPrinterCallback = response;
		return this.mApi.openPrinter(params.optString(PARAMS_NAME));
	}

	public boolean openPrinterSync(JSONObject params) {
		return mApi.openPrinterSync(params.optString(PARAMS_NAME));
	}

	/**
	 * 获取当前打印机名称；
	 */
	public String getPrinterName() {
		return this.mApi.getPrinterName();
	}

	public boolean isPrinterOpened() {
		return this.mApi.isPrinterOpened();
	}

	public void cancel() {
		this.mApi.cancel();
	}

	public void closePrinter() {
		this.mApi.closePrinter();
	}

	public boolean reopenPrinter(IResponse response) {
		this.mOpenPrinterCallback = response;
		return this.mApi.reopenPrinter();
	}

	public boolean reopenPrinterSync() {
		return this.mApi.reopenPrinterSync();
	}

	public void quit() {
		this.mApi.quit();
	}

	public boolean printImage(JSONObject params, IResponse response) {
		if (params == null)
			return false;

		String image = params.optString(PARAMS_IMAGE);
		if (!isBase64Image(image))
			return false;

		Bitmap bitmap = getBitmap(image);
		if (bitmap == null)
			return false;

		this.mPrintCallback = response;
		return this.mApi.printBitmap(bitmap, this.getPrintParams(params));
	}

	private Bundle getPrintParams(JSONObject params) {
		int printDensity = params.optInt(PrintParamName.PRINT_DENSITY);
		int printSpeed = params.optInt(PrintParamName.PRINT_SPEED);
		int printCopys = params.optInt(PrintParamName.PRINT_COPIES);

		Bundle printParam = new Bundle();
		if (printDensity != 0)
			printParam.putInt(PrintParamName.PRINT_DENSITY, printDensity);
		if (printSpeed != 0)
			printParam.putInt(PrintParamName.PRINT_SPEED, printSpeed);
		if (printCopys != 0)
			printParam.putInt(PrintParamName.PRINT_COPIES, printCopys);

		return printParam;
	}

	public boolean startJob(JSONObject params) {
		double width = params.optDouble(PARAMS_WIDTH);
		double height = params.optDouble(PARAMS_HEIGHT);
		int orientation = params.optInt(PARAMS_ORIENTATION);
		return this.mApi.startJob(width, height, orientation);
	}

	public void abortJob() {
		this.mApi.abortJob();
	}

	public boolean commitJob(JSONObject params, IResponse response) {
		this.mPrintCallback = response;

		return this.mApi.commitJobWithParam(getPrintParams(params));
	}

	public boolean startPage() {
		return this.mApi.startPage();
	}

	public void endPage() {
		this.mApi.endPage();
	}

	public void endJob() {
		this.mApi.endJob();
	}

	public JSONArray getJobPages() {
		List<Bitmap> pages = this.mApi.getJobPages();
		if (pages == null || pages.size() <= 0)
			return null;

		JSONArray array = new JSONArray();
		for (Bitmap bitmap : pages) {
			array.put(getBase64(bitmap));
		}

		return array;
	}

	// -----------------------------参数设置--------------------- //

	public int getItemOrientation() {
		return this.mApi.getItemOrientation();
	}

	public boolean setItemOrientation(JSONObject params) {
		if (params == null)
			return false;

		int orientation = params.optInt(PARAMS_ORIENTATION, -1);
		if (orientation == -1)
			return false;
		
		this.mApi.setItemOrientation(orientation);
		return true;
	}

	public int getItemHorizontalAlignment() {
		return this.mApi.getItemHorizontalAlignment();
	}

	public boolean setItemHorizontalAlignment(JSONObject params) {
		if (params == null)
			return false;

		int alignment = params.optInt(PARAMS_ALIGNMENT, -1);
		if (alignment == -1)
			return false;
		
		this.mApi.setItemHorizontalAlignment(alignment);
		return true;
	}

	public int getItemVerticalAlignment() {
		return this.mApi.getItemVerticalAlignment();
	}

	public boolean setItemVerticalAlignment(JSONObject params) {
		if (params == null)
			return false;

		int alignment = params.optInt(PARAMS_ALIGNMENT, -1);
		if (alignment == -1)
			return false;
		
		this.mApi.setItemVerticalAlignment(alignment);
		return true;
	}

	public int getItemPenAlignment() {
		return this.mApi.getItemPenAlignment();
	}

	public boolean setItemPenAlignment(JSONObject params) {
		if (params == null)
			return false;

		int penAlignment = params.optInt(PARAMS_ALIGNMENT, -1);
		if (penAlignment == -1)
			return false;
		
		this.mApi.setItemPenAlignment(penAlignment);
		return true;
	}

	// ***************************************************
	// * 打印相关图形对象。
	// ***************************************************

	public void drawText(JSONObject params) {
		String content = params.optString(PARAMS_CONTENT);
		double x = params.optDouble(PARAMS_X);
		double y = params.optDouble(PARAMS_Y);
		double width = params.optDouble(PARAMS_WIDTH);
		double height = params.optDouble(PARAMS_HEIGHT);
		double fontHeight = params.optDouble(PARAMS_FONT_HEIGHT);
		int fontStyle = params.optInt(PARAMS_FONT_STYLE);
		if (TextUtils.isEmpty(content))
			return;

		this.mApi.drawTextRegular(content, x, y, width, height, fontHeight, fontStyle);
	}

	public void drawRichText(JSONObject params) {
		String text = params.optString(PARAMS_TEXT);
		if (TextUtils.isEmpty(text))
			text = params.optString(PARAMS_CONTENT);
		double x = params.optDouble(PARAMS_X);
		double y = params.optDouble(PARAMS_Y);
		double width = params.optDouble(PARAMS_WIDTH);
		double height = params.optDouble(PARAMS_HEIGHT);
		double fontHeight = params.optDouble(PARAMS_FONT_HEIGHT);
		int fontStyle = params.optInt(PARAMS_FONT_STYLE);
		if (TextUtils.isEmpty(text))
			return;

		this.mApi.drawRichText(text, x, y, width, height, fontHeight, fontStyle);
	}

	public void draw1DBarcode(JSONObject params) {
		String text = params.optString(PARAMS_TEXT);
		if (TextUtils.isEmpty(text))
			text = params.optString(PARAMS_CONTENT);
		int type = params.optInt(PARAMS_TYPE);
		double x = params.optDouble(PARAMS_X);
		double y = params.optDouble(PARAMS_Y);
		double width = params.optDouble(PARAMS_WIDTH);
		double height = params.optDouble(PARAMS_HEIGHT);
		double textHeight = params.optDouble(PARAMS_TEXT_HEIGHT);
		if (TextUtils.isEmpty(text))
			return;

		this.mApi.draw1DBarcode(text, type, x, y, width, height, textHeight);
	}

	public void draw2DQRCode(JSONObject params) {
		String text = params.optString(PARAMS_TEXT);
		if (TextUtils.isEmpty(text))
			text = params.optString(PARAMS_CONTENT);
		double x = params.optDouble(PARAMS_X);
		double y = params.optDouble(PARAMS_Y);
		double width = params.optDouble(PARAMS_WIDTH);
		if (TextUtils.isEmpty(text))
			return;

		this.mApi.draw2DQRCode(text, x, y, width);
	}

	public void draw2DPdf417(JSONObject params) {
		String text = params.optString(PARAMS_TEXT);
		if (TextUtils.isEmpty(text))
			text = params.optString(PARAMS_CONTENT);
		double x = params.optDouble(PARAMS_X);
		double y = params.optDouble(PARAMS_Y);
		double width = params.optDouble(PARAMS_WIDTH);
		double height = params.optDouble(PARAMS_HEIGHT);
		if (TextUtils.isEmpty(text))
			return;

		this.mApi.draw2DPdf417(text, x, y, width, height);
	}

	public void drawRectangle(JSONObject params) {
		double x = params.optDouble(PARAMS_X);
		double y = params.optDouble(PARAMS_Y);
		double width = params.optDouble(PARAMS_WIDTH);
		double height = params.optDouble(PARAMS_HEIGHT);
		double lineWidth = params.optDouble(PARAMS_LINE_WIDTH, LINEWIDTH_DEFAULT);

		this.mApi.drawRectangle(x, y, width, height, lineWidth);
	}

	public void fillRectangle(JSONObject params) {
		double x = params.optDouble(PARAMS_X);
		double y = params.optDouble(PARAMS_Y);
		double width = params.optDouble(PARAMS_WIDTH);
		double height = params.optDouble(PARAMS_HEIGHT);

		this.mApi.fillRectangle(x, y, width, height);
	}

	public void drawRoundRectangle(JSONObject params) {
		double x = params.optDouble(PARAMS_X);
		double y = params.optDouble(PARAMS_Y);
		double width = params.optDouble(PARAMS_WIDTH);
		double height = params.optDouble(PARAMS_HEIGHT);
		double corner = params.optDouble(PARAMS_CORNER, CORNER_DEFAULT);
		double cornerWidth = params.optDouble(PARAMS_CORNER_WIDTH, corner);
		double cornerHeight = params.optDouble(PARAMS_CORNER_HEIGHT, corner);
		double lineWidth = params.optDouble(PARAMS_LINE_WIDTH, LINEWIDTH_DEFAULT);

		this.mApi.drawRoundRectangle(x, y, width, height, cornerWidth, cornerHeight, lineWidth);
	}

	public void fillRoundRectangle(JSONObject params) {
		double x = params.optDouble(PARAMS_X);
		double y = params.optDouble(PARAMS_Y);
		double width = params.optDouble(PARAMS_WIDTH);
		double height = params.optDouble(PARAMS_HEIGHT);
		double corner = params.optDouble(PARAMS_CORNER, CORNER_DEFAULT);
		double cornerWidth = params.optDouble(PARAMS_CORNER_WIDTH, corner);
		double cornerHeight = params.optDouble(PARAMS_CORNER_HEIGHT, corner);

		this.mApi.fillRoundRectangle(x, y, width, height, cornerWidth, cornerHeight);
	}

	public void drawEllipse(JSONObject params) {
		double x = params.optDouble(PARAMS_X);
		double y = params.optDouble(PARAMS_Y);
		double width = params.optDouble(PARAMS_WIDTH);
		double height = params.optDouble(PARAMS_HEIGHT);
		double lineWidth = params.optDouble(PARAMS_LINE_WIDTH, LINEWIDTH_DEFAULT);

		this.mApi.drawEllipse(x, y, width, height, lineWidth);
	}

	public void fillEllipse(JSONObject params) {
		double x = params.optDouble(PARAMS_X);
		double y = params.optDouble(PARAMS_Y);
		double width = params.optDouble(PARAMS_WIDTH);
		double height = params.optDouble(PARAMS_HEIGHT);

		this.mApi.fillEllipse(x, y, width, height);
	}

	public void drawCircle(JSONObject params) {
		double x = params.optDouble(PARAMS_X);
		double y = params.optDouble(PARAMS_Y);
		double radius = params.optDouble(PARAMS_RADIUS);
		double lineWidth = params.optDouble(PARAMS_LINE_WIDTH, LINEWIDTH_DEFAULT);

		this.mApi.drawCircle(x, y, radius, lineWidth);
	}

	public void fillCircle(JSONObject params) {
		double x = params.optDouble(PARAMS_X);
		double y = params.optDouble(PARAMS_Y);
		double radius = params.optDouble(PARAMS_RADIUS);

		this.mApi.fillCircle(x, y, radius);
	}

	public boolean drawLine(JSONObject params) {
		double x1 = params.optDouble(PARAMS_X1);
		double y1 = params.optDouble(PARAMS_Y1);
		double x2 = params.optDouble(PARAMS_X2);
		double y2 = params.optDouble(PARAMS_Y2);
		double lineWidth = params.optDouble(PARAMS_LINE_WIDTH, LINEWIDTH_DEFAULT);

		return this.mApi.drawLine(x1, y1, x2, y2, lineWidth);
	}

	public void drawDashLine(JSONObject params) {
		double x1 = params.optDouble(PARAMS_X1);
		double y1 = params.optDouble(PARAMS_Y1);
		double x2 = params.optDouble(PARAMS_X2);
		double y2 = params.optDouble(PARAMS_Y2);
		double lineWidth = params.optDouble(PARAMS_LINE_WIDTH, LINEWIDTH_DEFAULT);

		try {
			List<Double> dashLen = new ArrayList<Double>();
			JSONArray asDashLen = params.optJSONArray(PARAMS_DASH_LEN);
			if (asDashLen != null && asDashLen.length() > 0) {
				for (int i = 0; i < asDashLen.length(); i++) {
					dashLen.add(asDashLen.optDouble(i));
				}
			} else {
				int dashCount = params.optInt(PARAMS_DASH_COUNT, 10);
				double item = 0;
				for (int i = 0; i < dashCount; i++) {
					item = params.optDouble(PARAMS_DASH_LEN + (i + 1));
					if (item > 0) {
						dashLen.add(item);
					} else {
						break;
					}
				}
			}

			if (dashLen != null && dashLen.size() > 0) {
				double[] dDashLen = new double[dashLen.size()];
				for (int i = 0; i < dashLen.size(); i++) {
					dDashLen[i] = dashLen.get(i);
				}

				this.mApi.drawDashLine(x1, y1, x2, y2, lineWidth, dDashLen, dDashLen.length);
			}
		} catch (Exception e) {
		}
	}

	public void drawImage(JSONObject params) {
		String image = params.optString(PARAMS_IMAGE);
		double x = params.optDouble(PARAMS_X);
		double y = params.optDouble(PARAMS_Y);
		double width = params.optDouble(PARAMS_WIDTH);
		double height = params.optDouble(PARAMS_HEIGHT);
		int threshold = params.optInt(PARAMS_THRESHOLD, THRESHOLD_DEFAULT);
		if (TextUtils.isEmpty(image))
			return;

		if (isBase64Image(image))
			this.mApi.drawBitmapWithThreshold(getBitmap(image), x, y, width, height, threshold);
		else
			this.mApi.drawImageWithThreshold(image, x, y, width, height, threshold);
	}

	/**
	 * 将给定的base64字符串解析成一个bitmap对象。
	 */
	public static Bitmap getBitmap(String image) {
		if (!isBase64Image(image))
			return null;

		try {
			byte[] bytes = Base64.decode(image, Base64.DEFAULT);
			return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
		} catch (Exception e) {
			return null;
		}
	}

	public static boolean isBase64Image(String image) {
		if (TextUtils.isEmpty(image))
			return false;

		return image.matches(REG_IMG_BASE64);
	}

	/**
	 * 将给定的bitmap对象转换成base64字符串；
	 */
	public static String getBase64(Bitmap bmp) {
		if (bmp == null) {
			return null;
		}

		ByteArrayOutputStream baos = null;
		try {
			baos = new ByteArrayOutputStream();
			bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
			baos.flush();

			return Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				baos.close();
			} catch (IOException e) {
			}
		}
	}
}
