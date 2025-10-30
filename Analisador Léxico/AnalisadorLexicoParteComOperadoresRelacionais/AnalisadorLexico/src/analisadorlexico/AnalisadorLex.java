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
public class AnalisadorLex {
    LerArquivosEntrada lae;
    public AnalisadorLex(String arquivo) {
        lae = new LerArquivosEntrada(arquivo);
    }
    public Token proximoToken() {
        int caractereLido = -1;
        while((caractereLido = lae.lerProximoCaractere()) != -1) {
            char c = (char)caractereLido;
            if(c == ' ' || c == '\n') continue;
            if(c == ':') {
                return new Token(TipoToken.Delim,":");
            } else if(c == '*') {
                return new Token(TipoToken.OpAritMult,"*");
            } else if(c == '/'){
                return new Token(TipoToken.OpAritDiv, "/");
            } else if (c == '+'){
                return new Token(TipoToken.OpAritSoma, "+");
            } else if (c == '-'){
                return new Token(TipoToken.OpAritSub, "-");
            } else if (c == '(') {
                return new Token(TipoToken.AbrePar, "(");
            } else if (c == ')') {
                return new Token(TipoToken.FechaPar, ")");
            } else if (c == '=') {
                return new Token(TipoToken.OpRelIgual, "=");
            } else if (c == '<') {
                char d = (char) lae.lerProximoCaractere();
                if (d == ' ' || d == '\n') {
                    return new Token(TipoToken.OpRelMenor, "<");
                } else if (d == '>'){
                    return new Token(TipoToken.OpRelDif, "<>");
                } else if (d == '='){
                    return new Token(TipoToken.OpRelMenorIgual, "<=");
                }
            } else if (c == '>') {
                char d = (char) lae.lerProximoCaractere();
                if (d == ' ' || d == '\n') {
                    return new Token(TipoToken.OpRelMaior, ">");
                } else if (d == '=') {
                    return new Token(TipoToken.OpRelMaiorIgual, ">=");
                }
            }
        }
        return null;    
    }
}