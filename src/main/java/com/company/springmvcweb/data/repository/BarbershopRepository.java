package com.company.springmvcweb.data.repository;

import com.company.springmvcweb.data.helper.Helper;
import com.company.springmvcweb.data.model.*;
import lombok.NonNull;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;

public class BarbershopRepository {
    private static SessionFactory factory;

    public BarbershopRepository() {
        try {
            factory = new Configuration().
                    configure().
                    addAnnotatedClass(Employee.class).
                    addAnnotatedClass(Client.class).
                    addAnnotatedClass(Service.class).
                    addAnnotatedClass(WorkTime.class).
                    addAnnotatedClass(Appointment.class).
                    addAnnotatedClass(Schedule.class).
                    buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public Iterable<Employee> getEmployees() {
        var session = factory.openSession();

        try {
            return session.createQuery("FROM Employee").list();
        } catch (HibernateException exception) {
            System.err.println(exception);
        } finally {
            session.close();
        }

        return new ArrayList<>();
    }

    public Iterable<Client> getClients() {
        var session = factory.openSession();

        try {
            return session.createQuery("FROM Client").list();
        } catch (HibernateException exception) {
            System.err.println(exception);
        } finally {
            session.close();
        }

        return new ArrayList<>();
    }

    public Iterable<Service> getServices() {
        var session = factory.openSession();

        try {
            return session.createQuery("FROM Service").list();
        } catch (HibernateException exception) {
            System.err.println(exception);
        } finally {
            session.close();
        }

        return new ArrayList<>();
    }

    public Iterable<WorkTime> getWorkTime() {
        var session = factory.openSession();

        try {
            return session.createQuery("FROM WorkTime").list();
        } catch (HibernateException exception) {
            System.err.println(exception);
        } finally {
            session.close();
        }

        return new ArrayList<>();
    }

    /**
     * get service duration
     *
     * @param serviceId
     * @return
     */
    public int getServiceTime(int serviceId) {
        var session = factory.openSession();

        try {
            var result = session.createQuery("SELECT duration FROM Service WHERE id = :s").setParameter("s", serviceId).list();
            if (result != null) {
                return (int) result.get(0);
            }
        } catch (HibernateException exception) {
            System.err.println(exception);
        } finally {
            session.close();
        }

        return 0;
    }

    /**
     * saves Object to database
     *
     * @param item
     */
    public int save(@NonNull Object item) {
        var session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            var result = session.save(item);
            tx.commit();
            return (int) result;
        } catch (HibernateException exception) {
            if (tx != null) {
                tx.rollback();
            }
            System.err.println(exception);
        } finally {
            session.close();
        }
        return 0;
    }

    public Iterable<Appointment> getAppointments() {
        var session = factory.openSession();

        try {
            return session.createQuery("FROM Appointment").list();
        } catch (HibernateException exception) {
            System.err.println(exception);
        } finally {
            session.close();
        }

        return new ArrayList<>();
    }

    public Object getAppointment(int id) {
        var session = factory.openSession();

        try {
            var result = session.createQuery("FROM Appointment WHERE id = :id").setParameter("id", id).list();
            return result.get(0);
        } catch (HibernateException exception) {
            System.err.println(exception);
        } finally {
            session.close();
        }
        return new Object();
    }

    public boolean checkEmployeeAvailability(int employeeId, long startTime, long endTime) {
        var dayId = Helper.convertDate(startTime, "u");
        var startInHoursAndMinutes = Helper.convertDate(startTime, "HHmm");
        var endInHoursAndMinutes = Helper.convertDate(endTime, "HHmm");


        var session = factory.openSession();

        try {
            var result = session.createQuery(
                    "FROM Schedule WHERE " +
                            "employee_id = :eId AND " +
                            "day_id = :dId AND " +
                            "start_time <= :st AND " +
                            "end_time >= :et")
                    .setParameter("eId", employeeId)
                    .setParameter("dId", dayId)
                    .setParameter("st", startInHoursAndMinutes)
                    .setParameter("et", endInHoursAndMinutes)
                    .list();

            if (!result.isEmpty()) {
                var result1 = session.createQuery(
                        "FROM Appointment WHERE " +
                                "employee_id = :eId AND " +
                                "start_time < :et AND " +
                                "end_time > :st" )
                        .setParameter("eId", employeeId)
                        .setParameter("st", startTime)
                        .setParameter("et", endTime)
                        .list();


                if (result1.isEmpty()) {
                    return true;
                }
            }

        } catch (HibernateException exception) {
            System.err.println(exception);
        } finally {
            session.close();
        }

        return false;
    }


    //getAppointment
    //getSchedule
    //updateSchedule
}
