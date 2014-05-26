/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;
import java.io.File;

/**
 *
 * @author Alejandro
 */
public class generaLexer {

    /**
     * @param path
     */
    public static void generarLexer(String path){
        File file=new File(path);
        jflex.Main.generate(file);
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        String path="src\\Model\\pascal.flex";
        generarLexer(path);
    }
}
