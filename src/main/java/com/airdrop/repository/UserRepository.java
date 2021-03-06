package com.airdrop.repository;

import com.airdrop.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ShengGuang.Ye
 * @version V1.0
 * @Description: 用户dao
 * @date 2018/9/10 18:35
 */

public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    User findOneByPhone(String phone);
    User findOneByEmail(String email);

    @Query(value = "select count(*) from t_user where phone = ?1",nativeQuery = true)
    Integer checkUserByPhone(String phone);

    @Query(value = "select count(*) from t_user where email = ?1",nativeQuery = true)
    Integer checkUserByEmail(String email);

    @Query(value = "select money from t_user where id = ?1",nativeQuery = true)
    Integer  findBalanceByUserId(Integer id);

    /**
     * 根据手机号或邮箱查询用户
     *
     * @param username
     * @return
     */
    @Query(value = "SELECT t.* FROM t_user t WHERE t.status = 0 AND (t.phone=?1 OR t.email=?1)",
            nativeQuery = true)
    User findByUsername(String username);

    /**
     * 后台系统根据手机号登陆
     *
     * @param     phone
     * @return
     */
    @Query(value = "SELECT t.* FROM t_user t WHERE t.`status` = 0 AND t.`phone`=?1 AND t.`type`=1", nativeQuery = true)
    User findByPhoneB(String phone);

    /**
     * 删除用户
     * @param id
     * @return
     */
    @Query(value = "update t_user t set t.`status` = 1 where t.id = ?1",nativeQuery = true)
    @Modifying
    int deleteById(int id);

    /**
     * 根据用户id查询
     * @param id
     * @return
     */
    @Query(value = "select t.* from t_user t where t.`status` = 0 and t.id = ?1", nativeQuery = true)
    User getById(int id);

    @Modifying
    @Transactional
    @Query(value = "update t_user u SET u.money= ?2 WHERE u.id= ?1",nativeQuery = true)
    void  updateUMoney(Integer id,Integer money);

    //根据手机号查询用户的帐户余额
    @Query(value = "select money from t_user where phone = ?1", nativeQuery = true)
    Integer findSpecialBalanceByPhone(String number);
    //根据邮箱查询用户的帐户余额
    @Query(value = "select money from t_user where email = ?1", nativeQuery = true)
    Integer findSpecialBalanceByEmail(String number);

    //查询用户余额之总和
    @Query(value = "select sum(money) from t_user_history where id = ?1", nativeQuery = true)
    Integer checkTotalMoney(Integer id);
}

