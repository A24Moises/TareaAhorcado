import kotlin.system.exitProcess

// colores texto
const val GREEN = "\u001B[32m"
const val RESET = "\u001B[0m"
const val WHITE = "\u001B[37m"
const val RED = "\u001B[31m"
const val YELLOW = "\u001B[33m"
const val BLUE = "\u001B[34m"
const val PURPLE = "\u001B[35m"
const val CYAN = "\u001B[36m"
//colores fondo
const val GREEN_BACKGROUND = "\u001B[42m"
const val RED_BACKGROUND = "\u001B[41m"
const val YELLOW_BACKGROUND = "\u001B[43m"
const val BLUE_BACKGROUND = "\u001B[44m"
const val PURPLE_BACKGROUND = "\u001B[45m"
const val CYAN_BACKGROUND = "\u001B[46m"
const val WHITE_BACKGROUND = "\u001B[47m"

fun main() {
    val music=reproductorMidi("pugnodollari.mid")
    println("Cargando juego...")
    println()
    Thread.sleep(10000)

    println("${CYAN}¡Bienvenido al clasico juego de El Ahorcado!")
    println()
    println("$GREEN            ------\n" +
            "            |    |\n" +
            "            |$PURPLE    O$RESET\n" +
            "$GREEN            |$PURPLE   /|\\\n$RESET" +
            "$GREEN            |$PURPLE   / \\\n$RESET" +
            "$GREEN            |\n" +
            "         |----------|\n" +
            "         |          |\n" +
            "         |----------|")
    println()
    print("${RESET}Jugador 1, ingresa la palabras secreta: ")
    val palabraSecreta = readln()?.uppercase() ?: ""
    print("Jugador 1, ingresa 4 palabras relacionadas: ")
    val palabrasRelacionadas = readln().uppercase().split(" ") ?: ""
    println("Estas son: "+ palabrasRelacionadas)
    if (palabraSecreta.isEmpty()) {
        println("$RED_BACKGROUND                                        $RESET")
        println("La palabra secreta no puede estar vacía.")
        println("$RED_BACKGROUND                                        $RESET")
        println("inicia de nuevo el juego...")
        println("-----------------------------------------")
        exitProcess(0)
    }

    repeat(50) { println() }
    println("¡La palabra secreta ha sido ingresada! Turno del Jugador 2.")

// Inicio del Juego
    val intentosMaximos = 7
    var intentos = 0
    val letrasAdivinadas = mutableSetOf<Char>()
    val palabraSecretaSet = palabraSecreta.toSet()

    while (intentos < intentosMaximos) {
        println("\n${PURPLE}Palabra: " + obtenerProgreso(palabraSecreta, letrasAdivinadas))
        println("\n${YELLOW}Palabras relacionadas: " + palabrasRelacionadas)
        println("${RESET}Letras adivinadas: $letrasAdivinadas"+" $GREEN_BACKGROUND  $RESET")
        println("${RESET}Intentos restantes: ${intentosMaximos - intentos}"+" $YELLOW_BACKGROUND  $RESET")
        DibujoAhorcado.dibujar(intentos)
        Thread.sleep(1000)

        print("Ingresa una letra: ")
        val letra = readln()?.uppercase()?.getOrNull(0) ?: continue
        println("-".repeat(45))

        if (letra in letrasAdivinadas) {
            println("$YELLOW_BACKGROUND  $RESET "+"Ya intentaste con la letra $letra."+" $YELLOW_BACKGROUND  $RESET")
        } else if (letra in palabraSecretaSet) {
            println("$BLUE_BACKGROUND  $RESET "+"¡Bien hecho! La letra $letra está en la palabra."+" $BLUE_BACKGROUND  $RESET")
            letrasAdivinadas.add(letra)
            if (palabraSecretaSet.all { it in letrasAdivinadas }) {
                println("\n$GREEN_BACKGROUND  $RESET "+" $GREEN_BACKGROUND  $RESET "+" $GREEN_BACKGROUND  $RESET "+"¡Felicidades! Adivinaste la palabra: $palabraSecreta"+" $GREEN_BACKGROUND  $RESET "+" $GREEN_BACKGROUND  $RESET "+" $GREEN_BACKGROUND  $RESET ")
                break
            }
        } else {
            println("$RED_BACKGROUND  $RESET "+"Lo siento, la letra $letra no está en la palabra."+" $RED_BACKGROUND  $RESET")
            letrasAdivinadas.add(letra)
            intentos++
        }
        if (intentos == intentosMaximos) {
            DibujoAhorcado.dibujar(7)
            println("\n$RED_BACKGROUND  $RESET "+" $RED_BACKGROUND  $RESET " +" $RED_BACKGROUND  $RESET "+"¡Perdiste! La palabra era: $palabraSecreta"+" $RED_BACKGROUND  $RESET "+" $RED_BACKGROUND  $RESET " +" $RED_BACKGROUND  $RESET ")
            break
        }
    }
    exitProcess(0)
}

fun obtenerProgreso(palabra: String, letrasAdivinadas: Set<Char>): String {
    return palabra.map { if (it in letrasAdivinadas) it else '_' }.joinToString(" ")
}