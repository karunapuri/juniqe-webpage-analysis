package controllers

import java.io.IOException
import java.net.{MalformedURLException, UnknownHostException}
import javax.inject.Inject

import com.google.inject.Singleton
import models.WebPageAnalysisForm
import org.jsoup.Jsoup
import org.jsoup.nodes.DocumentType
import play.api.mvc._

import scala.collection.JavaConversions._

@Singleton
class WebPageParseController @Inject()(cc:ControllerComponents) extends AbstractController(cc) with play.api.i18n.I18nSupport {

  def index() = Action{
    Ok("Web-Page/URL Analysis Form")
  }

  def formGet() = Action {
    implicit request: Request[AnyContent] =>
      Ok(views.html.webpageanalysis(WebPageAnalysisForm.form))
  }

  def formPost() = Action {
    implicit request: Request[AnyContent] =>
    WebPageAnalysisForm.form.bindFromRequest.fold(
      formWithErrors => {
        BadRequest(views.html.basicFormsWithError(formWithErrors))
      },
      formData => {
        Ok(
          {
            val url = formData.url.toString
            getCompleteWebPgInfo(url)
          }
        )
      }
    )
  }

  def getCompleteWebPgInfo(url: String) = {
    (getDocTitle(url) + "\n" + getHtmlVersion(url) + "\n" + getHtmlh1Heading(url) + "\n" + getHtmlh2Heading(url) +
      "\n"+ getHtmlh3Heading(url) + "\n"+ getHtmlh4Heading(url) + "\n"+getHtmlh5Heading(url) + "\n"+ getHtmlh6Heading(url)
      +"\n"+getAllHtmlExternalLinks(url) + "\n"+ getAllHtmlInternalLinks(url)+ "\n"+ getAllHtmlInaccessibleLinks(url)+"\n" + getHtmlLoginFormInfo(url))
    }

  private def getDocTitle(url: String) = {
    try {
      val document = Jsoup.connect(url).get()
      val title = document.title
      "Document Title: " + title + "\n"
    }
    catch  {
      case e: UnknownHostException  =>  "Error Code: "+ INTERNAL_SERVER_ERROR + "\n"+ "Error Description: The IP address of a host could not be determined."
      case e: MalformedURLException => "Error Code: "+ FORBIDDEN + "\n "+ "Error Description: A malformed URL error has occurred. Either no legal protocol could be found in " +
        "a specification string or the string could not be parsed."
      case e: IllegalArgumentException =>  "Error Code: " + BAD_REQUEST +"\n"+ "Error Description: A method has been passed an illegal or inappropriate argument."
      case e: IOException => "Error Code: "+ FORBIDDEN +"\n"+ "Error Description: An I/O exception of some sort has occurred. It is produced by failed or interrupted I/O operations."
    }

  }

  private def getHtmlVersion(url: String): String = {
    try {
    val document = Jsoup.connect(url).get
    val nodes = document.childNodes
    for (node <- nodes) {
      if (node.isInstanceOf[DocumentType]) {
        val documentType = node
        val docTypeString: String = documentType.toString

        docTypeString match {
          case "<!DOCTYPE html>" => return "Document HTML Version: 5"+ "\n"
          case "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\"\n   \"http://www.w3.org/TR/html4/strict.dtd\">" =>
            return "Document HTML Version: Strict 4"+ "\n"
          case "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"\n   \"http://www.w3.org/TR/html4/loose.dtd\">" =>
            return "Document HTML Version: Transitional 4"+ "\n"
          case "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Frameset//EN\"\n   \"http://www.w3.org/TR/html4/frameset.dtd\">" =>
            return "Document HTML Version: Frameset 4"+ "\n"
          case "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\"\n   \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">" =>
            return "Document XHTML Version: Strict 1"+ "\n"
          case "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\"\n   \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">" =>
            return "Document XHTML Version: Transitional 1"+ "\n"
          case "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Frameset//EN\"\n   \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd\">" =>
            return "Document XHTML Version: Frameset 1" + "\n"
        }
      }
     }
    "Document HTML Version: Unknown"
    }
    catch  {
      case e: UnknownHostException =>  ""
      case e: MalformedURLException => ""
      case e: IllegalArgumentException => ""
      case e: IOException => ""
    }
  }

