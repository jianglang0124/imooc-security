package com.imooc.security.core.social.qq.connect;

import com.imooc.security.core.social.qq.api.QQ;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/*
 * @Description
 *
 *   这里就是真正做适配的地方，我们把QQUserInfo，转换成了ConnectionValues。
    当我们完成了适配和Serviceprovider后，我们就可以开始构造我们的ConnectionFactory了
 *  这里的providerId表示服务提供商的唯一标识。
 *  OAuth2ConnectionFactory的构造非常简单，
 *  然后SpringSocial就可以利用它创建Connection了，创建好后，
 *  就会使用UsersConnectionRepository来将Connection存储到DBUserConnection中去。
 *  UsersConnectionRepository已经由SpringSocail提供了一个JdbcUsersConnectionRepository了，所以我们只需要做一个配置即可
 *
 * @Date 16:02 2020/5/29
 * @Param
 * @return
 **/
public class QQConnectionFactory  extends OAuth2ConnectionFactory<QQ> {

    public QQConnectionFactory(String providerId, String appId, String appSecret) {
        super(providerId, new QQServiceProvider(appId,appSecret), new QQAdapter());
    }
}
