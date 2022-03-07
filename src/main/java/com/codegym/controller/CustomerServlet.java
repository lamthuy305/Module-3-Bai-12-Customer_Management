package com.codegym.controller;

import com.codegym.dao.CustomerDao;
import com.codegym.model.Customer;
import com.codegym.service.CustomerService;
import com.codegym.service.ICustomerService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CustomerServlet", value = "/customers")
public class CustomerServlet extends HttpServlet {

    private ICustomerService customerService;

    public CustomerServlet() {
        this.customerService = new CustomerService(new CustomerDao());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }

        switch (action) {
            case "view": {
                formView(request, response);
                break;
            }

            case "create": {
                formCreate(request, response);
                break;
            }

            case "delete": {
                formDelete(request, response);
                break;
            }
            case "edit": {
                formEdit(request, response);
                break;
            }

            default: {
                formShowAllCustomer(request, response);
                break;
            }
        }

    }

    private void formShowAllCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Customer> customers = customerService.findAll();
        request.setAttribute("customers", customers);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/customer/list.jsp");
        dispatcher.forward(request, response);
    }

    private void formEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Customer customer = customerService.findById(id);
        request.setAttribute("customer", customer);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/customer/edit.jsp");
        dispatcher.forward(request, response);
    }

    private void formDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Customer customer = customerService.findById(id);
        request.setAttribute("customer", customer);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/customer/delete.jsp");
        dispatcher.forward(request, response);
    }

    private void formCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/customer/create.jsp");
        dispatcher.forward(request, response);
    }

    private void formView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Customer customer = customerService.findById(id);
        request.setAttribute("customer", customer);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/customer/view.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }

        switch (action) {
            case "create": {
                createCustomer(request, response);
                break;
            }
            case "delete": {
                deleteCustomer(request, response);
                break;
            }
            case "edit": {
                editCustomer(request, response);
                break;
            }
        }
    }

    private void editCustomer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        int age = Integer.parseInt(request.getParameter("age"));
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        Customer customer = new Customer(id, name, age, address, phone);
        customerService.updateById(id,customer);
        response.sendRedirect("/customers");
    }

    private void deleteCustomer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        customerService.deleteById(id);
        response.sendRedirect("customers");
    }

    private void createCustomer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        int age = Integer.parseInt(request.getParameter("age"));
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        Customer customer = new Customer(name, age, address, phone);
        customerService.create(customer);
        response.sendRedirect("/customers");
    }
}
