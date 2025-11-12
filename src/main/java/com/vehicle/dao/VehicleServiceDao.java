package com.vehicle.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import com.vehicle.util.HibernateUtil;
import com.vehicle.entity.*;

public class VehicleServiceDao {

    public void saveCustomer(Customer customer) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(customer);
            tx.commit();
            System.out.println("✅ Customer saved successfully!");
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public void saveVehicle(Vehicle vehicle) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(vehicle);
            tx.commit();
            System.out.println("✅ Vehicle saved successfully!");
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public void saveServiceRecord(ServiceRecord record) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(record);
            tx.commit();
            System.out.println("✅ Service Record saved successfully!");
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }
}
