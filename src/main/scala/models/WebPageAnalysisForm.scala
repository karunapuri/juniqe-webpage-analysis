package models

import java.net.URL
import play.api.data.Form


// Class to hold form data
case class WebPageAnalysisForm(url: String)

//companion obj. to define case class WebPageAnalysisForm
object WebPageAnalysisForm {

  val form:Form[WebPageAnalysisForm] = Form(
    mapping(
      "url" -> text
    )(WebPageAnalysisForm.apply)(WebPageAnalysisForm.unapply)
  )
}
