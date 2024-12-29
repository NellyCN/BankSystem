package org.xyzbank.service;

import org.xyzbank.model.BankAccount;
import org.xyzbank.model.Client;
import org.xyzbank.repository.BankRepository;

import java.util.Scanner;
import java.util.UUID;

/* Esta clase proporciona servicios para gestionar clientes y cuentas bancarias.
 * Actúa como una capa intermedia entre el sistema y el repositorio, permitiendo
 * realizar operaciones como registro de clientes, apertura de cuentas, depósitos,
 * retiros y consultas de balance.
 */
public class BankService {

    // Dependencia para almacenar y gestionar datos.
    private final BankRepository bankRepository;

    /* Constructor que inicializa el servicio del banco con un repositorio específico.
     * @param bankRepository. Repositorio que se usará para almacenar y gestionar datos de clientes y cuentas bancarias.
     */
    public BankService(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    /* Registra un nuevo cliente en el sistema.
     * Este metodo crea un objeto Client con la información proporcionada y lo almacena en el repositorio.
     * @param firstName, Nombre del cliente.
     * @param lastName, Apellido del cliente.
     * @param dni, Documento Nacional de Identidad del cliente.
     * @param email, Dirección de correo electrónico del cliente.
     */
    public void registerClient(String firstName, String lastName, String dni, String email) {
        // Crear una instancia de Client con los datos proporcionados.
        Client client = new Client(firstName, lastName, dni, email);
        // Agregar el cliente al repositorio.
        bankRepository.addClient(client);
    }

    /** Abre una nueva cuenta bancaria para un cliente existente identificado por su DNI.
     * Este metodo realiza las siguientes acciones:
     *  1. Verifica que el cliente exista en el repositorio.
     *  2. Genera un número único para la cuenta bancaria utilizando UUID.randomUUID().
     *  3. Crea un objeto BankAccount con el tipo de cuenta especificado.
     *  4. Asocia la cuenta bancaria al cliente.
     * Si el cliente no existe, lanza una excepción.
     * *
     * Validaciones realizadas:
     *  - Verifica que el DNI del cliente exista en el sistema antes de proceder.
     *  - Garantiza que el número de cuenta generado sea único gracias al uso de UUID.
     * *
     * '@param dni DNI del cliente.
     * '@param accountType Tipo de cuenta bancaria (AHORROS o CORRIENTE).
     */
    public void openBankAccount(String dni, BankAccount.AccountType accountType) {
        // Buscar el cliente en el repositorio por su DNI.
        Client client = bankRepository.getClientByDni(dni);
        if (client == null) {
            // Si el cliente no existe, lanzar una excepción.
            throw new IllegalArgumentException("Client not found with DNI: " + dni);
        }
        // Generar un número único para la cuenta bancaria.
        String accountNumber = UUID.randomUUID().toString();
        // Se crea una nueva instancia de BankAccount.
        BankAccount account = new BankAccount(accountNumber, accountType);
        // Agregar la cuenta al cliente.
        client.addAccount(account);
    }

    /* Realiza un depósito en una cuenta bancaria específica.
     * Busca la cuenta usando su número y aumenta el balance en la cantidad especificada.
     * @param accountNumber, Número de la cuenta bancaria.
     * @param amount, Monto a depositar.
     */
    public void deposit(String accountNumber, double amount) {
        // Iterar sobre todos los clientes.
        for (Client client : bankRepository.getAllClients()) {
            // Iterar sobre las cuentas de cada cliente.
            for (BankAccount account : client.getAccounts()) {
                // Si se encuentra la cuenta, realizar el depósito.
                if (account.getAccountNumber().equals(accountNumber)) {
                    account.deposit(amount);
                    return;      // Salir del metodo tras realizar el depósito.
                }
            }
        }
        // Si no se encuentra la cuenta, se lanza una excepción.
        throw new IllegalArgumentException("Account not found: " + accountNumber);
    }

    /* Realiza un retiro de una cuenta bancaria específica.
     * Busca la cuenta usando su número y disminuye el balance en la cantidad especificada.
     * @param accountNumber, Número de la cuenta bancaria.
     * @param amount, Monto a retirar.
     * Si la cuenta no existe o no tiene suficiente balance, lanza una excepción.
     */
    public void withdraw(String accountNumber, double amount) {
        // Iterar sobre todos los clientes.
        for (Client client : bankRepository.getAllClients()) {
            // Iterar sobre las cuentas de cada cliente.
            for (BankAccount account : client.getAccounts()) {
                // Si se encuentra la cuenta, realizar el retiro.
                if (account.getAccountNumber().equals(accountNumber)) {
                    account.withdraw(amount);
                    return;         // Salir del metodo tras realizar el retiro.
                }
            }
        }
        // Si no se encuentra la cuenta, se lanza una excepción.
        throw new IllegalArgumentException("Account not found: " + accountNumber);
    }

    /* Consulta el balance actual de una cuenta bancaria específica.
     * @param accountNumber, Número de la cuenta bancaria.
     * @return, El balance actual de la cuenta.
     */
    public double checkBalance(String accountNumber) {
        // Iterar sobre todos los clientes.
        for (Client client : bankRepository.getAllClients()) {
            // Iterar sobre las cuentas de cada cliente.
            for (BankAccount account : client.getAccounts()) {
                // Si se encuentra la cuenta, devolver el saldo.
                if (account.getAccountNumber().equals(accountNumber)) {
                    return account.getBalance();
                }
            }
        }
        // Si no se encuentra la cuenta, se lanza una excepción.
        throw new IllegalArgumentException("Account not found: " + accountNumber);
    }

    /* Muestra en consola la lista de todos los clientes y sus cuentas bancarias asociadas.
    */
    public void showAllClientsAndAccounts() {
        System.out.println("\n--- Clients and Accounts ---");
        // Iterar sobre todos los clientes en el repositorio.
        for (Client client : bankRepository.getAllClients()) {
            // Mostrar información del cliente.
            System.out.println("Client: " + client.getFirstName() + " " + client.getLastName() +
                    " (DNI: " + client.getDni() + ", Email: " + client.getEmail() + ")");
            // Mostrar información de las cuentas asociadas.
            for (BankAccount account : client.getAccounts()) {
                System.out.println("  Account: " + account.getAccountNumber() +
                        ", Type: " + account.getAccountType() +
                        ", Balance: " + account.getBalance());
            }
        }
    }

    /* Inicia la interfaz de consola para interactuar con el sistema bancario.
     * Permite a los usuarios registrar clientes, abrir cuentas, realizar transacciones y
     * consultar información.
     */
    public void startConsoleInterface() {
        Scanner scanner = new Scanner(System.in);   // Escáner para leer entradas del usuario.
        while (true) {
            // Mostrar opciones del menú.
            System.out.println("\n=== Banking System - User Menu ===");
            System.out.println("1. Register Client");
            System.out.println("2. Open Bank Account");
            System.out.println("3. Deposit");
            System.out.println("4. Withdraw");
            System.out.println("5. Check Balance");
            System.out.println("6. Exit");
            System.out.println("\n=== Banking System ===");
            System.out.println("7. Show All Clients and Accounts");
            System.out.print("\n Choose an option: ");

            int choice = scanner.nextInt();     // Leer la elección del usuario.
            scanner.nextLine();                 // Limpiar el buffer de entrada

            try {
                // Manejar la elección del usuario con un switch.
                switch (choice) {
                    case 1 -> {
                        System.out.print("Enter First Name: ");
                        String firstName = scanner.nextLine();
                        System.out.print("Enter Last Name: ");
                        String lastName = scanner.nextLine();
                        System.out.print("Enter DNI: ");
                        String dni = scanner.nextLine();
                        System.out.print("Enter Email: ");
                        String email = scanner.nextLine();
                        registerClient(firstName, lastName, dni, email);        // Registrar cliente.
                        System.out.println("Client registered successfully!");
                    }
                    case 2 -> {
                        System.out.print("Enter DNI: ");
                        String dni = scanner.nextLine();
                        System.out.print("Enter Account Type (SAVINGS/CHECKING): ");
                        BankAccount.AccountType accountType = BankAccount.AccountType.valueOf(scanner.nextLine().toUpperCase());
                        openBankAccount(dni, accountType);                      // Abrir cuenta bancaria.
                        System.out.println("Bank account opened successfully!");
                    }
                    case 3 -> {
                        System.out.print("Enter Account Number: ");
                        String accountNumber = scanner.nextLine();
                        System.out.print("Enter Deposit Amount: ");
                        double amount = scanner.nextDouble();
                        deposit(accountNumber, amount);                          // Realizar depósito.
                        System.out.println("Deposit successful!");
                    }
                    case 4 -> {
                        System.out.print("Enter Account Number: ");
                        String accountNumber = scanner.nextLine();
                        System.out.print("Enter Withdrawal Amount: ");
                        double amount = scanner.nextDouble();
                        withdraw(accountNumber, amount);                        // Realizar retiro.
                        System.out.println("Withdrawal successful!");
                    }

                    case 5 -> {
                        System.out.print("Enter Account Number: ");
                        String accountNumber = scanner.nextLine();
                        double balance = checkBalance(accountNumber);           // Consultar saldo.
                        System.out.println("Account Balance: " + balance);
                    }

                    case 6 -> {
                        System.out.println("Exiting system. Goodbye!");         // Salir del sistema.
                        return;
                    }
                    case 7 -> showAllClientsAndAccounts();                  // Mostrar clientes y cuentas.

                    default -> System.out.println("Invalid option. Please try again.");
                }
            } catch (Exception e) {
                // Muestra los mensajes de error, en caso se de una de las excepciones
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
