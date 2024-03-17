package ru.va.liutiy.battletanks.models

import ru.va.liutiy.battletanks.enums.Material

data class Element(
    val viewId: Int,
    val material: Material,
    val coordinate: Coordinate
){

}
