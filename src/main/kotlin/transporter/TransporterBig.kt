package transporter

import cargo.Cargo
import kotlin.random.Random

class TransporterBig(
    currentCapacity: Int = 0,
    capacity: Int = Random.nextInt(1500, 2500),
    cargoList: MutableList<Cargo> = mutableListOf()
) :
    Transporter(currentCapacity, capacity, cargoList)