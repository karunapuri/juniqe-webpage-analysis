package controllers

import org.jsoup.Jsoup
import org.mockito.Mockito._
import org.scalatest._
import org.scalatest.mockito.MockitoSugar

class WebPageParseControllerTest extends FunSpec with Matchers with MockitoSugar {

  describe("Web Page Parse Controller") {
    val webPageParseController = mock[WebPageParseController]
    val url = "http://jsoup.org"

    describe("routes") {
      it("should route to web form index/home url") {
        routes.WebPageParseController.index().toString should equal("/")
      }
      it("should route to web form get url") {
        routes.WebPageParseController.formGet().toString should equal("/webForm")
      }
      it("should route to web form post url") {
        routes.WebPageParseController.formPost().toString should equal("/webForm")
      }
    }

    describe("Document HTML version"){
      it("should get document correct HTML version") {
        val documentTypeString = "<!DOCTYPE html>"
        val docTypeString = documentTypeString

        val docType = if (docTypeString == "<!DOCTYPE html>") "Document HTML Version: 5" else "Document HTML Version: Unknown"

        when(webPageParseController.getCompleteWebPgInfo(url)) thenReturn (docType)

        webPageParseController.getCompleteWebPgInfo(url) should be (docType)
        docType should be("Document HTML Version: 5")
      }
    }

    describe("Document/Page title") {
      it("should render document title correctly") {
        val document = Jsoup.connect(url).get()
        val title = document.title
        when(webPageParseController.getCompleteWebPgInfo(url)) thenReturn (title)
        title should be("jsoup Java HTML Parser, with best of DOM, CSS, and jquery")
      }
    }
    describe("Different headings of document and their level"){
      it("should render different headings their level & their count as"){
        val document = Jsoup.connect(url).get
        val h1Headings = document.getElementsByTag("h1")
        val h3Headings = document.getElementsByTag("h3")
        val h1HeadingVal = h1Headings.toString
        val h3HeadingVal = h3Headings.toString

        when(webPageParseController.getCompleteWebPgInfo(url)) thenReturn (h1HeadingVal)
        h1HeadingVal should be ("<h1>jsoup: Java HTML Parser</h1>")
        h1Headings.size() should be (1)

        when(webPageParseController.getCompleteWebPgInfo(url)) thenReturn (h3HeadingVal)
        h3Headings.size() should be (5)
      }
    }

    describe("Document internal and external links"){
      it("should render all internal-external links & their total"){
        val document = Jsoup.connect(url).get
        val links = document.select("a[href]")
        val internalLink = links.attr("abs:href")
        val externalLink = links.attr("href")

        when(webPageParseController.getCompleteWebPgInfo(url)) thenReturn (internalLink + externalLink)
        internalLink.leftSideValue should be ("https://jsoup.org/")
        externalLink.leftSideValue should be ("/")
        links.size() should be (45)
      }
    }

    describe("Document inaccessible links") {
      it("should render inaccessible links on page & their count") {
        val document = Jsoup.connect(url).get
        val links = document.select("a[href]")
        val accessibleLink = Jsoup.connect(links.attr("abs:href"))
        val inaccessibleLinkVal = Jsoup.connect(links.attr("abs:href")).toString
        val okStatus = (accessibleLink.execute().statusCode() == 200)
        val inaccessibleLink = if(okStatus) ""
        when(webPageParseController.getCompleteWebPgInfo(url)) thenReturn (inaccessibleLinkVal)
        okStatus should be (true)
        inaccessibleLink should be ("")
      }
    }

    describe("Document/page login-form"){

    }
  }
}
