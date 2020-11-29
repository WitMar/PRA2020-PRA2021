package hibernate.queries;

import hibernate.model.Employee;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class Queries {

    Session session;

    public Queries(Session session) {
        this.session = session;
    }

    public List<Employee> getEmployeeByName(String name) {
        return session.createQuery(
                "SELECT c FROM Employee c WHERE c.lastName LIKE :name", Employee.class)
                .setParameter("name", "%" + name + "%").list();
    }

    public List<Employee> getAllEmployeeByPage(int pagenr) {
        //calculate total number
        Query queryTotal = session.createQuery
                ("Select count(e) from Employee e");
        long countResult = (long)queryTotal.getSingleResult();

        //create query
        Query query = session.createQuery("Select e FROM Employee e");
        //set pageSize
        int pageSize = 10;
        //calculate number of pages
        int pageNumber = (int) ((countResult / pageSize) + 1);

        if (pagenr > pageNumber) pagenr = pageNumber;
        query.setFirstResult((pagenr-1) * pageSize);
        query.setMaxResults(pageSize);

        return query.getResultList();
    }
}
