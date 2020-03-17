package com.qf.best.team.userlogin;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.base.BaseServiceImpl;
import com.qf.base.IBaseDao;
import com.qf.best.team.userloginapi.UserLoginApi;
import com.qf.dto.ResultBean;
import com.qf.entity.TUser;
import com.qf.mapper.TUserMapper;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.aop.RawTargetAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Service
@Component
public class UserLogin extends BaseServiceImpl<TUser> implements UserLoginApi {

    @Autowired
    private TUserMapper mapper;

    @Override
    public IBaseDao<TUser> getBaseDao() {
        return mapper;
    }

    @Override
    public ResultBean checkLogin(String uname, String password) {
        TUser user = mapper.selectByUsername(uname);
        System.out.println(user.toString());
        Boolean flag = user.getFlag();
        if (!flag){
            return ResultBean.error();
        }
        if(user!=null){
            if(password.equals(user.getPassword())){
                return ResultBean.success(user);
            }
        }
        return ResultBean.error();
    }
}
