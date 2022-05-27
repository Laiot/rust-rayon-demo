package rust.rayon.demo;

import javax.inject.Inject;

import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain    
public class Main implements QuarkusApplication {

  @Inject
  DatabaseService db;

  @Override
  public int run(String... args) throws Exception {   
    for(int i=0; i<1000; i++) {
      db.createUser("user"+i, "mypass", "user@test.com");
    }

    for(User user : db.get()) {
      db.delete(user);
    }

    return 0;
 }
}