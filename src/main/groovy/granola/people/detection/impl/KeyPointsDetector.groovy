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
import org.openimaj.util.pair.Pair;

import java.awt.*;
import java.util.List;

class KeyPointsDetector implements ObjectDetector {

    @Override
    double computeSimilarity(ImageSet images) {
        return 0
    }

    @Override
    List<ImagePair> computeAllSimilarities(ImageSet images) {
        return null
    }

    static void main(String[] args) {
        KeyPointsDetector kpt = new KeyPointsDetector()
    }

    KeyPointsDetector() {
        computeSimilarity(null, null)
    }

    @Override
    double computeSimilarity(Image im1, Image im2) {
        // Read the images from two streams
        final String input_1Str = "/org/openimaj/examples/image/input_0.png";
        final String input_2Str = "/org/openimaj/examples/image/input_1.png";
        final FImage input_1
        final FImage input_2
        try {
            input_1 = ImageUtilities.readF(KeyPointsDetector.class.getResourceAsStream(input_1Str));
            input_2 = ImageUtilities.readF(KeyPointsDetector.class.getResourceAsStream(input_2Str));
        } catch(Exception e) {
            // TODO
        }

        // Prepare the engine to the parameters in the IPOL demo
        final ASIFTEngine engine = new ASIFTEngine(false, 7);

        // Extract the keypoints from both images
        final LocalFeatureList<Keypoint> input1Feats = engine.findKeypoints(input_1);
        System.out.println("Extracted input1: " + input1Feats.size());
        final LocalFeatureList<Keypoint> input2Feats = engine.findKeypoints(input_2);
        System.out.println("Extracted input2: " + input2Feats.size());

        // Prepare the matcher, uncomment this line to use a basic matcher as
        // opposed to one that enforces homographic consistency
        // LocalFeatureMatcher<Keypoint> matcher = createFastBasicMatcher();
        final LocalFeatureMatcher<Keypoint> matcher = createConsistentRANSACHomographyMatcher();

        // Find features in image 1
        matcher.setModelFeatures(input1Feats);
        // ... against image 2
        matcher.findMatches(input2Feats);

        // Get the matches
        final List<Pair<Keypoint>> matches = matcher.getMatches();
        System.out.println("NMatches: " + matches.size());

        // Display the results
        final MBFImage inp1MBF = input_1.toRGB();
        final MBFImage inp2MBF = input_2.toRGB();
        DisplayUtilities.display(MatchingUtilities.drawMatches(inp1MBF, inp2MBF, matches, RGBColour.RED));
        return 0.0
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
