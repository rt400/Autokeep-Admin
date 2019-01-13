/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autokeep.auto.Communication;

import javax.swing.JOptionPane;

/**
 *
 * @author Yuval
 */
public class MessageControl {
    
    private final static MessageControl popupMessage = new MessageControl();

    public MessageControl() {
        
    }
    
    public static MessageControl getInstance() {
        return MessageControl.popupMessage;
    }
    
    public void sendAlert(String msg) {
        JOptionPane.showMessageDialog(null,
                msg,
                "Warning",
                JOptionPane.WARNING_MESSAGE);
    }
    public void sendMSG() {
        if(AdminSocket.getStatusData().equals("ERROR")){
        JOptionPane.showMessageDialog(null,
                AdminSocket.getServerMSG(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
        if(AdminSocket.getStatusData().equals("OK")){
        JOptionPane.showMessageDialog(null,
                AdminSocket.getServerMSG(),
                "Information",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    public void sendError(String msg) {
        JOptionPane.showMessageDialog(null,
                msg,
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }
    
    public void sendInfo(String msg) {
        JOptionPane.showMessageDialog(null,
                msg,
                "Information",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
