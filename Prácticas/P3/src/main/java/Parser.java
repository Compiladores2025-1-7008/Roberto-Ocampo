package main.java;

import java.io.IOException;
import main.jflex.Lexer;

public class Parser {
    private Lexer analizadorLexico;
    private int tokenActual;

    public Parser(Lexer analizadorLexico) {
        this.analizadorLexico = analizadorLexico;
    }

    public void eat(int claseLexicaEsperada) throws IOException, ParserException {
        if (tokenActual == claseLexicaEsperada) {
            tokenActual = analizadorLexico.yylex();
        } else {
            throw new ParserException("Token inesperado: se esperaba '" + obtenerNombreToken(claseLexicaEsperada) +
                    "', pero se encontró '" + obtenerNombreToken(tokenActual) + "' en la línea " + analizadorLexico.getLine());
        }
    }

    private String obtenerNombreToken(int claseLexica) {
        switch (claseLexica) {
            case ClaseLexica.INT: return "int";
            case ClaseLexica.FLOAT: return "float";
            case ClaseLexica.ID: return "identificador";
            case ClaseLexica.NUMERO: return "número";
            case ClaseLexica.PYC: return "punto y coma";
            case ClaseLexica.ASIG: return "asignación (=)";
            case ClaseLexica.IF: return "if";
            case ClaseLexica.ELSE: return "else";
            case ClaseLexica.WHILE: return "while";
            case ClaseLexica.SUMA: return "suma (+)";
            case ClaseLexica.RESTA: return "resta (-)";
            case ClaseLexica.MULT: return "multiplicación (*)";
            case ClaseLexica.DIV: return "división (/)";
            case ClaseLexica.LPAR: return "paréntesis izquierdo";
            case ClaseLexica.RPAR: return "paréntesis derecho";
            case ClaseLexica.COMA: return "coma";
            default: return "token desconocido";
        }
    }

    public void parse() throws IOException, ParserException {
        tokenActual = analizadorLexico.yylex();
        programa();
        if (tokenActual == -1) {
            System.out.println("Análisis sintáctico exitoso: la cadena pertenece al lenguaje.");
        } else {
            throw new ParserException("Tokens adicionales encontrados al final del análisis.");
        }
    }

    public void programa() throws IOException, ParserException {
        declaraciones();
        sentencias();
    }

    public void declaraciones() throws IOException, ParserException {
        if (tokenActual == ClaseLexica.INT || tokenActual == ClaseLexica.FLOAT) {
            declaracion();
            declaraciones_();
        }
    }

    public void declaraciones_() throws IOException, ParserException {
        if (tokenActual == ClaseLexica.INT || tokenActual == ClaseLexica.FLOAT) {
            declaracion();
            declaraciones_();
        }
    }

    public void declaracion() throws IOException, ParserException {
        tipo();
        lista_var();
        eat(ClaseLexica.PYC);
    }

    public void tipo() throws IOException, ParserException {
        if (tokenActual == ClaseLexica.INT) {
            eat(ClaseLexica.INT);
        } else if (tokenActual == ClaseLexica.FLOAT) {
            eat(ClaseLexica.FLOAT);
        } else {
            throw new ParserException("Se esperaba 'int' o 'float' como tipo de dato en la línea " + analizadorLexico.getLine());
        }
    }

    public void lista_var() throws IOException, ParserException {
        if (tokenActual == ClaseLexica.ID) {
            eat(ClaseLexica.ID);
            lista_var_();
        } else {
            throw new ParserException("Se esperaba un identificador después de la declaración del tipo en la línea " + analizadorLexico.getLine());
        }
    }

    public void lista_var_() throws IOException, ParserException {
        if (tokenActual == ClaseLexica.COMA) {
            eat(ClaseLexica.COMA);
            if (tokenActual == ClaseLexica.ID) {
                eat(ClaseLexica.ID);
                lista_var_();
            } else {
                throw new ParserException("Se esperaba un identificador después de la coma en la línea " + analizadorLexico.getLine());
            }
        }
    }

    public void sentencias() throws IOException, ParserException {
        if (tokenActual == ClaseLexica.ID || tokenActual == ClaseLexica.IF || tokenActual == ClaseLexica.WHILE) {
            sentencia();
            sentencias_();
        }
    }

    public void sentencias_() throws IOException, ParserException {
        if (tokenActual == ClaseLexica.ID || tokenActual == ClaseLexica.IF || tokenActual == ClaseLexica.WHILE) {
            sentencia();
            sentencias_();
        }
    }

    public void sentencia() throws IOException, ParserException {
        if (tokenActual == ClaseLexica.ID) {
            eat(ClaseLexica.ID);
            eat(ClaseLexica.ASIG);
            expresion();
            eat(ClaseLexica.PYC);
        } else if (tokenActual == ClaseLexica.IF) {
            eat(ClaseLexica.IF);
            eat(ClaseLexica.LPAR);
            expresion();
            eat(ClaseLexica.RPAR);
            sentencias();
            eat(ClaseLexica.ELSE);
            sentencias();
        } else if (tokenActual == ClaseLexica.WHILE) {
            eat(ClaseLexica.WHILE);
            eat(ClaseLexica.LPAR);
            expresion();
            eat(ClaseLexica.RPAR);
            sentencias();
        } else {
            throw new ParserException("Se esperaba una sentencia válida (asignación, if o while) en la línea " + analizadorLexico.getLine());
        }
    }

    public void expresion() throws IOException, ParserException {
        expresion_suma();
    }

    public void expresion_suma() throws IOException, ParserException {
        expresion_mult();
        expresion_suma_();
    }

    public void expresion_suma_() throws IOException, ParserException {
        if (tokenActual == ClaseLexica.SUMA) {
            eat(ClaseLexica.SUMA);
            expresion_mult();
            expresion_suma_();
        } else if (tokenActual == ClaseLexica.RESTA) {
            eat(ClaseLexica.RESTA);
            expresion_mult();
            expresion_suma_();
        }
    }

    public void expresion_mult() throws IOException, ParserException {
        termino();
        expresion_mult_();
    }

    public void expresion_mult_() throws IOException, ParserException {
        if (tokenActual == ClaseLexica.MULT) {
            eat(ClaseLexica.MULT);
            termino();
            expresion_mult_();
        } else if (tokenActual == ClaseLexica.DIV) {
            eat(ClaseLexica.DIV);
            termino();
            expresion_mult_();
        }
    }

    public void termino() throws IOException, ParserException {
        if (tokenActual == ClaseLexica.ID) {
            eat(ClaseLexica.ID);
        } else if (tokenActual == ClaseLexica.NUMERO) {
            eat(ClaseLexica.NUMERO);
        } else if (tokenActual == ClaseLexica.LPAR) {
            eat(ClaseLexica.LPAR);
            expresion();
            eat(ClaseLexica.RPAR);
        } else {
            throw new ParserException("Se esperaba un identificador, número o paréntesis para formar un término en la línea " + analizadorLexico.getLine());
        }
    }
}
