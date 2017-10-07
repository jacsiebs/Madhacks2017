package granola.people.app

import granola.people.detection.api.ObjectDetector
import granola.people.detection.impl.pHash
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.DefaultServlet
import org.eclipse.jetty.servlet.ServletHolder
import org.eclipse.jetty.webapp.WebAppContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.imageio.ImageIO
import javax.inject.Inject
import java.awt.Image

class ObjectDetectionApp {

    private static Logger LOG = LoggerFactory.getLogger(ObjectDetectionApp.class)

    // TODO inject this
    ObjectDetector detector

    static void main(String[] args) {

        ObjectDetectionApp app = new ObjectDetectionApp()

//        WebAppContext webAppContext = new WebAppContext(
//                contextPath: "/",
//                descriptor: ObjectDetectionApp.class.getResource("/WEB-INF/web.xml").toString(),
//                resourceBase: ".",
//                parentLoaderPriority: true)
//
//        int port = 30001
//        Server jettyServer = new Server(port)
//        jettyServer.setHandler(webAppContext)
//        LOG.info("Started listening on port ${port}")
//
//        ServletHolder staticServlet = webAppContext.addServlet(DefaultServlet.class, "/static/*")
//        staticServlet.setInitParameter("resourceBase", ObjectDetectionApp.class.getResource("/WEB-INF/web.xml")
//                .toString().replace("/web.xml", ""))
//        staticServlet.setInitParameter("pathInfoOnly", "true")
//
//        try {
//            jettyServer.start()
//            jettyServer.join()
//        } finally {
//            LOG.info('Destroying server...')
//            jettyServer.destroy()
//        }
    }

    ObjectDetectionApp() {
        LOG.info("Object Detection App started")

        // *** YOUR IMPL HERE ***
        detector = new pHash()
//        if(detector == null) {
//            LOG.error("No ObjectDetection Impl found!")
//            throw new RuntimeException()
//        }

        // load image sets


        // test the algorithm
    }

    private loadImageSet(String filename) {
        File dir = new File("test_sets/" + filename)
        File[] directoryListing = dir.listFiles()
        if (directoryListing != null) {
            for (File child : directoryListing) {
                loadImage()
            }
        }
    }

    private Image loadImage(String filename) throws FileNotFoundException {
        InputStream image_stream = this.getClass().getClassLoader().getResourceAsStream(filename)
        return ImageIO.read(image_stream)
    }

}


