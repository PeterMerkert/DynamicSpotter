package org.spotter.core.test.dummies.detection;

import org.spotter.core.detection.AbstractDetectionExtension;
import org.spotter.core.detection.IDetectionController;

public class DetectionFExtension extends AbstractDetectionExtension {

	@Override
	public IDetectionController createExtensionArtifact() {
		return new DetectionF(this);
	}

	@Override
	public String getName() {
		return "DetectionF";
	}

	@Override
	protected void initializeConfigurationParameters() {

	}

}
