package com.airdrop.repository.dao;

import com.airdrop.dto.UpdateDto;
import com.airdrop.util.StringUtil;
import com.airdrop.vo.AirDropVo;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author ShengGuang.Ye
 * @version V1.0
 * @Description: 空投数据库交互操作
 * @date 2018/9/26 16:40
 */
@Component
public class AirDropDao {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 关联查询
     *
     * @return
     */
    public UpdateDto findAirDrop() {
        // 获得已领取数据
        String sql = "select t.use_status `status`,count(t.id) from t_redeem t where t.data_status = 0 group by t.use_status";
        Query query = entityManager.createNativeQuery(sql);
        // 获得集合
        List<AirDropVo> status = jxStatus(query.getResultList());
        // 获得当前空投
        sql = "select t.remaining_num,t.loosen_num from t_airdrop t";
        query = entityManager.createNativeQuery(sql);
        List<AirDropVo> values = jx(query.getResultList());
        return new UpdateDto(new HashMap<String, List<AirDropVo>>() {{
            this.put("status", status);
            this.put("values", values);
        }});
    }

    /**
     * 解析数据
     *
     * @param list
     * @return
     */
    public List<AirDropVo> jxStatus(List list) {
        ArrayList<AirDropVo> airDropVos = new ArrayList<>();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                Object[] val = (Object[]) list.get(i);
                AirDropVo vo = new AirDropVo();
                vo.setStatus(StringUtil.parseInt(val[0]));
                vo.setCount(StringUtil.parseInt(val[1]));
                airDropVos.add(vo);
            }
        }
        return airDropVos;
    }

    /**
     * 解析数据
     *
     * @param list
     * @return
     */
    public List<AirDropVo> jx(List list) {
        ArrayList<AirDropVo> airDropVos = new ArrayList<>();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                Object[] val = (Object[]) list.get(i);
                AirDropVo vo = new AirDropVo();
                vo.setRemainingNum(StringUtil.parseInt(val[0]));
                vo.setLoosenNum(StringUtil.parseInt(val[1]));
                airDropVos.add(vo);
            }
        }
        return airDropVos;
    }


}
