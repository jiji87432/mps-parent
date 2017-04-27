package com.chanpay.ppd.mps.provider.serviceimpl;

import com.chanpay.ppd.mps.api.entity.TripUser;
import com.chanpay.ppd.mps.api.exception.UserExistException;
import com.chanpay.ppd.mps.api.service.ITripUserService;
import com.chanpay.ppd.mps.common.service.service.CrudService;
import com.chanpay.ppd.mps.provider.mapper.TripUserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户服务实现
 *
 * @author zhangxd
 */
@Service
@Transactional(readOnly = true)
public class TripUserService extends CrudService<TripUserMapper, TripUser> implements ITripUserService {

    @Override
    public TripUser getByMobile(String mobile) {
        return getDao().getByMobile(mobile);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateInfo(TripUser tripUser) {
        tripUser.preUpdate();
        getDao().updateInfo(tripUser);
    }

    @Override
    @Transactional(readOnly = false)
    public void registryUser(String mobile, String password) throws UserExistException {
        // 用户已存在不做处理，防止客户端重复提交
        TripUser oldUser = getByMobile(mobile);
        if (oldUser != null) {
            throw new UserExistException(String.format("用户 '%s' 已存在", oldUser.getMobile()));
        }

        //插入用户信息
        TripUser user = new TripUser();
        user.preInsert();
        user.setMobile(mobile);
        user.setPassword(password);
        getDao().insert(user);
    }

    @Override
    @Transactional(readOnly = false)
    public void updatePasswordByMobile(String mobile, String password) {
        getDao().updatePasswordByMobile(mobile, password);
    }

    @Override
    @Transactional(readOnly = false)
    public void updatePhotoByUserId(String userId, String photo) {
        getDao().updatePhotoByUser(userId, photo);
    }

}
