package cargo

import kotlin.random.Random

class MediumCargo(
    name: String = listOf("ПК", "Надувной батут", "Палатка", "Тумбочка").random(),
    typeCargo: TypeCargo = TypeCargo.MediumCargo,
    timeLoad: Long = Random.nextLong(2000, 3000),
    weight: Int = Random.nextInt(10, 30)
) : Cargo(name, typeCargo, timeLoad, weight)