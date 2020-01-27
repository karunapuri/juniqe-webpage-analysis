# Objective:

The objective of this web-application is to do some analysis of a web-page/URL. The application shows a form with a text-field wherein the user can type in the URL of the webpage to analyze. Additionally the form contains a button for sending the input to the server as well as to trigger the server-side analysis process. After processing the results are shown to the user below the form.

The result basically comprises of following information:

1. HTML version document has.
2. The page title.
3. Different headings of document and of what level they are.
4. Number of internal and external links in the document.
5. Any inaccessible links if any in document and their number.
6. The page login-form information if any.

And in case the URL given by the user is not reachable an error message would appear in input form describing some useful error description.

# Technologies Used:

Backend:
1. Scala V2.12.1
2. sbt V0.13.15
3. App built using Play Framework V2.7.4

Frontend:
1. Scala Twirl Template (HTML)

# Execution Steps:
1. git clone https://github.com/karunapuri/juniqe-webpage-analysis
2. cd juniqe-webpage-analysis: sbt (Press Enter)
3. run 9000 (locally start project from terminal)
4. Active Routes:
		http://localhost:9000 - Home Page
		http://localhost:9000/webForm - UserForm (Enter a URL in Text-Field, Press Submit Button) 
5. app/controllers/ParseWebPage.scala - This can also be run directly in IDE (like IntelliJ). It  would render results on terminal.



