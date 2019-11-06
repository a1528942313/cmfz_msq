package com.baizhi.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 类描述信息 (轮播图类)
 *
 * @author : maoshuqi
 * @date : 2019-10-27 15:32
 * @version: V_1.0.0
 */
@Table(name = "cmfz_chart")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Chart implements Serializable {

    @Id
    private String id;
    private String name;
    private String image;
    private String title;
    @Column(name = "create_date")
    private Date createDate;
    @Column(name = "last_update_date")
    private Date lastUpdateDate;
    private String status;
}