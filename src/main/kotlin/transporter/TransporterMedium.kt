package transporter

import cargo.Cargo
import kotlin.random.Random

class TransporterMedium(
    currentCapacity: Int = 0,
    capacity: Int = Random.nextInt(1000, 2000),
    cargoList: MutableList<Cargo> = mutableListOf()
) :
    Transporter(currentCapacity, capacity, cargoList)