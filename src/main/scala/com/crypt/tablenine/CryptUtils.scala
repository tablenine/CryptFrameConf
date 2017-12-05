package com.crypt.tablenine

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor
import java.nio.file.Paths
import java.nio.file.Files
import scala.io.Source
import java.nio.charset.Charset
import java.nio.channels.FileChannel
import java.nio.ByteBuffer
import java.io.IOException
import java.nio.file.StandardOpenOption
import scala.collection.mutable.StringBuilder

object CryptUtils {
  def logWriter(x: Any) = println(x)
  
  def writeFile(dst: String, data: String) {
    var buffer = ByteBuffer.allocateDirect(data.length())
    val charset = Charset.forName("UTF-8");
    buffer = charset.encode(data);

    val fileChannel = FileChannel.open(Paths.get(dst), StandardOpenOption.CREATE, StandardOpenOption.WRITE)

    try {
      fileChannel.write(buffer);
    } finally {
      fileChannel.close()
    }
  }

  def cryptText(src: String)(cryptFunction: (String, StandardPBEStringEncryptor) => String): String = {
    val source = Source.fromFile(src, "utf-8")
    val builder = new StringBuilder
    val standardPBEStringEncryptor = new StandardPBEStringEncryptor
    val pswd = "ssbr*272746"
    standardPBEStringEncryptor.setPassword(pswd)

    try {
      source.getLines()
        .map(s => {
          if (s.startsWith("frame.")) {
            val sb = s.split("=", 2)
            sb(0) + "=" + cryptFunction(sb(1).trim(), standardPBEStringEncryptor) + "\n"
          } else {
            s + "\n"
          }
        })
        .foreach(builder.append)
    } finally {
      source.close()
    }
    builder.toString()
  }
  private val helpMessage = 
"""sourceFile dstFile enc|dec
"""

  val printHelp = () => logWriter(helpMessage)
}