package com.xiaohe.common.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.util.CellRangeAddressList;

import com.xiaohe.common.utils.Base64.Decoder;

public class ExportComboxExcel {

	public ExportComboxExcel() {
		super();
	}

	// 下拉框级联所需
	class InformationSystem {
		private int id;
		private String name;

		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}

	List<InformationSystem> systemList = new ArrayList<InformationSystem>();
	List<String> systemNames = new ArrayList<String>();
	List<String> deviceNames = new ArrayList<String>();
	List<String> deviceTypeNames = new ArrayList<String>();
	/** 普通下拉框集合 */
	Map<Integer, List> normalMap;

	private HSSFWorkbook workbook = null;
	private HSSFCellStyle titleStyle = null;
	private HSSFCellStyle dataStyle = null;
	private HSSFCellStyle dataTimeStyle = null;

	/**
	 * 列头样式 (自定义列宽)
	 * @param workbook
	 * @param sheet
	 */
	public void setTitleCellStyles(HSSFWorkbook workbook, HSSFSheet sheet, int[] columnWidth) {
		titleStyle = workbook.createCellStyle();

		// 设置边框
		titleStyle.setBorderBottom(HSSFCellStyle.BORDER_HAIR);
		titleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		titleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		titleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		// 设置背景色
		titleStyle.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
		titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		// 设置居中
		titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
		titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
		// 设置字体
		HSSFFont font = workbook.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 11); // 设置字体大小
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 粗体显示
		titleStyle.setFont(font);// 选择需要用到的字体格式
		// 设置自动换行
		titleStyle.setWrapText(true);
		// 设置列宽 ,第一个参数代表列id(从0开始),第2个参数代表宽度值
		for (int i = 0; i < columnWidth.length; i++) {
			sheet.setColumnWidth(i, columnWidth[i]);
		}
	}

	/**
	 * 列头样式 (列宽默认设置)
	 * @param workbook
	 * @param sheet
	 */
	public void setTitleCellStyles(HSSFWorkbook workbook, HSSFSheet sheet) {
		titleStyle = workbook.createCellStyle();

		// 设置边框
		titleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		titleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		titleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		titleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		// 设置背景色
		titleStyle.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
		titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		// 设置居中
		titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
		titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
		// 设置字体
		HSSFFont font = workbook.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 11); // 设置字体大小
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 粗体显示
		titleStyle.setFont(font);// 选择需要用到的字体格式
		// 设置自动换行
		titleStyle.setWrapText(true);
		sheet.setDefaultColumnWidth(15);
	}

	/**
	 * 数据样式
	 * @param workbook
	 * @param sheet
	 */
	public void setDataCellStyles(HSSFWorkbook workbook, HSSFSheet sheet) {
		dataStyle = workbook.createCellStyle();

		// 设置边框
		dataStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		dataStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		dataStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		dataStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		// 设置背景色
		dataStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		dataStyle.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
		// 设置居中
		dataStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
		dataStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
		// 设置字体
		/*
		 * HSSFFont font = workbook.createFont(); font.setFontName("宋体");
		 * font.setFontHeightInPoints((short) 11); //设置字体大小
		 * dataStyle.setFont(font);//选择需要用到的字体格式 // 设置为文本格式
		 * dataStyle.setDataFormat(workbook.createDataFormat().getFormat("@"));
		 * //设置自动换行 dataStyle.setWrapText(true);
		 */
	}

	/**
	 * 时间格式数据样式
	 */
	public void setDataTimeCellStyles(HSSFWorkbook workbook) {
		dataTimeStyle = workbook.createCellStyle();

		// 设置边框
		dataTimeStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		dataTimeStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		dataTimeStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		dataTimeStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		// 设置背景色
		dataTimeStyle.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
		dataTimeStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		// 设置居中
		dataTimeStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		dataTimeStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
		// 设置字体
		HSSFFont font = workbook.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 11); // 设置字体大小
		dataTimeStyle.setFont(font);// 选择需要用到的字体格式
		dataTimeStyle.setDataFormat(workbook.createDataFormat().getFormat("yyyy-MM-dd"));
		// 设置自动换行
		dataTimeStyle.setWrapText(true);
	}


	/** 获取所有信息系统数据 **/
	private void getAllEnableInfoSystemList(String[] arr) {
		for (String s : arr) {
			InformationSystem infoSys = new InformationSystem();
			infoSys.setId(Integer.valueOf(s.split("\\.")[0]));
			infoSys.setName(s.split("\\.")[1]);
			systemList.add(infoSys);
		}
	}

	/**
	 * 创建一列数据
	 * @param currentRow
	 * @param textList
	 */
	public void creatRow(HSSFRow currentRow, List<String> textList) {
		if (textList != null && textList.size() > 0) {
			int i = 0;
			for (String cellValue : textList) {
				HSSFCell userNameLableCell = currentRow.createCell(i++);
				userNameLableCell.setCellValue(cellValue);
			}
		}
	}


	// 根据数据值确定单元格位置（比如：28-AB）
	private String getcellColumnFlag(int num) {
		String columFiled = "";
		int chuNum = 0;
		int yuNum = 0;
		if (num >= 1 && num <= 26) {
			columFiled = this.doHandle(num);
		} else {
			chuNum = num / 26;
			yuNum = num % 26;

			columFiled += this.doHandle(chuNum);
			columFiled += this.doHandle(yuNum);
		}
		return columFiled;
	}

	private String doHandle(final int num) {
		String[] charArr = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X",
				"Y", "Z" };
		return charArr[num - 1].toString();
	}

	/**
	 * 创建一列应用列头
	 * @param rowNum 在第几行创建
	 */
	public void creatAppRowHeadByArray(HSSFSheet userinfosheet1, String[] titleArr, int rowNum) {
		HSSFRow row = userinfosheet1.createRow(rowNum);
		row.setHeight((short) 600);// 设置列高
		for (int i = 0; i < titleArr.length; i++) {
			// 0.设备名称
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(titleStyle);
			cell.setCellValue(titleArr[i]);
		}
	}

	/**
	 * 创建一列应用数据
	 */
	public void creatAppRow(HSSFSheet userinfosheet1, int naturalRowIndex, int length) {
		// 在第一行第一个单元格，插入下拉框
		HSSFRow row = userinfosheet1.createRow(naturalRowIndex - 1);
		// 为各列添加数据与设置样式（此方法不通用）
		for (int i = 0; i < length; i++) {
			if (i == 6 || i == 7) {
				HSSFCell cell = row.createCell(i);
				cell.setCellStyle(dataTimeStyle);
				cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString());
			} else {
				HSSFCell cell = row.createCell(i);
				cell.setCellStyle(dataStyle);
			}
		}

		// 普通下拉框的数据关联
		if (normalMap != null && normalMap.size() > 0) {
			Set<Integer> keySet = normalMap.keySet();
			for (Integer key : keySet) {
				DataValidation validation = this.getDataValidationByFormula("info" + key, naturalRowIndex, key); // 从1开始下拉框处于第几列
				userinfosheet1.addValidationData(validation);
			}
		}

		// 级联下拉框的数据关联
		// 得到验证对象
		DataValidation data_validation_list = this.getDataValidationByFormula("sysytemInfo", naturalRowIndex, 4); // 从1开始下拉框处于第几列
		// 级联表达式
		DataValidation data_validation_list2 = this.getDataValidationByFormula("INDIRECT($D" + naturalRowIndex + ")", naturalRowIndex, 5);
		// 工作表添加验证数据
		userinfosheet1.addValidationData(data_validation_list2);
		userinfosheet1.addValidationData(data_validation_list);
	}

	/**
	 * 为列添加数据(可合并单元格)
	 * @param headTitle 第一行的标题头数据
	 * @param num 第一行合并的列数
	 */
	public void creatAppRowByList(List<Map<String, Object>> lists, HSSFSheet sheet1, String headTitle, int num) {
		// 单元格合并  
	    // 四个参数分别是：起始行，起始列，结束行，结束列
        Region region1 = new Region(0, (short)0, 0, (short)num);   
        sheet1.addMergedRegion(region1);
		// 创建第一行数据
		HSSFRow rowHead = sheet1.createRow(0);
		rowHead.setHeight((short) 600);// 设置列高
		HSSFCell cellHead = rowHead.createCell(0);
		cellHead.setCellStyle(titleStyle);
		cellHead.setCellValue(headTitle);
		// 创建其他行数据
		if (lists != null && lists.size() > 0) {
			int y = 2;
			for (Map<String, Object> map : lists) {
				HSSFRow row = sheet1.createRow(y);
				Set<String> keySet = map.keySet();
				int i = 0;
				for (String key : keySet) {
					HSSFCell cell = row.createCell(i);
					cell.setCellStyle(dataStyle);
					if (map.get(key) == null) {
						cell.setCellValue("");
					} else {
						cell.setCellValue(map.get(key).toString());
					}
					i++;
				}
				y++;
			}
		}
	}
	
	/**
	 * 为列添加数据
	 */
	public void creatAppRowByList(List<Map<String, Object>> lists, HSSFSheet sheet1) {
		if (lists != null && lists.size() > 0) {
			int y = 1;
			for (Map<String, Object> map : lists) {
				HSSFRow row = sheet1.createRow(y);
				Set<String> keySet = map.keySet();
				int i = 0;
				for (String key : keySet) {
					HSSFCell cell = row.createCell(i);
					cell.setCellStyle(dataStyle);
					if (map.get(key) == null) {
						cell.setCellValue("");
					} else {
						cell.setCellValue(map.get(key).toString());
					}
					i++;
				}
				y++;
			}
		}
	}

	public void creatAppRowByList(LinkedList<Map<String, Object>> lists, HSSFSheet sheet1) {
		if (lists != null && lists.size() > 0) {
			int y = 1;
			for (Map<String, Object> map : lists) {
				HSSFRow row = sheet1.createRow(y);
				Set<String> keySet = map.keySet();
				int i = 0;
				for (String key : keySet) {
					HSSFCell cell = row.createCell(i);
					cell.setCellStyle(dataStyle);
					if (map.get(key) == null) {
						cell.setCellValue("");
					} else {
						cell.setCellValue(map.get(key).toString());
					}
					i++;
				}
				y++;
			}
		}
	}
	/**
	 * 为列添加数据
	 */
	public void creatAppRowByList2(List<Map<String, Object>> lists, HSSFSheet sheet1) {
		Map<String, Integer> reMap = new HashMap<String, Integer>();
		List<String> reList = new ArrayList<String>();
		if (lists != null && lists.size() > 0) {
			int y = 1;
			for (Map<String, Object> map : lists) {
				HSSFRow row = sheet1.createRow(y);
				Set<String> keySet = map.keySet();
				int i = 0;
				for (String key : keySet) {
					HSSFCell cell = row.createCell(i);
					cell.setCellStyle(dataStyle);
					if (map.get(key) == null) {
						cell.setCellValue("");
					} else {
						cell.setCellValue(map.get(key).toString());
						if (i == 0) {
							String keyStr = map.get(key).toString();
							if (!reList.contains(keyStr)) {
								reList.add(keyStr);
							}
							if (reMap.get(keyStr) == null) {
								reMap.put(keyStr, 1);
							} else {
								reMap.put(keyStr, reMap.get(keyStr) + 1);
							}
						}
					}
					i++;
				}
				y++;
			}
		}
		int count = 0;
		for (int i = 0; i < reList.size(); i++) {
			if (i == 0) {
				sheet1.addMergedRegion(new Region(1, (short) 0, reMap.get(reList.get(i)), (short) 0));
				count += reMap.get(reList.get(i));
			} else {
				sheet1.addMergedRegion(new Region(count + 1, (short) 0, count + reMap.get(reList.get(i)), (short) 0));
				count += reMap.get(reList.get(i));
			}
		}
	}

	/**
	 * 使用已定义的数据源方式设置一个数据验证
	 * @param formulaString
	 * @param naturalRowIndex
	 * @param naturalColumnIndex
	 * @return
	 */
	public DataValidation getDataValidationByFormula(String formulaString, int naturalRowIndex, int naturalColumnIndex) {
		// 加载下拉列表内容
		DVConstraint constraint = DVConstraint.createFormulaListConstraint(formulaString);
		// 设置数据有效性加载在哪个单元格上。
		// 四个参数分别是：起始行、终止行、起始列、终止列
		int firstRow = naturalRowIndex - 1;
		int lastRow = naturalRowIndex - 1;
		int firstCol = naturalColumnIndex - 1;
		int lastCol = naturalColumnIndex - 1;
		CellRangeAddressList regions = new CellRangeAddressList(firstRow, lastRow, firstCol, lastCol);
		// 数据有效性对象
		DataValidation data_validation_list = new HSSFDataValidation(regions, constraint);
		return data_validation_list;
	}


	/** 导出普通数据 */
	public ExportComboxExcel(String outPathStr, List<Map<String, Object>> list, String[] titleArr, int[] columnWidth) {
		try {
			workbook = new HSSFWorkbook();// excel文件对象
			HSSFSheet sheet = workbook.createSheet("sheet1");// 工作表对象
			// 设置列头样式
			this.setTitleCellStyles(workbook, sheet, columnWidth);
			// 设置数据样式
			this.setDataCellStyles(workbook, sheet);
			// 设置日期数据样式
			this.setDataTimeCellStyles(workbook);
			// 创建一行列头数据
			this.creatAppRowHeadByArray(sheet, titleArr, 0);
			// 将数据填入表中
			this.creatAppRowByList(list, sheet);
			// 生成输入文件
			FileOutputStream out = new FileOutputStream(outPathStr);
			workbook.write(out);
			out.close();
			System.out.println("导出成功!");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/** 导出普通数据(带标题头) */
	public ExportComboxExcel(String outPathStr, List<Map<String, Object>> list, String headTitle, int num, String[] titleArr, int[] columnWidth) {
		try {
			workbook = new HSSFWorkbook();// excel文件对象
			HSSFSheet sheet = workbook.createSheet("sheet1");// 工作表对象
			// 设置列头样式
			this.setTitleCellStyles(workbook, sheet, columnWidth);
			// 设置数据样式
			this.setDataCellStyles(workbook, sheet);
			// 设置日期数据样式
			this.setDataTimeCellStyles(workbook);
			// 创建一行列头数据
			this.creatAppRowHeadByArray(sheet, titleArr, 1);
			// 将数据填入表中
			this.creatAppRowByList(list, sheet, headTitle, num);
			// 生成输入文件
			FileOutputStream out = new FileOutputStream(outPathStr);
			workbook.write(out);
			out.close();
			System.out.println("导出成功!");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** 生成多个sheet导出 */
	public ExportComboxExcel(String outPathStr, List<List<Map<String, Object>>> list, String[] headTitleArr, int[] numArr, List<String[]> titleListArr, String[] sheetNameArr, String picBase64Info) {
		try {
			workbook = new HSSFWorkbook();// excel文件对象
			for (int i = 0; i < titleListArr.size(); i++) {
				HSSFSheet sheet = workbook.createSheet(sheetNameArr[i]);// 工作表对象
				// 设置列头样式
				this.setTitleCellStyles(workbook, sheet);
				// 设置数据样式
				this.setDataCellStyles(workbook, sheet);
				// 创建一行列头数据
				this.creatAppRowHeadByArray(sheet, titleListArr.get(i), 1);
				// 将数据填入表中
				this.creatAppRowByList(list.get(i), sheet, headTitleArr[i], numArr[i]);
			}
			
			if(picBase64Info != null){
				// 走势图生成
				picBase64Info = picBase64Info.replaceAll("\\$2B", "\\+");  
				picBase64Info = picBase64Info.replaceAll("\\$3D", "\\=");  
				picBase64Info = picBase64Info.replaceAll("\\$2F", "/");  
				Decoder decoder = Base64.getDecoder();  
				String[] arr = picBase64Info.split("base64,");  
				byte[] buffer = decoder.decode(arr[1]);  
				File imageFile = new File(outPathStr.substring(0, outPathStr.lastIndexOf("\\")) + "\\" + UUID.randomUUID() + ".png");
				OutputStream os = new FileOutputStream(imageFile);  
				os.write(buffer);  
				os.close(); 
				ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
				// 加载图片
				BufferedImage bufferImg = ImageIO.read(imageFile);
				ImageIO.write(bufferImg, "png", byteArrayOut);
				HSSFSheet sheetPic = workbook.createSheet("走势图");// 工作表对象
				sheetPic.setDisplayGridlines(false);
				HSSFPatriarch patriarch = sheetPic.createDrawingPatriarch();
				HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 0, (short) 2, 2, (short) 20, 23);
				// 插入图片
				patriarch.createPicture(anchor, workbook.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
				// 删除图片
				imageFile.delete();
				// 单元格合并  
			    // 四个参数分别是：起始行，起始列，结束行，结束列
		        Region region1 = new Region(24, (short)8, 24, (short)12);   
		        sheetPic.addMergedRegion(region1);
				// 创建第一行数据
				HSSFRow rowHead = sheetPic.createRow(24);
				rowHead.setHeight((short) 600);// 设置列高
				HSSFCell cellHead = rowHead.createCell(8);
				cellHead.setCellValue(headTitleArr[0]);
			}
			
			// 生成输入文件
			FileOutputStream out = new FileOutputStream(outPathStr);
			workbook.write(out);
			out.close();
			System.out.println("导出成功!");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** 导出有下拉框的数据 */
	public ExportComboxExcel(String outPathStr, List<Map<String, Object>> list, String[] titleArr, int[] columnWidth, String empty) {
		try {
			workbook = new HSSFWorkbook();// excel文件对象
			HSSFSheet sheet = workbook.createSheet("sheet1");// 工作表对象
			// 设置列头样式
			this.setTitleCellStyles(workbook, sheet, columnWidth);
			// 设置数据样式
			this.setDataCellStyles(workbook, sheet);
			// 设置日期数据样式
			this.setDataTimeCellStyles(workbook);
			// 创建一行列头数据
			this.creatAppRowHeadByArray(sheet, titleArr, 0);
			// 将数据填入表中
			this.creatAppRowByList2(list, sheet);
			// 生成输入文件
			FileOutputStream out = new FileOutputStream(outPathStr);
			workbook.write(out);
			out.close();
			System.out.println("导出成功!");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}