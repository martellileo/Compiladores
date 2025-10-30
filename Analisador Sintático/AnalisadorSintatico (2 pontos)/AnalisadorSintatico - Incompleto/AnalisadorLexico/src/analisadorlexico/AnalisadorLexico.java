/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisadorlexico;

/**
 *
 * @author oclum
 */
public class AnalisadorLexico {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        AnalisadorLex lex = new AnalisadorLex(args[0]);
        Token t = null;
        while((t=lex.proximoToken()).nome != TipoToken.Fim) {
            System.out.print(t);
        }
    }
    
}
