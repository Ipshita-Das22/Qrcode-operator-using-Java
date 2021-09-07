package com.codeQr;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;


public class makeQR extends JFrame {

        JLabel read,write, area;
        JTextField rd,wr;
        JButton readbtn,writebtn,clearbtn,closebtn;
        JFileChooser receive,save;
        String openpth,wrtpth;

    makeQR(){
    	
    	setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(750, 750);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("QR-EN-DE-Coder");
    	
    	
        read = new JLabel(" READ ");
        read.setBounds(25, 7, 100, 100);
        read.setFont(new Font("Bahnschrift SemiBold", Font.BOLD, 23));
        read.setForeground(Color.BLACK);

        write = new JLabel(" WRITE ");
        write.setBounds(25, 60, 100, 100);
        write.setFont(new Font("Bahnschrift SemiBold", Font.BOLD, 23));
        write.setForeground(Color.BLACK);

        rd = new JTextField();
        rd.setBounds(130, 33, 400, 40);
        rd.setFont(new Font("Bahnschrift SemiBold", Font.BOLD, 20));
        rd.setForeground(Color.BLACK);
        rd.setBackground(new Color(224, 255, 255));
        rd.setEditable(false);

        wr = new JTextField();
        wr.setBounds(130, 90, 400, 40);
        wr.setFont(new Font("Bahnschrift SemiBold", Font.BOLD, 20));
        wr.setForeground(Color.BLACK);
        wr.setBackground(new Color(224, 255, 255));

        readbtn = new JButton("READ");
        readbtn.setBounds(580, 33, 100, 35);
        readbtn.setFont(new Font("Georgia", Font.BOLD, 15));

        writebtn = new JButton("WRITE");
        writebtn.setBounds(580, 90, 100, 35);
        writebtn.setFont(new Font("Georgia", Font.BOLD, 15));

        area = new JLabel();
        area.setBounds(130, 170, 400, 400);
        area.setBackground(new Color(240, 255, 255));
        area.setForeground(Color.BLACK);
        area.setFont(new Font("Candara", Font.BOLD, 25));
        area.setOpaque(true);

        clearbtn = new JButton("CLEAR");
        clearbtn.setBounds(230, 600, 100, 35);
        clearbtn.setFont(new Font("Georgia", Font.BOLD, 15));

        closebtn = new JButton("CLOSE");
        closebtn.setBounds(350, 600, 100, 35);
        closebtn.setFont(new Font("Georgia", Font.BOLD, 15));


        Container c = this.getContentPane();
        c.setLayout(null);
        c.setBackground(new Color(255, 235, 215));

        c.add(read);
        c.add(write);
        c.add(rd);
        c.add(wr);
        c.add(readbtn);
        c.add(writebtn);
        c.add(area);
        c.add(clearbtn);
        c.add(closebtn);

        readbtn.addActionListener(new ActionListener() {
       
            public void actionPerformed(ActionEvent e) {
                receive = new JFileChooser();
                receive.setAcceptAllFileFilterUsed(false);
                receive.addChoosableFileFilter(new FileNameExtensionFilter("JPEG Files", "jpeg"));
                receive.addChoosableFileFilter(new FileNameExtensionFilter("PNG Files", "png"));
                receive.addChoosableFileFilter(new FileNameExtensionFilter("JPG Files", "jpg"));
                int a = receive.showOpenDialog(null);
                if (a == JFileChooser.APPROVE_OPTION) {
                    openpth = receive.getSelectedFile().getAbsolutePath();
                    try {
                        BufferedImage bf = ImageIO.read(new File(openpth));
                        BinaryBitmap bp = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(bf)));

                        Result rs = new MultiFormatReader().decode(bp);
                        rd.setText(rs.getText());
                        ImageIcon img=new ImageIcon(openpth);
                        area.setIcon(img);

                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    } catch (NotFoundException notFoundException) {
                        notFoundException.printStackTrace();
                    }
                }
            }
        });

        writebtn.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                String checking= wr.getText();
                if (checking.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "NO FIELD SHOULD BE EMPTY", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                else{
                    save=new JFileChooser();
                    save.setAcceptAllFileFilterUsed(false);
                    save.addChoosableFileFilter(new FileNameExtensionFilter("JPEG Files", "jpeg"));
                    save.addChoosableFileFilter(new FileNameExtensionFilter("PNG Files", "png"));
                    save.addChoosableFileFilter(new FileNameExtensionFilter("JPG Files", "jpg"));

                    int ap = save.showSaveDialog(null);
                    if(ap == JFileChooser.APPROVE_OPTION){
                        wrtpth = save.getSelectedFile().getAbsolutePath();
                        try{


                            BitMatrix matrix;
                            matrix = new MultiFormatWriter().encode(wr.getText(), BarcodeFormat.QR_CODE,400,400);
                            MatrixToImageWriter.writeToPath(matrix, "jpeg", Paths.get(wrtpth));
                            wr.setText("");
                            ImageIcon img=new ImageIcon(wrtpth);
                            area.setIcon(img);


                            JOptionPane.showMessageDialog(null, "Created Successfully","Message",JOptionPane.INFORMATION_MESSAGE);


                        }catch(Exception e1){

                            JOptionPane.showMessageDialog(null, "Cannot Be Created","Error",JOptionPane.ERROR_MESSAGE);
                        }

                    }
                    else{
                        wr.setText("");
                    }

                    }
            }
        });

        closebtn.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e){

                System.exit(0);
            }
        });

        clearbtn.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e){

                wr.setText("");
                rd.setText("");
                openpth = "";
                wrtpth="";
                area.setIcon(null);
            }
        });


    }

    public static void main(String[] args) {

        new makeQR();
        
    }
}
