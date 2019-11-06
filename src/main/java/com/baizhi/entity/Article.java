package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Table(name = "cmfz_article")
@ExcelTarget(value = "Album")
public class Article implements Serializable {

    @Id
    @Excel(name = "编号")
    private String id;
    @Excel(name = "标题")
    private String title;
    @Excel(name = "内容")
    private String content;
    @Excel(name = "作者")
    private String author;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发布日期",databaseFormat = "yyyyMMdd", format = "yyyy-MM-dd")
    private Date publishDate;
    @Excel(name = "创建时间",databaseFormat = "yyyyMMddHHmmss", format = "yyyy-MM-dd")
    private Date createDate;
    @Excel(name = "最后一次修改时间",databaseFormat = "yyyyMMddHHmmss", format = "yyyy-MM-dd")
    private Date lastUpdateDate;
    @Excel(name = "状态",replace = { "激活_yes", "冻结_no" })
    private String status;
    @Excel(name = "上师ID")
    private String guruId;
    @Excel(name = "上师头像",type = 2 ,width = 40 , height = 20,imageType = 1)
    private String guruImage;
}