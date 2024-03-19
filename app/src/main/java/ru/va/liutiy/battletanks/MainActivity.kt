package ru.va.liutiy.battletanks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_DPAD_DOWN
import android.view.KeyEvent.KEYCODE_DPAD_LEFT
import android.view.KeyEvent.KEYCODE_DPAD_RIGHT
import android.view.KeyEvent.KEYCODE_DPAD_UP
import android.view.KeyEvent.KEYCODE_SPACE
import android.view.Menu
import android.view.MenuItem
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import ru.va.liutiy.battletanks.enums.Direction.UP
import ru.va.liutiy.battletanks.enums.Direction.DOWN
import ru.va.liutiy.battletanks.enums.Direction.LEFT
import ru.va.liutiy.battletanks.enums.Direction.RIGHT
import ru.va.liutiy.battletanks.databinding.ActivityMainBinding
import ru.va.liutiy.battletanks.drawers.BulletDrawer
import ru.va.liutiy.battletanks.drawers.ElementsDrawer
import ru.va.liutiy.battletanks.drawers.GridDrawer
import ru.va.liutiy.battletanks.drawers.TankDrawer
import ru.va.liutiy.battletanks.enums.Material

const val CELL_SIZE = 50

lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var editMode = false
    private val gridDrawer by lazy {
        GridDrawer(binding.container)
    }

    private val elementsDrawer by lazy {
        ElementsDrawer(binding.container)
    }

    private val tankDrawer by lazy {
        TankDrawer(binding.container)
    }

    private val bulletDrawer by lazy {
        BulletDrawer(binding.container)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Menu"

        binding.editorClear.setOnClickListener { elementsDrawer.currentMaterial = Material.EMPTY }
        binding.editorBrick.setOnClickListener { elementsDrawer.currentMaterial = Material.BRICK }
        binding.editorConcrete.setOnClickListener {
            elementsDrawer.currentMaterial = Material.CONCRETE
        }
        binding.editorGrass.setOnClickListener { elementsDrawer.currentMaterial = Material.GRASS }
        binding.container.setOnTouchListener { _, event ->
            elementsDrawer.onTouchContainer(event.x, event.y)
            return@setOnTouchListener true
        }
    }

    private fun switchEditMode() {
        if (editMode) {
            gridDrawer.removeGrid()
            binding.materialsContainer.visibility = INVISIBLE
        } else {
            gridDrawer.drawGrid()
            binding.materialsContainer.visibility = VISIBLE
        }
        editMode = !editMode
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.settings, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_settings -> {
                switchEditMode()
                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when (keyCode) {
            KEYCODE_DPAD_UP -> tankDrawer.move(binding.myTank, UP, elementsDrawer.elementOnContainer)
            KEYCODE_DPAD_DOWN -> tankDrawer.move(binding.myTank, DOWN, elementsDrawer.elementOnContainer)
            KEYCODE_DPAD_LEFT -> tankDrawer.move(binding.myTank, LEFT, elementsDrawer.elementOnContainer)
            KEYCODE_DPAD_RIGHT -> tankDrawer.move(binding.myTank, RIGHT, elementsDrawer.elementOnContainer)

            KEYCODE_SPACE -> bulletDrawer.makeBulletMove(binding.myTank, tankDrawer.currentDirection)
        }
        return super.onKeyDown(keyCode, event)
    }




}