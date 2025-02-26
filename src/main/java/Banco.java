import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Banco {
    private String nombre;
    private ArrayList<Usuario> listaUsuarios;
    private ArrayList<BilleteraVirtual> billeterasVirtuales;

    // Constructor
    public Banco(String nombre) {
        this.nombre = nombre;
        this.listaUsuarios = new ArrayList<>();
        this.billeterasVirtuales = new ArrayList<>();
    }

    /**
     * Método que permite agregar un usuario a la lista de usuarios.
     * @param usuario Usuario a agregar
     * @throws Exception Si el usuario ya existe o es nulo
     */
    public void agregarUsuario(Usuario usuario) throws Exception {
        if (usuario == null) {
            throw new IllegalArgumentException("El usuario no puede ser nulo.");
        }

        Usuario usuarioBuscado = obtenerUsuario(usuario.getId());

        // Si el usuario ya existe, lanzar una excepción
        if (usuarioBuscado != null) {
            throw new Exception("Ya existe un usuario con el mismo ID.");
        } else {
            listaUsuarios.add(usuario);
        }
    }

    /**
     * Método que permite eliminar un usuario de la lista de usuarios.
     * @param id ID del usuario a eliminar
     * @throws Exception Si no se encuentra un usuario con el ID dado
     */
    public void eliminarUsuario(String id) throws Exception {
        Usuario usuarioBuscado = obtenerUsuario(id);

        // Si el usuario no existe, lanzar una excepción
        if (usuarioBuscado == null) {
            throw new Exception("No existe un usuario con el ID dado.");
        } else {
            listaUsuarios.remove(usuarioBuscado);
        }
    }

    /**
     * Método que permite actualizar un usuario en la lista de usuarios.
     * @param usuarioActualizado Usuario con los datos actualizados
     * @throws Exception Si no se encuentra un usuario con el ID dado
     */
    public void actualizarUsuario(Usuario usuarioActualizado) throws Exception {
        Usuario usuarioBuscado = obtenerUsuario(usuarioActualizado.getId());

        // Si el usuario no existe, lanzar una excepción
        if (usuarioBuscado != null) {
            usuarioBuscado.setNombre(usuarioActualizado.getNombre());
            usuarioBuscado.setDireccion(usuarioActualizado.getDireccion());
            usuarioBuscado.setCorreo(usuarioActualizado.getCorreo());
            usuarioBuscado.setContraseña(usuarioActualizado.getContraseña());
            usuarioBuscado.setEstado(usuarioActualizado.isEstado());
        } else {
            throw new Exception("No existe un usuario con el ID dado.");
        }
    }

    /**
     * Método que permite obtener un usuario de la lista de usuarios.
     * @param id ID del usuario a buscar
     * @return Usuario encontrado o null si no se encuentra
     */
    public Usuario obtenerUsuario(String id) {
        return listaUsuarios
                .stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Método que permite crear una billetera virtual para un usuario.
     * @param usuario Usuario al que se le creará la billetera
     * @param saldoInicial Saldo inicial de la billetera
     * @throws Exception Si el usuario no está registrado o es nulo
     */
    public void crearBilleteraVirtual(Usuario usuario, double saldoInicial) throws Exception {
        if (usuario == null) {
            throw new IllegalArgumentException("El usuario no puede ser nulo.");
        }

        Usuario usuarioBuscado = obtenerUsuario(usuario.getId());

        // Si el usuario no está registrado, lanzar una excepción
        if (usuarioBuscado == null) {
            throw new Exception("El usuario no está registrado en el banco.");
        } else {
            BilleteraVirtual billetera = new BilleteraVirtual(saldoInicial, usuario);
            billeterasVirtuales.add(billetera);
        }
    }

    /**
     * Método que permite realizar una transacción en una billetera virtual.
     * @param numeroBilletera Número de la billetera
     * @param transaccion Transacción a realizar
     * @throws Exception Si no se encuentra la billetera o la transacción no es válida
     */
    public void realizarTransaccion(String numeroBilletera, RegistroTransaccion transaccion) throws Exception {
        BilleteraVirtual billetera = obtenerBilleteraPorNumero(numeroBilletera);

        // Si la billetera no existe, lanzar una excepción
        if (billetera == null) {
            throw new Exception("No se encontró una billetera con el número: " + numeroBilletera);
        } else {
            billetera.realizarTransaccion(transaccion);
        }
    }

    /**
     * Método para obtener una billetera por su número.
     * @param numeroBilletera Número de la billetera
     * @return Billetera encontrada o null si no se encuentra
     */
    private BilleteraVirtual obtenerBilleteraPorNumero(String numeroBilletera) {
        return billeterasVirtuales
                .stream()
                .filter(b -> b.getNumero().equals(numeroBilletera))
                .findFirst()
                .orElse(null);
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(ArrayList<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public ArrayList<BilleteraVirtual> getBilleterasVirtuales() {
        return billeterasVirtuales;
    }

    public void setBilleterasVirtuales(ArrayList<BilleteraVirtual> billeterasVirtuales) {
        this.billeterasVirtuales = billeterasVirtuales;
    }
}