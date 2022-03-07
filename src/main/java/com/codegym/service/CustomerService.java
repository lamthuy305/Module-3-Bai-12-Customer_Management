package com.codegym.service;

import com.codegym.dao.ICustomerDao;
import com.codegym.model.Customer;

import java.util.List;

public class CustomerService implements ICustomerService {
    private ICustomerDao customerDao;

    public CustomerService(ICustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Override
    public List<Customer> findAll() {
        return customerDao.findAll();
    }

    @Override
    public Customer findById(int id) {
        return customerDao.findById(id);
    }

    @Override
    public boolean create(Customer customer) {
        return customerDao.create(customer);
    }

    @Override
    public boolean updateById(int id, Customer customer) {
        return customerDao.updateById(id, customer);
    }

    @Override
    public boolean deleteById(int id) {
        return customerDao.deleteById(id);
    }
}
