package com.airdrop.repository;

import com.airdrop.entity.Redeem;
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

    @Query(value = "UPDATE t_redeem t SET t.air_drop=?1 WHERE t.id = ?2", nativeQuery = true)
    @Modifying
    int updateAirDrop(String airDrop, int id);

}
