package ru.va.liutiy.battletanks.drawers

import android.view.View
import android.widget.FrameLayout
import ru.va.liutiy.battletanks.CELL_SIZE
import ru.va.liutiy.battletanks.binding
import ru.va.liutiy.battletanks.enums.Direction
import ru.va.liutiy.battletanks.models.Coordinate
import ru.va.liutiy.battletanks.models.Element
import ru.va.liutiy.battletanks.utils.checkViewCanMoveThroughBorder

class TankDrawer (val container: FrameLayout) {
    var currentDirection = Direction.UP

    fun move(myTank: View, direction: Direction, elementOnContainer: List<Element>) {
        val layoutParams = myTank.layoutParams as FrameLayout.LayoutParams
        val currentCoordinate = Coordinate(layoutParams.topMargin, layoutParams.leftMargin)
        currentDirection = direction
        myTank.rotation = direction.rotation
        when (direction) {
            Direction.UP -> {
                (myTank.layoutParams as FrameLayout.LayoutParams).topMargin -= CELL_SIZE
            }

            Direction.DOWN -> {
                (myTank.layoutParams as FrameLayout.LayoutParams).topMargin += CELL_SIZE
            }

            Direction.LEFT -> {
                (myTank.layoutParams as FrameLayout.LayoutParams).leftMargin -= CELL_SIZE
            }

            Direction.RIGHT -> {
                (myTank.layoutParams as FrameLayout.LayoutParams).leftMargin += CELL_SIZE
            }
        }

        val nextCoordinate = Coordinate(layoutParams.topMargin, layoutParams.leftMargin)
        if (myTank.checkViewCanMoveThroughBorder(
                nextCoordinate
            ) && checkTankCanMoveThroughMaterial(nextCoordinate,elementOnContainer)
        ) {
            binding.container.removeView(myTank)
            binding.container.addView(myTank)
        } else {
            (myTank.layoutParams as FrameLayout.LayoutParams).topMargin = currentCoordinate.top
            (myTank.layoutParams as FrameLayout.LayoutParams).leftMargin = currentCoordinate.left
        }
    }

    private fun checkTankCanMoveThroughMaterial(coordinate: Coordinate, elementOnContainer: List<Element>): Boolean {
        getTankCoordinates(coordinate).forEach {
            val element = getElementByCoordinates(it, elementOnContainer)
            if (element != null && !element.material.tankCanGoThrough) {
                return false
            }
        }
        return true
    }

    private fun getTankCoordinates(topLeftCoordinate: Coordinate): List<Coordinate> {
        val coordinateList = mutableListOf<Coordinate>()
        coordinateList.add(topLeftCoordinate)
        coordinateList.add(Coordinate(topLeftCoordinate.top + CELL_SIZE, topLeftCoordinate.left))
        coordinateList.add(Coordinate(topLeftCoordinate.top, topLeftCoordinate.left + CELL_SIZE))
        coordinateList.add(
            Coordinate(
                topLeftCoordinate.top + CELL_SIZE,
                topLeftCoordinate.left + CELL_SIZE
            )
        )
        return coordinateList
    }

    private fun getElementByCoordinates(coordinate: Coordinate, elementOnContainer: List<Element>) =
        elementOnContainer.firstOrNull { it.coordinate == coordinate }
}