package com.baizhi.entity;

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
@Table(name = "cmfz_guru")
public class Guru implements Serializable {

    @Id
    private String id;
    private String dharma;
    private String name;
    private String image;
    private String phone;
    private String password;
    private Date createDate;
    private Date lastUpdateDate;
    private String status;
    private String salt;

}