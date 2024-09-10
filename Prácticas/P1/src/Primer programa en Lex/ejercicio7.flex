/**
* Ejercicio 7. Escáner que detecta numeros, palabras, hexadecimales (En notacion Java), palabras reservadas, identificadores y espacios en blanco
*/

%%

%public
%class Lexer2
%standalone

digito=[0-9]
letra=[a-zA-Z]
palabra={letra}+
espacio=[ \t\n]

hexadecimal=0[xX][0-9a-fA-F]+ /* Usamos la notacion 0x */
reservada=class|public|boolean|int|private
identificador=({letra}|_|\$)({letra}|{digito}|_|\$){0,31}

%%

{espacio} {System.out.print("Encontré un espacio: "+yytext()+"\n");}
{digito}+ { System.out.print("Encontré un número: "+yytext()+"\n"); }
{hexadecimal} { System.out.print("Encontré un hexadecimal: "+yytext()+"\n"); }
{reservada} { System.out.print("Encontré una palabra reservada: "+yytext()+"\n"); }
{palabra} { System.out.print("Encontré una palabra: "+yytext()+"\n"); }
{identificador} { System.out.print("Encontré un identificador válido: "+yytext()+"\n"); }