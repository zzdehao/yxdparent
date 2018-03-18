package com.tf.action;
import com.tf.entity.TAdmin;
import com.tf.utils.CurrentUser;
/**
 * Created by wugq on 2018/3/18.
 */
public class BaseController {
    protected TAdmin getCurrent(){
        TAdmin admin = CurrentUser.get();
        return admin;
    }

}
