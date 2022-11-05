package com.example.lostdog.ui.objects

import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.drawerlayout.widget.DrawerLayout
import com.example.lostdog.R
import com.example.lostdog.ui.fragments.HomeFragment
import com.example.lostdog.ui.fragments.ProfileFragment
import com.example.lostdog.ui.fragments.UserPostFragment
import com.example.lostdog.utilities.APP_ACTIVITY
import com.example.lostdog.utilities.USER
import com.example.lostdog.utilities.downloadAndSetImage
import com.example.lostdog.utilities.replaceFragment
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader
import com.mikepenz.materialdrawer.util.DrawerImageLoader

class AppDrawer() {
    private lateinit var mDrawer: Drawer
    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var mHeader: AccountHeader
    private lateinit var mCurrentProfile: ProfileDrawerItem

    fun create() {
        initLoader()
        createHeader()
        createDrawer()

        mDrawerLayout = mDrawer.drawerLayout
    }

    fun enableDrawer() {
        APP_ACTIVITY.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        mDrawer.actionBarDrawerToggle?.isDrawerIndicatorEnabled = true
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        APP_ACTIVITY.mToolbar.setNavigationOnClickListener {
            mDrawer.openDrawer()
        }
    }

    fun disableDrawer() {
        mDrawer.actionBarDrawerToggle?.isDrawerIndicatorEnabled = false
        APP_ACTIVITY.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        APP_ACTIVITY.mToolbar.setNavigationOnClickListener {
            APP_ACTIVITY.supportFragmentManager.popBackStack()
        }
    }

    private fun createDrawer() {
        mDrawer = DrawerBuilder()
            .withActivity(APP_ACTIVITY)
            .withToolbar(APP_ACTIVITY.mToolbar)
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
                    .withName("Мои посты")
                    .withSelectable(false),
                ProfileDrawerItem()
                    .withIdentifier(102)
                    .withName("Профиль")
                    .withSelectable(false),
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
                    clickToItem(position)

                    return false
                }
            }).build()
    }

    private fun clickToItem(position: Int) {
        when (position) {
            1 -> APP_ACTIVITY.replaceFragment(HomeFragment())
            2 -> APP_ACTIVITY.replaceFragment(UserPostFragment())
            3 -> APP_ACTIVITY.replaceFragment(ProfileFragment())
        }
    }

    private fun createHeader() {
        mCurrentProfile = ProfileDrawerItem()
            .withName(USER.full_name)
            .withEmail(USER.phone)
            .withIcon(USER.photo_url)
            .withIdentifier(200)

        mHeader = AccountHeaderBuilder()
            .withActivity(APP_ACTIVITY)
            .withHeaderBackground(R.drawable.header)
            .addProfiles(mCurrentProfile)
            .build()
    }

    fun updateHeader() {
        mCurrentProfile
            .withName(USER.full_name)
            .withEmail(USER.phone)
            .withIcon(USER.photo_url)

        mHeader.updateProfile(mCurrentProfile)
    }

    private fun initLoader() {
        DrawerImageLoader.init(object : AbstractDrawerImageLoader() {
            override fun set(imageView: ImageView, uri: Uri, placeholder: Drawable) {
                imageView.downloadAndSetImage(uri.toString())
            }
        })
    }
}