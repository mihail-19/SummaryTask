package ua.nure.teslenko.SummaryTask4.web;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ua.nure.teslenko.SummaryTask4.db.dao.DaoCommon;
import ua.nure.teslenko.SummaryTask4.db.entity.HospitalCard;
import ua.nure.teslenko.SummaryTask4.db.entity.Patient;
import ua.nure.teslenko.SummaryTask4.exception.AppException;

/**
 * Servlet for downloading patient's info.
 * @author Mykhailo Teslenko
 *
 */
@WebServlet("/download")
public class Download extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Download() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * Method makes a stream from Document object and returns 
	 * to the user "PatientInfo.xml".
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain");
		response.setHeader("Content-disposition", "attachment; filename=PatientInfo.xml");
		HttpSession session = request.getSession();
	    try(OutputStream out = response.getOutputStream()) {
	    	int patientId;
			patientId = Integer.parseInt(request.getParameter("patientId"));
			DaoCommon dao = new DaoCommon();
			Patient patient = dao.getPatient(patientId);
			ResourceBundle bundle = ResourceBundle.getBundle("resources.resource", new Locale(session.getAttribute("locale").toString()));
			Document doc = makeXML(patient, bundle);
			
	        TransformerFactory transformerFactory = TransformerFactory.newInstance();
	        Transformer transformer = transformerFactory.newTransformer();
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
	        DOMSource source = new DOMSource(doc);
	        StreamResult result = new StreamResult(out);
	        transformer.transform(source, result);
		} catch (ParserConfigurationException | AppException | TransformerException e) {
			e.printStackTrace();
		}
		
		

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	/**
	 * Method for xml building from patient parameters.
	 * Type of "description" is localized.
	 * @param patient
	 * @param bundle bundle with current locale in session
	 * @return Document with parameters
	 * @throws ParserConfigurationException
	 */
	public Document makeXML(Patient patient, ResourceBundle bundle) throws ParserConfigurationException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("PatientInfo");
        doc.appendChild(rootElement);
        
        Element firstname = doc.createElement("firstName");
        firstname.appendChild(doc.createTextNode(patient.getFirstName()));
        rootElement.appendChild(firstname);
        Element lastname = doc.createElement("lastName");
        lastname.appendChild(doc.createTextNode(patient.getLastName()));
        rootElement.appendChild(lastname);
        Element dateOfBirth = doc.createElement("dateOfBirth");
        dateOfBirth.appendChild(doc.createTextNode(patient.getDateOfBirth().toString()));
        rootElement.appendChild(dateOfBirth);
        
        Element card = doc.createElement("card");
        rootElement.appendChild(card);
        
        Element diagnose = doc.createElement("diagnose");
        diagnose.appendChild(doc.createTextNode(patient.getHospitalCard().getDiagnose()));
        card.appendChild(diagnose);
        
        Element prescriptions = doc.createElement("prescriptions");
        card.appendChild(prescriptions);
        
        for(HospitalCard.Prescription presc : patient.getHospitalCard().getPrescriptions()) {
        	Element prescription = doc.createElement("prescription");
        	prescriptions.appendChild(prescription);
        	
        	Element type = doc.createElement("type");
        	type.appendChild(doc.createTextNode(bundle.getString("prescType."+presc.getType().toString())));
        	prescription.appendChild(type);
        	Element description = doc.createElement("description");
        	description.appendChild(doc.createTextNode(presc.getDescription()));
        	prescription.appendChild(description);
        }
        return doc;
	}

}
