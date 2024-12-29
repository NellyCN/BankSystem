// Clase que representa un cliente del banco
package org.xyzbank.model;

import java.util.ArrayList;
import java.util.List;

/**
 * La clase Client contiene información personal del cliente y una lista de cuentas bancarias asociadas.
 * Los campos obligatorios son: nombre, apellido, DNI y correo electrónico.
 */
public class Client {
    private String firstName;
    private String lastName;
    private String dni;
    private String email;
    private List<BankAccount> accounts;     // Lista de cuentas bancarias asociadas al cliente

    /**
     * Constructor para inicializar un cliente con los datos obligatorios como parámetros.
     * '@params firstName, lastName, dni, email.
     * '@throws IllegalArgumentException si algún campo obligatorio está vacío o si el formato del correo es inválido.
     */
    public Client(String firstName, String lastName, String dni, String email) {
        // Validación para crear cliente, considera: Todos los campos son obligatorios para crear un cliente
        if (firstName == null || firstName.isBlank() || lastName == null || lastName.isBlank() ||
                dni == null || dni.isBlank() || email == null || email.isBlank()) {
            throw new IllegalArgumentException("All fields are mandatory and cannot be blank");
        }
        // Validación formato válido de correo
        if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.dni = dni;
        this.email = email;
        this.accounts = new ArrayList<>();
    }

    // Getters y Setters para acceder y modificar los atributos de la clase
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDni() {
        return dni;
    }

    public String getEmail() {
        return email;
    }

    public List<BankAccount> getAccounts() {
        return accounts;
    }

    /**
     * Metodo para agregar una cuenta bancaria a la lista de cuentas del cliente.
     * Valida que no exista ya una cuenta con el mismo número.
     * '@param account La cuenta bancaria que se va a asociar al cliente.
     * '@throws IllegalArgumentException si ya existe una cuenta con el mismo número.
     */
    public void addAccount(BankAccount account) {
        if (accounts.stream().anyMatch(existingAccount -> existingAccount.getAccountNumber().equals(account.getAccountNumber()))) {
            throw new IllegalArgumentException("An account with the same number already exists");
        }
        this.accounts.add(account);
    }
}
