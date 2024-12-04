/**
 * Analizador léxico para el lenguaje C_1.
 */

package main.jflex;

import main.java.ClaseLexica;
import main.java.Token;

// Declaracion de la variable que se utilizara para almacenar el token que esta siendo reconocido actualmente
%%

%{

public Token actual;

%}

%public
%class Lexer
%standalone
%unicode

// Definicion de las expresiones regulares que representan los elementos del lenguaje C_1

espacio=[ \t\n]+         // Reconoce espacios en blanco (espacios, tabulaciones y nuevas lineas)
int=int                  // Reconoce la palabra reservada int
float=float              // Reconoce la palabra reservada float
if=if                    // Reconoce la palabra reservada if
else=else                // Reconoce la palabra reservada else
while=while              // Reconoce la palabra reservada while
num=(0|[1-9][0-9]*)(\.[0-9]+)?([eE][+-]?[0-9]+)?  // Reconoce numeros, tanto enteros como flotantes (con o sin notacion cientifica)
id=[_a-zA-Z][_a-zA-Z0-9]* // Reconoce identificadores
pyc=;                    // Reconoce el símbolo de punto y coma
coma=,                   // Reconoce el símbolo de coma
lpar=\(                  // Reconoce el paréntesis izquierdo
rpar=\)                  // Reconoce el paréntesis derecho

// Definición de las reglas para el reconocimiento de tokens
// Se da prioridad a las palabras reservadas y luego a los demás elementos del lenguaje.

%%

{espacio} {/* Acción léxica vacía para ignorar los espacios en blanco durante el análisis */}


{int} {
    // Reconoce la palabra reservada "int" y crea un token asociado de tipo INT
    actual = new Token(ClaseLexica.INT, yytext());
    System.out.println(actual.toString());
}

{float} {
    // Reconoce la palabra reservada "float" y crea un token asociado de tipo FLOAT
    actual = new Token(ClaseLexica.FLOAT, yytext());
    System.out.println(actual.toString());
}

{if} {
    // Reconoce la palabra reservada "if" y crea un token asociado de tipo IF
    actual = new Token(ClaseLexica.IF, yytext());
    System.out.println(actual.toString());
}

{else} {
    // Reconoce la palabra reservada "else" y crea un token asociado de tipo ELSE
    actual = new Token(ClaseLexica.ELSE, yytext());
    System.out.println(actual.toString());
}

{while} {
    // Reconoce la palabra reservada "while" y crea un token asociado de tipo WHILE
    actual = new Token(ClaseLexica.WHILE, yytext());
    System.out.println(actual.toString());
}

{num} {
    // Reconoce un número (entero o flotante) y crea un token asociado de tipo NUMERO
    actual = new Token(ClaseLexica.NUMERO, yytext());
    System.out.println(actual.toString());
}

{id} {
    // Reconoce un identificador y crea un token asociado de tipo ID
    actual = new Token(ClaseLexica.ID, yytext());
    System.out.println(actual.toString());
}

{pyc} {
    // Reconoce el símbolo de punto y coma (;) y crea un token asociado de tipo PYC
    actual = new Token(ClaseLexica.PYC, yytext());
    System.out.println(actual.toString());
}

{coma} {
    // Reconoce el símbolo de coma (,) y crea un token asociado de tipo COMA
    actual = new Token(ClaseLexica.COMA, yytext());
    System.out.println(actual.toString());
}

{lpar} {
    // Reconoce el paréntesis izquierdo (() y crea un token asociado de tipo LPAR
    actual = new Token(ClaseLexica.LPAR, yytext());
    System.out.println(actual.toString());
}

{rpar} {
    // Reconoce el paréntesis derecho ()) y crea un token asociado de tipo RPAR
    actual = new Token(ClaseLexica.RPAR, yytext());
    System.out.println(actual.toString());
}
