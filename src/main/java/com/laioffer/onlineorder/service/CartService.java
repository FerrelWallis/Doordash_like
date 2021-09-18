package com.laioffer.onlineorder.service;

import com.laioffer.onlineorder.dao.CartDao;
import com.laioffer.onlineorder.entity.Cart;
import com.laioffer.onlineorder.entity.Customer;
import com.laioffer.onlineorder.entity.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private CartDao cartDao;


    public Cart getCart() {
        Authentication loggeredUser = SecurityContextHolder.getContext().getAuthentication();
        String username = loggeredUser.getName();
        Customer customer = customerService.getCustomer(username);

        if (customer != null) {
            Cart cart = customer.getCart();
            double totalPrice = 0.0;
            for (OrderItem item : cart.getOrderItemList()) {
                totalPrice += item.getPrice() * item.getQuantity();
            }
            cart.setTotalPrice(totalPrice);
            return cart;

        } else {
            return new Cart();
        }
    }

    public void cleanCart() {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String username = loggedInUser.getName();
        Customer customer = customerService.getCustomer(username);
        cartDao.removeAllCartItems(customer.getCart());
    }


}
