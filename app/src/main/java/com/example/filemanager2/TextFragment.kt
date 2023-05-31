package com.example.filemanager2

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TextFragment : Fragment(), RecyclerFileAdapter.FileClickListener  {

    private lateinit var recyclerView: RecyclerView

    private val textFragmentViewModel:TextFragmentViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i("detailsFrag","oncreateView in TextFrag")
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_text, container, false)

        recyclerView = view.findViewById(R.id.textRecyclerviewFile)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val itemsAdapter = RecyclerFileAdapter(requireContext(), AllData.getAllTextFiles(), this)
        recyclerView.adapter = itemsAdapter

        if (textFragmentViewModel.file != null) {
            Log.i("detailsFrag","saved in textFragmentViewModel View mode")
            val detailsFragment = DetailsFragment()
            detailsFragment.sourceFragment = "TextFragment"
            val result = Bundle()
//            result.putString("sourceFragment","TextFragment")
            result.putString("fileName", textFragmentViewModel.file!!.fileName)
            result.putString(
                "lastModifiedTime",
                textFragmentViewModel.file!!.lastModifiedTime
            )
            result.putString("fileType", textFragmentViewModel.file!!.fileType.toString())
            result.putString("fileSize", textFragmentViewModel.file!!.fileSize)
            result.putString("filePath", textFragmentViewModel.file!!.filePath)
            detailsFragment.arguments = result
            val manager = requireActivity().supportFragmentManager.beginTransaction()
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                manager.replace(
                    R.id.textSelectedFileView,
                    detailsFragment,"TextFragment"
                )
            } else if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                manager.replace(
                    R.id.drawerSwitch, detailsFragment,"TextFragment"
                )
            }
            manager.addToBackStack("TextFragment").commit()
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
        textFragmentViewModel.file = file
        val detailsFragment = DetailsFragment()
        detailsFragment.sourceFragment = "TextFragment"
//        result.putString("sourceFragment","TextFragment")
        detailsFragment.arguments = result
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.i("tag11", "Portrait")
            val manager = requireActivity().supportFragmentManager
            manager.beginTransaction()
                .replace(R.id.drawerSwitch, detailsFragment,"TextFragment")
                .addToBackStack("TextFragment")
                .commit()
        } else {
            Log.i("tag11", "Landscape")
            val manager = requireActivity().supportFragmentManager
            manager.beginTransaction().replace(
                R.id.textSelectedFileView,
                detailsFragment,"TextFragment"
            )
                .addToBackStack("TextFragment")
                .commit()
        }
    }

}