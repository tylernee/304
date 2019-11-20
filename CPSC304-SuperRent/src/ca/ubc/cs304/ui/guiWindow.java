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
    private JPanel vehicleSelection;
    private JPanel selectType;
    private JComboBox vTypes;
    private JPanel selectLocation;
    private JComboBox selLocation;
    private JPanel selectTime;
    private JTextField selTime;
    private JButton stButton;
    private JButton backButtonSelType;
    private JButton backButtonSelLoc;
    private JButton backButtonSelTime;
    private String vehicleType;
    private String location;
    private String city;
    private String time;
    private TerminalTransactions t;


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
                switchPanel(vehicleSelection);
            }
        });
        vTypes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JComboBox cb = (JComboBox)actionEvent.getSource();
                vehicleType = (String)cb.getSelectedItem();
                switchPanel(selectLocation);
            }
        });
        selLocation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JComboBox cb = (JComboBox)actionEvent.getSource();
                String locationCity = (String)cb.getSelectedItem();
                //gets locationCity into fields
                switchPanel(selectTime);
            }
        });
        stButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String timeText = selTime.getText();
                //format time to get required information and put in fields
                //call transaction method
                //switch to panel that produces result
                
            }
        });
        backButtonSelType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                switchPanel(customerSelection);
            }
        });
        backButtonSelLoc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                switchPanel(selectType);
            }
        });
        backButtonSelTime.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                switchPanel(selectLocation);
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
