import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class BilleteraVirtualTest {

    private BilleteraVirtual billetera;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario(
                "miguel",       // nombre
                "Calle 123",  // direccion
                "12345",            // id
                "miguel@gmail.com", // correo
                "123",      // contraseña
                true                // estado (activo)
        );
        billetera = new BilleteraVirtual(1000.0, usuario);
    }

    @Test
    void testConsultarSaldo() {
        // Verificar que el saldo inicial es correcto
        assertEquals(1000.0, billetera.consultarSaldo());
    }

    @Test
    void testConsultarTransaccionExistente() throws Exception {
        // Agregar una transacción
        RegistroTransaccion transaccion = new RegistroTransaccion(
                "1", LocalDateTime.now(), 500.0, "Juan Perez", Categoria.INGRESO
        );
        billetera.realizarTransaccion(transaccion);

        // Verificar que la transacción existe
        assertEquals(transaccion, billetera.consultarTransaccion("1"));
    }

    @Test
    void testConsultarTransaccionNoExistente() {
        // Verificar que se lanza una excepción si la transacción no existe
        Exception exception = assertThrows(Exception.class, () -> {
            billetera.consultarTransaccion("99");
        });
        assertEquals("No se encontró una transacción con el ID: 99", exception.getMessage());
    }

    @Test
    void testRealizarTransaccionValida() throws Exception {
        // Realizar una transacción válida (ingreso)
        RegistroTransaccion transaccion = new RegistroTransaccion(
                "1", LocalDateTime.now(), 500.0, "Juan Perez", Categoria.INGRESO
        );
        billetera.realizarTransaccion(transaccion);

        // Verificar que el saldo se actualizó correctamente
        assertEquals(1500.0, billetera.consultarSaldo());
    }

    @Test
    void testRealizarTransaccionSaldoInsuficiente() {
        // Intentar realizar una transacción con saldo insuficiente (gasto)
        RegistroTransaccion transaccion = new RegistroTransaccion(
                "1", LocalDateTime.now(), -1500.0, "miguel", Categoria.GASTO
        );

        // Verificar que se lanza una excepción
        Exception exception = assertThrows(Exception.class, () -> {
            billetera.realizarTransaccion(transaccion);
        });
        assertEquals("Saldo insuficiente para realizar la transacción.", exception.getMessage());
    }

    @Test
    void testObtenerPorcentajeGastosIngresos() throws Exception {
        // Agregar transacciones de ingreso y gasto
        billetera.realizarTransaccion(new RegistroTransaccion(
                "1", LocalDateTime.now(), 500.0, "miguel", Categoria.INGRESO
        ));
        billetera.realizarTransaccion(new RegistroTransaccion(
                "2", LocalDateTime.now(), -200.0, "Felipe Garcia", Categoria.GASTO
        ));

        // Verificar que el porcentaje de gastos sobre ingresos es correcto
        assertEquals(40.0, billetera.obtenerPorcentajeGastosIngresos());
    }

    @Test
    void testObtenerPorcentajeGastosIngresosSinIngresos() {
        // Verificar que se lanza una excepción si no hay ingresos
        Exception exception = assertThrows(Exception.class, () -> {
            billetera.obtenerPorcentajeGastosIngresos();
        });
        assertEquals("No hay ingresos registrados para calcular el porcentaje.", exception.getMessage());
    }
}