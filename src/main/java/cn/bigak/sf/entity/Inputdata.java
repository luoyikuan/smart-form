package cn.bigak.sf.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Inputdata entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "inputdata", catalog = "sf")

public class Inputdata implements java.io.Serializable {

	// Fields

	private Integer inputDataId;
	private Input input;
	private Integer rowIndex;
	private String value;

	// Constructors

	/** default constructor */
	public Inputdata() {
	}

	/** minimal constructor */
	public Inputdata(Input input, Integer rowIndex) {
		this.input = input;
		this.rowIndex = rowIndex;
	}

	/** full constructor */
	public Inputdata(Input input, Integer rowIndex, String value) {
		this.input = input;
		this.rowIndex = rowIndex;
		this.value = value;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "inputDataId", unique = true, nullable = false)

	public Integer getInputDataId() {
		return this.inputDataId;
	}

	public void setInputDataId(Integer inputDataId) {
		this.inputDataId = inputDataId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "inputId", nullable = false)

	public Input getInput() {
		return this.input;
	}

	public void setInput(Input input) {
		this.input = input;
	}

	@Column(name = "rowIndex", nullable = false)

	public Integer getRowIndex() {
		return this.rowIndex;
	}

	public void setRowIndex(Integer rowIndex) {
		this.rowIndex = rowIndex;
	}

	@Column(name = "value")

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}