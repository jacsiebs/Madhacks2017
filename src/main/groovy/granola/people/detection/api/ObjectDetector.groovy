package granola.people.detection.api

import granola.people.model.ImagePair
import granola.people.model.ImageSet

import java.awt.Image

interface ObjectDetector {

    // returns an overall similarity score for every image pairing
    double computeSimilarity(ImageSet images)

    // returns a similarity score for every pair of images
    List<ImagePair> computeAllSimilarities(ImageSet images)

    // returns a similarity score for any 2 images
    double computeSimilarity(Image im1, Image im2)

}