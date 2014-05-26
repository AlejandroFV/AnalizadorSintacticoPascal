/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.IOException;
import Model.Analizador;
import Model.sintaxError;

/**
 *
 * @author Alejandro
 */
public class analizadorControl {
    
    public String parsear(){
        Analizador a = new Analizador();
       
        String texto;
        try {
            a.getTokens();
            texto = a.comienzo();
        } catch (sintaxError | IOException ex) {
            return "ERROR: " + ex.getMessage();
        }
        return texto;
    }
}
    