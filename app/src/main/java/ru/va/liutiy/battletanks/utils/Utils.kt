package ru.va.liutiy.battletanks.utils

import android.view.View
import ru.va.liutiy.battletanks.binding
import ru.va.liutiy.battletanks.models.Coordinate

fun View.checkViewCanMoveThroughBorder(coordinate: Coordinate): Boolean {
    return coordinate.top >= 0 &&
            coordinate.top + this.height <= binding.container.height &&
            coordinate.left >= 0 &&
            coordinate.left + this.width <= binding.container.width
}