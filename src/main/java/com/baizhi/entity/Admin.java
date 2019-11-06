package com.baizhi.entity;

import lombok.*;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 类描述信息 (管理员类)
 *
 * @author : maoshuqi
 * @date : 2019-10-27 15:32
 * @version: V_1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Table(name = "cmfz_admin")
public class Admin implements Serializable {

    @Id
    private String id;
    private String name;
    private String password;
    private String nickname;
    private String salt;

}
