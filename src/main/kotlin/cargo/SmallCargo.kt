package cargo

import kotlin.random.Random

class SmallCargo(
    name: String = listOf("Картина", "Комплект для вышивания", "Посылка", "Коробка").random(),
    typeCargo: TypeCargo = TypeCargo.SmallCargo,
    timeLoad: Long = Random.nextLong(1000, 2000),
    weight: Int = Random.nextInt(2, 10)
) : Cargo(name, typeCargo, timeLoad, weight)