package cargo

enum class TypeCargo {
    SmallCargo {
        override fun toString(): String {
            return "Малогабаритный груз"
        }
    },
    MediumCargo {
        override fun toString(): String {
            return "Среднегабаритный груз"
        }
    },
    BulkyCargo {
        override fun toString(): String {
            return "Крупногабаритный груз"
        }
    },
    Food {
        override fun toString(): String {
            return "Еда"
        }
    }
}