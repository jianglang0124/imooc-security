package com.imooc.security.core.social.qq.connect;

import com.imooc.security.core.social.qq.api.QQ;
import com.imooc.security.core.social.qq.api.QQUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
/*
 * @Description
 *   这个主要是用于适配作用的，试想，我们从QQ、微信、微博中获取到的UserInfo信息是五花八门的，
 *   但是我们的Connection想要的信息就那么多，
 *   如何适配呢？很简单直接继承SpringSocial提供的ApiAdapter
 *
 * @Date 15:59 2020/5/29
 * @Param
 * @return
 **/
public class QQAdapter implements ApiAdapter<QQ> {

    @Override
    public boolean test(QQ api) {
        return true;
    }

    @Override
    public void setConnectionValues(QQ api, ConnectionValues values) {

        QQUserInfo userInfo = api.getQQUserInfo();
        values.setDisplayName(userInfo.getNickname());
        values.setImageUrl(userInfo.getFigureurl_qq_1());
        values.setProfileUrl(null);
        values.setProviderUserId(userInfo.getOpenId());

    }

    @Override
    public UserProfile fetchUserProfile(QQ api) {
        return null;
    }

    @Override
    public void updateStatus(QQ api, String message) {

    }
}
