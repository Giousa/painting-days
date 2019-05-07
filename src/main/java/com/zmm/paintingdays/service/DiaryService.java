package com.zmm.paintingdays.service;

import com.zmm.paintingdays.bean.Diary;
import com.zmm.paintingdays.bean.ResultVO;
import org.springframework.data.domain.Pageable;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2019/5/7
 * Email:65489469@qq.com
 */
public interface DiaryService {

    ResultVO addDiary(String uId,String title, String content, String createTime);

    ResultVO updateDiary(Diary diary);

    ResultVO deleteDiary(String id);

    ResultVO findDiaryById(String id);

    ResultVO findAllDiaryByUid(String uId, Pageable pageable);
}
