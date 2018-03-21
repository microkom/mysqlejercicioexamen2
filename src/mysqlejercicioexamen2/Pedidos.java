/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysqlejercicioexamen2;

/**
 *
 * @author DAW
 */
public class Pedidos {

    private String cliente;
    private int conteoPedido;
    private int sumaCantidad;

    public Pedidos(String cliente, int conteoPedido, int sumaCantidad) {
        this.cliente = cliente;
        this.conteoPedido = conteoPedido;
        this.sumaCantidad = sumaCantidad;
    }

    public String getCliente() {
        return cliente;
    }

    public int getConteoPedido() {
        return conteoPedido;
    }

    public int getSumaCantidad() {
        return sumaCantidad;
    }

   
    
    

}
