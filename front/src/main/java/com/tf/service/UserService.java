package com.tf.service;

import com.tf.entity.TAdmin;
import com.tf.entity.TAdminExample;
import com.tf.mapper.TAdminMapper;
import com.tf.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
/**
 * Created by wugq on 2018/3/18.
 */

@Service
@Transactional
public class UserService {
    @Autowired
    private TAdminMapper tAdminMapper;
    /**
     * 根据手机号获得用户信息
     * @param tel
     * @return
     */
    public TAdmin getAdminByTel(String tel){
        if(StringUtil.isEmpty(tel)){
            return null;
        }
        TAdminExample example = new TAdminExample();
        example.createCriteria().andTelEqualTo(tel);
        List<TAdmin> userList = tAdminMapper.selectByExample(example);
        if(userList!=null && userList.size()>0){
            return userList.get(0);
        }else{
            return null;
        }
    }
    public TAdmin getAdminById(Integer id){
      return this.tAdminMapper.selectByPrimaryKey(id);
    }

}
