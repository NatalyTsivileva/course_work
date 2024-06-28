package cargo

import kotlin.random.Random

class Food(
    name: String = listOf("Сметана", "Хлеб", "Сосиски", "Молоко").random(),
    typeCargo: TypeCargo = TypeCargo.Food,
    timeLoad: Long = Random.nextLong(500, 1000),
    weight: Int = Random.nextInt(10, 30)
) : Cargo(name, typeCargo, timeLoad, weight)