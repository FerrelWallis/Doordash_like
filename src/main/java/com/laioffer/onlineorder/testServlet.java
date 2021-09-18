package com.laioffer.onlineorder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laioffer.onlineorder.entity.Customer;
import org.json.JSONObject;

import java.io.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "testServlet", value = "/testServlet")
public class testServlet extends HttpServlet{

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        Customer customer= new Customer();
        customer.setEmail("sun@laioffer.com");
        customer.setPassword("123456");
        customer.setFirstName("rick");
        customer.setLastName("sun");
        customer.setEnabled(true);

        ObjectMapper mapper = new ObjectMapper();

        response.getWriter().print(mapper.writeValueAsString(customer));
    }



}
