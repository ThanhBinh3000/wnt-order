package vn.com.gsoft.order.service.impl;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;
import vn.com.gsoft.order.constant.CachingConstant;
import vn.com.gsoft.order.service.UserCacheService;

@Service
@EnableCaching
public class UserCacheServiceImpl implements UserCacheService {
    @Override
    @CacheEvict(value = CachingConstant.USER)
    public void clearCacheByUsername(String username) {
    }
}
