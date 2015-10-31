/*
 * Copyright (C) 2015 thirdy
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package io.jexiletools.es.model.json;

import java.util.Map;

/**
 * @author thirdy
 *
 */
public class Range {
	private Double min;
	private Double max;
	
	public Range() {
		super();
	}
	
	public Range(Double min, Double max) {
		this.min = min;
		this.max = max;
	}

	public Range(Map<String, Double> m) {
		this.min = m.get("min");
		this.max = m.get("max");
	}

	public Double getMin() {
		return min;
	}
	public void setMin(Double min) {
		this.min = min;
	}
	public Double getMax() {
		return max;
	}
	public void setMax(Double max) {
		this.max = max;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Range [min=");
		builder.append(min);
		builder.append(", max=");
		builder.append(max);
		builder.append("]");
		return builder.toString();
	}

	
}
