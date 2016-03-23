// $codepro.audit.disable methodJavadoc
/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Copyright ©2016 Gary F. Pollice
 *******************************************************************************/

package tollbooth;

import static org.junit.Assert.*;
import org.junit.Test;
import tollbooth.gatecontroller.*;

/**
 * Test cases for the Tollbooth, TollGate class.
 * @version Feb 3, 2016
 */
public class TollboothTest
{

	@Test
	public void createNewTollGateWithNoController()
	{
		assertNotNull(new TollGate(null, null));
	}
	
	@Test
	public void createNewTollGateWithAController()
	{
		assertNotNull(new TollGate(new TestGateController(), null));
	}
	
	@Test
	public void newGateControllerIsClosed() throws TollboothException
	{
		final GateController controller = new TestGateController();
		final TollGate gate = new TollGate(controller, null);
		assertFalse(gate.isOpen());
	}

	@Test
	public void gateControllerIsOpenAfterOpenMessage() throws TollboothException
	{
		final GateController controller = new TestGateController();
		final SimpleLogger logger = new SimpleLoggerImplementation();
		final TollGate gate = new TollGate(controller, logger);
		gate.open();
		assertTrue(gate.isOpen());
	}
	
	@Test
	public void gateControllerIsOpenAfterTwoOpens() throws TollboothException
	{
		final GateController controller = new TestGateController();
		final SimpleLogger logger = new SimpleLoggerImplementation();
		final TollGate gate = new TollGate(controller, logger);
		gate.open();
		gate.open();
		assertTrue(gate.isOpen());
	}
	
	@Test
	public void gateOpensAfterOneMalfunction() throws TollboothException
	{
		/**
		 * NOTE: you will need to have a different test GateController or modify the
		 * current one to be programmed to fail opening one time before opening correctly.
		 * You will also need to create your own instance of a SimpleLogger and pass it into the constructor. 
		 */
		final TestGateController controller = new TestGateController();
		final SimpleLogger logger = new SimpleLoggerImplementation();
		final TollGate gate = new TollGate(controller, logger);
		controller.makeNextNTriesFail(1);
		gate.open();
		LogMessage message = logger.getNextMessage();
		assertEquals("open: malfunction", message.getMessage());
		message = logger.getNextMessage();
		assertEquals("open: successful", message.getMessage());
		assertTrue(gate.isOpen());
	}
	
	@Test
	public void gateOpensAfterTwoMalfunctions() throws TollboothException
	{
		final TestGateController controller = new TestGateController();
		final SimpleLogger logger = new SimpleLoggerImplementation();
		final TollGate gate = new TollGate(controller, logger);
		controller.makeNextNTriesFail(2);
		gate.open();
		LogMessage message = logger.getNextMessage();
		assertEquals("open: malfunction", message.getMessage());
		message = logger.getNextMessage();
		assertEquals("open: malfunction", message.getMessage());
		message = logger.getNextMessage();
		assertEquals("open: successful", message.getMessage());
		assertTrue(gate.isOpen());
	}
	
	@Test
	public void gateWillNotRespondAfterThreeOpens() throws TollboothException
	{
		final TestGateController controller = new TestGateController();
		final SimpleLogger logger = new SimpleLoggerImplementation();
		final TollGate gate = new TollGate(controller, logger);
		controller.makeNextNTriesFail(3);
		gate.open();
		LogMessage message = logger.getNextMessage();
		assertEquals("open: malfunction", message.getMessage());
		message = logger.getNextMessage();
		assertEquals("open: malfunction", message.getMessage());
		message = logger.getNextMessage();
		assertEquals("open: malfunction", message.getMessage());
		message = logger.getNextMessage();
		assertEquals("open: unrecoverable malfunction", message.getMessage());
		assertFalse(gate.isOpen());
	}
	
	@Test
	public void gateWillNotRespondToOpen() throws TollboothException
	{
		final GateController controller = new TestGateController();
		final SimpleLogger logger = new SimpleLoggerImplementation();
		final TollGate gate = new TollGate(controller, logger);
		gate.makeNonResponsive();
		gate.open();
		LogMessage message = logger.getNextMessage();
		assertEquals("open: will not respond", message.getMessage());
		assertFalse(gate.isOpen());
	}
	
	@Test
	public void gateControllerIsClosedAfterCloseMessage() throws TollboothException 
	{
		final GateController controller = new TestGateController();
		final SimpleLogger logger = new SimpleLoggerImplementation();
		final TollGate gate = new TollGate(controller, logger);
		gate.open();
		gate.close();
		assertFalse(gate.isOpen());
	}
	
	@Test
	public void gateControllerIsClosedAfterTwoCloses() throws TollboothException
	{
		final GateController controller = new TestGateController();
		final SimpleLogger logger = new SimpleLoggerImplementation();
		final TollGate gate = new TollGate(controller, logger);
		gate.open();
		gate.close();
		gate.close();
		assertFalse(gate.isOpen());
	}
	
	@Test
	public void gateClosesAfterOneMalfunction() throws TollboothException
	{
		final TestGateController controller = new TestGateController();
		final SimpleLogger logger = new SimpleLoggerImplementation();
		final TollGate gate = new TollGate(controller, logger);
		gate.open();
		LogMessage message = logger.getNextMessage(); // Throw the open message away
		controller.makeNextNTriesFail(1);
		gate.close();
		message = logger.getNextMessage();
		assertEquals("close: malfunction", message.getMessage());
		message = logger.getNextMessage();
		assertEquals("close: successful", message.getMessage());
		assertFalse(gate.isOpen());
	}
	
