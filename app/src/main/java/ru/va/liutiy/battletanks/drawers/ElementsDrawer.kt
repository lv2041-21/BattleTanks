package ru.va.liutiy.battletanks.drawers

import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import ru.va.liutiy.battletanks.CELL_SIZE
import ru.va.liutiy.battletanks.R
import ru.va.liutiy.battletanks.enums.Material
import ru.va.liutiy.battletanks.models.Coordinate
import ru.va.liutiy.battletanks.models.Element


class ElementsDrawer (val container: FrameLayout){
    var currentMaterial = Material.EMPTY
    private val elementOnContainer = mutableListOf<Element>()

    fun onTouchContainer(x:Float, y:Float){
        val topMargin = y.toInt() - (y.toInt() % CELL_SIZE)
        val leftMargin = x.toInt() - (x.toInt() % CELL_SIZE)
        val coordinate = Coordinate(topMargin, leftMargin)
        drawView(coordinate)
    }

    fun drawView(coordinate: Coordinate){
        val view = ImageView(container.context)
        val layoutParams = FrameLayout.LayoutParams(CELL_SIZE, CELL_SIZE)
        when(currentMaterial){
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
}