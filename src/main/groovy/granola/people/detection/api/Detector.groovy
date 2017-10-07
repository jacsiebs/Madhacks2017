package granola.people.detection.api

import granola.people.model.ImagePair
import granola.people.model.ImageSet

interface Detector<E> {

    // returns a similarity score for every pair of images
    List<ImagePair> computeAllSimilarities(List<String> image_filenames)

    // computes the similarity for a single image pair
    void computeSimilarity(ImagePair<E> pair)

    // load the proper type of images given their filenames
    ImageSet loadImageSet(List<String> filenames)

}