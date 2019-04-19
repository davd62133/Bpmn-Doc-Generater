import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

//These classes read the sample XML file and manage output:
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

//Finally, import the W3C definitions for a DOM, DOM exceptions, entities and nodes:
import model.*;
import model.Process;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import static j2html.TagCreator.*;

/**
 *
 * @author daniel
 */
public class xmlechoparser {

    static final String outputEncoding = "UTF-8";

    private static Main main = new Main();

    private static void usage() {
        System.out.println("Usage: xmlechoparser file1");
    }

    public static void main(String[] args) throws Exception {
        String filename = "src/main/resources/MyDiagram1 - 1.0.bpmn";

        // Retire el false para hacer un programa que se innvoque desde la linea de comandos
        if (args.length < 1 && false) {
            usage();
        } else {

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            //filename = args[0]; 
            Document doc = db.parse(new File(filename));
            navigate(doc);
        }
    }

    private static void navigate(Node n) {
        navigate(n, "",false);
        //System.out.println(main);
        generateHTML();
    }

    private static void navigateCollaboration(Node n, String prefix){
        if(n.getNodeName().equals("model:participant")){

            if(n.getAttributes().getNamedItem("processRef")!=null){
                Process process = new Process();
                process.setName(n.getAttributes().getNamedItem("name").getNodeValue());
                for(int i = 0; i<n.getChildNodes().getLength();i++){
                    if(n.getChildNodes().item(i).getNodeName().equals("model:documentation")){
                        process.setDescription(n.getChildNodes().item(i).getChildNodes().item(0).getNodeValue());
                    }
                }
                main.addProcess(process);
            }else{
                Employee employee = new Employee();
                employee.setName(n.getAttributes().getNamedItem("name").getNodeValue());
                for(int i = 0; i<n.getChildNodes().getLength();i++){
                    if(n.getChildNodes().item(i).getNodeName().equals("model:documentation")){
                        employee.setDescription(n.getChildNodes().item(i).getChildNodes().item(0).getNodeValue());
                    }
                }
                main.getProcesses().get(0).addEmployee(employee);
            }
        }

        //Navegar los nodos hijo del nodo actual
        NodeList childnodes = n.getChildNodes();
        for (int i = 0; i < childnodes.getLength(); i++) {
            navigateCollaboration(childnodes.item(i), prefix + "|-----");
        }
    }

    private static void navigateLaneSet(Node n, String prefix){
        if(n.hasChildNodes()){
            for(int i = 0; i<n.getChildNodes().getLength();i++){
                if(n.getChildNodes().item(i).hasChildNodes()){
                    Lane lane = new Lane();
                    lane.setName(n.getChildNodes().item(i).getAttributes().getNamedItem("name").getNodeValue());
                    for(int j = 0; j< n.getChildNodes().item(i).getChildNodes().getLength();j++){
                        if(n.getChildNodes().item(i).getChildNodes().item(j).getNodeName().equals("model:documentation")){
                            lane.setDescription(n.getChildNodes().item(i).getChildNodes().item(j).getChildNodes().item(0).getNodeValue());
                        }else if(n.getChildNodes().item(i).getChildNodes().item(j).getNodeName().equals("model:flowNodeRef")){
                            lane.addRefernece(n.getChildNodes().item(i).getChildNodes().item(j).getChildNodes().item(0).getNodeValue());
                        }
                    }
                    main.getProcesses().get(0).addLane(lane);
                }
            }
        }
    }

    private static void navigateProcess(Node n, String prefix){
        if(n.getNodeName().equals("model:laneSet")){
            navigateLaneSet(n,"");
        }else if(n.getNodeName().equals("model:sequenceFlow")){
            Task first = main.getProcesses().get(0).getTaskById(n.getAttributes().getNamedItem("sourceRef").getNodeValue());
            Task second = main.getProcesses().get(0).getTaskById(n.getAttributes().getNamedItem("targetRef").getNodeValue());
            first.addDestination(second);
            second.addOrigin(first);
        }else{
            if(!n.getNodeName().equals("#text") && !n.getNodeName().equals("model:ioSpecification")
            && !n.getNodeName().equals("model:inputSet")&&!n.getNodeName().equals("model:outputSet")
            &&!n.getNodeName().equals("model:process")&&!n.getNodeName().equals("model:documentation")){
                Task task = new Task();
                task.setType(n.getNodeName().replace("model:",""));
                task.setId(n.getAttributes().getNamedItem("id").getNodeValue());
                task.setName(n.getAttributes().getNamedItem("name").getNodeValue());
                if(n.hasChildNodes()) {
                    //Navegar los nodos hijo del nodo actual
                    NodeList childnodes = n.getChildNodes();
                    for (int i = 0; i < childnodes.getLength(); i++) {
                        if(childnodes.item(i).hasChildNodes()){
                            task.setDescription(childnodes.item(i).getChildNodes().item(0).getNodeValue());
                        }
                    }
                }
                main.getProcesses().get(0).addTask(task);
            }
            //Navegar los nodos hijo del nodo actual
            NodeList childnodes = n.getChildNodes();
            for (int i = 0; i < childnodes.getLength(); i++) {
                navigateProcess(childnodes.item(i), prefix + "|-----");
            }
        }
    }

