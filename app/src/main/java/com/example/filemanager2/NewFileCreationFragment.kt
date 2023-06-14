package com.example.filemanager2

//import com.example.filemanager2.databinding.ActivityNewFileCreationBinding
import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputLayout
import java.util.*


class NewFileCreationFragment : BottomSheetDialogFragment() {
    //    private lateinit var newFileCreationBinding: ActivityNewFileCreationBinding
    private lateinit var timePickerDialog: TimePickerDialog
    internal val newFileCreationViewModel: NewFIleCreationViewModel by activityViewModels()
    private lateinit var addFileListener: AddFileListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addFileListener = newFileCreationViewModel.getListener()
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.new_file_creation_fragment, container, false)
        val numberSpinner = view.findViewById<Spinner>(R.id.numberSpinner)
        val unitSpinner = view.findViewById<Spinner>(R.id.unitSpinner)
        val filepathSpinner = view.findViewById<Spinner>(R.id.filePathSpinner)
        val closeButton = view.findViewById<ImageView>(R.id.closeButton)

        closeButton.setOnClickListener {
            this.dismiss()
        }

        val numbers = (1..100).toList()
        val units = listOf("MB", "GB")

        val numberAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, numbers)
        numberAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        numberSpinner.adapter = numberAdapter

        val unitAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, units)
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        unitSpinner.adapter = unitAdapter

        val paths = listOf("/images/", "/images/photos/", "/files/archives/", "/music/albums/")

        val pathAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, paths)
        pathAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        filepathSpinner.adapter = pathAdapter

        val lastModifiedTime = view.findViewById<Button>(R.id.lastModifiedTime)

        lastModifiedTime.setOnClickListener {
            Log.d("OnClickLastModifiedTime", "OnClickLastModifiedTime")
            timePickerDialog = TimePickerDialog(
                requireContext(),
                { _, hourOfDay, minute ->
                    Log.d("OnClickLastModifiedTime", "OnClickLastModifiedTime")

//                        val editableTime = Editable.Factory.getInstance().newEditable(formattedTime)
                    lastModifiedTime.text = "$hourOfDay : $minute"
                    view.findViewById<TextView>(R.id.LastModifiedMandatoryField).text = ""
                },
                0, 0, false
            )
            timePickerDialog.setTitle("select time")
            timePickerDialog.show()
        }

//        checkSubmit(view)
        val detailsSubmit: Button = view.findViewById(R.id.detailsSubmit)

        detailsSubmit.setOnClickListener {
            Log.i("onclickInDetailsSubmit", "onclickInDetailsSubmit")
            val submit = submit(view)
            if (!submit) {
                Toast.makeText(requireContext(), "Fill all Mandatory Items", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val fileNameText: EditText = view.findViewById(R.id.fileNameText)
                val fileTypeText: RadioGroup = view.findViewById(R.id.fileTypeRadio)
                val selectedRadioButtonId = fileTypeText.checkedRadioButtonId
                val selectedRadioButton = view.findViewById<RadioButton>(selectedRadioButtonId)

                val fileSize: String = numberSpinner.selectedItem.toString()+ " " + unitSpinner.selectedItem.toString()

                var selectedFileType:FileType?=null

                when (selectedRadioButton.text) {
                    "DOC" -> {
                        selectedFileType = FileType.Doc
                    }
                    "DOCX" -> {
                        selectedFileType = FileType.Docx
                    }
                    "TXT" -> {
                        selectedFileType = FileType.Txt
                    }
                }
                val file = File(
                    (fileNameText as TextView).text.toString(),
                    selectedFileType!!,
                    lastModifiedTime.text.toString(),
                    fileSize,
                    filepathSpinner.selectedItem.toString()
                )
                Log.i("onclickInDetailsSubmit", "onclickInDetailsSubmit added ")
                addFileListener.addFile(file)
                dismiss()
            }

        }
        return view
    }

//    private fun checkSubmit(view: View) {
//
//        fileNameInputTextOnFocusListener()
//        lastModifiedTimeInputTextOnFocusListener()
//        fileSizeInputTextOnFocusListener()
//        filePathInputTextOnFocusListener()

