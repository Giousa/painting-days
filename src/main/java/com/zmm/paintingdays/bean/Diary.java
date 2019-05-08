package com.zmm.paintingdays.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/11/1
 * Email:65489469@qq.com
 */

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
@Table(name = "p_diary")
public class Diary {

    @Id
    @NotBlank(message = "参数id不能为空")
    private String id;

    private String uId;

    private String title;

    private String content;

    @JsonIgnore
    private int active;

    @JsonFormat( pattern="yyyy-MM-dd")
    private Date createTime;

    @JsonIgnore
    @LastModifiedDate
    private Date updateTime;
}
