package controlador;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.ModeloPersona;
import modelo.Persona;
import vista.VistaPersonas;

public class ControladorPersona {

    private ModeloPersona modelo;
    private VistaPersonas vista;

    public ControladorPersona(ModeloPersona modelo, VistaPersonas vista) {
        this.modelo = modelo;
        this.vista = vista;
        vista.setVisible(true);//Aprovecho el constructor para hacer visible la vista 
    }

    private void cargaPersonas() {
        //Control para consultar al modelo y luego en la vista.
        List<Persona> listap = modelo.listaPersonas(); //Llamo al metodo de listar persona del paquete modelo y agrego a otro list

        DefaultTableModel mTabla;
        mTabla = (DefaultTableModel) vista.getTbPersona().getModel(); //Me devueleve el 'modelo' y tengo que hacer un casting para pasarlo a "DefaultModelTable"
        mTabla.setNumRows(0);//Limpiar las filas de la tabla

        listap.stream().forEach(pe -> {

            //Intero el bucle con los datos extraidos desde la BD y agrego al array de tipo String "filanueva"
            String[] filanueva = {pe.getIdPersona(), pe.getNombre() + " " + pe.getApellido(), pe.getSexo(), String.valueOf(pe.getFechaDeNacimiento()), pe.getTelefono(), pe.getCorreo(), String.valueOf(pe.getSueldo()), String.valueOf(pe.getCupo())};
            mTabla.addRow(filanueva);

        });
    }

//    public void iniciarControl(){
//        vista.getBtnActualizar().addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//            }
//        });
//    }
    public void iniciarControl() {
        vista.getBtnActualizar().addActionListener(l -> cargaPersonas());

        //jDialog
        /*vista.getBtnCrear().addActionListener(l -> abrirDialogo(1));
        vista.getBtnEditar().addActionListener(l -> abrirDialogo(2));*/
        //***
        vista.getBtnCrear().addActionListener(l -> abrirDialogCrear());
        vista.getBtnEditar().addActionListener(l -> abrirYCargarDatosEnElDialog());
        //***

        vista.getBtnAceptar().addActionListener(l -> crearEditarPersona());
        vista.getBtnEliminar().addActionListener(l -> eliminarPersona());
        buscarPersona();//Llama al metodo de "buscarPersona"
    }

    //jDialog
    /*private void abrirDialogo(int ce) {
        String title;
        if (ce == 1) {
            title = "Crear nueva persona";
            vista.getDlgPersona().setName(title);
        } else {

            title = "Editar";
            vista.getDlgPersona().setName(title);
            cargarDatosPersona();
        }

        vista.getDlgPersona().setLocationRelativeTo(vista);
        vista.getDlgPersona().setSize(1100, 500);
        vista.getDlgPersona().setTitle(title);
        vista.getDlgPersona().setVisible(true);
    }*/
    //***
    public void abrirDialogCrear() {
        vista.getDlgPersona().setName("Crear nueva persona");
        vista.getDlgPersona().setLocationRelativeTo(vista);
        vista.getDlgPersona().setSize(1100, 500);
        vista.getDlgPersona().setTitle("Crear nueva persona");
        vista.getDlgPersona().setVisible(true);

        //Limpiar los datos del jDialog
        limpiarDatos();
    }
    //***

    private void crearEditarPersona() {
        if ("Crear nueva persona".equals(vista.getDlgPersona().getName())) {

            //INSERTAR
            String cedula = vista.getTxtIdentificacion().getText();
            String nombres = vista.getTxtNombres().getText();
            String apellidos = vista.getTxtApellidos().getText();

            String sexo;
            if (vista.getRbMasculino().isSelected()) {
                sexo = "Masculino";
            } else {
                if (vista.getRbFemenino().isSelected()) {
                    sexo = "Femenino";
                } else {
                    sexo = "null";
                }
            }

            String telefono = vista.getTxtTelefono().getText();
            Date fecha = vista.getjDateFecha().getDate();
            double sueldo = Double.parseDouble(vista.getSpinnerSueldo().getValue().toString());
            int cupo = Integer.parseInt(vista.getSpinnerCupo().getValue().toString());
            String correo = vista.getTxtCorreo().getText();

            ModeloPersona persona = new ModeloPersona();
            persona.setIdPersona(cedula);
            persona.setNombre(nombres);
            persona.setApellido(apellidos);
            persona.setSexo(sexo);
            persona.setTelefono(telefono);

            java.sql.Date fechaSQL = new java.sql.Date(fecha.getTime());//Paso de util.Date a sql.Date
            persona.setFechaDeNacimiento(fechaSQL);
            persona.setSueldo(sueldo);
            persona.setCupo(cupo);
            persona.setCorreo(correo);

            if (persona.crearPersona()) {
                vista.getDlgPersona().setVisible(false);
                JOptionPane.showMessageDialog(vista, "Persona Creada Satisfactoriamente");
            } else {
                JOptionPane.showMessageDialog(vista, "No se pudo crear la persona");
            }

        } else {

            //EDITAR
            String cedula = vista.getTxtIdentificacion().getText();
            String nombres = vista.getTxtNombres().getText();
            String apellidos = vista.getTxtApellidos().getText();

            String sexo;
            if (vista.getRbMasculino().isSelected()) {
                sexo = "Masculino";
            } else {
                if (vista.getRbFemenino().isSelected()) {
                    sexo = "Femenino";
                } else {
                    sexo = "null";
                }
            }

            String telefono = vista.getTxtTelefono().getText();
            Date fecha = vista.getjDateFecha().getDate();
            double sueldo = Double.parseDouble(vista.getSpinnerSueldo().getValue().toString());
            int cupo = Integer.parseInt(vista.getSpinnerCupo().getValue().toString());
            String correo = vista.getTxtCorreo().getText();

            ModeloPersona persona = new ModeloPersona();
            persona.setIdPersona(cedula);
            persona.setNombre(nombres);
            persona.setApellido(apellidos);
            persona.setSexo(sexo);
            persona.setTelefono(telefono);

            java.sql.Date fechaSQL = new java.sql.Date(fecha.getTime());//Paso de util.Date a sql.Date
            persona.setFechaDeNacimiento(fechaSQL);
            persona.setSueldo(sueldo);
            persona.setCupo(cupo);
            persona.setCorreo(correo);

            if (persona.modificarPersona()) {
                vista.getDlgPersona().setVisible(false);
                JOptionPane.showMessageDialog(vista, "Persona Modificada Satisfactoriamente");
            } else {
                JOptionPane.showMessageDialog(vista, "No se pudo modificar la persona");
            }
        }

        cargaPersonas(); //Actualizo la tabla con los datos
    }

