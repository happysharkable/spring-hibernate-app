package com.flamexander.hibernate.homework;

import com.flamexander.hibernate.PrepareDataApp;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        PrepareDataApp.forcePrepareData();

        SessionFactory factory = new Configuration().
                configure("configs/homework/hibernate.cfg.xml")
                .buildSessionFactory();

        Session session = null;
        Scanner scanner = null;
        String input;
        MsgLibrary library;

        try {
            scanner = new Scanner(System.in);
            library = new MsgLibrary();
            input = scanner.nextLine();

            while (!input.equals(library.CHECKOUT)) {
                if (!input.startsWith("/")) {
                    System.out.println("Wrong command");
                    input = scanner.nextLine();
                    continue;
                }

                session = factory.getCurrentSession();
                session.beginTransaction();
                if (input.equals(library.SHOW_ALL_PRODUCTS)) {
                    List<Product> products = session.createQuery("SELECT p FROM Product p", Product.class).getResultList();
                    System.out.println(products);
                } else if (input.startsWith(library.SHOW_ALL_CUSTOMERS)) {
                    List<Customer> customers = session.createQuery("SELECT c FROM Customer c", Customer.class).getResultList();
                    System.out.println(customers);
                }

                if (input.split(" ").length == 2) {
                    try {
                        Long id = Long.parseLong(input.split(" ")[1]);

                        if (input.startsWith(library.SHOW_PRODUCTS_BOUGHT_BY_CUSTOMER)) {
                            Customer customer = session.createQuery("SELECT c FROM Customer c WHERE c.id = :id", Customer.class)
                                    .setParameter("id", id)
                                    .getSingleResult();
                            System.out.println(customer.getProducts());
                        } else if (input.startsWith(library.SHOW_CUSTOMERS_WHO_BOUGHT_PRODUCT)) {
                            Product product = session.createQuery("SELECT p FROM Product p WHERE p.id = :id", Product.class)
                                    .setParameter("id", id)
                                    .getSingleResult();
                            System.out.println(product.getCustomers());
                        } else if (input.startsWith(library.REMOVE_CUSTOMER)) {
                            session.createQuery("DELETE FROM Customer c WHERE c.id = :id").
                                    setParameter("id", id).
                                    executeUpdate();
                        } else if (input.startsWith(library.REMOVE_PRODUCT)) {
                            session.createQuery("DELETE FROM Product p WHERE p.id = :id").
                                    setParameter("id", id).
                                    executeUpdate();
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Wrong argument");
                    }
                }

                session.getTransaction().commit();
                input = scanner.nextLine();
            }
        } finally {
            factory.close();
            if (scanner != null) {
                scanner.close();
            }
            if (session != null) {
                session.close();
            }
        }
    }
}
