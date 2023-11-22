package com.itsi.almuntaqimorevn

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Handler(Looper.getMainLooper()).postDelayed({
            //findNavController().navigate(R.id.action_splashFragment_to_secondFragment)
        }, 3000)
        val view = inflater.inflate(R.layout.activity_splash, container, false)



        return view
    }

}