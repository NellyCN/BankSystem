package org.xyzbank.repository;

import org.xyzbank.model.Client;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase BankRepository, que actúa como repositorio en memoria para almacenar clientes del banco.
 * Utiliza una lista para simular el almacenamiento persistente.
 */
public class BankRepository {

    /* Lista de clientes que representa el almacenamiento en memoria.
     * Cada cliente contiene información personal y cuentas bancarias asociadas.
     */
    private List<Client> clientList = new ArrayList<>();

    /*Agrega un cliente a la lista, asegurándose de que el DNI sea único.
     * Agrega un cliente a la lista y Valida que el DNI sea único.
     * Verifica si el cliente con el mismo DNI ya existe en la lista.
     *
     * '@param client El cliente a agregar.
     * '@throws IllegalArgumentException si ya existe un cliente con el mismo DNI.
     */
    public void addClient(Client client) {
        for (Client existingClient : clientList) {
            if (existingClient.getDni().equals(client.getDni())) {
                throw new IllegalArgumentException("Client with DNI already exists");
            }
        }
        clientList.add(client);
    }

    /* Obtiene un cliente por su DNI mediante Stream para filtrar y encontrarlo.
     *
     * '@param dni El DNI del cliente buscado.
     * '@return El cliente encontrado o null si no existe.
     */
    public Client getClientByDni(String dni) {
        return clientList.stream()
                .filter(client -> client.getDni().equals(dni))
                .findFirst()
                .orElse(null);
    }

    /* Devuelve todos los clientes registrados en el repositorio.
     *
     * '@return Una lista de todos los clientes.
     */
    public List<Client> getAllClients() {
        return clientList;
    }
}
