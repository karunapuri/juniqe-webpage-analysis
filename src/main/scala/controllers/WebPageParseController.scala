package controllers

import play.api.mvc.{AbstractController, AnyContent, ControllerComponents, Request}
import javax.inject.Inject

import models.WebPageAnalysisForm

@Singleton
class WebPageParseController @Inject()(cc:ControllerComponents) extends AbstractController(cc) with play.api.i18n.I18nSupport{

  def index(): Unit = {

  }

  def forms() = Action {
    implicit request: Request[AnyContent] =>
      Ok(views.html.webpageanalysis(WebPageAnalysisForm.form))
  }

  def formsPost() = Action { implicit request =>
    val formData: WebPageAnalysisForm = WebPageAnalysisForm.form.bindFromRequest.get
    Ok(formData.toString)
  }
}
