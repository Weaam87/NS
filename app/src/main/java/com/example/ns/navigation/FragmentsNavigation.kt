package com.example.ns.navigation

import androidx.fragment.app.Fragment

interface FragmentsNavigation {

    fun navigateFragment(fragment : Fragment , addToStack : Boolean)
}