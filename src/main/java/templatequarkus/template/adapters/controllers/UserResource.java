package templatequarkus.template.adapters.controllers;

import org.bson.types.ObjectId;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import templatequarkus.template.adapters.databases.entities.UserEntity;
import templatequarkus.template.application.services.UserService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("/v1/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    private static final Logger logger = Logger.getLogger(UserResource.class);

    @Inject
    public UserService userService;

    @GET
    public Response index() {
        return Response.ok(userService.getAll()).build();
    }

    @GET
    @Path("/{_id}")
    public Response read(@PathParam("_id") final String _id) {
        try {
            final UserEntity userEntity = userService.findById(new ObjectId(_id));
            return Response.ok(userEntity).build();
        } catch (final Exception e) {
            logger.errorf("Usuário não encontrado: [HTTP-404] ");
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    public Response create(@RequestBody @Valid final UserEntity userEntity) {
        try {
            final UserEntity model = userService.save(userEntity);
            return Response.created(URI.create(model.get_id().toHexString())).build();
        } catch (final Exception e) {
            logger.errorf("Houve um erro ao criar um novo usuário (verifique campo 'data' formato yyyy-MM-dd): [HTTP-400] ");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("/{_id}")
    public Response edit(@PathParam("_id") final String _id, @RequestBody @Valid final UserEntity userEntity) {
        try {
            userService.update(new ObjectId(_id), userEntity);
            return Response.status(Response.Status.OK).build();
        } catch (final Exception e) {
            logger.errorf("Erro ao atualizar (verifique 'data' yyyy-MM-dd ou ID): [HTTP-404/400] ");
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{_id}")
    public Response delete(@PathParam("_id") final String _id) {
        try {
            userService.remove(_id);
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            logger.errorf("Usuário não encontrado: [HTTP-404] ");
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}