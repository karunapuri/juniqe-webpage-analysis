package models.helpers

import java.io.IOException
import java.net.{MalformedURLException, UnknownHostException}
import scala.collection.JavaConverters._

import org.jsoup.Jsoup

object WebPageHtmlLinksHelper {

  def getAllHtmlExternalLinks(url: String) = {

    try {
      val document = Jsoup.connect(url).get
      val links = document.select("a[href]")
      for {link <- links.asScala; if (link.attr("abs:href") == link.attr("href"))}
        yield {
          "External link: " + link.attr("href") + "\n" + "External Link text: " + link.text + "\n"
        }
    }
    catch  {
      case e: UnknownHostException => ""
      case e: MalformedURLException => ""
      case e: IllegalArgumentException => ""
      case e: IOException => ""
    }
  }

  def getAllHtmlInternalLinks(url: String) = {
    try {
      val document = Jsoup.connect(url).get
      val links = document.select("a[href]")
      val internalLinksVal =
        for {link <- links.asScala; if (link.attr("abs:href") != link.attr("href"))}
          yield {
            "Internal link: " + link.attr("abs:href") + "\n" + "Internal Link text: " + link.text + "\n"
          }
      val totalLinksCount = "Total Links: " + links.size()
      (internalLinksVal, totalLinksCount) + "\n"
    }
    catch  {
      case e: UnknownHostException => ""
      case e: MalformedURLException => ""
      case e: IllegalArgumentException => ""
      case e: IOException => ""
    }
  }

  def getAllHtmlInaccessibleLinks(url: String): String = {
    try {
      val document = Jsoup.connect(url).get
      val links = document.select("a[href]")
      var inaccessibleLinkCount = 0
      for (link <- links.asScala) {
        val inaccessibleLink = Jsoup.connect(link.attr("abs:href"))
        val notOkStatus = (inaccessibleLink.execute().statusCode() != 200)
        if (notOkStatus) {
          inaccessibleLinkCount += 1;
          return "Inaccessible/ Broken Link:" + inaccessibleLink + "\n" + "Inaccessible Links Count:" + inaccessibleLinkCount + "\n"
        }
        else return "Inaccessible Links Count:" + inaccessibleLinkCount
      }
    }
    catch  {
      case e: UnknownHostException => ""
      case e: MalformedURLException => ""
      case e: IllegalArgumentException => ""
      case e: IOException => ""
    }
    ""
  }


}
