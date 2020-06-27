package com.library.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author 0045M
 * @since 2020-05-23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User extends Model<User> {

private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private String studentNumber;

    private String password;




    private String headPortrait;

    private Integer sex;

    private String phone;

    /**
     * 已借图书数量
     */
    private Integer brrowedCount;

    /**
     * 生效时间
     */
    private LocalDateTime effDate;

    /**
     * 失效时间
     */
    private LocalDateTime expDete;

    private Integer status;

    /**
     * 用户详情
     */
    private String details;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }



}
