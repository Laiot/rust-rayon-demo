package rust.rayon.demo;

import javax.enterprise.context.control.ActivateRequestContext;

import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain    
public class Main implements QuarkusApplication {

  @ActivateRequestContext
  @Override
  public int run(String... args) throws Exception {

    long startTime = System.currentTimeMillis();
    
    for(int i=0; i<1000; i++) {
      User.create("user"+i, "mypass", "user@test");
    }

    for(User user : User.getUsers()) {
      User.deleteUser(user.getId());
    }

    long endTime = System.currentTimeMillis();

    System.out.println("Execution ended in " + (endTime - startTime) + " ms." );

    return 0;
  }
}