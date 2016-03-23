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

import tollbooth.gatecontroller.GateController;

/**
 * The TollGate contains everything about a tollgate in a tollbooth.
 * @version Feb 3, 2016
 */
public class TollGate
{
	private final GateController controller;
	private final SimpleLogger logger;
	
	private boolean willNotRespond;
	private int maxAttempts = 3;
	
	private int numberOpens = 0;
	private int numberCloses = 0;
	
	/**
	 * Constructor that takes the actual gate controller and the logger.
	 * @param controller the GateController object.
	 * @param logger the SimpleLogger object.
	 */
	public TollGate(GateController controller, SimpleLogger logger) {
		this.controller = controller;
		this.logger = logger;
		
		willNotRespond = false;
	}
	
	/**
	 * Open the gate.
	
	 * @throws TollboothException */
	public void open() throws TollboothException
	{
		int numAttempts = 0;
		
		TollboothException lastFailureCause = new TollboothException("");
		
		if (willNotRespond)
		{
			logger.accept(new LogMessage("open: will not respond"));
			return;
		}
		
		if (isOpen())
		{
			return;
		}
		
		while (!isOpen() && numAttempts < maxAttempts)
		{
			numAttempts++;
			try 
			{
				controller.open();
			} 
			catch (TollboothException e)
			{
				logger.accept(new LogMessage("open: malfunction", e));
				lastFailureCause = e;
			}
		}
		
		if (isOpen())
		{
			logger.accept(new LogMessage("open: successful"));
			numberOpens++;
		}
		else 
		{
			willNotRespond = true;
			logger.accept(new LogMessage("open: unrecoverable malfunction", lastFailureCause));
		}
	}
	
	/**
	 * Close the gate
	
	 * @throws TollboothException */
	public void close() throws TollboothException
	{
		int numAttempts = 0;
		
		TollboothException lastFailureCause = new TollboothException("");
		
		if (willNotRespond)
		{
			logger.accept(new LogMessage("close: will not respond"));
			return;
		}
		
		if (!isOpen())
		{
			return;
		}
		
		while (isOpen() && numAttempts < maxAttempts)
		{
			numAttempts++;
			try 
			{
				controller.close();
			} 
			catch (TollboothException e)
			{
				logger.accept(new LogMessage("close: malfunction", e));
				lastFailureCause = e;
			}
		}
		
		if (!isOpen())
		{
			logger.accept(new LogMessage("close: successful"));
			numberCloses++;
		}
		else 
		{
			willNotRespond = true;
			logger.accept(new LogMessage("close: unrecoverable malfunction", lastFailureCause));
		}
	}
	
	/**
	 * Reset the gate to the state it was in when created with the exception of the
	 * statistics.
	
	 * @throws TollboothException */
	public void reset() throws TollboothException
	{
		int numAttempts = 0;
		
		TollboothException lastFailureCause = new TollboothException("");
		
		while (isOpen() && numAttempts < maxAttempts)
		{
			numAttempts++;
			try 
			{
				controller.reset();
			} 
			catch (TollboothException e)
			{
				logger.accept(new LogMessage("reset: malfunction", e));
				lastFailureCause = e;
			}
		}
		
		if (!isOpen())
		{
			logger.accept(new LogMessage("reset: successful"));
			willNotRespond = false;
		}
		else 
		{
			willNotRespond = true;
			logger.accept(new LogMessage("reset: unrecoverable malfunction", lastFailureCause));
		}
	}
	
	/**
	 * @return true if the gate is open
	 * @throws TollboothException 
	 */
	public boolean isOpen() throws TollboothException
	{
			return controller.isOpen();
	}
	
	/**
	 * @return the number of times that the gate has been opened (that is, the
	 *  open method has successfully been executed) since the object was created.
	 */
	public int getNumberOfOpens()
	{
		return numberOpens;
	}
	
	/**
	 * @return the number of times that the gate has been closed (that is, the
	 *  close method has successfully been executed) since the object was created.
	 */
	public int getNumberOfCloses()
	{
		return numberCloses;
	}
	
	/**
	 * Method makeNonResponsive.
	 * Sets willNotRespond to true
	 */
	public void makeNonResponsive()
	{
		willNotRespond = true;
	}
	
	public boolean isResponsive()
	{
		return !willNotRespond;
	}
}
