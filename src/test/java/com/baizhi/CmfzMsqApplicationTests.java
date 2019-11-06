package com.baizhi;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.baizhi.dao.ArticleDAO;
import com.baizhi.entity.Article;
import com.baizhi.util.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CmfzMsqApplication.class)
public class CmfzMsqApplicationTests {

    @Autowired
    private ArticleDAO articleDAO;

    @Test
    public void contextLoads() throws Exception {
        //创建excel工作簿
        Workbook workbook = new HSSFWorkbook();
        Font font = workbook.createFont();
        font.setColor(Font.COLOR_RED);
        font.setBold(true);
        font.setFontName("华文行楷");
        //创建样式对象
        CellStyle cellStyle = workbook.createCellStyle();
        CellStyle cellStyle1 = workbook.createCellStyle();
        cellStyle1.setFont(font);
        //创建日期格式
        DataFormat dataFormat = workbook.createDataFormat();
        cellStyle.setDataFormat(dataFormat.getFormat("yyyy 年 MM 月 dd 日"));
        //创建表
        Sheet sheet = workbook.createSheet("文章信息表");
        sheet.setColumnWidth(4,20*256);
        //创建行
        Row row = sheet.createRow(0);
        String[] title = {"编号","标题","内容","作者","创建日期"};
        for (int i = 0; i < title.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(cellStyle1);
        }
        List<Article> articles = articleDAO.selectAll();
        for (int i = 0; i < articles.size(); i++) {
            Row row1 = sheet.createRow(i+1);
            row1.createCell(0).setCellValue(articles.get(i).getId());
            row1.createCell(1).setCellValue(articles.get(i).getTitle());
            row1.createCell(2).setCellValue(articles.get(i).getContent());
            row1.createCell(3).setCellValue(articles.get(i).getAuthor());
            Cell cell = row1.createCell(4);
            cell.setCellValue(articles.get(i).getCreateDate());
            cell.setCellStyle(cellStyle);
        }
        workbook.write(new FileOutputStream("D:/文章.xls"));

    }
    @Test
    public void loads() throws IOException {
        //获取excel工作簿
        Workbook workbook = new HSSFWorkbook(new FileInputStream("D:/文章.xls"));
        //获取excel工作表
        Sheet sheet = workbook.getSheet("文章信息表");
        int lastRowNum = sheet.getLastRowNum();
        System.out.println(lastRowNum);
        for (int i = 1; i < sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            String str= row.getCell(0).getStringCellValue();
            System.out.println(str);
            System.out.println(i+1+" "+row.getCell(0).getStringCellValue()+" "+row.getCell(1).getStringCellValue()+" "+row.getCell(2).getStringCellValue()+" "+row.getCell(3).getStringCellValue()+" "+row.getCell(4).getDateCellValue());
        }


    }

    @Test
    public void aaa() {

        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4FjrNdd1enCruvsgSFRf", "EJC3zTJv9MaGAzj0w58h51WFicNKWi");
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", "19907224232");
        request.putQueryParameter("SignName", "衣心依意");
        request.putQueryParameter("TemplateCode", "SMS_176936671");
        request.putQueryParameter("TemplateParam", "{\"code\":\"999999999\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void bbb() {
        String host = "http://dingxin.market.alicloudapi.com";
        String path = "/dx/sendSms";
        String method = "POST";
        String appcode = "5709292c5c734a2da0334ecc5bad83da";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("mobile", "13451132995");
        querys.put("param", "code:雪猪子猜猜我是谁");
        querys.put("tpl_id", "TP1711063");
        Map<String, String> bodys = new HashMap<String, String>();


        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
