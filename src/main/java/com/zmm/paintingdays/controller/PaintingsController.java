package com.zmm.paintingdays.controller;

import com.zmm.paintingdays.bean.ResultVO;
import com.zmm.paintingdays.enums.ResultEnum;
import com.zmm.paintingdays.service.PaintingsService;
import com.zmm.paintingdays.utils.UploadOSSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2019/4/22
 * Email:65489469@qq.com
 */
@RestController
@RequestMapping("/paintings")
@CrossOrigin
public class PaintingsController {

    @Autowired
    private PaintingsService paintingsService;


    @PostMapping(value = "/addPaintings")
    public ResultVO addPaintings(@RequestParam("uId")String uId,
                                 @RequestParam("username")String username,
                                 @RequestParam(value = "title",required = false)String title,
                                 @RequestParam(value = "content",required = false)String content,
                                 @RequestParam(value = "tags",required = false)String tags,
                                 @RequestParam(value = "jurisdiction",required = false)boolean jurisdiction,
                                 @RequestParam(value = "uploadFile",required = false) MultipartFile file){

        if(StringUtils.isEmpty(uId) || StringUtils.isEmpty(username) || file == null){
            return ResultVO.error(ResultEnum.PARAM_ERROR);
        }

        try {

            String pic = UploadOSSUtils.uploadSinglePic(file,username);

            return paintingsService.addPaintings(uId,title,content,tags,jurisdiction,pic);

        } catch (Exception e) {
            e.printStackTrace();
            return ResultVO.error(ResultEnum.PIC_UPLOAD_FAILURE);

        }

    }


    @PostMapping(value = "/addMultyPaintings")
    public ResultVO addMultyPaintings(@RequestParam("uId")String uId,
                                 @RequestParam("username")String username,
                                 @RequestParam(value = "title",required = false)String title,
                                 @RequestParam(value = "content",required = false)String content,
                                 @RequestParam(value = "tags",required = false)String tags,
                                 @RequestParam(value = "jurisdiction",required = false)boolean jurisdiction,
                                 @RequestParam(value = "uploadFiles",required=false) MultipartFile[] file){

        if(StringUtils.isEmpty(username) || file == null){
            return ResultVO.error(ResultEnum.PARAM_ERROR);
        }

        try {

            StringBuffer sb = new StringBuffer();

            for (MultipartFile mf : file) {
                if(!mf.isEmpty()){
                    String pic = UploadOSSUtils.uploadSinglePic(mf,username);
                    sb.append(pic+",");
                }
            }

            String pics = sb.toString();
            if(!StringUtils.isEmpty(pics)){
                pics = pics.substring(0,pics.length()-1);
                return paintingsService.addPaintings(uId,title,content,tags,jurisdiction,pics);
            }else{
                return ResultVO.error(ResultEnum.PIC_UPLOAD_FAILURE);
            }

        }catch (Exception e){
            return ResultVO.error(ResultEnum.PIC_UPLOAD_FAILURE);
        }


    }


    @PostMapping(value = "/updatePaintings")
    public ResultVO updatePaintings(@RequestParam("id")String id,
                                    @RequestParam("uId")String uId,
                                    @RequestParam("username")String username,
                                    @RequestParam(value = "title",required = false)String title,
                                    @RequestParam(value = "content",required = false)String content,
                                    @RequestParam(value = "tags",required = false)String tags,
                                    @RequestParam(value = "pic",required = false)String pic,
                                    @RequestParam(value = "jurisdiction",required = false)boolean jurisdiction,
                                    @RequestParam(value = "uploadFile",required = false) MultipartFile file) {

        if (StringUtils.isEmpty(id)|| StringUtils.isEmpty(uId) || StringUtils.isEmpty(username)) {
            return ResultVO.error(ResultEnum.PARAM_ERROR);
        }

        if(file != null){

            try {

                String picPath = UploadOSSUtils.uploadSinglePic(file,username);

                return paintingsService.updatePaintings(id,uId,title,content,tags,jurisdiction,picPath);

            } catch (Exception e) {
                e.printStackTrace();
                return ResultVO.error(ResultEnum.PIC_UPLOAD_FAILURE);

            }

        }else {

            //当没有新上传图片时，原来的图片地址需要传入
            if(StringUtils.isEmpty(pic)){
                return ResultVO.error(ResultEnum.PARAM_ERROR);
            }

            return paintingsService.updatePaintings(id,uId, title, content, tags,jurisdiction, pic);

        }


    }

    @PostMapping(value = "/updateMultyPaintings")
    public ResultVO updateMultyPaintings(@RequestParam("id")String id,
                                    @RequestParam("uId")String uId,
                                    @RequestParam("username")String username,
                                    @RequestParam(value = "title",required = false)String title,
                                    @RequestParam(value = "content",required = false)String content,
                                    @RequestParam(value = "tags",required = false)String tags,
                                    @RequestParam(value = "pics",required = false)String pics,
                                    @RequestParam(value = "jurisdiction",required = false)boolean jurisdiction,
                                    @RequestParam(value = "uploadFiles",required=false) MultipartFile[] file) {

        if (StringUtils.isEmpty(id)|| StringUtils.isEmpty(uId) || StringUtils.isEmpty(username)) {
            return ResultVO.error(ResultEnum.PARAM_ERROR);
        }

        if(file != null){

            try {

                StringBuffer sb = new StringBuffer();

                for (MultipartFile mf : file) {
                    if (!mf.isEmpty()) {
                        String pic = UploadOSSUtils.uploadSinglePic(mf, username);
                        sb.append(pic + ",");
                    }
                }

                String pics2 = sb.toString();
                if (!StringUtils.isEmpty(pics2)) {
                    pics2 = pics2.substring(0, pics2.length() - 1);
                    return paintingsService.updatePaintings(id,uId, title, content, tags, jurisdiction,pics2);
                } else {
                    return ResultVO.error(ResultEnum.PIC_UPLOAD_FAILURE);
                }

            } catch (Exception e) {
                e.printStackTrace();
                return ResultVO.error(ResultEnum.PIC_UPLOAD_FAILURE);
            }


        }else {

            //当没有新上传图片时，原来的图片地址需要传入
            if(StringUtils.isEmpty(pics)){
                return ResultVO.error(ResultEnum.PARAM_ERROR);
            }

            return paintingsService.updatePaintings(id,uId, title, content, tags,jurisdiction, pics);

        }


    }

    @GetMapping(value = {"/deletePaintings/{id}","/deletePaintings"})
    public ResultVO deletePaintings(@PathVariable(value = "id",required = false)String id){

        return paintingsService.deletePaintings(id);
    }

    @GetMapping(value = {"/findPaintingsById/{id}","/findPaintingsById"})
    public ResultVO findPaintingsById(@PathVariable(value = "id",required = false)String id){

        return paintingsService.findPaintingsById(id);
    }

    @GetMapping(value = "/findAllPaintingsByUid")
    public ResultVO findAllPaintingsByUid(@RequestParam(value = "uId",required = false)String uId,
                                          @RequestParam(value = "page",defaultValue = "0") int page,
                                          @RequestParam(value = "size",defaultValue = "10") int size){

        return paintingsService.findAllPaintingsByUid(uId,new PageRequest(page, size, Sort.Direction.DESC,"updateTime"));
    }
}
