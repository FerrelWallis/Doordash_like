package com.laioffer.onlineorder.dao;

import com.laioffer.onlineorder.entity.MenuItem;
import com.laioffer.onlineorder.entity.Restaurant;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MenuInfoDao {
    @Autowired
    private SessionFactory sessionFactory;

    public List<Restaurant> getRestaurants() {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            return session.createCriteria(Restaurant.class)
                    .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                    .list();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return new ArrayList<>();
    }


    public List<MenuItem> getAllMenuItem(int restaurantId) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Restaurant restaurant = session.get(Restaurant.class, restaurantId);
            if (restaurant != null) {
                return restaurant.getMenuItemList();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return new ArrayList<MenuItem>();
    }

    public MenuItem getMenuItem(int menuItemId) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            MenuItem menuItem = session.get(MenuItem.class, menuItemId);
            if (menuItem != null) {
                return menuItem;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return null;
    }

}
