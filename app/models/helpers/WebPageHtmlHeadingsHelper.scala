package models.helpers

import java.io.IOException
import java.net.{MalformedURLException, UnknownHostException}
import scala.collection.JavaConverters._

import org.jsoup.Jsoup

object WebPageHtmlHeadingsHelper {

  def getHtmlh2Heading(url: String) = {
    try {
      val document = Jsoup.connect(url).get
      val h2Headings = document.getElementsByTag("h2")
      val h2HeadingVal =
        for (h2Heading <- h2Headings.asScala)
          yield (h2Heading)
      val h2HeadingCount = "h2 Headings Count:" + h2Headings.size()
      (h2HeadingVal, h2HeadingCount)
    }
    catch  {
      case e: UnknownHostException => ""
      case e: MalformedURLException => ""
      case e: IllegalArgumentException => ""
      case e: IOException => ""
    }
  }
  def getHtmlh1Heading(url: String) = {
    try {
      val document = Jsoup.connect(url).get
      val h1Headings = document.getElementsByTag("h1")
      val h1HeadingVal =
        for (h1Heading <- h1Headings.asScala)
          yield (h1Heading)
      val h1HeadingCount = "h1 Headings Count:" + h1Headings.size()
      (h1HeadingVal, h1HeadingCount)
    }
    catch  {
      case e: UnknownHostException => ""
      case e: MalformedURLException => ""
      case e: IllegalArgumentException => ""
      case e: IOException => ""
    }
  }
  def getHtmlh3Heading(url: String) = {
    try {
      val document = Jsoup.connect(url).get
      val h3Headings = document.getElementsByTag("h3")
      val h3HeadingVal =
        for (h3Heading <- h3Headings.asScala)
          yield (h3Heading)
      val h3HeadingCount = "h3 Headings Count:" + h3Headings.size()
      (h3HeadingVal, h3HeadingCount)
    }
    catch  {
      case e: UnknownHostException => ""
      case e: MalformedURLException => ""
      case e: IllegalArgumentException => ""
      case e: IOException => ""
    }
  }
  def getHtmlh4Heading(url: String) = {
    try {
      val document = Jsoup.connect(url).get
      val h4Headings = document.getElementsByTag("h4")
      val h4HeadingVal =
        for (h4Heading <- h4Headings.asScala)
          yield (h4Heading)
      val h4HeadingCount = "h4 Headings Count:" + h4Headings.size()
      (h4HeadingVal, h4HeadingCount)
    }
    catch  {
      case e: UnknownHostException => ""
      case e: MalformedURLException => ""
      case e: IllegalArgumentException => ""
    }
  }
  def getHtmlh5Heading(url: String) = {
    try {
      val document = Jsoup.connect(url).get
      val h5Headings = document.getElementsByTag("h5")
      val h5HeadingVal =
        for (h5Heading <- h5Headings.asScala)
          yield (h5Heading + "\n")
      val h5HeadingCount = "h5 Headings Count:" + h5Headings.size()
      (h5HeadingVal, h5HeadingCount)
    }
    catch  {
      case e: UnknownHostException => ""
      case e: MalformedURLException => ""
      case e: IllegalArgumentException => ""
      case e: IOException => ""
    }
  }
  def getHtmlh6Heading(url: String) = {
    try {
      val document = Jsoup.connect(url).get
      val h6Headings = document.getElementsByTag("h6")
      val h6HeadingVal =
        for (h6Heading <- h6Headings.asScala)
          yield (h6Heading)
      val h6HeadingCount = "h6 Headings Count:" + h6Headings.size()
      (h6HeadingVal, h6HeadingCount) + "\n"
    }
    catch  {
      case e: UnknownHostException => ""
      case e: MalformedURLException => ""
      case e: IllegalArgumentException => ""
      case e: IOException => ""
    }
  }

}
