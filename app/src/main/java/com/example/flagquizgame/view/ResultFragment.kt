package com.example.flagquizgame.view

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.flagquizgame.MainActivity
import com.example.flagquizgame.R
import com.example.flagquizgame.databinding.FragmentHomeBinding
import com.example.flagquizgame.databinding.FragmentResultBinding
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry


class ResultFragment : Fragment() {
    lateinit var binding: FragmentResultBinding
    var correct : Float = 0f
    var wrong : Float = 0f
    var empty : Float = 0f


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val mainActivity : MainActivity = activity as MainActivity
        mainActivity.layout.setBackgroundColor(Color.WHITE)
        binding = FragmentResultBinding.inflate(inflater,container, false)

        correct = arguments?.getInt("correct")!!.toFloat()
        wrong = arguments?.getInt("wrong")!!.toFloat()
        empty = arguments?.getInt("empty")!!.toFloat()

        val bar_char_correct = ArrayList<BarEntry>()
        val bar_char_wrong = ArrayList<BarEntry>()
        val bar_char_empty = ArrayList<BarEntry>()

        bar_char_correct.add(BarEntry(0f,correct))
        bar_char_wrong.add(BarEntry(1f,wrong))
        bar_char_empty.add(BarEntry(2f,empty))

        val bar_data_set_correct  = BarDataSet(bar_char_correct,"Correct").apply {
            color = Color.GREEN
            valueTextSize = 24f
            valueTextColor = Color.BLACK
        }
        val bar_data_set_wrong  = BarDataSet(bar_char_wrong,"Wrong").apply {
            color = Color.RED
            valueTextSize = 24f
            valueTextColor = Color.BLACK
        }
        val bar_data_set_empty  = BarDataSet(bar_char_empty,"Empty").apply {
            valueTextSize = 24f
            valueTextColor = Color.BLACK
        }

        val barData = BarData(bar_data_set_correct,bar_data_set_wrong,bar_data_set_empty)

        binding.ResultBar.data = barData
        binding.ResultBar.animateY(1000)


        binding.playAgain.setOnClickListener {
            this.findNavController().popBackStack(R.id.homeFragment,false)
        }
        binding.exit.setOnClickListener {
            requireActivity().finish()
        }


        return binding.root
    }

}