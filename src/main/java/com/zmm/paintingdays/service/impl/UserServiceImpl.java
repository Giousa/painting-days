package com.zmm.paintingdays.service.impl;

import com.zmm.paintingdays.bean.ResultVO;
import com.zmm.paintingdays.bean.User;
import com.zmm.paintingdays.enums.ResultEnum;
import com.zmm.paintingdays.repository.UserRepository;
import com.zmm.paintingdays.service.UserService;
import com.zmm.paintingdays.utils.*;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/11/1
 * Email:65489469@qq.com
 */
@Service
public class UserServiceImpl implements UserService {

    @Value("${PAINTING_COOKIE}")
    private String PAINTING_COOKIE;

    @Value("${SMS_UID}")
    private String SMS_UID;

    @Value("${SMS_KEY}")
    private String SMS_KEY;


    @Autowired
    private UserRepository userRepository;

    @Override
    public ResultVO register(String username, String password, String verifyCode, HttpServletRequest request) {

        String cookieValue = CookieUtils.getCookie(request,PAINTING_COOKIE);

        System.out.println("注册：cookieValue = "+cookieValue);

        if(StringUtils.isEmpty(cookieValue)){
            return ResultVO.error(ResultEnum.VERIFYCODE_HAS_EXPIRE);
        }

        if(!cookieValue.equals(verifyCode)){
            return ResultVO.error(ResultEnum.VERIFYCODE_HAS_ERROR);
        }

        List<User> userList = userRepository.findByUsername(username);

        if(userList != null && userList.size() >0){
            return ResultVO.error(ResultEnum.USER_EXIST);
        }
        User pre = new User();
        pre.setId(KeyUtil.getKeyId());
        pre.setUsername(username);
        pre.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));

        User user = userRepository.save(pre);
        return ResultVO.ok(user);
    }

    @Override
    public ResultVO login(String username, String password) {


        User user = userRepository.findUserByUsernameAndPassword(username, DigestUtils.md5DigestAsHex(password.getBytes()));

        if(user != null){
            return ResultVO.ok(user);
        }

        return ResultVO.error(ResultEnum.LOGIN_FAILURE);
    }

    @Override
    public ResultVO delete(String id) {

        if(StringUtils.isEmpty(id)){
            return ResultVO.error(ResultEnum.PARAM_ERROR);
        }

        Optional<User> byId = userRepository.findById(id);
        if(!byId.isPresent()){
            return ResultVO.error(ResultEnum.USER_NOT_EXIST);
        }
        User userDB = byId.get();
        userDB.setUsername(KeyUtil.getRandomPhoneNumber());
        userRepository.save(userDB);

        return ResultVO.ok("账号删除成功");
    }

    @Override
    public ResultVO update(User user) {

        String id = user.getId();

        Optional<User> byId = userRepository.findById(id);

        if(!byId.isPresent()){
            return ResultVO.error(ResultEnum.USER_NOT_EXIST);
        }

        User userDB = byId.get();

        userDB.setNickname(user.getNickname());
        userDB.setSign(user.getSign());
        userDB.setGender(user.getGender());
        userDB.setBirthday(user.getBirthday());
        userDB.setHeight(user.getHeight());
        userDB.setWeight(user.getWeight());

        User update = userRepository.save(userDB);
        return ResultVO.ok(update);
    }

    @Override
    public ResultVO findUserById(String id) {

        if(StringUtils.isEmpty(id)){
            return ResultVO.error(ResultEnum.PARAM_ERROR);
        }

        Optional<User> byId = userRepository.findById(id);

        System.out.println("开始查询id = "+id);
        if(byId.isPresent()){

            System.out.println("查询成功");

            User user = byId.get();

//            //热点数
//            List<Hotspot> hotspotList = hotspotRepository.findHotspotsByUId(id);
//            if(hotspotList != null && hotspotList.size() > 0){
//                user.setReleases(hotspotList.size());
//            }
//
//            //关注数
//            List<Correlate> correlateList = correlateRepository.findAllByUserIdAndActive(id, true);
//            if(correlateList != null && correlateList.size() > 0){
//                user.setFollowers(correlateList.size());
//            }
//
//            //粉丝数
//            List<Correlate> correlateFunsList = correlateRepository.findAllByAuthorIdAndActive(id, true);
//            if(correlateFunsList != null && correlateFunsList.size() > 0){
//                user.setFuns(correlateFunsList.size());
//            }

            return ResultVO.ok(user);
        }

        return ResultVO.error(ResultEnum.USER_NOT_EXIST);

    }

    @Override
    public ResultVO resetPassword(String username, String newPassword, String verifyCode, HttpServletRequest request) {


        String cookieValue = CookieUtils.getCookie(request,PAINTING_COOKIE);

        if(StringUtils.isEmpty(cookieValue)){
            return ResultVO.error(ResultEnum.VERIFYCODE_HAS_EXPIRE);
        }

        if(!cookieValue.equals(verifyCode)){
            return ResultVO.error(ResultEnum.VERIFYCODE_HAS_ERROR);
        }

        List<User> userList = userRepository.findByUsername(username);

        if(userList == null || userList.size() == 0){
            return ResultVO.error(ResultEnum.USER_NOT_EXIST);
        }

        User user = userList.get(0);

        user.setPassword(DigestUtils.md5DigestAsHex(newPassword.getBytes()));

        userRepository.save(user);

        return ResultVO.ok("密码重置成功");
    }

    @Override
    public ResultVO modifyPassword(String username, String oldPassword, String newPassword) {

        List<User> userList = userRepository.findByUsername(username);

        if(userList == null || userList.size() == 0){
            return ResultVO.error(ResultEnum.USER_NOT_EXIST);
        }

        User user = userList.get(0);
        String old = DigestUtils.md5DigestAsHex(oldPassword.getBytes());
        if(user.getPassword().equals(old)){
            user.setPassword(DigestUtils.md5DigestAsHex(newPassword.getBytes()));
            userRepository.save(user);
            return ResultVO.ok("密码修改成功");
        }else {
            return ResultVO.error(ResultEnum.OLD_PASSWORD_ERROR);

        }

    }

    @Override
    public ResultVO modifyUsername(String id, String newUsername) {

        Optional<User> byId = userRepository.findById(id);

        if(!byId.isPresent()){
            return ResultVO.error(ResultEnum.USER_NOT_EXIST);
        }

        User user = byId.get();

        user.setUsername(newUsername);

        User modify = userRepository.save(user);

        return ResultVO.ok(modify);
    }

    @Override
    public ResultVO uploadIcon(String id, String icon) {

        Optional<User> byId = userRepository.findById(id);

        if(!byId.isPresent()){
            return ResultVO.error(ResultEnum.USER_NOT_EXIST);
        }

        User user = byId.get();

        user.setIcon(icon);

        User modify = userRepository.save(user);

        return ResultVO.ok(modify);
    }

    @Override
    public ResultVO getVerifyCode(String username, HttpServletRequest request, HttpServletResponse response) {

        if(StringUtils.isEmpty(username)){
            return ResultVO.error(ResultEnum.PARAM_ERROR);
        }

        String cookieValue = CookieUtils.getCookie(request,PAINTING_COOKIE);
        System.out.println("获取验证码cookieValue = "+cookieValue);

        if(TextUtils.isEmpty(cookieValue)){
            //生成验证码
            String verifyCode = VerifyCodeUtils.getCode();
            //存储验证码，用作注册和修改密码时校验 15分钟
            CookieUtils.writeCookie(response,PAINTING_COOKIE, verifyCode);

            //验证码不要展示，而是发送到手机
            HttpClientUtil clientUtil = HttpClientUtil.getInstance();
            String content = SmsUtils.build(verifyCode);
            clientUtil.sendMsgUtf8(SMS_UID, SMS_KEY, content, username);

            return ResultVO.ok("验证码发送成功");
        }else{
            return ResultVO.error(ResultEnum.VERIFYCODE_HAS_SEND);
        }

    }

}