	@Test
	public void gateClosesAfterTwoMalfunctions() throws TollboothException
	{
		final TestGateController controller = new TestGateController();
		final SimpleLogger logger = new SimpleLoggerImplementation();
		final TollGate gate = new TollGate(controller, logger);
		gate.open();
		LogMessage message = logger.getNextMessage(); // Throw the open message away
		controller.makeNextNTriesFail(2);
		gate.close();
		message = logger.getNextMessage();
		assertEquals("close: malfunction", message.getMessage());
		message = logger.getNextMessage();
		assertEquals("close: malfunction", message.getMessage());
		message = logger.getNextMessage();
		assertEquals("close: successful", message.getMessage());
		assertFalse(gate.isOpen());
	}
	
	@Test
	public void gateWillNotRespondAfterThreeCloses() throws TollboothException
	{
		final TestGateController controller = new TestGateController();
		final SimpleLogger logger = new SimpleLoggerImplementation();
		final TollGate gate = new TollGate(controller, logger);
		gate.open();
		LogMessage message = logger.getNextMessage(); // Throw the open message away
		controller.makeNextNTriesFail(3);
		gate.close();
		message = logger.getNextMessage();
		assertEquals("close: malfunction", message.getMessage());
		message = logger.getNextMessage();
		assertEquals("close: malfunction", message.getMessage());
		message = logger.getNextMessage();
		assertEquals("close: malfunction", message.getMessage());
		message = logger.getNextMessage();
		assertEquals("close: unrecoverable malfunction", message.getMessage());
		assertTrue(gate.isOpen());
	}
	
	@Test
	public void gateWillNotRespondToClose() throws TollboothException
	{
		final GateController controller = new TestGateController();
		final SimpleLogger logger = new SimpleLoggerImplementation();
		final TollGate gate = new TollGate(controller, logger);
		gate.open();
		LogMessage message = logger.getNextMessage(); // Throw the open message away
		gate.makeNonResponsive();
		gate.close();
		message = logger.getNextMessage();
		assertEquals("close: will not respond", message.getMessage());
		assertTrue(gate.isOpen());
	}
	
	@Test
	public void gateResetsOnClose() throws TollboothException
	{
		final GateController controller = new TestGateController();
		final SimpleLogger logger = new SimpleLoggerImplementation();
		final TollGate gate = new TollGate(controller, logger);
		gate.reset();
		LogMessage message = logger.getNextMessage();
		assertEquals("rest: successful", message.getMessage());
		assertFalse(gate.isOpen());
		assertTrue(gate.isResponsive());
	}
	
	@Test
	public void gateResetsOnOpen() throws TollboothException
	{
		final GateController controller = new TestGateController();
		final SimpleLogger logger = new SimpleLoggerImplementation();
		final TollGate gate = new TollGate(controller, logger);
		gate.open();
		gate.reset();
		LogMessage message = logger.getNextMessage();
		assertEquals("rest: successful", message.getMessage());
		assertFalse(gate.isOpen());
		assertTrue(gate.isResponsive());
	}
	
	@Test
	public void gateFailsOneReset() throws TollboothException
	{
		final TestGateController controller = new TestGateController();
		final SimpleLogger logger = new SimpleLoggerImplementation();
		final TollGate gate = new TollGate(controller, logger);
		gate.open();
		LogMessage message = logger.getNextMessage(); // Throw the open message away
		controller.makeNextNTriesFail(1);
		gate.reset();
		message = logger.getNextMessage();
		assertEquals("reset: successful", message.getMessage());
		message = logger.getNextMessage();
		assertEquals("reset: successful", message.getMessage());
		assertFalse(gate.isOpen());
		assertTrue(gate.isResponsive());
	}
	
	@Test
	public void gateFailsThreeResets() throws TollboothException
	{
		final TestGateController controller = new TestGateController();
		final SimpleLogger logger = new SimpleLoggerImplementation();
		final TollGate gate = new TollGate(controller, logger);
		gate.open();
		LogMessage message = logger.getNextMessage(); // Throw the open message away
		controller.makeNextNTriesFail(3);
		gate.reset();
		message = logger.getNextMessage();
		assertEquals("reset: malfunction", message.getMessage());
		message = logger.getNextMessage();
		assertEquals("reset: malfunction", message.getMessage());
		message = logger.getNextMessage();
		assertEquals("reset: malfunction", message.getMessage());
		message = logger.getNextMessage();
		assertEquals("reset: unrecoverable malfunction", message.getMessage());
		assertTrue(gate.isOpen());
		assertFalse(gate.isResponsive());
	}
	
	@Test
	public void gateResetsFromNonResponsiveOpen() throws TollboothException
	{
		final TestGateController controller = new TestGateController();
		final SimpleLogger logger = new SimpleLoggerImplementation();
		final TollGate gate = new TollGate(controller, logger);
		gate.open();
		gate.makeNonResponsive();
		gate.reset();
		assertTrue(gate.isResponsive());
	}
	
	@Test
	public void gateResetsFromNonResponsiveClose() throws TollboothException
	{
		
	}
	
	@Test
	public void gateFailsOneResetFromNonResponsive() throws TollboothException
	{
		
	}
	
	@Test
	public void gateFailsTwoResetsFromNonResponsive() throws TollboothException
	{
		
	}
	
	@Test
	public void gateFailsThreeResetsFromNonResponsive() throws TollboothException
	{
		
	}
}
