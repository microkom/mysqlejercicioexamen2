/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysqlejercicioexamen2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author DAW
 */
public class Empresa {
    
    private String nombre;
    private ArrayList<Pedidos> pedidosList = new ArrayList<Pedidos>();

    public Empresa(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public ArrayList<Pedidos> getPedidosList() {
        return pedidosList;
    }
    
    
    public void dBase() {

        File folderName;
        Conexion login = new Conexion();
        Connection con = null;
        PreparedStatement stmt = null;
        PreparedStatement stmt2 = null;
        ResultSet rs = null;
        ResultSet rs2 = null;

        try {
            con = login.conectar();

            stmt = con.prepareStatement("SELECT c.IdCliente, count(Distinct p.NumPedido) as 'conteoPedido', "
                    + " sum(l.cantidad) as 'sumaCantidad' from clientes c, pedidos p, lineaspedido l"
                    + " where c.idcliente=p.cliente and p.numpedido=l.numpedido group by c.idcliente");
            rs = stmt.executeQuery();
            
            String idClienteString;
            int cantNumPedido;
            int sumaCantidad;
            
            File idCliente;
            Pedidos pedido;
            
            File borrarPedidos = new File("Pedidos");
            wipeFolderContents(borrarPedidos);//Borrar archivos antes de crearlos si existen
            
            String text="";
            while (rs.next()) {
                idClienteString = rs.getString("idcliente")+".txt";
                cantNumPedido = rs.getInt("conteopedido");
                sumaCantidad = rs.getInt("sumacantidad");
                
                idCliente = new File("Pedidos",idClienteString);
                
                pedido = new Pedidos (idClienteString, cantNumPedido, sumaCantidad);
                
                pedidosList.add(pedido);
                
                for(Pedidos obj : pedidosList){
                    text = "Cantidad pedida: " +obj.getConteoPedido() +"\nSuma de pedidos: "+obj.getSumaCantidad()+"\n";
                     writeFile(idCliente, text);
                }
               
            }
            
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            login.desconectar(con);
        }
    }
    public void writeFile(File fileName, String texto) {
        
 
        FileWriter fileToWrite = null;
        BufferedWriter bufferWillWrite = null;

        try {
            //creacion de estructura de escritura
            fileToWrite = new FileWriter(fileName); //true: permite agregar info sin borrar el archivo

            bufferWillWrite = new BufferedWriter(fileToWrite);
            try {
                bufferWillWrite.write(texto + "\n");

            } catch (Exception e) {
                System.out.println(e.getMessage());
            } finally {
                try {
                    if (bufferWillWrite != null) {
                        bufferWillWrite.close();
                    }
                    if (fileToWrite != null) {
                        fileToWrite.close();
                    }
                } catch (Exception er) {
                    System.out.println(er.getMessage());
                }
            }
        } catch (Exception err) {
            System.out.println(err.getMessage());
        }
    }

    public void wipeFolderContents(File dir) {
        //Deletes a folder content

        try {
            String[] entries = dir.list();
            for (String s : entries) {
                File currentFile = new File(dir.getPath(), s);
                currentFile.delete();
            }
        } catch (SecurityException se) {
            System.out.println(se.getMessage());
        }
    }
}
