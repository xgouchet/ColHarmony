package fr.xgouchet.colharmony

data class Angle constructor(val value: Int) {

    operator fun plus(other: Angle): Angle {
        return Angle(value + other.value)
    }

    operator fun minus(other: Angle): Angle {
        return Angle(value - other.value)
    }

    fun normalized(): Angle {
        return if (value >= MAX_ANGLE) {
            Angle(value % MAX_ANGLE)
        } else if (value < 0) {
            Angle(value % MAX_ANGLE)
        } else {
            this
        }
    }

    companion object {
        const val MAX_ANGLE = 360
    }
}