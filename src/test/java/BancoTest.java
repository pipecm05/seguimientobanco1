import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class BancoTest {

    private Banco banco;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        banco = new Banco("Mi Banco");
        usuario = new Usuario(
                "miguel",       // nombre
                "Calle 123",  // direccion
                "12345",            // id
                "miguel@gmail.com", // correo
                "123",      // contraseña
                true                // estado (activo)
        );
    }

    @Test
    void testCrearUsuario() throws Exception {
        // Crear un usuario en el banco
        banco.agregarUsuario(usuario);

        // Verificar que el usuario fue agregado
        assertEquals(1, banco.getListaUsuarios().size());
        assertEquals(usuario, banco.getListaUsuarios().get(0));
    }

    @Test
    void testCrearUsuarioDuplicado() throws Exception {
        // Crear un usuario en el banco
        banco.agregarUsuario(usuario);

        // Intentar crear el mismo usuario nuevamente
        Exception exception = assertThrows(Exception.class, () -> {
            banco.agregarUsuario(usuario);
        });
        assertEquals("Ya existe un usuario con el mismo ID.", exception.getMessage());
    }

    @Test
    void testActualizarUsuario() throws Exception {
        // Crear un usuario en el banco
        banco.agregarUsuario(usuario);

        // Actualizar el usuario
        Usuario usuarioActualizado = new Usuario(
                "Maria Lopez",       // nombre
                "Avenida 456",  // direccion
                "12345",            // id
                "maria@gmail.com", // correo
                "456",      // contraseña
                false               // estado (inactivo)
        );
        banco.actualizarUsuario(usuarioActualizado);

        // Verificar que el usuario fue actualizado
        Usuario usuarioObtenido = banco.obtenerUsuario("12345");
        assertEquals("Maria Lopez", usuarioObtenido.getNombre());
        assertEquals(false, usuarioObtenido.isEstado());
    }

    @Test
    void testActualizarUsuarioNoExistente() {
        // Intentar actualizar un usuario que no existe
        Exception exception = assertThrows(Exception.class, () -> {
            banco.actualizarUsuario(usuario);
        });
        assertEquals("No existe un usuario con el ID dado.", exception.getMessage());
    }

    @Test
    void testEliminarUsuario() throws Exception {
        // Crear un usuario en el banco
        banco.agregarUsuario(usuario);

        // Eliminar el usuario
        banco.eliminarUsuario("12345");

        // Verificar que el usuario fue eliminado
        assertEquals(0, banco.getListaUsuarios().size());
    }

    @Test
    void testEliminarUsuarioNoExistente() {
        // Intentar eliminar un usuario que no existe
        Exception exception = assertThrows(Exception.class, () -> {
            banco.eliminarUsuario("99999");
        });
        assertEquals("No existe un usuario con el ID dado.", exception.getMessage());
    }

    @Test
    void testCrearBilleteraVirtual() throws Exception {
        // Crear un usuario en el banco
        banco.agregarUsuario(usuario);

        // Crear una billetera virtual para el usuario
        banco.crearBilleteraVirtual(usuario, 1000.0);

        // Verificar que la billetera fue creada
        assertEquals(1, banco.getBilleterasVirtuales().size());
        assertEquals(usuario, banco.getBilleterasVirtuales().get(0).getUsuario());
    }

    @Test
    void testCrearBilleteraVirtualUsuarioNoRegistrado() {
        // Intentar crear una billetera virtual para un usuario no registrado
        Exception exception = assertThrows(Exception.class, () -> {
            banco.crearBilleteraVirtual(usuario, 1000.0);
        });
        assertEquals("El usuario no está registrado en el banco.", exception.getMessage());
    }

    @Test
    void testObtenerUsuario() throws Exception {
        // Crear un usuario en el banco
        banco.agregarUsuario(usuario);

        // Obtener el usuario por su ID
        Usuario usuarioObtenido = banco.obtenerUsuario("12345");

        // Verificar que el usuario obtenido es correcto
        assertEquals(usuario, usuarioObtenido);
    }

    @Test
    void testRealizarTransaccion() throws Exception {
        // Crear un usuario en el banco
        banco.agregarUsuario(usuario);

        // Crear una billetera virtual para el usuario
        banco.crearBilleteraVirtual(usuario, 1000.0);
        String numeroBilletera = banco.getBilleterasVirtuales().get(0).getNumero();

        // Realizar una transacción en la billetera
        RegistroTransaccion transaccion = new RegistroTransaccion(
                "1", LocalDateTime.now(), 500.0, "miguel", Categoria.INGRESO
        );
        banco.realizarTransaccion(numeroBilletera, transaccion);

        // Verificar que el saldo de la billetera se actualizó
        assertEquals(1500.0, banco.getBilleterasVirtuales().get(0).consultarSaldo());
    }

    @Test
    void testRealizarTransaccionBilleteraNoExistente() {
        // Intentar realizar una transacción en una billetera que no existe
        Exception exception = assertThrows(Exception.class, () -> {
            banco.realizarTransaccion("99999", new RegistroTransaccion(
                    "1", LocalDateTime.now(), 500.0, "miguel", Categoria.INGRESO
            ));
        });
        assertEquals("No se encontró una billetera con el número: 99999", exception.getMessage());
    }
}