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
        Token proximo = null;
        espacosEComentarios();
        lae.confirmar();
        proximo = fim();
        if (proximo == null) {
            lae.zerar();
        } else {
            lae.confirmar();
            return proximo;
        }
        proximo = palavrasChave();
        if (proximo == null) {
            lae.zerar();
        } else {
            lae.confirmar();
            return proximo;
        }
        proximo = variavel();
        if (proximo == null) {
            lae.zerar();
        } else {
            lae.confirmar();
            return proximo;
        }
        proximo = numeros();
        if (proximo == null) {
            lae.zerar();
        } else {
            lae.confirmar();
            return proximo;
        }
        proximo = operadorAritmetico();
        if (proximo == null) {
            lae.zerar();
        } else {
            lae.confirmar();
            return proximo;
        }
        proximo = operadorRelacional();
        if (proximo == null) {
            lae.zerar();
        } else {
            lae.confirmar();
            return proximo;
        }
        proximo = delimitador();
        if (proximo == null) {
            lae.zerar();
        } else {
            lae.confirmar();
            return proximo;
        }
        proximo = parenteses();
        if (proximo == null) {
            lae.zerar();
        } else {
            lae.confirmar();
            return proximo;
        }
        proximo = cadeia();
        if (proximo == null) {
            lae.zerar();
        } else {
            lae.confirmar();
            return proximo;
        }
        System.err.println("Erro léxico!");
        System.err.println(lae.toString());
        return null;
    }
    private Token operadorAritmetico() {
        int caractereLido = lae.lerProximoCaractere();
        char c = (char) caractereLido;
        if (c == '*') {
            return new Token(TipoToken.OpAritMult, lae.getLexema());
        } else if (c == '/') {
            return new Token(TipoToken.OpAritDiv, lae.getLexema());
        } else if (c == '+') {
            return new Token(TipoToken.OpAritSoma, lae.getLexema());
        } else if (c == '-') {
            return new Token(TipoToken.OpAritSub, lae.getLexema());
        } else {
            return null;
        }
    }
    private Token delimitador() {
        int caractereLido = lae.lerProximoCaractere();
        char c = (char) caractereLido;
        if (c == ':') {
            return new Token(TipoToken.Delim, lae.getLexema());
        } else {
            return null;
        }
    }

    private Token parenteses() {
        int caractereLido = lae.lerProximoCaractere();
        char c = (char) caractereLido;
        if (c == '(') {
            return new Token(TipoToken.AbrePar, lae.getLexema());
        } else if (c == ')') {
            return new Token(TipoToken.FechaPar, lae.getLexema());
        } else {
            return null;
        }
    }
    private Token operadorRelacional() {
        int caractereLido = lae.lerProximoCaractere();
        char c = (char) caractereLido;
        if (c == '<') {
            c = (char) lae.lerProximoCaractere();
            if (c == '>') {
                return new Token(TipoToken.OpRelDif, lae.getLexema());
            } else if (c == '=') {
                return new Token(TipoToken.OpRelMenorIgual, lae.getLexema());
            } else {
                lae.retroceder();
                return new Token(TipoToken.OpRelMenor, lae.getLexema());
            }
        } else if (c == '=') {
            return new Token(TipoToken.OpRelIgual, lae.getLexema());
        } else if (c == '>') {
            c = (char) lae.lerProximoCaractere();
            if (c == '=') {
                return new Token(TipoToken.OpRelMaiorIgual, lae.getLexema());
            } else {
                lae.retroceder();
                return new Token(TipoToken.OpRelMaior, lae.getLexema());
            }
        }
        return null;
    }
    private Token numeros() {
        int estado = 1;
        while (true) {
            char c = (char) lae.lerProximoCaractere();
            if (estado == 1) {
                if (Character.isDigit(c)) {
                    estado = 2;
                } else {
                    return null;
                }
            } else if (estado == 2) {
                if (c == '.') {
                    c = (char) lae.lerProximoCaractere();
                    if (Character.isDigit(c)) {
                        estado = 3;
                    } else {
                        return null;
                    }
                } else if (!Character.isDigit(c)) {
                    lae.retroceder();
                    return new Token(TipoToken.NumInt, lae.getLexema());
                }
            } else if (estado == 3) {
                if (!Character.isDigit(c)) {
                    lae.retroceder();
                    return new Token(TipoToken.NumReal, lae.getLexema());
                }
            }
        }
    }
    private Token variavel() {
        int estado = 1;
        while (true) {
            char c = (char) lae.lerProximoCaractere();
            if (estado == 1) {
                if (Character.isLetter(c)) {
                    estado = 2;
                } else {
                    return null;
                }
            } else if (estado == 2) {
                if (!Character.isLetterOrDigit(c)) {
                    lae.retroceder();
                    return new Token(TipoToken.Var, lae.getLexema());
                }
            }
        }
    }
    private Token cadeia() {
        int estado = 1;
        while (true) {
            char c = (char) lae.lerProximoCaractere();
            if (estado == 1) {
                if (c == '\'') {
                    estado = 2;
                } else {
                    return null;
                }
            } else if (estado == 2) {
                if (c == '\n') {
                    return null;
                }
                if (c == '\'') {
                    return new Token(TipoToken.Cadeia, lae.getLexema());
                } else if (c == '\\') {
                    estado = 3;
                }
            } else if (estado == 3) {
                if (c == '\n') {
                    return null;
                } else {
                    estado = 2;
                }
            }
        }
    }
    private void espacosEComentarios() {
        int estado = 1;
        while (true) {
            char c = (char) lae.lerProximoCaractere();
            if (estado == 1) {
                if (Character.isWhitespace(c) || c == ' ') {
                    estado = 2;
                } else if (c == '%') {
                    estado = 3;
                } else {
                    lae.retroceder();
                    return;
                }
            } else if (estado == 2) {
                if (c == '%') {
                    estado = 3;
                } else if (!(Character.isWhitespace(c) || c == ' ')) {
                    lae.retroceder();
                    return;
                }
            } else if (estado == 3) {
                if (c == '\n') {
                    return;
                }
            }
        }
    }
    private Token palavrasChave() {
        while (true) {
            char c = (char) lae.lerProximoCaractere();
            if (!Character.isLetter(c)) {
                lae.retroceder();
                String lexema = lae.getLexema();
                if (lexema.equals("DECLARACOES")) {
                    return new Token(TipoToken.PCDeclaracoes, lexema);
                } else if (lexema.equals("ALGORITMO")) {
                    return new Token(TipoToken.PCAlgoritmo, lexema);
                } else if (lexema.equals("INTEIRO")) {
                    return new Token(TipoToken.PCInteiro, lexema);
                } else if (lexema.equals("REAL")) {
                    return new Token(TipoToken.PCReal, lexema);
                } else if (lexema.equals("ATRIBUIR")) {
                    return new Token(TipoToken.PCAtribuir, lexema);
                } else if (lexema.equals("A")) {
                    return new Token(TipoToken.PCA, lexema);
                } else if (lexema.equals("LER")) {
                    return new Token(TipoToken.PCLer, lexema);
                } else if (lexema.equals("IMPRIMIR")) {
                    return new Token(TipoToken.PCImprimir, lexema);
                } else if (lexema.equals("SE")) {
                    return new Token(TipoToken.PCSe, lexema);
                } else if (lexema.equals("ENTAO")) {
                    return new Token(TipoToken.PCEntao, lexema);
                } else if (lexema.equals("SENAO")) {
                    return new Token(TipoToken.PCSenao, lexema);
                } else if (lexema.equals("ENQUANTO")) {
                    return new Token(TipoToken.PCEnquanto, lexema);
                } else if (lexema.equals("INICIO")) {
                    return new Token(TipoToken.PCInicio, lexema);
                } else if (lexema.equals("FIM")) {
                    return new Token(TipoToken.PCFim, lexema);
                } else if (lexema.equals("E")) {
                    return new Token(TipoToken.OpBoolE, lexema);
                } else if (lexema.equals("OU")) {
                    return new Token(TipoToken.OpBoolOu, lexema);
                } else {
                    return null;
                }
            }
        }
    }
    private Token fim() {
        int caractereLido = lae.lerProximoCaractere();
        if (caractereLido == -1) {
            return new Token(TipoToken.Fim, "Fim");
        }
        return null;
    }
}