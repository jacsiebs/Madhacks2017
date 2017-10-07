package granola.people.detection.impl

import granola.people.detection.api.Detector
import granola.people.model.ImagePair
import granola.people.model.ImageSet

import java.awt.image.BufferedImage

class FFTDetector implements Detector<BufferedImage> {


    @Override
    List<ImagePair> computeAllSimilarities(List<String> image_filenames) {
        return null
    }

    @Override
    void computeSimilarity(ImagePair<BufferedImage> pair) {

    }

    @Override
    ImageSet loadImageSet(List<String> filenames) {
        for(String s : filenames) {

        }
    }
}
