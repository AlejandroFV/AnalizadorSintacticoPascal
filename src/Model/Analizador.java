/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import static Model.Token.ERROR;
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
        actualLexer = listaLexemas.get(0).toString();
        listaLexemas.remove(0);
        listaTokens.remove(0);
    }
    
    public void blanco() throws sintaxError{
        if(!listaTokens.isEmpty()){
            if(listaTokens.get(0).toString().equals("BLANCO")){
                removerSecuencia();
            }
        }else{
            throw new sintaxError("Se esperaba un espacio");
        }
    }
    
    public String comienzo()throws sintaxError{
        if(!(listaTokens.isEmpty())){
            if (listaTokens.get(0).toString().equals("RESERVADA") && (listaLexemas.get(0).toString().equals("PROGRAM")
                    || listaLexemas.get(0).toString().equals("program"))) {
                    removerSecuencia();
                    program();
                }else{
                    actualLexer = listaLexemas.get(0).toString();
                    throw new sintaxError("Se esperaba la instruccion PROGRAM\nSe ha encontrado: " + actualLexer);
                }
            return "Reeeebien tu sentencia. Aye!";
        }
        return "Vacío. Escribe algo";
    }
    
    public boolean program()throws sintaxError{
        if (!(listaTokens.isEmpty()) && listaTokens.get(0).toString().equals("BLANCO")) {
            removerSecuencia();
            if (!listaTokens.isEmpty() && listaTokens.get(0).toString().equals("IDENT")){
                removerSecuencia();
                if(!(listaTokens.isEmpty()) && listaTokens.get(0).toString().equals("SEMI")){
                    removerSecuencia();
                    blanco();
                    if(!(listaTokens.isEmpty()) && (listaTokens.get(0).toString().equals("RESERVADA") && (listaLexemas.get(0).toString().equals("BEGIN") || listaLexemas.get(0).toString().equals("begin")))){
                        removerSecuencia();
                        //block();
                        if (!(listaTokens.isEmpty()) && listaTokens.get(0).toString().equals("BLANCO")) {
                            removerSecuencia();
                            if(!(listaTokens.isEmpty()) && (listaTokens.get(0).toString().equals("RESERVADA") && (listaLexemas.get(0).toString().equals("END") || listaLexemas.get(0).toString().equals("end")))){
                                removerSecuencia();
                                if(!(listaTokens.isEmpty()) && listaTokens.get(0).toString().equals("DOT")){
                                    removerSecuencia();
                                    return true;
                                }else{
                                    throw new sintaxError("Se esperaba '.'");
                                }
                            }else{
                                throw new sintaxError("Se esperaba 'END'");
                            }
                        }
                        else{
                            throw new sintaxError("Se esperaba un espacio.");
                        }
                    }else{
                        throw new sintaxError("Se esperaba 'BEGIN'");
                    }
                }else{
                    throw new sintaxError("Despues de " + actualLexer + " se esperaba un ';'");
                }
            }else{
                throw new sintaxError("Se esperaba un identificador");
            }
        }else{
            throw new sintaxError("Se esperaba un espacio despues de PROGRAM.");
        } 
    }

    private boolean block() throws sintaxError{
        blanco();
        if(!listaTokens.isEmpty()){
            if(labelDeclarationPart()){
                if(constantDefinitionPart()){
                    if(typeDefinitionPart()){
                        if(variableDeclarationPart()){
                            
                        }
                    }
                }
            }
        }
        return true;
    }
    /**
     * LE FALTA RECURSIVIDAD
     * @return
     * @throws sintaxError 
     */
    public boolean labelDeclarationPart() throws sintaxError{
        blanco();
        if(!listaTokens.isEmpty()){
            if(listaTokens.get(0).toString().equals("RESERVADA") && (listaLexemas.get(0).toString().equals("LABEL")||
               listaLexemas.get(0).toString().equals("label") ) ){
                removerSecuencia();
                if(unsignedNumber()){
                    if(listaTokens.get(0).toString().equals("SEMI")){
                        removerSecuencia();
                        return true;
                    }else{
                        throw new sintaxError("se esperaba un ; despues de "+actualLexer);
                    }
                }else{
                    return false;
                }
            }else{
                return true;
            }
        }else{
            return false;
        }
    }
    /**
     * LE FALTA RECURSIVIDAD
     * @return
     * @throws sintaxError 
     */
    public boolean constantDefinitionPart() throws sintaxError{
        blanco();
        if(!listaTokens.isEmpty()){
            if(listaTokens.get(0).toString().equals("RESERVADA")&&(listaLexemas.get(0).toString().equals("CONST")||
               listaLexemas.get(0).toString().equals("const"))){
                removerSecuencia();
                if(constantDefinition()){
                    if(listaTokens.get(0).toString().equals("SEMI")){
                        return true;
                    }else{
                        throw new sintaxError("se esperaba un ; despues de "+actualLexer);
                    }
                }else{
                    return false;
                }
            }else{
                return true;
            }
        }else{
            return true;
        }
    }
    /**
     * LE FALTA SU RECURSIVIDAD
     * @return
     * @throws sintaxError 
     */
    public boolean typeDefinitionPart()throws sintaxError{
        blanco();
        if(!listaTokens.isEmpty()){
            if(listaTokens.get(0).toString().equals("RESERVADA")&&(listaLexemas.get(0).toString().equals("type")||
               listaLexemas.get(0).toString().equals("TYPE"))){
                removerSecuencia();
                if(typeDefinition()){
                    if(listaTokens.get(0).toString().equals("RESERVADA")&&listaLexemas.get(0).toString().equals(";")){
                        removerSecuencia();
                        return true;
                    }else{
                        throw new sintaxError("se esperaba un ; despues de "+actualLexer);
                    }
                }else{
                    return false;
                }
            }else{
                return true;
            }
        }else{
            return true;
        }
    }
    /**
     * LE FALTA RECURSIVIDAD
     * @return
     * @throws sintaxError 
     */
    public boolean variableDeclarationPart()throws sintaxError{
        blanco();
        if(!listaTokens.isEmpty()){
            if(listaTokens.get(0).toString().equals("RESERVADA")&&(listaLexemas.get(0).toString().equals("VAR")||
               listaLexemas.get(0).toString().equals("var"))){
                removerSecuencia();
                if(variableDeclaration()){
                    if(listaTokens.get(0).toString().equals("OP")&&listaLexemas.get(0).toString().equals(";")){
                        removerSecuencia();
                        return true;
                    }else{
                        throw new sintaxError("se esperaba un ; despues de "+actualLexer);
                    }
                }else{
                    return false;
                }
            }else{
                return true;
            }
        }else{
            return true;
        }
    } 
    /**
     * 
     * @return
     * @throws sintaxError 
     */
    private boolean constantDefinition() throws sintaxError{
        blanco();
        if(!(listaTokens.isEmpty())){
            if(identifier()){
                if(listaTokens.get(0).toString().equals("OP")&&listaLexemas.get(0).toString().equals("=")){
                    removerSecuencia();
                    return constant();
                }else{
                    throw new sintaxError("se esperaba el operador = despues de "+actualLexer);
                }
            }else{
                return false;
            }
        }else{
            return false;
        }  
    }
    /**
     * 
     * @return
     * @throws sintaxError 
     */
    private boolean typeDefinition() throws sintaxError {
        blanco();
        if(!listaTokens.isEmpty()){
            if(identifier()){
                if(listaTokens.get(0).toString().equals("OP")&&listaLexemas.get(0).toString().equals("=")){
                    removerSecuencia();
                    return type();
                }else{
                    throw new sintaxError("se esperaba eloperador = despues de "+actualLexer);
                }
            }else{
                return false;
            }
        }else{
            return false;
        }       
    }
    /**
 * 
 * @return
 * @throws sintaxError 
 */
    private boolean variableDeclaration() throws sintaxError {
        blanco();
        if(!listaTokens.isEmpty()){
            if(identifier()){
                if(listaTokens.get(0).toString().equals("OP")&&listaLexemas.get(0).toString().equals(",")){
                    removerSecuencia();
                    return variableDeclaration();
                }else{
                    if(listaTokens.get(0).toString().equals("OP")&&listaLexemas.get(0).toString().equals(":")){
                        removerSecuencia();
                        return type();
                    }else{
                        throw new sintaxError("se esperaban : despues de "+actualLexer);
                    }
                }
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
    //LE FALTA
    private boolean constant() throws sintaxError{
        blanco();
        if(!listaTokens.isEmpty()){
            if(listaTokens.get(0).toString().equals("OP")&&(listaLexemas.get(0).toString().equals("+")||
               listaTokens.get(0).toString().equals("-"))){
                removerSecuencia();
               if(constantIdentifier()){
                   return true;
               }else{
                    return unsignedNumber();
               }
            }else{
                return false;
                //lo que falta 'lol'
            }
        }else{
            return false;
        }
            
    } 
    /**
     * 
     * @return
     * @throws sintaxError 
     */
    public boolean constantIdentifier() throws sintaxError{
        blanco();
        if(!listaTokens.isEmpty()){  
            if(listaTokens.get(0).toString().equals("IDENT")){
                removerSecuencia();
                return true;
            }else{
                throw new sintaxError("se esperaba una constante despues de "+actualLexer);
            }
        }else{
            return false;
        }
    }
    /**
     * 
     * @return
     * @throws sintaxError 
     */
    public boolean unsignedNumber() throws sintaxError{
        blanco();
        if(!listaTokens.isEmpty()){
            if(listaTokens.get(0).toString().equals("NUMERO")){
                removerSecuencia();
                return true;
            }else{
               throw new sintaxError("se esperaba un numero en despues de "+actualLexer); 
            }
        }else{
            return false;
        }
    }
    //LE FALTA
    public boolean string() throws sintaxError{
        blanco();
        if(!listaTokens.isEmpty()){
            if(listaTokens.get(0).toString().equals("CADENA")){
                removerSecuencia();
                return true;
            }else{
                throw new sintaxError("se espraba una cadena despues de "+actualLexer);
            }
        }else{
            return false;
        }
    }
    /**
     * 
     * @return
     * @throws sintaxError 
     */
    public boolean sign() throws sintaxError{
        blanco();
        if(!listaTokens.isEmpty()){
            if(listaTokens.get(0).toString().equals("OP")&&listaLexemas.get(0).toString().equals("+")||
               listaLexemas.get(0).toString().equals("-")){
                removerSecuencia();
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
    /**
     * 
     * @return
     * @throws sintaxError 
     */
    public boolean type() throws sintaxError{
        blanco();
        if(!listaTokens.isEmpty()){
            if(simpleType()){
                return true;
            }else{
                if(structuredType()){
                    return true;
                }else{
                    return identifier();
                }
            }
        }else{
            return false;
        }     
    }
    /**
     * 
     * @return
     * @throws sintaxError 
     */
    public boolean simpleType() throws sintaxError{
        blanco();
        if(!listaTokens.isEmpty()){
            if(scalarType()){
                return true;
            }else{
                if(subrangeType()){
                    return true;
                }else{
                    return identifier();
                }
            }
        }else{
            return false;
        }
    }
    /**
     * 
     * @return
     * @throws sintaxError 
     */
    public boolean structuredType() throws sintaxError{
        blanco();
        if(listaTokens.isEmpty()){
            if(arrayType()){
                return true;
            }else{
                if(recordType()){
                    return true;
                }else{
                    if(setType()){
                        return true;
                    }else{
                        return fileType();
                    }
                }
            }
        }else{
            return false;
        }
    }
    /**
     * 
     * @return
     * @throws sintaxError 
     */
    public boolean fileType() throws sintaxError{
        blanco();
        if(!listaTokens.isEmpty()){
            if(listaTokens.get(0).toString().equals("RESERVADA")&&
              (listaLexemas.get(0).toString().equals("file")||listaLexemas.get(0).toString().equals("FILE"))){
                removerSecuencia();
                blanco();
                if(listaTokens.get(0).toString().equals("RESERVADA")&&
                (listaLexemas.get(0).toString().equals("of")||listaLexemas.get(0).toString().equals("OF"))){
                    removerSecuencia();
                    blanco();
                    return type();
                }else{
                    throw new sintaxError("se esperaba la palabra of despues de "+actualLexer);
                }
            }else{
                throw new sintaxError("se esperaba la palabra file of despues de "+actualLexer);
            }
        }else{
            return false;
        }
    }
    /**
     * 
     * @return
     * @throws sintaxError 
     */
    public boolean setType() throws sintaxError{
        blanco();
        if(!listaTokens.isEmpty()){
            if(listaTokens.get(0).toString().equals("RESERVADA")&&
              (listaLexemas.get(0).toString().equals("set")||listaLexemas.get(0).toString().equals("SET"))){
                removerSecuencia();
                blanco();
                if(listaTokens.get(0).toString().equals("RESERVADA")&&
                (listaLexemas.get(0).toString().equals("of")||listaLexemas.get(0).toString().equals("OF"))){
                    removerSecuencia();
                    blanco();
                    return simpleType();
                }else{
                    throw new sintaxError("se esperaba la palabra of despues de "+actualLexer);
                }
            }else{
                throw new sintaxError("se esperaba la palabra set of despues de "+actualLexer);
            }
        }else{
            return false;
        }
    }
    /**
     * 
     * @return
     * @throws sintaxError 
     */
    public boolean arrayType() throws sintaxError{
        blanco();
        if(!listaTokens.isEmpty()){
            
            if(listaTokens.get(0).toString().equals("RESERVADA")&&
              (listaLexemas.get(0).toString().equals("array")||listaLexemas.get(0).toString().equals("ARRAY"))){
                removerSecuencia();
                if(listaTokens.get(0).toString().equals("OP")&&(listaLexemas.get(0).toString().equals("["))){
                    removerSecuencia();
                    if(simpleType()){
                        if(listaTokens.get(0).toString().equals("OP")&&listaLexemas.get(0).toString().equals(",")){
                            removerSecuencia();
                            if(simpleType()){
                                if(listaTokens.get(0).toString().equals("OP")&&(listaLexemas.get(0).toString().equals("]"))){
                                    removerSecuencia();
                                    if(listaTokens.get(0).toString().equals("RESERVADA")&&
                                      (listaLexemas.get(0).toString().equals("OF")||listaLexemas.get(0).toString().equals("of"))){
                                        removerSecuencia();
                                        return type();
                                    }else{
                                        throw new sintaxError("se esperaba la palabra of y se ha encontrado "+actualLexer);
                                    }
                                }else{
                                    throw new sintaxError("se esperaba un ] despues de "+actualLexer);
                                }
                            }else{
                                throw new sintaxError("se esperaba un indice despues de "+actualLexer);
                            }
                        }else{
                            if(listaTokens.get(0).toString().equals("OP")&&(listaLexemas.get(0).toString().equals("]"))){
                                removerSecuencia();
                                if(listaTokens.get(0).toString().equals("RESERVADA")&&
                                  (listaLexemas.get(0).toString().equals("OF")||listaLexemas.get(0).toString().equals("of"))){
                                    removerSecuencia();
                                    return type();
                                }else{
                                    throw new sintaxError("se esperaba un indice despues de "+actualLexer);
                                }
                            }else{
                               throw new sintaxError("se esperaba un ] despues de "+actualLexer); 
                            }
                        }
                    }else{
                        throw new sintaxError("se esperaba un indice despues de "+actualLexer);
                    }  
                }else{
                    throw new sintaxError("se esperaba un [ despues de "+actualLexer);
                }
            }else{
                throw new sintaxError("se esperaba la palabra reservada array despues de "+actualLexer);
            }
        }else{
            return false;
        }
    }
    /**
     * 
     * @return
     * @throws sintaxError 
     */
    public boolean recordType() throws sintaxError{
        blanco();
        if(!listaTokens.isEmpty()){
            if(listaTokens.get(0).toString().equals("RESERVADA")&&
              (listaLexemas.get(0).toString().equals("record")||listaLexemas.get(0).toString().equals("RECORD"))){
                removerSecuencia();
                if(fieldList()){
                    if(listaTokens.get(0).toString().equals("RESERVADA")&&
                    (listaLexemas.get(0).toString().equals("end")||listaLexemas.get(0).toString().equals("END"))){
                        removerSecuencia();
                        return true;
                    }else{
                        throw new sintaxError("se esperaba la palabra end despues de "+actualLexer);
                    }
                }else{
                    return false;
                }
            }else{
                throw new sintaxError("se esperaba la palabra record despues de "+actualLexer);
            }
        }else{
            return false;
        }
    }
    /**
     * 
     * @return
     * @throws sintaxError 
     */
    public boolean fieldList() throws sintaxError{
        blanco();
        if(!listaTokens.isEmpty()){
            return fixedPart();
        }else{
            return false;
        }
    }
    /**
     * 
     * @return
     * @throws sintaxError 
     */
    public boolean fixedPart() throws sintaxError{
        blanco();
        if(!listaTokens.isEmpty()){
            if(recordSection()){
                if(listaTokens.get(0).toString().equals("SEMI")){
                    removerSecuencia();
                    return fixedPart();
                }else{
                    throw new sintaxError("se esperaba ; despues de "+actualLexer);
                }
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
    /**
     * 
     * @return
     * @throws sintaxError 
     */
    public boolean recordSection() throws sintaxError{
        blanco();
        if(!listaTokens.isEmpty()){
            if(identifier()){
                if(listaTokens.get(0).toString().equals("OP")&&listaLexemas.get(0).toString().equals(",")){
                    removerSecuencia();
                    if(identifier()){
                        if(listaTokens.get(0).toString().equals("OP")&&listaLexemas.get(0).toString().equals(":")){
                            removerSecuencia();
                            return type();
                        }else{
                            throw new sintaxError("se esperaba un : despues de "+actualLexer);
                        }
                    }else{
                        return false;
                    }
                }else{
                    if(listaTokens.get(0).toString().equals("OP")&&listaLexemas.get(0).toString().equals(":")){
                        removerSecuencia();
                        return type();
                    }else{
                        throw new sintaxError("se esperaba : despues de "+actualLexer);
                    }
                }
            }else{
                return false;
            }
        }else{
            return true;
        }
    }  
    /**
     * 
     * @return
     * @throws sintaxError 
     */
    public boolean scalarType() throws sintaxError{
       blanco(); 
       if(identifier()){
          if(!listaTokens.isEmpty()){
              if(listaTokens.get(0).toString().equals("OP")&&listaLexemas.get(0).toString().equals(",")){
                  removerSecuencia();
                  identifier();
              }
          }else{
              return true;
          }
       }return false;
    }
    /**
     * 
     * @return
     * @throws sintaxError 
     */
    public boolean subrangeType() throws sintaxError{
        blanco();
        if(constant()){
            if(!listaTokens.isEmpty()){
                actualLexer = listaLexemas.get(0).toString();
                if(listaTokens.get(0).toString().equals("OP")&&listaLexemas.get(0).toString().equals("..")){
                    removerSecuencia();
                    if (constant()) {
                        return true;
                    }else{
                        throw new sintaxError("se esperaba una constante despues de ..");
                    }
                }else{
                    throw new sintaxError("se esperaban .. despues de "+actualLexer);
                }
            }else{
                return false;
            }
        }else{
            return false;
        }
    } 
    /**
     * 
     * @return
     * @throws sintaxError 
     */
    public boolean identifier()throws sintaxError{
        blanco();
        if(!listaTokens.isEmpty()){
            actualLexer = listaLexemas.get(0).toString();
            if(listaTokens.get(0).toString().equals("IDENT")){
                removerSecuencia();
                return true;
            }else{
                throw new sintaxError("Se esperaba un identificador, se ha encontrado "+actualLexer);
            }
        }else{
            return false;
        }
    }
    /**
     * 
     * @return
     * @throws sintaxError 
     */
    public boolean procedureAndFunctionDeclarationPart() throws sintaxError{
        blanco();
        if(!listaTokens.isEmpty()){
            if(procedureOrFunctionDeclaration()){
                if(listaTokens.get(0).toString().equals("SEMI")){
                    removerSecuencia();
                    return true;
                }else{
                    throw new sintaxError("se esperaba un ; despues de "+actualLexer);
                }
            }else{
                return true;
            }
        }else{
            return true;
        }
    }
    /**
     * 
     * @return
     * @throws sintaxError 
     */
    public boolean procedureOrFunctionDeclaration() throws sintaxError{
        blanco();
        if(!listaTokens.isEmpty()){
            if(procedureDeclaration()){
                return true;
            }else{
                return functionDeclaration();
            }
        }else{
            return false;
        }
    }
    /**
     * 
     * @return
     * @throws sintaxError 
     */
    public boolean procedureDeclaration() throws sintaxError{
        blanco();
        if(!listaTokens.isEmpty()){
            if(procedureHeading()){
                return block();
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
    /**
     * FALTA RECURSIVIDAD
     * @return 
     * @throws sintaxError 
     */
    public boolean procedureHeading() throws sintaxError{
        blanco();
        if(!listaTokens.isEmpty()){
            if(listaTokens.get(0).toString().equals("RESERVADA")&&(listaLexemas.get(0).toString().equals("procedure")||
               listaLexemas.get(0).toString().equals("PROCEDURE"))){
                removerSecuencia();
                if(identifier()){
                    if(listaTokens.get(0).toString().equals("SEMI")){
                        removerSecuencia();
                        return true;
                    }else{
                        if(listaTokens.get(0).toString().equals("OP")&&listaLexemas.get(0).toString().equals("(")){
                            removerSecuencia();
                            if(formalParameterSection()){
                                if(listaTokens.get(0).toString().equals("OP")&&listaLexemas.get(0).toString().equals(")")){
                                    removerSecuencia();
                                    if(listaTokens.get(0).toString().equals("SEMI")){
                                        removerSecuencia();
                                        return true;
                                    }else{
                                        throw new sintaxError("se esperaba un ; despues de "+actualLexer);
                                    }
                                }else{
                                    throw new sintaxError("se esperaba un ) despues de "+actualLexer);
                                }
                            }else{
                                return false;
                            }
                        }else{
                            return false;
                        }
                    }
                }else{
                    return false;
                }
            }else{
                throw new sintaxError("se esperaba la palabra procedure despues de "+actualLexer);
            }
        }else{
            return false;
        }
    }
    /**
     * CHECAR SU RECURSIVIDAD
     * @return
     * @throws sintaxError 
     */
    public boolean formalParameterSection() throws sintaxError{
        blanco();
        if(!listaTokens.isEmpty()){
            if(parameterGroup()){
                return true;
            }else{
                if(listaTokens.get(0).toString().equals("RESERVADA")&&(listaLexemas.get(0).toString().equals("var")||
                listaLexemas.get(0).toString().equals("VAR"))){
                    removerSecuencia();
                    return parameterGroup();
                }else{
                    if(listaTokens.get(0).toString().equals("RESERVADA")&&(listaLexemas.get(0).toString().equals("function")||
                    listaLexemas.get(0).toString().equals("FUNCTION"))){
                        removerSecuencia();
                        return parameterGroup();
                    }else{
                        if(listaTokens.get(0).toString().equals("RESERVADA")&&(listaLexemas.get(0).toString().equals("procedure")||
                        listaLexemas.get(0).toString().equals("PROCEDURE"))){
                            removerSecuencia();
                            if(identifier()){
                                if(listaTokens.get(0).toString().equals("OP")&&listaLexemas.get(0).toString().equals(",")){
                                    removerSecuencia();
                                    return identifier();
                                }else{
                                    return true;
                                }
                            }else{
                                return false;
                            }
                        }else{
                            return false;
                        }
                    }
                }
            }
        }else{
            return false;
        }
    }
    /**
     * CHECAR LA RECURSIVIDAD
     * @return
     * @throws sintaxError 
     */
    public boolean parameterGroup() throws sintaxError{
        blanco();
        if(!listaTokens.isEmpty()){
            if(identifier()){
                if(listaTokens.get(0).toString().equals("OP")&&listaLexemas.get(0).toString().equals(",")){
                    removerSecuencia();
                    parameterGroup();
                    return true;
                }else{
                    if(listaTokens.get(0).toString().equals("OP")&&listaLexemas.get(0).toString().equals(":")){
                        removerSecuencia();
                        return identifier();
                    }else{
                        throw new sintaxError("se esperaban : despues de "+actualLexer);
                    }
                }
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
    /**
     * 
     * @return
     * @throws sintaxError 
     */
    public boolean functionDeclaration() throws sintaxError{
        blanco();
        if(!listaTokens.isEmpty()){
            if(functionHeading()){
                return block();
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
    /**
     * FALTO RECURSIVIDAD
     * @return 
     * @throws sintaxError 
     */
    public boolean functionHeading() throws sintaxError{
        blanco();
        if(!listaTokens.isEmpty()){
            if(listaTokens.get(0).toString().equals("RESERVADA")&&(listaLexemas.get(0).toString().equals("FUNCTION")||
               listaLexemas.get(0).toString().equals("function"))){
                removerSecuencia();
                if(identifier()){
                    if(listaTokens.get(0).toString().equals("OP")&&listaLexemas.get(0).toString().equals(":")){
                        removerSecuencia();
                        if(identifier()){
                            if(listaTokens.get(0).toString().equals("SEMI")){
                                removerSecuencia();
                                return true;
                            }else{
                                throw new sintaxError("se esperaba un ; despues de "+actualLexer);
                            }
                        }else{
                            return false;
                        }
                    }else{
                        if(listaTokens.get(0).toString().equals("OP")&&listaLexemas.get(0).toString().equals("(")){
                            removerSecuencia();
                            if(formalParameterSection()){
                                if(listaTokens.get(0).toString().equals("OP")&&listaLexemas.get(0).toString().equals(")")){
                                    removerSecuencia();
                                    if(listaTokens.get(0).toString().equals("OP")&&listaLexemas.get(0).toString().equals(":")){
                                        removerSecuencia();
                                        if(identifier()){
                                            if(listaTokens.get(0).toString().equals("SEMI")){
                                                removerSecuencia();
                                                return true;
                                            }else{
                                                throw new sintaxError("se esperaba un ; despues de "+actualLexer);
                                            }
                                        }else{
                                            return false;
                                        }
                                    }else{
                                        throw new sintaxError("se esperaban : despues de "+actualLexer);
                                    }
                                }else{
                                    throw new sintaxError("se esperaba un ( despues de "+actualLexer);
                                }
                            }else{
                                return false;
                            }
                        }else{
                            return false;
                        }
                    }
                }else{
                    return false;
                }
            }else{
                throw new sintaxError("se esperaba la palabra function despues de "+actualLexer);
            }
        }else{
            return false;
        }
    }
    /**
     * 
     * @return
     * @throws sintaxError 
     */
    public boolean statement() throws sintaxError{
        blanco();
        if(!listaTokens.isEmpty()){
            if(unlabelledStatement()){
                return true;
            }else{
                if(unsignedNumber()){
                    if(listaTokens.get(0).toString().equals("OP")&&listaLexemas.get(0).toString().equals(":")){
                        removerSecuencia();
                        return unlabelledStatement();
                    }else{
                        throw new sintaxError("se esperaban : despues de "+actualLexer);
                    }
                }else{
                    return false;
                }
            }
        }else{
            return false;
        }
    }
    /**
     * 
     * @return
     * @throws sintaxError 
     */
    public boolean unlabelledStatement() throws sintaxError{
        blanco();
        if(!listaTokens.isEmpty()){
            return simpleStatement()||structuredStatement();
        }else{
            return false;
        }
    }
    /**
     * 
     * @return
     * @throws sintaxError 
     */
    public boolean simpleStatement() throws sintaxError{
        blanco();
        if(!listaTokens.isEmpty()){
            return assignmentStatement()||procedureStatement()||goToStatement();
        }else{
            return true;
        }
    }
    /**
     * 
     * @return
     * @throws sintaxError 
     */
    public boolean assignmentStatement() throws sintaxError{
        blanco();
        if(!listaTokens.isEmpty()){
            if(variable()){
                if(listaTokens.get(0).toString().equals("OP")&&listaLexemas.get(0).toString().equals(":=")){
                    removerSecuencia();
                    return expression();
                }else{
                    throw new sintaxError("se esperaba el operador := despues de "+actualLexer);
                }
            }else{
                if(identifier()){
                    if(listaTokens.get(0).toString().equals("OP")&&listaLexemas.get(0).toString().equals(":=")){
                        return expression();
                    }else{
                        throw new sintaxError("se esperaba el operador := despues de "+actualLexer);
                    }
                }else{
                    return false;
                }
            }
        }else{
            return false;
        }
    }
    /**
     * 
     * @return
     * @throws sintaxError 
     */
    public boolean variable() throws sintaxError{
        blanco();
        if(!listaTokens.isEmpty()){
            return identifier()||componentVariable()||variable();
        }else{
            return false;
        }
    }
    /**
     * 
     * @return
     * @throws sintaxError 
     */
    public boolean componentVariable() throws sintaxError{
        blanco();
        if(!listaTokens.isEmpty()){
            return indexedVariable()||fieldDesignator()||variable();
        }else{
            return false;
        }
    }
    /**
     * 
     * @return
     * @throws sintaxError 
     */
    public boolean indexedVariable() throws sintaxError{
        blanco();
        if(!listaTokens.isEmpty()){
            if(variable()){
                if(listaTokens.get(0).toString().equals("OP")&&listaTokens.get(0).toString().equals("[")){
                    removerSecuencia();
                    if(expression()){
                        if(listaTokens.get(0).toString().equals("OP")&&listaTokens.get(0).toString().equals("]")){
                            removerSecuencia();
                            return true;
                        }else{
                            throw new sintaxError("se esperaba un ] despues de "+actualLexer);
                        }
                    }else{
                        return false;
                    }
                }else{
                    throw new sintaxError("se esperaba un [ despues de "+actualLexer);
                }
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
    /**
     * 
     * @return
     * @throws sintaxError 
     */
    public boolean expression() throws sintaxError{
        blanco();
        if(!listaTokens.isEmpty()){
            if(simpleExpression()){
                if(relationalOperator()){
                    return expression();
                }else{
                    //puede que despues de la expresion no venga nada
                    return false;
                }
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
    /**
     * 
     * @return
     * @throws sintaxError 
     */
    public boolean simpleExpression() throws sintaxError{
        blanco();
        if(!listaTokens.isEmpty()){
            if(term()){
                return true;
            }else{
                if(sign()){
                    return term();
                }else{
                    if(simpleExpression()){
                        if(addingOperator()){
                            return term();
                        }else{
                            return false;
                        }
                    }else{
                        return false;
                    }
                }
            }
        }else{
            return false;
        }
    }
    /**
     * 
     * @return
     * @throws sintaxError 
     */
    public boolean term() throws sintaxError{
        blanco();
        if(!listaTokens.isEmpty()){
            if(factor()){
                return true;
            }else{
                if(term()){
                    if(multiplyingOperator()){
                        return factor();
                    }else{
                        return false;
                    }
                }else{
                    return false;
                }
            }
        }else{
            return false;
        }
    }
    /**
     * 
     * @return
     * @throws sintaxError 
     */
    public boolean factor() throws sintaxError{
        blanco();
        if(!listaTokens.isEmpty()){
            if(variable()){
                return true;
            }else{
                if(unsignedConstant()){
                    return true;
                }else{
                    if(listaTokens.get(0).toString().equals("OP")&&listaLexemas.get(0).toString().equals("(")){
                        removerSecuencia();
                        if(expression()){
                            if(listaTokens.get(0).toString().equals("OP")&&listaLexemas.get(0).toString().equals(")")){
                                removerSecuencia();
                                return true;
                            }else{
                                throw new sintaxError("se esperaba un ) despues de "+actualLexer);
                            }
                        }else{
                            return false;
                        }
                    }else{
                        if(functionDesignator()){
                            return true;
                        }else{
                            if(set()){
                                return true;
                            }else{
                                if(listaTokens.get(0).toString().equals("RESERVADA")&& ( listaLexemas.get(0).toString().equals("not")||
                                  listaLexemas.get(0).toString().equals("NOT") ) ){
                                    return factor();
                                }else{
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }else{
            return false;
        }
    }
    /**
     * FALTO RECURSIVIDAD
     * @return
     * @throws sintaxError 
     */
    public boolean compundStatement() throws sintaxError{
        blanco();
        if(!listaTokens.isEmpty()){
            if(listaTokens.get(0).toString().equals("RESERVADA")&&(listaLexemas.get(0).toString().equals("begin")||
               listaLexemas.get(0).toString().equals("BEGIN"))){
                removerSecuencia();
                if(statement()){
                    if(listaTokens.get(0).toString().equals("RESERVADA")&&(listaLexemas.get(0).toString().equals("end")||
                    listaLexemas.get(0).toString().equals("END"))){
                        removerSecuencia();
                        if(listaTokens.get(0).toString().equals("SEMI")){
                            removerSecuencia();
                            return true;
                        }else{
                            throw new sintaxError("se esperaba un ; despues de "+actualLexer);
                        }
                    }else{
                        throw new sintaxError("se esperaba la palabra end despues de "+actualLexer);
                    }
                }else{
                    return false;
                }
            }else{
                throw new sintaxError("se esperaba la palabra begin despues de "+actualLexer);
            }
        }else{
            return false;
        }
    }
    /**
     * 
     * @return
     * @throws sintaxError 
     */
    public boolean unsignedConstant() throws sintaxError{
        blanco();
        if(!listaTokens.isEmpty()){
            if(unsignedNumber()){
                return true;
            }else{
                if(string()){
                    return true;
                }else{
                    return identifier();
                }
            }
        }
        return false;
    }
    /**
     * falta recursividad
     * @return
     * @throws sintaxError 
     */
    public boolean functionDesignator() throws sintaxError{
        blanco();
        if(!listaTokens.isEmpty()){
            if(identifier()){
                if(listaTokens.get(0).toString().equals("OP")&&listaLexemas.get(0).toString().equals("(")){
                    removerSecuencia();
                    if(actualParameter()){
                        if(listaTokens.get(0).toString().equals("OP")&&listaLexemas.get(0).toString().equals(")")){
                            removerSecuencia();
                            return true;
                        }else{
                            throw new sintaxError("se esperaba un ) despues de "+actualLexer);
                        }
                    }else{
                        return false;
                    }
                }else{
                    //despues del identificador puede que no valla nada
                    return true;
                }
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
    /**
     * 
     * @return
     * @throws sintaxError 
     */
    public boolean actualParameter() throws sintaxError{
        blanco();
        if(!listaTokens.isEmpty()){
            return expression()||variable()||identifier();
        }else{
            return false;
        }   
    }
    /**
     * 
     * @return
     * @throws sintaxError 
     */
    public boolean set() throws sintaxError {
        blanco();
        if(!listaTokens.isEmpty()){
            if(listaTokens.get(0).toString().equals("OP")&&listaLexemas.get(0).toString().equals("[")){
                if(elementList()){
                    if(listaTokens.get(0).toString().equals("OP")&&listaLexemas.get(0).toString().equals("]")){
                        removerSecuencia();
                        return true;
                    }else{
                       throw new sintaxError("se esperaba un ] despues de "+actualLexer); 
                    }
               }else{
                    return false;
                }
            }else{
                throw new sintaxError("se esperaba un [ despues de "+actualLexer);
            }
        }else{
            return false;
        }
    }
    /**
     * 
     * @return
     * @throws sintaxError 
     */
    public boolean multiplyingOperator() throws sintaxError{
        blanco();
        if(!listaTokens.isEmpty()){
            if(listaTokens.get(0).toString().equals("OP")){
                if(listaLexemas.get(0).toString().equals("*")||
                   listaLexemas.get(0).toString().equals("/")){
                    removerSecuencia();
                   return true;
                }else{
                    return false;
                }
            }else{
                if(listaTokens.get(0).toString().equals("RESERVADA")){
                    if((listaLexemas.get(0).toString().equals("div")||listaLexemas.get(0).toString().equals("DIV"))||
                       (listaLexemas.get(0).toString().equals("mod")||listaLexemas.get(0).toString().equals("MOD"))||
                       (listaLexemas.get(0).toString().equals("and")||listaLexemas.get(0).toString().equals("AND"))){
                       removerSecuencia();
                       return true;
                    }else{
                        return false;
                    }
                }else{
                    return false;
                }
            }
        }else{
            return false;
        }
    }
    /**
     * 
     * @return
     * @throws sintaxError 
     */
    public boolean addingOperator() throws sintaxError {
        blanco();
        if(!listaTokens.isEmpty()){
            if(listaTokens.get(0).toString().equals("OP")){
                if(listaLexemas.get(0).toString().equals("+")||
                   listaLexemas.get(0).toString().equals("-")){
                    removerSecuencia();
                   return true;
                }else{
                    return false;
                }
            }else{
                if(listaTokens.get(0).toString().equals("RESERVADA")){
                    if((listaLexemas.get(0).toString().equals("or")||listaLexemas.get(0).toString().equals("OR"))){
                       removerSecuencia();
                       return true;
                    }else{
                        return false;
                    }
                }else{
                    return false;
                }
            }
        }else{
            return false;
        }
    }
    /**
     * 
     * @return
     * @throws sintaxError 
     */
    public boolean relationalOperator() throws sintaxError{
        blanco();
        if(!listaTokens.isEmpty()){
            if(listaTokens.get(0).toString().equals("OP")){
                if(listaLexemas.get(0).toString().equals("==")||
                   listaLexemas.get(0).toString().equals("<>")||
                   listaLexemas.get(0).toString().equals("<")||
                   listaLexemas.get(0).toString().equals("<=")||
                   listaLexemas.get(0).toString().equals(">=")||
                   listaLexemas.get(0).toString().equals(">")){
                    removerSecuencia();
                   return true;
                }else{
                    return false;
                }
            }else{
                if(listaTokens.get(0).toString().equals("RESERVADA")){
                    if((listaLexemas.get(0).toString().equals("in")||listaLexemas.get(0).toString().equals("IN"))){
                       removerSecuencia();
                       return true;
                    }else{
                        return false;
                    }
                }else{
                    return false;
                }
            }
        }else{
            return false;
        }
    }
    /**
     * 
     * @return
     * @throws sintaxError 
     */
    public boolean fieldDesignator() throws sintaxError {
        blanco();
        if(!listaTokens.isEmpty()){
            if(variable()){
                if(listaTokens.get(0).toString().equals("PUNTO")){
                    removerSecuencia();
                    return identifier();
                }else{
                    throw new sintaxError("se esperaba un punto despues de "+actualLexer);
                }
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
    /**
     * FALTA RECURSIVIDAD
     * @return
     * @throws sintaxError 
     */
    public boolean procedureStatement() throws sintaxError{
        blanco();
        if(!listaTokens.isEmpty()){
            if(identifier()){
                if(listaTokens.get(0).toString().equals("OP")&&listaLexemas.get(0).toString().equals("(")){
                    removerSecuencia();
                    if(actualParameter()){
                        if(listaTokens.get(0).toString().equals("OP")&&listaLexemas.get(0).toString().equals(")")){
                            removerSecuencia();
                            return true;
                        }else{
                            throw new sintaxError("se esperaba un ) despues de "+actualLexer);
                        }
                    }else{
                        return false;
                    }
                }else{
                    //puede que no venga nada mas despues del identifier
                    return true;
                }
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
    /**
     * 
     * @return
     * @throws sintaxError 
     */
    public boolean goToStatement() throws sintaxError{
        blanco();
        if(!listaTokens.isEmpty()){
            if(listaTokens.get(0).toString().equals("RESERVADA")&&( listaLexemas.get(0).toString().equals("goto")||
               listaLexemas.get(0).toString().equals("GOTO") )){
                removerSecuencia();
                return unsignedNumber();
            }else{
                throw new sintaxError("se esperaba la palabra goto despues de "+actualLexer);
            }
        }else{
            return false;
        }
    }
    /**
     * 
     * @return
     * @throws sintaxError 
     */
    public boolean structuredStatement() throws sintaxError{
        blanco();
        if(!listaTokens.isEmpty()){
            return compoundStatement()||conditionalStatement()||repetitiveStatement()||withStatement();
        }else{
            return false;
        }
    }
    /**
     * FALTA RECURSIVIDAD
     * @return
     * @throws sintaxError 
     */
    public boolean compoundStatement() throws sintaxError{
        blanco();
        if(!listaTokens.isEmpty()){
            if(listaTokens.get(0).toString().equals("RESERVADA")&&( listaLexemas.get(0).toString().equals("begin")||
               listaLexemas.get(0).toString().equals("BEGIN") )){
                removerSecuencia();
                if(statement()){
                    if(listaTokens.get(0).toString().equals("RESERVADA")&&( listaLexemas.get(0).toString().equals("begin")||
                       listaLexemas.get(0).toString().equals("BEGIN") )){
                        removerSecuencia();
                        return true;
                    }else{
                        throw new sintaxError("se esperaba la palabra end despues de "+actualLexer);
                    }
                }else{
                    return false;
                }
            }else{
                throw new sintaxError("se esperaba la palabra begin despues de "+actualLexer);
            }
        }else{
            return false;
        }
    }
    /**
     * 
     * @return
     * @throws sintaxError 
     */
    public boolean conditionalStatement() throws sintaxError{
        blanco();
        if(!listaTokens.isEmpty()){
            return ifStatement()||caseStatement();
        }else{
            return false;
        }
    }
    /**
     * 
     * @return
     * @throws sintaxError 
     */
    public boolean ifStatement() throws sintaxError{
        blanco();
        if(!listaTokens.isEmpty()){
            if(listaTokens.get(0).toString().equals("RESERVADA")&&( listaLexemas.get(0).toString().equals("if")||
               listaLexemas.get(0).toString().equals("IF"))){
                removerSecuencia();
                if(expression()){
                    if(listaTokens.get(0).toString().equals("RESERVADA")&&( listaLexemas.get(0).toString().equals("then")||
                       listaLexemas.get(0).toString().equals("THEN"))){
                        removerSecuencia();
                        if(statement()){
                            if(listaTokens.get(0).toString().equals("RESERVADA")&&( listaLexemas.get(0).toString().equals("else")||
                               listaLexemas.get(0).toString().equals("ELSE"))){
                                removerSecuencia();
                                return statement();
                            }else{
                                return true;
                            }
                        }else{
                            return false;
                        }
                    }else{
                        throw new sintaxError("se esperaba la palabra then despues de "+actualLexer);
                    }
                }else{
                    return false;
                }
            }else{
                throw new sintaxError("se esperaba la palabra if despues de "+actualLexer);
            }
        }else{
            return false;
        }
    }
    /**
     * FALTA RECURSIVIDAD
     * @return
     * @throws sintaxError 
     */
    public boolean caseStatement() throws sintaxError{
        blanco();
        if(!listaTokens.isEmpty()){
            if(listaTokens.get(0).toString().equals("RESERVADA")&& (listaLexemas.get(0).toString().equals("case")||
               listaLexemas.get(0).toString().equals("CASE")) ){
                removerSecuencia();
                if(expression()){
                    if(listaTokens.get(0).toString().equals("RESERVADA")&& (listaLexemas.get(0).toString().equals("of")||
                       listaLexemas.get(0).toString().equals("OF"))){
                        removerSecuencia();
                        if(caseListElement()){
                            if(listaTokens.get(0).toString().equals("RESERVADA")&& (listaLexemas.get(0).toString().equals("end")||
                               listaLexemas.get(0).toString().equals("END"))){
                                removerSecuencia();
                                return true;
                            }else{
                                throw new sintaxError("se esperaba la palabra end despues de "+actualLexer);
                            }
                        }else{
                            return false;
                        }
                    }else{
                        throw new sintaxError("se esperaba la palabra of despues de "+actualLexer);
                    }
                }else{
                    return false;
                }
            }else{
                throw new sintaxError("se esperaba la palabra case despues de "+actualLexer);
            }
        }else{
            return false;
        }
    }
    /**
     * 
     * @return
     * @throws sintaxError 
     */
    public boolean caseListElement() throws sintaxError{
        blanco();
        if(!listaTokens.isEmpty()){
            if(caseLabelList()){
                if(listaTokens.get(0).toString().equals("OP")&& listaLexemas.get(0).toString().equals(":")){
                    removerSecuencia();
                    return statement();
                }else{
                    throw new sintaxError("se esperaban : despues de "+actualLexer);
                }
            }else{
                return false;
            }
        }else{
            return true;
        }
    }
    /**
     * 
     * @return
     * @throws sintaxError 
     */
    public boolean caseLabelList() throws sintaxError{
        blanco();
        if(!listaTokens.isEmpty()){
            if(constant()){
                if(listaTokens.get(0).toString().equals("OP")&& listaLexemas.get(0).toString().equals(",")){
                    removerSecuencia();
                    return caseLabelList();
                }else{
                    return true;
                }
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
    /**
     * 
     * @return
     * @throws sintaxError 
     */
    public boolean repetitiveStatement() throws sintaxError{
        blanco();
        if(!listaTokens.isEmpty()){
            return whileStatement()||repeatStatemant()||forStatement();
        }else{
            return false;
        }
    }
    /**
     * 
     * @return
     * @throws sintaxError 
     */
    public boolean whileStatement() throws sintaxError{
        blanco();
        if(!listaTokens.isEmpty()){
            if(listaTokens.get(0).toString().equals("RESERVADA")&&(listaLexemas.get(0).toString().equals("while")||
               listaLexemas.get(0).toString().equals("WHILE"))){
                removerSecuencia();
                if(expression()){
                    if(listaTokens.get(0).toString().equals("RESERVADA")&&(listaLexemas.get(0).toString().equals("do")||
                       listaLexemas.get(0).toString().equals("DO"))){
                        removerSecuencia();
                        return statement();
                    }else{
                        throw new sintaxError("se esperaba la palabra do despues de "+actualLexer);
                    }
                }else{
                    return false;
                }
            }else{
                throw new sintaxError("se esperaba la palabra while despues de "+actualLexer);
            }
        }else{
            return false;
        }
    }
    /**
     * FALTO RECURSIVIDAD
     * @return
     * @throws sintaxError 
     */
    public boolean repeatStatemant() throws sintaxError{
        blanco();
        if(!listaTokens.isEmpty()){
            if(listaTokens.get(0).toString().equals("RESERVADA")&&( listaLexemas.get(0).toString().equals("repeat")||
               listaLexemas.get(0).toString().equals("REPEAT") )){
                removerSecuencia();
                if(statement()){
                    if(listaTokens.get(0).toString().equals("RESERVADA")&&(listaLexemas.get(0).toString().equals("until")||
                       listaLexemas.get(0).toString().equals("UNTIL"))){
                        removerSecuencia();
                        return expression();
                    }else{
                        throw new sintaxError("se esperaba la palabra until despues de "+actualLexer);
                    }
                }else{
                    return false;
                }
            }else{
                throw new sintaxError("se esperaba la palabra repeat despues de "+actualLexer);
            }
        }else{
            return false;
        }
    }
    /**
     * 
     * @return
     * @throws sintaxError 
     */
    public boolean withStatement() throws sintaxError{
        blanco();
        if(!listaTokens.isEmpty()){
            if(listaTokens.get(0).toString().equals("RESERVADA")&&( listaLexemas.get(0).toString().equals("with")||
               listaLexemas.get(0).toString().equals("WITH") )){
                removerSecuencia();
                if(recordVariableList()){
                    if(listaTokens.get(0).toString().equals("RESERVADA")&&( listaLexemas.get(0).toString().equals("do")||
                       listaLexemas.get(0).toString().equals("DO") )){
                        removerSecuencia();
                        return statement();
                    }else{
                        throw new sintaxError("se esperaba la palabra do despues de "+actualLexer);
                    }
                }else{
                    return false;
                }
            }else{
                throw new sintaxError("se esperaba la palabra with despues de "+actualLexer);
            }
        }else{
            return false;
        }
    }
    /**
     * 
     * @return
     * @throws sintaxError 
     */
    public boolean recordVariableList() throws sintaxError{
        blanco();
        if(!listaTokens.isEmpty()){
            if(variable()){
                if(listaTokens.get(0).toString().equals("OP")&&listaLexemas.get(0).toString().equals(",")){
                    removerSecuencia();
                    return recordVariableList();
                }else{
                    throw new sintaxError("se esperaba una , despues de "+actualLexer);
                }
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
    /**
     * 
     * @return
     * @throws sintaxError 
     */
    public boolean forStatement() throws sintaxError{
        blanco();
        if(!listaTokens.isEmpty()){
            if(listaTokens.get(0).toString().equals("RESERVADA")&&( listaLexemas.get(0).toString().equals("for")||
               listaLexemas.get(0).toString().equals("FOR") )){
                removerSecuencia();
                if(variable()){
                    if(listaTokens.get(0).toString().equals("OP")&& listaLexemas.get(0).toString().equals(":=")){
                        removerSecuencia();
                        if(forList()){
                            if(listaTokens.get(0).toString().equals("RESERVADA")&&( listaLexemas.get(0).toString().equals("do")||
                               listaLexemas.get(0).toString().equals("DO") )){
                                return statement();
                            }else{
                                throw new sintaxError("se esperaba la palabra do despues de "+actualLexer);
                            }
                        }else{
                            return false;
                        }
                    }else{
                        throw new sintaxError("se esperaba el operador := despues de "+actualLexer);
                    }
                }else{
                    return false;
                }
            }else{
                throw new sintaxError("se esperaba la palabra for despues de "+actualLexer);
            }
        }else{
            return false;
        }
    }
    /**
     * 
     * @return
     * @throws sintaxError 
     */
    public boolean forList() throws sintaxError{
        blanco();
        if(!listaTokens.isEmpty()){
            if(expression()){
                if(listaTokens.get(0).toString().equals("RESERVADA")&& (listaLexemas.get(0).toString().equals("to")||
                   listaLexemas.get(0).toString().equals("TO"))){
                    return expression();
                }else{
                    if(listaTokens.get(0).toString().equals("RESERVADA")&& (listaLexemas.get(0).toString().equals("downto")||
                       listaLexemas.get(0).toString().equals("DOWNTO"))){
                        return expression();
                    }else{
                        throw new sintaxError("se esperaba la palabra to o downto despues de "+actualLexer);
                    }
                }
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
    /**
     * 
     * @return
     * @throws sintaxError 
     */
    public boolean elementList() throws sintaxError{
        blanco();
        if(!listaTokens.isEmpty()){
            if(element()){
                if(listaTokens.get(0).toString().equals("OP")&&listaLexemas.get(0).toString().equals(",")){
                    return elementList();
                }else{
                    return true;
                }
            }else{
                return false;
            }
        }else{
            return true;
        }
    }
    /**
     * 
     * @return
     * @throws sintaxError 
     */
    public boolean element() throws sintaxError{
        blanco();
        if(!listaTokens.isEmpty()){
            if(expression()){
                if(listaTokens.get(0).toString().equals("OP")&&listaLexemas.get(0).toString().equals("..")){
                    removerSecuencia();
                    return expression();
                }else{
                    return true;
                }
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
}
