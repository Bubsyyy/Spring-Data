import entities.Project;

import javax.persistence.*;
import java.util.Comparator;

public class FindLatest10Projects {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PU_Name");
        EntityManager entityManager = entityManagerFactory.createEntityManager();


/*        Query query = entityManager
                .createQuery("SELECT p FROM Project p ORDER BY p.startDate DESC", Project.class);

         TypedQuery<Project> query = entityManager
                .createQuery("SELECT p FROM Project p ORDER BY p.startDate DESC", Project.class);
*/
        entityManager
                .createQuery("SELECT p FROM Project p ORDER BY p.startDate DESC", Project.class)
                .setMaxResults(10)
                .getResultList()
                .stream()
                .sorted(Comparator.comparing(Project::getName))
                .forEach(System.out::println);

        entityManager.close();
    }
}
