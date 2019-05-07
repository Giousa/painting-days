package com.zmm.paintingdays.controller;

import com.zmm.paintingdays.bean.Diary;
import com.zmm.paintingdays.bean.ResultVO;
import com.zmm.paintingdays.service.DiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2019/5/7
 * Email:65489469@qq.com
 */
@RestController
@RequestMapping("/diary")
@CrossOrigin
public class DiaryController{

    @Autowired
    private DiaryService diaryService;


    @GetMapping(value = "/addDiary")
    public ResultVO addDiary(@RequestParam("title")String title,
                             @RequestParam("content")String content,
                             @RequestParam("createTime")Date createTime) {

        return diaryService.addDiary(title,content,createTime);
    }

    @GetMapping(value = "/updateDiary")
    public ResultVO updateDiary(@RequestBody Diary diary) {
        return diaryService.updateDiary(diary);
    }

    @GetMapping(value = "/deleteDiary/{id}")
    public ResultVO deleteDiary(@PathVariable("id") String id) {
        return diaryService.deleteDiary(id);
    }

    @GetMapping(value = "/findDiaryById/{id}")
    public ResultVO findDiaryById(@PathVariable("id") String id) {
        return diaryService.findDiaryById(id);
    }

    @GetMapping(value = "/findAllDiaryByUid")
    public ResultVO findAllDiaryByUid(@RequestParam(value = "uId",required = false)String uId,
                                      @RequestParam(value = "page",defaultValue = "0") int page,
                                      @RequestParam(value = "size",defaultValue = "10") int size) {
        return diaryService.findAllDiaryByUid(uId,new PageRequest(page, size, Sort.Direction.DESC,"createTime"));
    }
}
