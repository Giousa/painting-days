package com.zmm.paintingdays.repository;

import com.zmm.paintingdays.bean.Paintings;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2019/4/22
 * Email:65489469@qq.com
 */
public interface PaintingsRepository extends JpaRepository<Paintings,String> {


    List<Paintings> findAllByUId(String uId, Pageable pageable);

}
