<h1>Final Year Software Project - Backend</h1>
<p>RESTful API of a dental clinic management system. Developed by using Jersey 2.0 and EclipseLink.</p>
<h2>Requirements</h2>
<p>Before running the server-side application, you need to have MySQL installed along with Glassfish. You also need to create a schema with the name of clinigmentdbv2 in your database.</p>
<h2>Installation Guide</h2>
<p>In the /src/main/webapp/WEB-INF folder open glassfish-resources.xml. Add the username and password of your MySQL server on line 38 and 39.</p>
<ol>
<li>Make sure Glassfish is running, by typing your server URL:8080 into your browser. You see the following screen if your server is running.</li>
<li>Access Glassfish administration panel by accessing the 4848 port of the server in your browser. The default username is admin, the default password is either empty or admin.</li>
<li>Click on Deploy an Application</li>
<li>Click on Choose File button</li>
<li>Select ClinigmentRestAPI.war from the target folder of the server-side application</li>
<li>Change context root to <strong>/</strong></li>
<li>Type the URL of your server into the browser again (same as step 1). If the installation was successful, you see the following screen</li>


</ol>
