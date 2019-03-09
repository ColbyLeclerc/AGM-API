package io.colby;

/*
 * Copyright (c) 2019. CJ Software Company All rights reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Colby Leclerc <colby@colby.io>, January 1, 2018
 */


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
@Component(value = "io.colby")
//@EnableJpaRepositories(basePackages="io.colby", entityManagerFactoryRef="emf")
public class Application {

    private static final boolean DEV_MODE = true;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

//        sessionFactory = HibernateAnnotationUtil.getSessionFactory();
//        session = sessionFactory.getCurrentSession();
//        System.out.println("Session created");
//
//        tx = session.beginTransaction();
//
//        session.save(cart);
//        session.save(item1);
//        session.save(item2);
//
//        tx.commit();
//        System.out.println("Cart ID=" + cart.getId());
//        System.out.println("item1 ID=" + item1.getId()
//                + ", Foreign Key Cart ID=" + item.getCart().getId());
//        System.out.println("item2 ID=" + item2.getId()
//                + ", Foreign Key Cart ID=" + item.getCart().getId());

    }

    public static boolean isDevMode(){
        return DEV_MODE;
    }

}