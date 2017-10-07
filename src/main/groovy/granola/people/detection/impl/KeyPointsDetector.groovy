package granola.people.detection.impl;

import granola.people.detection.api.ObjectDetector;
import granola.people.model.ImagePair;
import granola.people.model.ImageSet;
import org.openimaj.feature.local.list.LocalFeatureList;
import org.openimaj.feature.local.matcher.FastBasicKeypointMatcher;
import org.openimaj.feature.local.matcher.LocalFeatureMatcher;
import org.openimaj.feature.local.matcher.MatchingUtilities;
import org.openimaj.feature.local.matcher.consistent.ConsistentLocalFeatureMatcher2d;
import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.colour.RGBColour;
import org.openimaj.image.feature.local.engine.asift.ASIFTEngine;
import org.openimaj.image.feature.local.keypoints.Keypoint;
import org.openimaj.math.geometry.transforms.HomographyRefinement;
import org.openimaj.math.geometry.transforms.estimation.RobustHomographyEstimator;
import org.openimaj.math.model.fit.RANSAC;
import org.openimaj.util.pair.Pair
import org.slf4j.Logger
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.List;

class KeyPointsDetector implements ObjectDetector<FImage> {

    private static Logger LOG = LoggerFactory.getLogger(KeyPointsDetector.class)

    @Override
    List<ImagePair<FImage>> computeAllSimilarities(List<String> image_filenames) {
        ImageSet<FImage> imSet = loadImageSet(image_filenames)
        List<ImagePair<FImage>> pairs = imSet.getAllPairs()
        for(ImagePair<FImage> pair: pairs) {
            computeSimilarity(pair)
        }
        return pairs
    }

    @Override
    ImageSet<FImage> loadImageSet(List<String> image_filenames) {
        ImageSet set = new ImageSet()
        for(String filename : image_filenames) {
            set.add(ImageUtilities.readF(this.getClass().getClassLoader().getResourceAsStream(filename)))
        }
        return set
    }

    @Override
    void computeSimilarity(ImagePair<FImage> pair) {

        // Prepare the engine to the parameters in the IPOL demo
        final ASIFTEngine engine = new ASIFTEngine(false, 7)

        // Extract the keypoints from both images
        final LocalFeatureList<Keypoint> input1Feats = engine.findKeypoints(pair.getImage_1())
        System.out.println("Extracted input1: " + input1Feats.size())
        final LocalFeatureList<Keypoint> input2Feats = engine.findKeypoints(pair.getImage_2())
        System.out.println("Extracted input2: " + input2Feats.size())

        // Prepare the matcher, uncomment this line to use a basic matcher as
        // opposed to one that enforces homographic consistency
        LocalFeatureMatcher<Keypoint> matcher = createFastBasicMatcher()
        // final LocalFeatureMatcher<Keypoint> matcher = createConsistentRANSACHomographyMatcher()

        // Find features in image 1
        matcher.setModelFeatures(input1Feats)
        // ... against image 2
        matcher.findMatches(input2Feats)

        // Get the matches
        final List<Pair<Keypoint>> matches = matcher.getMatches()
        System.out.println("NMatches: " + matches.size())

        pair.setSimilarity(matches.size())

        // Display the results
        final MBFImage inp1MBF = pair.getImage_1().toRGB()
        final MBFImage inp2MBF = pair.getImage_2().toRGB()
        DisplayUtilities.display(MatchingUtilities.drawMatches(inp1MBF, inp2MBF, matches, RGBColour.RED))
    }

    /**
     * @return a matcher with a homographic constraint
     */
    private static LocalFeatureMatcher<Keypoint> createConsistentRANSACHomographyMatcher() {
        final ConsistentLocalFeatureMatcher2d<Keypoint> matcher = new ConsistentLocalFeatureMatcher2d<Keypoint>(
                createFastBasicMatcher());
        matcher.setFittingModel(new RobustHomographyEstimator(10.0, 1000, new RANSAC.BestFitStoppingCondition(),
                HomographyRefinement.NONE));

        return matcher;
    }

    /**
     * @return a basic matcher
     */
    private static LocalFeatureMatcher<Keypoint> createFastBasicMatcher() {
        return new FastBasicKeypointMatcher<Keypoint>(8);
    }
}
