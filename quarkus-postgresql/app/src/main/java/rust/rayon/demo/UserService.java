package rust.rayon.demo;

import java.net.URI;
import java.time.Duration;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Uni;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserService {

    @GET
    public Uni<List<User>> list() {
        return User.listAll();
    }

    @GET
    @Path("/{id}")
    public Uni<User> get(int id) {
        return User.findById(id);
    }

    @POST
    public Uni<Response> create(User user) {
        Uni<User> result = Panache.withTransaction(user::persist)
        .replaceWith(user)
        .ifNoItem()
        .after(Duration.ofMillis(10000))
        .fail()
        .onFailure()
        .transform(t -> new IllegalStateException(t));

        return result.onItem().transform(u -> URI.create("/users" + u.getId()))
        .onItem().transform(uri -> Response.created(uri))
        .onItem().transform(Response.ResponseBuilder::build);
    }

    public Uni<User> findUserById(int id) {
        return User.findById(id);
    }

    @PUT
    @Path("/{id}")
    public Uni<Response> update(int id, User user) {
        Uni<Integer> result = Panache.withTransaction(() -> 
                            User.update("username=?1, password=?2, email=?3 where id=?4",
                                user.getUsername(), user.getPassword(), user.getEmail(), id)
                        )
                        .onFailure().recoverWithNull();
        return result.onItem().ifNotNull().transform(entity -> Response.ok(entity).build())
        .onItem().ifNull().continueWith(Response.ok().status(Response.Status.NOT_FOUND)::build);

    }

    @DELETE
    @Path("/{id}")
    public Uni<Response> delete(int id) {
        Uni<Boolean> result = Panache.withTransaction(() -> User.deleteById(id));

        return result.onItem()
        .transform(entity -> 
            !entity 
                ? Response.serverError().status(Response.Status.NOT_FOUND).build() 
                : Response.ok().status(Response.Status.OK).build());
    }
}