package models.helpers

import org.jsoup.Jsoup
import org.scalatest.{FunSpec, Matchers}
import org.scalatest.mockito.MockitoSugar

import scala.collection.mutable.ArrayBuffer

class WebPageHtmlLinksHelperTest extends FunSpec with Matchers with MockitoSugar {

  describe("Web Page HTML Links Helper") {

    val url = "http://jsoup.org"

    describe("Document internal and external links"){
      it("should render all external links"){
        val document = Jsoup.connect(url).get
        val links = document.select("a[href]")
        val externalLink = links.attr("href")

        externalLink.leftSideValue should be ("/")
        WebPageHtmlLinksHelper.getAllHtmlExternalLinks(url) should be
        (ArrayBuffer({"External link: https://whatwg.org/html"
          "External Link text:"},{"External link: https://whatwg.org/html"
            "External Link text: WHATWG HTML5"},{"External link: https://en.wikipedia.org/wiki/Main_Page"
        "External Link text: Wikipedia"},{"External link: https://github.com/jhy/jsoup/blob/master/src/main/java/org/jsoup/examples/Wikipedia.java"
        "External Link text: full source"}, {"External link: http://www.opensource.org/"
          "External Link text:"}, {"External link: https://github.com/jhy/jsoup/"
          "External Link text: GitHub"}
          ))
      }
      it("should render all internal links & total links"){
        val document = Jsoup.connect(url).get
        val links = document.select("a[href]")
        val internalLink = links.attr("abs:href")
        internalLink.leftSideValue should be ("https://jsoup.org/")
        links.size() should be (45)
        WebPageHtmlLinksHelper.getAllHtmlInternalLinks(url) should be
        (ArrayBuffer({"Internal link: https://jsoup.org/"
              "Internal Link text: jsoup"}, {"Internal link: https://jsoup.org/news/"
              "Internal Link text: News"}, {"Internal link: https://jsoup.org/bugs"
              "Internal Link text: Bugs"}, {"Internal link: https://jsoup.org/discussion"
              "Internal Link text: Discussion"}, {"Internal link: https://jsoup.org/download"
              "Internal Link text: Download"}, {"Internal link: https://jsoup.org/apidocs/"
              "Internal Link text: API Reference"}, {"Internal link: https://jsoup.org/cookbook/"
              "Internal Link text: Cookbook"} , {"Internal link: https://try.jsoup.org/"
              "Internal Link text: Try jsoup"}, {"Internal link: https://jsoup.org/"
              "Internal Link text: jsoup"}, {"Internal link: https://jsoup.org/cookbook/input/parse-document-from-string"
              "Internal Link text: parse"}, {"Internal link: https://jsoup.org/cookbook/extracting-data/selector-syntax"
              "Internal Link text: find"}, {"Internal link: https://jsoup.org/cookbook/modifying-data/set-html"
              "Internal Link text: manipulate"}, {"Internal link: https://jsoup.org/cookbook/cleaning-html/whitelist-sanitizer"
              "Internal Link text: clean"}, {"Internal link: https://jsoup.org/apidocs/org/jsoup/select/Elements.html#html--"
              "Internal Link text: output"}, {"Internal link: https://jsoup.org/apidocs/index.html?org/jsoup/select/Elements.html"
              "Internal Link text: Elements"}, {"Internal link: https://try.jsoup.org/~LGB7rk_atM2roavV0d-czMt3J_g"
              "Internal Link text: online sample"}, {"Internal link: https://jsoup.org/license"
              "Internal Link text: MIT license"} , {"Internal link: https://jsoup.org/download"
              "Internal Link text: Download"}, {"Internal link: https://jsoup.org/news/release-1.12.1"
              "Internal Link text: 1.12.1"}, {"Internal link: https://jsoup.org/cookbook/introduction/"
              "Internal Link text: introduction"}, {"Internal link: https://jsoup.org/discussion"
              "Internal Link text: mailing list"}, {"Internal link: https://jsoup.org/bugs"
              "Internal Link text: file a bug"}, {"Internal link: https://jsoup.org/colophon"
              "Internal Link text: colophon"}, {"Internal link: https://jsoup.org/cookbook"
              "Internal Link text:" }, {"Internal link: https://jsoup.org/cookbook/introduction/parsing-a-document"
              "Internal Link text: Parsing and traversing a Document"}, {"Internal link: https://jsoup.org/cookbook/input/parse-document-from-string"
              "Internal Link text: Parse a document from a String"}, {"Internal link: https://jsoup.org/cookbook/input/parse-body-fragment"
              "Internal Link text: Parsing a body fragment"}, {"Internal link: https://jsoup.org/cookbook/input/load-document-from-url"
              "Internal Link text: Load a Document from a URL"}, {"Internal link: https://jsoup.org/cookbook/input/load-document-from-file"
              "Internal Link text: Load a Document from a File"}, {"Internal link: https://jsoup.org/cookbook/extracting-data/dom-navigation"
              "Internal Link text: Use DOM methods to navigate a document"}, {"Internal link: https://jsoup.org/cookbook/extracting-data/selector-syntax"
              "Internal Link text: Use selector-syntax to find elements"}, {"Internal link: https://jsoup.org/cookbook/extracting-data/attributes-text-html"
              "Internal Link text: Extract attributes, text, and HTML from elements"}, {"Internal link: https://jsoup.org/cookbook/extracting-data/working-with-urls"
              "Internal Link text: Working with URLs"}, {"Internal link: https://jsoup.org/cookbook/extracting-data/example-list-links"
              "Internal Link text: Example program: list links"}, {"Internal link: https://jsoup.org/cookbook/modifying-data/set-attributes"
              "Internal Link text: Set attribute values"}, {"Internal link: https://jsoup.org/cookbook/modifying-data/set-html"
              "Internal Link text: Set the HTML of an element"}, {"Internal link: https://jsoup.org/cookbook/modifying-data/set-text"
              "Internal Link text: Setting the text content of elements"}, {"Internal link: https://jsoup.org/cookbook/cleaning-html/whitelist-sanitizer"
              "Internal Link text: Sanitize untrusted HTML (to prevent XSS)"}, {"Internal link: https://jhy.io/"
              "Internal Link text: Jonathan Hedley}"}),{"Total Links: 45"})
      }
    }

    describe("Document inaccessible links") {
      it("should render inaccessible links on page & their count") {
        val document = Jsoup.connect(url).get
        val links = document.select("a[href]")
        val accessibleLink = Jsoup.connect(links.attr("abs:href"))
        val okStatus = (accessibleLink.execute().statusCode() == 200)
        val inaccessibleLink = if(okStatus) ""
        okStatus should be (true)
        inaccessibleLink should be ("")
        WebPageHtmlLinksHelper.getAllHtmlInaccessibleLinks(url) should be ("Inaccessible Links Count:0")
      }
    }

  }
}
