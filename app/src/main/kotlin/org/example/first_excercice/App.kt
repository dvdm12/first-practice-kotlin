
//David Mantilla, Cod: 240220212015

package first_excercice

import kotlin.text.lowercase

fun toBinaryRecursive(n: Int): String {
    require(n >= 0) { "The number must be non-negative." }
    // Base case
    if (n < 2) return n.toString()
    // Recursive case: divide by 2 and append remainder
    return toBinaryRecursive(n / 2) + (n % 2).toString()
}

fun binaryToDecimalRecursive(bin: String): Int {
    if (bin.isEmpty() || !bin.all { it == '0' || it == '1' }) {
            throw InvalidBinaryException("Input must contain only binary digits (0 or 1).")
    }

    // Base case: if only one bit, just return it
    if (bin.length == 1) return bin[0].digitToInt()

    // Recursive case: first bit shifted by (length-1) + rest of the binary
    return (bin[0].digitToInt() shl (bin.length - 1)) + binaryToDecimalRecursive(bin.substring(1))
}

fun main() {
    var chosenAgain: Boolean

    do{
        println("Choose an option:")
        println("1) Convert decimal to binary")
        println("2) Convert binary to decimal")
        print("Enter your choice (1 or 2): ")
        
        when (readLine()?.trim()) {
            "1" -> {
                print("Put a positive number greater than 0: ")
                val input = readLine()?.toIntOrNull()
                if (input == null || input < 1) {
                    println("Error: you must put a number greater than 0.")
                    return
                }
                println("Binary value: ${toBinaryRecursive(input)}")
            }
            "2" -> {
                print("Put a binary number: ")
                val binInput = readLine()?.trim() ?: return
                val decimal = binaryToDecimalRecursive(binInput)
                println("Decimal value: $decimal")
            }
            else -> println("Invalid choice.")
        }

       println("¿Do you want to try it again? (yes or not)")
       chosenAgain = readLine()?.trim()?.lowercase() == "yes"
       
    }while(chosenAgain)
}
