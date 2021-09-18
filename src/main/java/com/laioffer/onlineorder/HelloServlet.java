package com.laioffer.onlineorder;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet") //resourse path
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello Rick Sun!!!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // Hello
        String name = request.getParameter("name");

        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + name + "</h1>");
        out.println("</body></html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Read customer information from request body
        JSONObject obj = new JSONObject(IOUtils.toString(request.getReader())); //读取request body
        String name = obj.getString("name");
        String sex = obj.getString("sex");

    }





    public void destroy() {
    }
}