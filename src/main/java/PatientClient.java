
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.clinigment.rest.api.model.Patient;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.jackson.JacksonFeature;



/**
 *
 * @author csaba
 */
public class PatientClient {
    
    public static void main(String[] args) {
        
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("admin", "admin");
        
        ClientConfig cc = new ClientConfig().register(new JacksonFeature());
        cc.register(feature);
        
        Client client = ClientBuilder.newClient(cc);
        
        WebTarget target = client
                .target("http://ec2-52-19-236-212.eu-west-1.compute.amazonaws.com/webapi/patients/1");
        Response response = target.request(MediaType.APPLICATION_JSON).get();
        
        System.out.println("Status code: " + response.getStatus());
        switch (response.getStatus()) {
            case 200:
                Patient patient = response.readEntity(Patient.class);
                System.out.println("Patient: " + patient);
                break;
            case 404:
                System.out.println("Patient was not found.");
                break;
            case 204:
                System.out.println("Patient was not found.");
                break;
            default:
                System.out.println("An error occured.");
                break;
        }
        
        response.close();
        
        WebTarget allPatientsTarget = client
                .target("http://ec2-52-19-236-212.eu-west-1.compute.amazonaws.com/webapi/patients");
        
        Response allPatientsResponse = allPatientsTarget.request(MediaType.APPLICATION_JSON).get();
        
        //It's all the same here
        //It seems to be something on my end that's 
        //Now, as you see, I created this class in the server application, just to test
        //it. So I had the power of Maven, to download any extra dependency.
        //Do you think, you could create a new Maven project (there is an pre-defined Maven FX
        //project)? Then copy all the packages over, and rebuild it with Maven?
        //Yea I think I'd be able to do that, then I would be able to copy your dependancy setup somewhat
        //Cool. If you just look for the pom.xml file, everything should be in there. I jus show
        //you something.
        //That's how you create a new JavaFX project with Maven.
        //So the pom.xml file is a bit different, because it contains all the JavaFX staff.
        //But watch this.
        switch (allPatientsResponse.getStatus()) {
            case 200:
                List<Patient> patients = allPatientsResponse.readEntity(new GenericType<List<Patient>>(){});
                System.out.println("Patients: " + patients);
                break;
            case 404:
                System.out.println("Patient was not found.");
                break;
            case 204:
                System.out.println("Patient was not found.");
                break;
            default:
                System.out.println("An error occured.");
                break;
        }
         
        allPatientsResponse.close();
    }
}
