package com.lab.hosaily.core.app.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class WriteExcel {
    private static final String EXCEL_XLS = "xls";
    private static final String EXCEL_XLSX = "xlsx";

    public static void main(String[] args) {

//        Map<String, String> dataMap = new HashMap<String, String>();
//        dataMap.put("BankName", "BankName");
//        dataMap.put("Addr", "Addr");
//        dataMap.put("Phone", "Phone");
//        List<Map> list = new ArrayList<Map>();
//        list.add(dataMap);
//        Map<String, String> dataMap2 = new HashMap<String, String>();
//        dataMap2.put("BankName", "BankName2");
//        dataMap2.put("Addr", "Addr2");
//        dataMap2.put("Phone", "Phone2");
//        list.add(dataMap2);
//        writeExcel(list, 3, "g://asd.xls");
        try {
            FileUtils.copyFile(new File("G:\\crm\\exportModel.xls"), new File("G:\\crm\\exportModel222.xls"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void writeExcel(List<Map> dataList, int cloumnCount, String finalXlsxPath) {
        OutputStream out = null;
        try {
            // 获取总列数
            int columnNumCount = cloumnCount;
            // 读取Excel文档
            File finalXlsxFile = new File(finalXlsxPath);
            Workbook workBook = getWorkbok(finalXlsxFile);
            // sheet 对应一个工作页
            Sheet sheet = workBook.getSheetAt(0);
            /**
             * 删除原有数据，除了属性列
             */
            int rowNumber = sheet.getLastRowNum();    // 第一行从0开始算
            System.out.println("原始数据总行数，除属性列：" + rowNumber);
            for (int i = 1; i <= rowNumber; i++) {
                Row row = sheet.getRow(i);
                sheet.removeRow(row);
            }
            // 创建文件输出流，输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效
            out = new FileOutputStream(finalXlsxPath);
            workBook.write(out);
            /**
             * 往Excel中写新数据
             */

            for (int j = 0; j < dataList.size(); j++) {
                // 创建一行：从第二行开始，跳过属性列
                Row row = sheet.createRow(j);
                String time = "";
                String follower = "";
                String address = "";
                String name = "";
                String sex = "";
                String phone = "";
                String weChat = "";
                String link = "";
                String chatRecord = "";
                String situation = "";
                String channel = "";
                // 得到要插入的每一条记录
                Map dataMap = dataList.get(j);
                if(null != dataMap.get("time")){
                    time = dataMap.get("time").toString();
                }
                if(null != dataMap.get("follower")){
                    follower = dataMap.get("follower").toString();
                }
                if(null != dataMap.get("address")){
                    address = dataMap.get("address").toString();
                }
                if(null != dataMap.get("name")){
                    name = dataMap.get("name").toString();
                }
                if(null != dataMap.get("sex")){
                    sex = dataMap.get("sex").toString();
                }
                if(null != dataMap.get("phone")){
                    phone = dataMap.get("phone").toString();
                }
                if(null != dataMap.get("weChat")){
                    weChat = dataMap.get("weChat").toString();
                }
                if(null != dataMap.get("link")){
                    link = dataMap.get("link").toString();
                }
                if(null != dataMap.get("chatRecord")){
                    chatRecord = dataMap.get("chatRecord").toString();
                }
                if(null != dataMap.get("situation")){
                    situation = dataMap.get("situation").toString();
                }
                if(null != dataMap.get("channel")){
                    channel = dataMap.get("channel").toString();
                }
                for (int k = 0; k <= columnNumCount; k++) {
                    // 在一行内循环
                    Cell first = row.createCell(0);
                    first.setCellValue(time);

                    Cell second = row.createCell(1);
                    second.setCellValue(follower);

                    Cell third = row.createCell(2);
                    third.setCellValue(address);

                    Cell four = row.createCell(3);
                    four.setCellValue(name);

                    Cell five = row.createCell(4);
                    five.setCellValue(sex);

                    Cell six = row.createCell(5);
                    six.setCellValue(phone);

                    Cell seven = row.createCell(6);
                    seven.setCellValue(weChat);

                    Cell eight = row.createCell(7);
                    eight.setCellValue(link);

                    Cell nigh = row.createCell(8);
                    nigh.setCellValue(chatRecord);

                    Cell ten = row.createCell(9);
                    ten.setCellValue(situation);

                    Cell eleven = row.createCell(10);
                    eleven.setCellValue(channel);
                }
            }
            // 创建文件输出流，准备输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效
            out = new FileOutputStream(finalXlsxPath);
            workBook.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("数据导出成功");
    }


    public static Workbook getWorkbok(File file) throws IOException {
        Workbook wb = null;
        FileInputStream in = new FileInputStream(file);
        if (file.getName().endsWith(EXCEL_XLS)) {     //Excel&nbsp;2003
            wb = new HSSFWorkbook(in);
        } else if (file.getName().endsWith(EXCEL_XLSX)) {    // Excel 2007/2010
            wb = new XSSFWorkbook(in);
        }
        return wb;
    }
}
