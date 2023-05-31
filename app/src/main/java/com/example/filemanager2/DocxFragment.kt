package com.example.filemanager2

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DocxFragment : Fragment(), RecyclerFileAdapter.FileClickListener {

    private lateinit var recyclerView: RecyclerView
    private val docxFragmentViewModel : DocxFragmentViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("back","onviewCreated in docxfrag")
        (activity as MainActivity).activeFragmentTag = "DocxFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i("detailsFrag","oncreateView in Docx frag")
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_docx, container, false)

        recyclerView = view.findViewById(R.id.docxRecyclerviewFile)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val itemsAdapter = RecyclerFileAdapter(requireContext(), AllData.getAllDocxFiles(), this)
        recyclerView.adapter = itemsAdapter

        if (docxFragmentViewModel.file != null) {
            Log.i("detailsFrag","saved in docxFragmentViewModel View mode")
            val detailsFragment = DetailsFragment()
            detailsFragment.sourceFragment = "DocxFragment"
            val result = Bundle()
//            result.putString("sourceFragment","DocxFragment")
            result.putString("fileName", docxFragmentViewModel.file!!.fileName)
            result.putString(
                "lastModifiedTime",
                docxFragmentViewModel.file!!.lastModifiedTime
            )
            result.putString("fileType", docxFragmentViewModel.file!!.fileType.toString())
            result.putString("fileSize", docxFragmentViewModel.file!!.fileSize)
            result.putString("filePath", docxFragmentViewModel.file!!.filePath)
            detailsFragment.arguments = result
            val manager = requireActivity().supportFragmentManager.beginTransaction()
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                manager.replace(
                    R.id.docxSelectedFileView,
                    detailsFragment,"DocxFragment"
                )
            } else if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                manager.replace(
                    R.id.drawerSwitch, detailsFragment,"DocxFragment"
                )
            }
            manager.addToBackStack("DocxFragment").commit()
        }

        return  view
    }

    override fun clickedFile(file: File) {
        Log.i("selected", "clicked")
        val result = Bundle()
        result.putString("fileName", file.fileName)
        result.putString("lastModifiedTime", file.lastModifiedTime)
        result.putString("fileType", file.fileType.toString())
        result.putString("fileSize", file.fileSize)
        result.putString("filePath", file.filePath)
        docxFragmentViewModel.file = file
        val detailsFragment =  DetailsFragment()
        detailsFragment.sourceFragment = "DocxFragment"
//        result.putString("sourceFragment","DocxFragment")
        detailsFragment.arguments = result
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.i("tag11", "Portrait")
            val manager = requireActivity().supportFragmentManager
            manager.beginTransaction()
                .replace(R.id.drawerSwitch, detailsFragment,"DocxFragment")
                .addToBackStack("DocxFragment")
                .commit()
        } else {
            Log.i("tag11", "Landscape")
            val manager = requireActivity().supportFragmentManager
            manager.beginTransaction().replace(
                R.id.docxSelectedFileView,
                detailsFragment,"DocxFragment"
            )
                .addToBackStack("DocxFragment")
                .commit()
        }
    }

}