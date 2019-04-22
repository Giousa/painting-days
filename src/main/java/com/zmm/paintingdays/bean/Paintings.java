package com.zmm.paintingdays.bean;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * Description: 画作
 * Author:zhangmengmeng
 * Date:2019/4/22
 * Email:65489469@qq.com
 */

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
@Table(name = "p_paintings")
public class Paintings {

    @Id
    private String id;

    private String uId;

    private String pics;

    private String title;

    private String content;

    private String tags;//标签

    private int collections;//收藏数

    private int likes;//点赞数

    @CreatedDate
    private Date createTime;

    @LastModifiedDate
    private Date updateTime;

}
