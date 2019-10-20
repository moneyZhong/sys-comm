package com.sys.comm.response;


public class CommonReturnType<T> {
    private int code;
    private String msg;
    private Long time;

    private T data;

    private CommonReturnType() {
        this.code = 0;
        this.msg = "success";
    }

    /**
     * 定义一个成功的创建方法
     *
     * @param data 为返回前端需要的数据
     * @return
     */
    public static <R>  CommonReturnType<R> createSuccess(R data) {
        CommonReturnType<R> commonReturnType = new CommonReturnType<R>();
        commonReturnType.setTime(System.currentTimeMillis());
        commonReturnType.setData(data);
        return commonReturnType;
    }

    /**
     * 定义一个失败的创建方法
     * @param data
     * @param code
     * @param msg
     * @return
     */
    public static <R> CommonReturnType<R> createFail(R data, int code, String msg) {
        CommonReturnType<R> commonReturnType = new CommonReturnType<R>();
        commonReturnType.setCode(code);
        commonReturnType.setMsg(msg);
        commonReturnType.setTime(System.currentTimeMillis());
        commonReturnType.setData(data);
        return commonReturnType;
    }

    /**
     * 定义一个成功的创建方法
     *
     * @return
     */
    public static <R> CommonReturnType<R> createFail(int code, String msg) {
        CommonReturnType<R> commonReturnType = new CommonReturnType<R>();
        commonReturnType.setTime(System.currentTimeMillis());
        commonReturnType.setCode(code);
        commonReturnType.setMsg(msg);
        return commonReturnType;
    }

    /**
     * @return success 为 true ,则data 内返回前端需要的json数据；
     * 反之 ,则data 内使用通用的错误码格式 ReponseErrCode 对象
     */
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    /**
     * 业务响应状态码，0 - 表示正常返回，非0 代表有异常
     *
     * @return
     */
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    /**
     * 业务状态响应描述
     *
     * @return
     */
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 响应时间
     *
     * @return
     */
    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
    
    
}
