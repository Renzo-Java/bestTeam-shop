package com.qf.best.team.regist.service.registservice;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.base.BaseServiceImpl;
import com.qf.base.IBaseDao;
import com.qf.best.team.regist.api.RegistApi;
import com.qf.dto.ResultBean;
import com.qf.entity.TUser;
import com.qf.mapper.TUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Service
@Component
public class RegistService extends BaseServiceImpl<TUser> implements RegistApi {

    @Autowired
    private TUserMapper mapper;

    @Override
    public IBaseDao<TUser> getBaseDao() {
        return mapper;
    }

    @Override
    public int updateByEmail(String email) {
        return mapper.updateByEmail(email);
    }


}
