package com.laioffer.onlineorder.dao;

import com.laioffer.onlineorder.entity.Cart;
import com.laioffer.onlineorder.entity.OrderItem;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CartDao {
    @Autowired
    private SessionFactory sessionFactory;

    public void removeCartItem(int cartItemId) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            OrderItem orderItem = session.get(OrderItem.class, cartItemId);
            Cart cart = orderItem.getCart();
            cart.getOrderItemList().remove(orderItem);
            //先要去除引用，再可以成功删除

            session.beginTransaction();
            session.delete(orderItem);
            session.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();;
            if (session != null) {
                session.getTransaction().rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public void removeAllCartItems(Cart cart) {
        for (OrderItem item : cart.getOrderItemList()) {
            removeCartItem(item.getId());
        }
    }

}
