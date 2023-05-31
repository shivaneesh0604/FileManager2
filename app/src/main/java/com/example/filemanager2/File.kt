package com.example.filemanager2


data class File(
    val fileName: String?,
    val fileType: FileType,
    val lastModifiedTime: String?,
    val fileSize: String?,
    val filePath: String?
) {
    var id: Int = ++nextId
        private set

    companion object {
        private var nextId = 1
    }
}