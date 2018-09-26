package com.airdrop.repository;

import com.airdrop.entity.Redeem;
import com.airdrop.vo.RedeemVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author ShengGuang.Ye
 * @version V1.0
 * @Description: 兑换码数据库交互操作
 * @date 2018/6/1 16:50
 */
public interface RedeemRepository extends JpaRepository<Redeem, Integer>, JpaSpecificationExecutor<Redeem> {

    /**
     * 根据id查询数据
     *
     * @param id
     * @return
     */
    @Query(value = "SELECT t.* FROM t_redeem t WHERE t.data_status = 0 AND t.id = ?1", nativeQuery = true)
    Redeem getOne(int id);

    /**
     * 根据id修改数据状态
     *
     * @param id
     * @return
     */
    @Query(value = "UPDATE t_redeem t SET t.data_status = 1 WHERE t.id = ?1", nativeQuery = true)
    @Modifying
    int deleteById(int id);

    /**
     * 兑换码用户关联查询
     * @param pageable
     * @return
     */
    @Query(value = "select t.*,t2.phone,t2.email from t_redeem t left join t_user t2 on t.get_user_id = t2.id where t.data_status = 0 and t2.`status` = 0 and t2.type = 1", nativeQuery = true, countQuery = "select count(t.id) from t_redeem t where t.data_status = 0")
    Page<RedeemVo> findJoinUser(Pageable pageable);

}
