package granola.people.detection.impl

import granola.people.detection.api.ObjectDetector
import granola.people.model.ImagePair
import granola.people.model.ImageSet

import java.awt.Image

class pHash implements ObjectDetector {
    @Override
    double computeSimilarity(ImageSet images) {
        return 0
    }

    @Override
    List<ImagePair> computeAllSimilarities(ImageSet images) {
        return null
    }

    @Override
    double computeSimilarity(Image im1, Image im2) {
        return 0
    }
}