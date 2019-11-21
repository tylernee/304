package ca.ubc.cs304.ui;

import ca.ubc.cs304.database.DatabaseConnectionHandler;

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
    private JPanel clerkSelection;
    private JButton rentButtonClerkSelection;
    private JButton returnButtonClerkSelection;
    private JButton generateReportButtonClerkSelection;
    private JButton backButtonClerkSelection;
    private JPanel Rent;
    private JPanel findVehiclesAddRental;
    private JPanel haveReservationRent;
    private JComboBox vehicleTypeAddRental;
    private JTextField fromDateAddRental;
    private JTextField toDateAddRental;
    private JButton findVehiclesButtonAddRental;
    private JButton backButtonAddRental;
    private JPanel addCustomerAddRental;
    private JTextField nameAddRental;
    private JTextField addressAddRental;
    private JTextField cellAddRental;
    private JTextField licenseAddRental;
    private JButton registerAddRental;
    private JButton backRegisterAddRental;
    private JPanel isCustomerRent;
    private JButton noButtonIsCustomerRent;
    private JButton yesButtonIsCustomerRent;
    private JButton backButtonIsCustomerRent;
    private JButton yesButtonHaveReservation;
    private JButton backButtonHaveReservation;
    private JButton noButtonHaveReservation;
    private JPanel listVehiclesRent;
    private JButton backButtonListVehiclesRent;
    private JButton addToRentalButton;
    private JPanel confNoRent;
    private JButton findReservation;
    private JTextField confNoFieldRent;
    private JButton backButtonConfRent;
    private JPanel Return;
    private JTextField rIdReturn;
    private JTextField dateReturn;
    private JTextField odometerReturn;
    private JButton returnVehicleButton;
    private JButton backButtonReturn;
    private JComboBox tankFullReturn;
    private JPanel generateReport;
    private JPanel selectReport;
    private JButton dailyRentalReportButton;
    private JButton dailyReturnReportButton;
    private JButton backButtonGenReport;
    private JPanel dailyRentalReport;
    private JComboBox rentalReportBranch;
    private JButton generateRentalReportButton;
    private JButton backButtonRentalReport;
    private JPanel dailyReturnReport;
    private JButton generateReturnReportButton;
    private JButton backButtonDailyReturn;
    private JComboBox returnReportBranch;
    private JComboBox selectVehicleRent;
    private JPanel creditCardInfo;
    private JTextField cardNoField;
    private JTextField expDateField;
    private JButton makeRentalButtonCreditCard;
    private JButton backButtonCreditCard;
    private String vehicleType;

    private DatabaseConnectionHandler dbHandler = null;

    public guiWindow(final DatabaseConnectionHandler dbHandler) {
        this.dbHandler = dbHandler;
        customerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                switchPanel(customerSelection);
            }
        });
        clerkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                switchPanel(clerkSelection);
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
                //add info into database
                switchPanel(makeReservation);
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
        backButtonClerkSelection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                switchPanel(users);
            }
        });
        rentButtonClerkSelection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                switchPanel(isCustomerRent);
            }
        });
        noButtonIsCustomerRent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                switchPanel(addCustomerAddRental);
            }
        });
        yesButtonIsCustomerRent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                switchPanel(haveReservationRent);
            }
        });
        backButtonIsCustomerRent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                switchPanel(clerkSelection);
            }
        });
        backButtonHaveReservation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                switchPanel(isCustomerRent);
            }
        });
        backButtonAddRental.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                switchPanel(isCustomerRent);
            }
        });
        noButtonHaveReservation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                switchPanel(findVehiclesAddRental);
            }
        });
        backButtonListVehiclesRent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                switchPanel(findVehiclesAddRental);
            }
        });
        findVehiclesButtonAddRental.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //using info, query availiable vehicles and show them
                String vehicleType = (String)vehicleTypeAddRental.getSelectedItem();
                String fromDate = fromDateAddRental.getText();
                String toDate = toDateAddRental.getText();
                //get through vehicle selection transaction
                switchPanel(listVehiclesRent);
            }
        });
        findReservation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String confNo = confNoFieldRent.getText();
                try {
                    int confNum = Integer.parseInt(confNo);
                    dbHandler.handleRent(confNum, "name", 123123, "dec21");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                switchPanel(listVehiclesRent);
            }
        });
        backButtonConfRent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                switchPanel(haveReservationRent);
            }
        });
        yesButtonHaveReservation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                switchPanel(confNoRent);
            }
        });
        returnButtonClerkSelection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                switchPanel(Return);
            }
        });
        backButtonReturn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                switchPanel(clerkSelection);
            }
        });
        returnVehicleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String rid = rIdReturn.getText();
                String date = rIdReturn.getText();
                String odometer = odometerReturn.getText();
                String tankFull = (String)tankFullReturn.getSelectedItem();
                boolean tank;
                if(tankFull.equalsIgnoreCase("Yes")){
                    tank = true;
                }else {
                    tank = false;
                }
                //do querying
                //switchPanel(print results);
            }
        });
        backButtonGenReport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                switchPanel(clerkSelection);
            }
        });
        backButtonRentalReport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                switchPanel(selectReport);
            }
        });
        generateRentalReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String branch = (String)rentalReportBranch.getSelectedItem();
                if (branch.equalsIgnoreCase("All")){
                    //generate report of all branches
                }else{
                    //generate report of selected branch
                }
            }
        });
        generateReportButtonClerkSelection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                switchPanel(selectReport);
            }
        });
        dailyRentalReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                switchPanel(dailyRentalReport);
            }
        });
        dailyReturnReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                switchPanel(dailyReturnReport);
            }
        });
        generateReturnReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String branch = (String)returnReportBranch.getSelectedItem();
                if (branch.equalsIgnoreCase("All")){
                    //generate report of all branches
                }else{
                    //generate report of selected branch
                }
            }
        });
        backButtonDailyReturn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                switchPanel(selectReport);
            }
        });
        backRegisterAddRental.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                switchPanel(isCustomerRent);
            }
        });
        registerAddRental.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //uses information of textfields to create new customer tuple
                String name = nameAddRental.getText();
                String address = addressAddRental.getText();
                String cellphone = cellAddRental.getText();
                String driversLicense = licenseAddRental.getText();
                //add info into database
                switchPanel(findVehiclesAddRental);
            }
        });
        addToRentalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String car = (String)selectVehicleRent.getSelectedItem(); //have id or some identifying feature in the combobox
                //select from vehicles, store it into local variable
                switchPanel(creditCardInfo);
            }
        });
        backButtonCreditCard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                switchPanel(listVehiclesRent);
            }
        });
        makeRentalButtonCreditCard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String cardNo = cardNoField.getText();
                String expDate = expDateField.getText();
                //add these into rent, maybe use global variables to store?
                //switchPanel(the output panel which prints the conf no);
            }
        });
    }

    public void makeWindow(){
        JFrame frame = new JFrame("SuperRent");
        frame.setContentPane(new guiWindow(this.dbHandler).panel1);
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
