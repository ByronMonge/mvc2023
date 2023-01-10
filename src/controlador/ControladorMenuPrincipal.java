package controlador;

import modelo.ModeloPersona;
import vista.VistaPersonas;
import vista.VistaPrincipal;

public class ControladorMenuPrincipal {

    VistaPrincipal vistaPrincipal;

    public ControladorMenuPrincipal(VistaPrincipal vistaprincipal) {
        this.vistaPrincipal = vistaprincipal;
        vistaprincipal.setVisible(true);
    }

    public void iniciaControl() {
        vistaPrincipal.getMnuPersonas().addActionListener(l -> crudPersonas());
        vistaPrincipal.getBtnPersonas().addActionListener(l -> crudPersonas());
    }

    private void crudPersonas() {
        //Instancio las clases del Modelo y la Vista.
        VistaPersonas vista = new VistaPersonas();
        ModeloPersona modelo = new ModeloPersona();

        //Agregar Vista Personas al Desktop Pane.
        vistaPrincipal.getEscritorio().add(vista);

        ControladorPersona control = new ControladorPersona(modelo, vista);
        control.iniciarControl();//Empezamos las escuchas a los eventos de la vista, Listeners.
    }

    public void ControladorPrincipal(VistaPrincipal vistaPrincipal) {
        this.vistaPrincipal = vistaPrincipal;
        vistaPrincipal.setVisible(true);
    }
}
