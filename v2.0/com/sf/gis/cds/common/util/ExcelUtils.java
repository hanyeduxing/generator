package com.sf.gis.cds.common.util;

import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.sf.gis.cds.common.exception.MyException;

/**
 * @Description :
 * @Author : 80003818
 * @Date : 2018/12/12.
 */
public class ExcelUtils {

    private static final Logger logger = LoggerFactory.getLogger(ExcelUtils.class);
    private static final String EXCEL_XLS = "xls";
    private static final String EXCEL_XLSX = "xlsx";



    /**
     * 读取Excel测试，兼容 Excel 2003/2007/2010
     * @throws Exception
     */
    @SuppressWarnings("deprecation")
	public static List<Object[]> readExcel(MultipartFile excelFile){
        List<Object[]> list = new ArrayList<>();

        //获取文件名
        String fileName = excelFile.getOriginalFilename();

        // 同时支持Excel 2003、2007
        checkExcelVaild(fileName);



        try (InputStream in = excelFile.getInputStream();
             Workbook workbook = getWorkbok(fileName,in);) {

            //设置当前excel中sheet的下标：0开始
            // 遍历第一个Sheet
            Sheet sheet = workbook.getSheetAt(0);

            //获取总行数
            int countRow = sheet.getLastRowNum();
            //如果为空表格，结束
            if(countRow <= 0){
                return list;
            }

            // 为跳过第一行目录设置count
            int count = 0;
            for (Row row : sheet) {
                // 跳过第一的目录
                if(count < 1 ) {
                    count++;
                    continue;
                }
                //列号 从0开始
                int colnum = 0;
                //如果当前行没有数据，跳出循环(表格读取完成)
                Cell temp = row.getCell(colnum);
                if(temp == null){
                    return list;
                }
                if(Cell.CELL_TYPE_BLANK == temp.getCellType()){
                    return list;
                }

                //获取总列数
                int end = row.getLastCellNum();
                Object[] objs = new Object[end];
                for (int i = 0; i < end; i++) {
                    Cell cell = row.getCell(i);
                    if(cell == null) {
                        objs[i] = null;
                        continue;
                    }

                    objs[i] = getValue(cell);
                }
                list.add(objs);
            }
        } catch (Exception e) {
            logger.warn("Excel 文件读取出错，文件名称{},错误信息{}",fileName,e.getMessage(),e);
            throw new MyException("Excel 文件读取出错");
        }
        return list;
    }

    /**
     * 检查文件格式
     * @param fileName
     * @throws Exception
     */
    public static void checkExcelVaild(String fileName) {
        if (!fileName.endsWith(EXCEL_XLS) && !fileName.endsWith(EXCEL_XLSX)) {
            throw new MyException("文件不是Excel");
        }
    }

    /**
     * 判断Excel的版本,获取Workbook
     * @param in
     * @return
     * @throws IOException
     */
    public static Workbook getWorkbok(String fileName, InputStream in) throws IOException {
        Workbook wb = null;
        if(fileName.endsWith(EXCEL_XLS)){
            //Excel 2003
            wb = new HSSFWorkbook(in);
        }else if(fileName.endsWith(EXCEL_XLSX)){
            // Excel 2007/2010
            wb = new XSSFWorkbook(in);
        }
        return wb;
    }


