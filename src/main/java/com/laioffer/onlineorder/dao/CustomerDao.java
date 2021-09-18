package com.laioffer.onlineorder.dao;

import com.laioffer.onlineorder.entity.Authorities;
import com.laioffer.onlineorder.entity.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerDao {
    @Autowired
    private SessionFactory sessionFactory;

    public void signUp(Customer customer) {
        //分配用户权限
        Authorities authorities = new Authorities();
        authorities.setEmail(customer.getEmail());
        authorities.setAuthorities("ROLE_USER");

        Session session = null;
        try {
          session = sessionFactory.openSession();
          session.beginTransaction(); //transaction可以防止有边写边读dirty read，只会读没有在操作的部分
          //通过transaction进行操作，transaction可以帮助回滚roll back，回到失败之前的状态
          // 否则就得自行操作失败回滚寻找失败的地方
          session.save(authorities);
          session.save(customer);
          session.getTransaction().commit();//确认上传操作
        } catch (Exception ex) {
          ex.printStackTrace();
          if (session != null) {
              session.getTransaction().rollback();//有异常作roll back
          }
        } finally {
            if (session != null) {
                session.close(); //最后session一定要关闭
            }
        }
    }

    public Customer getCustomer(String email) {
        Customer customer = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            customer = session.get(Customer.class, email); //在customer数据库中读取主键为email的数据
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return customer;
    }

}
