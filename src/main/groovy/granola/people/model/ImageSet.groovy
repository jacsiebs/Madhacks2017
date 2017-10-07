package granola.people.model

import java.awt.Image

class ImageSet<E extends Image> {

    private List<E> images
    private double similarity // average of all pair similarities

    ImageSet() {
        images = new ArrayList<>()
    }

    List<E> getImages() { return images }
    void setImages(List<E> images) { this.images = images }
    double getSimilarity() { return similarity }
    void setSimiarity(double sim) { similarity = sim }

    void add(E im) {
        images.add(im)
    }

    E get(int i) {
        return images.get(i)
    }

    List<ImagePair<E>> getAllPairs() {
        List<ImagePair<E>> pairs = new LinkedList<>()
        for(int i = 0; i < images.size() - 1; i++) {
            for(int j = i + 1; j < images.size(); j++) {
                pairs.add(new ImagePair(images.get(i), images.get(j)))
            }
        }
        return pairs
    }
}
