package ru.va.liutiy.battletanks.drawers

import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.view.marginLeft
import androidx.core.view.marginTop
import ru.va.liutiy.battletanks.CELL_SIZE
import ru.va.liutiy.battletanks.R
import ru.va.liutiy.battletanks.binding
import ru.va.liutiy.battletanks.enums.Direction
import ru.va.liutiy.battletanks.enums.Material
import ru.va.liutiy.battletanks.models.Coordinate
import ru.va.liutiy.battletanks.models.Element


class ElementsDrawer(val container: FrameLayout) {
    var currentMaterial = Material.EMPTY
    val elementOnContainer = mutableListOf<Element>()

    fun onTouchContainer(x: Float, y: Float) {
        val topMargin = y.toInt() - (y.toInt() % CELL_SIZE)
        val leftMargin = x.toInt() - (x.toInt() % CELL_SIZE)
        val coordinate = Coordinate(topMargin, leftMargin)
        if(currentMaterial == Material.EMPTY){
            eraseView(coordinate)
        } else {
            drawOrReplaceView(coordinate)
        }
    }

    private fun drawOrReplaceView(coordinate: Coordinate){
        val viewOnCoordinate = getElementByCoordinates(coordinate)
        if(viewOnCoordinate == null){
            drawView(coordinate)
            return
        }
        if(viewOnCoordinate.material != currentMaterial){
            replaceView(coordinate)
        }
    }

    private fun replaceView(coordinate: Coordinate){
        eraseView(coordinate)
        drawView(coordinate)
    }

    private fun eraseView(coordinate: Coordinate){
        val elementOnCoordinate = getElementByCoordinates(coordinate)
        if(elementOnCoordinate != null){
            val erasingView = container.findViewById<View>(elementOnCoordinate.viewId)
            container.removeView(erasingView)
            elementOnContainer.remove(elementOnCoordinate)
        }

    }

    fun drawView(coordinate: Coordinate) {
        val view = ImageView(container.context)
        val layoutParams = FrameLayout.LayoutParams(CELL_SIZE, CELL_SIZE)
        when (currentMaterial) {
            Material.EMPTY -> {

            }

            Material.BRICK -> view.setImageResource(R.drawable.brick)
            Material.CONCRETE -> view.setImageResource(R.drawable.concrete)
            Material.GRASS -> view.setImageResource(R.drawable.grass)
        }
        layoutParams.topMargin = coordinate.top
        layoutParams.leftMargin = coordinate.left
        val viewId = View.generateViewId()
        view.id = viewId
        view.layoutParams = layoutParams
        container.addView(view)
        elementOnContainer.add(Element(viewId, currentMaterial, coordinate))

    }

    private fun getElementByCoordinates(coordinate: Coordinate) =
        elementOnContainer.firstOrNull { it.coordinate == coordinate }
}