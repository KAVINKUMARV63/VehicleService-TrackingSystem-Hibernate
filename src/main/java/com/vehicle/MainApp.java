package com.vehicle;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.vehicle.entity.Customer;
import com.vehicle.entity.ServiceRecord;
import com.vehicle.entity.Vehicle;
import com.vehicle.util.HibernateUtil;

public class MainApp {

    // single Scanner for the whole class
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        // Ensure SessionFactory / HibernateUtil has shutdown() defined
        while (true) {
            printMenu();
            // declare choice here so it's visible to the switch
            String input = sc.nextLine().trim();
            int choice = -1;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("❌ Please enter a valid number.");
                continue;
            }

            switch (choice) {
                case 1:
                    addCustomer();
                    break;
                case 2:
                    addVehicle();
                    break;
                case 3:
                    addServiceRecord();
                    break;
                case 4:
                    viewCustomers();
                    break;
                case 5:
                    updateCustomer();
                    break;
                case 6:
                    deleteCustomer();
                    break;
                case 7:
                    System.out.println("👋 Exiting... Goodbye!");
                    HibernateUtil.shutdown(); // ensure this method exists
                    sc.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("❌ Invalid choice. Try again.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n=== 🚗 Vehicle Service Tracking System ===");
        System.out.println("1. Add Customer");
        System.out.println("2. Add Vehicle");
        System.out.println("3. Add Service Record");
        System.out.println("4. View All Customers");
        System.out.println("5. Update Customer");
        System.out.println("6. Delete Customer");
        System.out.println("7. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void addCustomer() {
        System.out.print("Enter customer name: ");
        String name = sc.nextLine();
        System.out.print("Enter email: ");
        String email = sc.nextLine();
        System.out.print("Enter phone: ");
        String phone = sc.nextLine();

        Customer c = new Customer(name, email, phone);

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(c);
            tx.commit();
            System.out.println("✅ Customer added successfully! ID: " + c.getCustomerId());
        } catch (Exception e) {
            System.out.println("❌ Error adding customer: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void addVehicle() {
        System.out.print("Enter customer ID (existing): ");
        int customerId;
        try {
            customerId = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid customer ID.");
            return;
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Customer customer = session.get(Customer.class, customerId);
            if (customer == null) {
                System.out.println("❌ Customer not found!");
                return;
            }

            System.out.print("Enter vehicle model: ");
            String model = sc.nextLine();
            System.out.print("Enter registration number: ");
            String regNum = sc.nextLine();
            System.out.print("Enter brand/type (Car/Bike/etc): ");
            String brand = sc.nextLine();

            Vehicle v = new Vehicle(regNum, brand, model, customer); // adjust constructor if yours differs
            Transaction tx = session.beginTransaction();
            session.persist(v);
            tx.commit();
            System.out.println("✅ Vehicle added successfully! Vehicle ID: " + v.getId());
        } catch (Exception e) {
            System.out.println("❌ Error adding vehicle: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void addServiceRecord() {
        System.out.print("Enter vehicle ID (existing): ");
        int vehicleId;
        try {
            vehicleId = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid vehicle ID.");
            return;
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Vehicle v = session.get(Vehicle.class, vehicleId);
            if (v == null) {
                System.out.println("❌ Vehicle not found!");
                return;
            }

            System.out.print("Enter service description: ");
            String desc = sc.nextLine();
            System.out.print("Enter service cost: ");
            double cost;
            try {
                cost = Double.parseDouble(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid cost.");
                return;
            }

            ServiceRecord record = new ServiceRecord(desc, cost, v);
            record.setServiceDate(LocalDate.now());

            Transaction tx = session.beginTransaction();
            session.persist(record);
            tx.commit();
            System.out.println("✅ Service record added successfully!");
        } catch (Exception e) {
            System.out.println("❌ Error adding service record: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void viewCustomers() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Customer> list = session.createQuery("from Customer", Customer.class).list();
            if (list.isEmpty()) {
                System.out.println("⚠️ No customers found.");
                return;
            }

            System.out.println("\n=== Customer List ===");
            for (Customer c : list) {
                System.out.println("ID: " + c.getCustomerId() +
                                   " | Name: " + c.getName() +
                                   " | Email: " + c.getEmail() +
                                   " | Phone: " + c.getPhone());
            }
        } catch (Exception e) {
            System.out.println("❌ Error fetching customers: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void updateCustomer() {
        System.out.print("Enter Customer ID to update: ");
        int id;
        try {
            id = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid ID.");
            return;
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Customer customer = session.get(Customer.class, id);
            if (customer == null) {
                System.out.println("❌ Customer not found!");
                return;
            }

            System.out.println("Current details:");
            System.out.println("Name: " + customer.getName());
            System.out.println("Email: " + customer.getEmail());
            System.out.println("Phone: " + customer.getPhone());

            System.out.print("Enter new name (leave blank to keep same): ");
            String name = sc.nextLine();
            if (!name.isEmpty()) customer.setName(name);

            System.out.print("Enter new email (leave blank to keep same): ");
            String email = sc.nextLine();
            if (!email.isEmpty()) customer.setEmail(email);

            System.out.print("Enter new phone (leave blank to keep same): ");
            String phone = sc.nextLine();
            if (!phone.isEmpty()) customer.setPhone(phone);

            session.update(customer);
            tx.commit();
            System.out.println("✅ Customer updated successfully!");
        } catch (Exception e) {
            System.out.println("❌ Error updating customer: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void deleteCustomer() {
        System.out.print("Enter Customer ID to delete: ");
        int id;
        try {
            id = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid ID.");
            return;
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Customer customer = session.get(Customer.class, id);
            if (customer == null) {
                System.out.println("❌ Customer not found!");
                return;
            }

            session.delete(customer);
            tx.commit();
            System.out.println("✅ Customer deleted successfully!");
        } catch (Exception e) {
            System.out.println("❌ Error deleting customer: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
