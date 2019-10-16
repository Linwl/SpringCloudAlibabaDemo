package enums;

/**
 * @author ：linwl
 * @date ：Created in 2019/10/16 17:26
 * @description ：
 * @modified By：
 */
public enum ERRORCODE {

    Success(0, "操作成功"),
    SystemErr(1, "系统错误"),
    ParamErr(101, "参数错误"),
    InfoErr(102, "信息错误"),
    Error(103, "操作失败"),
    PermissionDenied(104, "没有权限"),
    PassportErr(105, "用户校验失败"),
    LoginError(106, "登入过期"),
    RefreshToken(107, "刷新Token"),
    UploadError(108, "上传失败"),
    DataNoCheckPass(109, "数据不完整无法提交提交或审核，需显示问题数据"),
    FallBackError(110, "服务器正在开小差，请稍等...");

    /**
     * 错误码
     */
    private int errorcode;
    /**
     * 提示信息
     */
    private String message;

    ERRORCODE(int errorcode) {
        this.errorcode = errorcode;
    }

    ERRORCODE(int errorcode, String message)
    {
        this.errorcode =errorcode;
        this.message =message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(int errorcode) {
        this.errorcode = errorcode;
    }

}
