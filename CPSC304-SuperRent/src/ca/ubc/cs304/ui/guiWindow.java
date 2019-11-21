package ca.ubc.cs304.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class guiWindow {
    private JPanel panel1;
    private JPanel users;
    private JPanel customerSelection;
    private JButton customerButton;
    private JButton clerkButton;
    private JButton makeReservationButton;
    private JButton vAvailVeh;
    private JComboBox vTypes;
    private JPanel selectVehicles;
    private JComboBox selLocation;
    private JTextField selTime;
    private JButton backButtonSelType;
    private JPanel Reservation;
    private JPanel isCustomer;
    private JButton yesButtonIsCustomer;
    private JButton noButtonIsCustomer;
    private JPanel addCustomer;
    private JTextField nameAddCustomer;
    private JTextField addressAddCustomer;
    private JTextField cellAddCustomer;
    private JTextField licenseAddCustomer;
    private JButton addCustomerButton;
    private JButton backButtonAddCustomer;
    private JButton backButtonIsCustomer;
    private JPanel makeReservation;
    private JComboBox selectTypeMakeReservation;
    private JTextField licenseMakeReservation;
    private JTextField fromDateMakeReservation;
    private JTextField toDateMakeReservation;
    private JButton makeReservationButton1;
    private JButton backButtonMakeReservation;
    private JButton showAvaliableVehiclesButton;
    private JPanel vehicleSelection;
    private JButton backToSelUser;
    private String vehicleType;


    public guiWindow() {
        customerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                switchPanel(customerSelection);
            }
        });
        clerkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //switchPanel(clerkSelection);
            }
        });
        vAvailVeh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                switchPanel(selectVehicles);
            }
        });
        showAvaliableVehiclesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                vehicleType = (String)vTypes.getSelectedItem();
                if(vehicleType.equalsIgnoreCase("None")){
                    vehicleType = null;
                }
                String loc = (String)selLocation.getSelectedItem();
                String time = selTime.getText();
                //format location and time
                //query data with transaction
                //switchPanel(result panel);
            }
        });


        backButtonSelType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                switchPanel(customerSelection);
            }
        });

        makeReservationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                switchPanel(isCustomer);
            }
        });
        noButtonIsCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                switchPanel(addCustomer);
            }
        });
        addCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //uses information of textfields to create new customer tuple
                String name = nameAddCustomer.getText();
                String address = addressAddCustomer.getText();
                String cellphone = cellAddCustomer.getText();
                String driversLicense = licenseAddCustomer.getText();

            }
        });
        backButtonAddCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                switchPanel(isCustomer);
            }
        });
        backButtonIsCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                switchPanel(customerSelection);
            }
        });
        yesButtonIsCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                switchPanel(makeReservation);
            }
        });
        backButtonMakeReservation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                switchPanel(isCustomer);
            }
        });
        backToSelUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                switchPanel(users);
            }
        });
        makeReservationButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String vt = (String)selectTypeMakeReservation.getSelectedItem();
                String dl = licenseMakeReservation.getText();
                String fromDate = fromDateMakeReservation.getText();
                String toDate = toDateMakeReservation.getText();
            }
        });
    }

    public void makeWindow(){
        JFrame frame = new JFrame("SuperRent");
        frame.setContentPane(new guiWindow().panel1);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(400,400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void switchPanel(JPanel panel){
        panel1.removeAll();
        panel1.add(panel);
        panel1.repaint();
        panel1.revalidate();
    }
}
