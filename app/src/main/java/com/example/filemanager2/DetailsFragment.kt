package com.example.filemanager2

import android.app.FragmentManager
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

    internal lateinit var sourceFragment: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("detailsFrag","onView created in details frag")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("detailsFrag","oncreateView in DetailsFrag")
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            Log.e("detailsFrag", "back press success in details frag")

            val fragmentManager = requireActivity().supportFragmentManager
            val backstackCount = fragmentManager.backStackEntryCount

            for (i in 0 until backstackCount) {
                val backStackEntry = fragmentManager.getBackStackEntryAt(i)
                val fragmentTag = backStackEntry.name

//                Log.i("detailsFrag","fragment tag is "+fragmentTag)
//                Log.i("detailsFrag","sourcefrag is "+sourceFragment)

                if (fragmentTag == sourceFragment) {
                    if (fragmentTag == "AllFragment") {
                        Log.e("detailsFrag", "All ")
                        val allFragmentViewModel: AllFragmentViewModel by activityViewModels()
                        allFragmentViewModel.file = null
                        fragmentManager.popBackStack(
                            fragmentTag,
                            FragmentManager.POP_BACK_STACK_INCLUSIVE
                        )
                        break
                    } else if (fragmentTag == "DocxFragment") {
                        Log.e("detailsFrag", "Docx ")
                        val docxFragmentViewModel: DocxFragmentViewModel by activityViewModels()
                        docxFragmentViewModel.file = null
                        fragmentManager.popBackStack(
                            fragmentTag,
                            FragmentManager.POP_BACK_STACK_INCLUSIVE
                        )
                        break
                    } else if (fragmentTag == "DocFragment") {
                        Log.e("detailsFrag", "Doc ")
                        val docFragmentViewModel: DocFragmentViewModel by activityViewModels()
                        docFragmentViewModel.file = null
                        fragmentManager.popBackStack(
                            fragmentTag,
                            FragmentManager.POP_BACK_STACK_INCLUSIVE
                        )
                        break
                    } else if (fragmentTag == "TextFragment") {
                        Log.e("detailsFrag", "Text ")
                        val textFragmentViewModel: TextFragmentViewModel by activityViewModels()
                        textFragmentViewModel.file = null
                        fragmentManager.popBackStack(
                            fragmentTag,
                            FragmentManager.POP_BACK_STACK_INCLUSIVE
                        )
                        break
                    }

                }
            }
//                val fragmentManager = requireActivity().supportFragmentManager
//                val fragmentFound = fragmentManager.findFragmentByTag("AllFragment") as AllFragment
//                if (fragmentFound != null) {
//
//                    fragmentFound.allFragmentViewModel.file = null
//                }

//            if (sourceFragment == "AllFragment") {
//                Log.e("detailsFrag", "All ")
//                val allFragmentViewModel: AllFragmentViewModel by activityViewModels()
//                allFragmentViewModel.file = null
//                requireActivity().supportFragmentManager.popBackStack()
//            } else if (sourceFragment == "DocxFragment") {
//                Log.e("detailsFrag", "Docx")
//                val docxFragmentViewModel: DocxFragmentViewModel by activityViewModels()
//                docxFragmentViewModel.file = null
//                requireActivity().supportFragmentManager.popBackStack()
//            } else if (sourceFragment == "DocFragment") {
//                Log.e("detailsFrag", "Doc")
//                val docFragmentViewModel: DocFragmentViewModel by activityViewModels()
//                docFragmentViewModel.file = null
//                requireActivity().supportFragmentManager.popBackStack()
//            } else if (sourceFragment == "TextFragment") {
//                Log.e("detailsFrag", "Text")
//                val textFragmentViewModel: TextFragmentViewModel by activityViewModels()
//                textFragmentViewModel.file = null
//                requireActivity().supportFragmentManager.popBackStack()
//            }


//            val fragmentToRemove = requireActivity().supportFragmentManager.findFragmentByTag(sourceFragment)
//            if (fragmentToRemove!=null){
//                fragmentManager?.popBackStack(sourceFragment, FragmentManager.POP_BACK_STACK_INCLUSIVE)
//            }

        }
        //todo: to fetch from which frag i came here
        //todo: to fetch from which frag i came here
        //todo: to fetch from which frag i came here
        //todo: to fetch from which frag i came here
        //todo: to fetch from which frag i came here
        //todo: to fetch from which frag i came here
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        Log.e("detailsFrag","source fragment is "+sourceFragment)
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

        if (fileType.text == "docx") {
            image.setImageResource(R.drawable.docx_image)
        } else if (fileType.text == "txt") {
            image.setImageResource(R.drawable.text_image)
        } else if (fileType.text == "doc") {
            image.setImageResource(R.drawable.doc_image)
        }

        Log.i("selected", "came here " + result?.getString("fileName"))

        return view
    }

}

