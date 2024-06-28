package cargo

open class Cargo(
    private val name: String,
    val typeCargo: TypeCargo,
    val timeLoad: Long,
    val weight: Int
) {
    override fun toString(): String {
        return "Название='$name'вес=$weight)"
    }
}