    private static void navigate(Node n, String prefix,boolean print) {
        if(n.getNodeName().equals("model:collaboration")){
            navigateCollaboration(n,"");
        }else if(n.getNodeName().equals("model:process")){
            navigateProcess(n,"");
        }else {
            /**if(n.getNodeName().equals("model:collaboration")
             || n.getNodeName().equals("model:process")) print = true;
             //if(n.getNodeName().equals("#text")
             || n.getNodeName().equals("model:ioSpecification")
             || n.getNodeName().equals("model:flowNodeRef")) print = false;//
             if(print){
             System.out.println(prefix + "Node name : " + n.getNodeName());
             System.out.println(prefix + "Node type : " + getNodeTypeName(n.getNodeType()));
             System.out.println(prefix + "Node value: " + n.getNodeValue());
             }**/

            // Navegar los atributos del nodo
            NamedNodeMap childAttributes = n.getAttributes();
            if (childAttributes != null) {
                for (int i = 0; i < childAttributes.getLength(); i++) {
                    navigate(childAttributes.item(i), prefix + "|a----", print);
                }
            }

            //Navegar los nodos hijo del nodo actual
            NodeList childnodes = n.getChildNodes();
            for (int i = 0; i < childnodes.getLength(); i++) {
                navigate(childnodes.item(i), prefix + "|-----", print);
            }
        }

    }

    private static String getNodeTypeName(short nodeType) {
        String respuesta = "";
        switch (nodeType) {
            case Node.ATTRIBUTE_NODE:
                respuesta = "ATTRIBUTE_NODE";
                break;
            case Node.CDATA_SECTION_NODE:
                respuesta = "CDATA_SECTION_NODE";
                break;
            case Node.COMMENT_NODE:
                respuesta = "COMMENT_NODE";
                break;
            case Node.DOCUMENT_FRAGMENT_NODE:
                respuesta = "DOCUMENT_FRAGMENT_NODE";
                break;
            case Node.DOCUMENT_NODE:
                respuesta = "DOCUMENT_NODE";
                break;
            case Node.DOCUMENT_POSITION_CONTAINED_BY:
                respuesta = "DOCUMENT_POSITION_CONTAINED_BY";
                break;
            case Node.DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC:
                respuesta = "DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC";
                break;
            case Node.DOCUMENT_TYPE_NODE:
                respuesta = "DOCUMENT_TYPE_NODE";
                break;
            case Node.ELEMENT_NODE:
                respuesta = "ELEMENT_NODE";
                break;
            case Node.ENTITY_NODE:
                respuesta = "ENTITY_NODE";
                break;
            case Node.TEXT_NODE:
                respuesta = "TEXT_NODE";
                break;
            default:
                respuesta = "other type";
                break;
        }

        return respuesta;
    }

    private static void generateHTML(){
        Process p = main.getProcesses().get(0);
        String s = body(
                h1("Process "+p.getName()),
                div("Process Description: "+p.getDescription()),
                br(),
                table(
                        tbody(
                                tr(
                                        th("Lane Name").withStyle("border: 1px solid black;border-collapse: collapse"),
                                        th("Description").withStyle("border: 1px solid black;border-collapse: collapse"),
                                        th("Employee").withStyle("border: 1px solid black;border-collapse: collapse"),
                                        th("Tasks").withStyle("border: 1px solid black;border-collapse: collapse")
                                ).withStyle("border: 1px solid black;border-collapse: collapse"),
                                each(p.getLanes(), l -> tr(
                                        td(l.getName()).withStyle("border: 1px solid black;border-collapse: collapse"),
                                        td(l.getDescription()).withStyle("border: 1px solid black;border-collapse: collapse"),
                                        td(
                                                table(
                                                        tbody(
                                                                tr(
                                                                        td(
                                                                        "In charge: " + l.getEmployee().getName()
                                                                        ).withStyle("border: 1px solid black;border-collapse: collapse")
                                                                ),
                                                                tr(
                                                                        td(
                                                                        " Description: "+ l.getEmployee().getDescription()).withStyle("border: 1px solid black;border-collapse: collapse")
                                                                )
                                                        )
                                                )
                                        ),

                                        each(l.getTasks(), t -> td(
                                                table(
                                                        tbody(
                                                                tr(
                                                                        td(
                                                                                "Task name: " +t.getName()
                                                                        ).withStyle("border: 1px solid black;border-collapse: collapse")
                                                                ),
                                                                tr(
                                                                        td(
                                                                                " Task Description: "+t.getDescription()
                                                                        ).withStyle("border: 1px solid black;border-collapse: collapse")
                                                                ),
                                                                tr(
                                                                        td(
                                                                                " Type: "+t.getType()
                                                                        ).withStyle("border: 1px solid black;border-collapse: collapse")
                                                                ),
                                                                tr(
                                                                        td(
                                                                        " Origins:" + each(t.getOrigins(), o -> div(o.getName()+" ")).render().replace("<div>","").replace("</div>","")+" "
                                                                        ).withStyle("border: 1px solid black;border-collapse: collapse")
                                                                ),
                                                                tr(
                                                                        td(
                                                                                " Destinations:" + each(t.getDestinations(), o -> div(o.getName()+" ")).render().replace("<div>","").replace("</div>","")+" "
                                                                        ).withStyle("border: 1px solid black;border-collapse: collapse")
                                                                )
                                                        )
                                                )
                                        ).withStyle("border: 1px solid black;border-collapse: collapse"))

                                ).withStyle("border: 1px solid black;border-collapse: collapse"))
                        )
                ).withStyle("border: 1px solid black;border-collapse: collapse")
        ).render();
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("example.html"));
            writer.write(s);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}