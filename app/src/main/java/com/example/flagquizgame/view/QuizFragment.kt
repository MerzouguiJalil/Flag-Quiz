package com.example.flagquizgame.view

import android.graphics.Color
import android.graphics.Color.WHITE
import android.os.Bundle
import android.provider.ContactsContract.Data
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.flagquizgame.R
import com.example.flagquizgame.dataBase.FlagDAO
import com.example.flagquizgame.dataBase.FlagModel
import com.example.flagquizgame.databinding.FragmentQuizBinding
import com.techmania.flagquizwithsqlitedemo.DatabaseCopyHelper


class QuizFragment : Fragment() {

    lateinit var  binding : FragmentQuizBinding
    var  dao = FlagDAO()
    lateinit var recordList : ArrayList<FlagModel>
    var correct : Int = 0
    var empty : Int = 0
    var wrong : Int = 0
    var question : Int = 0
    var op : Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentQuizBinding.inflate(inflater,container,false)
        val dao = FlagDAO()
        recordList = dao.generate_ten_randmon(DatabaseCopyHelper(requireActivity()))

        show_data()

        binding.buttonnext.setOnClickListener {
            if(question >= 9) {
                if(! op) empty ++
                val bundle = Bundle().apply {
                    putInt("correct",correct)
                    putInt("wrong",wrong)
                    putInt("empty",empty)
                }
                this.findNavController().navigate(
                    R.id.action_quizFragment_to_resultFragment,
                    bundle ,
                    NavOptions.Builder().setPopUpTo(R.id.homeFragment,false).build()
                )

            }else {
                if(!op) empty ++
                modify_state(true)
                question++
                show_data()
                reset_states()
                op = false
            }

        }
        binding.buttonA.setOnClickListener {
            controle_answers(binding.buttonA)
        }
        binding.buttonB.setOnClickListener {
            controle_answers(binding.buttonB)
        }
        binding.buttonC.setOnClickListener {
            controle_answers(binding.buttonC)
        }
        binding.buttonD.setOnClickListener {
            controle_answers(binding.buttonD)
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun show_data(){

        val correct_answer = recordList[question]
        val mini_record = dao.generate_three_randmon(DatabaseCopyHelper(requireActivity()),correct_answer.flag_id)

        binding.imageView2.setImageResource(resources.getIdentifier(correct_answer.flag_name,"drawable",requireActivity().packageName))

        var mix = HashSet<FlagModel>()
        mix.add(correct_answer)
        mix.addAll(mini_record)

        var option = ArrayList<FlagModel>()
        for(i in mix){
            option.add(i)
        }

        binding.buttonA.text = option[0].country_name
        binding.buttonB.text = option[1].country_name
        binding.buttonC.text = option[2].country_name
        binding.buttonD.text = option[3].country_name

        binding.correct.text = correct.toString()
        binding.wrong.text = wrong.toString()
        binding.empty.text = empty.toString()
        binding.Qestion.text = "Question ${question+1}"

    }
    private fun controle_answers(button : Button){

        modify_state(false)
        op = true
        val correct_answer = recordList[question]
        if(correct_answer.country_name == button.text){
            correct ++
            button.setBackgroundColor(Color.GREEN)
        }else {
            wrong++
            button.setBackgroundColor(Color.RED)
            button.setTextColor(Color.WHITE)
            when(correct_answer.country_name){
                binding.buttonA.text -> binding.buttonA.setBackgroundColor(Color.GREEN)
                binding.buttonB.text -> binding.buttonB.setBackgroundColor(Color.GREEN)
                binding.buttonC.text -> binding.buttonC.setBackgroundColor(Color.GREEN)
                binding.buttonD.text -> binding.buttonD.setBackgroundColor(Color.GREEN)
            }
        }
        binding.correct.text = correct.toString()
        binding.wrong.text = wrong.toString()

    }
    private fun modify_state(state : Boolean) {
        binding.buttonA.isClickable = state
        binding.buttonB.isClickable = state
        binding.buttonC.isClickable = state
        binding.buttonD.isClickable = state
    }
    private fun reset_states() {
        binding.buttonA.setBackgroundColor(Color.WHITE)
        binding.buttonB.setBackgroundColor(Color.WHITE)
        binding.buttonC.setBackgroundColor(Color.WHITE)
        binding.buttonD.setBackgroundColor(Color.WHITE)

        binding.buttonA.setTextColor(ContextCompat.getColor(requireContext(), R.color.pink))
        binding.buttonB.setTextColor(ContextCompat.getColor(requireContext(), R.color.pink))
        binding.buttonC.setTextColor(ContextCompat.getColor(requireContext(), R.color.pink))
        binding.buttonD.setTextColor(ContextCompat.getColor(requireContext(), R.color.pink))

    }

}