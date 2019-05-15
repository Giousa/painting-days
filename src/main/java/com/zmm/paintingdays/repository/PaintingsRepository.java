package com.zmm.paintingdays.repository;

import com.zmm.paintingdays.bean.Paintings;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2019/4/22
 * Email:65489469@qq.com
 */
public interface PaintingsRepository extends JpaRepository<Paintings,String> {

//    @Query(value = "SELECT * FROM painting_days.p_paintings where u_id=?", nativeQuery = true)
//    List<Paintings> findAllByUId(String uId, Pageable pageable);
//
//    @Query(value = "SELECT * FROM painting_days.p_paintings where to_days(create_time)=to_days(now()) and u_id=?", nativeQuery = true)
//    List<Paintings> findTodayPaintingsByUid(String userId,Pageable pageable);
//
//    @Query(value = "SELECT * FROM painting_days.p_paintings where to_days(create_time)=to_days(?) and u_id=? order by create_time desc", nativeQuery = true)
//    List<Paintings> findPaintingsByCreateTime(String createTime,String userId);

    @Query(value = "SELECT * FROM jhaku.p_paintings where u_id=?", nativeQuery = true)
    List<Paintings> findAllByUId(String uId, Pageable pageable);

    @Query(value = "SELECT * FROM jhaku.p_paintings where to_days(create_time)=to_days(now()) and u_id=?", nativeQuery = true)
    List<Paintings> findTodayPaintingsByUid(String userId,Pageable pageable);

    @Query(value = "SELECT * FROM jhaku.p_paintings where to_days(create_time)=to_days(?) and u_id=? order by create_time desc", nativeQuery = true)
    List<Paintings> findPaintingsByCreateTime(String createTime,String userId);
}
