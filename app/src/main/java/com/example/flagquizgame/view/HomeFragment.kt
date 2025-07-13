package com.example.flagquizgame.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.flagquizgame.R
import com.example.flagquizgame.databinding.FragmentHomeBinding
import com.techmania.flagquizwithsqlitedemo.DatabaseCopyHelper
import java.io.IOException


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        creatAndOpenDataBase()
        binding.start.setOnClickListener {

            val direction = HomeFragmentDirections.actionHomeFragmentToQuizFragment()
            this.findNavController().navigate(direction)
        }

        return binding.root
    }

    private fun creatAndOpenDataBase() {
        val helper = DatabaseCopyHelper(requireContext())

        try {
            helper.createDataBase()
            helper.openDataBase()
            Toast.makeText(requireActivity(), "${getRowCount(helper)} records found", Toast.LENGTH_LONG).show()

        }catch (e : IOException) {
            Toast.makeText(requireActivity(),"base not created",Toast.LENGTH_LONG).show()
        }

    }
    fun getRowCount(helper: DatabaseCopyHelper): Int {
        var count = 0
        try {
            val db = helper.readableDatabase
            val cursor = db.rawQuery("SELECT COUNT(*) FROM flags", null)
            if (cursor.moveToFirst()) {
                count = cursor.getInt(0)
            }
            cursor.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return count
    }

}