package com.foreveross.koala.model;

public class Column {

	private String property;
	private String label;
	private boolean sortable;
	private boolean isHide;
	public Column(){
		
	}
	
	public Column(String property, String label, boolean sortable, boolean isHide) {
		super();
		this.property = property;
		this.label = label;
		this.sortable = sortable;
		this.isHide = isHide;
	}


	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public boolean isSortable() {
		return sortable;
	}
	public void setSortable(boolean sortable) {
		this.sortable = sortable;
	}
	
	
	public boolean isHide() {
		return isHide;
	}

	public void setHide(boolean isHide) {
		this.isHide = isHide;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (isHide ? 1231 : 1237);
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result
				+ ((property == null) ? 0 : property.hashCode());
		result = prime * result + (sortable ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Column other = (Column) obj;
		if (isHide != other.isHide)
			return false;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		if (property == null) {
			if (other.property != null)
				return false;
		} else if (!property.equals(other.property))
			return false;
		if (sortable != other.sortable)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Column [property=" + property + ", label=" + label
				+ ", sortable=" + sortable + ", isHide=" + isHide + "]";
	}

	
}
