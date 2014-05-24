/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.*;
import java.util.ArrayList;

/**
 *
 * @author Alejandro
 */
public class Analizador {

    public ArrayList listaTokens;
    public ArrayList listaLexemas;
    public String actualLexer = "";

    public void getTokens() throws IOException, sintaxError {
        Reader reader = new BufferedReader(new FileReader("salida.txt"));
        PascalLexer lexer = new PascalLexer(reader);
        listaTokens = new ArrayList();
        listaLexemas = new ArrayList();

        while (true) {
            Token token = lexer.yylex();
            if (token == null) {
                return;
            }
            switch (token) {
                case ERROR:
                    throw new sintaxError(lexer.yytext() + ": lexema indefinido.");
                default:
                    listaTokens.add(token);
                    listaLexemas.add(lexer.yytext());
                    break;
            }
        }
    }
    
    public void removerSecuencia(){
        listaLexemas.remove(0);
        listaTokens.remove(0);
    }
    
    public void blanco(){
        if(!listaTokens.isEmpty()){
            if(listaTokens.get(0).toString().equals("BLANCO")){
                removerSecuencia();
            }
        }
    }
    
    public String comienzo()throws sintaxError{
        if(!(listaTokens.isEmpty())){
            if (listaTokens.get(0).toString().equals("RESERVADA") && (listaLexemas.get(0).toString().equals("PROGRAM")
                    || listaLexemas.get(0).toString().equals("program"))) {
                    actualLexer = listaLexemas.get(0).toString();
                    removerSecuencia();
                    program();
                }else{
                    actualLexer = listaLexemas.get(0).toString();
                    throw new sintaxError("Error. Se esperaba la instruccion PROGRAM\nSe ha encontrado: " + actualLexer);
                }
            return "Reeeebien tu sentencia. Aye!";
        }
        return "Esta vacío. Escribe algo";
    }
    
    public boolean program()throws sintaxError{
        if (!(listaTokens.isEmpty()) && listaTokens.get(0).toString().equals("BLANCO")) {
            removerSecuencia();
            if (!listaTokens.isEmpty() && listaTokens.get(0).toString().equals("IDENT")){
                actualLexer = listaLexemas.get(0).toString();
                removerSecuencia();
                if(!(listaTokens.isEmpty()) && listaTokens.get(0).toString().equals("SEMI")){
                    actualLexer = listaLexemas.get(0).toString();
                    removerSecuencia();
                    if(!(listaTokens.isEmpty())){
                        block();
                        if(!(listaTokens.isEmpty())&&listaTokens.get(0).toString().equals("DOT")){
                            removerSecuencia();
                            return true;
                        }else{
                            throw new sintaxError("Error, se esperaba un '.'");
                        }
                    }else{
                        return true;
                    }
                }else{
                    throw new sintaxError("Error de sintaxis despues de el identificador " +actualLexer+ ", se esperaba un ;");
                }
            }else{
                throw new sintaxError("Error de sintaxis después de la instrucción \" PROGRAM \".\nNOTA: Se esperaba un identificador");
            }
        }else{
            throw new sintaxError("Error de sintaxis después de la instrucción \" PROGRAM \".\nNOTA: Se esperaba un espacio.");
        }
            
    }

    private boolean block() throws sintaxError{
        blanco();
        if(!listaTokens.isEmpty()){
            labelDeclarationPart();
        }
        if(!listaTokens.isEmpty()){
            constantDefinitionPart();
        }
        if(!listaTokens.isEmpty()){
            typeDefinitionPart();
        }
        if(!listaTokens.isEmpty()){
            variableDeclarationPart();
        }
        return true;
    }
    
    public boolean labelDeclarationPart() throws sintaxError{
        if(listaTokens.isEmpty()){
            return true;
        }else{
            if(listaTokens.get(0).toString().equals("RESERVADA") && listaLexemas.get(0).toString().equals("LABEL")||
               listaLexemas.get(0).toString().equals("label")){
                removerSecuencia();
                label();
                if(!(listaTokens.isEmpty())&&listaTokens.get(0).toString().equals("SEMI")){
                    removerSecuencia();
                }else{
                    throw new sintaxError("Se esperaba un ;");
                }
                return true;
            }else{
                throw new sintaxError("Error, se esperaba la palabra label");
            }
        }
        
    }
    public void constantDefinitionPart(){
        
    }
    public boolean typeDefinitionPart(){
        if(listaTokens.isEmpty()){
            return true;
        }else{
            if(listaTokens.get(0).toString().equals("RESERVADA")&&listaLexemas.get(0).toString().equals("TYPE")||
               listaLexemas.get(0).toString().equals("type")){
                removerSecuencia();
                typeDefinition();
            }
        }
        return true;
    }
    public void variableDeclarationPart(){
        
    }

    private boolean label() throws sintaxError{
        blanco();
        if(!listaTokens.isEmpty()){
            if(listaTokens.get(0).toString().equals("IDENT")){
                removerSecuencia();
                return true;
            }else{
                throw new sintaxError("Se esperaba un identificador");
            }
        }else{
            throw new sintaxError("Se esperaba un identificador y no hay nada");
        }
    }

    private void typeDefinition() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
    
