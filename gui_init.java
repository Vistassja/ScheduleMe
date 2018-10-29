/*
 * File Name: gui_init.java
 * Author:    Keara Leibovitz
 * Purpose:   Creates the initial gui that you see on screen. Runs the calendar gui on submit.
 * Date:      October 26, 2018
 */

import javax.swing.*;
import javax.swing.filechooser.*;
import java.awt.event.*;
import java.awt.Color;
import CalendarInterface.*;

public class gui_init extends JFrame implements ActionListener {
 
 private static final long serialVersionUID = 1L;
 private JLabel lbQuestion1, lbQuestion2, lbExistingFile;
 private JButton btUploadFile, btSubmit;
 private Color lime = new Color(129, 196, 43);
 private Color maroon = new Color(234, 98, 98);
 public String filePath;
 public String returnString;
 public void createView() {
  
  JPanel p = new JPanel();
  getContentPane().add(p);
  
  // Question
  lbQuestion1 = new JLabel("To get started, upload an existing text file");
  p.add(lbQuestion1);
  lbQuestion2 = new JLabel("or submit without one to create a new session.");
  p.add(lbQuestion2);

  // existing file label updates when file selected
  lbExistingFile = new JLabel("Upload a file");
  lbExistingFile.setForeground(maroon);
  lbExistingFile.setSize(100,30);
  p.add(lbExistingFile);
  
  // Upload Existing
  btUploadFile = new JButton("Upload");
  p.add(btUploadFile);
  // Upload file action
  btUploadFile.addActionListener((ActionListener) this);
  
  // Submit all
  btSubmit = new JButton("Submit");
  p.add(btSubmit);
  
  // Submit action
  btSubmit.addActionListener((ActionListener) this);
 }
 
 private void updateFileName(String fileName) {
   lbExistingFile.setForeground(lime);
   lbExistingFile.setText(fileName);
 }
 
 gui_init() {
  createView();
  
  // General defaults
  setTitle("ScheduleMe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setSize(410,160);
        setVisible(true);
    }
 
 // ActionEvent handler
 public void actionPerformed(ActionEvent e) {
  // Upload a file
  if (e.getSource() == btUploadFile) {
   JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
   jfc.setDialogTitle("Select a .json file");
   jfc.setAcceptAllFileFilterUsed(false);
   FileNameExtensionFilter filter = new FileNameExtensionFilter("*.json", "json");
   jfc.addChoosableFileFilter(filter);

   int returnValue = jfc.showOpenDialog(null);
   if (returnValue == JFileChooser.APPROVE_OPTION) {
    System.out.println(jfc.getSelectedFile().getPath());
    System.out.println(jfc.getSelectedFile().getName());
    updateFileName(jfc.getSelectedFile().getName());
   }
  }
  // Submit and run calendar
  if (e.getSource() == btSubmit) {
   setVisible(false);
   CalendarFrame.main();
   CalendarPanel cal = new CalendarPanel();
   boolean [] [] availability = cal.getAvailability();
   backend(availability, "sampleName", getPath());
  }
 }
 
 public String getPath(){
  return filePath; 
 }
 
 public void backend(boolean [][] array, String name, String path)
  {
    convert newCon = new convert();
    returnString = newCon.convertArray(array);
    System.out.println(returnString);
    
    SamsFunctions func = new SamsFunctions(name, returnString, path);
    func.addEntry(name, returnString, path); 
  }

 // main() method
 public static void main(String[] args) {
  SwingUtilities.invokeLater(new Runnable() {
   @Override
   public void run() {
    new gui_init();
   }
  });
 }
}