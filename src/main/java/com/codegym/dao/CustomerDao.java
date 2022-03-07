package com.codegym.dao;

import com.codegym.model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDao implements ICustomerDao {
    public static final String SELECT_FROM_CUSTOMER = "SELECT * FROM customer;";
    public static final String SELECT_FROM_CUSTOMER_WHERE_ID = "SELECT * FROM customer WHERE id = ?;";
    public static final String INSERT_INTO_CUSTOMER = "INSERT INTO customer (name,age,address,phone) VALUES (?,?,?,?);";
    public static final String UPDATE_CUSTOMER_WHERE_ID = "UPDATE customer SET name =?,age =?,address=?,phone=? WHERE id =?;";
    public static final String DELETE_FROM_CUSTOMER_WHERE_ID = "DELETE FROM customer WHERE id = ?;";
    private Connection connection = DBConnection.getConnection();

    public CustomerDao() {
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> customers = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_FROM_CUSTOMER);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String address = resultSet.getString("address");
                String phone = resultSet.getString("phone");
                Customer customer = new Customer(id, name, age, address, phone);
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customers;
    }

    @Override
    public Customer findById(int id) {
        Customer customer = new Customer();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_FROM_CUSTOMER_WHERE_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String address = resultSet.getString("address");
                String phone = resultSet.getString("phone");
                customer = new Customer(id, name, age, address, phone);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    @Override
    public boolean create(Customer customer) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_CUSTOMER);
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setInt(2, customer.getAge());
            preparedStatement.setString(3, customer.getAddress());
            preparedStatement.setString(4, customer.getPhone());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateById(int id, Customer customer) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CUSTOMER_WHERE_ID);
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setInt(2, customer.getAge());
            preparedStatement.setString(3, customer.getAddress());
            preparedStatement.setString(4, customer.getPhone());
            preparedStatement.setInt(5, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_FROM_CUSTOMER_WHERE_ID);
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
