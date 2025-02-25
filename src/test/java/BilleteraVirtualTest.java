import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class BilleteraVirtualTest {

    private BilleteraVirtual billetera;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario(
                "Juan Perez",       // nombre
                "Calle 123",  // direccion
                "12345",            // id
                "juan@gmail.com", // correo
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
    void testConsultarTransaccionExistente() {
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
        assertThrows(NoSuchElementException.class, () -> {
            billetera.consultarTransaccion("99");
        });
    }

    @Test
    void testRealizarTransaccionValida() {
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
                "1", LocalDateTime.now(), -1500.0, "Juan Perez", Categoria.GASTO
        );

        // Verificar que se lanza una excepción
        assertThrows(IllegalArgumentException.class, () -> {
            billetera.realizarTransaccion(transaccion);
        });
    }

    @Test
    void testObtenerPorcentajeGastosIngresos() {
        // Agregar transacciones de ingreso y gasto
        billetera.realizarTransaccion(new RegistroTransaccion(
                "1", LocalDateTime.now(), 500.0, "Juan Perez", Categoria.INGRESO
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
        assertThrows(ArithmeticException.class, () -> {
            billetera.obtenerPorcentajeGastosIngresos();
        });
    }
}