    /**
     * 获取单元格数据
     * @param cell
     * @return
     */
    @SuppressWarnings("deprecation")
	private static Object getValue(Cell cell) {
        Object obj = null;
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC:
                obj = cell.getNumericCellValue();
                break;
            case Cell.CELL_TYPE_STRING:
                obj = cell.getStringCellValue();
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                obj = cell.getBooleanCellValue();
                break;
            case Cell.CELL_TYPE_ERROR:
                obj = cell.getErrorCellValue();
                break;
            default:
                break;
        }
        return obj;
    }
    
    
    /**
	 * 创建表格标题
	 * 
	 * @param wb
	 *            Excel文档对象
	 * @param sheet
	 *            工作表对象
	 * @param headString
	 *            标题名称
	 * @param col
	 *            标题占用列数
	 */
	@SuppressWarnings("deprecation")
	public static void createHeadTittle(XSSFWorkbook wb, XSSFSheet sheet, String headString, int col) {
		XSSFRow row = sheet.createRow(0); // 创建Excel工作表的行
		XSSFCell cell = row.createCell(0); // 创建Excel工作表指定行的单元格
		row.setHeight((short) 1000); // 设置高度

		cell.setCellType(XSSFCell.CELL_TYPE_STRING); // 定义单元格为字符串类型
		cell.setCellValue(new XSSFRichTextString(headString));

		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, col)); // 指定标题合并区域

		// 定义单元格格式，添加单元格表样式，并添加到工作簿
		XSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐
		cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); // 指定单元格垂直居中个对齐
		cellStyle.setWrapText(true); // 指定单元格自动换行

		// 设置单元格字体
		XSSFFont font = wb.createFont();
		font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("微软雅黑");
		font.setFontHeightInPoints((short) 16); // 字体大小

		cellStyle.setFont(font);
		cell.setCellStyle(cellStyle);
	}

	
	/**
	 * 创建表头
	 * 
	 * @param wb
	 *            Excel文档对象
	 * @param sheet
	 *            工作表对象
	 * @param thead
	 *            表头内容
	 * @param columnWidth
	 *            每一列宽度
	 */
	@SuppressWarnings("deprecation")
	public static void createThead(XSSFWorkbook wb, XSSFSheet sheet, String[] thead, int[] columnWidth) {
		XSSFRow row1 = sheet.createRow(0);
		row1.setHeight((short) 600);
		// 定义单元格格式，添加单元格表样式，并添加到工作簿
		XSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		cellStyle.setWrapText(true);
		cellStyle.setFillForegroundColor(new XSSFColor(Color.GREEN)); // 设置背景色
		cellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN); // 设置右边框类型
		cellStyle.setRightBorderColor(new XSSFColor(Color.BLACK)); // 设置右边框颜色

		// 设置单元格字体
		XSSFFont font = wb.createFont();
		font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 12);
		cellStyle.setFont(font);

		// 设置表头内容
		for (int i = 0; i < thead.length; i++) {
			XSSFCell cell1 = row1.createCell(i);
			cell1.setCellType(XSSFCell.CELL_TYPE_STRING);
			cell1.setCellValue(new XSSFRichTextString(thead[i]));
			cell1.setCellStyle(cellStyle);
		}

		// 设置每一列宽度
		for (int i = 0; i < columnWidth.length; i++) {
			sheet.setColumnWidth(i, 256 * columnWidth[i] + 184);
		}
	}
	
	
	/**
	 * 填入数据
	 * 
	 * @param wb
	 *            // Excel文档对象
	 * @param sheet
	 *            // 工作表对象
	 * @param result
	 *            // 表数据
	 */
	@SuppressWarnings("deprecation")
	public static void createTable(XSSFWorkbook wb, XSSFSheet sheet, List<List<String>> result, int[] sheetWidth) {
		// 定义单元格格式，添加单元格表样式，并添加到工作薄
		XSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		cellStyle.setWrapText(true);

		// 单元格字体
		XSSFFont font = wb.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 12);
		cellStyle.setFont(font);

		// 循环插入数据
		for (int i = 0; i < result.size(); i++) {
			XSSFRow row = sheet.createRow(i + 1);
			row.setHeight((short) 400); // 设置高度
			XSSFCell cell = null;
			
			List<String> datas = result.get(i);
			for (int j = 0; j < datas.size(); j ++) {
				cell = row.createCell(j);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(new XSSFRichTextString(datas.get(j)));
			}
		}
		//冻结第一行
		sheet.createFreezePane( 0, 1, 0, 1 ); 
		// 设置自动宽度,本地windows环境可用,linux测试环境会报错,目前找不到原因,暂时使用固定宽度。
		/*for (int i = 0; i < sheetWidth.length; i++) {
			sheet.autoSizeColumn(i);
			if(sheet.getColumnWidth(i)>20000){
				sheet.setColumnWidth(i, 20000);
			}
		}*/
		
	}
	
	
	/**
	 * 输出excel到浏览器
	 * @param response
	 * @param fileName
	 * @param workBook
	 */
	public static void writeExcelFile(HttpServletResponse response, String fileName, XSSFWorkbook workBook) {
		ServletOutputStream fos = null;
		try {
			response.setContentType("application/x-download");
			fileName += DateUtil.date2Str(new Date(), DateUtil.SHORT_DATE_FORMAT);
			//文件名编码
			fileName = StringUtil.encode(fileName);
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xlsx");
			fos = response.getOutputStream();
			workBook.write(fos);
		} catch (IOException e) {
			logger.warn("Excel导出异常：message={}", e.getMessage(), e);
		} finally {
			IOUtils.closeQuietly(fos);
			try {
				workBook.close();
			} catch (IOException e) {
				logger.warn("Excel导出关闭工作书异常： message={}", e.getMessage(), e);
			}
		}
	}
	
	
	/**
	 * 导出Excel文件
	 * @param response
	 * @param tableName 表名
	 * @param thead	表头
	 * @param columnWidth 列宽
	 * @param dataList	数据列表
	 */
	public static void exportExcel(HttpServletResponse response, String tableName, 
			String[] thead, int[] columnWidth, List<List<String>> dataList) {
		// 创建Excel文档对象
		XSSFWorkbook wb = new XSSFWorkbook(); 
		// 创建工作表
		XSSFSheet sheet = wb.createSheet(tableName); 
		//创建表头
		ExcelUtils.createThead(wb, sheet, thead, columnWidth);
		//写入数据
		ExcelUtils.createTable(wb, sheet, dataList, columnWidth);
		//输出到浏览器
		ExcelUtils.writeExcelFile(response, tableName, wb);
	}

}
