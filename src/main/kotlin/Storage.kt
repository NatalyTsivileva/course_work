import cargo.*
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class Storage(
    private val valuesFood: MutableList<Food> = mutableListOf(),
    private val valuesSmallCargo: MutableList<SmallCargo> = mutableListOf(),
    private val valuesMediumCargo: MutableList<MediumCargo> = mutableListOf(),
    private val valuesBulkyCargo: MutableList<BulkyCargo> = mutableListOf(),
    val countPortsForDownload: Int = 3,
    val countPortsForUpload: Int = 5
) {
    private val mutex = Mutex()

    private val countFood: Int
        get() = getSize(TypeCargo.Food)
    private val countSmallCargo: Int
        get() = getSize(TypeCargo.SmallCargo)
    private val countMediumCargo: Int
        get() = getSize(TypeCargo.MediumCargo)
    private val countBulkyCargo: Int
        get() = getSize(TypeCargo.BulkyCargo)
    private val capacityFood: Int
        get() = valuesFood.sumOf { it.weight }
    private val capacitySmallCargo: Int
        get() = valuesSmallCargo.sumOf { it.weight }
    private val capacityMediumCargo: Int
        get() = valuesMediumCargo.sumOf { it.weight }
    private val capacityBulkyCargo: Int
        get() = valuesBulkyCargo.sumOf { it.weight }
    private val overAllCountOfCargo: Int
        get() = countFood + countSmallCargo + countMediumCargo + countBulkyCargo
    private val overAllCapacityOfCargo: Int
        get() = capacityFood + countSmallCargo + capacityMediumCargo + capacityBulkyCargo

    suspend fun addElement(cargo: Cargo) {
        delay(cargo.timeLoad)
        if (currentCoroutineContext().isActive) {
            when (cargo) {
                is Food -> {
                    valuesFood.add(cargo)
                }

                is SmallCargo -> {
                    valuesSmallCargo.add(cargo)
                }

                is MediumCargo -> {
                    valuesMediumCargo.add(cargo)
                }

                is BulkyCargo -> {
                    valuesBulkyCargo.add(cargo)
                }
            }
        }
    }

    suspend fun getElement(typeCargo: TypeCargo): Cargo {
        mutex.withLock {
            val cargo: Cargo
            while (true) {
                if (checkAvailability(typeCargo)) {
                    when (typeCargo) {
                        TypeCargo.SmallCargo -> {
                            cargo = valuesMediumCargo.random()
                            valuesMediumCargo.remove(cargo)
                        }

                        TypeCargo.MediumCargo -> {
                            cargo = valuesMediumCargo.random()
                            valuesMediumCargo.remove(cargo)
                        }

                        TypeCargo.BulkyCargo -> {
                            cargo = valuesBulkyCargo.random()
                            valuesBulkyCargo.remove(cargo)
                        }

                        TypeCargo.Food -> {
                            cargo = valuesFood.random()
                            valuesFood.remove(cargo)
                        }
                    }
                    break
                }
            }
            delay(cargo.timeLoad)
            return cargo
        }
    }

    override fun toString(): String {
        return "${ColorFont.GREEN}Состояние хранилища:\n" +
                "${ColorFont.WHITE}" +
                "Общее количество товаров - $overAllCountOfCargo\n" +
                "Общий объем товаров - $overAllCapacityOfCargo\n" +
                "${ColorFont.YELLOW}" +
                "Еда: количество - $countFood, объем - $capacityFood\n" +
                "Малогабаритные товары - $countSmallCargo, объем - $capacitySmallCargo\n" +
                "Среднегабаритные товары - $countMediumCargo, объем - $capacityMediumCargo\n" +
                "Крупногабаритные товары - $countBulkyCargo, объем - $capacityBulkyCargo${ColorFont.WHITE}\n"
    }

    private fun checkAvailability(typeCargo: TypeCargo): Boolean {
        return when (typeCargo) {
            TypeCargo.SmallCargo -> countSmallCargo > 0
            TypeCargo.MediumCargo -> countMediumCargo > 0
            TypeCargo.BulkyCargo -> countBulkyCargo > 0
            TypeCargo.Food -> countFood > 0
        }
    }

    private fun getSize(typeCargo: TypeCargo): Int {
        return when (typeCargo) {
            TypeCargo.SmallCargo -> valuesSmallCargo.size
            TypeCargo.MediumCargo -> valuesMediumCargo.size
            TypeCargo.BulkyCargo -> valuesBulkyCargo.size
            TypeCargo.Food -> valuesFood.size
        }
    }
}