package com.airdrop.repository.dao;

import com.airdrop.dto.QueryDto;
import com.airdrop.util.StringUtil;
import com.airdrop.vo.UserVo;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ShengGuang.Ye
 * @version V1.0
 * @Description: 兑换码数据库交互操作
 * @date 2018/6/1 16:50
 */
@Component
public class UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 关联查询
     *
     * @param pageable
     * @return
     */
    public QueryDto findJoinMoney(Pageable pageable) {
        String sql = "select t.id,t.phone,t.email,t.money,t.create_stamp,m.last_stamp,t.eos from t_user t left join (select t.t_user_id,left(GROUP_CONCAT(t.time_stamp),locate(',',GROUP_CONCAT(t.time_stamp))-1) as last_stamp from t_money_history t group by t.t_user_id) m on m.t_user_id = t.id where t.`status` = 0 and t.`type` = 0 order by t.id desc";
        Query query = entityManager.createNativeQuery(sql).
                setMaxResults(pageable.getPageSize()).
                setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        // 获得集合
        List<UserVo> userVos = jx(query.getResultList());
        // 获得总行数
        query = entityManager.createNativeQuery("select count(t.id) from t_user t where t.`status` = 0 and t.`type` = 0");
        Integer total = StringUtil.parseInt(query.getResultList().get(0));
        int totalPage = (int) Math.ceil((total * 1.0) / pageable.getPageSize());
        return new QueryDto().setData(userVos, pageable.getPageNumber(), pageable.getPageSize(), total, totalPage);
    }

    /**
     * 解析数据
     *
     * @param list
     * @return
     */
    public List<UserVo> jx(List list) {
        ArrayList<UserVo> redeemVos = new ArrayList<>();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                Object[] val = (Object[]) list.get(i);
                UserVo vo = new UserVo();
                vo.setId(StringUtil.parseInt(val[0]));
                vo.setPhone(StringUtil.parseStr(val[1]));
                vo.setEmail(StringUtil.parseStr(val[2]));
                vo.setMoney(StringUtil.parseInt(val[3]));
                vo.setCreateStamp(StringUtil.parseTime(val[4]));
                vo.setLastStamp(StringUtil.parseTime(val[5]));
                vo.setEos(StringUtil.parseStr(val[6]));
                redeemVos.add(vo);
            }
        }
        return redeemVos;
    }


}
