package com.qf.best.team.indexservice;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.base.BaseServiceImpl;
import com.qf.base.IBaseDao;
import com.qf.best.team.producttype.TProductTypeApi;
import com.qf.constant.TypesRedisLock;
import com.qf.entity.TProductType;
import com.qf.mapper.TProductTypeMapper;
import com.sun.javafx.binding.StringConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@Component
public class IndexService extends BaseServiceImpl<TProductType> implements TProductTypeApi {

    @Autowired
    private TProductTypeMapper mapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public IBaseDao<TProductType> getBaseDao() {
        return mapper;
    }

    @Override
    public List<TProductType> getAll() {
        String key = "productType";
        List<TProductType> types = (List<TProductType>)redisTemplate.opsForValue().get(key);

        if(types==null){
            String uuid = UUID.randomUUID().toString();
            //上锁
//            Boolean absent = redisTemplate.opsForValue().setIfAbsent(StringConstant.redis_types_lock, uuid);
            //原子操作 防线程安全问题和事务问题。
            Boolean absent = redisTemplate.opsForValue().setIfAbsent(TypesRedisLock.TYPE_REDIS_Lock, uuid, 5, TimeUnit.MINUTES);
            if(absent){
                try {
                    //设置超时时间
//                    redisTemplate.expire(StringConstant.redis_types_lock,5, TimeUnit.MINUTES);
                    //查数据
                    types = mapper.getAll();
                    //存到redis中
                    redisTemplate.opsForValue().set(key,types);
                    //释放锁
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    String currentUUID = (String) redisTemplate.opsForValue().get(TypesRedisLock.TYPE_REDIS_Lock);
                    if(uuid.equals(currentUUID)){
                        redisTemplate.delete(TypesRedisLock.TYPE_REDIS_Lock);
                    }
                }
            }else{
                try {
                    Thread.sleep(10);
                    return getAll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


        }
        return types;
    }
}
