package models.helpers

import org.jsoup.Jsoup
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FunSpec, Matchers}

import scala.collection.mutable.ArrayBuffer

class WebPageHtmlHeadingsHelperTest extends FunSpec with Matchers with MockitoSugar {

  describe("Different headings of document and their level"){
    val url = "http://jsoup.org"

    it("should render h1 headings their level & their count as"){
      val document = Jsoup.connect(url).get
      val h1Headings = document.getElementsByTag("h1")
      val h1HeadingVal = h1Headings.toString

      h1HeadingVal should be ("<h1>jsoup: Java HTML Parser</h1>")
      h1Headings.size() should be (1)
      WebPageHtmlHeadingsHelper.getHtmlh1Heading(url) should be
      (ArrayBuffer{("<h1>jsoup: Java HTML Parser</h1>")}, {"h1 Headings Count:1"})
    }

    it("should render h2 headings count as") {
      val document = Jsoup.connect(url).get
      val h2Headings = document.getElementsByTag("h2")
      h2Headings.size() should be(6)
    }

    it("should render h3 headings their level & their count as"){
      val document = Jsoup.connect(url).get
      val h3Headings = document.getElementsByTag("h3")
      h3Headings.size() should be (5)
      WebPageHtmlHeadingsHelper.getHtmlh3Heading(url) should be
      (ArrayBuffer({"<h3>Introduction</h3>"}, {"<h3>Input</h3>"}, {"<h3>Extracting data</h3>"}, {"<h3>Modifying data</h3>"}, {"<h3>Cleaning HTML</h3>"}),{"h3 Headings Count:5"})
    }

    it("should render h4 headings count as") {
      val document = Jsoup.connect(url).get
      val h4Headings = document.getElementsByTag("h4")
      h4Headings.size() should be(1)
    }

    it("should render h5 headings count as") {
      val document = Jsoup.connect(url).get
      val h5Headings = document.getElementsByTag("h5")
      h5Headings.size() should be(0)
    }

    it("should render h6 headings count as") {
      val document = Jsoup.connect(url).get
      val h6Headings = document.getElementsByTag("h6")
      h6Headings.size() should be(0)
    }
  }
}
