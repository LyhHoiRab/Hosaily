package com.lab.hosaily.core.app.utils;


import java.io.*;
import java.nio.file.Path;
import java.util.HashMap;

import javax.sound.midi.Patch;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
//import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.lab.hosaily.commons.utils.FileNameUtils;
import com.lab.hosaily.commons.utils.UpyunUtils;
import com.rab.babylon.commons.utils.FileUtils;

//生成二维码
public class CreateQRCode {
    public static void main(String[] args) {
        final int width = 300;
        final int height = 300;
        final String format = "png";
        final String content = "http://www.baidu.comaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

        //定义二维码的参数
        HashMap hints = new HashMap();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        hints.put(EncodeHintType.MARGIN, 2);

        //生成二维码
        try {
            File filePath = new File(CreateQRCode.class.getResource("/").getPath() + "tem/");
            if(!filePath.exists()){
                filePath.mkdir();
            }
            //OutputStream stream = new OutputStreamWriter();
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            File file = new File(CreateQRCode.class.getResource("/").getPath() + "tem/" + "new.jpg");
            MatrixToImageWriter.writeToFile(bitMatrix, format, file);


            TwoComposePic.composePicUrl("http://kuliao.b0.upaiyun.com/advisor/head/4d84b9274ab3c86d125d640dd7f3ab37.jpg", CreateQRCode.class.getResource("/").getPath() + "tem/" + "new.jpg",
                    CreateQRCode.class.getResource("/").getPath() + "tem/" + "c6_new.jpg");



            byte[] fileByte = File2byte(CreateQRCode.class.getResource("/").getPath() + "tem/" + "c6_new.jpg");
            //MD5
            String md5 = FileUtils.getMD5(fileByte);
            //文件后缀
            String suffix = FileNameUtils.getSuffix("c6_new.png");
            //上传名称
            String name = md5 + suffix;
            //上传路径
            String uploadPath = UpyunUtils.QR_Code_DIR + "c6_new.png";
            //上传
            boolean result = UpyunUtils.upload(uploadPath, fileByte);
            System.out.println("uploadPathuploadPath: " + uploadPath);
            System.out.println("resultresult: " + UpyunUtils.URL + uploadPath);

            //MatrixToImageWriter.writeToStream(bitMatrix, format, stream);
        } catch (Exception e) {

        }

    }


    public static byte[] File2byte(String filePath)
    {
        byte[] buffer = null;
        try
        {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1)
            {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return buffer;
    }

    public static void byte2File(byte[] buf, String filePath, String fileName)
    {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try
        {
            File dir = new File(filePath);
            if (!dir.exists() && dir.isDirectory())
            {
                dir.mkdirs();
            }
            file = new File(filePath + File.separator + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(buf);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (bos != null)
            {
                try
                {
                    bos.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (fos != null)
            {
                try
                {
                    fos.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

}
