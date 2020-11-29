package hibernate;

import hibernate.model.Address;
import hibernate.model.Employee;
import hibernate.queries.Queries;
import org.hibernate.Session;

import javax.persistence.*;
import java.util.List;
import java.util.Random;


class Manager {

    public static void main(String[] args) {

        System.out.println("Start");

        EntityManager entityManager = null;

        EntityManagerFactory entityManagerFactory = null;

        try {

            // FACTORY NAME HAS TO MATCHED THE ONE FROM PERSISTED.XML !!!
            entityManagerFactory = Persistence.createEntityManagerFactory("hibernate-dynamic");

            entityManager = entityManagerFactory.createEntityManager();
            Session session = entityManager.unwrap(Session.class);

            //New transaction
            session.beginTransaction();

            Employee emp = new Employee();
            emp.setFirstName("Jan");
            emp.setLastName("Polak" + new Random().nextInt());
            emp.setSalary(100);
            emp.setPesel(new Random().nextInt());

            Address add = new Address();
            add.setCity("poznan");
            add.setHousenr("5");
            add.setNr("4");
            add.setPostcode("22321");
            add.setStreet("Norwida");

            emp.setAddress(add);

            // Save in First order Cache (not database yet)
            session.save(add);
            session.save(emp);

            //Simple Query
            Employee employee = session.get(Employee.class, emp.getId());
            if (employee == null) {
                System.out.println(emp.getId() + " not found! ");
            } else {
                System.out.println("Found " + employee);
            }

            System.out.println("Employee " + employee.getId() + " " + employee.getFirstName() + employee.getLastName());

            //User-defined query
            getThemAll(session);
            changeFirstGuyToNowak(session);

            //Pageable query
            for (int i = 1; i < 101; i++) {
                session.save(Employee.copyEmployee(emp, i*100));
            }
            session.flush();
            session.getTransaction().commit();

            session.getTransaction().begin();
            Queries query = new Queries(session);
            List<Employee> resultByPage = query.getAllEmployeeByPage(1);
            resultByPage = query.getAllEmployeeByPage(2);
            session.getTransaction().commit();

            session.getTransaction().begin();
            //Illegal
//            int id = employee.getId();
//            session.remove(employee);
//            Employee emp2 = new Employee();
//            emp2.setId(id);
//            emp2.setFirstName("Marcin");
//            emp2.setLastName("Wito" + new Random().nextInt());
//            emp2.setSalary(100);
//            emp2.setPesel(new Random().nextInt());
//            session.persist(emp2);

            //Commit transaction to database
            session.getTransaction().commit();

            System.out.println("Done");

            session.close();

        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
        } finally {
            entityManagerFactory.close();
        }

    }

    static void getThemAll(Session session)  {
        Query query = session.createQuery("SELECT k FROM Employee k");

        List<Employee> result = query.getResultList();
        System.out.println("I got a person " + result.get(0).getFirstName());
    }


    static void changeFirstGuyToNowak(Session session) {

        List<Employee> employees = new Queries(session).getEmployeeByName("Polak");

        employees.get(0).setLastName("NowakPRE" + new Random().nextInt());

    }

}