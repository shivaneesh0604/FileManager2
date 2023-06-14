package com.example.filemanager2

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels

class DetailsFragment : Fragment() {

//    internal lateinit var sourceFragment: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("detailsFrag", "onView created in details frag")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("detailsFrag", "oncreateView in DetailsFrag")
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            Log.e("detailsFrag", "back press success in details frag")
            val allFragmentViewModel: AllFragmentViewModel by activityViewModels()
            allFragmentViewModel.file = null
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.details_fragment, container, false)

        val result = arguments

        val image = view.findViewById<ImageView>(R.id.scode_profile)
//        image = result?.getInt("fileImage")
        view.findViewById<TextView>(R.id.file_name_display).text =
            result?.getString("fileName")
        view.findViewById<TextView>(R.id.file_path_display).text =
            result?.getString("filePath")
        view.findViewById<TextView>(R.id.file_size_display).text =
            result?.getString("fileSize")
        view.findViewById<TextView>(R.id.lastModifiedTimeDisplay).text =
            result?.getString("lastModifiedTime")
        val fileType = view.findViewById<TextView>(R.id.file_type_display)
        fileType.text =
            result?.getString("fileType")

        when (fileType.text) {
            "docx" -> {
                image.setImageResource(R.drawable.docx_image)
            }
            "txt" -> {
                image.setImageResource(R.drawable.text_image)
            }
            "doc" -> {
                image.setImageResource(R.drawable.doc_image)
            }
        }

        Log.i("selected", "came here " + result?.getString("fileName"))

        return view
    }

}

