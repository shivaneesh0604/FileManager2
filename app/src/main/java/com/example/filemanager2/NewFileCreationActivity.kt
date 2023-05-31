package com.example.filemanager2

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.example.filemanager2.databinding.ActivityNewFileCreationBinding

class NewFileCreationActivity : AppCompatActivity() {
    private lateinit var newFileCreationBinding: ActivityNewFileCreationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newFileCreationBinding = ActivityNewFileCreationBinding.inflate(layoutInflater)
        setContentView(newFileCreationBinding.root)
        fileNameInputTextOnFocusListener()
        lastModifiedTimeInputTextOnFocusListener()
        fileSizeInputTextOnFocusListener()
        filePathInputTextOnFocusListener()

        val detailsSubmit: Button = findViewById(R.id.detailsSubmit)

        detailsSubmit.setOnClickListener {
            val submit = submit()
            if (!submit) {
                Toast.makeText(this, "Fill all Mandatory Items", Toast.LENGTH_SHORT).show()
            } else {
                val intent = intent
                val fileNameText: EditText = findViewById(R.id.fileNameText)
                val lastModifiedTimeText: EditText = findViewById(R.id.lastModifiedTime)
                val fileSizeText: EditText = findViewById(R.id.fileSize)
                val filePathText: EditText = findViewById(R.id.filePath)
                val fileTypeText: RadioGroup = findViewById(R.id.fileTypeRadio)
                val selectedRadioButtonId = fileTypeText.checkedRadioButtonId
                val selectedRadioButton = findViewById<RadioButton>(selectedRadioButtonId)

                intent.putExtra("fileName", fileNameText.text.toString())
                intent.putExtra("fileType", selectedRadioButton.text.toString().toLowerCase())
                intent.putExtra("lastModifiedTime", lastModifiedTimeText.text.toString())
                intent.putExtra("fileSize", fileSizeText.text.toString())
                intent.putExtra("filePath", filePathText.text.toString())
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }
    }

    private fun submit(): Boolean {
        newFileCreationBinding.fileNameContainer.helperText = checkFileNameNull()
//        newFileCreationBinding.fileTypeContainer.helperText = checkFileTypeNull()
        newFileCreationBinding.lastModifiedTimeContainer.helperText =
            checkLastModifiedTimeNull()
        newFileCreationBinding.fileSizeContainer.helperText = checkFileSizeNull()
        newFileCreationBinding.filePathContainer.helperText = checkFilePathNull()

        val checkIfFileNameIsNotNull = newFileCreationBinding.fileNameContainer.helperText == null
//        val checkIfFileTypeIsNotNull =
//            newFileCreationBinding.fileTypeContainer.helperText == null
        val checkIflastModifiedTimeIsNotNull =
            newFileCreationBinding.lastModifiedTimeContainer.helperText == null
        val checkIfFileSizeIsNotNull =
            newFileCreationBinding.fileSizeContainer.helperText == null
        val checkIfFilePathIsNotNull =
            newFileCreationBinding.filePathContainer.helperText == null

        if (checkIfFileNameIsNotNull && checkIflastModifiedTimeIsNotNull && checkIfFileSizeIsNotNull && checkIfFilePathIsNotNull) {
            return true
        }
        return false

    }

    private fun checkFilePathNull(): String? {
        val filePath = newFileCreationBinding.filePath.text.toString()
        if (filePath.length > 0) {
            return null
        } else {
            return "Required"
        }
    }

    private fun checkFileSizeNull(): String? {
        val fileSize = newFileCreationBinding.fileSize.text.toString()
        if (fileSize.length > 0) {
            return null
        } else {
            return "Required"
        }
    }

    private fun checkLastModifiedTimeNull(): String? {
        val lastModifiedTime = newFileCreationBinding.lastModifiedTime.text.toString()

        if (lastModifiedTime.length > 0) {
            return null
        } else {
            return "Required"
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

    fun checkFileNameNull(): String? {
        val fileNameText = newFileCreationBinding.fileNameText.text.toString()

        if (fileNameText.length > 0) {
            return null
        } else {
            return "Required"
        }
    }

    private fun fileNameInputTextOnFocusListener() {
        newFileCreationBinding.fileNameText.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                Log.i("check","here in filename")
                newFileCreationBinding.fileNameContainer.helperText = checkFileNameNull()
            }
        }
    }

//    private fun fileTypeInputTextOnFocusListener() {
//        val fileTypeText: RadioGroup = newFileCreationBinding.fileTypeRadio
//        val selectedRadioButtonId = fileTypeText.checkedRadioButtonId
//        if (selectedRadioButtonId!=null){
//            newFileCreationBinding.fileTypeContainer.helperText =
//        }
//    }

    private fun lastModifiedTimeInputTextOnFocusListener() {
        newFileCreationBinding.lastModifiedTime.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                newFileCreationBinding.lastModifiedTimeContainer.helperText = checkLastModifiedTimeNull()
            }
        }
    }

    private fun fileSizeInputTextOnFocusListener() {
        newFileCreationBinding.fileSize.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                newFileCreationBinding.fileSizeContainer.helperText = checkFileSizeNull()
            }
        }
    }

    private fun filePathInputTextOnFocusListener() {
        newFileCreationBinding.filePath.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                newFileCreationBinding.filePathContainer.helperText = checkFilePathNull()
            }
        }
    }
}