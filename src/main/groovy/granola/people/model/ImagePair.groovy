package granola.people.model

class ImagePair<E> {

    private E im1
    private E im2
    private double similarity

    ImagePair(E im1, E im2) {
        this.im1 = im1
        this.im2 = im2
    }

    E getImage_1() { return im1 }
    E getImage_2() { return im2 }

    double getSimilarity() { return similarity }
    void setSimilarity(double similarity) { this.similarity = similarity }

}