//    }

    private fun submit(view: View): Boolean {
//        newFileCreationBinding.fileNameContainer.helperText = checkFileNameNull(view)
//        newFileCreationBinding.fileTypeContainer.helperText = checkFileTypeNull()
//        newFileCreationBinding.lastModifiedTimeContainer.helperText =
//            checkLastModifiedTimeNull(view)
//        newFileCreationBinding.fileSizeContainer.helperText = checkFileSizeNull()
//        newFileCreationBinding.filePathContainer.helperText = checkFilePathNull()

        val checkIfFileNameIsNotNull =
            checkFileNameNull(view) == null
//        val checkIfFileTypeIsNotNull =
//            newFileCreationBinding.fileTypeContainer.helperText == null
        val checkIflastModifiedTimeIsNotNull =
            checkLastModifiedTimeNull(view) == null
//        val checkIfFileSizeIsNotNull =
//            checkFileSizeNull(view) == null
//        val checkIfFilePathIsNotNull =
//            checkFilePathNull(view) == null

        if (checkIfFileNameIsNotNull && checkIflastModifiedTimeIsNotNull) {
            return true
        }
        return false

    }

    //
//    private fun checkFilePathNull(view: View): String? {
//        val filePath = view.findViewById<Spinner>(R.id.filePathSpinner).selectedItem.toString()
//        if (filePath.isNotEmpty()) {
//            return null
//        } else {
//            return "Required"
//        }
//    }

//    private fun checkFileSizeNull(view: View): String? {
//        val fileSize = view.findViewById<Spinner>(R.id.numberSpinner).selectedItem.toString()
//
//        if (fileSize.isNotEmpty()) {
//            return null
//        } else {
//            return "Required"
//        }
//    }

    private fun checkLastModifiedTimeNull(view: View): String? {
        val lastModifiedTime = view.findViewById<Button>(R.id.lastModifiedTime).text.toString()

        return if (lastModifiedTime.isNotEmpty()) {
            null
        } else {
            view.findViewById<TextView>(R.id.LastModifiedMandatoryField).text = "*"
            "Required"
        }
    }

//    private fun checkFileTypeNull(): String? {
//        val fileTypeText: RadioGroup = findViewById(R.id.fileTypeRadio)
//        val selectedRadioButtonId = fileTypeText.checkedRadioButtonId
//
//        if (selectedRadioButtonId != null) {
//            return null
//        } else {
//            return "Required"
//        }
//
//    }

    private fun checkFileNameNull(view: View): String? {
        val fileNameText = view.findViewById<EditText>(R.id.fileNameText).text.toString()

        return if (fileNameText.isNotEmpty()) {
            null
        } else {
            view.findViewById<TextInputLayout>(R.id.fileNameContainer).helperText = "*"
            "Required"
        }
    }

//    private fun fileNameInputTextOnFocusListener() {
//        newFileCreationBinding.fileNameText.setOnFocusChangeListener { _, focused ->
//            if (!focused) {
//                Log.i("check", "here in filename")
//                newFileCreationBinding.fileNameContainer.helperText = checkFileNameNull()
//            }
//        }
//    }
//
////    private fun fileTypeInputTextOnFocusListener() {
////        val fileTypeText: RadioGroup = newFileCreationBinding.fileTypeRadio
////        val selectedRadioButtonId = fileTypeText.checkedRadioButtonId
////        if (selectedRadioButtonId!=null){
////            newFileCreationBinding.fileTypeContainer.helperText =
////        }
////    }
//
//    private fun lastModifiedTimeInputTextOnFocusListener() {
//        newFileCreationBinding.lastModifiedTime.setOnFocusChangeListener { _, focused ->
//            if (!focused) {
//                newFileCreationBinding.lastModifiedTimeContainer.helperText =
//                    checkLastModifiedTimeNull()
//            }
//        }
//    }
//
//    private fun fileSizeInputTextOnFocusListener() {
//        newFileCreationBinding.fileSize.setOnFocusChangeListener { _, focused ->
//            if (!focused) {
//                newFileCreationBinding.fileSizeContainer.helperText = checkFileSizeNull()
//            }
//        }
//    }
//
//    private fun filePathInputTextOnFocusListener() {
//        newFileCreationBinding.filePath.setOnFocusChangeListener { _, focused ->
//            if (!focused) {
//                newFileCreationBinding.filePathContainer.helperText = checkFilePathNull()
//            }
//        }
//    }
}