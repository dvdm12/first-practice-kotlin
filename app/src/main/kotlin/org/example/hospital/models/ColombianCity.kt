package models

enum class ColombianCity(val displayName: String) {
    BOGOTA("Bogotá"),
    MEDELLIN("Medellín"),
    CALI("Cali"),
    BARRANQUILLA("Barranquilla"),
    CARTAGENA("Cartagena"),
    CUCUTA("Cúcuta"),
    PEREIRA("Pereira"),
    MANIZALES("Manizales"),
    IBAGUE("Ibagué"),
    BUCARAMANGA("Bucaramanga");

    companion object {
        fun fromInt(index: Int): ColombianCity? {
            return values().getOrNull(index)
        }
    }
}
