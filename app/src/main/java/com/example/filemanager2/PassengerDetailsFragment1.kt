package com.example.filemanager2

import android.app.ActionBar
import android.graphics.Typeface
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.example.filemanager2.databinding.FragmentPassengerDetailsBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class PassengerDetailsFragment1(private val selectedSeats: List<Int>) : Fragment() {

    private lateinit var passengerDetailsBinding: FragmentPassengerDetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        passengerDetailsBinding =
            FragmentPassengerDetailsBinding.inflate(inflater, container, false)

        val passengerDetailsLayout = passengerDetailsBinding.PassengerDetailsLinearLayout

        val linearLayoutParent = LinearLayout(requireContext())
        linearLayoutParent.layoutParams = LinearLayout.LayoutParams(
            ActionBar.LayoutParams.MATCH_PARENT,
            ActionBar.LayoutParams.MATCH_PARENT
        )

        val editTextMap = mutableMapOf<Int, TextInputEditText>()
        val genderMap = mutableMapOf<Int, RadioGroup>()
        val ageTextMap = mutableMapOf<Int, TextInputEditText>()

        linearLayoutParent.orientation = LinearLayout.VERTICAL
        (linearLayoutParent.layoutParams as LinearLayout.LayoutParams).setMargins(10, 10, 10, 10)

        for ((index, seatNumber) in selectedSeats.withIndex()) {

            val cardView = CardView(requireContext())
            val cardViewLayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
            cardViewLayoutParams.setMargins(0, 16, 0, 16)

            cardView.layoutParams = cardViewLayoutParams

            cardView.cardElevation = 8f
            cardView.radius = 8f


            val linearLayoutChild = LinearLayout(requireContext())
            linearLayoutChild.layoutParams = LinearLayout.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.WRAP_CONTENT
            )
            linearLayoutChild.orientation = LinearLayout.VERTICAL
            (linearLayoutChild.layoutParams as LinearLayout.LayoutParams).setMargins(
                0,
                20,
                0,
                20
            )

            //passenger seat info
            val passengerSeatInfoLinearLayout = LinearLayout(requireContext())
            if (index == 0) {
                //add textView
                val primaryPassengerTv: TextView = TextView(requireContext())
                val primaryPassengerLayoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )

                primaryPassengerLayoutParams.setMargins(16, 8, 16, 8)

                primaryPassengerTv.layoutParams = primaryPassengerLayoutParams

                val typeface: Typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD_ITALIC)
                primaryPassengerTv.typeface = typeface
                primaryPassengerTv.text = getString(R.string.primary_passenger_details)
                passengerSeatInfoLinearLayout.addView(primaryPassengerTv)
            } else {
                //add textView
                val coPassengerTv: TextView = TextView(requireContext())

                val coPassengerLayoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )

                coPassengerLayoutParams.setMargins(16, 8, 16, 8)

                coPassengerTv.layoutParams = coPassengerLayoutParams
                val typeface: Typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD_ITALIC)
                coPassengerTv.typeface = typeface
                coPassengerTv.text = getString(R.string.co_passenger_details)
                passengerSeatInfoLinearLayout.addView(coPassengerTv)
            }
            val seatNumberTv = TextView(requireContext())
            "Seat $seatNumber".also { seatNumberTv.text = it }

            passengerSeatInfoLinearLayout.addView(seatNumberTv)

            linearLayoutChild.addView(passengerSeatInfoLinearLayout)

            // Create TextInputLayout
            val passengerNameTextInputLayout = TextInputLayout(requireContext())
            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            passengerNameTextInputLayout.layoutParams = layoutParams

            // Create TextInputEditText
            val textInputEditText = TextInputEditText(requireContext())
            textInputEditText.hint = "Enter your name"
            textInputEditText.inputType = InputType.TYPE_CLASS_TEXT
            textInputEditText.id = View.generateViewId()
            editTextMap[textInputEditText.id] = textInputEditText

            passengerNameTextInputLayout.addView(textInputEditText)

            linearLayoutChild.addView(passengerNameTextInputLayout)


            //gender adding
            // Create RadioGroup

            val genderLinearLayout = LinearLayout(requireContext())
            genderLinearLayout.layoutParams = LinearLayout.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.WRAP_CONTENT
            )
            genderLinearLayout.orientation = LinearLayout.HORIZONTAL
            (genderLinearLayout.layoutParams as LinearLayout.LayoutParams).setMargins(
                0,
                10,
                20,
                10
            )

            val genderText: TextView = TextView(requireContext())
            genderText.text = getString(R.string.gender)
            genderLinearLayout.addView(genderText)

            val radioGroup = RadioGroup(requireContext())
            radioGroup.id = View.generateViewId()
            val genderRadioGroupLayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            radioGroup.layoutParams = genderRadioGroupLayoutParams

            // Create Male RadioButton
            val maleRadioButton = RadioButton(requireContext())
            maleRadioButton.text = getString(R.string.male)

            // Create Female RadioButton
            val femaleRadioButton = RadioButton(requireContext())
            femaleRadioButton.text = getString(R.string.female)


            // Add RadioButtons to the RadioGroup
            radioGroup.addView(maleRadioButton)
            radioGroup.addView(femaleRadioButton)

            genderMap[radioGroup.id] = radioGroup

            genderLinearLayout.addView(radioGroup)

            // Add RadioGroup to the parent layout
            linearLayoutChild.addView(genderLinearLayout)

            //for adding age

            val passengerAgeTextInputLayout = TextInputLayout(requireContext())
            val passengerAgeLayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            passengerAgeTextInputLayout.layoutParams = passengerAgeLayoutParams

            // Create TextInputEditText
            val ageInputEditText = TextInputEditText(requireContext())
            ageInputEditText.hint = "Age"
            ageInputEditText.inputType = InputType.TYPE_CLASS_NUMBER
            ageInputEditText.id = View.generateViewId()

            ageTextMap[ageInputEditText.id] = ageInputEditText

            passengerAgeTextInputLayout.addView(ageInputEditText)

            linearLayoutChild.addView(passengerAgeTextInputLayout)

            cardView.addView(linearLayoutChild)

            linearLayoutParent.addView(cardView)
        }

        passengerDetailsLayout.addView(linearLayoutParent)

        val button = passengerDetailsBinding.bookSeatButton

//        val editTextMap = mutableMapOf<Int, TextInputEditText>()
//        val genderMap = mutableMapOf<Int, RadioGroup>()
//        val ageTextMap = mutableMapOf<Int, TextInputEditText>()
        button.setOnClickListener {
            ageTextMap.values.forEach {
                if (it.text?.isEmpty() == true) {
                    Toast.makeText(requireContext(), "fill the Ages", Toast.LENGTH_SHORT).show()
                }
            }
            editTextMap.values.forEach {
                if (it.text?.isEmpty() == true) {
                    Toast.makeText(requireContext(), "fill the Names", Toast.LENGTH_SHORT).show()
                }
            }
//            Log.e("onclick","check ${genderMap.values.}")
//            genderMap.values.forEach {
//                if (it.checkedRadioButtonId == 3 || it.checkedRadioButtonId == 8){
//                    Toast.makeText(requireContext(), "fill the Gender", Toast.LENGTH_SHORT).show()
//                }
//            }
        }


        return passengerDetailsBinding.root
    }

}