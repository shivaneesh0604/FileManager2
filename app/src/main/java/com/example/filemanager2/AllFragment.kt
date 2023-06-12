package com.example.filemanager2

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class AllFragment : Fragment(), RecyclerFileAdapter.FileClickListener, AddFileListener {

    private lateinit var recyclerView: RecyclerView
    private var currentData: MutableList<File> = mutableListOf()
    internal val allFragmentViewModel: AllFragmentViewModel by activityViewModels()
    internal val newFileCreationViewModel: NewFIleCreationViewModel by activityViewModels()
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var mMenuProvider: MenuProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newFileCreationViewModel.addListener(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.i("detailsFrag", "oncreateView in AllFrag")
        val view = inflater.inflate(R.layout.fragment_all, container, false)
        val newFileButton: FloatingActionButton = view.findViewById(R.id.add_file)
        recyclerView = view.findViewById(R.id.allRecyclerviewFile)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val itemsAdapter = RecyclerFileAdapter(requireContext(), AllData.getAllFiles(), this)
        recyclerView.adapter = itemsAdapter
        currentData = AllData.getAllFiles()

        activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent = result.data!!
                    val fileName = data.getStringExtra("fileName")
                    val fileTypeReceived = data.getStringExtra("fileType")
                    val lastModifiedTime = data.getStringExtra("lastModifiedTime")
                    val fileSize = data.getStringExtra("fileSize")
                    val filePath = data.getStringExtra("filePath")
                    lateinit var fileType: FileType
                    if (fileTypeReceived == "txt") {
                        fileType = FileType.txt
                    } else if (fileTypeReceived == "doc") {
                        fileType = FileType.doc
                    } else if (fileTypeReceived == "docx") {
                        fileType = FileType.docx
                    }
                    val file = File(fileName, fileType, lastModifiedTime, fileSize, filePath)
                    AllData.addFile(file)
                }
            }

        if (allFragmentViewModel.file != null) {
            Log.i("detailsFrag", "saved in allFragment View mode")
            val detailsFragment = DetailsFragment()
            val result = Bundle()
            result.putString("fileName", allFragmentViewModel.file!!.fileName)
            result.putString(
                "lastModifiedTime",
                allFragmentViewModel.file!!.lastModifiedTime
            )
            result.putString("fileType", allFragmentViewModel.file!!.fileType.toString())
            result.putString("fileSize", allFragmentViewModel.file!!.fileSize)
            result.putString("filePath", allFragmentViewModel.file!!.filePath)
            detailsFragment.arguments = result
            val manager = requireActivity().supportFragmentManager.beginTransaction()
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                manager.replace(
                    R.id.allSelectedFileView,
                    detailsFragment, "AllFragment"
                )
            } else if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                manager.replace(
                    R.id.drawerSwitch, detailsFragment, "AllFragment"
                )
            }
            manager.addToBackStack("AllFragment").commit()
        }

        newFileButton.setOnClickListener {
            val newFileCreationFragment = NewFileCreationFragment()
            newFileCreationFragment.show(
                requireActivity().supportFragmentManager,
                "NewFileCreationFragment"
            )
        }
        return view
    }

    override fun onStart() {
        super.onStart()
        Log.i("onStart", "called onstart")
        mMenuProvider = object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                Log.i("onStart", "called onstart2 below onstart")
                menuInflater.inflate(R.menu.top_app_bar, menu)
                val searchFun = menu.findItem(R.id.search)
                val searchView: SearchView = searchFun.actionView as SearchView
                searchView.queryHint = "Search..."
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return true
                    }

                    override fun onQueryTextChange(newText: String): Boolean {
                        performSearch(newText)
                        return true
                    }
                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.search -> return true
                    R.id.filterDoc -> {
                        performFilterOperation("Doc")
                        return true
                    }

                    R.id.filterDocx -> {
                        performFilterOperation("Docx")
                        return true
                    }

                    R.id.filterText -> {
                        performFilterOperation("Text")
                        return true
                    }

                    R.id.filterAll -> {
                        performFilterOperation("All")
                        return true
                    }


                }
                return false
            }
        }
        (requireActivity() as MenuHost).addMenuProvider(mMenuProvider)
    }

    private fun performFilterOperation(filter: String) {

        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        val isDarkMode = currentNightMode == Configuration.UI_MODE_NIGHT_YES

        if (isDarkMode) {

        }
        when (filter) {
            "Doc" -> {
                recyclerView.adapter =
                    RecyclerFileAdapter(requireContext(), AllData.getAllDocFiles(), this)
                currentData = AllData.getAllDocFiles()
            }
            "Docx" -> {
                recyclerView.adapter =
                    RecyclerFileAdapter(requireContext(), AllData.getAllDocxFiles(), this)
                currentData = AllData.getAllDocxFiles()
            }
            "Text" -> {
                recyclerView.adapter =
                    RecyclerFileAdapter(requireContext(), AllData.getAllTextFiles(), this)
                currentData = AllData.getAllTextFiles()
            }
            "All" -> {
                recyclerView.adapter =
                    RecyclerFileAdapter(requireContext(), AllData.getAllFiles(), this)
                currentData = AllData.getAllFiles()
            }

        }
//        Toast.makeText(requireContext(), "Selected filter: $filter", Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        super.onPause()
        (requireActivity() as MenuHost).removeMenuProvider(mMenuProvider)
    }


    override fun clickedFile(file: File) {
        Log.i("selected", "clicked")
        val result = Bundle()
//        result.putString("sourceFragment","AllFragment")
        result.putString("fileName", file.fileName)
        result.putString("lastModifiedTime", file.lastModifiedTime)
        result.putString("fileType", file.fileType.toString())
        result.putString("fileSize", file.fileSize)
        result.putString("filePath", file.filePath)

        allFragmentViewModel.file = file
        Log.i(
            "orientation",
            "check in recyclerViewContainerFragment whether fileis saved " + allFragmentViewModel.file
        )
        val detailsFragment = DetailsFragment()
//        detailsFragment.sourceFragment = "AllFragment"
        detailsFragment.arguments = result
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.i("tag11", "Portrait")
            val manager = requireActivity().supportFragmentManager
            manager.beginTransaction()
                .replace(R.id.drawerSwitch, detailsFragment, "AllFragment")
                .addToBackStack("AllFragment")
                .commit()
        } else {
            Log.i("tag11", "Landscape")
            val manager = requireActivity().supportFragmentManager
            manager.beginTransaction().replace(
                R.id.allSelectedFileView,
                detailsFragment, "AllFragment"
            )
                .addToBackStack("AllFragment")
                .commit()
        }
    }

    fun performSearch(query: String) {
        val filteredList = mutableListOf<File>()

        for (item in currentData) {
            if (item.fileName?.toLowerCase()!!.contains(query.toLowerCase())) {
                filteredList.add(item)
            }
        }

        if (filteredList.isEmpty()) {
            Toast.makeText(requireContext(), "No Data Found", Toast.LENGTH_SHORT).show()
        } else {
            recyclerView.adapter = RecyclerFileAdapter(requireContext(), filteredList, this)
        }
    }

    override fun addFile(file: File) {
        Log.e("fileCheck", "fileCheck")
        AllData.addFile(file)

        Log.e("fileCheck", "FIles size " + AllData.getAllFiles().size)

        recyclerView.adapter = RecyclerFileAdapter(requireContext(), AllData.getAllFiles(), this)
    }

}
