// --------------------------------------------
// Camila Cañete Calquin
// 1º DAM
// Modalidad Presencial
// Práctica nº 2
// --------------------------------------------
import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;
import java.util.Locale;
public class Ahorcado {
public static void main(String[] args) {
Scanner entrada = new Scanner(System.in);
char [] palabraIncognita = IngresarPalabra(entrada);
char [] palabraGuiones = OcultarPalabra(palabraIncognita);
char letraIngresada;
String juegoCompletado;
Set<Character> letrasIngresadas = new HashSet<>();
int intentos = 0;
boolean ahorcado = false;
boolean inicio = false;

// metodo para "limpiar consola" cuando se ingresa la palabra o frase, agrega muchos saltos de linea para que
//la palabra o frase no se vea en pantalla
limpiarConsola();
while (!ahorcado){ //bucle principal del juego, contiene funciones para ingresar y comparar letras, reiniciar o salir del programa
    while (!inicio){ //bucle para imprimir incognita en guiones, solo al inicio del juego
        System.out.print("Palabra(s) incognitas(s): " + new String(palabraGuiones)); //tiene que ser new String, de lo contrario muestra la palabra con caracteres especiales
        inicio = true;
    }
    try { //excepciones para cuando se ingresa una leta, si no es letra, si son mas de un caracter y evitar caracteres epeciales del alfabeto
        System.out.print("\nIngresa una letra: ");
        String exceptionUnaLetra = entrada.next().toLowerCase(Locale.ROOT); //entrada string que convierte los caracteres a minusculas
        if (exceptionUnaLetra.length() != 1 || !Character.isLetter(exceptionUnaLetra.charAt(0)) || "ªºÇç".contains(exceptionUnaLetra)){
            throw new IllegalArgumentException("Ingresa solo una letra");
        }
        letraIngresada = exceptionUnaLetra.charAt(0); //igual variable char al valor de  variable string
        if (letrasIngresadas.contains(letraIngresada)){ //condición letra acertada no se puede volver a ingresar
            throw new IllegalArgumentException("Letra acertada, prueba con otra");
        }
        //condicional para comparar si la letra ingresada esta en la palabra incognita y luego actualizar la palabra con guiones y letras acertadas
        if (compararLetra(letraIngresada, palabraIncognita, palabraGuiones)){
            System.out.println(new String(palabraGuiones));
            letrasIngresadas.add(letraIngresada); //si la letra es acerta se guarda en el conjunto set, permite condicional anterior
        }else {
            //si la letra ingresada no es acertada, se concidera fallo, en que se llama al metodo para mostrar estado del ahorcado
            intentos++; //contador de fallos, maximos 6, aumenta en 1 cada vez que pasa por aqui
            descontarFallos(intentos);
            System.out.println(palabraGuiones);
            //si el numero de intentos a llegado a 6, el juego a ferminado sin acertar la palabra y termina el programa
            if (intentos == 6){
                System.out.println("R.I.P. La palabra era " + "\"" + new String(palabraIncognita) + "\"" );
                ahorcado = true;
            }
        }
        //bucle para recorrer la incognita e identificar si aun quedan guiones bajos, es decir letras por adivinar
        boolean todasLetrasCorrectas = true;
        for (char caracter : palabraGuiones){
            if (caracter == '_'){
                todasLetrasCorrectas = false;
                break;
            }
        }
        //si del bucle anterior no quedan guines bajo entoces la variable es false, y se utiliza en la codicion para mensaje de felicitaciones
        if (todasLetrasCorrectas){
            System.out.println("\nFELICITACIONES! COMPLETASTE EL JUEGO");
            //bucle para que cuando el juego este logrado el usuario puede salir y terminar el programa o continuar jugando y se reinicia el juego
            //y condicionar que solo se puede ingres S para salir o C para continuar
            boolean salirOReiniciarJuego = false;
            while (!salirOReiniciarJuego){
                System.out.println("ingresa S, para salir del programa o C, para volver a jugar");
                juegoCompletado = entrada.next();
                //si se ingresa s por consola, se sale del programa
                if (juegoCompletado.equalsIgnoreCase("s")){
                    ahorcado = true;
                    salirOReiniciarJuego = true;

                // si se ingresa C se reinicia el programa y todas las variables
                } else if (juegoCompletado.equalsIgnoreCase("c")) {
                    intentos = 0;
                    letrasIngresadas.clear();
                    entrada.nextLine();
                    palabraIncognita = IngresarPalabra(entrada);
                    palabraGuiones = OcultarPalabra(palabraIncognita);
                    inicio = false;
                    limpiarConsola();
                    salirOReiniciarJuego = true;
                }else { //si ingresa un caracter diferente a S o C, sera valido solo cuando ingres S o C
                    System.out.println("Opción no valida, ingresa S o C");
                }
            }
        }
    // excepciones, trae mensaje de expecion cuando para cuando no es letra, es mas de una letra, o letra acertada ingresada nuevamente
    }catch (IllegalArgumentException e){
        System.out.println(e.getMessage());
        entrada.nextLine();
    }
}
}
//metodo, ingresar palabra o frase incognita, que permite letras del alfabeto, pero con
//condicional que no sean numeros y otros caracteres especiales
public static char [] IngresarPalabra (Scanner entrada){
while (true){
    System.out.print("Ingresa una palabra o frase, para comenzar el juego:");
    String incognita = entrada.nextLine().trim();
    if (incognita.matches("^[a-zA-Z\\s]{2,254}") && !incognita.matches(".*\\d.*") ){
        return incognita.toLowerCase(Locale.ROOT).toCharArray(); //retorna el valor del string ingresado en minisculas y tipo char
    }
    else {
    System.out.println("\nError, debes ingresar una palabra de al menos 2 letras o una frase, solo letras, sin tildes");
    }
}
}
//metodo convertir palabra o frase a incognita en la longitud de la cadena en guiones, represa letras en guion bajo (_) y espacios
//en blacos con guiones medios (-) y devuelve el arrays en guiones
public static char [] OcultarPalabra (char[] palabra){
char [] palabraGuiones = new char[palabra.length]; //variable palabraGuiones toma valor de la longitud de la variable palabra
//bucle para comparar cada caracter de la palabra ingresa y asignar guion medio o bajo
for (int i= 0; i < palabra.length; i++){
    if (palabra[i] == ' '){
        palabraGuiones[i] = '-';
    }else {
        palabraGuiones[i] = '_';
    }
}
return palabraGuiones;
}
//metodo para "limpiar" consola una vez ingresada la frase o palabra a adivinar, se imprimen 50 saltos de lineas y desaparece de la pantalla
public static void limpiarConsola() {
for (int i= 0; i < 50; i++)
    System.out.println();
}
//metodo para comparar si la letra ingresada se encuentra dentro de la palabra incognita
public static boolean compararLetra(char letra, char[] palabra, char[] palabraGuiones) {
boolean letraCorrecta = false;
//bucle recorre toda la palabra a adivinar y si en la posicion de i en la variable palabra es igual a la letra ingresa
//asigna el valor de la letra en la misma posicion de i en la variable que muestra la palabra en guiones, reemplaza el guion por la letra acertada
for (int i = 0; i < palabra.length; i++) {
    if (palabra[i] == letra) {
        palabraGuiones[i] = letra;
        letraCorrecta = true;
    }
}
return letraCorrecta;
}
//metodo para representar dibujo del ahoracado en cada numero de fallos, 6 intentos
public static void descontarFallos (int intentos){
switch (intentos) {
    case 1 -> {
        System.out.println("┌───┐   ");
        System.out.println("│   o   ");
        System.out.println("│       ");
        System.out.println("│       ");
        System.out.println("┴───────");
    }
    case 2 -> {
        System.out.println("┌───┐   ");
        System.out.println("│   o   ");
        System.out.println("│  /    ");
        System.out.println("│       ");
        System.out.println("┴───────");
    }
    case 3 -> {
        System.out.println("┌───┐   ");
        System.out.println("│   o   ");
        System.out.println("│  /|   ");
        System.out.println("│       ");
        System.out.println("┴───────");
    }
    case 4 -> {
        System.out.println("┌───┐   ");
        System.out.println("│   o   ");
        System.out.println("│  /|\\ ");
        System.out.println("│       ");
        System.out.println("┴───────");
    }
    case 5 -> {
        System.out.println("┌───┐   ");
        System.out.println("│   o   ");
        System.out.println("│  /|\\ ");
        System.out.println("│  /    ");
        System.out.println("┴───────");
    }
    case 6 -> {
        System.out.println("┌───┐   ");
        System.out.println("│   o   ");
        System.out.println("│  /|\\ ");
        System.out.println("│  / \\ ");
        System.out.println("┴───────");
    }
}
}
}