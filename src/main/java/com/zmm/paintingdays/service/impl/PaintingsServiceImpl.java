package com.zmm.paintingdays.service.impl;

import com.zmm.paintingdays.bean.Paintings;
import com.zmm.paintingdays.bean.ResultVO;
import com.zmm.paintingdays.enums.ResultEnum;
import com.zmm.paintingdays.repository.PaintingsRepository;
import com.zmm.paintingdays.service.PaintingsService;
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
 * Date:2019/4/22
 * Email:65489469@qq.com
 */
@Service
public class PaintingsServiceImpl implements PaintingsService {

    @Autowired
    private PaintingsRepository paintingsRepository;

    @Override
    public ResultVO addPaintings(String uId,String title, String content, String tags,boolean jurisdiction, String pics) {
        if(StringUtils.isEmpty(uId)|| StringUtils.isEmpty(pics)){
            return ResultVO.error(ResultEnum.PARAM_ERROR);
        }

        Paintings paintings = new Paintings();
        paintings.setId(KeyUtil.getKeyId());
        paintings.setUId(uId);
        paintings.setTitle(title);
        paintings.setContent(content);
        paintings.setTags(tags);
        paintings.setPics(pics);
        paintings.setJurisdiction(jurisdiction);

        return ResultVO.ok(paintingsRepository.save(paintings));
    }

//    @Override
//    public ResultVO addMultyPaintings(String uId, String title, String content, String tags, boolean jurisdiction, String pics) {
//        if(StringUtils.isEmpty(uId)|| StringUtils.isEmpty(pics)){
//            return ResultVO.error(ResultEnum.PARAM_ERROR);
//        }
//
//        Paintings paintings = new Paintings();
//        paintings.setId(KeyUtil.getKeyId());
//        paintings.setUId(uId);
//        paintings.setTitle(title);
//        paintings.setContent(content);
//        paintings.setTags(tags);
//        paintings.setPics(pics);
//        paintings.setJurisdiction(jurisdiction);
//
//        return ResultVO.ok(paintingsRepository.save(paintings));
//    }

    @Override
    public ResultVO deletePaintings(String id) {

        if(StringUtils.isEmpty(id)){
            return ResultVO.error(ResultEnum.PARAM_ERROR);
        }

        paintingsRepository.deleteById(id);

        return ResultVO.ok("删除成功");
    }

    @Override
    public ResultVO updatePaintings(String id, String uId, String title, String content, String tags,boolean jurisdiction, String pics) {

        Optional<Paintings> byId = paintingsRepository.findById(id);
        if(byId.isPresent()){
            Paintings paintings = byId.get();
            paintings.setUId(uId);
            paintings.setTitle(title);
            paintings.setContent(content);
            paintings.setTags(tags);
            paintings.setPics(pics);
            paintings.setJurisdiction(jurisdiction);
            return ResultVO.ok(paintingsRepository.save(paintings));
        }

        return ResultVO.error(ResultEnum.PAINTINGS_NOT_EXIST);
    }

//    @Override
//    public ResultVO updateMultyPaintings(String id, String uId, String title, String content, String tags, boolean jurisdiction, String pics) {
//        Optional<Paintings> byId = paintingsRepository.findById(id);
//        if(byId.isPresent()){
//            Paintings paintings = byId.get();
//            paintings.setUId(uId);
//            paintings.setTitle(title);
//            paintings.setContent(content);
//            paintings.setTags(tags);
//            paintings.setPics(pics);
//            paintings.setJurisdiction(jurisdiction);
//            return ResultVO.ok(paintingsRepository.save(paintings));
//        }
//
//        return ResultVO.error(ResultEnum.PAINTINGS_NOT_EXIST);
//    }


    @Override
    public ResultVO findPaintingsById(String id) {

        if(StringUtils.isEmpty(id)){
            return ResultVO.error(ResultEnum.PARAM_ERROR);
        }

        Optional<Paintings> optionalPaintings = paintingsRepository.findById(id);

        if(optionalPaintings.isPresent()){
            return ResultVO.ok(optionalPaintings.get());
        }

        return ResultVO.error(ResultEnum.PAINTINGS_NOT_EXIST);
    }

    @Override
    public ResultVO findAllPaintingsByUid(String uId, Pageable pageable) {

        if(StringUtils.isEmpty(uId)){
            return ResultVO.error(ResultEnum.PARAM_ERROR);
        }

        List<Paintings> paintingsList = paintingsRepository.findAllByUId(uId,pageable);

        return ResultVO.ok(paintingsList);
    }
}
