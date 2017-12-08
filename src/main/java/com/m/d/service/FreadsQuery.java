package com.m.d.service;

import com.m.d.common.entity.MFrends;

/**
 * Created by 马东 on 2017/12/8.
 *
 * @Author:madong
 * @Description:
 * @Date:Create in 21:57 2017/12/8
 * 关关雎鸠，在河之洲，
 * 窈窕淑女，君子好逑。
 */
public class FreadsQuery {
    protected static final MFrends DAO = new MFrends();
    private static final FreadsQuery QUERY = new FreadsQuery();
    public static FreadsQuery me(){return QUERY;}


}
