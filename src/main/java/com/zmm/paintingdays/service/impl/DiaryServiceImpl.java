package com.zmm.paintingdays.service.impl;

import com.zmm.paintingdays.bean.Diary;
import com.zmm.paintingdays.bean.ResultVO;
import com.zmm.paintingdays.enums.ResultEnum;
import com.zmm.paintingdays.repository.DiaryRepository;
import com.zmm.paintingdays.service.DiaryService;
import com.zmm.paintingdays.utils.DateUtils;
import com.zmm.paintingdays.utils.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2019/5/7
 * Email:65489469@qq.com
 */
@Service
public class DiaryServiceImpl implements DiaryService {

    @Autowired
    private DiaryRepository diaryRepository;

    @Override
    public ResultVO addDiary(String uId,String title, String content, String createTime) {

        if(StringUtils.isEmpty(uId)||StringUtils.isEmpty(title)|| StringUtils.isEmpty(content)){
            return ResultVO.error(ResultEnum.PARAM_ERROR);
        }

        Diary diary = new Diary();
        diary.setId(KeyUtil.getKeyId());
        diary.setUId(uId);
        diary.setTitle(title);
        diary.setContent(content);
        diary.setCreateTime(DateUtils.stringToDate(createTime,"yyyy-MM-dd"));

        return ResultVO.ok(diaryRepository.save(diary));
    }

    @Override
    public ResultVO updateDiary(Diary diary) {
        System.out.println("diary = "+diary.toString());
        return ResultVO.ok(diaryRepository.save(diary));
    }

    @Override
    public ResultVO deleteDiary(String id) {
        if(StringUtils.isEmpty(id)){
            return ResultVO.error(ResultEnum.PARAM_ERROR);
        }

        Optional<Diary> optionalDiary = diaryRepository.findById(id);
        if(!optionalDiary.isPresent()){

            return ResultVO.error(ResultEnum.DIARYS_NOT_EXIST);
        }

        diaryRepository.deleteById(id);

        return ResultVO.ok("删除成功");
    }

    @Override
    public ResultVO findDiaryById(String id) {
        if(StringUtils.isEmpty(id)){
            return ResultVO.error(ResultEnum.PARAM_ERROR);
        }

        Optional<Diary> optionalDiary = diaryRepository.findById(id);

        if(optionalDiary.isPresent()){
            return ResultVO.ok(optionalDiary.get());
        }

        return ResultVO.error(ResultEnum.DIARYS_NOT_EXIST);
    }

    @Override
    public ResultVO findAllDiaryByUid(String uId, Pageable pageable) {
        if(StringUtils.isEmpty(uId)){
            return ResultVO.error(ResultEnum.PARAM_ERROR);
        }

        List<Diary> diaryList = diaryRepository.findAllByUId(uId,pageable);

        return ResultVO.ok(diaryList);
    }
}
