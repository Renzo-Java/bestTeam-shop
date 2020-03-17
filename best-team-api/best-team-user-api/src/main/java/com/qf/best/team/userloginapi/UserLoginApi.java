package com.qf.best.team.userloginapi;

import com.qf.base.IBaseDao;
import com.qf.base.IBaseService;
import com.qf.dto.ResultBean;
import com.qf.entity.TUser;

public interface UserLoginApi extends IBaseService<TUser> {
    ResultBean checkLogin(String uname, String password);
}
