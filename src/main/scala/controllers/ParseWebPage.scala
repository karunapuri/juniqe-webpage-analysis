package controllers

import java.io.IOException

import org.jsoup.Jsoup
import org.jsoup.nodes.DocumentType

import scala.collection.JavaConversions._

object ParseWebPage {
  @throws[IOException]
  def main(args: Array[String]): Unit = {
    val url = "http://jsoup.org"
    //      val url = "https://link.springer.com/signup-login?previousUrl=http%3A%2F%2Fmaterials.springer.com%2F"
    val document = Jsoup.connect(url).get

    println("=== 1. HTML version of document ===")
    val nodes = document.childNodes
    for (node <- nodes) {
      if (node.isInstanceOf[DocumentType]) {
        val documentType = node
        val docTypeString = documentType.toString
        if( docTypeString == "<!DOCTYPE html>")
          println("HTML Version = 5")

        else if(docTypeString == "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\"\n   \"http://www.w3.org/TR/html4/strict.dtd\">")
          println("HTML Version = Strict 4")

        else if(docTypeString == "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"\n   \"http://www.w3.org/TR/html4/loose.dtd\">")
          println("HTML Version = Transitional 4")

        else if(docTypeString == "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Frameset//EN\"\n   \"http://www.w3.org/TR/html4/frameset.dtd\">")
          println("HTML Version = Frameset 4")

        else if(docTypeString == "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\"\n   \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">")
          println("XHTML Version = Strict 1")

        else if(docTypeString == "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\"\n   \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">")
          println("XHTML Version = Transitional 1")

        else if(docTypeString == "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Frameset//EN\"\n   \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd\">")
          println("XHTML Version = Frameset 1")
      }
    }

    println("=== 2. page title ===")
    val title = document.title
    println(title +"\n")

    println("=== 3. Number of Headings and their level in document ===")

    val h1Headings = document.getElementsByTag("h1")
    var h1Count = 0
    for(h1Heading<-h1Headings) {
      println("h1 Headings:" + h1Heading)
      h1Count+=1
    }
    println()
    println("h1 Headings:" + h1Count+ "\n")

    val h2Headings = document.getElementsByTag("h2")
    var h2Count = 0
    for(h2Heading<-h2Headings) {
      println("h2 Headings:" + h2Heading)
      h2Count+=1
    }
    println()
    println("h2 Headings:" + h2Count + "\n")

    val h3Headings = document.getElementsByTag("h3")
    var h3Count = 0
    for(h3Heading<-h3Headings) {
      println("h3 Headings:" + h3Heading)
      h3Count+=1
    }
    println()
    println("h3 Headings:" + h3Count+ "\n")

    val h4Headings = document.getElementsByTag("h4")
    var h4Count = 0
    for(h4Heading<-h4Headings) {
      println("h4 Headings:" + h4Heading)
      h4Count+=1
    }
    println()
    println("h4 Headings:" + h4Count+ "\n")

    val h5Headings = document.getElementsByTag("h5")
    var h5Count = 0
    for(h5Heading<-h5Headings) {
      println("h5 Headings:" + h5Heading)
      h5Count+=1
    }
    println()
    println("h5 Headings:" + h5Count+ "\n")

    val h6Headings = document.getElementsByTag("h6")
    var h6Count = 0
    for(h6Heading<-h6Headings) {
      println("h6 Headings:" + h6Heading)
      h6Count+=1
    }
    println()
    println("h6 Headings: " + h6Count+ "\n")


    println("=== 4. internal and external links in document ===")
    val links = document.select("a[href]")
    var inaccessLinkCount = 0

    println("Total Links: "+links.size()+"\n")
    for (link <- links) {
      println("Link text: " + link.text)
      if (link.attr("abs:href") == link.attr("href"))
        println("External link: " + link.attr("href") + "\n")
      else
        println("Internal link: " + link.attr("abs:href")+"\n")

      val inaccessLink = Jsoup.connect(link.attr("abs:href"))
      val okStatus =  (inaccessLink.execute().statusCode() != 200)
      if(okStatus) {
        println("Inaccessible/ Broken Link:"+inaccessLink)
        inaccessLinkCount += 1
      }
    }
    println()
    println("=== 5. inaccessible links ===")
    println("inaccessible links:"+inaccessLinkCount)

//    var inaccessLinkCount = 0
//    for (link <- links) {
//      val inaccessLink = Jsoup.connect(link.attr("abs:href"))
//      val okStatus =  (inaccessLink.execute().statusCode() != 200)
//      if(okStatus) {
//        println("Inaccessible/ Broken Link:"+inaccessLink)
//        inaccessLinkCount += 1
//      }
//    }
    println()

    println("Login ????")
    println("=== 6. check whether page has login-form or not ===")
    val loginForm = document.select("form[id$=login-box]")
    if(loginForm.nonEmpty)
    println("Login Form exists for entered web page/URL" + "\n")
    else
    println("Login Form doesn't exists for entered web page/URL"+ "\n")

  }
}
