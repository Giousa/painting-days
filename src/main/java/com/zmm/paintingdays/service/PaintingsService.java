package com.zmm.paintingdays.service;

import com.zmm.paintingdays.bean.ResultVO;
import org.springframework.data.domain.Pageable;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2019/4/22
 * Email:65489469@qq.com
 */
public interface PaintingsService {

    ResultVO addPaintings(String uId,String title,String content, String tags,boolean jurisdiction,String pics);

    ResultVO deletePaintings(String id);

    ResultVO updatePaintings(String id,String uId,String title,String content, String tags,boolean jurisdiction,String pics);

    ResultVO findPaintingsById(String id);

    ResultVO findAllPaintingsByUid(String uId, Pageable pageable);
}
