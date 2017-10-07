package granola.people.model

import java.awt.Image

class ImagePair<E extends Image> {

    private E im1
    private E im2
    private double similarity

    ImagePair(E im1, E im2) {
        this.im1 = im1
        this.im2 = im2
    }

    double getSimilarity() { return similarity }
    void setSimilarity(double similarity) { this.similarity = similarity }

}
