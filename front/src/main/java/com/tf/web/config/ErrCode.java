package com.tf.web.config;
public enum ErrCode {
    SUCCESS                         ("200","操作成功"),                      // success
    NO_PASS                         ("1001","登录错误"),
    NO_EXISTS                       ("1002","用户不存在"),          // 用户不存在
    CODE_ERR                        ("1003","微信认证失败"),             // code换去不到openid
    REGISTERED                      ("1004","用户已注册"),             // 已经注册
    NO_LOGIN                        ("1005","未登录");          // 用户未登录
    // 定义私有变量
    private String nCode ;
    private String nContent ;

    // 构造函数，枚举类型只能为私有
    ErrCode(String nCode, String nContent) {
        this.nCode = nCode;
        this.nContent = nContent;
    }

    public String getnCode() {
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
