package com.zmm.paintingdays.service;


import com.zmm.paintingdays.bean.ResultVO;
import com.zmm.paintingdays.bean.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/11/1
 * Email:65489469@qq.com
 */
public interface UserService {

    ResultVO register(String username, String password, String verifyCode, HttpServletRequest request);

    ResultVO login(String username, String password);

    ResultVO delete(String id);

    ResultVO update(User user);

    ResultVO findUserById(String id);

    ResultVO modifyUsername(String id, String newUsername);

    ResultVO uploadIcon(String id, String icon);

    ResultVO getVerifyCode(String username, HttpServletRequest request, HttpServletResponse response);

    ResultVO modifyPassword(String username, String oldPassword, String newPassword);

    ResultVO resetPassword(String username, String newPassword, String verifyCode, HttpServletRequest request);
}
