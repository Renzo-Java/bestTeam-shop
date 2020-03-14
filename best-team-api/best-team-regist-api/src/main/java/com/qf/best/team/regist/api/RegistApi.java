package com.qf.best.team.regist.api;

import com.qf.base.IBaseDao;
import com.qf.dto.ResultBean;
import com.qf.entity.TUser;
import sun.net.www.protocol.http.HttpURLConnection;

public interface RegistApi extends IBaseDao<TUser> {
    int updateByEmail(String email);
}
