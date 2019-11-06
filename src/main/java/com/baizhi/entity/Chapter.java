package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.*;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Table(name = "cmfz_chapter")
@ExcelTarget(value = "Chapter")
public class Chapter implements Serializable {

    @Id
    @Excel(name = "编号")
    private String id;
    @Excel(name = "标题")
    private String title;
    @Excel(name = "章节大小")
    private Double size;
    @Excel(name = "音频时长",type = 2 ,width = 40 , height = 20)
    private String duration;
    @Excel(name = "音频")
    private String url;
    @Excel(name = "创建时间",databaseFormat = "yyyyMMddHHmmss", format = "yyyy-MM-dd")
    private Date createDate;
    @Excel(name = "最后一次修改时间",databaseFormat = "yyyyMMddHHmmss", format = "yyyy-MM-dd")
    private Date lastUpdateDate;
    @Excel(name = "状态",replace = { "激活_yes", "冻结_no" })
    private String status;
    @Excel(name = "所属专辑ID")
    private String albumId;
}