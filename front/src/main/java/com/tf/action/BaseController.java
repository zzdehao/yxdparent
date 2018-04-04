package com.tf.action;
import com.tf.entity.TAdmin;
import com.tf.utils.CurrentUser;
import com.tf.utils.ReturnException;
import com.tf.web.config.ErrCode;

/**
 * Created by wugq on 2018/3/18.
 */
public class BaseController {
    protected TAdmin getCurrent() throws ReturnException {
        TAdmin admin = CurrentUser.get();
        if(admin == null){
            throw new ReturnException(ErrCode.NO_LOGIN.getnCode(), ErrCode.NO_LOGIN.getnContent());
        }
        return admin;
    }

}