    public void eliminarPersona() {

        int fila = vista.getTbPersona().getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Aun no ha seleccionado una fila");
        } else {

            int response = JOptionPane.showConfirmDialog(vista, "¿Seguro que desea eliminar esta información?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (response == JOptionPane.YES_OPTION) {

                String cedula;
                cedula = vista.getTbPersona().getValueAt(fila, 0).toString();

                if (modelo.eliminarPersona(cedula)) {
                    JOptionPane.showMessageDialog(null, "La persona fue eliminada exitosamente");
                    cargaPersonas();//Actualizo la tabla con los datos
                } else {
                    JOptionPane.showMessageDialog(null, "Error: La persona no se pudo eliminar");
                }
            }
        }

    }

    public void abrirYCargarDatosEnElDialog() {

        int seleccion = vista.getTbPersona().getSelectedRow();

        if (seleccion == -1) {
            JOptionPane.showMessageDialog(null, "Aun no ha seleccionado una fila");
        } else {

            String cedula = vista.getTbPersona().getValueAt(seleccion, 0).toString();
            modelo.listaPersonas().forEach((pe) -> {
                if (pe.getIdPersona().equals(cedula)) {

                    //Abre el jDialog y carga los datos en el jDialog
                    vista.getDlgPersona().setName("Editar");
                    vista.getDlgPersona().setLocationRelativeTo(vista);
                    vista.getDlgPersona().setSize(1100, 500);
                    vista.getDlgPersona().setTitle("Editar");
                    vista.getDlgPersona().setVisible(true);

                    vista.getTxtIdentificacion().setText(pe.getIdPersona());
                    vista.getTxtNombres().setText(pe.getNombre());
                    vista.getTxtApellidos().setText(pe.getApellido());

                    if (pe.getSexo().equalsIgnoreCase("Masculino")) {
                        vista.getRbMasculino().setSelected(true);
                    } else {
                        if (pe.getSexo().equalsIgnoreCase("Femenino")) {
                            vista.getRbFemenino().setSelected(true);
                        }
                    }

                    vista.getTxtTelefono().setText(pe.getTelefono());
                    vista.getTxtCorreo().setText(pe.getCorreo());
                    vista.getjDateFecha().setDate(pe.getFechaDeNacimiento());
                    vista.getSpinnerSueldo().setValue(pe.getSueldo());
                    vista.getSpinnerCupo().setValue(pe.getCupo());
                }
            });
        }
    }

    public void buscarPersona() {

        KeyListener eventoTeclado = new KeyListener() {//Crear un objeto de tipo keyListener(Es una interface) por lo tanto se debe implementar sus metodos abstractos

            @Override
            public void keyTyped(KeyEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyPressed(KeyEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyReleased(KeyEvent e) {

                List<Persona> listap = modelo.buscarPersonas(vista.getTxtBuscar().getText()); //Llamo al metodo de listar persona del paquete modelo y agrego a otro list

                DefaultTableModel mTabla;
                mTabla = (DefaultTableModel) vista.getTbPersona().getModel(); //Me devueleve el 'modelo' y tengo que hacer un casting para pasarlo a "DefaultModelTable"
                mTabla.setNumRows(0);//Limpiar las filas de la tabla

                listap.stream().forEach(pe -> {

                    //Intero el bucle con los datos extraidos desde la BD y agrego al array de tipo String "filanueva"
                    String[] filanueva = {pe.getIdPersona(), pe.getNombre() + " " + pe.getApellido(), pe.getSexo(), String.valueOf(pe.getFechaDeNacimiento()), pe.getTelefono(), pe.getCorreo(), String.valueOf(pe.getSueldo()), String.valueOf(pe.getCupo())};
                    mTabla.addRow(filanueva);

                });
            }
        };

        vista.getTxtBuscar().addKeyListener(eventoTeclado); //"addKeyListener" es un metodo que se le tiene que pasar como argumento un objeto de tipo keyListener 
    }

    public void limpiarDatos() {
        vista.getTxtIdentificacion().setText("");
        vista.getTxtNombres().setText("");
        vista.getTxtApellidos().setText("");
        vista.getSexo().clearSelection();
        vista.getTxtTelefono().setText("");
        vista.getTxtCorreo().setText("");
        vista.getjDateFecha().setDate(null);
        vista.getSpinnerSueldo().setValue(0);
        vista.getSpinnerCupo().setValue(0);
    }
}
