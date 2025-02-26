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

    /**
     * Método para consultar el saldo de la billetera.
     * @return Saldo actual de la billetera
     */
    public double consultarSaldo() {
        return saldo;
    }

    /**
     * Método para consultar una transacción por su ID.
     * @param id ID de la transacción a buscar
     * @return Transacción encontrada
     * @throws Exception Si no se encuentra una transacción con el ID dado
     */
    public RegistroTransaccion consultarTransaccion(String id) throws Exception {
        return registroTransacciones
                .stream()
                .filter(t -> t.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new Exception("No se encontró una transacción con el ID: " + id));
    }

    /**
     * Método para realizar una transacción.
     * @param transaccion Transacción a realizar
     * @throws Exception Si la transacción es nula o no hay saldo suficiente
     */
    public void realizarTransaccion(RegistroTransaccion transaccion) throws Exception {
        if (transaccion == null) {
            throw new IllegalArgumentException("La transacción no puede ser nula.");
        }

        // Verificar si es un gasto y si hay saldo suficiente
        if (transaccion.getMonto() < 0 && saldo < Math.abs(transaccion.getMonto())) {
            throw new Exception("Saldo insuficiente para realizar la transacción.");
        }

        // Actualizar el saldo
        saldo += transaccion.getMonto();

        // Agregar la transacción al registro
        registroTransacciones.add(transaccion);
    }

    /**
     * Método para obtener el porcentaje de gastos sobre ingresos.
     * @return Porcentaje de gastos sobre ingresos
     * @throws Exception Si no hay ingresos registrados
     */
    public double obtenerPorcentajeGastosIngresos() throws Exception {
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
            throw new Exception("No hay ingresos registrados para calcular el porcentaje.");
        }

        return (totalGastos / totalIngresos) * 100;
    }

    // Getters y Setters
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) throws Exception {
        if (numero == null || numero.length() != 10 || !numero.matches("\\d{10}")) {
            throw new Exception("El número de billetera debe tener 10 dígitos.");
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