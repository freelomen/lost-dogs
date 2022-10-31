package com.example.lostdog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.lostdog.activities.RegisterActivity
import com.example.lostdog.databinding.ActivityMainBinding
import com.example.lostdog.utilities.AUTH
import com.example.lostdog.utilities.replaceActivity
import com.example.lostdog.utilities.showToast
import com.google.firebase.auth.FirebaseAuth
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mDrawer: Drawer
    private lateinit var mHeader: AccountHeader
    private lateinit var mToolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }

    override fun onStart() {
        super.onStart()
        initFields()
        initFunc()

        main_button_out.setOnClickListener {
            AUTH.signOut()
            replaceActivity(RegisterActivity())
        }
    }

    private fun initFunc() {
        if (AUTH.currentUser != null) {
            setSupportActionBar(mToolbar)
            createHeader()
            createDrawer()
        } else {
            replaceActivity(RegisterActivity())
        }
    }

    private fun createDrawer() {
        mDrawer = DrawerBuilder()
            .withActivity(this)
            .withToolbar(mToolbar)
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
                    Toast.makeText(applicationContext, position.toString(), Toast.LENGTH_SHORT).show()
                    return false
                }
            }).build()
    }

    private fun createHeader() {
        mHeader = AccountHeaderBuilder()
            .withActivity(this)
            .withHeaderBackground(R.drawable.header)
            .addProfiles(
                ProfileDrawerItem()
                    .withName("Anton Fedorov")
                    .withEmail("+79991112233")
            ).build()
    }

    private fun initFields() {
        AUTH = FirebaseAuth.getInstance()
        AUTH.setLanguageCode("ru")
        mToolbar = mBinding.mainToolbar
    }
}