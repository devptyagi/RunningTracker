package com.devtyagi.runningtracker.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.devtyagi.runningtracker.R
import com.devtyagi.runningtracker.other.Constants.KEY_NAME
import com.devtyagi.runningtracker.other.Constants.KEY_WEIGHT
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.fragment_setup.etName
import kotlinx.android.synthetic.main.fragment_setup.etWeight
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings) {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadDataFromSharedPrefs()

        btnApplyChanges.setOnClickListener {
            val success = applyChangesToSharedPrefs()
            if(success) {
                Snackbar.make(view, "Changes Saved!", Snackbar.LENGTH_LONG).show()
            } else {
                Snackbar.make(view, "All fields are required!", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun applyChangesToSharedPrefs() : Boolean {
        val name = etName.text.toString()
        val weight = etWeight.text.toString()
        if(name.isEmpty() || weight.isEmpty()) {
            return false
        }
        sharedPreferences.edit()
            .putString(KEY_NAME, name)
            .putFloat(KEY_WEIGHT, weight.toFloat())
            .apply()
        val toolbarText = "Hey, $name"
        requireActivity().tvToolbarTitle.text = toolbarText
        return true
    }

    private fun loadDataFromSharedPrefs() {
        val name = sharedPreferences.getString(KEY_NAME, "")
        val weight = sharedPreferences.getFloat(KEY_WEIGHT, 0f)
        etName.setText(name)
        etWeight.setText(weight.toString())
    }

}