package com.example.lostdog.ui.objects

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.lostdog.R
import com.example.lostdog.ui.fragments.HomeFragment
import com.example.lostdog.ui.fragments.SettingsFragment
import com.example.lostdog.utilities.replaceFragment
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem

class AppDrawer(val mainActivity: AppCompatActivity, val toolbar: Toolbar) {
    private lateinit var mDrawer: Drawer
    private lateinit var mHeader: AccountHeader

    fun create() {
        createHeader()
        createDrawer()
    }

    private fun createDrawer() {
        mDrawer = DrawerBuilder()
            .withActivity(mainActivity)
            .withToolbar(toolbar)
            .withActionBarDrawerToggle(true)
            .withSelectedItem(-1)
            .withAccountHeader(mHeader)
            .addDrawerItems(
                ProfileDrawerItem()
                    .withIdentifier(100)
                    .withName("Главная")
                    .withSelectable(false),
                ProfileDrawerItem()
                    .withIdentifier(101)
                    .withName("Добавить")
                    .withSelectable(false),
                ProfileDrawerItem()
                    .withIdentifier(102)
                    .withName("Профиль")
                    .withSelectable(false),
                ProfileDrawerItem()
                    .withIdentifier(103)
                    .withName("Настройки")
                    .withSelectable(false),
                DividerDrawerItem(),
                ProfileDrawerItem()
                    .withIdentifier(104)
                    .withName("О приложении")
                    .withSelectable(false),
            ).withOnDrawerItemClickListener(object : Drawer.OnDrawerItemClickListener {
                override fun onItemClick(
                    view: View?,
                    position: Int,
                    drawerItem: IDrawerItem<*>
                ): Boolean {
                    when (position) {
                        1 -> mainActivity.replaceFragment(HomeFragment())
                        4 -> mainActivity.replaceFragment(SettingsFragment())
                    }
                    return false
                }
            }).build()
    }

    private fun createHeader() {
        mHeader = AccountHeaderBuilder()
            .withActivity(mainActivity)
            .withHeaderBackground(R.drawable.header)
            .addProfiles(
                ProfileDrawerItem()
                    .withName("Anton Fedorov")
                    .withEmail("+79991112233")
            ).build()
    }

}