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

    // Método para crear un usuario
    public void crearUsuario(Usuario usuario) throws IllegalArgumentException {
        if (usuario == null) {
            throw new IllegalArgumentException("El usuario no puede ser nulo.");
        }
        if (listaUsuarios.contains(usuario)) {
            throw new IllegalArgumentException("El usuario ya existe en el banco.");
        }
        listaUsuarios.add(usuario);
    }

    // Método para actualizar un usuario
    public void actualizarUsuario(String id, Usuario usuarioActualizado) throws NoSuchElementException {
        Usuario usuario = obtenerUsuario(id);
        if (usuario == null) {
            throw new NoSuchElementException("No se encontró un usuario con el ID: " + id);
        }
        usuario.setNombre(usuarioActualizado.getNombre());
        usuario.setDireccion(usuarioActualizado.getDireccion());
        usuario.setCorreo(usuarioActualizado.getCorreo());
        usuario.setContraseña(usuarioActualizado.getContraseña());
        usuario.setEstado(usuarioActualizado.isEstado());
    }

    // Método para eliminar un usuario
    public void eliminarUsuario(String id) throws NoSuchElementException {
        Usuario usuario = obtenerUsuario(id);
        if (usuario == null) {
            throw new NoSuchElementException("No se encontró un usuario con el ID: " + id);
        }
        listaUsuarios.remove(usuario);
    }

    // Método para crear una billetera virtual
    public void crearBilleteraVirtual(Usuario usuario, double saldoInicial) throws IllegalArgumentException {
        if (usuario == null) {
            throw new IllegalArgumentException("El usuario no puede ser nulo.");
        }
        if (!listaUsuarios.contains(usuario)) {
            throw new IllegalArgumentException("El usuario no está registrado en el banco.");
        }
        BilleteraVirtual billetera = new BilleteraVirtual(saldoInicial, usuario);
        billeterasVirtuales.add(billetera);
    }

    // Método para obtener un usuario por su ID
    public Usuario obtenerUsuario(String id) throws NoSuchElementException {
        for (Usuario usuario : listaUsuarios) {
            if (usuario.getId().equals(id)) {
                return usuario;
            }
        }
        throw new NoSuchElementException("No se encontró un usuario con el ID: " + id);
    }

    // Método para realizar una transacción en una billetera virtual
    public void realizarTransaccion(String numeroBilletera, RegistroTransaccion transaccion) throws NoSuchElementException, IllegalArgumentException {
        BilleteraVirtual billetera = obtenerBilleteraPorNumero(numeroBilletera);
        if (billetera == null) {
            throw new NoSuchElementException("No se encontró una billetera con el número: " + numeroBilletera);
        }
        billetera.realizarTransaccion(transaccion);
    }

    // Método auxiliar para obtener una billetera por su número
    private BilleteraVirtual obtenerBilleteraPorNumero(String numeroBilletera) {
        for (BilleteraVirtual billetera : billeterasVirtuales) {
            if (billetera.getNumero().equals(numeroBilletera)) {
                return billetera;
            }
        }
        return null;
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