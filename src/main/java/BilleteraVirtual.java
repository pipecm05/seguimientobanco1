import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

public class BilleteraVirtual {
    private String numero; // Número de 10 dígitos
    private double saldo; // Saldo actual
    private final double COSTO_ENVIO = 200; // Costo de envío fijo
    private List<RegistroTransaccion> registroTransacciones; // Lista de transacciones
    private Usuario usuario; // Usuario asociado a la billetera

    // Conjunto para almacenar números de billetera ya utilizados
    private static Set<String> numerosUtilizados = new HashSet<>();

    // Constructor
    public BilleteraVirtual(double saldo, Usuario usuario) {
        this.numero = generarNumeroUnico(); // Generar un número único de 10 dígitos
        this.saldo = saldo;
        this.usuario = usuario;
        this.registroTransacciones = new ArrayList<>();
    }

    // Método para generar un número de billetera único de 10 dígitos
    private String generarNumeroUnico() {
        String numeroGenerado;
        do {
            // Generar un número aleatorio de 10 dígitos
            long numeroAleatorio = (long) (Math.random() * 9_000_000_000L) + 1_000_000_000L;
            numeroGenerado = String.valueOf(numeroAleatorio);
        } while (numerosUtilizados.contains(numeroGenerado)); // Verificar si el número ya está en uso

        numerosUtilizados.add(numeroGenerado); // Registrar el número como utilizado
        return numeroGenerado;
    }

    // Método para consultar el saldo
    public double consultarSaldo() {
        return saldo;
    }

    // Método para consultar una transacción por su ID
    public RegistroTransaccion consultarTransaccion(String id) throws NoSuchElementException {
        for (RegistroTransaccion transaccion : registroTransacciones) {
            if (transaccion.getId().equals(id)) {
                return transaccion;
            }
        }
        throw new NoSuchElementException("No se encontró una transacción con el ID: " + id);
    }

    // Método para realizar una transacción
    public void realizarTransaccion(RegistroTransaccion transaccion) throws IllegalArgumentException {
        if (transaccion == null) {
            throw new IllegalArgumentException("La transacción no puede ser nula.");
        }

        // Verificar si es un gasto y si hay saldo suficiente
        if (transaccion.getMonto() < 0 && saldo < Math.abs(transaccion.getMonto())) {
            throw new IllegalArgumentException("Saldo insuficiente para realizar la transacción.");
        }

        // Actualizar el saldo
        saldo += transaccion.getMonto();

        // Agregar la transacción al registro
        registroTransacciones.add(transaccion);
    }

    // Método para obtener el porcentaje de gastos sobre ingresos
    public double obtenerPorcentajeGastosIngresos() throws ArithmeticException {
        double totalIngresos = 0;
        double totalGastos = 0;

        for (RegistroTransaccion transaccion : registroTransacciones) {
            if (transaccion.getMonto() > 0) {
                totalIngresos += transaccion.getMonto();
            } else {
                totalGastos += Math.abs(transaccion.getMonto());
            }
        }

        if (totalIngresos == 0) {
            throw new ArithmeticException("No hay ingresos registrados para calcular el porcentaje.");
        }

        return (totalGastos / totalIngresos) * 100;
    }

    // Getters y Setters
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        if (numero == null || numero.length() != 10 || !numero.matches("\\d{10}")) {
            throw new IllegalArgumentException("El número de billetera debe tener 10 dígitos.");
        }
        this.numero = numero;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public double getCostoEnvio() {
        return COSTO_ENVIO;
    }

    public List<RegistroTransaccion> getRegistroTransacciones() {
        return registroTransacciones;
    }

    public void setRegistroTransacciones(List<RegistroTransaccion> registroTransacciones) {
        this.registroTransacciones = registroTransacciones;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}