/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autokeep.auto;

import autokeep.auto.AdminModels.UserModel;
import autokeep.auto.Communication.adminSocket;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Yuval
 */
public class Users extends javax.swing.JFrame {

    /**
     * Creates new form Users
     */
    DefaultTableModel tableModel;
    List<UserModel> userList;

    private void setUserList() {
        this.userList.clear();
        while (!adminSocket.getUsersList().isEmpty()) {
            this.userList.add(adminSocket.getUsersList().poll());
        }
    }

    public Users() {
        this.userList = new ArrayList<>();
        initComponents();
        tableModel = (DefaultTableModel) userTable.getModel();
        if(adminSocket.getUsersList() == null){
            sendAlert("The DB is Empty !");
        }
        else {
            setUserList();
            displayItems();
        
        }
        
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        userTable = new javax.swing.JTable();
        returnButton = new javax.swing.JButton();
        newButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        updateButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        emailText = new javax.swing.JTextField();
        passwordText = new javax.swing.JTextField();
        clearFileds = new javax.swing.JButton();
        refreshButton = new javax.swing.JButton();
        phoneText = new javax.swing.JFormattedTextField();
        firstnameText = new javax.swing.JTextField();
        familyText = new javax.swing.JTextField();
        adminText = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        idSpinner = new javax.swing.JLabel();
        bitrthdateText = new javax.swing.JFormattedTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        userTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Email", "Password", "Date", "First", "Last", "Phone", "Admin"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        userTable.setColumnSelectionAllowed(true);
        userTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                userTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(userTable);
        userTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        returnButton.setText("Return");
        returnButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                returnButtonActionPerformed(evt);
            }
        });

        newButton.setText("New User");
        newButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newButtonActionPerformed(evt);
            }
        });

        deleteButton.setText("Delete");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        updateButton.setText("Update");
        updateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateButtonActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setText("User Menu");

        jLabel2.setText("Email :");

        jLabel4.setText("Password :");

        jLabel6.setText("Birth Date (YYYY-MM-DD) :");

        jLabel8.setText("Family Name :");

        jLabel9.setText("First Name :");

        jLabel10.setText("Phone :");

        jLabel11.setText("Administrator");

        emailText.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                emailTextFocusLost(evt);
            }
        });
        emailText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                emailTextKeyTyped(evt);
            }
        });

        passwordText.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                passwordTextFocusLost(evt);
            }
        });

        clearFileds.setText("Clear Fields");
        clearFileds.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearFiledsActionPerformed(evt);
            }
        });

        refreshButton.setText("Refresh");
        refreshButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                refreshButtonMouseClicked(evt);
            }
        });
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });

        
        phoneText.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                phoneTextFocusLost(evt);
            }
        });

        firstnameText.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                firstnameTextFocusLost(evt);
            }
        });
        firstnameText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                firstnameTextActionPerformed(evt);
            }
        });

        familyText.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                familyTextFocusLost(evt);
            }
        });

        adminText.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "True", "False" }));

        jLabel3.setText("ID :");

        bitrthdateText.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("yyyy-MM-dd"))));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(newButton, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel2)
                    .addComponent(jLabel6)
                    .addComponent(jLabel9)
                    .addComponent(jLabel8)
                    .addComponent(jLabel11)
                    .addComponent(jLabel10)
                    .addComponent(jLabel3))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(updateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(refreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(clearFileds, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(returnButton, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(idSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(305, 305, 305)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(emailText, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(passwordText, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(phoneText, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(firstnameText, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(familyText, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(adminText, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(bitrthdateText, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 721, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(26, 26, 26))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(idSpinner, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(emailText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(passwordText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(bitrthdateText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(firstnameText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(familyText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(phoneText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(adminText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(returnButton)
                    .addComponent(deleteButton)
                    .addComponent(updateButton)
                    .addComponent(newButton)
                    .addComponent(clearFileds)
                    .addComponent(refreshButton))
                .addGap(18, 18, 18))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void returnButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_returnButtonActionPerformed
        Menu adminMenu = new Menu();
        adminMenu.setVisible(true);
        dispose();
    }//GEN-LAST:event_returnButtonActionPerformed

    private void refreshButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_refreshButtonMouseClicked

    }//GEN-LAST:event_refreshButtonMouseClicked

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
        refreshData();
    }//GEN-LAST:event_refreshButtonActionPerformed

    private void userTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userTableMouseClicked
        int row = userTable.getSelectedRow();
        showItem(userList.get(row));
    }//GEN-LAST:event_userTableMouseClicked

    private void newButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newButtonActionPerformed
        if (checkFields()) {
            try {
                adminSocket.getInstance().SendUserData(addItem(),"New");
                refreshData();
            } catch (IOException ex) {
                Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } else {
            sendAlert("Some Filleds are empty!\n\nPlease fill again");
        }

    }//GEN-LAST:event_newButtonActionPerformed

    private void clearFiledsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearFiledsActionPerformed
        clearFields();
    }//GEN-LAST:event_clearFiledsActionPerformed

    private void emailTextKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_emailTextKeyTyped

    }//GEN-LAST:event_emailTextKeyTyped

    private void passwordTextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_passwordTextFocusLost
        if (!passwordText.getText().isEmpty()) {
            if (passwordText.getText().length() < 6 || passwordValidation(passwordText.getText())) {
                sendAlert("Password didn't match patterns!\n\nPlease type again");
                passwordText.setText("");
            }
        }
    }//GEN-LAST:event_passwordTextFocusLost

    private void emailTextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_emailTextFocusLost
        if (!emailText.getText().isEmpty()) {
            if (!validateEmail(emailText.getText())) {
                sendAlert("Email didn't match patterns!\n\nPlease type again");
                emailText.setText("");
            }
        }
    }//GEN-LAST:event_emailTextFocusLost

    private void firstnameTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_firstnameTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_firstnameTextActionPerformed

    private void phoneTextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_phoneTextFocusLost
        if (!phoneText.getText().isEmpty()) {
            if (!phoneText.getText().matches("[0-9+/. ()-]{9,11}")) {
                sendAlert("Phone need only number!\n\nPlease type again");
                phoneText.setText("");
            }
        }
    }//GEN-LAST:event_phoneTextFocusLost

    private void firstnameTextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_firstnameTextFocusLost
        if (!firstnameText.getText().isEmpty()) {
            if (!firstnameText.getText().matches("[A-Za-z]+")) {
                sendAlert("Name cannot contains numbers!\n\nPlease type again");
                firstnameText.setText("");
            }
        }
    }//GEN-LAST:event_firstnameTextFocusLost

    private void familyTextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_familyTextFocusLost
        if (!familyText.getText().isEmpty()) {
            if (!familyText.getText().matches("[A-Za-z]+")) {
                sendAlert("Family cannot contains numbers!\n\nPlease type again");
                familyText.setText("");
            }
        }
    }//GEN-LAST:event_familyTextFocusLost

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
        int row = userTable.getSelectedRow();
        if (row > -1) {
            userList.get(row).setEmailAddress(emailText.getText());
            userList.get(row).setPassword(passwordText.getText());
            userList.get(row).setDateOfBirth(bitrthdateText.getText());
            userList.get(row).setFirstName(firstnameText.getText());
            userList.get(row).setLastName(familyText.getText());
            userList.get(row).setPhoneNumber(phoneText.getText());
            userList.get(row).setIsAdministrator(isAdmin());
            if (checkFields()) {
            try {
                adminSocket.getInstance().SendUserData(userList.get(row),"Update");
                refreshData();
            } catch (IOException ex) {
                Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } else {
            sendAlert("Some Filleds are empty!\n\nPlease fill again");
        }
            clearFields();
        }
    }//GEN-LAST:event_updateButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        int row = userTable.getSelectedRow();
        if (row > -1) {
            try {
                adminSocket.getInstance().SendUserData(userList.get(row),"Delete");
                userList.remove(row);
                refreshData();
            } catch (IOException ex) {
                Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            clearFields();
        }
    }//GEN-LAST:event_deleteButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Users.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Users.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Users.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Users.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Users().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> adminText;
    private javax.swing.JFormattedTextField bitrthdateText;
    private javax.swing.JButton clearFileds;
    private javax.swing.JButton deleteButton;
    private javax.swing.JTextField emailText;
    private javax.swing.JTextField familyText;
    private javax.swing.JTextField firstnameText;
    private javax.swing.JLabel idSpinner;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton newButton;
    private javax.swing.JTextField passwordText;
    private javax.swing.JFormattedTextField phoneText;
    private javax.swing.JButton refreshButton;
    private javax.swing.JButton returnButton;
    private javax.swing.JButton updateButton;
    private javax.swing.JTable userTable;
    // End of variables declaration//GEN-END:variables

    public void showItem(UserModel item) {
        idSpinner.setText(String.valueOf(item.getId()));
        emailText.setText(item.getEmailAddress());
        passwordText.setText(item.getPassword());
        bitrthdateText.setText(item.getDateOfBirth());
        firstnameText.setText(item.getFirstName());
        familyText.setText(item.getLastName());
        phoneText.setText(item.getPhoneNumber());
        adminText.setSelectedIndex(getIsAdmin(item.IsAdministrator()));
    }

    public int getIsAdmin(boolean data) {
        if (!data) {
            return 1;
        }
        return 0;
    }

    public boolean isAdmin() {
        if (adminText.getSelectedIndex() == 0) {
            return true;
        }
        return false;
    }

    public UserModel addItem() {
        return new UserModel(
                emailText.getText(),
                passwordText.getText(),
                bitrthdateText.getText(),
                firstnameText.getText(),
                familyText.getText(),
                phoneText.getText(),
                isAdmin());
    }

    public void clearFields() {
        idSpinner.setText("");
        emailText.setText("");
        bitrthdateText.setText("");
        passwordText.setText("");
        bitrthdateText.setText("");
        firstnameText.setText("");
        familyText.setText("");
        phoneText.setText("");
    }

    private void displayItems() {
        tableModel.setRowCount(0);
        userList.forEach(item -> {
            Object[] rowdata = new Object[]{item.getId(),item.getEmailAddress(), item.getPassword(), item.getDateOfBirth(),
                item.getFirstName(), item.getLastName(), item.getPhoneNumber(), item.IsAdministrator()};
            tableModel.insertRow(tableModel.getRowCount(), rowdata);
        });
    }

    public static boolean validateEmail(String email) {
        final String PATTERN_MAIL
                = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        return email.matches(PATTERN_MAIL);
    }

    public boolean passwordValidation(String passwordEd) {
        String PASSWORD_PATTERN = "^(?=.*[A-z])(?=.*[@_.]).*$";
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(passwordEd);
        if (!passwordEd.matches(".*\\d.*") || !matcher.matches()) {
            return true;
        }
        return false;
    }

    public boolean checkFields() {
        return !(emailText.getText().isEmpty()
                || passwordText.getText().isEmpty()
                || bitrthdateText.getText().isEmpty()
                || firstnameText.getText().isEmpty()
                || familyText.getText().isEmpty()
                || phoneText.getText().isEmpty());
    }

    private void refreshData(){
        try {
            adminSocket.getInstance().SendUsers();
            setUserList();
        } catch (IOException ex) {
            Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
        }
        displayItems();
        clearFields();
    }
    
    private void sendAlert(String msg) {
        JOptionPane.showMessageDialog(null,
                msg,
                "Error",
                JOptionPane.WARNING_MESSAGE);
    }
}