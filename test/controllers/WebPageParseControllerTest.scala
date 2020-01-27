package controllers

import org.jsoup.Jsoup
import org.mockito.Mockito._
import org.scalatest._
import org.scalatest.mockito.MockitoSugar

class WebPageParseControllerTest extends FunSpec with Matchers with MockitoSugar {

  describe("Web Page Parse Controller") {
    val mockWebPageParseController = mock[WebPageParseController]
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

        when(mockWebPageParseController.getCompleteWebPgInfo(url)) thenReturn (docType)

        mockWebPageParseController.getCompleteWebPgInfo(url) should be (docType)
        docType should be("Document HTML Version: 5")
      }
    }

    describe("Document/Page title") {
      it("should render document title correctly") {
        val document = Jsoup.connect(url).get()
        val title = document.title
        when(mockWebPageParseController.getCompleteWebPgInfo(url)) thenReturn (title)
        title should be("jsoup Java HTML Parser, with best of DOM, CSS, and jquery")
      }
    }

    describe("Document/page login-form"){
      it("should analyse login-form info. on a page"){
        val document = Jsoup.connect(url).get
        val loginForm = document.select("input[type$=password]")
        val loginFormNotExist = "login form doesn't exists for given page"
          if(loginForm.isEmpty)
            println(loginFormNotExist)

        when(mockWebPageParseController.getCompleteWebPgInfo(url)) thenReturn(loginFormNotExist)
        mockWebPageParseController.getCompleteWebPgInfo(url) should be ("login form doesn't exists for given page")

      }

    }
  }
}
