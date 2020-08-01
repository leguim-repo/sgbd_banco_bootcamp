package com.miguel.seatcode.bootcamp.sgdb.banco.gui;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.gui2.table.Table;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class GuiLanterna {
    public Terminal terminal;
    public Screen screen;
    public BasicWindow window;
    public WindowBasedTextGUI textGUI;

    public Panel mainPanel;

    public GuiLanterna() throws IOException {
        this.terminal = new DefaultTerminalFactory().createTerminal();
        this.screen = new TerminalScreen(terminal);
        this.window = new BasicWindow();
        this.textGUI = new MultiWindowTextGUI(this.screen);
        this.mainPanel = new Panel();
        this.mainPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));

        this.window.setHints(Arrays.asList(Window.Hint.CENTERED));
        this.window.setComponent(mainPanel.withBorder(Borders.singleLine("Main Panel")));

        this.screen.startScreen();
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
                verMenuPrincipal();
            }
        });

        panelTabla.addComponent(new EmptySpace(new TerminalSize(0, 1)));
        panelTabla.addComponent(tabla);
        panelTabla.addComponent(new EmptySpace(new TerminalSize(0, 1)));
        panelTabla.addComponent(btnCerrar);

        this.mainPanel.addComponent(panelTabla);

    }

    public void verMenuPrincipal(){
        this.mainPanel.removeAllComponents();
        //Definicion de lista de opciones de menu

        TerminalSize size = new TerminalSize(35, 10);
        ActionListBox actionListBox = new ActionListBox(size);

        actionListBox.addItem("Crear Usuario", new Runnable() {
            @Override
            public void run() {
                System.out.println("Crear  Usuario");
                formClientes();

            }
        });

        actionListBox.addItem("Consultar Usuario", new Runnable() {
            @Override
            public void run() {
                System.out.println("Consultar Usuario");

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

        actionListBox.addItem("Salir del SGBD", new Runnable() {
            @Override
            public void run() {
                System.out.println("Salir");
                verPopUp("Have a nice day!!","Salir",MessageDialogButton.OK);
                System.exit(0);
            }
        });

        this.mainPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
        this.window.setComponent(this.mainPanel.withBorder(Borders.singleLine("SGBD Maze Bank")));
        this.mainPanel.addComponent(actionListBox);
        textGUI.addWindowAndWait(this.window);

    }

    public void formClientes() {
        this.mainPanel.removeAllComponents();
        this.mainPanel.setLayoutManager(new GridLayout(1));

        Panel panelDatos = new Panel();
        panelDatos.setLayoutManager(new LinearLayout(Direction.VERTICAL));
        panelDatos.setLayoutManager(new GridLayout(2));

        // creamos los elementos del gui necesarios
        panelDatos.addComponent(new Label("Nombre"));
        final TextBox txtNombre = new TextBox();
        panelDatos.addComponent(txtNombre);

        panelDatos.addComponent(new Label("Apellidos"));
        final TextBox txtApellidos =  new TextBox(new TerminalSize(40, 1));
        panelDatos.addComponent(txtApellidos);

        panelDatos.addComponent(new Label("DNI"));
        final TextBox txtDNI = new TextBox();
        panelDatos.addComponent(txtDNI);

        panelDatos.addComponent(new Label("Pais"));
        final TextBox txtPais = new TextBox();
        panelDatos.addComponent(txtPais);


        // botones de opciones
        Button btnCancelar = new Button("Cancelar", new Runnable() {
            @Override
            public void run() {
                // Actions go here
                System.out.println("btnCancelar");
                verMenuPrincipal();
            }
        });
        Button btnCrear = new Button("Crear", new Runnable() {
            @Override
            public void run() {
                // Actions go here
                System.out.println("btnCrear");
            }
        });

        //panel botones inferior
        Panel panelBotones = new Panel();
        panelBotones.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
        panelBotones.setLayoutManager(new GridLayout(2));
        panelBotones.addComponent(btnCancelar);
        panelBotones.addComponent(btnCrear);


        this.mainPanel.addComponent(new EmptySpace(new TerminalSize(0, 1)));
        this.mainPanel.addComponent(panelDatos);
        this.mainPanel.addComponent(new EmptySpace(new TerminalSize(0, 1)));
        this.mainPanel.addComponent(panelBotones);

    }

    public void verPopUp(String mensaje, String titulo, MessageDialogButton botones) {
        new MessageDialogBuilder()
                .setTitle(titulo)
                .setText(mensaje)
                .addButton(botones)
                .build()
                .showDialog(this.textGUI);

    }
}
