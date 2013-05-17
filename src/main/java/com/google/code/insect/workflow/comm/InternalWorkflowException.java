/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.google.code.insect.workflow.comm;

import java.io.PrintStream;
import java.io.PrintWriter;

public class InternalWorkflowException extends RuntimeException {
	// ~ Instance fields
	// ////////////////////////////////////////////////////////

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Throwable rootCause;

	// ~ Constructors
	// ///////////////////////////////////////////////////////////

	public InternalWorkflowException() {
	}

	public InternalWorkflowException(String s) {
		super(s);
	}

	public InternalWorkflowException(String s, Throwable rootCause) {
		super(s);
		this.rootCause = rootCause;
	}

	public InternalWorkflowException(Throwable rootCause) {
		this.rootCause = rootCause;
	}

	// ~ Methods
	// ////////////////////////////////////////////////////////////////

	public Throwable getRootCause() {
		return rootCause;
	}

	public void printStackTrace() {
		super.printStackTrace();

		if (rootCause != null) {
			synchronized (System.err) {
				System.err.println("\nRoot cause:");
				rootCause.printStackTrace();
			}
		}
	}

	public void printStackTrace(PrintStream s) {
		super.printStackTrace(s);

		if (rootCause != null) {
			synchronized (s) {
				s.println("\nRoot cause:");
				rootCause.printStackTrace(s);
			}
		}
	}

	public void printStackTrace(PrintWriter s) {
		super.printStackTrace(s);

		if (rootCause != null) {
			synchronized (s) {
				s.println("\nRoot cause:");
				rootCause.printStackTrace(s);
			}
		}
	}
}
