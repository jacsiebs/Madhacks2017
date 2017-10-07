package granola.people.model

import java.awt.Image

class ImageSet {

    private List<Image> images

    List<Image> getImages() { return images }
    void setImages(List<Image> images) { this.images = images }

    Image get(int i) {
        return images.get(i)
    }
}
