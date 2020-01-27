package controllers

import java.io.IOException
import java.net.{MalformedURLException, UnknownHostException}
import javax.inject.Inject

import com.google.inject.Singleton
import models.WebPageAnalysisForm
import models.helpers.{WebPageHtmlHeadingsHelper, WebPageHtmlLinksHelper}
import org.jsoup.Jsoup
import play.api.mvc._

import scala.collection.JavaConverters._

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
    (getDocTitle(url) + "\n" + getHtmlVersion(url) + "\n" + WebPageHtmlHeadingsHelper.getHtmlh1Heading(url) + "\n" +
      WebPageHtmlHeadingsHelper.getHtmlh2Heading(url) + "\n"+ WebPageHtmlHeadingsHelper.getHtmlh3Heading(url) + "\n"+
      WebPageHtmlHeadingsHelper.getHtmlh4Heading(url) + "\n"+WebPageHtmlHeadingsHelper.getHtmlh5Heading(url) + "\n"+
      WebPageHtmlHeadingsHelper.getHtmlh6Heading(url) +"\n"+WebPageHtmlLinksHelper.getAllHtmlExternalLinks(url) + "\n"+
      WebPageHtmlLinksHelper.getAllHtmlInternalLinks(url)+ "\n"+ WebPageHtmlLinksHelper.getAllHtmlInaccessibleLinks(url)
      +"\n" + getHtmlLoginFormInfo(url))
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
    for (node <- nodes.asScala) {
//      if (node.asInstanceOf[DocumentType]) {
        val documentType = node
        val docTypeString: String = documentType.toString()

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
//      }
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

  private def getHtmlLoginFormInfo(url: String) = {
    try {
      val document = Jsoup.connect(url).get
      //for login form within a page type="password" is must or type="submit" condition might be true most times.
      val loginForm = document.select("input[type$=password]")
      if (!loginForm.isEmpty)
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
