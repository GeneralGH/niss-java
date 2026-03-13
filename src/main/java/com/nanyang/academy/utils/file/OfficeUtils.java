package com.nanyang.academy.utils.file;

import cn.hutool.core.util.StrUtil;
import com.nanyang.academy.utils.StringUtil;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Document;
import org.apache.poi.hslf.usermodel.*;
import org.apache.poi.xslf.usermodel.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @ClassName pt
 * @Description TODO
 * @Author pt
 * @Date 2023/7/20
 * @Version 1.0
 **/
public class OfficeUtils {

    public static void main(String[] args){

        String source3 = "http://192.168.0.75:8070/profile/upload/2023/07/20/演示文稿_20230720145622A008.pptx";
        String targetDir = "D:/gy/uploadPath/temp";
        String res = ppt2pdf(source3,targetDir);

        System.out.println(res);
    }
    public static String ppt2pdf(String sourcePath,String targetDir){
        try {
            //System.out.println(sourcePath);

        } catch (Exception e) {

        }
        return null;
    }


    public static boolean pptToPdf(String pptPath, String pdfDir) {

        if (StringUtil.isEmpty(pptPath)) {
            throw new RuntimeException("word文档路径不能为空");
        }

        if (StringUtil.isEmpty(pdfDir)) {
            throw new RuntimeException("pdf目录不能为空");
        }


        String pdfPath = pdfDir + StringUtil.substring(pptPath, pptPath.lastIndexOf(StrUtil.BACKSLASH), pptPath.lastIndexOf(StrUtil.DOT)) + StrUtil.DOT + "pdf";
        //System.out.println(pdfPath);
        Document document = null;
        HSLFSlideShow hslfSlideShow = null;
        FileOutputStream fileOutputStream = null;
        PdfWriter pdfWriter = null;

        try {
            hslfSlideShow = new HSLFSlideShow(new FileInputStream(pptPath));

            // 获取ppt文件页面
            Dimension dimension = hslfSlideShow.getPageSize();

            fileOutputStream = new FileOutputStream(pdfPath);

            document = new Document();

            // pdfWriter实例
            pdfWriter = PdfWriter.getInstance(document, fileOutputStream);

            document.open();

            PdfPTable pdfPTable = new PdfPTable(1);

            java.util.List<HSLFSlide> hslfSlideList = hslfSlideShow.getSlides();

            for (int i=0; i < hslfSlideList.size(); i++) {
                HSLFSlide hslfSlide = hslfSlideList.get(i);
                // 设置字体, 解决中文乱码
                for (HSLFShape shape : hslfSlide.getShapes()) {
                    HSLFTextShape textShape = (HSLFTextShape) shape;

                    for (HSLFTextParagraph textParagraph : textShape.getTextParagraphs()) {
                        for (HSLFTextRun textRun : textParagraph.getTextRuns()) {
                            textRun.setFontFamily("宋体");
                        }
                    }
                }
                BufferedImage bufferedImage = new BufferedImage((int)dimension.getWidth(), (int)dimension.getHeight(), BufferedImage.TYPE_INT_RGB);

                Graphics2D graphics2d = bufferedImage.createGraphics();

                graphics2d.setPaint(Color.white);
                graphics2d.setFont(new Font("宋体", Font.PLAIN, 12));

                hslfSlide.draw(graphics2d);

                graphics2d.dispose();

                com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance(bufferedImage, null);
                image.scalePercent(50f);

                // 写入单元格
                pdfPTable.addCell(new PdfPCell(image, true));
                document.add(image);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (document != null) {
                    document.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                if (pdfWriter != null) {
                    pdfWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     *
     * @Title: pptxToPdf
     * @param pptPath PPT文件路径
     * @param pdfDir 生成的PDF文件路径
     */
    public static boolean pptxToPdf(String pptPath, String pdfDir) {

        if (StrUtil.isEmpty(pptPath)) {
            throw new RuntimeException("word文档路径不能为空");
        }

        if (StrUtil.isEmpty(pdfDir)) {
            throw new RuntimeException("pdf目录不能为空");
        }

        String pdfPath = pdfDir + StrUtil.sub(pptPath, pptPath.lastIndexOf(StrUtil.SLASH), pptPath.lastIndexOf(StrUtil.DOT)) + StrUtil.DOT + "pdf";
        //System.out.println(pdfPath);
        Document document = null;

        XMLSlideShow slideShow = null;


        FileOutputStream fileOutputStream = null;

        PdfWriter pdfWriter = null;


        try {

            slideShow = new XMLSlideShow(new FileInputStream(pptPath));

            Dimension dimension = slideShow.getPageSize();

            fileOutputStream = new FileOutputStream(pdfPath);

            document = new Document();

            pdfWriter = PdfWriter.getInstance(document, fileOutputStream);

            document.open();

            PdfPTable pdfPTable = new PdfPTable(1);

            java.util.List<XSLFSlide> slideList = slideShow.getSlides();

            for (int i = 0, row = slideList.size(); i < row; i++) {

                XSLFSlide slide = slideList.get(i);

                // 设置字体, 解决中文乱码
                /*for (XSLFShape shape : slide.getShapes()) {
                    XSLFTextShape textShape = (XSLFTextShape) shape;

                    for (XSLFTextParagraph textParagraph : textShape.getTextParagraphs()) {
                        for (XSLFTextRun textRun : textParagraph.getTextRuns()) {
                            textRun.setFontFamily("宋体");
                        }
                    }
                }*/

                BufferedImage bufferedImage = new BufferedImage((int)dimension.getWidth(), (int)dimension.getHeight(), BufferedImage.TYPE_INT_RGB);

                Graphics2D graphics2d = bufferedImage.createGraphics();

                graphics2d.setPaint(Color.white);
                graphics2d.setFont(new java.awt.Font("宋体", java.awt.Font.PLAIN, 12));

                slide.draw(graphics2d);

                graphics2d.dispose();

                com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance(bufferedImage, null);
                image.scalePercent(50f);

                // 写入单元格
                pdfPTable.addCell(new PdfPCell(image, true));
                document.add(image);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (document != null) {
                    document.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                if (pdfWriter != null) {
                    pdfWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }


    //PPT文档转PDF
    //static final int wdDoNotSaveChanges = 0;// 不保存待定的更改。

    //static final int ppSaveAsPDF = 32;// ppt 转PDF 格式
    /**
     * PPT文档转PDF
     * @author pt
     * @date 15:17 2023/7/20
     * @param source
     * @param target
     **/
    /*public static void ppt2pdf( String source, String target) {
        System.out.println("启动PPT");
        long start = System.currentTimeMillis();
        ComThread.InitSTA(true);
        ActiveXComponent app = new ActiveXComponent("Powerpoint.Application"); //创建office的一个应用，比如你操作的是word还是ppt等
//        app.setProperty("Visible", new Variant(false));
//        app.setProperty("AutomationSecurity", new Variant(3)); // 禁用宏
        try {

            //Dispatch 调度处理类，封装了一些操作来操作office
            Dispatch presentations = app.getProperty("Presentations").toDispatch();
            System.out.println("打开文档" + source);
            Dispatch presentation = Dispatch.call(presentations, "Open",
                    source,// FileName
                    true,// ReadOnly
                    true,// Untitled 指定文件是否有标题。
                    false // WithWindow 指定文件是否可见。
            ).toDispatch();

            System.out.println("转换文档到PDF " + target);
            File tofile = new File(target);
            if (tofile.exists()) {
                tofile.delete();
            }
            Dispatch.call(presentation,//
                    "SaveAs", //
                    target, // FileName
                    ppSaveAsPDF);

            Dispatch.call(presentation, "Close");


            long end = System.currentTimeMillis();
            System.out.println("转换完成."+source+".用时：" + (end - start) + "ms.");

        } catch (Exception e) {

            System.out.println("========Error:文档转换失败：" + e.getMessage());
        } finally {
            if (app != null)
                app.invoke("Quit");


            ComThread.Release();
            ComThread.quitMainSTA();
        }
    }*/

    /**
     * excel转pdf
     * @author pt
     * @date 15:17 2023/7/20
     * @param inputFile
     * @param pdfFile
     **/
    /*public static void Ex2PDF(String inputFile, String pdfFile) {
        // 0=标准 (生成的PDF图片不会变模糊) 1=最小文件(生成的PDF图片糊的一塌糊涂)
        final int xlTypePDF = 0;
        // try {
        // User32.SetWinEventHook(new UINT(0x0003), new UINT(0x0003), 0, 0, new DWORD(0), new DWORD(0), new UINT(0));
        // } catch (IllegalAccessException e1) {
        // e1.printStackTrace();
        // } catch (NativeException e1) {
        // e1.printStackTrace();
        // }
        ComThread.InitSTA(true);
        ActiveXComponent ax = new ActiveXComponent("Excel.Application");
        long date = new Date().getTime();
        ax.setProperty("Visible", new Variant(false));
        ax.setProperty("AutomationSecurity", new Variant(3)); // 禁用宏
        try {
            Dispatch excels = ax.getProperty("Workbooks").toDispatch();
            Dispatch excel = Dispatch.invoke(excels, "Open", Dispatch.Method, new Object[]{inputFile, new Variant(false), new Variant(false)},
                    new int[9]).toDispatch();
            File tofile = new File(pdfFile);
            if (tofile.exists()) {
                tofile.delete();
            }
            // 转换格式
            Dispatch.invoke(excel, "ExportAsFixedFormat", Dispatch.Method, new Object[]{
                    new Variant(0), // PDF格式=0
                    pdfFile, new Variant(xlTypePDF), Variant.VT_MISSING, Variant.VT_MISSING, Variant.VT_MISSING, Variant.VT_MISSING,
                    new Variant(false), Variant.VT_MISSING}, new int[1]);
            long date2 = new Date().getTime();
            int time = (int) ((date2 - date) / 1000);
            Dispatch.call(excel, "Close", new Variant(false));

        } catch (Exception e) {


            e.printStackTrace();

        } finally {
            ax.invoke("Quit");
            ax = null;
            ComThread.Release();
            ComThread.quitMainSTA();
        }
    }*/

    /**
     * word转pdf
     * @author pt
     * @date 15:17 2023/7/20
     * @param wordFile
     * @param pdfFile
     **/
    /*public static void word2pdf( String wordFile, String pdfFile) {
        // 开始时间
        long start = System.currentTimeMillis();
        ComThread.InitSTA(true);
        // 打开word
        ActiveXComponent app = new ActiveXComponent("Word.Application");
        app.setProperty("Visible", new Variant(false));
        app.setProperty("AutomationSecurity", new Variant(3)); // 禁用宏
        System.out.println("开始转换...");

        try {
            // 设置word不可见,很多博客下面这里都写了这一句话，其实是没有必要的，因为默认就是不可见的，如果设置可见就是会打开一个word文档，对于转化为pdf明显是没有必要的
            //app.setProperty("Visible", false);
            // 获得word中所有打开的文档
            Dispatch documents = app.getProperty("Documents").toDispatch();
            System.out.println("打开文件: " + wordFile);
            // 打开文档
            Dispatch document = Dispatch.call(documents, "Open", wordFile, false, true).toDispatch();
            // 如果文件存在的话，不会覆盖，会直接报错，所以我们需要判断文件是否存在
            File target = new File(pdfFile);
            if (target.exists()) {
                target.delete();
            }
            System.out.println("另存为: " + pdfFile);
            // 另存为，将文档保存为pdf，其中word保存为pdf的格式宏的值是17
            Dispatch.call(document, "SaveAs", pdfFile, 17);
            // 关闭文档
            Dispatch.call(document, "Close", false);


            // 结束时间
            long end = System.currentTimeMillis();
            System.out.println("转换成功，用时：" + (end - start) + "ms");

        }catch(Exception e) {

            e.getMessage();
            System.out.println("转换失败"+e.getMessage());

        }finally {
            // 关闭office
            app.invoke("Quit", 0);
            ComThread.Release();
            ComThread.quitMainSTA();
        }
    }*/
}
