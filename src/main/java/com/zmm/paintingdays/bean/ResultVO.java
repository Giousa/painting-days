package com.zmm.paintingdays.bean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zmm.paintingdays.enums.ResultEnum;
import lombok.Data;

import java.io.Serializable;

@Data
public class ResultVO implements Serializable{

    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();

    // 响应业务状态
    private Integer code;

    // 响应消息
    private String message;

    // 响应中的数据
    private Object data;

    public ResultVO() {

    }

    public ResultVO(Object data) {
        this.code = 200;
        this.message = "成功";
        this.data = data;
    }

    public ResultVO(Integer status, String msg, Object data) {
        this.code = status;
        this.message = msg;
        this.data = data;
    }


    public static ResultVO ok() {
        return new ResultVO(null);
    }

    public static ResultVO ok(Object data) {
        return new ResultVO(data);
    }

    public static ResultVO build(Integer status, String msg) {
        return new ResultVO(status, msg, null);
    }

    public static ResultVO build(Integer status, String msg, Object data) {
        return new ResultVO(status, msg, data);
    }


    public static ResultVO error(ResultEnum resultEnum){

        return new ResultVO(resultEnum.getCode(),resultEnum.getMessage(),null);
    }

	@Override
	public String toString() {
		return "ResultVO [code=" + code + ", message=" + message + ", data=" + data + "]";
	}
    
    

}
