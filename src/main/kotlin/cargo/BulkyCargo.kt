package cargo

import kotlin.random.Random

class BulkyCargo(
    name: String = listOf("Шкаф", "Диван", "Кухонный стол").random(),
    typeCargo: TypeCargo = TypeCargo.BulkyCargo,
    timeLoad: Long = Random.nextLong(3000, 5000),
    weight: Int = Random.nextInt(50, 100)
) : Cargo(name, typeCargo, timeLoad, weight)