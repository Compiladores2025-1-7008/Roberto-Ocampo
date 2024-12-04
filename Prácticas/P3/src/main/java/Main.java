package main.java;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import main.jflex.Lexer;

public class Main {
    public static void main(String[] args) {
        imprimirBanner("Análisis Léxico y Sintáctico Iniciado");

        if (args.length < 1) {
            System.err.println("Error: No se proporcionó la ruta del archivo como argumento.");
            System.err.println("Uso: java -cp build main.java.Main <ruta_del_archivo> [v1|v2]");
            System.exit(1);
        }

        String rutaArchivo = args[0];
        String versionParser = (args.length > 1) ? args[1].toLowerCase() : "v1";  // Por defecto usa ParserV1

        try (FileReader fileReader = new FileReader(rutaArchivo);
             BufferedReader reader = new BufferedReader(fileReader)) {

            System.out.println("Contenido del archivo proporcionado:");
            System.out.println("-------------------------------------");
            String linea;
            while ((linea = reader.readLine()) != null) {
                System.out.println(linea);
            }
            System.out.println("-------------------------------------\n");

            
            System.out.println("Iniciando análisis léxico y sintáctico con Parser " + versionParser.toUpperCase() + "...\n");
            Lexer lexer = new Lexer(new FileReader(rutaArchivo));

            if (versionParser.equals("v2")) {
                System.out.println("Utilizando ParserV2...");
                ParserV2 parser = new ParserV2(lexer);
                parser.parse();
            } else {
                System.out.println("Utilizando ParserV1...");
                Parser parser = new Parser(lexer);
                parser.parse();
            }

            System.out.println("\nAnálisis completado con éxito. No se encontraron errores.");
        } catch (FileNotFoundException e) {
            System.err.println("Error: No se encontró el archivo especificado en la ruta: " + rutaArchivo);
        } catch (IOException e) {
            System.err.println("Error: Ocurrió un problema al leer el archivo: " + e.getMessage());
        } catch (ParserException e) {
            System.err.println("Error de sintaxis: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
        } finally {
            imprimirBanner("Análisis Léxico y Sintáctico Finalizado");
        }
    }

    private static void imprimirBanner(String mensaje) {
        System.out.println("==========================");
        System.out.println(" " + mensaje);
        System.out.println("==========================");
    }
}
