package rust.rayon.demo;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@ApplicationScoped
public class DatabaseService {
    @Inject
    EntityManager em; 

    @Transactional 
    public void createUser(String username, String password, String email) {
        User user = new User();

        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);

        em.persist(user);
    }

    public List<User> get() {
        return em.createQuery("SELECT id FROM app_user").getResultList();
    }
    
    @Transactional
    public void delete(User user) {
        em.remove(user);
    }
}
