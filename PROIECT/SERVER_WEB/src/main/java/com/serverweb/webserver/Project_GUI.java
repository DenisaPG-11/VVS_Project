package com.serverweb.webserver;

import com.serverweb.webserver.config.Configuration;
import com.serverweb.webserver.config.ConfigurationManager;
import com.serverweb.webserver.core.ServerListenerThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Project_GUI  extends JFrame {

        private final static Logger LOGGER = LoggerFactory.getLogger(ServerWeb.class);

        JRadioButton jRadioButton1;
        JRadioButton jRadioButton2;
        JRadioButton jRadioButton3;

        JButton jButton;

        ButtonGroup buttonGroup;

        JLabel jLabel;

        public Project_GUI()
        {
            this.setLayout(null);

            jRadioButton1 = new JRadioButton();
            jRadioButton2 = new JRadioButton();
            jRadioButton3 = new JRadioButton();

            jButton = new JButton("Set");

            buttonGroup = new ButtonGroup();

            jLabel = new JLabel("State for the web server: ");

            jRadioButton1.setText("Stopped");

            jRadioButton2.setText("Running");

            jRadioButton3.setText("Maintenance");

            jRadioButton1.setBounds(220, 30, 120, 50);

            jRadioButton2.setBounds(420, 30, 150, 50);

            jRadioButton3.setBounds(620, 30, 150, 50);

            jButton.setBounds(400, 90, 80, 30);

            jLabel.setBounds(20, 30, 350, 50);

            this.add(jRadioButton1);
            this.add(jRadioButton2);
            this.add(jRadioButton3);
            this.add(jButton);
            this.add(jLabel);

            buttonGroup.add(jRadioButton1);
            buttonGroup.add(jRadioButton2);
            buttonGroup.add(jRadioButton3);

            jButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e)
                {
                    String qual = " ";

                    // If condition to check if jRadioButton2 is selected.
                    if (jRadioButton1.isSelected()) {

                        qual = "Web Server is in : Stopped state!";
                    }

                    else if (jRadioButton2.isSelected()) {

                        qual = "Web Server is in : Running state!";

                        //System.out.println("running");

                        //start the server
                        LOGGER.info("Server starting ...");

                        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/web.json");
                        Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();

                        LOGGER.info("Using Port: " + conf.getPort());
                        LOGGER.info("Using Webroot: " + conf.getWebroot() );

                        try {
                            ServerListenerThread serverListenerThread = new ServerListenerThread(conf.getPort(), conf.getWebroot());
                            serverListenerThread.start();
                        } catch (IOException error) {
                            error.printStackTrace();
                        }

                    }
                    else if (jRadioButton3.isSelected()) {

                        qual = "Web Server is in : Maintenance state!";
                    }
                    else {

                        qual = "Please select a state for the server";
                    }
                    JOptionPane.showMessageDialog(Project_GUI.this, qual);
                }
            });
        }
    }

    class WebServer_GUI {
        public static void main(String args[])
        {
            Project_GUI f = new Project_GUI();

            f.setBounds(100, 100, 800, 200);

            f.setTitle("WebServer");

            f.setVisible(true);
        }
}
