package granola.people.detection.impl

import granola.people.detection.api.Detector
import granola.people.model.ImagePair
import granola.people.model.ImageSet

class ObjectDetector implements Detector<E> {

    @Override
    List<ImagePair> computeAllSimilarities(List<String> image_filenames) {
        return null
    }

    @Override
    void computeSimilarity(ImagePair<E> pair) {

    }

    @Override
    ImageSet loadImageSet(List<String> filenames) {
        return null
    }
}
