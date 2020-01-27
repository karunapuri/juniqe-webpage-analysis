package models

import java.net.URL
import play.api.data.Form
import play.api.data.Forms._

case class WebPageAnalysisForm(url: String)

object WebPageAnalysisForm {

  val form:Form[WebPageAnalysisForm] = Form(
    mapping(
      "url" -> text
    )(WebPageAnalysisForm.apply)(WebPageAnalysisForm.unapply)
  )
}
