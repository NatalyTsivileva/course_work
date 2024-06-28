package transporter

import cargo.Cargo

open class Transporter(
    var currentCapacity: Int,
    val capacity: Int,
    val cargoList: MutableList<Cargo>
) {
    fun getCargoListSize(): Int = cargoList.size

    fun addCargoList(cargo: Cargo) {
        cargoList.add(cargo)
        currentCapacity += cargo.weight
    }

    override fun toString(): String {
        return "Грузовик${this.hashCode()}, вместимость = $capacity)"
    }
}