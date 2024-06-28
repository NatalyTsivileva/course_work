import cargo.*

object Storehouse {
    fun getFood(): Food {
        return Food()
    }

    fun getSmallCargo(): SmallCargo {
        return SmallCargo()
    }

    fun getMediumCargo(): MediumCargo {
        return MediumCargo()
    }

    fun getBulkyCargo(): BulkyCargo {
        return BulkyCargo()
    }
}