package com.msr.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Intended as a starting point for Unit Testing of the BuildingsController
 * class.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class BuildingsControllerTest {

	private BuildingsController buildingsController;

	@Before
	public void setUp() { buildingsController = new BuildingsController(); }

	@Test
	public void testSampleResponse_NullMessageParameter() throws Exception {
		String response = buildingsController.getSampleResponse(null, false);
		assertEquals(BuildingsController.NO_SAMPLE_PARAM_PROVIDED, response);
	}

	@Test
	public void testSampleResponse_EmptyMessageParameter() throws Exception {
		String response = buildingsController.getSampleResponse("", false);
		assertEquals(BuildingsController.NO_SAMPLE_PARAM_PROVIDED, response);
	}

	@Test
	public void testSampleResponse_MessageParameterProvided() throws Exception {
		String expectedString = "This is the expected output parameter.";
		String response = buildingsController.getSampleResponse(expectedString, false);
		assertEquals(BuildingsController.SAMPLE_PARAM_PROVIDED + expectedString, response);
	}

	@Test(expected = Exception.class)
	public void testSampleResponse_ErrorThrown_WithoutMessageParameter() throws Exception {
		buildingsController.getSampleResponse(null, true);
	}

	@Test(expected = Exception.class)
	public void testSampleResponse_ErrorThrown_WithMessageParameter() throws Exception {
		buildingsController.getSampleResponse("test", true);
	}

	/**
	 * Intended to test the controller's get all sites functionality.
	 */
	@Test
	@Ignore("Remove this @Ignore annotation when functionality is ready for test.")
	public void testGetAllSites() {
		// TODO: Flesh out test based on implementation
		// List<Site> sites = buildingsController.getAllSites();
		// assertEquals(expectedListSize, sites.getLength());
	}

	/**
	 * Intended to test the controller's get site by ID functionality.
	 */
	@Test
	@Ignore("Remove this @Ignore annotation when functionality is ready for test.")
	public void testGetSiteById() {
		// TODO: Flesh out test based on implementation
		// Site site = buildingsController.getSite(siteId);
		// assertEquals(expectedSite, site);
	}
}