  private def getHtmlh2Heading(url: String) = {
    try {
      val document = Jsoup.connect(url).get
      val h2Headings = document.getElementsByTag("h2")
      val h2HeadingVal =
        for (h2Heading <- h2Headings)
          yield (h2Heading)
      val h2HeadingCount = "h2 Headings Count:" + h2Headings.size()
      (h2HeadingVal.toList, h2HeadingCount)
    }
    catch  {
      case e: UnknownHostException => ""
      case e: MalformedURLException => ""
      case e: IllegalArgumentException => ""
      case e: IOException => ""
    }
  }
  private def getHtmlh1Heading(url: String) = {
    try {
      val document = Jsoup.connect(url).get
      val h1Headings = document.getElementsByTag("h1")
      val h1HeadingVal =
        for (h1Heading <- h1Headings)
          yield (h1Heading)
      val h1HeadingCount = "h1 Headings Count:" + h1Headings.size()
      (h1HeadingVal.toList, h1HeadingCount)
    }
    catch  {
      case e: UnknownHostException => ""
      case e: MalformedURLException => ""
      case e: IllegalArgumentException => ""
      case e: IOException => ""
    }
  }
  private def getHtmlh3Heading(url: String) = {
    try {
      val document = Jsoup.connect(url).get
      val h3Headings = document.getElementsByTag("h3")
      val h3HeadingVal =
        for (h3Heading <- h3Headings)
          yield (h3Heading)
      val h3HeadingCount = "h3 Headings Count:" + h3Headings.size()
      (h3HeadingVal.toList, h3HeadingCount)
    }
    catch  {
      case e: UnknownHostException => ""
      case e: MalformedURLException => ""
      case e: IllegalArgumentException => ""
      case e: IOException => ""
    }
  }
  private def getHtmlh4Heading(url: String) = {
    try {
      val document = Jsoup.connect(url).get
      val h4Headings = document.getElementsByTag("h4")
      val h4HeadingVal =
        for (h4Heading <- h4Headings)
          yield (h4Heading)
      val h4HeadingCount = "h4 Headings Count:" + h4Headings.size()
      (h4HeadingVal.toList, h4HeadingCount)
    }
    catch  {
      case e: UnknownHostException => ""
      case e: MalformedURLException => ""
      case e: IllegalArgumentException => ""
    }
  }
  private def getHtmlh5Heading(url: String) = {
    try {
      val document = Jsoup.connect(url).get
      val h5Headings = document.getElementsByTag("h5")
      val h5HeadingVal =
        for (h5Heading <- h5Headings)
          yield (h5Heading + "\n")
      val h5HeadingCount = "h5 Headings Count:" + h5Headings.size()
      (h5HeadingVal.toList, h5HeadingCount)
    }
    catch  {
      case e: UnknownHostException => ""
      case e: MalformedURLException => ""
      case e: IllegalArgumentException => ""
      case e: IOException => ""
    }
  }
  private def getHtmlh6Heading(url: String) = {
    try {
      val document = Jsoup.connect(url).get
      val h6Headings = document.getElementsByTag("h6")
      val h6HeadingVal =
        for (h6Heading <- h6Headings)
          yield (h6Heading)
      val h6HeadingCount = "h6 Headings Count:" + h6Headings.size()
      (h6HeadingVal.toList, h6HeadingCount) + "\n"
    }
    catch  {
      case e: UnknownHostException => ""
      case e: MalformedURLException => ""
      case e: IllegalArgumentException => ""
      case e: IOException => ""
    }
  }

  private def getAllHtmlExternalLinks(url: String) = {

    try {
      val document = Jsoup.connect(url).get
      val links = document.select("a[href]")
      for {link <- links; if (link.attr("abs:href") == link.attr("href"))}
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

  private def getAllHtmlInternalLinks(url: String) = {
    try {
      val document = Jsoup.connect(url).get
      val links = document.select("a[href]")
      val internalLinksVal =
        for {link <- links; if (link.attr("abs:href") != link.attr("href"))}
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

  private def getAllHtmlInaccessibleLinks(url: String): String = {
    try {
      val document = Jsoup.connect(url).get
      val links = document.select("a[href]")
      var inaccessibleLinkCount = 0
      for (link <- links) {
        val inaccessibleLink = Jsoup.connect(link.attr("abs:href"))
        val notOkStatus = (inaccessibleLink.execute().statusCode() != 200)
        if (notOkStatus) {
          inaccessibleLinkCount += 1;
          return "Inaccessible/ Broken Link:" + inaccessibleLink + "\n" + "Inaccessible Links Count:" + inaccessibleLinkCount + "\n"
        }
        else return "Inaccessible Links Count:" + inaccessibleLinkCount + "\n"
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

  private def getHtmlLoginFormInfo(url: String) = {
    try {
      val document = Jsoup.connect(url).get
      val loginForm = document.select("form[id$=login-box]")
      if (loginForm.nonEmpty)
        "Login Form exists for entered web page/URL" + "\n"
      else
        "Login Form doesn't exists for entered web page/URL" + "\n"
    }
    catch  {
      case e: UnknownHostException => ""
      case e: MalformedURLException => ""
      case e: IllegalArgumentException => ""
      case e: IOException => ""
    }
  }
}
