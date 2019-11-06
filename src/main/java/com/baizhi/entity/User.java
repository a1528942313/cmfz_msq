package com.baizhi.entity;

import lombok.*;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Table(name = "cmfz_user")
public class User implements Serializable {

    @Id
    private String id;
    private String dharma;
    private String name;
    private String image;
    private String sex;
    private String province;
    private String city;
    private String sign;
    private String phone;
    private String password;
    private Date createDate;
    private Date lastUpdateDate;
    private String status;
    private String salt;
    private String guruId;
    private String guruImage;
    @Transient
    private Integer number;

}