package com.example.filemanager2

import android.util.Log

internal object AllData {
//    private val albumFolders: MutableList<Folder> = mutableListOf()

    private var files: MutableList<File> = mutableListOf()

    val fileNames = listOf(
        "file1alkjcsadvnlks;anvdavnkjlmsjkdnudhsbkdvjdjscxmdnjscxmndjkscxjdsmcjdkmscjknd", "file2", "file3", "file4", "file5",
        "SKJBCDCBIUBDIUVBOUDVHUDFHVUDFHIUVHDIUHVIUDHFVUIHDFIUHVIUFHUIDFHDIHDFUIDVHDVHFUIFUID", "file7", "file8", "file9", "file10"
    )

    val lastModifiedTimes = listOf(
        "2023-05-15 10:30:00", "2023-05-15 11:15:00", "2023-05-15 12:00:00", "2023-05-15 13:30:00", "2023-05-15 14:45:00",
        "2023-05-15 15:20:00", "2023-05-15 16:10:00", "2023-05-15 17:00:00", "2023-05-15 18:30:00", "2023-05-15 19:15:00"
    )

    val fileSizes = listOf(
        "1.2 MB", "800 KB", "5.6 MB", "3.9 MB", "2.1 MB",
        "1.8 MB", "4.5 MB", "700 KB", "2.3 MB", "6.7 MB"
    )

    val filePaths = listOf(
        "/documents/vf;kndklndnvinfevdnpijwapiodjpiejavp;jfspopfxlkmdfpfdvpodvmopdfooo", "/images/", "/files/akscbjadncisdjvnbahiudsacdlnsjckhbsdajlsjkhsbusidskljkashvj", "/music/", "/videos/",
        "/documents/reports/DNVBKJ.DNV;DFNV;LDFVNLDKJVILDKJVNIDUFKVBIDFHKVBIDFBVIUDFUU", "/images/photos/", "/files/archives/", "/music/albums/", "/videos/movies/"
    )

    val fileTypes = listOf(
        FileType.doc, FileType.docx, FileType.txt, FileType.docx, FileType.doc,
        FileType.txt, FileType.doc, FileType.docx, FileType.txt, FileType.docx
    )

    init {
        for (i in 0..9) {
            val file: File =
                File(fileNames[i], fileTypes[i], lastModifiedTimes[i], fileSizes[i] , filePaths[i])
            files.add(file)
        }
    }

    internal fun getFile(fileId: Int): File? {
        for (file in files) {
            if (file.id == fileId) {
                return file
            }
        }
        return null
    }

//    internal fun getFolder(folderId: Int): Folder? {
//        for (folder in albumFolders) {
//            if (folder.id == folderId) {
//                return folder
//            }
//        }
//        return null
//    }

    internal fun getAllFiles():MutableList<File>{
        return files.toMutableList()
    }

    internal fun getAllTextFiles():MutableList<File>{
        val textFiles = mutableListOf<File>()
        for (file in files){
            if (file.fileType == FileType.txt){
                textFiles.add(file)
            }
        }
        return textFiles
    }

    internal fun getAllDocFiles():MutableList<File>{
        val docFiles = mutableListOf<File>()
        for (file in files){
            if (file.fileType == FileType.doc){
                docFiles.add(file)
            }
        }
        return docFiles
    }

    internal fun getAllDocxFiles():MutableList<File>{
        val docxFiles = mutableListOf<File>()
        for (file in files){
            if (file.fileType == FileType.docx){
                docxFiles.add(file)
            }
        }
        return docxFiles
    }

//    internal fun getAllFolders():MutableList<Folder>{
//        Log.i("folders",""+ albumFolders)
//        Log.i("folders",""+ albumFolders.size)
//        return albumFolders.toMutableList()
//    }

    internal fun addFile(file: File){
        files.add(file)
    }

//    internal fun addFolder(folder: Folder){
//        albumFolders.add(folder)
//    }
//
//    internal fun removeFolder(folder: Folder){
//        albumFolders.remove(folder)
//    }

}