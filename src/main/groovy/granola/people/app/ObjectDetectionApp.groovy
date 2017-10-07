package granola.people.app

import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.DefaultServlet
import org.eclipse.jetty.servlet.ServletHolder
import org.eclipse.jetty.webapp.WebAppContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.imageio.ImageIO
import java.awt.Image

class ObjectDetectionApp {

    private static Logger LOG = LoggerFactory.getLogger(ObjectDetectionApp.class)

    static void main(String[] args) {
        WebAppContext webAppContext = new WebAppContext(
                contextPath: "/",
                descriptor: ObjectDetectionApp.class.getResource("/WEB-INF/web.xml").toString(),
                resourceBase: ".",
                parentLoaderPriority: true)

        int port = 30001
        Server jettyServer = new Server(port)
        jettyServer.setHandler(webAppContext)
        LOG.info("Started listening on port ${port}")

        ServletHolder staticServlet = webAppContext.addServlet(DefaultServlet.class, "/static/*")
        staticServlet.setInitParameter("resourceBase", ObjectDetectionApp.class.getResource("/WEB-INF/web.xml")
                .toString().replace("/web.xml", ""))
        staticServlet.setInitParameter("pathInfoOnly", "true")

        try {
            jettyServer.start()
            jettyServer.join()
        } finally {
            LOG.info('Destroying server...')
            jettyServer.destroy()
        }
    }

    ObjectDetectionApp() { }

    private Image loadImage(String filename) throws FileNotFoundException {
        InputStream image_stream = this.getClass().getClassLoader().getResourceAsStream(filename)
        return ImageIO.read(image_stream)
    }

}


