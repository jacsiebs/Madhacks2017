package granola.people.app

import granola.people.detection.api.ObjectDetector
import granola.people.detection.impl.pHash
import granola.people.model.ImagePair
import granola.people.model.ImageSet
import nu.pattern.OpenCV
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.imageio.ImageIO
import java.awt.image.BufferedImage

class ObjectDetectionApp {

    private static Logger LOG = LoggerFactory.getLogger(ObjectDetectionApp.class)

    private static String TEST_SET_NAMES_FILE = "test_sets/image_set_names"

    // TODO inject this
    ObjectDetector detector

    static void main(String[] args) {
        OpenCV.loadLibrary()
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
        testDetectionOnImageSets()
    }

    private void testDetectionOnImageSets() {
        // load image set names
        List<String> image_set_names = loadImageSetNames()

        // output file
        File resultsFile = new File("test_results/results")
        FileOutputStream resultStream = new FileOutputStream(resultsFile, false) // overwrites file
        /* For every image set
         *  1. Load its images
         *  2. Run the detection algorithm on every pair of images in the set
         *  3. Save results to file
         */
        for(String set_name : image_set_names) {
            ImageSet<BufferedImage> curr = loadImageSet(set_name)
            // run the algorithm
            List<ImagePair<BufferedImage>> image_pairs = detector.computeAllSimilarities(curr)

            // write results to a file
            StringBuilder str = new StringBuilder()
            str.append("********** ").append(set_name).append(" **********\n Image Pair Similarities:\n")
            for(ImagePair<BufferedImage> pair : image_pairs) {
                str.append("  ").append(pair.getSimilarity()).append("\n")
            }
            str.append(" Overall Similarity: ").append(curr.getSimilarity()).append("\n\n")
            byte[] content = str.toString().getBytes()
            resultStream.write(content)
        }
        resultStream.close()
    }

    private List<String> loadImageSetNames() {
        List<String> names = new LinkedList<>()
        URL pathUrl = this.class.getClassLoader().getResource(TEST_SET_NAMES_FILE)
        File names_file = new File(pathUrl.toURI())
        Scanner sc = new Scanner(names_file)
        while(sc.hasNextLine()) {
            names.add(sc.nextLine())
        }
        return names
    }

    private ImageSet<BufferedImage> loadImageSet(String filename) {
        ImageSet imSet = new ImageSet()

        List<String> files = filesInDir("images/" + filename)
        files.each {
            imSet.add(loadImage(it))
        }
        return imSet
    }

    private List<String> filesInDir(String dir) {
        List<String> files = new LinkedList<>()
        URL pathUrl = this.class.getClassLoader().getResource(dir)
        if ((pathUrl != null) && pathUrl.getProtocol().equals("file")) {
            new File(pathUrl.toURI()).list().each {
                files << "${dir}/${it}"
            }
        }
        return files
    }

    private BufferedImage loadImage(String filename) throws FileNotFoundException {
        InputStream image_stream = this.getClass().getClassLoader().getResourceAsStream(filename)
        return ImageIO.read(image_stream)
    }

}


