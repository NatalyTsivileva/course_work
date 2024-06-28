package transporter

import cargo.Cargo
import kotlin.random.Random

class TransporterSmall(
    currentCapacity: Int = 0,
    capacity: Int = Random.nextInt(500, 1000),
    cargoList: MutableList<Cargo> = mutableListOf()
) :
    Transporter(currentCapacity, capacity, cargoList)