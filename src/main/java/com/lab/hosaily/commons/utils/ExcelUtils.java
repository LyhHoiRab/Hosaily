package com.lab.hosaily.commons.utils;

import com.lab.hosaily.core.client.consts.AppointmentState;
import com.lab.hosaily.core.client.entity.Appointment;
import com.rab.babylon.commons.utils.NameUtils;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

@NoArgsConstructor
public class ExcelUtils{

    public void read(){
        XSSFWorkbook book = new XSSFWorkbook();
    }

    public static <T> XSSFWorkbook write(Map<String, String> map, List<T> contents) throws Exception{
        if(map == null || map.isEmpty()){
            throw new IllegalArgumentException("Excel标题不能为空");
        }

        //创建excel
        XSSFWorkbook book = new XSSFWorkbook();
        //创建工作簿
        XSSFSheet sheet = book.createSheet();
        //标题
        List<String> titles = new ArrayList<String>();
        Iterator<String> iterator = map.keySet().iterator();
        while(iterator.hasNext()){
            titles.add(iterator.next());
        }

        //创建标题
        XSSFRow titleRow = sheet.createRow(0);
        for(int i = 0; i < titles.size(); i++){
            XSSFCell cell = titleRow.createCell(i);
            cell.setCellValue(titles.get(i));
        }

        //填充正文
        for(int i = 1; i <= contents.size(); i++){
            //创建行
            XSSFRow row = sheet.createRow(i);
            //行内容对象
            T entry = contents.get(i - 1);
            Class clazz = entry.getClass();

            for(int j = 0; j < titles.size(); j++){
                //反射GET方法
                Method getter = clazz.getMethod("get" + NameUtils.upperCaseToFirst(map.get(titles.get(j))));
                getter.setAccessible(true);
                Object value = getter.invoke(entry, (Object[]) null);
                //赋值
                XSSFCell cell = row.createCell(j);
                setValueAsString(value, cell);
            }
        }

        return book;
    }

    public static void setValueAsString(Object value, XSSFCell cell) throws Exception{
        if(value == null){
            cell.setCellValue("");
            cell.setCellType(XSSFCell.CELL_TYPE_BLANK);
        }else if(value instanceof Date){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            cell.setCellValue(format.format((Date) value));
            cell.setCellType(XSSFCell.CELL_TYPE_STRING);
        }else if(value instanceof Number){
            cell.setCellValue(value.toString());
            cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
        }else if(value instanceof Enum){
            Method method = value.getClass().getMethod("getDescription");
            method.setAccessible(true);

            cell.setCellValue(method.invoke(value, (Object[]) null).toString());
            cell.setCellType(XSSFCell.CELL_TYPE_STRING);
        }else{
            cell.setCellValue(value.toString());
            cell.setCellType(XSSFCell.CELL_TYPE_STRING);
        }
    }

    public static void main(String[] args) throws Exception{
        List<Appointment> appointments = new ArrayList<Appointment>();
        for(int i = 0; i < 10; i++){
            Appointment appointment = new Appointment();
            appointment.setId(String.valueOf(i));
            appointment.setCreateTime(new Date());
            appointment.setState(AppointmentState.REGISTER);
            appointments.add(appointment);
        }

        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("ID", "id");
        map.put("创建时间", "createTime");
        map.put("状态", "state");

        XSSFWorkbook book = write(map, appointments);

        FileOutputStream output = new FileOutputStream("C:\\Users\\xiuxiu\\Desktop\\excel\\test.xlsx");
        book.write(output);
        output.close();
    }
}
