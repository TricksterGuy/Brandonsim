/* Copyright (c) 2010, Carl Burch. License information is located in the
 * com.cburch.logisim.Main source code and at www.cburch.com/logisim/. */

package com.cburch.logisim.std.memory;

import com.cburch.logisim.data.Value;
import com.cburch.logisim.instance.InstanceData;

class RegisterData extends ClockState implements InstanceData {
	Value value;

	public RegisterData() {
		value = Value.UNKNOWN;
	}
	
	public void setValue(Value value) {
		this.value = value;
	}
	
	public Value getValue() {
		return value;
	}
}