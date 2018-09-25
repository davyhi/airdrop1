package com.airdrop.repository.dao;

import com.airdrop.dto.QueryDto;
import com.airdrop.util.StringUtil;
import com.airdrop.vo.RedeemVo;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ShengGuang.Ye
 * @version V1.0
 * @Description: 兑换码数据库交互操作
 * @date 2018/6/1 16:50
 */
@Component
public class RedeemDao {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 关联查询
     *
     * @param pageable
     * @return
     */
    public QueryDto findJoinUser(Pageable pageable) {
        String sql = "select t.*,t2.phone,t2.email from t_redeem t left join t_user t2 on t.get_user_id = t2.id where t.data_status = 0 and t2.`status` = 0 and t2.type = 1 and t.use_status = 1 order by t.id desc";
        Query query = entityManager.createNativeQuery(sql).
                setMaxResults(pageable.getPageSize()).
                setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        // 获得集合
        List<RedeemVo> redeemVos = jx(query.getResultList());
        // 获得总行数
        query = entityManager.createNativeQuery("select count(t.id) from t_redeem t where t.data_status = 0 and t.use_status = 1");
        Integer total = StringUtil.parseInt(query.getResultList().get(0));
        int totalPage = (int) Math.ceil((total * 1.0) / pageable.getPageSize());
        return new QueryDto().setData(redeemVos, pageable.getPageNumber(), pageable.getPageSize(), total, totalPage);
    }

    /**
     * 解析数据
     *
     * @param list
     * @return
     */
    public List<RedeemVo> jx(List list) {
        ArrayList<RedeemVo> redeemVos = new ArrayList<>();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                Object[] val = (Object[]) list.get(i);
                RedeemVo vo = new RedeemVo();
                vo.setId(StringUtil.parseInt(val[0]));
                vo.setRedeemCode(StringUtil.parseStr(val[1]));
                vo.setAirDrop(StringUtil.parseStr(val[2]));
                vo.setGetUserId(StringUtil.parseInt(val[3]));
                vo.setGetStamp(StringUtil.parseTime(val[4]));
                vo.setUserId(StringUtil.parseInt(val[5]));
                vo.setCreateStamp(StringUtil.parseTime(val[6]));
                vo.setUseStatus(StringUtil.parseInt(val[7]));
                vo.setRemark(StringUtil.parseStr(val[9]));
                vo.setPhone(StringUtil.parseStr(val[10]));
                vo.setEmail(StringUtil.parseStr(val[11]));
                redeemVos.add(vo);
            }
        }
        return redeemVos;
    }


}
