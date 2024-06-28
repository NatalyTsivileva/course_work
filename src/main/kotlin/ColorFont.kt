enum class ColorFont(private val string: String) {
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    WHITE("\u001B[0m"),
    BLUE("\u001B[34m");

    override fun toString(): String {
        return this.string
    }
}