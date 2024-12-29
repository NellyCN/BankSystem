// Clase que representa una cuenta bancaria
package org.xyzbank.model;

/**
 * La clase BankAccount contiene los detalles de una cuenta bancaria,
 * incluyendo el número de cuenta, saldo actual y tipo de cuenta.
 */
public class BankAccount {

    public enum AccountType {
        AHORROS,    // Cuenta de ahorros
        CORRIENTE   // Cuenta corriente
    }

    private String accountNumber;       // Número único de la cuenta bancaria
    private double balance;             // Saldo actual de la cuenta
    private AccountType accountType;    // Tipo de cuenta (AHORROS o CORRIENTE)

    /**
     * Constructor para inicializar una cuenta bancaria con el número de cuenta y tipo.
     * Valida que el número de cuenta no sea nulo ni vacío.
     * '@param accountNumber Número único de la cuenta.
     * '@param accountType Tipo de cuenta, definido en el enum AccountType.
     * '@throws IllegalArgumentException si el número de cuenta es nulo o vacío.
     */
    public BankAccount(String accountNumber, AccountType accountType) {
        if (accountNumber == null || accountNumber.isEmpty() || accountType == null) {
                throw new IllegalArgumentException("Account number and type are mandatory, cannot be null or empty");
        }
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = 0.0;     // Inicializa el saldo en 0.0
    }

    // Métodos Getters y Setters para acceder y modificar los atributos de la clase
    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    /**
     * Metodo para DEPOSITAR dinero en la cuenta.
     * '@param amount Cantidad a depositar.
     * '@throws IllegalArgumentException si el monto es negativo.
     */
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        this.balance += amount;

        // Registro de log para auditoría de la operación de depósito
//        System.out.println("Deposited " + amount + " into account " + accountNumber + ". New balance: " + balance);
    }

    /**
     * Metodo para RETIRAR dinero de la cuenta, con validaciones según el tipo de cuenta.
     * '@param amount Cantidad a retirar.
     * '@throws IllegalArgumentException si el monto es negativo o no cumple las reglas del tipo de cuenta.
     */
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        if (this.accountType == AccountType.AHORROS && this.balance - amount < 0) {
//            System.out.println("Withdrawal of " + amount + " rejected for account " + accountNumber + ". Insufficient balance.");
//            throw new IllegalArgumentException("Insufficient balance for savings account");
            throw new IllegalArgumentException("Savings accounts cannot have a negative balance");
        }
        if (this.accountType == AccountType.CORRIENTE && this.balance - amount < -500.00) {
//            System.out.println("Withdrawal of " + amount + " rejected for account " + accountNumber + ". Overdraft limit exceeded.");
//            throw new IllegalArgumentException("Overdraft limit exceeded for current account");
            throw new IllegalArgumentException("Checking accounts cannot exceed overdraft limit of -500.00");
        }
        this.balance -= amount;
    }
}

//Podrías implementar un sistema de registro para rastrear retiros rechazados por superar el límite de sobregiro.

