<h1>Final Year Software Project - Backend</h1>
<p>RESTful API of a dental clinic management system. Developed by using Jersey 2.0 and EclipseLink.</p>
<h2>Requirements</h2>
<p>Before running the server-side application, you need to have MySQL installed along with Glassfish. You also need to create a schema with the name of clinigmentdbv2 in your database.</p>
<h2>Installation Guide</h2>
<p>In the /src/main/webapp/WEB-INF folder open glassfish-resources.xml. Add the username and password of your MySQL server on line 38 and 39.</p>
<ol>
<li>
  <p>Make sure Glassfish is running, by typing your server URL:8080 into your browser. You see the following screen if your server is running.</p>
  <img src="http://176.32.230.250/lastminute84.com/images/step1.png" alt="Step 1" />
</li>
<li>
  <p>Access Glassfish administration panel by accessing the 4848 port of the server in your browser. The default username is admin, the default password is either empty or admin.</p>
  <img src="http://176.32.230.250/lastminute84.com/images/step2.png" alt="Step 2" />  
</li>
<li>
  <p>Click on Deploy an Application</p>
  <img src="http://176.32.230.250/lastminute84.com/images/step3.png" alt="Step 3" />
</li>
<li>
  <p>Click on Choose File button</p>
  <img src="http://176.32.230.250/lastminute84.com/images/step4.png" alt="Step 4" />
</li>
<li>
  <p>Select ClinigmentRestAPI.war from the target folder of the server-side application</p>
  <img src="http://176.32.230.250/lastminute84.com/images/step5.png" alt="Step 5" />
</li>
<li>
  <p>Change context root to <strong>/</strong></p>
  <img src="http://176.32.230.250/lastminute84.com/images/step6.png" alt="Step 6" />
</li>
<li>
  <p>Type the URL of your server into the browser again (same as step 1). If the installation was successful, you see the following screen</p>
  <img src="http://176.32.230.250/lastminute84.com/images/step7.png" alt="Step 7" />
</li>
</ol>
