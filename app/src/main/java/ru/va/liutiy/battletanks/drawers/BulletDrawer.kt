package ru.va.liutiy.battletanks.drawers

import android.app.Activity
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import ru.va.liutiy.battletanks.CELL_SIZE
import ru.va.liutiy.battletanks.R
import ru.va.liutiy.battletanks.enums.Direction
import ru.va.liutiy.battletanks.models.Coordinate
import ru.va.liutiy.battletanks.utils.checkViewCanMoveThroughBorder

private const val BULLET_WIDTH = 15
private const val BULLET_HEIGTH = 15

class BulletDrawer(val container: FrameLayout) {

    fun makeBulletMove(myTank: View, currentDirection: Direction) {
        Thread(Runnable {
            val bullet = createBullet(myTank, currentDirection)
            while (bullet.checkViewCanMoveThroughBorder(Coordinate(bullet.top,bullet.left))) {
                when (currentDirection) {
                    Direction.UP -> (bullet.layoutParams as FrameLayout.LayoutParams).topMargin -= BULLET_HEIGTH
                    Direction.DOWN -> (bullet.layoutParams as FrameLayout.LayoutParams).topMargin += BULLET_HEIGTH
                    Direction.LEFT -> (bullet.layoutParams as FrameLayout.LayoutParams).leftMargin -= BULLET_HEIGTH
                    Direction.RIGHT -> (bullet.layoutParams as FrameLayout.LayoutParams).leftMargin += BULLET_HEIGTH
                }
                Thread.sleep(30)
                (container.context as Activity).runOnUiThread {
                    container.removeView(bullet)
                    container.addView(bullet)
                }
            }
            (container.context as Activity).runOnUiThread {
                container.removeView(bullet)
            }
        }).start()
    }

    private fun createBullet(myTank: View, currentDirection: Direction): ImageView{
        return ImageView(container.context)
            .apply { this.setImageResource(R.drawable.bullet)
            this.layoutParams = FrameLayout.LayoutParams(BULLET_WIDTH, BULLET_HEIGTH)
                val bulletCoordinate = getBulletCoordinate(this, myTank, currentDirection)
                (this.layoutParams as FrameLayout.LayoutParams).topMargin = bulletCoordinate.top
                (this.layoutParams as FrameLayout.LayoutParams).leftMargin = bulletCoordinate.left
                this.rotation = currentDirection.rotation
            }
    }

    private fun getBulletCoordinate(bullet:ImageView, myTank:View, currentDirection: Direction): Coordinate{
        val tankLeftTopCoordinate = Coordinate(myTank.top, myTank.left)
        return when(currentDirection) {
            Direction.UP -> Coordinate(
                top = tankLeftTopCoordinate.top - bullet.layoutParams.height,
                left = getDistanceToMiddleOfTank(tankLeftTopCoordinate.left, bullet.layoutParams.width))

            Direction.DOWN -> Coordinate(
                top = tankLeftTopCoordinate.top + myTank.layoutParams.height,
                left = getDistanceToMiddleOfTank(tankLeftTopCoordinate.left, bullet.layoutParams.width))

            Direction.LEFT -> Coordinate(
                top = getDistanceToMiddleOfTank(tankLeftTopCoordinate.top, bullet.layoutParams.height),
                left = tankLeftTopCoordinate.left - bullet.layoutParams.width)

            Direction.RIGHT -> Coordinate(
                top = getDistanceToMiddleOfTank(tankLeftTopCoordinate.top, bullet.layoutParams.height),
                left = tankLeftTopCoordinate.left + myTank.layoutParams.width)
        }
    }

    private fun getDistanceToMiddleOfTank(startCoordinate: Int, bulletSize: Int): Int{
        return  startCoordinate + (CELL_SIZE - bulletSize / 2)
    }
}