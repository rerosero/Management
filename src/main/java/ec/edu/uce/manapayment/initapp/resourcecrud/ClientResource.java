package ec.edu.uce.manapayment.initapp.resourcecrud;

import ec.edu.uce.manapayment.crud.client.ClientService;
import ec.edu.uce.manapayment.jpa.client.Client;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
@Path("/clients") // Base path sigue siendo /api/clients
@Produces(MediaType.TEXT_PLAIN) // Respuestas en texto plano
@Consumes(MediaType.TEXT_PLAIN) // Solicitudes en texto plano
public class ClientResource {

    @Inject
    private ClientService clientService;

    // Crear un cliente (POST /api/clients)
    @POST
    public Response createClient(String clientData) {
        // Procesa los datos del cliente desde texto plano
        //   Juan Perez,juan.perez@example.com,123456
        try {
            String[] data = clientData.split(",");
            Client client = new Client();
            client.setNames(data[0].trim());
            client.setEmail(data[1].trim());
            client.setPassword(data[2].trim());

            clientService.createClient(client);
            return Response.status(Response.Status.CREATED).entity("Cliente creado exitosamente con ID: "
                    + client.getIdcliente()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Error procesando el cliente: " +
                    e.getMessage()).build();
        }
    }

    // Obtener todos los clientes (GET /api/clients)
    @GET
    public String findAllClients() {
        List<Client> clients = clientService.findAllClient();
        StringBuilder result = new StringBuilder();
        for (Client client : clients) {
            result.append("ID: ").append(client.getIdcliente())
                    .append(", Name: ").append(client.getNames())
                    .append(", Email: ").append(client.getEmail()).append("\n");
        }
        return result.toString();
    }

    // Obtener un cliente por ID (GET /api/clients/{id})
    @GET
    @Path("/{id}")
    public Response findClientById(@PathParam("id") int id) {
        Client client = clientService.FindByIdClient(id);
        if (client == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Cliente no encontrado").build();
        }
        return Response.ok("ID: " + client.getIdcliente() +
                ", Name: " + client.getNames() +
                ", Email: " + client.getEmail()).build();
    }

    // Actualizar un cliente (PUT /api/clients/{id})
    @PUT
    @Path("/{id}")
    public Response updateClient(@PathParam("id") int id, String clientData) {
        // Procesa los datos del cliente para actualizar (en texto plano, separado por comas)
        try {
            String[] data = clientData.split(",");
            Client client = clientService.FindByIdClient(id);
            if (client == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Cliente no encontrado").build();
            }

            client.setNames(data[0].trim());
            client.setEmail(data[1].trim());
            client.setPassword(data[2].trim());
            clientService.updateClient(client);

            return Response.ok("Cliente actualizado exitosamente").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Error actualizando el cliente: " + e.getMessage()).build();
        }
    }

    // Eliminar un cliente (DELETE /api/clients/{id})
    @DELETE
    @Path("/{id}")
    public Response deleteClient(@PathParam("id") int id) {
        Client existingClient = clientService.FindByIdClient(id);
        if (existingClient == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Cliente no encontrado").build();
        }

        clientService.deleteClient(id);
        return Response.noContent().build(); // 204: No Content
    }
}