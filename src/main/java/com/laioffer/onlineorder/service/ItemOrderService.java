package com.laioffer.onlineorder.service;

import com.laioffer.onlineorder.dao.ItemOrderDao;
import com.laioffer.onlineorder.entity.Customer;
import com.laioffer.onlineorder.entity.MenuItem;
import com.laioffer.onlineorder.entity.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ItemOrderService {
    @Autowired
    private ItemOrderDao itemOrderDao;

    @Autowired
    private MenuInfoService menuInfoService;

    @Autowired
    private CustomerService customerService;

    public void saveOrderItem(int menuItemId) {
        OrderItem orderItem = new OrderItem();
        MenuItem menuItem = menuInfoService.getMenuItem(menuItemId);

        Authentication loggeredUser = SecurityContextHolder.getContext().getAuthentication();
        String username = loggeredUser.getName(); //email

        Customer customer = customerService.getCustomer(username);

        orderItem.setMenuItem(menuItem);
        orderItem.setCart(customer.getCart());
        orderItem.setQuantity(1);
        orderItem.setPrice(menuItem.getPrice());

        itemOrderDao.save(orderItem);
    }

}
