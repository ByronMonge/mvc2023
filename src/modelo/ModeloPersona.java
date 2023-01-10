package modelo;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author monge
 */
public class ModeloPersona extends Persona {

    ConectPG conpg = new ConectPG();

    public ModeloPersona() {
    }

    public ModeloPersona(String idPersona, String nombre, String apellido, String sexo, Date fechaDeNacimiento, String telefono, double sueldo, int cupo, String correo) {
        super(idPersona, nombre, apellido, sexo, fechaDeNacimiento, telefono, sueldo, cupo, correo);
    }

    public List<Persona> listaPersonas() {
        try {
            //Me retorna un "List" de "persona"
            List<Persona> lista = new ArrayList<>();

            String sql = "select idpersona,nombres,apellidos,fechanacimiento, telefono,sexo, sueldo, cupo, correo from persona";

            //ConectPG conpg = new ConectPG();
            ResultSet rs = conpg.consulta(sql); //La consulta nos devuelve un "ResultSet"

            //Pasar de "ResultSet" a "List"
            while (rs.next()) {
                //Crear las instancias de las personas
                Persona persona = new Persona();

                //Todo lo que haga en la sentencia define como voy a extraer los datos
                persona.setIdPersona(rs.getString("idpersona"));
                persona.setNombre(rs.getString("nombres"));
                persona.setApellido(rs.getString("apellidos"));
                persona.setFechaDeNacimiento(rs.getDate("fechanacimiento"));
                persona.setTelefono(rs.getString("telefono"));
                persona.setSexo(rs.getString("sexo"));
                persona.setSueldo(rs.getDouble("sueldo"));
                persona.setCupo(rs.getInt("cupo"));
                persona.setCorreo(rs.getString("correo"));

                lista.add(persona); //Agrego los datos a la lista
            }

            //Cierro la conexion a la BD
            rs.close();
            //Retorno la lista
            return lista;

        } catch (SQLException ex) {
            Logger.getLogger(ModeloPersona.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public boolean crearPersona() { //Grabar la instancia en la BD

        String sql = "INSERT INTO persona(idpersona, nombres, apellidos, fechanacimiento, telefono, sexo, sueldo, cupo, foto, correo) VALUES ('" + getIdPersona() + "', '" + getNombre() + "', '" + getApellido() + "', '" + getFechaDeNacimiento() + "', '" + getTelefono() + "', '" + getSexo() + "'," + getSueldo() + ", " + getCupo() + ", null,'" + getCorreo() + "');";

        ConectPG conpg = new ConectPG();

        return conpg.accion(sql);
    }

    public boolean modificarPersona() { //modificar la instancia en la BD

        String sql = "UPDATE persona SET nombres='" + getNombre() + "', apellidos='" + getApellido() + "', fechanacimiento='" + getFechaDeNacimiento() + "', telefono='" + getTelefono() + "', sexo='" + getSexo() + "', sueldo=" + getSueldo() + ", cupo=" + getCupo() + ", foto=null" + ",correo='" + getCorreo() + "' WHERE idpersona = '" + getIdPersona() + "';";

        ConectPG conpg = new ConectPG();

        return conpg.accion(sql);
    }

    public boolean eliminarPersona(String cedula) { //eliminar la instancia en la BD. Se pasa como parametro la cedula de la persona que se desea eliminar

        String sql = "DELETE FROM persona WHERE idpersona = '" + cedula + "';";

        ConectPG conpg = new ConectPG();

        return conpg.accion(sql);
    }

    public List<Persona> buscarPersonas(String cedula) {
        try {
            //Me retorna un "List" de "persona"
            List<Persona> lista = new ArrayList<>();

            String sql = "Select * from persona where idpersona like '"+ cedula +"%'";

            //ConectPG conpg = new ConectPG();
            ResultSet rs = conpg.consulta(sql); //La consulta nos devuelve un "ResultSet"

            //Pasar de "ResultSet" a "List"
            while (rs.next()) {
                //Crear las instancias de las personas
                Persona persona = new Persona();

                //Todo lo que haga en la sentencia define como voy a extraer los datos
                persona.setIdPersona(rs.getString("idpersona"));
                persona.setNombre(rs.getString("nombres"));
                persona.setApellido(rs.getString("apellidos"));
                persona.setFechaDeNacimiento(rs.getDate("fechanacimiento"));
                persona.setTelefono(rs.getString("telefono"));
                persona.setSexo(rs.getString("sexo"));
                persona.setSueldo(rs.getDouble("sueldo"));
                persona.setCupo(rs.getInt("cupo"));
                persona.setCorreo(rs.getString("correo"));

                lista.add(persona); //Agrego los datos a la lista
            }

            //Cierro la conexion a la BD
            rs.close();
            //Retorno la lista
            return lista;

        } catch (SQLException ex) {
            Logger.getLogger(ModeloPersona.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
