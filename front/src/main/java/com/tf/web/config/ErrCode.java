package com.tf.web.config;
public enum ErrCode {
    SUCCESS                         (0,"Success"),                      // success
    CODE_ERR                        (251,"Code error"),             // code换去不到openid
    REGISTERED                      (252,"Registered"),             // 已经注册
    NO_LOGIN                        (255,"No login");          // 用户未登录
    // 定义私有变量
    private int nCode ;
    private String nContent ;

    // 构造函数，枚举类型只能为私有
    ErrCode(int nCode, String nContent) {
        this.nCode = nCode;
        this.nContent = nContent;
    }

    public int getnCode() {
        return nCode;
    }

    public String getnContent() {
        return nContent;
    }

    @Override
    public String toString() {
        return "ErrCode{" +
                "nCode=" + nCode +
                ", nContent='" + nContent + '\'' +
                '}';
    }
}
