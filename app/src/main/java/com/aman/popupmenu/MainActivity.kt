package com.aman.popupmenu

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.aman.popupmenu.databinding.ActivityMainBinding
import com.aman.popupmenu.databinding.LayoutPopUpWindowBinding

class MainActivity : AppCompatActivity() {
    val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    lateinit var popUpMenu: PopupMenu
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnPopUpMenu.setOnClickListener {
            var popUpMenu = PopupMenu(this@MainActivity, it)
            popUpMenu.inflate(R.menu.pop_up_menu)
            popUpMenu.setOnMenuItemClickListener {
                return@setOnMenuItemClickListener true
            }
            popUpMenu.show()
        }

        binding.btnIconPopUpLayout.setOnClickListener {
            var popUpMenu = PopupMenu(this@MainActivity, it)
            popUpMenu.inflate(R.menu.pop_up_menu)
            popUpMenu.setOnMenuItemClickListener {
                return@setOnMenuItemClickListener true
            }

            try {
                val popUp = PopupMenu::class.java.getDeclaredField("mPopup")
                popUp.isAccessible = true
                val menu = popUp.get(popUpMenu)
                menu.javaClass
                    .getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                    .invoke(menu, true)
            } catch (exception: Exception) {
            } finally {
                popUpMenu.show()
            }
        }

        binding.btnCustomPopUpLayout.setOnClickListener {
            var popUpBinding = LayoutPopUpWindowBinding.inflate(layoutInflater)
            var popupWindow = PopupWindow(popUpBinding.root,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true)
            popupWindow.showAsDropDown(it)
            popupWindow.showAtLocation(it, Gravity.CENTER, 0, 0)
            popUpBinding.llPlay.setOnClickListener {
                popupWindow.dismiss()

            }
        }
    }
}