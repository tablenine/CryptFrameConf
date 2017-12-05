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

object Crypt extends App {
  private val encryptText = (sb: String, standardPBEStringEncryptor: StandardPBEStringEncryptor) 
  => "ENC(" + standardPBEStringEncryptor.encrypt(sb.trim()) + ")"

  private val decryptText = (sb: String, sEncryptor: StandardPBEStringEncryptor) 
  => sEncryptor.decrypt(sb.trim().substring(4, (sb.length() - 1)));

  if (args.length != 3) {
    CryptUtils.printHelp()
  } else {

    val src = args(0)
    val dst = args(1)
    val mode = args(2)

    val crypt = CryptUtils.cryptText(src) _

    val cryptFunction = mode match {
      case "enc" => Some(encryptText)
      case "dec" => Some(decryptText)
      case _ => None
    }

    cryptFunction match {
      case Some(f) => {
        val text = crypt(f)
        CryptUtils.writeFile(dst, text)
      }
      case None => CryptUtils.printHelp()
    }
  }
}