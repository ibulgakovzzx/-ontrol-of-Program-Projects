package com.ibulgakov.clientcontrolpc.utils

import android.os.Environment
import java.io.File
import java.io.FileWriter
import java.io.IOException

object FileUtils {

    private fun getExternalDirectory(): File {
        val dir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "test")
        if (!dir.exists()) {
            dir.mkdir()
        }
        return dir
    }


    fun write(fileName: String, text: String) {
        //Определяем файл
        val file = File(getExternalDirectory(), fileName)

        try {
            //проверяем, что если файл не существует то создаем его
            if(!file.exists()){
                file.createNewFile()
            }

            val fileWriter = FileWriter(file, true)

            try {
                //Записываем текст в файл
                fileWriter.write(text)
            } finally {
                //После чего мы должны закрыть файл
                //Иначе файл не запишется
                //out.close()
                fileWriter.flush()
                fileWriter.close()
            }
        } catch (e: IOException) {
            throw RuntimeException(e)
        }

    }

}
