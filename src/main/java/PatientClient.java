
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
