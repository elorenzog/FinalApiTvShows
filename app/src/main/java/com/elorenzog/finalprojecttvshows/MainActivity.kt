package com.elorenzog.finalprojecttvshows

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.elorenzog.finalprojecttvshows.databinding.ActivityMainBinding
import com.elorenzog.finalprojecttvshows.fragments.LoginFragment
import com.elorenzog.finalprojecttvshows.viewmodel.EpisoDateViewModel

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var actionBar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel = ViewModelProvider(this).get(EpisoDateViewModel::class.java)

        // Configure actionbar
        actionBar = supportActionBar!!
        actionBar.hide()


        val login = ""

        val bundle = Bundle()
//        bundle.put("value", this)
        bundle.putString("hello", login)

//        setupActionBarWithNavController(findNavController(R.id.fragmentContainer))

        val fragment = LoginFragment()
        fragment.arguments = bundle
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainerView, fragment)
        transaction.addToBackStack(null)
        transaction.commit()

    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragmentContainerView)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}