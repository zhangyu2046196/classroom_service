package com.longbei.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhangy
 * @version 1.0
 * @description 用户
 * @date 2020/3/10 21:39
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = 7146618021670403495L;
    /**
     * id
     */
    private Integer id;
    /**
     * 用户名
     */
    private String name;
    /**
     * 住址
     */
    private String address;

}
