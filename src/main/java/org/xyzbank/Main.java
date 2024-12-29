package org.xyzbank;

import org.xyzbank.repository.BankRepository;   // Importa la clase BankRepository, que maneja el almacenamiento de datos.
import org.xyzbank.service.BankService;     // Importa la clase BankService, que gestiona las operaciones bancarias.

/** Clase principal del sistema bancario.
 * Inicializar los componentes necesarios (repositorio y servicio)
 * y arranca la interfaz de usuario por consola.
 */
public class Main {
    public static void main(String[] args) {

        // Crea una instancia de BankRepository, que será utilizada para almacenar y gestionar
        // los datos de clientes y cuentas bancarias.
        BankRepository repository = new BankRepository();

        // Crea una instancia de BankService, pasándole el repositorio como dependencia.
        // Esto permite que el servicio interactúe con los datos a través del repositorio.
        BankService service = new BankService(repository);

        // Inicia la interfaz de usuario por consola para permitir al usuario interactuar con el sistema bancario,
        // como registrar clientes, abrir cuentas y realizar transacciones.
        service.startConsoleInterface();
    }
}