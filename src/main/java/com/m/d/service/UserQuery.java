package com.m.d.service;


import com.m.d.common.entity.MUser;

import java.util.List;

/**
 * @类名: ${CLASS_NAME}
 * @包名: com.m.im.service
 * @描述: User数据库操作类
 * @日期: 2017/10/9 9:48
 * @版本: V1.0
 * @创建人：马东
 * @修改人：马东
 */
public class UserQuery {
    protected static final MUser DAO = new MUser();
    private static final UserQuery QUERY = new UserQuery();
    public static UserQuery me(){return QUERY;}
    public MUser findUserByNumPwd(String num, String pwd){
        return DAO.findFirst("select * from m_user where user_num = ? and pwd = ?",num,pwd);
    }

    /**
     * 注册用户
     * @param mUser
     * @return
     */
    public boolean register(MUser mUser){
        return mUser.saveOrUpdate();
    }

    /**
     * 获取所有好友
     * @return
     */
    public List<MUser> findFrends(String userNum){
        return DAO.find("select mu.* FROM m_frends mf join m_user mu on mf.frend_num = mu.user_num where mf.user_num = ?",userNum);
    }
}
