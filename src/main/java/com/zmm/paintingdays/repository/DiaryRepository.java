package com.zmm.paintingdays.repository;

import com.zmm.paintingdays.bean.Diary;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2019/4/22
 * Email:65489469@qq.com
 */
public interface DiaryRepository extends JpaRepository<Diary,String> {


    List<Diary> findAllByUId(String uId, Pageable pageable);

}
