/**
 * Created by HuDianxing on 2017-12-16.
 *
 * 德佟电子标签打印机 JavaScript 接口的封装。
 *
 * Copyright(C) 2011~2017, 上海道臻信息技术有限公司
 *
 */
var LPAPI = (function () {

    /**
     * 对象初始化。
     * @param lpapi Android代码中添加JS接口的时候指定的接口名称，
     *  如果在Android指定的接口名称为"LPAPIPlugin"，则在JS中需要用"window.LPAPIPlugin"来表示该接口。
     * @constructor
     */
    function LPAPI() {
    }

    /***************************************************************************
     * 打印机的打开与断开操作。
     **************************************************************************/

    /**
     * 以字符串的形式返回已经安装过的所有打印机名称，不同打印机名称间以英文","分隔。
     * @param names 打印机型号，
     *      空字符串表示返回所有型号的打印机
     *      要获取多个型号的打印机，多个型号之间可以用";"进行分割；
     * @param success 结果回调函数；
     */
    LPAPI.prototype.getAllPrinters = function (names, success) {
        LPAPIPlugin.getAllPrinters({
            name : names
        }, success);
    };

    /**
     * 以数组的形式返货制定型号打印机的详细信息；
     * @param names 打印机型号，
     *      空字符串表示返回所有型号的打印机
     *      要获取多个型号的打印机，多个型号之间可以用";"进行分割；
     * @param success 结果回调函数；
     */
    LPAPI.prototype.getAllPrinterAddresses = function (names, success) {
        LPAPIPlugin.getAllPrinterAddresses({
            name : names
        }, success);
    };

    /**
     * 打开指定名称的打印机(异步调用)。
     * @param printerName
     *            打印机名称。打印机名称类型：
	 *            1、空字符串：打开当前客户端系统上的第一个支持的打印机；
	 *            2、打印机型号：例如：DT20S；
	 *            3、打印机名称：例如：DT20S-60901687;
	 *            4、MAC地址：打开指定地址的打印机，例如：00:18:E4:0C:68:CA。
     */
    LPAPI.prototype.openPrinter = function (printerName, success, fail) {
        LPAPIPlugin.openPrinter({
            name : printerName
        }, success, fail);
    };

    /**
     * 得到当前使用的打印机名称。
     *
     * @return 如果已连接打印机，则返回当前使用的打印机名称，否则返回空字符串。
     */
    LPAPI.prototype.getPrinterName = function (success) {
        LPAPIPlugin.getPrinterName(success);
    };

    /**
     * 判断当前打印机是否打开（连接成功）？
     */
    LPAPI.prototype.isPrinterOpened = function () {
        LPAPIPlugin.isPrinterOpened();
    };

    /**
     * 断开当前打印机的连接。
     */
    LPAPI.prototype.closePrinter = function () {
        LPAPIPlugin.closePrinter();
    };

    /**
     * 重新连接上次连接的打印机（异步调用）。
     *
     * @return 异步连接操作是否成功提交？
     * 		注意：返回成功仅仅表示操作被提交成功，并不代表着连接成功了，具体的连接结果会通过回调函数给出通知。
     */
    LPAPI.prototype.reopenPrinter = function () {
        LPAPIPlugin.reopenPrinter();
    };

    /***************************************************************************
     * 打印任务的开始，分页，结束等操作。
     **************************************************************************/

    /**
     * 以指定的参数，开始一个打印任务。
     *
     * @param width
     *            标签宽度（单位mm）。
     * @param height
     *            标签高度（单位mm）。
     * @param orientation
     *            标签打印方向。0：不旋转；90：顺时针旋转90度；180：旋转180度；270：逆时针旋转90度。
     * @return 成功与否？
     */
    LPAPI.prototype.startJob = function (width, height, orientation, success, fail) {
        LPAPIPlugin.startJob({
            width : width,
            height : height,
            orientation : orientation
        }, success, fail);
    };

    /**
     * 开始一个打印页面。
     */
    LPAPI.prototype.startPage = function (success, fail) {
        LPAPIPlugin.startPage(success, fail);
    };

    /**
     * 结束一个打印页面。
     */
    LPAPI.prototype.endPage = function () {
        LPAPIPlugin.endPage();
    };

    /**
     * 结束绘图任务。
     */
    LPAPI.prototype.endJob = function () {
        LPAPIPlugin.endJob();
    };

    /**
     * 取消绘图任务，用于取消任务提交前的所有绘制操作。
     */
    LPAPI.prototype.abortJob = function () {
        LPAPIPlugin.abortJob();
    };

    /**
     * 提交打印数据，进行真正的打印。
     */
    LPAPI.prototype.commitJob = function (success, fail, callback) {
        LPAPIPlugin.commitJob(success, fail, callback);
    };

    /**
     * 取消当前的打印操作，用于在提交打印任务后执行取消操作。
     */
    LPAPI.prototype.cancel = function () {
        LPAPIPlugin.cancel();
    };

    /**
     * 调用函数endJob() 之后可以调用该函数，获取base64图片格式的打印任务；
     * @param success
     */
    LPAPI.prototype.getJobPages = function (success) {
        LPAPIPlugin.getJobPages(success);
    };

    /**
     * 打印base64格式的图片对象
     * @param image base64格式图片；
     * @param success 打印成功回调；
     * @param fail 打印失败回调；
     */
    LPAPI.prototype.printImage = function (image, success, fail) {
        LPAPIPlugin.printImage({
            image : image
        }, success, fail);
    };

    /***************************************************************************
     * 打印参数设置。
     **************************************************************************/

    /**
     * 得到当前打印动作的顺时针旋转角度。
     *
     * @return 当前打印动作的顺时针旋转角度（0，90，180，270）。
     */
    LPAPI.prototype.getItemOrientation = function (success) {
        LPAPIPlugin.getItemOrientation(success);
    };

    /**
     * 设置打印动作的旋转角度。
     *
     * @param orientation
     *            orientation: 旋转角度。参数描述如下：
     *            0：不旋转；
     *            90：顺时针旋转90度；
     *            180：旋转180度；
     *            270：逆时针旋转90度。
     */
    LPAPI.prototype.setItemOrientation = function (orientation) {
        LPAPIPlugin.setItemOrientation({
            orientation : orientation
        });
    };

    /**
     * 得到当前打印动作的水平对齐方式。
     *
     * @return 后续打印动作的水平对齐方式。水平对齐方式值如下：
     *         0：水平居左；
     *         1：水平居中；
     *         2：水平居右；
     */
    LPAPI.prototype.getItemHorizontalAlignment = function (success) {
        LPAPIPlugin.getItemHorizontalAlignment(success);
    };

    /**
     * 设置打印动作的水平对齐方式。
     *
     * @param alignment
     *            水平对齐方式。参数描述如下：
     *            0：水平居左（默认方式）；
     *            1：水平居中；
     *            2：水平居右。
     */
    LPAPI.prototype.setItemHorizontalAlignment = function (alignment) {
        LPAPIPlugin.setItemHorizontalAlignment({
            alignment : alignment
        });
    };

    /**
     * 得到当前打印动作的垂直对齐方式。
     *
     * @return 后续打印动作的垂直对齐方式。返回结果描述如下：
     *         0：垂直居上；
     *         1：垂直居中；
     *         2：垂直居下；
     */
    LPAPI.prototype.getItemVerticalAlignment = function (success) {
        LPAPIPlugin.getItemVerticalAlignment(success);
    };

    /**
     * 设置打印动作的垂直对齐方式。
     *
     * @param alignment
     *            垂直对齐方式，参数描述如下：
     *            0：垂直居上（默认方式）；
     *            1：垂直居中；
     *            2：垂直居下。
     */
    LPAPI.prototype.setItemVerticalAlignment = function (alignment) {
        LPAPIPlugin.setItemVerticalAlignment({
            alignment : alignment
        });
    };

    /**
     * 得到线条画笔对齐方式。
     *
     * @return 线条画笔对齐方式（{@link PenAlignment}），<br>
     *         数值为以下两者之一：<br>
     *         PenAlignment.CENTER：绘制的线以指定的位置为中央；<br>
     *         PenAlignment.INSET：绘制的线在指定的位置内侧。<br>
     */
    LPAPI.prototype.getItemPenAlignment = function (success) {
        LPAPIPlugin.getItemPenAlignment(success);
    };

    /**
     * 设置线条画笔对齐方式。
     *
     * @param penAlignment
     *            线条画笔对齐方式（{@link PenAlignment}），<br>
     *            数值为以下两者之一：<br>
     *            PenAlignment.CENTER：绘制的线以指定的位置为中央； <br>
     *            PenAlignment.INSET：绘制的线在指定的位置内侧。
     */
    LPAPI.prototype.setItemPenAlignment = function (penAlignment) {
        LPAPIPlugin.setItemPenAlignment({
            alignment : penAlignment
        });
    };

    /***************************************************************************
     * 打印对象的绘制操作。
     **************************************************************************/

    /**
     * 打印文本。
     *
     * @param text
     *            文本内容。
     * @param x
     *            打印对象的位置(单位mm)。
     * @param y
     *            打印对象的位置(单位mm)。
     * @param width
     *            打印对象的宽度(单位mm)。
     * @param height
     *            打印对象的高度(单位mm)。
     *            height 为 0 时，真正的打印文本高度会根据内容来扩展；否则当指定的高度不足以打印指定的文本时，会自动缩小字体来适应指定的高度进行文本打印。
     * @param fontHeight
     *            文本的字体高度(单位mm)。
     * @param fontStyle
     *            文本的字体风格（可按位组合），可以不指定，默认为0（正常）。0：正常；1：粗体；2：斜体；3：粗斜体 ；4：下划线；8：删除线。
     * @return 打印成功与否？
     */
    LPAPI.prototype.drawText = function (text, x, y, width, height, fontHeight, fontStyle) {
        LPAPIPlugin.drawText({
            x : x,
            y : y,
            width : width,
            height : height,
            fontHeight : fontHeight,
            fontStyle : fontStyle
        });
    };

    /**
     * 以指定的线宽，打印矩形框。
     *
     * @param x
     *            绘制的矩形框的左上角水平位置（单位mm）。
     * @param y
     *            绘制的矩形框的左上角垂直位置（单位mm）。
     * @param width
     *            绘制的矩形框的水平宽度（单位mm）。
     * @param height
     *            绘制的矩形框的垂直高度（单位mm）。
     * @param lineWidth
     *            矩形框的线宽（单位mm）。矩形框的线宽是向矩形框内部延伸的。
     * @return 打印成功与否？
     */
    LPAPI.prototype.drawRectangle = function (x, y, width, height, lineWidth) {
        LPAPIPlugin.drawRectangle({
            x : x,
            y : y,
            width : width,
            height : height,
            lineWidth : lineWidth
        });
    };

    /**
     * 打印填充的矩形框。
     *
     * @param x
     *            绘制的填充矩形框的左上角水平位置（单位mm）。
     * @param y
     *            绘制的填充矩形框的左上角垂直位置（单位mm）。
     * @param width
     *            绘制的填充矩形框的水平宽度（单位mm）。
     * @param height
     *            绘制的填充矩形框的垂直高度（单位mm）。
     * @return 打印成功与否？
     */
    LPAPI.prototype.fillRectangle = function (x, y, width, height) {
        LPAPIPlugin.fillRectangle({
            x : x,
            y : y,
            width : width,
            height : height
        });
    };

    /**
     * 以指定的线宽，打印圆角矩形框。
     *
     * @param x
     *            绘制的圆角矩形框的左上角水平位置（单位mm）。
     * @param y
     *            绘制的圆角矩形框的左上角垂直位置（单位mm）。
     * @param width
     *            绘制的圆角矩形框的水平宽度（单位mm）。
     * @param height
     *            绘制的圆角矩形框的垂直高度（单位mm）。
     * @param cornerWidth
     *            圆角宽度（单位mm）。
     * @param cornerHeight
     *            圆角高度（单位mm）。
     * @param lineWidth
     *            圆角矩形框的线宽（单位mm）。圆角矩形框的线宽是向圆角矩形框内部延伸的。
     * @return 打印成功与否？
     */
    LPAPI.prototype.drawRoundRectangle = function (x, y, width, height, cornerWidth, cornerHeight, lineWidth) {
        LPAPIPlugin.drawRoundRectangle({
            x : x,
            y : y,
            width : width,
            height : height,
            cornerWidth : cornerWidth,
            cornerHeight : cornerHeight,
            lineWidth : lineWidth
        });
    };

    /**
     * 打印填充的圆角矩形框。
     *
     * @param x
     *            绘制的填充圆角矩形框的左上角水平位置（单位mm）。
     * @param y
     *            绘制的填充圆角矩形框的左上角垂直位置（单位mm）。
     * @param width
     *            绘制的填充圆角矩形框的水平宽度（单位mm）。
     * @param height
     *            绘制的填充圆角矩形框的垂直高度（单位mm）。
     * @param cornerWidth
     *            圆角宽度（单位mm）。
     * @param cornerHeight
     *            圆角高度（单位mm）。
     * @return 打印成功与否？
     */
    LPAPI.prototype.fillRoundRectangle = function (x, y, width, height, cornerWidth, cornerHeight) {
        LPAPIPlugin.fillRoundRectangle({
            x : x,
            y : y,
            width : width,
            height : height,
            cornerWidth : cornerWidth,
            cornerHeight : cornerHeight
        });
    };

    /**
     * 以指定的线宽，打印椭圆/圆。
     *
     * @param x
     *            绘制的椭圆的左上角水平位置（单位mm）。
     * @param y
     *            绘制的椭圆的左上角垂直位置（单位mm）。
     * @param width
     *            绘制的椭圆的水平宽度（单位mm）。
     * @param height
     *            绘制的椭圆的垂直高度（单位mm）。
     * @param lineWidth
     *            椭圆的线宽（单位mm）。椭圆的线宽是向椭圆内部延伸的。
     * @return 打印成功与否？
     */
    LPAPI.prototype.drawEllipse = function (x, y, width, height, lineWidth) {
        LPAPIPlugin.drawEllipse({
            x : x,
            y : y,
            width : width,
            height : height,
            lineWidth : lineWidth
        });
    };

    /**
     * 打印填充的椭圆/圆。
     *
     * @param x
     *            绘制的填充椭圆的左上角水平位置（单位mm）。
     * @param y
     *            绘制的填充椭圆的左上角垂直位置（单位mm）。
     * @param width
     *            绘制的填充椭圆的水平宽度（单位mm）。
     * @param height
     *            绘制的填充椭圆的垂直高度（单位mm）。
     * @return 打印成功与否？
     */
    LPAPI.prototype.fillEllipse = function (x, y, width, height) {
        LPAPIPlugin.fillEllipse({
            x : x,
            y : y,
            width : width,
            height : height
        });
    };

    /**
     * 以指定的线宽，打印圆。
     *
     * @param x
     *            绘制的填充椭圆的左上角水平位置（单位mm）。
     * @param y
     *            绘制的填充椭圆的左上角垂直位置（单位mm）。
     * @param radius
     *            绘制圆的半径（单位mm）。
     * @param lineWidth
     *            圆的线宽（单位mm）。圆的线宽是向圆内部延伸的。
     * @return 打印成功与否？
     */
    LPAPI.prototype.drawCircle = function (x, y, radius, lineWidth) {
        LPAPIPlugin.drawCircle({
            x : x,
            y : y,
            radius : radius,
            lineWidth : lineWidth
        });
    };

    /**
     * 打印填充的圆。
     *
     * @param x
     *            绘制的填充椭圆的左上角水平位置（单位mm）。
     * @param y
     *            绘制的填充椭圆的左上角垂直位置（单位mm）。
     * @param radius
     *            绘制的填圆的半径（单位mm）。
     * @return 打印成功与否？
     */
    LPAPI.prototype.fillCircle = function (x, y, radius) {
        LPAPIPlugin.fillCircle({
            x : x,
            y : y,
            radius : radius
        });
    };

    /**
     * 打印线（直线/斜线）。
     *
     * @param x1
     *            线的起点的水平位置（单位mm）。
     * @param y1
     *            线的起点的垂直位置（单位mm）。
     * @param x2
     *            线的终点的水平位置（单位mm）。
     * @param y2
     *            线的终点的垂直位置（单位mm）。
     * @param lineWidth
     *            线宽（单位mm）。线宽是向线的下方延伸的。
     * @return 打印成功与否？
     */
    LPAPI.prototype.drawLine = function (x1, y1, x2, y2, lineWidth) {
        LPAPIPlugin.drawLine({
            x1 : x1,
            y1 : y1,
            x2 : x2,
            y2 : y2,
            lineWidth : lineWidth
        });
    };

    /**
     * 打印点划线。
     *
     * @param x1
     *            线的起点的水平位置（单位mm）。
     * @param y1
     *            线的起点的垂直位置（单位mm）。
     * @param x2
     *            线的终点的水平位置（单位mm）。
     * @param y2
     *            线的终点的垂直位置（单位mm）。
     * @param lineWidth
     *            线宽（单位mm）。线宽是向线的下方延伸的。
     * @param dashLen
     *            点划线线段长度的数组（单位mm）。
     * @param dashCount
     *            点划线线段长度数组的元素个数。
     * @return 打印成功与否。
     */
    LPAPI.prototype.drawDashLine = function (x1, y1, x2, y2, lineWidth, dashLen, dashCount) {
        LPAPIPlugin.drawDashLine({
            x1 : x1,
            y1 : y1,
            x2 : x2,
            y2 : y2,
            lineWidth : lineWidth,
            dashLen : dashLen,
            dashCount : dashCount
        });
    };

    /**
     * 打印点划线。
     *
     * @param x1
     *            线的起点的水平位置（单位mm）。
     * @param y1
     *            线的起点的垂直位置（单位mm）。
     * @param x2
     *            线的终点的水平位置（单位mm）。
     * @param y2
     *            线的终点的垂直位置（单位mm）。
     * @param lineWidth
     *            线宽（单位mm）。线宽是向线的下方延伸的。
     * @param dashLen1
     *            点划线第一段的长度（单位mm）。
     * @param dashLen2
     *            点划线第二段的长度（单位mm）。
     * @return 打印成功与否。
     */
    LPAPI.prototype.drawDashLine2 = function (x1, y1, x2, y2, lineWidth, dashLen1, dashLen2) {
        LPAPIPlugin.drawDashLine({
            x1 : x1,
            y1 : y1,
            x2 : x2,
            y2 : y2,
            lineWidth : lineWidth,
            dashLen1 : dashLen1,
            dashLen2 : dashLen2
        });
    };

    /**
     * 打印点划线。
     *
     * @param x1
     *            线的起点的水平位置（单位mm）。
     * @param y1
     *            线的起点的垂直位置（单位mm）。
     * @param x2
     *            线的终点的水平位置（单位mm）。
     * @param y2
     *            线的终点的垂直位置（单位mm）。
     * @param lineWidth
     *            线宽（单位mm）。线宽是向线的下方延伸的。
     * @param dashLen1
     *            点划线第一段的长度（单位mm）。
     * @param dashLen2
     *            点划线第二段的长度（单位mm）。
     * @param dashLen3
     *            点划线第三段的长度（单位mm）。
     * @param dashLen4
     *            点划线第四段的长度（单位mm）。
     * @return 打印成功与否。
     */
    LPAPI.prototype.drawDashLine4 = function (x1, y1, x2, y2, lineWidth, dashLen1, dashLen2, dashLen3, dashLen4) {
        LPAPIPlugin.drawDashLine({
            x1 : x1,
            y1 : y1,
            x2 : x2,
            y2 : y2,
            lineWidth : lineWidth,
            dashLen1 : dashLen1,
            dashLen2 : dashLen2,
            dashLen3 : dashLen3,
            dashLen4 : dashLen4
        });
    };

    /**
     * 打印一维条码。
     *
     * @param text
     *            需要绘制的一维条码的内容。
     * @param type
     *            一维条码的编码类型参考文档。
     * @param x
     *            绘制的一维条码的左上角水平位置（单位mm）。
     * @param y
     *            绘制的一维条码的左上角垂直位置（单位mm）。
     * @param width
     *            一维条码的整体显示宽度。
     * @param height
     *            一维条码的显示高度（包括供人识读文本）。
     * @param textHeight
     *            供人识读文本的高度（单位mm），建议为3毫米。
     * @return 打印成功与否？
     */
    LPAPI.prototype.draw1DBarcode = function (text, type, x, y, width, height, textHeight) {
        LPAPIPlugin.draw1DBarcode({
            text : text,
            type : type,
            x : x,
            y : y,
            width : width,
            height : height,
            textHeight : textHeight
        });
    };

    /**
     * 打印 QRCode 二维码。
     *
     * @param text
     *            需要绘制的QRCode二维码的内容。
     * @param x
     *            绘制的QRCode二维码的左上角水平位置（单位mm）。
     * @param y
     *            绘制的QRCode二维码的左上角垂直位置（单位mm）。
     * @param width
     *            绘制的QRCode二维码的水平宽度（单位mm）。
     * @return 打印成功与否？
     */
    LPAPI.prototype.draw2DQRCode = function (text, x, y, width) {
        LPAPIPlugin.draw2DQRCode({
            text : text,
            x : x,
            y : y,
            width : width
        });
    };

    /**
     * 打印 Pdf417 二维码。
     *
     * @param text
     *            需要绘制的Pdf417二维码的内容。
     * @param x
     *            绘制的Pdf417二维码的左上角水平位置（单位mm）。
     * @param y
     *            绘制的Pdf417二维码的左上角垂直位置（单位mm）。
     * @param width
     *            绘制的Pdf417二维码的水平宽度（单位mm）。
     * @param height
     *            绘制的Pdf417二维码的垂直高度（单位mm）。
     * @return 打印成功与否？
     */
    LPAPI.prototype.draw2DPdf417 = function (text, x, y, width, height) {
        LPAPIPlugin.draw2DPdf417({
            text : text,
            x : x,
            y : y,
            width : width,
            height : height
        });
    };

    /**
     * 打印图片。
     *
     * @param image
     *            图片路径。
     * @param x
     *            打印对象在水平方向上的位置(单位mm)。
     * @param y
     *            打印对象在垂直方向上的位置(单位mm)。
     * @param width
     *            打印对象的宽度(单位mm)。
     * @param height
     *            打印对象的高度(单位mm)。
     * @param threshold
     *            绘制位图的灰度阀值。
     *            256 表示绘制灰度图片；
     *            257 表示绘制原色图片；
     *            0～255表示绘制黑白图片，原图颜色>=灰度阀值的点会被认为是白色，而原图颜色<灰度阀值的点会被认为是黑色。默认值为192。
     * @return 打印成功与否？
     */
    LPAPI.prototype.drawImage = function (image, x, y, width, height, threshold) {
        LPAPIPlugin.drawImage({
            image : image,
            x : x,
            y : y,
            width : width,
            height : height,
            threshold : threshold
        });
    };

    return LPAPI;
})();

module.exports = LPAPI;