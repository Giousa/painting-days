package com.zmm.paintingdays.controller;

import com.zmm.paintingdays.bean.ResultVO;
import com.zmm.paintingdays.bean.User;
import com.zmm.paintingdays.enums.ResultEnum;
import com.zmm.paintingdays.service.UserService;
import com.zmm.paintingdays.utils.UploadOSSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/11/1
 * Email:65489469@qq.com
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


//    @CrossOrigin
    @PostMapping(value = "/register")
    public ResultVO register(@RequestParam("username")String username, @RequestParam("password")String password, @RequestParam("verifyCode")String verifyCode, HttpServletRequest request){

        return userService.register(username, password,verifyCode,request);
    }


    @PostMapping(value = "/login")
    public ResultVO login(@RequestParam("username")String username, @RequestParam("password")String password){

        return userService.login(username, password);
    }

    @GetMapping(value = {"/deleteUserById/{id}","/deleteUserById"})
    public ResultVO deleteUserById(@PathVariable(value = "id",required = false)String id){

        return userService.delete(id);
    }


    @PostMapping(value = "/updateUser")
    public ResultVO updateUser(@RequestBody User user, BindingResult bindingResult){

        if(bindingResult.hasErrors()){

            return ResultVO.build(201,bindingResult.getFieldError().getDefaultMessage());
        }

        return userService.update(user);
    }

    @GetMapping(value = {"/findUserById/{id}","/findUserById"})
    public ResultVO findUserById(@PathVariable(value = "id",required = false)String id){


        return userService.findUserById(id);
    }

    @PostMapping(value = "/resetPassword")
    public ResultVO resetPassword(@RequestParam("username")String username,
                                  @RequestParam("newPassword")String newPassword,
                                  @RequestParam("verifyCode")String verifyCode,
                                  HttpServletRequest request){

        return userService.resetPassword(username,newPassword,verifyCode,request);
    }


    @PostMapping(value = "/modifyPassword")
    public ResultVO modifyPassword(@RequestParam("username")String username,
                                   @RequestParam("oldPassword")String oldPassword,
                                   @RequestParam("newPassword")String newPassword){

        return userService.modifyPassword(username,oldPassword,newPassword);
    }


    @PostMapping(value = "/modifyUsername")
    public ResultVO modifyUsername(@RequestParam("id")String id,
                                   @RequestParam("newUsername")String newUsername){

        return userService.modifyUsername(id,newUsername);
    }

//    @PostMapping(value = "/uploadIcon/{id}")
//    public ResultVO uploadIcon(@PathVariable String id, @RequestParam(value="uploadFile",required=false) MultipartFile file){
//
//        try {
//            String path = UploadOSSUtils.uploadSinglePic(file,".jpg","icons/");
//
//            return userService.uploadIcon(id,path);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResultVO.error(ResultEnum.PIC_UPLOAD_FAILURE);
//
//        }
//
//    }

    @PostMapping(value = "/uploadIcon")
    public ResultVO uploadIcon(@RequestParam("id") String id,
                               @RequestParam("username") String username,
                               @RequestParam(value="uploadFile",required=false) MultipartFile file){

        try {

            if(StringUtils.isEmpty(id) || StringUtils.isEmpty(username)){
                return ResultVO.error(ResultEnum.PARAM_ERROR);
            }

            String path = UploadOSSUtils.uploadSinglePic(file,username+"/");

            return userService.uploadIcon(id,path);

        } catch (Exception e) {
            e.printStackTrace();
            return ResultVO.error(ResultEnum.PIC_UPLOAD_FAILURE);

        }

    }


    @PostMapping(value = {"/getVerifyCode"})
    public ResultVO getVerifyCode(@RequestParam(value = "username",required = false)String username,
                                  HttpServletRequest request, HttpServletResponse response){

        return userService.getVerifyCode(username,request,response);
    }
}
