package com.miguel.seatcode.bootcamp.sgdb.banco.gui;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.bundle.LanternaThemes;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.ActionListDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.gui2.table.Table;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.miguel.seatcode.bootcamp.sgdb.banco.database.Cliente;
import com.miguel.seatcode.bootcamp.sgdb.banco.database.EngineSQL;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class GuiLanterna {
    public Terminal terminal;
    public Screen screen;
    public BasicWindow window;
    public WindowBasedTextGUI textGUI;
    public Panel mainPanel;
    public EngineSQL engineSQL;


    public GuiLanterna() throws IOException {
        this.terminal = new DefaultTerminalFactory().createTerminal();
        this.screen = new TerminalScreen(terminal);
        this.window = new BasicWindow();
        this.textGUI = new MultiWindowTextGUI(this.screen);
        this.mainPanel = new Panel();
        this.mainPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));

        this.window.setHints(Arrays.asList(Window.Hint.CENTERED));
        this.window.setComponent(mainPanel.withBorder(Borders.singleLine("Main Panel")));

        //esto tiene que ir al constructor
        //Setup theme
        this.textGUI.setTheme(LanternaThemes.getRegisteredTheme("default"));

        this.screen.startScreen();
        this.engineSQL = new EngineSQL();

    }

    public void menuPrincipal()  {
        //verMenuPrincipalActionListDialog();
        verMenuPrincipalActionList();
    }

    public void verTablaDatos() {
        this.mainPanel.removeAllComponents();
        Panel panelTabla = new Panel();
        panelTabla.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        final Table<String> tabla = new Table<String>("Nombre", "Apellido", "Num Cuenta");

        tabla.getTableModel().addRow("Jason", "Bourne", "111111");
        tabla.getTableModel().addRow("Denis", "Pastor", "222222");
        tabla.getTableModel().addRow("James", "Stocks", "333333");

        tabla.setSelectAction(new Runnable() {
            @Override
            public void run() {
                List<String> data = tabla.getTableModel().getRow(tabla.getSelectedRow());
                for(int i = 0; i < data.size(); i++) {
                    System.out.println(data.get(i));
                }
            }
        });

        Button btnCerrar = new Button("Cerrar", new Runnable() {
            @Override
            public void run() {
                // Actions go here
                System.out.println("btnCerrar");
                menuPrincipal();
            }
        });

        panelTabla.addComponent(new EmptySpace(new TerminalSize(0, 1)));
        panelTabla.addComponent(tabla);
        panelTabla.addComponent(new EmptySpace(new TerminalSize(0, 1)));
        panelTabla.addComponent(btnCerrar);

        this.mainPanel.addComponent(panelTabla);

    }


    public void formCliente(Cliente datos) {
        this.mainPanel.removeAllComponents();
        this.mainPanel.setLayoutManager(new GridLayout(1));

        Panel panelDatos = new Panel();
        panelDatos.setLayoutManager(new LinearLayout(Direction.VERTICAL));
        panelDatos.setLayoutManager(new GridLayout(2));

        // creamos los elementos del gui necesarios
        // id se debe crear en funcion de los parametros
        if (datos != null) {
            panelDatos.addComponent(new Label("ID"));
            final TextBox txtIDCliente = new TextBox(new TerminalSize(20, 1));
            txtIDCliente.setText(String.valueOf(datos.id));
            panelDatos.addComponent(txtIDCliente);
        }

        panelDatos.addComponent(new Label("Nombre"));
        final TextBox txtNombre = new TextBox(new TerminalSize(20, 1));
        if (datos != null) txtNombre.setText(datos.nombre);
        panelDatos.addComponent(txtNombre);

        panelDatos.addComponent(new Label("Apellidos"));
        final TextBox txtApellidos =  new TextBox(new TerminalSize(20, 1));
        if (datos != null) txtApellidos.setText(datos.apellido);
        panelDatos.addComponent(txtApellidos);

        panelDatos.addComponent(new Label("DNI"));
        final TextBox txtDNI = new TextBox(new TerminalSize(10, 1));
        if (datos != null) txtDNI.setText(datos.dni);
        panelDatos.addComponent(txtDNI);

        panelDatos.addComponent(new Label("Usuario"));
        final TextBox txtUsuario = new TextBox(new TerminalSize(5, 1));
        if (datos != null) txtUsuario.setText(datos.usuario);
        panelDatos.addComponent(txtUsuario);

        panelDatos.addComponent(new Label("Pin"));
        final TextBox txtPin = new TextBox(new TerminalSize(5, 1));
        if (datos != null) txtPin.setText(String.valueOf(datos.pin));
        panelDatos.addComponent(txtPin);

        panelDatos.addComponent(new Label("Cliente Activo"));
        TerminalSize size = new TerminalSize(5, 1);
        final CheckBoxList<String> cbActivo = new CheckBoxList<String>(size);
        cbActivo.addItem("");
        if (datos != null) cbActivo.setChecked("", datos.activo);
        panelDatos.addComponent(cbActivo);




        this.mainPanel.addComponent(new EmptySpace(new TerminalSize(0, 1)));
        this.mainPanel.addComponent(panelDatos);

        //dependiendo de si tengo datos veo o creo un usuario
        if (datos != null) {
            // botones para ver el usuario
            this.mainPanel.addComponent(new EmptySpace(new TerminalSize(0, 1)));
            this.mainPanel.addComponent(botonesVerCliente());
        }
        else {
            // botones para crear un usuario nuevo
            Button btnCancelar = new Button("Cancelar", new Runnable() {
                @Override
                public void run() {
                    // Actions go here
                    System.out.println("btnCancelar");
                    menuPrincipal();
                }
            });
            Button btnCrear = new Button("Crear", new Runnable() {
                @Override
                public void run() {
                    // Actions go here
                    System.out.println("btnCrear");
                    txtNombre.setText(txtNombre.getText().toUpperCase());
                    txtApellidos.setText(txtApellidos.getText().toUpperCase());
                    txtDNI.setText(txtDNI.getText().toUpperCase());
                    txtUsuario.setText(txtUsuario.getText().toUpperCase());

                    if ( txtNombre.getText().isBlank() || txtApellidos.getText().isBlank() || txtDNI.getText().isBlank() || txtUsuario.getText().isBlank() || txtPin.getText().isBlank( )) {
                        verPopUp("Algun campo esta vacio.\nNo se puede crear el cliente sin datos","Error",MessageDialogButton.OK );

                    } else if ( !txtPin.getText().matches("\\d\\d\\d\\d") ) {
                        System.out.println("error en el pin");
                        verPopUp("El pin debe ser numerico y de 4 cifras","Error",MessageDialogButton.OK );
                    } else if ( !cbActivo.isChecked(0)) {
                        verPopUp("No se puede dar de alta un cliente inactivo.\nCompruebe el campo Activo","Error", MessageDialogButton.OK);
                    }
                    else{
                        //datos validos
                        if (MessageDialogButton.Yes == verPopUp("Datos validos!\n¿Desea crear el nuevo cliente?","Informacion", MessageDialogButton.Yes, MessageDialogButton.No )) {
                            System.out.println(txtPin.getText());
                            Cliente cliente = new Cliente(txtNombre.getText(),txtApellidos.getText(), txtDNI.getText(),txtUsuario.getText(),Integer.parseInt(txtPin.getText()), cbActivo.isChecked(0));
                            System.out.println(cliente.insCliente());
                            //llamar al metodo necesario para insertar el nuevo cliente
                        }
                        else {
                            System.out.println("Creacion nuevo usuario cancelada");
                        }
                    }

                }
            });

            //panel botones inferior
            Panel panelBotones = new Panel();
            panelBotones.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
            panelBotones.setLayoutManager(new GridLayout(2));
            panelBotones.addComponent(btnCancelar);
            panelBotones.addComponent(btnCrear);
        }


    }

    public void botonesCrearCliente() {

    }

    public Panel botonesVerCliente() {
        Button btnCancelar = new Button("Cancelar", new Runnable() {
            @Override
            public void run() {
                // Actions go here
                System.out.println("btnCancelar");
                menuPrincipal();
            }
        });
        Panel panelBotones = new Panel();
        panelBotones.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
        panelBotones.setLayoutManager(new GridLayout(2));
        panelBotones.addComponent(btnCancelar);
        return panelBotones;
    }

    
    public MessageDialogButton verPopUp(String mensaje, String titulo, MessageDialogButton... botones) {
        return MessageDialog.showMessageDialog(textGUI, titulo, mensaje, botones);
    }


    private void verMenuPrincipalActionList(){
        this.mainPanel.removeAllComponents();
        //Definicion de lista de opciones de menu

        TerminalSize size = new TerminalSize(35, 10);
        ActionListBox actionListBox = new ActionListBox(size);

        actionListBox.addItem("Crear Usuario", new Runnable() {
            @Override
            public void run() {
                System.out.println("Crear  Usuario");
                formCliente(null);
            }
        });

        actionListBox.addItem("Consultar Usuario", new Runnable() {
            @Override
            public void run() {
                System.out.println("Consultar Usuario");
                try {
                    //todo preguntar que cliente quieres buscar...por el dni
                    Cliente cliente = engineSQL.getCliente();
                    System.out.println(cliente.toString());
                    formCliente(cliente);
                }
                catch (SQLException sqle) {
                    sqle.printStackTrace();
                }

            }
        });

        actionListBox.addItem("Reintegro en cuenta", new Runnable() {
            @Override
            public void run() {
                System.out.println("Reintegro");

            }
        });

        actionListBox.addItem("Ingreso en cuenta", new Runnable() {
            @Override
            public void run() {
                System.out.println("Ingreso");

            }
        });

        actionListBox.addItem("Ver historico de cuenta", new Runnable() {
            @Override
            public void run() {
                // Code to run when action activated
                System.out.println("Historico");
                verTablaDatos();

            }
        });

        actionListBox.addItem("Ver todos los usuarios y cuentas", new Runnable() {
            @Override
            public void run() {
                System.out.println("Ver Todos");

            }
        });

        this.mainPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
        this.window.setComponent(this.mainPanel.withBorder(Borders.singleLine("SGBD Maze Bank")));
        this.mainPanel.addComponent(new EmptySpace(TerminalSize.ONE));
        this.mainPanel.addComponent(actionListBox);
        this.mainPanel.addComponent(new Button("Salir", new Runnable() {
            @Override
            public void run() {
                System.out.println("Salir");
                verPopUp("Have a nice day!!","Salir",MessageDialogButton.OK);
                System.exit(0);
            }
        }));

        //this.mainPanel.setTheme(LanternaThemes.getRegisteredTheme("conqueror"));
        System.out.println(LanternaThemes.getRegisteredThemes().toString());

        this.textGUI.addWindowAndWait(this.window);

    }

    /* No me termina de gustar
    private void verMenuPrincipalActionListDialog()  {
        this.mainPanel.removeAllComponents();
        ActionListDialogBuilder menuPrincipal = new ActionListDialogBuilder();
        menuPrincipal.setTitle("Maze Bank");
        menuPrincipal.setDescription("Escoja una opcion");
        menuPrincipal.addAction("Crear nuevo Cliente", new Runnable() {
            @Override
            public void run() {
                System.out.println("Crear  Usuario");
                formCliente(null);
            }
        });

        menuPrincipal.addAction("Consultar Cliente", new Runnable() {
            @Override
            public void run() {
                System.out.println("Consultar Cliente");
            }
        });
        menuPrincipal.addAction("Reintegro en Cuenta", new Runnable() {
            @Override
            public void run() {
                System.out.println("Reintegro en Cuenta");
            }
        });
        menuPrincipal.addAction("Ingreso en Cuenta", new Runnable() {
            @Override
            public void run() {
                System.out.println("Ingreso en Cuenta");
            }
        });
        menuPrincipal.addAction("Ver lista Clientes", new Runnable() {
            @Override
            public void run() {
                System.out.println("Ver lista Clientes");
            }
        });
        menuPrincipal.addAction("Ver historico de cuenta", new Runnable() {
            @Override
            public void run() {
                System.out.println("Ver historico de cuenta");
                verTablaDatos();
            }
        });

        //this.mainPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
        menuPrincipal.build().showDialog(textGUI);
        menuPrincipal.setCanCancel(true);
        //textGUI.addWindowAndWait(this.window);

    }
    */

}
